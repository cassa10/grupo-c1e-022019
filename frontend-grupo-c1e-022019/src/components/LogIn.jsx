import { withTranslation } from 'react-i18next';
import { GoogleLogin } from 'react-google-login';
import React from 'react';
import ChangeLanguage from './ChangeLanguage';
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
        clientId="723708835832-24ugpa2lt64gs3149fvdceb5g9gv81on.apps.googleusercontent.com"
        buttonText={buttonText}
        onSuccess={onSuccessFunc}
        onFailure={onFailureFunc}
        cookiePolicy="single_host_origin"
      />
    );
  }

  createLoginForm(t) {
    const responseGoogle = (response) => {
      console.log(response);
    };
    return (
      <div className="container">
        <div className="row">
          <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div className="card card-signin my-5">
              <div className="card-body">
                <h5 className="card-title text-center">{t('Welcome to Viendas Ya')}</h5>
                <div className="text-center">
                  {this.createGoogleButtonAuth(t('Log in'), responseGoogle, responseGoogle)}
                </div>
                <hr className="my_4" />
                <div className="text-center">
                  {this.createGoogleButtonAuth(t('Sign up 2'), responseGoogle, responseGoogle)}
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
              <img className="logo" src="https://fontmeme.com/permalink/191102/03a545ac680d1396fcfae624d4ee0c3a.png" alt="viendasYa-logo" border="0"/>
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
