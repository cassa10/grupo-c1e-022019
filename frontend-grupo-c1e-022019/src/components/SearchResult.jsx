import React from 'react';
import '../dist/css/SearchResult.css';
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Modal from 'react-bootstrap/Modal';
import Row from 'react-bootstrap/Row';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { withTranslation } from 'react-i18next';
import Swal from 'sweetalert2';
import API from '../service/api';


class SearchResult extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      priceOrder: 'min',
      rankOrder: 'max',
      fromPage: 0,
      sizePage: 4,
      results: [],
      showModal: false,
      quantityOfMenus: 0,
      delivery: false,
      pictures: [
        'https://www.seriouseats.com/recipes/images/2015/07/20150728-homemade-whopper-food-lab-35-1500x1125.jpg',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSZdKVn9EaecGuHITxS6GZO8d7eGSLO66qHcA-sG9fI-dc3-PWZ',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTjttrBPwqIuSqoO0Ms-VnJOVW_wDA2DKau1xUMDaUhMRCWiKJY',
        'https://placeralplato.com/files/2016/01/Pizza-con-pepperoni.jpg',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQYb3pgd8BIbJsxFNFCUEc5KBxkvFytZhyEZeKRk5ElefPWkCeW',
        'https://argentina.gastronomia.com/media/cache/noticia_grande/uploads/noticias/lengua.S3hpbFR2NG5mOEY0SEZ5RS8vMTQ5NDQ2NjkzMy8.jpg',
        'https://sifu.unileversolutions.com/image/es-AR/recipe-topvisual/2/1260-709/noquis-de-papas-50339442.jpg',
      ],
    };
  }

  componentDidMount() {
    if (this.props.location.state !== undefined && this.props.location.state.searchFromHome) {
      this.requestAPISearch();
    } else {
      this.props.history.push({
        pathname: '/home',
        state: {
          googleId: this.props.location.state.googleId,
          accessToken: this.props.location.state.accessToken,
          client: this.props.location.state.client,
        },
      });
    }
  }

  setShow(b) {
    this.setState({ showModal: b });
  }

  getBodyFromDelivery(menu) {
    if (this.state.delivery) {
      return (
        {
          tokenAccess: 'FAKEACCESSTOKEN1',
          googleId: 'FAKEID1',
          idClient: 52,
          idMenu: menu.id,
          menusAmount: this.state.quantityOfMenus,
          deliverType: 'delivery',
          deliverDate: new Date().toJSON(),
          destination: {
            coord: {
              latitude: -34.707191,
              longitude: -58.276366,
            },
            location: 'Dean Funes 630, Bernal, Buenos Aires, Argentina',
          },
        }
      );
    }

    return (
      {
        tokenAccess: 'FAKEACCESSTOKEN1',
        googleId: 'FAKEID1',
        idClient: 52,
        idMenu: menu.id,
        menusAmount: this.state.quantityOfMenus,
        deliverType: 'pickup',
        deliverDate: new Date().toJSON(),
      }
    );
  }

  requestAPISearch() {
    const locState = this.props.location.state;
    const body = {
      googleId: this.props.location.state.googleId,
      accessToken: this.props.location.state.accessToken,
      name: this.props.location.state.searchInputName,
      city: this.props.location.state.searchInputCity,
      category: this.props.location.state.searchInputCategory,
      priceOrder: this.state.priceOrder,
      rankOrder: this.state.rankOrder,
      fromPage: this.state.fromPage,
      sizePage: this.state.sizePage,
    };
    API.get(`/menu/search/${this.detectPathSearch(locState)}/`, body)
      .then((response) => this.setState({ results: response }))
      .catch((error) => console.log(error));
  }

  detectPathSearch(locState) {
    if (locState.searchInputName !== '' && locState.searchInputCategory !== ''
          && locState.searchInputCity !== '') {
      return ('name_category_city');
    }

    if (locState.searchInputName !== '' && locState.searchInputCategory !== '') {
      return ('name_category');
    }

    if (locState.searchInputName !== '' && locState.searchInputCity !== '') {
      return ('name_city');
    }

    if (locState.searchInputName !== '') {
      return ('name');
    }

    if (locState.searchInputCategory !== '' && locState.searchInputCity !== '') {
      return ('category_city');
    }

    if (locState.searchInputCategory !== '') {
      return ('category');
    }

    return ('city');
  }

  mapResults(t) {
    if (this.state.results && this.state.results.length <= 0) {
      return (
        <Container className="card container">
          <Row>
            <Col className="text-center">
              <div className="no_results">
                {t('No results found')}
              </div>
            </Col>
          </Row>
        </Container>
      );
    }
    return (
      <Container className="card_container">
        <Row className="card_row">
          {this.state.results.map(
            (menu) => (
              this.renderMenu(menu, t)
            ),
          )}
        </Row>
      </Container>
    );
  }

  handleInsuficientCredit(menu, t) {
    Swal.fire({
      title: t('Insufficient credit'),
      imageUrl: 'https://cdn.memegenerator.es/imagenes/memes/full/6/96/6965905.jpg',
      imageHeight: 250,
      imageWidth: 250,
      imageAlt: 'Maldita pobreza',
      icon: 'error',
    });
  }

  buyDone() {
    this.props.history.push('/');
    window.location.reload();
  }


  makeApiPost(menu, t) {
    const body = this.getBodyFromDelivery(menu);
    console.log(body)
    // API.post('/order', body)
    //   .then(() => this.buyDone())
    //   .catch(() => this.handleInsuficientCredit(menu, t));
  }

  buyConfirmed(isConfirmed, menu, t) {
    if (isConfirmed) {
      Swal.fire(
        t('Done!'),
        `${t('enjoy your')} ${menu.name}`,
        'success',
      );
      this.makeApiPost(menu, t);
    }
  }

  handleBuyFromModal(menu, t) {
    Swal.fire({
      title: t('¿Estas seguro?'),
      text: `${t("We'll debit ")} ${menu.price}  pesos`,
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: 'Green',
      cancelButtonColor: 'Red',
      confirmButtonText: t('buy'),
      cancelButtonText: t('cancel'),
    })
      .then((result) => this.buyConfirmed(result.value, menu, t))
      .catch((error) => console.log(error.response));
  }

  handleQuantityOfMenus(e) {
    this.setState({ quantityOfMenus: e.target.value });
  }

  handleDelivery(e) {
    this.setState({ delivery: e.target.checked });
  }

  buyButton(menu, t) {
    const handleClose = () => this.setShow(false);
    const handleShow = () => this.setShow(true);
    return (
      <div className="">
        <Button variant="primary" onClick={handleShow}>
          {t('buy')}
        </Button>

        <Modal show={this.state.showModal} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{t('buy')}</Modal.Title>
          </Modal.Header>
          <Modal.Body className="buy-body">{t('How many?')}</Modal.Body>
          <input type="number" onChange={(e) => this.handleQuantityOfMenus(e)} />
          <p className="buy-body">Do you want delivery?</p>
          <Form.Check label="Delivery" type="checkbox" id="inline-checkbox-1" onClick={(e) => this.handleDelivery(e)} />
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              {t('cancel')}
            </Button>
            <Button variant="primary" onClick={() => this.handleBuyFromModal(t, menu)}>
              {t('buy')}
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    );
  }

  renderMenu(menu, t) {
    const randomNumber = Math.floor(Math.random() * (this.state.pictures.length));
    return (
      <div className="menu_card" key={menu.id}>
        <Col>
          <Card>
            <Card.Img className="card_img" variant="top" src={this.state.pictures[randomNumber]} />
            <Card.Body>
              <Card.Title>{menu.name}</Card.Title>
              <Card.Text>
                {menu.description}
              </Card.Text>
              <Card.Text>
                {`${menu.price} pesos`}
              </Card.Text>
              {this.buyButton(menu, t)}
            </Card.Body>
          </Card>
        </Col>
      </div>
    );
  }

  render() {
    const { t } = this.props;
    return (
      <div className="search_results">
        <h1 className="title">
          {`${t('Search results')}`}
        </h1>
        {this.mapResults(t)}
      </div>
    );
  }
}

export default withTranslation()(SearchResult);
