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
      client: {
        credit: { amount: 0 },
      },
      showModal: false,
      creditInput: 0,
    };
  }

  componentDidMount() {
    API.get('/client/47')
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

  accredit() {
    const body = {
      id: 47,
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
            <Button variant="primary" onClick={() => this.accredit()}>
              {t('Accredit')}
            </Button>
          </Modal.Footer>
        </Modal>
      </div>
    );
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <Navbar className="all-navbar" fixed="top">
          <Container className="container-navbar">
            <Row className="row-navbar">
              <Col className="logo-col">
                <Navbar.Brand href="/">
                  <img src="https://fontmeme.com/permalink/191102/03a545ac680d1396fcfae624d4ee0c3a.png" width="250" alt="netflix-font" border="0" className="pointerImg" role="presentation" />
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
