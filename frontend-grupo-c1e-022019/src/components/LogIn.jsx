import { withTranslation } from 'react-i18next';
import { GoogleLogin } from 'react-google-login';
import React from 'react';
import ChangeLanguage from './ChangeLanguage';
import API from '../service/api';
import '../dist/css/LogIn.css';

class LogIn extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  createGoogleButtonAuth(buttonText, onSuccessFunc, onFailureFunc) {
    return (
      <GoogleLogin
        clientId="677456909751-9cfvoal7a938s5sd07u3qee5bfm2ba4o.apps.googleusercontent.com"
        buttonText={buttonText}
        onSuccess={onSuccessFunc}
        onFailure={onFailureFunc}
        cookiePolicy="single_host_origin"
      />
    );
  }

  goToHomePage(response, googleDataResponse) {
    const loginStateValues = {
      googleId: googleDataResponse.googleId,
      tokenAccess: googleDataResponse.tokenAccess,
      user: response,
      sideBarSelected: 'home',
    };

    this.props.history.push({
      pathname: '/home',
      state: loginStateValues,
    });
  }

  handleLoginResponseOk(response) {
    const googleDataResponse = {
      googleId: response.googleId,
      tokenAccess: response.tokenObj.access_token,
      tokenId: response.tokenObj.id_token,
      expires_in: response.tokenObj.expires_in,
    };

    API.post('/login', googleDataResponse)
      .then((user) => this.goToHomePage(user, googleDataResponse))
      .catch((error) => this.handleAuthAPIError(error));
  }

  handleSignUpResponseOk(response) {
    const googleDataResponse = {
      googleId: response.googleId,
      tokenAccess: response.tokenObj.access_token,
      tokenId: response.tokenObj.id_token,
      expires_in: response.tokenObj.expires_in,
    };

    const clientDataResponse = {
      firstName: response.profileObj.givenName,
      lastName: response.profileObj.familyName,
      email: response.profileObj.email,
      googleId: response.googleId,
      imageUrl: response.profileObj.imageUrl,
      googleAuthDTO: googleDataResponse,
    };

    API.post('/signup', clientDataResponse)
      .then((client) => this.goToHomePage(client, googleDataResponse))
      .catch((error) => this.handleAuthAPIError(error));
  }

  handleAuthAPIError(error) {
    const { t } = this.props;
    if (error.response && error.response.data) {
      alert(t(error.response.data));
    } else {
      alert(t('Server is not responding, please try again later'));
    }
  }

  handleGoogleResponseError() {
    const { t } = this.props;
    alert(t('Ups something went wrong with google, please try again'));
  }

  createLoginForm(t) {
    const responseLoginOk = (response) => {
      this.handleLoginResponseOk(response);
    };
    const responseSignupOk = (response) => {
      this.handleSignUpResponseOk(response);
    };
    const responseError = () => {
      this.handleGoogleResponseError();
    };
    return (
      <div className="container">
        <div className="row">
          <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div className="card card-signin my-5">
              <div className="card-body">
                <h5 className="card-title text-center">{t('Welcome to Viendas Ya')}</h5>
                <div className="text-center">
                  {this.createGoogleButtonAuth(t('Log in'), responseLoginOk, responseError)}
                </div>
                <hr className="my_4" />
                <div className="text-center">
                  {this.createGoogleButtonAuth(t('Sign up 2'), responseSignupOk, responseError)}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  render() {
    const { t } = this.props;
    return (
      <main>
        <header>
          <div className="row">
            <div className="col">
              <img className="logginlogo" src="https://fontmeme.com/permalink/191206/ae6df82115063423c0205aaf7499bff2.png" alt="viendasYa-logo" border="0" />
            </div>
            <ChangeLanguage />
          </div>
        </header>
        {this.createLoginForm(t)}
      </main>
    );
  }
}
export default withTranslation()(LogIn);
