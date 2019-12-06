import React from 'react';
import { withTranslation } from 'react-i18next';
import SideNav, {
  NavItem, NavIcon, NavText,
} from '@trendmicro/react-sidenav';
import '@trendmicro/react-sidenav/dist/react-sidenav.css';
import '../dist/css/Sidebar.css';
// Delete this when integrate component and navbar in render
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import ChangeLanguage from './ChangeLanguage';
import '../dist/css/Navbar.css';
// ------------------------------------
import homeIcon from '../dist/icons/home-icon-sidebar.png';
import walletIcon from '../dist/icons/wallet-icon-sidebar.png';
import switchUserIcon from '../dist/icons/switching-user-sidebar.png';
import logoutIcon from '../dist/icons/logout-icon-sidebar.png';
import formatCredit from './formatter/formatCredit.js';

class SideBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      existAsProvider: true,
      googleId: '',
      tokenAccess: '',
      user: {
        id: 0,
        credit: {
          amount: 0,
        },
        typeClient: false,
        typeProvider: true,
      },
      prevSelected: 'home',
      imgProfile: 'https://icon-library.net/images/user-icon-image/user-icon-image-20.jpg',
    };
  }

  componentDidMount() {
    /*
      const bodyRequest = {
        googleId: this.props.location.state.googleId,
        tokenAccess: this.props.location.state.tokenAccess,
      };

      this.setState({ googleId: bodyRequest.googleId, tokenAccess: bodyRequest.tokenAccess });

    if (this.props.location.state.user.typeClient) {

      API.get('/client', bodyRequest)
        .then((response) => this.handleGetClient(response))
        .catch((error) => console.log(error));

      // Lo necesito para saber si tiene que ir al form o al home provider:
      API.get('/exist_provider', bodyRequest)
        .then((response) => this.setState({ existAsProvider: response }))
        .catch((error) => console.log(error));
    }

    if (this.props.location.state.user.typeProvider) {

      API.get('/provider', bodyRequest)
        .then((response) => this.handleGetProvider(response))
        .catch((error) => console.log(error));

    }
    */
  }

  handleGetClient(response) {
    this.setState({ user: response });

    if (response.imageUrl) {
      this.setState({ imgProfile: response.imageUrl });
    }
  }

  handleGetProvider(response) {
    this.setState({ user: response });

    if (response.logo) {
      this.setState({ imgProfile: response.logo });
    }
  }

  showAppropiateCredit(t) {
    return (formatCredit(t, this.state.user.credit.amount));
  }

  showAppropiateSwitchText(t) {
    if (this.state.user.typeClient) {
      if (this.state.existAsProvider) {
        return (t('Switch Provider'));
      }
      return (t('Make me Provider'));
    }

    return (t('Switch Client'));
  }

  handleSelectedOption(selected) {
    // selected = 'home' | 'profile' | 'balance' | 'switch' | 'logout'
    if (selected === 'home') {
      this.handleHomeSelected();
    }

    if (selected === 'profile' || selected === 'balance') {
      this.handleProfileOrBalanceSelected();
    }

    if (selected === 'switch') {
      this.handleSwitchSelected();
    }

    if (selected === 'logout') {
      this.handleLogoutSelected();
    }
  }

  handleHomeSelected() {
    if (this.state.user.typeClient) {
      /* (HOME client)
        this.props.history.push({
          pathname: '/home',
          state: {
            googleId: this.props.location.state.googleId,
            tokenAccess: this.props.location.state.tokenAccess,
          },
        });
      */
      console.log('Go to home client');
    }
    if (this.state.user.typeProvider) {
      /* (HOME provider)
        this.props.history.push({
          pathname: '/provider',
          state: {
            googleId: this.state.googleId,
            tokenAccess: this.state.tokenAccess,
          },
        });
      */
      console.log('Go to home provider');
    }
  }

  handleProfileOrBalanceSelected() {
    if (this.state.user.typeClient) {
      /* (HOME client)
        this.props.history.push({
          pathname: '/client/profile',
          state: {
            googleId: this.state.googleId,
            tokenAccess: this.state.tokenAccess,
            clientId: this.state.user.id
          },
        });
      */
      console.log('Go to profile client');
    }
    if (this.state.user.typeProvider) {
      /* (HOME provider)
        this.props.history.push({
          pathname: '/provider/profile',
          state: {
            googleId: this.state.googleId,
            tokenAccess: this.state.tokenAccess,
            providerId: this.state.user.id
          },
        });
      */
      console.log('Go to profile provider');
    }
  }

  handleLogoutSelected() {
    /*
    API.post('/logout', {googleId: this.state.googleId, tokenAccess: this.state.tokenAccess})
        .then(() => this.handleLogout())
        .catch((error) => console.log(error));
    */
    console.log('Handle logout');
  }

  handleSwitchSelected() {
    if (this.state.user.typeClient) {
      if (this.state.existAsProvider) {
        /* (HOME provider)
        this.props.history.push({
          pathname: '/provider',
          state: {
            googleId: this.state.googleId,
            tokenAccess: this.state.tokenAccess,
          },
        });
        */
        console.log('Go to home provider');
      } else {
        /* (FORM create provider)
        this.props.history.push({
          pathname: '/provider/signup',
          state: {
            googleId: this.state.googleId,
            tokenAccess: this.state.tokenAccess,
          },
        });
        */
        console.log('Go to form sign up provider');
      }
    }
    if (this.state.user.typeProvider) {
      /* (HOME client)
        this.props.history.push({
          pathname: '/home',
          state: {
            googleId: this.state.googleId,
            tokenAccess: this.state.tokenAccess,
          },
        });
      */
      console.log('Go to home client');
    }
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <SideNav
          className="sidebar"
          onSelect={(selected) => {
            this.handleSelectedOption(selected);
          }}
        >
          <SideNav.Toggle />
          <SideNav.Nav defaultSelected={this.state.prevSelected}>
            <NavItem eventKey="home">
              <NavIcon>
                <img src={homeIcon} alt="home" width="30" height="30" />
              </NavIcon>
              <NavText>
                <div className="sidenavtext">
                  {t('Home')}
                </div>
              </NavText>
            </NavItem>
            <NavItem eventKey="profile">
              <NavIcon>
                <img src={this.state.imgProfile} alt="profile" width="30" height="30" />
              </NavIcon>
              <NavText>
                <div className="sidenavtext">
                  {t('Profile')}
                </div>
              </NavText>
            </NavItem>
            <NavItem eventKey="balance">
              <NavIcon>
                <img src={walletIcon} alt="wallet" width="30" height="30" />
              </NavIcon>
              <NavText>
                <div className="sidenavtext">
                  {this.showAppropiateCredit(t)}
                </div>
              </NavText>
            </NavItem>
            <NavItem eventKey="switch">
              <NavIcon>
                <img src={switchUserIcon} alt="switch" width="30" height="30" />
              </NavIcon>
              <NavText>
                <div className="sidenavtext">
                  {this.showAppropiateSwitchText(t)}
                </div>
              </NavText>
            </NavItem>
            <NavItem eventKey="logout">
              <NavIcon>
                <img src={logoutIcon} alt="logout" width="30" height="30" />
              </NavIcon>
              <NavText>
                <div className="sidenavtext">
                  {t('Log out')}
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
        {
          // ---------------------------------------
        }
      </div>
    );
  }
}

export default withTranslation()(SideBar);
