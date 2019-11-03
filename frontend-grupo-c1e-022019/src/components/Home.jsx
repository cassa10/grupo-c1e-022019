import React, { Suspense } from 'react';
import API from '../service/api';
import '../dist/css/Home.css';

import { withTranslation } from 'react-i18next';


class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tmp: '',
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

  goToMap() {
    this.props.history.push({
      pathname: '/map',
    });
  }

  goToSignUpProvider() {
    this.props.history.push({
      pathname: '/signup_provider',
    });
  }

  render() {
    const {t} = this.props;
    return (
      <Suspense fallback={<div>loading...</div>}>
        <div className="container">
          <div className="row">
            <div className="col-12 title-home">
              <h4 className="message-title-home">
              INICIO: {this.state.tmp}
              </h4>
            </div>
            <div className="row">
              <div className="col-12 map-provider">
                <button type="button" className="btn btn-primary" onClick={() => this.goToMap()}>{t("Go to map")}</button>
              </div>
              <div className="col-12 signup-provider-button">
                <button type="button" className="btn btn-danger" onClick={()=> this.goToSignUpProvider()}>
                  {t("Sign up as provider")}
                </button>
              </div>
            </div>
          </div>
        </div>
      </Suspense>
    );
  }
}
export default withTranslation()(Home);
