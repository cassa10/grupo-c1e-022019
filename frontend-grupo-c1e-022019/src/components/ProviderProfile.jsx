/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import { withTranslation } from 'react-i18next';
import '../dist/css/Profile.css';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import OrderCard from './OrderCard';
import formatCredit from './formatter/formatCredit.js';
import ModalEditProfileClient from './ModalEditProfileClient';
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

  renderName() {
    return <h1 className="provider-title">{this.state.user.name}</h1>;
  }


  renderImg() {
    return <img className="profile-img" alt="providerimg" src={this.state.user.logo} />;
  }

  renderBalanceAndEdit(t) {
    return (
      <Card className="card">
        <Card.Body>
          <Card.Title>
            <Container>
              <Row>
                <Col>
                  <h3 className="balance-text">{t('Su saldo es de')} {formatCredit(t, 20)}</h3>
                </Col>
                <Col>
                  <Button> Debit</Button>
                </Col>
                <Col>
                  <ModalEditProfileClient
                    {...this.props}
                    googleId={this.state.googleId}
                    tokenAccess={this.state.tokenAccess}
                    profileImg={this.state.profileImg}
                    user={this.state.user}
                    isClient={this.state.user.typeClient}
                  />
                </Col>
              </Row>
            </Container>
          </Card.Title>

        </Card.Body>
      </Card>
    );
  }

  renderTitleOrders(t) {
    return <h2 className="historic-orders provider-title">{t('Ordenes historicas')}</h2>;
  }

  renderOrder(order) {
    return (
      <OrderCard
        {...this.props}
        key={order.id}
        order={order}
        googleId={this.props.location.state.googleId}
        tokenAccess={this.props.location.state.tokenAccess}
        isClient={false}
      />
    );
  }

  renderOrders(t) {
    return (this.state.orders.map((order) => this.renderOrder(order, t)));
  }

  render() {
    const { t } = this.props;
    console.log(this.state);
    return (
      <div className="profile-root">
        {this.renderImg(t)}
        {this.renderName(t)}
        {this.renderBalanceAndEdit(t)}
        {this.renderTitleOrders(t)}
        {this.renderOrders(t)}
      </div>
    );
  }
}

export default withTranslation()(ProviderProfile);
