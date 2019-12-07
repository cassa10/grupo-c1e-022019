import React from 'react';
import { withTranslation } from 'react-i18next';
import '../dist/css/Profile.css';
import API from '../service/api';

class ProviderProfile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      profileImg: '',
      googleId: '',
      tokenAccess: '',
      user: {
        id: 0,
      },
      orders: [],
    };
  }

  componentDidMount() {
    const body = {
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
      idProvider: this.props.location.state.user.id,
    };

    API.get('/provider', body)
      .then((response) => this.setState({ user: response }))
      .catch((error) => this.handleAPIError(error));

    API.get('/order/provider/all', body)
      .then((response) => this.setState({ orders: response }))
      .catch((error) => this.handleAPIError(error));
  }

  handleAPIError(errorAPI) {
    console.log(errorAPI);
    this.props.history.push({
      pathname: '/error',
      state: {
        googleId: this.state.googleId,
        tokenAccess: this.state.tokenAccess,
        user: this.state.user,
        error: errorAPI,
      },
    });
  }

  render() {
    const { t } = this.props;
    return (
      <div className="profile-root">
        {t('HOLA MENSAJE Q SE TIENE QUE BORRAR')}
        {`USER_ID: ${this.state.user.id} `}
        {`Type_Client: ${this.state.user.typeClient} `}
        {`Type_Provider: ${this.state.user.typeProvider} `}
        {`Orders: ${this.state.orders} `}
      </div>
    );
  }
}

export default withTranslation()(ProviderProfile);
