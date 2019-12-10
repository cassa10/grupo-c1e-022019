import React from 'react';
import '../dist/css/ProviderHome.css';
import '../dist/css/SearchResult.css';
import {
  Col, Container, Row, Card,
} from 'react-bootstrap';
import StarRatingComponent from 'react-star-rating-component';
import { withTranslation } from 'react-i18next';
import Badge from 'react-bootstrap/Badge';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import API from '../service/api';
import addMenuIcon from '../dist/icons/add-icon.png';
import editMenuIcon from '../dist/icons/pencil-edit-icon.png';
import menuInfoIcon from '../dist/icons/info-icon.png';
import formatPrice from './formatter/formatCredit';
import formatNumber from './formatter/formatNumber';
import formatDate from './formatter/formatDate';

class ProviderHome extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      menuImage: 'https://www.seriouseats.com/recipes/images/2015/07/20150728-homemade-whopper-food-lab-35-1500x1125.jpg',
      // 'https://i.ibb.co/8DPBPZw/menu-Image.png', (el que estaba antes)
      googleId: '',
      tokenAccess: '',
      user: {
        id: 0,
        googleId: '',
        name: '',
        logo: '',
        city: '',
        menus: [],
      },
      showModalSee: false,
      sideBarSelected: 'home',
    };
  }

  componentDidMount() {
    const body = {
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
    };
    this.setState({
      googleId: body.googleId,
      tokenAccess: body.tokenAccess,
      sideBarSelected: this.props.location.state.sideBarSelected,
    });

    API.get('/provider', body)
      .then((response) => this.setState({ user: response }))
      .catch((error) => this.handleErrorAPI(error));
  }

  setShowSee(b) {
    this.setState({ showModalSee: b });
  }

  handleErrorAPI(error) {
    this.props.history.push({
      pathname: '/error',
      state: {
        googleId: this.props.location.state.googleId,
        tokenAccess: this.props.location.state.tokenAccess,
        user: this.props.location.state.user,
        error,
      },
    });
  }

  pushToCreateMenu() {
    this.props.history.push({
      pathname: '/create_menu',
      state: {
        googleId: this.props.location.state.googleId,
        tokenAccess: this.props.location.state.tokenAccess,
        user: this.state.user,
      },
    });
  }

  pushToEditMenu(menux) {
    this.props.history.push({
      pathname: 'edit_menu',
      state: {
        googleId: this.props.location.state.googleId,
        tokenAccess: this.props.location.state.tokenAccess,
        user: this.state.user,
        menu: menux,
      },
    });
  }

  showStars(menu) {
    return (
      <StarRatingComponent
        className="stars"
        name="rate2"
        editing={false}
        starCount={5}
        value={menu.rankAverage}
      />
    );
  }

  showBadge(menu) {
    menu.categories.map((cat) => (
      <Badge pill variant="warning">
        {cat.name}
      </Badge>
    ));
  }

  createFreeBadge(t) {
    return (
      <Badge pill variant="success">
        {t('Free')}
      </Badge>
    );
  }

  seeButton(menu, t) {
    const handleClose = () => this.setShowSee(false);
    const handleShow = () => this.setShowSee(true);
    return (
      <div className="">
        <Button className="buy-button" variant="info" onClick={handleShow}>
          <img src={menuInfoIcon} alt={t('Ver')} />
        </Button>
        <Modal show={this.state.showModalSee} onHide={handleClose}>
          <Modal.Header closeButton>
            <h1>{menu.name}</h1>
          </Modal.Header>
          <Modal.Title>{menu.description}</Modal.Title>
          <Modal.Body>
            <Card.Img className="card_img" variant="left" src={this.state.menuImage} /><br />
            <h5>{t('Delivery')} {menu.deliveryValue === 0 ? this.createFreeBadge(t) : formatPrice(t, menu.deliveryValue)} <br />
              {t('Comprando mas de')} {formatNumber(t, menu.firstMinAmount)} {t('unidades')} <br />
              {t('the price will be')} {formatPrice(t, menu.firstMinAmountPrice)}<br />
              {t('Comprando mas de')} {formatNumber(t, menu.menuPriceCalculator.secondMinAmount)} {t('unidades')}<br />
              {t('the price will be')} {formatPrice(t, menu.menuPriceCalculator.secondMinAmountPrice)}<br />
              {t('Distancia de delivery')}: {formatNumber(t, menu.deliveryMaxDistanceInKM)} {t('kms')}<br />
              {t('Estado del menu')}:
              <p
                className={menu.menuStateName === 'CancelledMenu' ? 'state-cancel-text' : 'state-normal-text'}
              >
                {menu.menuStateName}
              </p><br />
            </h5>
          </Modal.Body>

        </Modal>
      </div>
    );
  }


  editButton(menu, t) {
    return (
      <Button className="buy-button" variant="warning" onClick={() => this.pushToEditMenu(menu)}>
        <img src={editMenuIcon} alt={t('Editar')} />
      </Button>
    );
  }

  renderMenues(t) {
    return (
      this.state.user.menus.map((menu) => this.renderMenu(menu, t))
    );
  }

  renderMenu(menu, t) {
    return (
      <div className="menu_card" key={menu.id}>
        <Card>
          <Card.Header as="h4">{menu.name}</Card.Header>
          <Card.Body>
            <Row>
              <Col lg={4.5}>
                <Card.Img className="card_img" variant="left" src={this.state.menuImage} />
              </Col>
              <Col>
                {this.showStars(menu)}
                {this.showBadge(menu)}
                <h5>
                  {t('Description')}: {menu.description}<br />
                  {t('Valido hasta')} {formatDate(t, new Date(menu.effectiveDateGoodThru))}<br />
                </h5>
                <h4>
                  {`${t('Precio')}: ${formatPrice(t, menu.price)}`}
                </h4>
              </Col>
              <Col lg={2}>
                {this.seeButton(menu, t)}
                {this.editButton(menu, t)}
              </Col>
            </Row>
          </Card.Body>
        </Card>
      </div>
    );
  }

  render() {
    const { t } = this.props;
    console.log(this.state.user);
    return (
      <div className="home_provider_all">
        <Container>
          <Row>
            <Col className="around_provider_title">
              <h1 className="provider-title">{this.state.user.name}</h1>
            </Col>
          </Row>
          <Row>
            <hr />
          </Row>
          <Row className="your_menus_container_2">
            <Col>
              <h3 className="your_menus_title">{t('your menus')}</h3>
            </Col>
            <Col className="add-button">
              <Button className="add-button" variant="success" onClick={() => this.pushToCreateMenu()}>
                <img src={addMenuIcon} alt={t('add menu')} width="30" />
              </Button>
            </Col>
          </Row>
          <Row>
            <Col>
              {this.renderMenues(t)}
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

export default withTranslation()(ProviderHome);
