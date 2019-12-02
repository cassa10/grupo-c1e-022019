import React from 'react';
import '../dist/css/ProviderProfile.css';
import '../dist/css/SearchResult.css';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import StarRatingComponent from 'react-star-rating-component';
import { withTranslation } from 'react-i18next';
import Badge from 'react-bootstrap/Badge';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import API from '../service/api';

class ProviderProfile extends React.Component {
  // eslint-disable-next-line no-useless-constructor
  constructor(props) {
    super(props);
    this.state = {
      provider: {
        id: 0,
        googleId: '',
        name: '',
        logo: '',
        city: '',
        menus: [],
      },
      showModalSee: false,
    };
  }

  componentDidMount() {
    const body = {
      googleId: 'FAKEID1',
      tokenAccess: 'FAKEACCESSTOKEN1',
    };
    API.get('/provider', body)
      .then((response) => this.setState({ provider: response }));
  }

  setShowSee(b) {
    this.setState({ showModalSee: b });
  }

  pushToCreateMenu() {
    this.props.history.push('/create_menu');
  }

  pushToEditMenu(menux) {
    this.props.history.push({
      pathname: 'edit_menu',
      state: {
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

  seeButton(menu, t) {
    const handleClose = () => this.setShowSee(false);
    const handleShow = () => this.setShowSee(true);
    return (
      <div className="">
        <Button className="buy-button" variant="info" onClick={handleShow}>
          {t('Ver')}
        </Button>
        <Modal show={this.state.showModalSee} onHide={handleClose}>
          <Modal.Header closeButton>
            <h1>{menu.name}</h1>
          </Modal.Header>
          <Modal.Title>{menu.description}</Modal.Title>
          <Modal.Body>
            <Card.Img className="card_img" variant="left" src="data:image/png;base64,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" /><br />
            <h5>Delivery : {menu.deliveryValue} pesos<br />
              {t('Comprando mas de')}: {menu.firstMinAmount} unidades<br />
              {t('the price will be')} {menu.firstMinAmountPrice} pesos<br />
              {t('Comprando mas de')}: {menu.menuPriceCalculator.secondMinAmount} unidades<br />
              {t('the price will be')} {menu.menuPriceCalculator.secondMinPrice} pesos<br />
              {t('Distancia de delivery')} : {menu.deliveryMaxDistanceInKM} kms<br />
              {t('Estado del menu')} {menu.menuStateName}<br />
            </h5>
          </Modal.Body>

        </Modal>
      </div>
    );
  }


  editButton(menu, t) {
    return (
      <Button className="buy-button" variant="warning" onClick={() => this.pushToEditMenu(menu)}>
        {t('Editar')}
      </Button>
    );
  }

  renderMenues(t) {
    return (
      this.state.provider.menus.map((menu) => this.renderMenu(menu, t))
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
                <Card.Img className="card_img" variant="left" src="data:image/png;base64,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" />
              </Col>
              <Col>
                {this.showStars(menu)}
                {this.showBadge(menu)}
                <h5>
                  {t('Description')}: {menu.description}<br />
                  {t('Valido hasta')} {menu.effectiveDateGoodThru}<br />
                </h5>
                <h4>
                  {`a solo $${menu.price}`}
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
    return (
      <div>
        <h1 className="provider-title">{this.state.provider.name}</h1>
        <hr />
        <h3 className="provider-title">{t('your menus')}</h3>
        <Button className="add-button" variant="success" onClick={() => this.pushToCreateMenu()}>{t('add menu')}</Button>
        <div>
          {this.renderMenues(t)}
        </div>
      </div>
    );
  }
}

export default withTranslation()(ProviderProfile);
