import React from 'react';
import '../dist/css/ModalBuyMenu.css';
import {
  Row, Col, Modal, Button, Container, Form,
} from 'react-bootstrap';
import Calendar from 'react-calendar';
import TimeField from 'react-simple-timefield';
import { withTranslation } from 'react-i18next';
import Swal from 'sweetalert2';
import formatPrice from './formatter/formatCredit';
import formatNumber from './formatter/formatNumber';
import shoppingCartIcon from '../dist/icons/shopping-cart-buy-icon.png';
import API from '../service/api';
import formatDate from './formatter/formatDate';

class ModalBuyMenu extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      googleId: '',
      tokenAccess: '',
      user: {},
      menu: {},
      quantityOfMenus: 0,
      isDelivery: false,
      // ESTO ESTA HARDCODEADO (PONER INPUT DEL MAP)
      coord: {
        latitude: '',
        longitude: '',
      },
      location: '',
      timeDeliveryDate: '23:59',
      dateDeliveryDate: undefined,
      modalOpen: false,
    };
  }

  componentDidMount() {
    const body = {
      googleId: this.props.googleId,
      tokenAccess: this.props.tokenAccess,
      user: this.props.user,
      menu: this.props.menu,
    };

    this.setState({
      googleId: body.googleId,
      tokenAccess: body.tokenAccess,
      user: body.user,
      menu: body.menu,
    });
  }

  setShowModal(b) {
    this.setState({ modalOpen: b });
  }

  getBodyToMakeOrder() {
    const day = this.getDeliveryInputDateTime().getDate();
    const month = this.getDeliveryInputDateTime().getMonth() + 1;
    const year = this.getDeliveryInputDateTime().getFullYear();
    const deliveryDateTime = `${year}-${month}-${day}T${this.state.timeDeliveryDate}:00.000Z`;
    if (this.state.isDelivery) {
      return (
        {
          tokenAccess: this.state.tokenAccess,
          googleId: this.state.googleId,
          idClient: this.state.user.id,
          idMenu: this.state.menu.id,
          menusAmount: this.state.quantityOfMenus,
          deliverType: 'delivery',
          deliverDate: deliveryDateTime,
          destination: {
            coord: this.state.coord,
            location: this.state.location,
          },
        }
      );
    }
    return (
      {
        tokenAccess: this.state.tokenAccess,
        googleId: this.state.googleId,
        idClient: this.state.user.id,
        idMenu: this.state.menu.id,
        menusAmount: this.state.quantityOfMenus,
        deliverType: 'pickup',
        deliverDate: deliveryDateTime,
      }
    );
  }

  getDeliveryInputDateTime() {
    const hourInput = this.state.timeDeliveryDate.split(':', 2)[0];
    const minuteInput = this.state.timeDeliveryDate.split(':', 2)[1];
    const date = new Date(this.state.dateDeliveryDate.setHours(hourInput, minuteInput, 0));
    return date;
  }

  handleQuantityOfMenus(e) {
    this.setState({ quantityOfMenus: parseInt(e.target.value, 10) });
  }

  handleDelivery(e) {
    this.setState({ isDelivery: e.target.checked });
  }

  handleLocationOfDelivery(e) {
    this.setState({ location: e.target.value });
  }

  handleDeliveryTime(e) {
    this.setState({ timeDeliveryDate: e.target.value });
  }

  handleDeliveryDate(date) {
    this.setState({ dateDeliveryDate: date });
  }

  calculatePrice() {
    if (this.state.quantityOfMenus >= this.state.menu.secondMinAmount) {
      return (this.state.quantityOfMenus * this.state.menu.secondMinAmountPrice);
    }
    if (this.state.quantityOfMenus >= this.state.menu.firstMinAmount) {
      return (this.state.quantityOfMenus * this.state.menu.firstMinAmountPrice);
    }
    return (this.state.quantityOfMenus * this.state.menu.price);
  }

  isValidDeliveryDestination() {
    // Ya se este if es innecesario pero para entender mas facil la logica.
    if (this.state.isDelivery) {
      return (
        this.state.coord.latitude.trim().length > 0
          && this.state.coord.longitude.trim().length > 0
          && this.state.location.trim().length > 0
      );
    }
    return (true);
  }

  isValidDeliveryDateTime() {
    const minDate = this.dateNowPlusDays(2);
    const maxDate = this.dateNowPlusDays(15);
    if (this.state.dateDeliveryDate) {
      const date = this.getDeliveryInputDateTime();
      return (
        date >= minDate && date <= maxDate
      );
    }
    return false;
  }

  validForm() {
    return (
      this.state.quantityOfMenus > 0
      && this.isValidDeliveryDateTime()
      && this.isValidDeliveryDestination()
    );
  }

  handleBuyFromModal(t) {
    if (this.validForm()) {
      const price = this.calculatePrice() + this.state.menu.deliveryValue;
      Swal.fire({
        title: t('¿Estas seguro?'),
        text: `${t("We'll debit ")} ${formatPrice(t, price)} - 
            ${t('Deliver DateTime')} ${formatDate(t, this.getDeliveryInputDateTime())} ${this.state.timeDeliveryDate}`,
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: 'Green',
        cancelButtonColor: 'Red',
        confirmButtonText: t('buy'),
        cancelButtonText: t('cancel'),
      })
        .then((result) => this.buyConfirmed(result.value, t))
        .catch((error) => console.log(error.response));
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: t('Please, complete all fields'),
      });
    }
  }

  buyConfirmed(isConfirmed, t) {
    if (isConfirmed) {
      this.makeOrderRequest(t);
    }
  }

  makeOrderRequest(t) {
    const body = this.getBodyToMakeOrder();
    API.post('/order', body)
      .then((response) => this.buyDone(t, response))
      .catch((error) => this.handleBuyAPIError(t, error));
  }

  handleBuyAPIError(t, error) {
    console.log(error);
    if (error && error.response && error.response.data) {
      this.showAppropiateErrorAPI(t, error.response.data);
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: t('Something went wrong'),
      });
    }
  }

  showAppropiateErrorAPI(t, message) {
    if (message === 'Sorry, this menu is cancelled') {
      this.showErrorMessage(t, message);
    }
    if (message === 'Rank your previous orders, please') {
      this.showErrorMessage(t, message);
    }
    if (message === 'Invalid data form') {
      this.showErrorMessage(t, message);
    }
    if (message === 'Menu provider reach own limit of orders, try another deliver date') {
      this.showErrorMessage(t, message);
    }
    if (message === 'Client does not has enough credits') {
      this.showErrorMessage(t, 'Insufficient credit');
    }
    if (message === 'Please, log in' || message === 'Invalid data request') {
      this.props.history.push({
        pathname: '/',
      });
    }
  }

  showErrorMessage(t, message) {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: t(message),
    });
  }

  buyDone(t, response) {
    Swal.fire(
      t('Done!'),
      `${t('enjoy your')} ${this.state.menu.name}`,
      'success',
    )
      .then(() => this.goToHome(response))
      .catch((error) => console.log(error));
  }

  goToHome(response) {
    this.setShowModal(false);
    this.setState({ isDelivery: false });
    this.props.history.push({
      pathname: '/home',
      state: {
        googleId: this.state.googleId,
        tokenAccess: this.state.tokenAccess,
        user: response,
        sideBarSelected: 'home',
        isClient: true,
      },
    });
  }

  createMenuPricesInfo(t) {
    return (
      <div>
        <Row>
          <Col className="text-center title_info_menu">
            {t('Precios del menu')}
          </Col>
        </Row>
        <Row>
          <Col>
            {t('Compra por menor')}
          </Col>
          <Col className="text-right">
            {formatPrice(t, this.props.menu.price)}
          </Col>
        </Row>
        <Row>
          <Col>
            {t('Comprando')} x{formatNumber(t, this.state.menu.firstMinAmount)} {t('unidades')}
          </Col>
          <Col className="text-right">
            {formatPrice(t, this.props.menu.firstMinAmountPrice)}
          </Col>
        </Row>
        <Row>
          <Col>
            {t('Comprando')} x{formatNumber(t, this.props.menu.menuPriceCalculator.secondMinAmount)} {t('unidades')}
          </Col>
          <Col className="text-right">
            {formatPrice(t, this.props.menu.menuPriceCalculator.secondMinAmountPrice)}
          </Col>
        </Row>
        <Row className="end-menu-info">
          <Col>
            {t('Delivery')}
          </Col>
          <Col className="text-right">
            {formatPrice(t, this.props.menu.deliveryValue)}
          </Col>
        </Row>
      </div>
    );
  }

  showLocationAndMapIfIsDelivery(t) {
    if (this.state.isDelivery) {
      return (
        <div>
          <Row>
            <Col>
              <input type="text" placeholder={t('Location')} onChange={(e) => this.handleLocationOfDelivery(e)} />
            </Col>
          </Row>
          <Row>
            {this.showMap(t)}
          </Row>
        </div>
      );
    }
    return (
      <div />
    );
  }

  showMap(t) {
    return (
      <div>
        {t('FALTA AGREGAR EL MAPA ACA PARA ELEGIR EL DESTINO SIEMPRE VA A TIRAR ERROR')}
        {t(' CON ESTE MENSAJE PORQUE NUNCA SE SETEAN UNAS COORDENADAS VALIDAS Y SE VALIDA QUE ESO ESTE')}
      </div>
    );
  }

  dateNowPlusDays(days) {
    const result = new Date();
    result.setDate(result.getDate() + days);
    return result;
  }

  render() {
    const { t } = this.props;
    const handleClose = () => { this.setShowModal(false); this.setState({ isDelivery: false }); };
    const handleShow = () => this.setShowModal(true);
    return (
      <div>
        <Button className="buy-button" variant="danger" onClick={handleShow}>
          <img src={shoppingCartIcon} alt="buy" />
        </Button>
        <Modal show={this.state.modalOpen} onHide={handleClose}>
          <Container>
            <Modal.Header closeButton>
              <Modal.Title>
                <Row>
                  <Col>
                    {t('buy')} {` "${this.state.menu.name}"`}
                  </Col>
                </Row>
              </Modal.Title>
            </Modal.Header>
            <Modal.Body className="buy-body">
              {this.createMenuPricesInfo(t)}
              <Row>
                <Col>
                  {t('How many?')}
                </Col>
                <Col>
                  <input type="number" min="1" onChange={(e) => this.handleQuantityOfMenus(e)} />
                </Col>
              </Row>
              <Row>
                <Col className="text-center">
                  {t('Deliver date')}
                </Col>
              </Row>
              <Row>
                <Col className="text-center">
                  <Calendar
                    className="calendar_input"
                    onChange={(date) => this.handleDeliveryDate(date)}
                    value={this.state.dateFrom}
                    minDate={this.dateNowPlusDays(2)}
                    maxDate={this.dateNowPlusDays(15)}
                  />
                </Col>
              </Row>
              <Row className="timefield_all">
                <Col className="text-left">
                  {t('Deliver time')}
                </Col>
                <Col>
                  <TimeField
                    className="timefield_input"
                    value={this.state.timeDeliveryDate}
                    onChange={(e) => this.handleDeliveryTime(e)}
                  />
                </Col>
              </Row>
              <Row>
                <Col>
                  {t('Do you want delivery?')}
                </Col>
                <Col>
                  <Form.Check inline label="Delivery" type="checkbox" id="inline-checkbox-1" onChange={(e) => this.handleDelivery(e)} />
                </Col>
              </Row>
              {this.showLocationAndMapIfIsDelivery(t)}
            </Modal.Body>
            <Modal.Footer>
              <Row>
                <Col>
                  <Button variant="danger" onClick={handleClose}>
                    {t('cancel')}
                  </Button>
                </Col>
                <Col>
                  <Button variant="success" onClick={() => this.handleBuyFromModal(t)}>
                    {t('buy')}
                  </Button>
                </Col>
              </Row>
            </Modal.Footer>
          </Container>
        </Modal>
      </div>
    );
  }
}

export default withTranslation()(ModalBuyMenu);
