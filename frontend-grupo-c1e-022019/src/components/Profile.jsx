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
import formatCredit from './formatter/formatCredit.js';
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
      googleId: this.state.user.googleId,
      amount: this.state.inputCredit,
    };
    API.post('/client/accredit', body)
      .then((response) => this.swalSuccess(t))
      .catch((e) => console.log(e));
  }

  accreditButton(t) {
    const handleClose = () => this.setShow(false);
    const handleShow = () => this.setShow(true);
    console.log(this.state);
    return (
      <div>
        <Button variant="primary" onClick={handleShow}>{t('Accredit')}</Button>

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
            <Button variant="primary" onClick={() => this.doAccredit(t)}>
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

  renderBalance(t) {
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
                  <Button variant="primary">{t('Edit profile')}</Button>
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

  render() {
    const { t } = this.props;
    console.log(this.state);
    return (
      <div className="profile-root">
        {this.renderImg(t)}
        {this.renderName(t)}
        {this.renderBalance(t)}
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
