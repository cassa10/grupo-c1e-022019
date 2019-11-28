import React from 'react';
import { withTranslation } from 'react-i18next';
import '../dist/css/Sign-up-provider.css';

class SignUpProvider extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      logo: '',
      city: '',
      address: {
        coord: {
          latitude: 0.0,
          longitude: 0.0,
        },
        location: '',
      },
      description: '',
      webURL: '',
      email: '',
      telNumber: '',
      deliveryMaxDistanceInKM: 0.0,
      schedule: {
        daysAndHours: {},
      },
    };
  }

  cancelToSignUpProvider() {
    this.setState({});
    this.props.history.push({
      pathname: '/',
    });
  }

  signUpProvider() {
    console.log('ERROR');
  }

  handlerProviderName(e) {
    this.setState({ name: e.target.value });
  }

  handlerProviderLogo(e) {
    this.setState({ logo: e.target.value });
  }

  handlerProviderCity(e) {
    this.setState({ city: e.target.value });
  }

  handlerProviderDescription(e) {
    this.setState({ description: e.target.value });
  }

  handlerProviderAddressLocation(e) {
    this.setState({ address: { coord: this.state.address.coord, location: e.target.value } });
  }

  handlerProviderTelephoneNumber(e) {
    this.setState({ telNumber: e.target.value });
  }

  handlerProviderWebURL(e) {
    this.setState({ webURL: e.target.value });
  }

  createButtonsOfForm({ t }) {
    return (
      <div className="col-12 signup-buttons">
        <button
          type="button"
          className="btn btn-danger sign-up-button"
          onClick={() => this.signUpProvider()}
        >
          {t('Sign up')}
        </button>
        <button
          type="button"
          className="btn btn-primary"
          onClick={() => this.cancelToSignUpProvider()}
        >
          {t('Go back')}
        </button>
      </div>
    );
  }

  createInputOfName({ t }) {
    return (
      <input type="text" className="form-control input-name-provider" id="inputNameProvider" placeholder={t('Name provider')} onChange={(e) => this.handlerProviderName(e)} />
    );
  }

  createInputOfAddress({ t }) {
    return (
      <input type="text" className="form-control input-address-location-provider" id="inputAddressLocationProvider" placeholder={t('Address')} onChange={(e) => this.handlerProviderAddressLocation(e)} />
    );
  }

  createInputOfCity({ t }) {
    return (
      <input type="text" className="form-control input-city-provider" id="inputCityProvider" placeholder={t('City')} onChange={(e) => this.handlerProviderCity(e)} />
    );
  }

  createInputOfDescription({ t }) {
    return (
      <input type="text" className="form-control input-description-provider" id="inputDescriptionProvider" placeholder={t('Description')} onChange={(e) => this.handlerProviderDescription(e)} />
    );
  }

  createInputOfTelephoneNumber({ t }) {
    return (
      <input type="text" className="form-control input-tel-number-provider" id="inputTelephoneProvider" placeholder={t('Telephone')} onChange={(e) => this.handlerProviderTelephoneNumber(e)} />
    );
  }

  render() {
    const { t } = this.props;
    return (
      <div className="container">
        <div className="row">
          <div className="col-12 sign-up-provider">
            <h2 className="text-provider-sign-up">
              {t('Fill out this form for sign up as provider')}
            </h2>
          </div>
          <form className="form-inline">
            {this.createInputOfName({ t })}
            {this.createInputOfAddress({ t })}
            {this.createInputOfCity({ t })}
            {this.createInputOfTelephoneNumber({ t })}
            {this.createInputOfDescription({ t })}
            {this.createButtonsOfForm({ t })}
          </form>
        </div>
      </div>
    );
  }
}

export default withTranslation()(SignUpProvider);
