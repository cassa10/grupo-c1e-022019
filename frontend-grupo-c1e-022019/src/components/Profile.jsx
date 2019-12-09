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
import OrderCard from './OrderCard';
import formatCredit from './formatter/formatCredit.js';
import ModalEditProfileClient from './ModalEditProfileClient';
import accreditIcon from '../dist/icons/accredit-icon.png';
import API from '../service/api';

class Profile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      profileImg: '',
      googleId: '',
      tokenAccess: '',
      user: {
        id: 0,
        credit: {
          id: 0,
          amount: 0,
        },
      },
      orders: [],
      showBuyModal: false,
      inputCredit: 0,
    };
  }

  componentDidMount() {
    const body = {
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
      idClient: this.props.location.state.user.id,
    };

    this.setState({ googleId: body.googleId });
    this.setState({ tokenAccess: body.tokenAccess });
    this.setState({ user: this.props.location.state.user });

    API.get('/client', body)
      .then((response) => this.setState({ user: response }))
      .catch((error) => this.handleAPIError(error));

    API.get('/order/client/all', body)
      .then((response) => this.setState({ orders: response }))
      .catch((error) => this.handleAPIError(error));
  }

  setShow(b) {
    this.setState({ showBuyModal: b });
  }

  handleAPIError(errorAPI) {
    this.props.location.history.push({
      pathname: '/error',
      state: {
        googleId: this.state.googleId,
        tokenAccess: this.state.tokenAccess,
        user: this.state.user,
        error: errorAPI,
      },
    });
  }

  handleCredit(e) {
    this.setState({ inputCredit: e.target.value });
  }

  swalSuccess(t) {
    Swal.fire({
      title: t('Accredited!'),
      icon: 'success',
    });
    window.location.reload();
  }

  doAccredit(t) {
    const body = {
      googleId: this.state.googleId,
      tokenAccess: this.state.tokenAccess,
      amount: this.state.inputCredit,
    };
    API.post('/client/accredit', body)
      .then(() => this.swalSuccess(t))
      .catch((e) => console.log(e));
  }

  accreditButton(t) {
    const handleClose = () => this.setShow(false);
    const handleShow = () => this.setShow(true);
    return (
      <div>
        <Button variant="primary" onClick={handleShow}>
          <img src={accreditIcon} alt={t('Accredit')} />
        </Button>
        <Modal show={this.state.showBuyModal} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{t('Accredit')}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {t('How much?')}
            <input type="number" className="form-control input-weburl-provider" placeholder={t('Please fill with a positive mount')} onChange={(e) => this.handleCredit(e)} />
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              {t('cancel')}
            </Button>
            <Button variant="success" onClick={() => this.doAccredit(t)}>
              {t('Accredit')}
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    );
  }

  renderImg() {
    return <img className="profile-img" alt="providerimg" src={this.state.user.imageUrl} />;
  }

  renderBalanceAndEdit(t) {
    return (
      <Card className="card">
        <Card.Body>
          <Card.Title>
            <Container>
              <Row>
                <Col>
                  <h3 className="balance-text">{t('Su saldo es de')} {formatCredit(t, this.state.user.credit.amount)}</h3>
                </Col>
                <Col>
                  {this.accreditButton(t)}
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

  renderName(t) {
    return <h1 className="provider-title">{t('Hola')}{this.state.user.firstName}</h1>;
  }

  renderTitleOrders(t) {
    return <h2 className="historic-orders provider-title">{t('Ordenes historicas')}</h2>;
  }

  renderOrders(t) {
    return (this.state.orders.map((order) => this.renderOrder(order, t)));
  }

  renderOrder(order) {
    return <OrderCard order={order} googleId={this.props.location.state.googleId} tokenAccess={this.props.location.state.tokenAccess} />;
  }

  render() {
    const { t } = this.props;
    return (
      <div className="profile-root">
        {this.renderImg(t)}
        {this.renderName(t)}
        {this.renderBalanceAndEdit(t)}
        {this.renderTitleOrders(t)}
        {this.renderOrders(t)}
        <Container>
          <Row>
            <Col className="around_provider_title" />
          </Row>
        </Container>
      </div>
    );
  }
}

export default withTranslation()(Profile);
