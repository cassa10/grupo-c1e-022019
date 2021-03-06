/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import '../dist/css/SearchResult.css';
import {
  Card, Container, Col, DropdownButton, Dropdown,
  Row, Badge,
} from 'react-bootstrap';
import { withTranslation } from 'react-i18next';
import Swal from 'sweetalert2';
import StarRatingComponent from 'react-star-rating-component';
import API from '../service/api';
import formatPrice from './formatter/formatCredit';
import ModalSee from './ModalSee';
import ModalProvider from './ModalProvider';
import ModalBuyMenu from './ModalBuyMenu';
import formatDate from './formatter/formatDate';


class SearchResult extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      googleId: '',
      tokenAccess: '',
      user: {},
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
      user: this.props.location.state.user,
      name: this.props.location.state.searchInputName,
      city: this.props.location.state.searchInputCity,
      category: this.props.location.state.searchInputCategory,
      priceOrder: this.props.location.state.priceOrder,
      rankOrder: this.props.location.state.rankOrder,
      fromPage: this.props.location.state.fromPage,
      sizePage: this.state.sizePage,
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

  getDecreasedFromPage() {
    return (
      this.state.fromPage - 1
    );
  }

  requestAPISearch(body) {
    window.scrollTo(0, 0);
    API.get(`/menu/search/${this.detectPathSearch(body.locState)}/`, body)
      .then((response) => this.handleAPISearch(response))
      .catch((error) => this.handleErrorAPI(error));
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
    Swal.fire({
      title: t('Insufficient credit'),
      imageUrl: 'https://cdn.memegenerator.es/imagenes/memes/full/6/96/6965905.jpg',
      imageHeight: 250,
      imageWidth: 250,
      imageAlt: 'Maldita pobreza',
      icon: 'error',
    });
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
      <Row className="ordenar_por_row">
        <Col xs={8} sm={8} md={12} lg={8} xl={8}>
          <h4>{t('Ordenar por')}</h4>
        </Col>
        <Col xs={8} sm={8} md={2} lg={2} xl={2}>
          <DropdownButton
            title={t('Price')}
            variant="info"
            className="dropdown-config"
            onSelect={(e) => this.changePriceSortSelected(e)}
          >
            <Dropdown.Item eventKey="min">{t('Lowest Price')}</Dropdown.Item>
            <Dropdown.Item eventKey="max">{t('Highest Price')}</Dropdown.Item>
          </DropdownButton>
        </Col>
        <Col xs={8} sm={8} md={4} lg={2} xl={2}>
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
    );
  }

  buyButton(menu) {
    return (
      <Row>
        <Col>
          <ModalBuyMenu
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...this.props}
            googleId={this.state.googleId}
            tokenAccess={this.state.tokenAccess}
            user={this.state.user}
            menu={menu}
          />
        </Col>
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

  seeButton(menu) {
    return (
      <Row>
        <Col>
          <ModalSee menu={menu} />
        </Col>
      </Row>
    );
  }

  providerInfoButton(menu) {
    return (
      <Row>
        <Col>
          <ModalProvider
            {...this.props}
            providerId={menu.providerId}
            googleId={this.state.googleId}
            tokenAccess={this.state.tokenAccess}
          />
        </Col>
      </Row>
    );
  }

  showBadges(t, rankAverage, categories) {
    return (
      <Row>
        {this.showHighlightedBadge(t, rankAverage)}
        {categories.map((cat) => this.showCategoryBadge(t, cat))}
      </Row>
    );
  }

  showCategoryBadge(t, category) {
    // Categories: PIZZA | BEER | HAMBURGER | SUSHI | EMPANADAS | GREEN | VEGAN
    this.detecVariantForCategoryBadge(category);
    return (
      <Badge key={category} variant={this.detecVariantForCategoryBadge(category)} className="space-category-badge">
        {t(category)}
      </Badge>
    );
  }

  detecVariantForCategoryBadge(category) {
    let variantCat = '';
    if (category === 'PIZZA') {
      variantCat = 'danger';
    }
    if (category === 'BEER') {
      variantCat = 'info';
    }
    if (category === 'HAMBURGER') {
      variantCat = 'primary';
    }
    if (category === 'SUSHI') {
      variantCat = 'light';
    }
    if (category === 'EMPANADAS') {
      variantCat = 'dark';
    }
    if (category === 'GREEN') {
      variantCat = 'success';
    }
    if (category === 'VEGAN') {
      variantCat = 'secondary';
    }
    return (
      variantCat
    );
  }

  showHighlightedBadge(t, rankAverage) {
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

  searchResultTitle(t) {
    if (this.state.results.length > 0) {
      return (
        <Container className="search_results_container_1">
          <h1 className="title">
            {`${t('Search results')}`}
          </h1>
          {this.searchConfig(t)}
        </Container>
      );
    }
    return (
      <div />
    );
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
    return (
      <div className="container menu_card" key={menu.id}>
        <Card>
          <Card.Header as="h4">{menu.name}</Card.Header>
          <Card.Body>
            <Row>
              <Col lg={4.5}>
                <Card.Img className="card_img" variant="left" src={this.state.pictures[0]} />
                <div className="text-center font-weight-bold price-card_menu">
                  {`${formatPrice(t, menu.price)}`}
                </div>
              </Col>
              <Col>
                {this.showBadges(t, menu.rankAverage, menu.categories)}
                <Row>
                  <Col>
                    {this.showStars(menu)}
                  </Col>

                </Row>

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
      <div className="search_results_div_all">
        {this.searchResultTitle(t)}
        <div className="page_buttons">
          {this.mapResults(t)}
          {this.paginationButtons(t)}
        </div>
      </div>
    );
  }
}

export default withTranslation()(SearchResult);
