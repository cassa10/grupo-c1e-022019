import { withTranslation } from 'react-i18next';
import React, { Suspense } from 'react';
import Button from 'react-bootstrap/Button';
import API from '../service/api';
import '../dist/css/Home.css';

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tmp: '',
      searchInputName: '',
      searchInputCategory: '',
      searchInputCity: '',
    };
  }

  componentDidMount() {
    this.useAPI();
  }

  useAPI() {
    API.get('/')
      .then((response) => this.setState({ tmp: response }))
      .catch((error) => error);
  }

  goToSignUpProvider() {
    this.props.history.push({
      pathname: '/signup_provider',
    });
  }

  search() {
    const { t } = this.props;
    if (this.state.searchInputName.trim() !== '' || this.state.searchInputCategory !== ''
        || this.state.searchInputCity.trim() !== '') {
      this.goToSearch();
    } else {
      alert(t('Please, complete any field'));
    }
  }

  goToSearch() {
    this.props.history.push({
      pathname: '/search',
      state: {
        searchFromHome: true,
        searchInputName: this.state.searchInputName.trim(),
        searchInputCity: this.state.searchInputCity.trim(),
        searchInputCategory: this.state.searchInputCategory,
      },
    });
  }

  handleSearchNameInput(e) {
    this.setState({ searchInputName: e.target.value });
  }

  changeCategorySearchValue(e) {
    this.setState({ searchInputCategory: e.target.value });
  }

  changeCityLocationSearchValue(e) {
    this.setState({ searchInputCity: e.target.value });
  }

  createSearchForm(t) {
    return (
      <div className="container search-home">
        <form className="form-menu-search">
          <div className="row">
            <div className="col-lg-12">
              <div className="row">
                <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                  <input type="text" className="form-control search-slt" placeholder={t('Enter Menu Name')} onChange={(e) => this.handleSearchNameInput(e)} />
                </div>
                <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                  <input type="text" className="form-control search-slt" placeholder={t('Enter City Location')} onChange={(e) => this.changeCityLocationSearchValue(e)} />
                </div>
                <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                  <select className="form-control search-slt" onChange={(e) => this.changeCategorySearchValue(e)}>
                    <option value="">{t('Select a Category')}</option>
                    <option value="PIZZA">{t('Pizza')}</option>
                    <option value="HAMBURGER">{t('Hamburger')}</option>
                    <option value="SUSHI">{t('Sushi')}</option>
                    <option value="BEER">{t('Beer')}</option>
                    <option value="EMPANADAS">{t('Empanadas')}</option>
                    <option value="GREEN">{t('Green')}</option>
                    <option value="VEGAN">{t('Vegan')}</option>
                  </select>
                </div>
                <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                  <Button className="searchButton wrn-btn" variant="danger" onClick={() => this.search()}>{`${t('Search text')} Menu`} </Button>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    );
  }

  render() {
    const { t } = this.props;
    return (
      <Suspense fallback={<div />}>
        <div className="container">
          <div className="row">
            <div className="col-12 title-home">
              <h4 className="message-title-home">
                {`${t('Inicio')}:`} {this.state.tmp}
              </h4>
            </div>
          </div>
          {this.createSearchForm(t)}
        </div>
      </Suspense>
    );
  }
}
export default withTranslation()(Home);
