/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import { withTranslation } from 'react-i18next';
import '../dist/css/Profile.css';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Modal from 'react-bootstrap/Modal';
import Swal from 'sweetalert2';
import debitIcon from '../dist/icons/debitIcon.png';
import OrderCard from './OrderCard';
import formatCredit from './formatter/formatCredit.js';
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
      showModal: false,
      inputCredit: 0,
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

  setShow(b) {
    this.setState({ showModal: b });
  }

  getCredit() {
    if (this.state.user.credit === undefined) {
      return 0;
    }
    return this.state.user.credit.amount;
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

  swalSuccess(t) {
    Swal.fire({
      title: t('Debited!'),
      icon: 'success',
    });
    window.location.reload();
  }

  swalVerifyInput(t) {
    if (parseInt(this.state.inputCredit,10) < 0) {
      Swal.fire({
        title: t('Verify input'),
        icon: 'error',
      });
    } else {
      Swal.fire({
        title: t('Insufficient credit'),
        icon: 'error',
      });
    }
  }

  doDebit(t) {
    const body = {
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
      amountToWithdraw: this.state.inputCredit,
    };
    API.post('/provider/credit/withdraw', body)
      .then(() => this.swalSuccess(t))
      .catch((e) => this.swalVerifyInput(t));
  }


  handleCredit(e) {
    this.setState({ inputCredit: e.target.value });
  }


  debitButton(t) {
    const handleClose = () => this.setShow(false);
    const handleShow = () => this.setShow(true);
    return (
      <div>
        <Button className="buy-button" variant="primary" onClick={handleShow}>
          <img src={debitIcon} alt={t('Accredit')} />
        </Button>
        <Modal show={this.state.showModal} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{t('Debit')}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {t('How much?')}
            <input type="number" className="form-control input-weburl-provider" placeholder={t('Please fill with a positive mount')} onChange={(e) => this.handleCredit(e)} />
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              {t('cancel')}
            </Button>
            <Button variant="success" onClick={() => this.doDebit(t)}>
              {t('Debit')}
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    );
  }

  renderName() {
    return <h1 className="provider-title">{this.state.user.name}</h1>;
  }


  renderImg() {
    return <img className="profile-img" alt="providerimg" src={this.state.user.logo} />;
  }

  renderBalance(t) {
    return (
      <Card className="card">
        <Card.Body>
          <Card.Title>
            <Container>
              <Row>
                <Col>
                  <h3 className="balance-text">{t('Su saldo es de')} {formatCredit(t, this.getCredit())}</h3>
                </Col>
                <Col className="text-left">
                  {this.debitButton(t)}
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
    return (
      <div className="profile-root">
        {this.renderImg(t)}
        {this.renderName(t)}
        {this.renderBalance(t)}
        {this.renderTitleOrders(t)}
        {this.renderOrders(t)}
      </div>
    );
  }
}

export default withTranslation()(ProviderProfile);
