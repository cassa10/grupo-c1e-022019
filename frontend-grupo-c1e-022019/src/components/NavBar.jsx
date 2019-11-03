import Button from 'react-bootstrap/Button';
import React from 'react';
import { withTranslation } from 'react-i18next';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import ChangeLenguage from './ChangeLenguage';
import '../dist/css/Navbar.css';

class NavBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  goToHome(){

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
            <form>
              <label>
                <input type="text" name="name" />
              </label>
              <Button className="searchButton" variant="primary">{t('Search text')} </Button>
            </form>
          </Row>
          <Row>
            <ChangeLenguage />
          </Row>
        </Container>
      </Navbar>
      </div>
    );
  }
}

export default withTranslation()(NavBar);