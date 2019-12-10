import React from 'react';
import { withTranslation } from 'react-i18next';
import {
  Button, Container, Form, Row, Col,
} from 'react-bootstrap';
import Calendar from 'react-calendar';
import Swal from 'sweetalert2';
import API from '../service/api';
import '../dist/css/CreateMenu.css';

class RegisterMenuForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      dateFrom: new Date(),
      dateThru: new Date(),
      name: '',
      description: '',
      pizza: false,
      beer: false,
      hamburger: false,
      sushi: false,
      empanadas: false,
      green: false,
      vegan: false,
      maxSalesPerDay: 0,
      averageDeliveryTime: 0,
      initialPrice: 0,
      fstQuantity: 0,
      sndQuantity: 0,
      fstPrice: 0,
      sndPrice: 0,
      deliveryValue: 0,
    };
  }

  getCategories() {
    const cs = [];
    this.checkPizza(cs);
    this.checkBeer(cs);
    this.checkVegan(cs);
    this.checkGreen(cs);
    this.checkSushi(cs);
    this.checkHamburger(cs);
    this.checkEmpanadas(cs);
    return (cs);
  }

  changeName(e) {
    this.setState({ name: e.target.value });
  }

  nameField(t) {
    return (
      <Form.Group controlId="formBasicEmail">
        <Form.Label className="form-name-menu-alert">{t('Menu name')}</Form.Label>
        <Form.Control type="text" placeholder={t('Submit the name here')} onChange={(e) => this.changeName(e)} />
        <Form.Text className="form-name-menu-alert">
          {t('This name will appear in your posts')}
        </Form.Text>
      </Form.Group>
    );
  }

  changeDescription(e) {
    this.setState({ description: e.target.value });
  }

  descriptionField(t) {
    return (
      <Form.Group controlId="formBasicPassword">
        <Form.Label className="form-name-menu-alert">{t('Description')}</Form.Label>
        <Form.Control type="text" placeholder={t('Submit the discription here')} onChange={(e) => this.changeDescription(e)} />
        <Form.Text>{t('Debe contener entre 20 y 40 caracteres')}</Form.Text>
      </Form.Group>
    );
  }

  handlePizza(e) {
    this.setState({ pizza: e.target.checked });
  }

  handleBeer(e) {
    this.setState({ beer: e.target.checked });
  }

  handleHamburger(e) {
    this.setState({ hamburger: e.target.checked });
  }

  handleGreen(e) {
    this.setState({ green: e.target.checked });
  }

  handleSushi(e) {
    this.setState({ sushi: e.target.checked });
  }

  handleEmpanadas(e) {
    this.setState({ empanadas: e.target.checked });
  }

  handleVegan(e) {
    this.setState({ vegan: e.target.checked });
  }

  categoryField(t) {
    return (

      <Form.Group controlId="formBasicCheckbox">
        <Form.Label className="form-name-menu-alert">{t('Choose yout product category')}</Form.Label>
        <Row className="line_of_checkboxs">
          <Form.Check className="form-name-menu-alert" inline label="Pizza" type="checkbox" id="inline-checkbox-1" onClick={(e) => this.handlePizza(e)} />
          <Form.Check className="form-name-menu-alert" inline label={t('Burger')} type="checkbox" id="inline-checkbox-2" onClick={(e) => this.handleHamburger(e)} />
          <Form.Check className="form-name-menu-alert" inline label={t('Green')} type="checkbox" id="inline-checkbox-3" onClick={(e) => this.handleGreen(e)} />
          <Form.Check className="form-name-menu-alert" inline label={t('Vegan')} type="checkbox" id="inline-checkbox-4" onClick={(e) => this.handleVegan(e)} />
          <Form.Check className="form-name-menu-alert" inline label={t('Beer')} type="checkbox" id="inline-checkbox-5" onClick={(e) => this.handleBeer(e)} />
          <Form.Check className="form-name-menu-alert" inline label="Empanadas" type="checkbox" id="inline-checkbox-6" onClick={(e) => this.handleEmpanadas(e)} />
          <Form.Check className="form-name-menu-alert" inline label="Sushi" type="checkbox" id="inline-checkbox-7" onClick={(e) => this.handleSushi(e)} />
        </Row>
      </Form.Group>
    );
  }

  changeSalesPerDay(e) {
    this.setState({ maxSalesPerDay: parseInt(e.target.value, 10) });
  }

  changeAverageTime(e) {
    this.setState({ averageDeliveryTime: parseInt(e.target.value, 10) });
  }

  changeDeliveryValue(e) {
    this.setState({ deliveryValue: parseFloat(e.target.value, 10) });
  }

  timeAndSalesPerDay(t) {
    return (
      <div>
        <Form.Group>
          <Row>
            <Col>
              <Form.Label className="form-name-menu-alert">{t('Max sales per day')}</Form.Label>
              <Form.Control type="number" min="1" placeholder={t('insert max sales per day')} onChange={(e) => this.changeSalesPerDay(e)} />
            </Col>
            <Col>
              <Form.Label className="form-name-menu-alert">{t('Average time')}</Form.Label>
              <Form.Control type="number" min="1" placeholder={t('Insert average time')} onChange={(e) => this.changeAverageTime(e)} />
            </Col>
          </Row>
        </Form.Group>
        <Form.Group>
          <Row>
            <Col>
              <Form.Label className="form-name-menu-alert">{t('Delivery value')}</Form.Label>
              <Form.Control type="number" min="0" placeholder={t('insert delivery value')} onChange={(e) => this.changeDeliveryValue(e)} />
            </Col>
          </Row>
        </Form.Group>
      </div>
    );
  }

  changeDateFrom(newDate) {
    this.setState({ dateFrom: newDate });
  }

  changeDateThru(newDate) {
    this.setState({ dateThru: newDate });
  }


  validFromAndThru(t) {
    return (
      <Form.Group>
        <Row>
          <Col>
            <Form.Label className="form-name-menu-alert">{t('Insert date valid from')}</Form.Label>
            <Calendar
              onChange={(date) => this.changeDateFrom(date)}
              value={this.state.dateFrom}
              minDate={new Date()}
            />
          </Col>
          <Col>
            <Form.Label className="form-name-menu-alert">{t('Insert date valid thru')}</Form.Label>
            <Calendar
              onChange={(date) => this.changeDateThru(date)}
              value={this.state.dateThru}
              minDate={this.state.dateFrom}
            />
          </Col>
        </Row>
      </Form.Group>
    );
  }

  pad(n) {
    if (n.toString().length === 1) {
      return (`0${n}`);
    }
    return n;
  }

  parseDate(date) {
    return (
      `${date.getFullYear()}-${this.pad(date.getMonth() + 1)}-${this.pad(date.getDate())}`
    );
  }

  checkPizza(cs) {
    if (this.state.pizza) {
      cs.push('PIZZA');
    }
  }

  checkBeer(cs) {
    if (this.state.beer) {
      cs.push('BEER');
    }
  }

  checkHamburger(cs) {
    if (this.state.hamburger) {
      cs.push('HAMBURGER');
    }
  }

  checkEmpanadas(cs) {
    if (this.state.empanadas) {
      cs.push('EMPANADAS');
    }
  }

  checkGreen(cs) {
    if (this.state.green) {
      cs.push('GREEN');
    }
  }

  checkSushi(cs) {
    if (this.state.sushi) {
      cs.push('SUSHI');
    }
  }

  checkVegan(cs) {
    if (this.state.vegan) {
      cs.push('VEGAN');
    }
  }


  changeInitialPrice(e) {
    return (
      this.setState({ initialPrice: e.target.value })
    );
  }

  changeFstQuantity(e) {
    return (
      this.setState({ fstQuantity: e.target.value })
    );
  }

  changeSndQuantity(e) {
    return (
      this.setState({ sndQuantity: e.target.value })
    );
  }

  changeFstPrice(e) {
    return (
      this.setState({ fstPrice: e.target.value })
    );
  }

  changeSndPrice(e) {
    return (
      this.setState({ sndPrice: e.target.value })
    );
  }

  price(t) {
    return (
      <Form.Group>
        <Row>
          <Col>
            <Form.Label className="initialPrice form-name-menu-alert">{t('Initial price')}</Form.Label>
            <Form.Control type="number" min="0" placeholder={t('insert here the initial price')} onChange={(e) => this.changeInitialPrice(e)} />
          </Col>
        </Row>
        <Row>
          <Col>
            <Form.Label className="initialPrice form-name-menu-alert">{t('If you sell more than')}</Form.Label>
            <Form.Control type="number" min="0" placeholder={t('insert here a quantity of menus')} onChange={(e) => this.changeFstQuantity(e)} />
          </Col>
          <Col>
            <Form.Label className="initialPrice form-name-menu-alert">{t('the first wholesaler price will be')}</Form.Label>
            <Form.Control type="number" min="0" placeholder={t('insert here the wholesaler price')} onChange={(e) => this.changeFstPrice(e)} />
            {this.renderFirstPriceFeedback(t)}
          </Col>
          <Col>
            <Form.Label className="initialPrice form-name-menu-alert">{t('If you sell more than')}</Form.Label>
            <Form.Control type="number" min="0" placeholder={t('insert here a quantity of menus')} onChange={(e) => this.changeSndQuantity(e)} />
          </Col>
          <Col>
            <Form.Label className="initialPrice form-name-menu-alert">{t('the second wholesaler price will be')}</Form.Label>
            <Form.Control type="number" min="0" placeholder={t('insert here the wholesaler price')} onChange={(e) => this.changeSndPrice(e)} />
            {this.renderSecondPriceFeedback(t)}
          </Col>
        </Row>
      </Form.Group>
    );
  }

  menuCreated(t) {
    Swal.fire({
      title: t('Done'),
      icon: 'success',
    });
    this.props.history.push({
      pathname: '/provider',
      state: {
        googleId: this.props.location.state.googleId,
        tokenAccess: this.props.location.state.tokenAccess,
        user: this.props.location.state.user,
      },
    });
  }

  errorInForm(t) {
    Swal.fire({
      title: t('Theres an error in the form'),
      icon: 'error',
    });
  }

  postInfo(t) {
    const body = {
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
      name: this.state.name,
      description: this.state.description,
      categories: this.getCategories(),
      deliveryValue: this.state.deliveryValue,
      effectiveDate: {
        validFrom: this.parseDate(this.state.dateFrom),
        goodThru: this.parseDate(this.state.dateThru),
      },
      averageDeliveryTimeInMinutes: this.state.averageDeliveryTime,
      maxSalesPerDay: this.state.maxSalesPerDay,
      menuPriceCalculator: {
        price: this.state.initialPrice,
        firstMinAmount: this.state.fstQuantity,
        firstMinAmountPrice: this.state.fstPrice,
        secondMinAmount: this.state.sndQuantity,
        secondMinAmountPrice: this.state.sndPrice,
      },
    };
    API.post('/menu', body)
      .then(() => this.menuCreated(t))
      .catch((error) => this.errorInForm(t));
  }

  validCategory() {
    return (
      this.state.pizza || this.state.beer || this.state.hamburger || this.state.sushi
      || this.state.empanadas || this.state.green || this.state.vegan
    );
  }

  validForm() {
    return (
      this.state.name.trim().length > 0
      && this.state.description.trim().length >= 20
      && this.state.description.trim().length <= 40
      && parseFloat(this.state.deliveryValue) >= 0
      && this.validCategory()
      && parseInt(this.state.maxSalesPerDay, 10) > 0
      && parseInt(this.state.averageDeliveryTime, 10) > 0
      && parseFloat(this.state.initialPrice, 10) > 0
      && parseFloat(this.state.initialPrice, 10) > parseFloat(this.state.fstPrice, 10)
      && parseFloat(this.state.fstPrice, 10) > parseFloat(this.state.sndPrice, 10)
      && parseFloat(this.state.fstQuantity, 10) < parseFloat(this.state.sndQuantity, 10)
    );
  }

  checkFieldsAndPost(t) {
    if (this.validForm()) {
      this.postInfo(t);
    } else {
      Swal.fire({
        title: t('Complete todos los campos correctamente'),
        icon: 'error',
      });
    }
  }

  renderSecondPriceFeedback(t) {
    if (this.state.sndPrice !== 0
      && parseFloat(this.state.fstPrice, 10) <= parseFloat(this.state.sndPrice, 10)) {
      return (
        <Form.Text className="form-feedback">{t('Price must be lower than the last')}</Form.Text>
      );
    }
    return <div />;
  }

  renderFirstPriceFeedback(t) {
    if (this.state.fstPrice !== 0
      && parseFloat(this.state.initialPrice, 10) <= parseFloat(this.state.fstPrice, 10)) {
      return (
        <Form.Text className="form-feedback">{t('Price must be lower than price per unit')}</Form.Text>
      );
    }
    return <div />;
  }

  render() {
    const { t } = this.props;
    return (
      <Container>
        <h1 className="register_menu_title"> {t('register your menu')}</h1>
        <div className="form_register_menu">
          <Form className="register_menu_form">
            {this.nameField(t)}
            {this.descriptionField(t)}
            {this.categoryField(t)}
            {this.timeAndSalesPerDay(t)}
            {this.validFromAndThru(t)}
            {this.price(t)}
          </Form>
          <Row>
            <Col className="button_register_menu">
              <Button className="btn-block" variant="primary" size="lg" onClick={() => this.checkFieldsAndPost(t)}>
                {t('register')}
              </Button>
            </Col>
          </Row>
        </div>
      </Container>
    );
  }
}

export default withTranslation()(RegisterMenuForm);
