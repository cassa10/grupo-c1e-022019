import React from 'react';
import { withTranslation } from 'react-i18next';
import SideNav, {
  NavItem, NavIcon, NavText,
} from '@trendmicro/react-sidenav';
import '@trendmicro/react-sidenav/dist/react-sidenav.css';
import '../dist/css/Sidebar.css';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import ChangeLanguage from './ChangeLanguage';
import '../dist/css/Navbar.css';

class SideBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      googleId: '',
      tokenAccess: '',
      prevSelected: 'profile',
      img: 'https://lh3.googleusercontent.com/-nT1rvOtwKPY/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rdE4aM8n_lDyHypOFK4JzNctllhow.CMID/s48-c/photo.jpg',
    };
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <SideNav
          className="sidebar"
          onSelect={(selected) => {
            console.log(selected);
          }}
        >
          <SideNav.Toggle />
          <SideNav.Nav defaultSelected={this.state.prevSelected}>
            <NavItem eventKey="profile">
              <NavIcon>
                <img src={this.state.img} alt="img-perfil" width="30" height="30" />
              </NavIcon>
              <NavText>
                <div className="sidenavtext">
                  {t('Profile')}
                </div>
              </NavText>
            </NavItem>
            <NavItem eventKey="balance">
              <NavIcon>
                {t('Balance')}
              </NavIcon>
              <NavText>
                <div className="sidenavtext">
                  $300.00
                </div>
              </NavText>
            </NavItem>
            <NavItem eventKey="clientSection">
              <NavIcon>
                {t('Client')}
              </NavIcon>
              <NavText>
                <div className="sidenavtext">
                  {t('Switch Client')}
                </div>
              </NavText>
            </NavItem>
          </SideNav.Nav>
        </SideNav>
        {
          // DELETE THIS WHEN INTEGRATE WITH NAVBAR
        }
        <Navbar className="all-navbar" fixed="top">
          <Container className="container-navbar" fluid>
            <Row className="row-navbar">
              <Col className="logo-col">
                <Navbar.Brand className="homelogoimg">
                  <img src="https://fontmeme.com/permalink/191102/03a545ac680d1396fcfae624d4ee0c3a.png" width="250" onClick={() => console.log('GO HOME')} alt="netflix-font" border="0" className="pointerImg" role="presentation" />
                </Navbar.Brand>
              </Col>
              <Col xs={1} sm={1} md={4} lg={5} xl={8} />
              <Col>
                <ChangeLanguage />
              </Col>
            </Row>
          </Container>
        </Navbar>
      </div>
    );
  }
}

export default withTranslation()(SideBar);
