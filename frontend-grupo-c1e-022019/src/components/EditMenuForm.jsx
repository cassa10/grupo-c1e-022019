import React from 'react';
import { withTranslation } from 'react-i18next';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import '../dist/css/CreateMenu.css';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Calendar from 'react-calendar';
import Swal from 'sweetalert2';
import API from '../service/api';

class EditMenuForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      dateFrom: new Date(props.menu.effectiveDate.validFrom),
      dateThru: new Date(props.menu.effectiveDate.goodThru),
      name: props.menu.name,
      description: props.menu.description,
      pizza: this.props.menu.categories.includes('PIZZA'),
      beer: this.props.menu.categories.includes('BEER'),
      hamburger: this.props.menu.categories.includes('BURGER'),
      sushi: this.props.menu.categories.includes('SUSHI'),
      empanadas: this.props.menu.categories.includes('EMPANADAS'),
      green: this.props.menu.categories.includes('GREEN'),
      vegan: this.props.menu.categories.includes('VEGAN'),
      maxSalesPerDay: props.menu.maxSalesPerDay,
      averageDeliveryTime: props.menu.averageDeliveryTimeInMinutes,
      initialPrice: props.menu.price,
      fstQuantity: props.menu.firstMinAmount,
      sndQuantity: props.menu.secondMinAmount,
      fstPrice: props.menu.firstMinAmountPrice,
      sndPrice: props.menu.secondMinAmountPrice,

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
        <Form.Control type="text" placeholder={t('Submit the name here')} defaultValue={this.props.menu.name} onChange={(e) => this.changeName(e)} />
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
        <Form.Label>{t('Description')}</Form.Label>
        <Form.Control type="text" placeholder={t('Submit the discription here')} defaultValue={this.props.menu.description} onChange={(e) => this.changeDescription(e)} />
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

  contains(nameOfCategory) {
    return this.props.menu.categories.includes(nameOfCategory);
  }

  categoryField(t) {
    return (

      <Form.Group controlId="formBasicCheckbox">
        <Form.Label>{t('Choose yout product category')}</Form.Label>
        <Row className="line_of_checkboxs">
          <Form.Check inline label="Pizza" type="checkbox" id="inline-checkbox-1" defaultChecked={this.contains('PIZZA')} onClick={(e) => this.handlePizza(e)} />
          <Form.Check inline label={t('Burger')} type="checkbox" id="inline-checkbox-1" defaultChecked={this.contains('BURGER')} onClick={(e) => this.handleHamburger(e)} />
          <Form.Check inline label={t('Green')} type="checkbox" id="inline-checkbox-1" defaultChecked={this.contains('GREEN')} onClick={(e) => this.handleGreen(e)} />
          <Form.Check inline label={t('Vegan')} type="checkbox" id="inline-checkbox-1" defaultChecked={this.contains('VEGAN')} onClick={(e) => this.handleVegan(e)} />
          <Form.Check inline label={t('Beer')} type="checkbox" id="inline-checkbox-1" defaultChecked={this.contains('BEER')} onClick={(e) => this.handleBeer(e)} />
          <Form.Check inline label="Empanadas" type="checkbox" id="inline-checkbox-1" defaultChecked={this.contains('EMPANADAS')} onClick={(e) => this.handleEmpanadas(e)} />
          <Form.Check inline label="Sushi" type="checkbox" id="inline-checkbox-1" defaultChecked={this.contains('SUSHI')} onClick={(e) => this.handleSushi(e)} />
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

  timeAndSalesPerDay(t) {
    return (
      <Form.Group>
        <Row>
          <Col>
            <Form.Label>{t('Max sales per day')}</Form.Label>
            <Form.Control placeholder={t('insert max sales per day')} defaultValue={this.props.menu.maxSalesPerDay} onChange={(e) => this.changeSalesPerDay(e)} />
          </Col>
          <Col>
            <Form.Label>{t('Average time')}</Form.Label>
            <Form.Control placeholder={t('Insert average time')} defaultValue={this.props.menu.averageDeliveryTimeInMinutes} onChange={(e) => this.changeAverageTime(e)} />
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

  pad(n) {
    if (n.toString().length === 1) {
      return (`0${n}`);
    }
    return n;
  }

  parseDate(date) {
    return (
      `${date.getFullYear()}-${this.pad(date.getMonth() + 1)}-${this.pad(date.getDate() + 1)}`
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
            <Form.Control type="number" placeholder={t('insert here the initial price')} defaultValue={this.props.menu.price} onChange={(e) => this.changeInitialPrice(e)} />
          </Col>
        </Row>
        <Row>
          <Col>
            <Form.Label className="initialPrice">{t('If you sell more than')}</Form.Label>
            <Form.Control type="number" placeholder={t('insert here a quantity of menus')} defaultValue={this.props.menu.firstMinAmount} onChange={(e) => this.changeFstQuantity(e)} />
          </Col>
          <Col>
            <Form.Label className="initialPrice">{t('the first wholesaler price will be')}</Form.Label>
            <Form.Control type="number" placeholder={t('insert here the wholesaler price')} defaultValue={this.props.menu.firstMinAmountPrice} onChange={(e) => this.changeFstPrice(e)} />
            {this.renderFirstPriceFeedback(t)}
          </Col>
          <Col>
            <Form.Label className="initialPrice">{t('If you sell more than')}</Form.Label>
            <Form.Control type="number" placeholder={t('insert here a quantity of menus')} defaultValue={this.props.menu.secondMinAmount} onChange={(e) => this.changeSndQuantity(e)} />
          </Col>
          <Col>
            <Form.Label className="initialPrice">{t('the second wholesaler price will be')}</Form.Label>
            <Form.Control type="number" placeholder={t('insert here the wholesaler price')} defaultValue={this.props.menu.secondMinAmountPrice} onChange={(e) => this.changeSndPrice(e)} />
            {this.renderSecondPriceFeedback(t)}
          </Col>
        </Row>
      </Form.Group>
    );
  }

  menuUpdated(t) {
    Swal.fire({
      title: t('Updated!'),
      icon: 'success',
    });
    this.props.location.history.push({
      pathname: '/provider',
      state: {
        googleId: this.props.location.state.googleId,
        tokenAccess: this.props.location.state.tokenAccess,
        user: this.props.location.state.user,
      },
    });
    window.location.reload();
  }

  postInfo(t) {
    const body = {
      googleId: this.props.fatherProps.location.state.googleId,
      tokenAccess: this.props.fatherProps.location.state.tokenAccess,
      id: this.props.menu.id,
      name: this.state.name,
      description: this.state.description,
      categories: this.getCategories(),
      deliveryValue: this.state.averageDeliveryTime,
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
    API.put('/menu', body)
      .then(() => this.menuUpdated(t))
      .catch((error) => console.log(error));
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
        icon: 'question',
        title: 'Oops...',
        text: 'Complete todos los campos',
      });
    }
  }

  renderSecondPriceFeedback(t) {
    if (parseFloat(this.state.fstPrice, 10) <= parseFloat(this.state.sndPrice, 10)) {
      return (
        <Form.Text className="form-feedback">{t('Price must be lower than the last')}</Form.Text>
      );
    }
    return <div />;
  }

  renderFirstPriceFeedback(t) {
    if (parseFloat(this.state.initialPrice, 10) <= parseFloat(this.state.fstPrice, 10)) {
      return (
        <Form.Text className="form-feedback">{t('Price must be lower than price per unit')}</Form.Text>
      );
    }
    return <div />;
  }

  render() {
    const { t } = this.props;
    return (
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
              {t('Done')}
            </Button>
          </Col>
        </Row>
      </div>
    );
  }
}

export default withTranslation()(EditMenuForm);
