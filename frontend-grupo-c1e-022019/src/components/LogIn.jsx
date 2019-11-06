import { withTranslation } from 'react-i18next';
import React from 'react';
import ChangeLanguage from './ChangeLanguage';
import '../dist/css/LogIn.css';

class LogIn extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  logIn() {

  }

  render() {
    const { t } = this.props;
    return (
      <main>
        <header>
          <div className="row">
            <div className="col">
              <img className="logo" src="https://fontmeme.com/permalink/191102/03a545ac680d1396fcfae624d4ee0c3a.png" alt="viendasYa-logo" border="0" />
            </div>
            <ChangeLanguage />
          </div>
        </header>
        <div className="row">
          <div className="container">
            <div className="col-16 col-sm-10 col-md-8 col-lg-6 col-xl-4 offset-xl-4 logIn">
              <div className="header-logIn">
                {t('Log in')}
              </div>
              <div className="text-right providerSingUpLogIn">
                <span className="align-text-bottom">
                  {t('Sing up/log in as provider')}
                </span>
              </div>
            </div>
          </div>
        </div>
      </main>
    );
  }
}
export default withTranslation()(LogIn);
