import React from 'react';
import '../dist/css/SearchResult.css';
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Modal from 'react-bootstrap/Modal';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Dropdown from 'react-bootstrap/Dropdown';
import Row from 'react-bootstrap/Row';
import Form from 'react-bootstrap/Form';
import Badge from 'react-bootstrap/Badge';
import Button from 'react-bootstrap/Button';
import { withTranslation } from 'react-i18next';
import Swal from 'sweetalert2';
import StarRatingComponent from 'react-star-rating-component';
import API from '../service/api';
import shoppingCartIcon from '../dist/icons/shopping-cart-buy-icon.png';
import formatPrice from './formatter/formatCredit';
import formatNumber from './formatter/formatNumber';
import formatDate from './formatter/formatDate';
import providerInfoIcon from '../dist/icons/provider-info-icon.png';
import menuInfoIcon from '../dist/icons/info-icon.png'; 


class SearchResult extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      googleId: '',
      tokenAccess: '',
      client: {},
      priceOrder: '',
      rankOrder: '',
      priority: '', // price | rank
      fromPage: 0,
      sizePage: 4,
      results: [],
      showModal: false,
      quantityOfMenus: 0,
      delivery: false,
      showModalSee: false,
      pictures: [
        'https://www.seriouseats.com/recipes/images/2015/07/20150728-homemade-whopper-food-lab-35-1500x1125.jpg',
      ],
      currentPage: 0,
      totalPages: 0,
      totalElements: 0,
    };
  }

  componentDidMount() {
    const body = {
      locState: this.props.location.state,
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
      client: this.props.location.state.client,
      name: this.props.location.state.searchInputName,
      city: this.props.location.state.searchInputCity,
      category: this.props.location.state.searchInputCategory,
      priceOrder: this.props.location.state.priceOrder,
      rankOrder: this.props.location.state.rankOrder,
      fromPage: this.props.location.state.fromPage,
      sizePage: 4,
      priority: this.props.location.state.priority,
    };
    this.setState(body);
    this.requestAPISearch(body);
  }

  setShow(b) {
    this.setState({ showModal: b });
  }

  setShowSee(b) {
    this.setState({ showModalSee: b });
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
              latitude: '-34.707191',
              longitude: '-58.276366',
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

  getDecreasedFromPage() {
    return (
      this.state.fromPage - 1
    );
  }

  requestAPISearch(body) {
    window.scrollTo(0, 0);
    API.get(`/menu/search/${this.detectPathSearch(body.locState)}/`, body)
      .then((response) => this.handleAPISearch(response))
      .catch((error) => console.log(error));
  }

  handleAPISearch(response) {
    // pageNumber starts at 0.
    // totalPages starts at 1.
    this.setState({ currentPage: response.pageable.pageNumber });
    this.setState({ totalPages: response.totalPages });
    this.setState({ results: response.content });
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
    if (this.state.results.length > 0) {
      return (
        <Container className="card_container">
          {this.state.results.map(
            (menu) => (
              this.renderMenu(menu, t)
            ),
          )}
        </Container>
      );
    }
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

  handleInsuficientCredit(menu, t) {
    console.log(this.state);
    Swal.fire({
      title: t('Insufficient credit'),
      imageUrl: 'https://cdn.memegenerator.es/imagenes/memes/full/6/96/6965905.jpg',
      imageHeight: 250,
      imageWidth: 250,
      imageAlt: 'Maldita pobreza',
      icon: 'error',
    });
  }

  buyDone(t, menu) {
    this.props.history.push('/home');
    Swal.fire(
      t('Done!'),
      `${t('enjoy your')} ${menu.name}`,
      'success',
    );
    window.location.reload();
  }


  makeApiPost(menu, t) {
    const body = this.getBodyFromDelivery(menu);
    console.log(body);
    API.post('/order', body)
      .then(() => this.buyDone(t, menu))
      .catch((e) => console.log(e));
  }

  buyConfirmed(isConfirmed, menu, t) {
    if (isConfirmed) {
      this.makeApiPost(menu, t);
    }
  }

  handleBuyFromModal(menu, t) {
    Swal.fire({
      title: t('¿Estas seguro?'),
      text: `${t("We'll debit ")} ${menu.price * this.state.quantityOfMenus}  pesos`,
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

  async changePriceSortSelected(e) {
    if (this.state.priceOrder !== e || this.state.priority !== 'price') {
      await this.setState({ priceOrder: e });
      await this.setState({ priority: 'price' });
      this.requestAPISearch(this.createBodyWithPriceOrderAndPriority(e, 'price'));
    }
  }

  async changeRankSortSelected(e) {
    if (this.state.rankOrder !== e || this.state.priority !== 'rank') {
      await this.setState({ rankOrder: e });
      await this.setState({ priority: 'rank' });
      this.requestAPISearch(this.createBody());
    }
  }

  createBodyWithPriceOrderAndPriority(newRankOrder, newPriority) {
    const body = {
      locState: this.state.locState,
      googleId: this.state.googleId,
      tokenAccess: this.state.tokenAccess,
      name: this.state.name,
      city: this.state.city,
      category: this.state.category,
      priceOrder: this.state.priceOrder,
      rankOrder: newRankOrder,
      fromPage: this.state.fromPage,
      sizePage: this.state.sizePage,
      priority: newPriority,
    };
    return (body);
  }

  createBody() {
    const body = {
      locState: this.state.locState,
      googleId: this.state.googleId,
      tokenAccess: this.state.tokenAccess,
      name: this.state.name,
      city: this.state.city,
      category: this.state.category,
      priceOrder: this.state.priceOrder,
      rankOrder: this.state.rankOrder,
      fromPage: this.state.fromPage,
      sizePage: this.state.sizePage,
      priority: this.state.priority,
    };
    return (body);
  }

  searchConfig(t) {
    if (this.state.results.length === 0) {
      return (
        <div />
      );
    }
    return (
      <Container>
        <Row>
          <Col xs={1} sm={1} md={1} lg={1} xl={1} />
          <Col xs={8} sm={8} md={8} lg={8} xl={8}>
            <h3>{t('Ordenar por')}:</h3>
          </Col>
        </Row>
        <Row>
          <Col xs={1} sm={1} md={1} lg={1} xl={1} />
          <Col xs={4} sm={4} md={2} lg={2} xl={2}>
            <DropdownButton
              title={t('Price')}
              variant="info"
              className="dropdown-config"
              onSelect={(e) => this.changePriceSortSelected(e)}
            >
              <Dropdown.Item eventKey="" disabled>{t('Select one')}</Dropdown.Item>
              <Dropdown.Item eventKey="min">{t('Lowest Price')}</Dropdown.Item>
              <Dropdown.Item eventKey="max">{t('Highest Price')}</Dropdown.Item>
            </DropdownButton>
          </Col>
          <Col xs={2} sm={2} md={2} lg={2} xl={2}>
            <DropdownButton
              title={t('puntuacion')}
              variant="info"
              className="dropdown-config"
              onSelect={(e) => this.changeRankSortSelected(e)}
            >
              <Dropdown.Item eventKey="min">{t('Lowest Rank')}</Dropdown.Item>
              <Dropdown.Item eventKey="max">{t('Highest Rank')}</Dropdown.Item>
            </DropdownButton>
          </Col>
        </Row>
      </Container>
    );
  }

  buyButton(menu, t) {
    const handleClose = () => this.setShow(false);
    const handleShow = () => this.setShow(true);
    return (
      <Row>
        <Button className="buy-button" variant="danger" onClick={handleShow}>
          <img src={shoppingCartIcon} alt="buy" />
        </Button>

        <Modal show={this.state.showModal} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{t('buy')}</Modal.Title>
          </Modal.Header>
          <Modal.Body className="buy-body">{t('How many?')}
            <br />
            <input type="number" onChange={(e) => this.handleQuantityOfMenus(e)} />
            <p className="buy-body">{t('Do you want delivery?')}</p>
            <Form.Check inline label="Delivery" type="checkbox" id="inline-checkbox-1" onChange={(e) => this.handleDelivery(e)} />
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              {t('cancel')}
            </Button>
            <Button variant="primary" onClick={() => this.handleBuyFromModal(menu, t)}>
              {t('buy')}
            </Button>
          </Modal.Footer>
        </Modal>
      </Row>
    );
  }

  createFreeBadge(t) {
    return (
      <Badge pill variant="success">
        {t('Free')}
      </Badge>
    );
  }

  seeButton(menu, t) {
    console.log(menu);
    const handleClose = () => this.setShowSee(false);
    const handleShow = () => this.setShowSee(true);
    return (
      <Row>
        <Button className="buy-button" variant="info" onClick={handleShow}>
          <img src={menuInfoIcon} alt="menu-info" />
        </Button>
        <Modal show={this.state.showModalSee} onHide={handleClose}>
          <Modal.Header closeButton>
            <h1>{menu.name}</h1>
          </Modal.Header>
          <Modal.Title>{menu.description}</Modal.Title>
          <Modal.Body>
            <Card.Img className="card_img" variant="left" src={this.state.pictures[0]} /><br />
            <h5>{t('Delivery')}: {menu.deliveryValue <= 0 ? this.createFreeBadge(t) : formatPrice(t, menu.deliveryValue)}<br />
              {t('Comprando mas de')}: {menu.firstMinAmount} unidades<br />
              {t('the price will be')} {menu.firstMinAmountPrice} pesos<br />
              {t('Comprando mas de')}: {menu.menuPriceCalculator.secondMinAmount} unidades<br />
              {t('the price will be')} {menu.menuPriceCalculator.secondMinPrice} <br />
              {t('Distancia de delivery')} : {menu.deliveryMaxDistanceInKM} kms<br />
              {t('Estado del menu')} {menu.menuStateName}<br />
            </h5>
          </Modal.Body>

        </Modal>
      </Row>
    );
  }

  providerInfoButton(menu, t) {
    return (
      <Row>
        <Button className="buy-button" variant="success" onClick={console.log(menu)}>
          <img src={providerInfoIcon} alt="provider" />
        </Button>
      </Row>
    );
  }

  showBadge(t, rankAverage) {
    if (rankAverage === 5) {
      return (
        <Badge pill variant="warning">
          {t('Destacado')}
        </Badge>
      );
    }
    return (<div />);
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

  createAppropiatePaginationButton(functionOnClick, isDisabled, text) {
    if (isDisabled) {
      return (
        <li className="page-item disabled st_dis">
          <span className="page-link">{text}</span>
        </li>
      );
    }
    return (
      <li className="page-item st_en">
        <button type="submit" className="page-link" onClick={functionOnClick}>{text}</button>
      </li>
    );
  }

  createBodyFromPage(page) {
    const body = {
      locState: this.state.locState,
      googleId: this.state.googleId,
      tokenAccess: this.state.tokenAccess,
      name: this.state.name,
      city: this.state.city,
      category: this.state.category,
      priceOrder: this.state.priceOrder,
      rankOrder: this.state.rankOrder,
      fromPage: page,
      sizePage: this.state.sizePage,
      priority: this.state.priority,
    };
    return (body);
  }

  goFirstPage() {
    this.setState({ fromPage: 0 });
    this.requestAPISearch(this.createBodyFromPage(0));
  }

  goPreviousPage() {
    const page = this.state.currentPage - 1;
    this.requestAPISearch(this.createBodyFromPage(page));
  }

  goNextPage() {
    const page = this.state.currentPage + 1;
    this.requestAPISearch(this.createBodyFromPage(page));
  }

  goLastPage() {
    this.requestAPISearch(this.createBodyFromPage(this.state.totalPages - 1));
  }

  paginationButtons(t) {
    const firstPageClick = () => this.goFirstPage();
    const previousPageClick = () => this.goPreviousPage();
    const nextPageClick = () => this.goNextPage();
    const lastPageClick = () => this.goLastPage();
    if (this.state.results.length > 0) {
      return (
        <nav aria-label="Page navigation example">
          <ul className="pagination justify-content-center">
            {this.createAppropiatePaginationButton(firstPageClick, this.state.currentPage <= 0, t('First'))}
            {this.createAppropiatePaginationButton(previousPageClick, this.state.currentPage <= 0, t('<<'))}
            <li className="page-item disabled">
              <span className="page-link">{this.state.currentPage + 1}</span>
            </li>
            {this.createAppropiatePaginationButton(nextPageClick, this.state.currentPage >= this.state.totalPages - 1, t('>>'))}
            {this.createAppropiatePaginationButton(lastPageClick, this.state.currentPage >= this.state.totalPages - 1, `${t('Last')} (${this.state.totalPages})`)}
          </ul>
        </nav>
      );
    }
    return (
      <div />
    );
  }

  renderMenu(menu, t) {
    console.log(menu);
    const randomNumber = Math.floor(Math.random() * (this.state.pictures.length));
    return (
      <div className="container menu_card" key={menu.id}>
        <Card>
          <Card.Header as="h4">{menu.name}</Card.Header>
          <Card.Body>
            <Row>
              <Col lg={4.5}>
                <Card.Img className="card_img" variant="left" src={this.state.pictures[randomNumber]} />
                <div className="text-center font-weight-bold price-card_menu">
                  {`${formatPrice(t, menu.price)}`}
                </div>
              </Col>
              <Col>

                {this.showStars(menu)} <br />
                {this.showBadge(t, menu.rankAverage)}
                <h5>
                  {t('Description')}: {menu.description}<br />
                  {t('Delivery')}: {menu.deliveryValue === 0 ? this.createFreeBadge(t) : formatPrice(t, menu.deliveryValue)}<br />
                  {t('Valido hasta')} {formatDate(t, new Date(menu.effectiveDateGoodThru))}<br />
                </h5>

              </Col>
              <Col lg={2}>
                {this.buyButton(menu, t)}
                {this.seeButton(menu, t)}
                {this.providerInfoButton(menu, t)}
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
      <div className="search_results">
        <h1 className="title">
          {`${t('Search results')}`}
        </h1>
        <div className="page_buttons">
          {this.searchConfig(t)}
          {this.mapResults(t)}
          {this.paginationButtons(t)}
        </div>
      </div>
    );
  }
}

export default withTranslation()(SearchResult);
