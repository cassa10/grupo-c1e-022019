import React from 'react';
import { withTranslation } from 'react-i18next';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import ChangeLanguage from './ChangeLanguage';
import '../dist/css/Navbar.css';

class NavBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      credit: '100,00',
    };
  }

  getClientBalance() {
    return this.state.credit;
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <Navbar className="all-navbar" fixed="top">
          <Container>
            <Row>
              <Navbar.Brand href="/">
                <img src="https://fontmeme.com/permalink/191102/03a545ac680d1396fcfae624d4ee0c3a.png" width="250" alt="netflix-font" border="0" className="pointerImg" role="presentation" />
              </Navbar.Brand>
            </Row>
            <Row>
              <div className="balance">
                {t('Balance')}: ${this.getClientBalance()}
              </div>
              <ChangeLanguage />
            </Row>
          </Container>
        </Navbar>
      </div>
    );
  }
}

export default withTranslation()(NavBar);
