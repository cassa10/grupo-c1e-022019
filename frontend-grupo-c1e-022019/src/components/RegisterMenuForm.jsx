import React from 'react';
import { withTranslation } from 'react-i18next';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import '../dist/css/CreateMenu.css';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Calendar from 'react-calendar';
import API from '../service/api';

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
        <Form.Label>{t('Menu name')}</Form.Label>
        <Form.Control type="text" placeholder={t('Submit the name here')} onChange={(e) => this.changeName(e)} />
        <Form.Text className="text-muted">
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
        <Form.Label>{t('Description')}</Form.Label>
        <Form.Control type="text" placeholder={t('Submit the discription here')} onChange={(e) => this.changeDescription(e)} />
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
        <Form.Label>{t('Choose yout product category')}</Form.Label>
        <Row className="line_of_checkboxs">
          <Form.Check inline label="Pizza" type="checkbox" id="inline-checkbox-1" onClick={(e) => this.handlePizza(e)} />
          <Form.Check inline label={t('Burger')} type="checkbox" id="inline-checkbox-1" onClick={(e) => this.handleHamburger(e)} />
          <Form.Check inline label={t('Green')} type="checkbox" id="inline-checkbox-1" onClick={(e) => this.handleGreen(e)} />
          <Form.Check inline label={t('Vegan')} type="checkbox" id="inline-checkbox-1" onClick={(e) => this.handleVegan(e)} />
          <Form.Check inline label={t('Beer')} type="checkbox" id="inline-checkbox-1" onClick={(e) => this.handleBeer(e)} />
          <Form.Check inline label="Empanadas" type="checkbox" id="inline-checkbox-1" onClick={(e) => this.handleEmpanadas(e)} />
          <Form.Check inline label="Sushi" type="checkbox" id="inline-checkbox-1" onClick={(e) => this.handleSushi(e)} />
        </Row>
      </Form.Group>
    );
  }

  changeSalesPerDay(e) {
    this.setState({ maxSalesPerDay: parseInt(e.target.value) });
  }

  changeAverageTime(e) {
    this.setState({ averageDeliveryTime: parseInt(e.target.value) });
  }

  timeAndSalesPerDay(t) {
    return (
      <Form.Group>
        <Row>
          <Col>
            <Form.Label>{t('Max sales per day')}</Form.Label>
            <Form.Control placeholder={t('insert max sales per day')} onChange={(e) => this.changeSalesPerDay(e)} />
          </Col>
          <Col>
            <Form.Label>{t('Average time')}</Form.Label>
            <Form.Control placeholder={t('Insert average time')} onChange={(e) => this.changeAverageTime(e)} />
          </Col>
        </Row>
      </Form.Group>
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
            <Form.Label>{t('Insert date valid from')}</Form.Label>
            <Calendar
              onChange={(date) => this.changeDateFrom(date)}
              value={this.state.dateFrom}
              minDate={new Date()}
            />
          </Col>
          <Col>
            <Form.Label>{t('Insert date valid thru')}</Form.Label>
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

  parseDate(date) {
    return (
      `${date.getFullYear()}-${date.getMonth()}-${date.getDate()}`
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
            <Form.Label className="initialPrice">{t('Initial price')}</Form.Label>
            <Form.Control placeholder={t('insert here the initial price')} onChange={(e) => this.changeInitialPrice(e)} />
          </Col>
        </Row>
        <Row>
          <Col>
            <Form.Label className="initialPrice">{t('If you sell more than')}</Form.Label>
            <Form.Control placeholder={t('insert here a quantity of menus')} onChange={(e) => this.changeFstQuantity(e)} />
          </Col>
          <Col>
            <Form.Label className="initialPrice">{t('the price will be')}</Form.Label>
            <Form.Control placeholder={t('insert here the wholesaler price')} onChange={(e) => this.changeFstPrice(e)} />
          </Col>
          <Col>
            <Form.Label className="initialPrice">{t('If you sell more than')}</Form.Label>
            <Form.Control placeholder={t('insert here a quantity of menus')} onChange={(e) => this.changeSndQuantity(e)} />
          </Col>
          <Col>
            <Form.Label className="initialPrice">{t('the price will be')}</Form.Label>
            <Form.Control placeholder={t('insert here the wholesaler price')} onChange={(e) => this.changeSndPrice(e)} />
          </Col>
        </Row>
      </Form.Group>
    );
  }

  postInfo() {
    const body = {
      idProvider: 1,
      name: this.state.name,
      description: this.state.description,
      categories: this.getCategories(),
      deliveryValue: this.state.averageDeliveryTime,
      effectiveDate: {
        validFrom: this.parseDate(this.state.dateFrom),
        validThru: this.parseDate(this.state.dateThru),
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
    API.post('/menu', body);
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <Form className="register_menu_form">
          {this.nameField(t)}

          {this.descriptionField(t)}

          {this.categoryField(t)}

          {this.timeAndSalesPerDay(t)}

          {this.validFromAndThru(t)}

          {this.price(t)}
        </Form>
        <Button className="register_button" variant="primary" size="lg" block>
          {t('register')}
        </Button>
      </div>
    );
  }
}

export default withTranslation()(RegisterMenuForm);
