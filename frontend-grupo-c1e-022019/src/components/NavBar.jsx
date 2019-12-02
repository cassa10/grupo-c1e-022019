import React from 'react';
import { withTranslation } from 'react-i18next';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import ChangeLanguage from './ChangeLanguage';
import '../dist/css/Navbar.css';
import API from '../service/api';

class NavBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      googleId: '',
      tokenAccess: '',
      client: {
        address: {},
        cannotBuyClient: false,
        clientHaveToRank: false,
        credit: {
          id: 0,
          amount: 0,
        },
        email: '',
        firstName: '',
        googleId: '',
        id: 0,
        imageUrl: '',
        lastName: '',
        location: null,
        normalClient: true,
        ordersHaveToRank: [],
        phoneNumber: null,
        sizeOrderHaveToRank: 0,
        stateClient: {
          cannotBuyClient: false,
          id: 0,
          normal: true,
        },
      },
      showModal: false,
      creditInput: 0,
    };
  }

  componentDidMount() {
    const bodyRequest = {
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
      idClient: this.props.location.state.client.id,
    };

    this.setState({ googleId: bodyRequest.googleId, tokenAccess: bodyRequest.tokenAccess });

    API.get('/client', bodyRequest)
      .then((response) => this.setState({ client: response }))
      .catch((error) => console.log(error));
  }

  getClientBalance() {
    return this.state.client.credit.amount;
  }

  setShow(b) {
    this.setState({ showModal: b });
  }

  handleCreditInput(e) {
    this.setState({ creditInput: e.target.value });
  }

  updateClient(newClient) {
    this.setState({ client: newClient });
  }

  handleAccredit(t) {
    if (this.state.creditInput > 0) {
      this.accredit();
    } else {
      alert(t('Please fill with a positive mount'));
    }
  }

  accredit() {
    const body = {
      id: this.state.client.id,
      googleId: this.state.googleId,
      tokenAccess: this.state.tokenAccess,
      amount: this.state.creditInput,
    };
    API.post('/client/accredit', body)
      .then((response) => this.updateClient(response))
      .catch((error) => console.log(error));
    this.setShow(false);
  }

  accrditButton(t) {
    const handleClose = () => this.setShow(false);
    const handleShow = () => this.setShow(true);
    return (
      <div className="container-button">
        <Button variant="primary" onClick={handleShow}>
          {t('Accredit')}
        </Button>

        <Modal show={this.state.showModal} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{t('Accredit')}</Modal.Title>
          </Modal.Header>
          <Modal.Body>{t('How much?')}</Modal.Body>
          <input type="number" onChange={(e) => this.handleCreditInput(e)} />
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              {t('cancel')}
            </Button>
            <Button variant="primary" onClick={() => this.handleAccredit(t)}>
              {t('Accredit')}
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    );
  }

  goBackHome() {
    this.props.history.push({
      pathname: '/home',
      state: {
        googleId: this.props.location.state.googleId,
        tokenAccess: this.props.location.state.tokenAccess,
        client: this.props.location.state.client,
      },
    });
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <Navbar className="all-navbar" fixed="top">
          <Container className="container-navbar">
            <Row className="row-navbar">
              <Col className="logo-col">
                <Navbar.Brand className="homelogoimg">
                  <img src="https://fontmeme.com/permalink/191102/03a545ac680d1396fcfae624d4ee0c3a.png" width="250" onClick={() => this.goBackHome()} alt="viendas-font" border="0" className="pointerImg" role="presentation" />
                </Navbar.Brand>
              </Col>
              <Col>
                {this.accrditButton(t)}
                <div className="balance container-balance">
                  {t('Balance')}: ${this.getClientBalance()}
                </div>
                <ChangeLanguage />
              </Col>
            </Row>
          </Container>
        </Navbar>
      </div>
    );
  }
}

export default withTranslation()(NavBar);
