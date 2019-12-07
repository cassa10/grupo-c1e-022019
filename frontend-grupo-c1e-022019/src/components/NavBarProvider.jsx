import React from 'react';
import { withTranslation } from 'react-i18next';
import {
  Navbar, Nav,
} from 'react-bootstrap';
import ChangeLanguage from './ChangeLanguage';
import '../dist/css/Navbar.css';

class NavBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      googleId: '',
      tokenAccess: '',
      user: {},
    };
  }

  componentDidMount() {
    const bodyRequest = {
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
    };

    this.setState({ googleId: bodyRequest.googleId, tokenAccess: bodyRequest.tokenAccess });
  }

  goBackProviderHome() {
    this.props.history.push({
      pathname: '/provider',
      state: {
        googleId: this.props.location.state.googleId,
        tokenAccess: this.props.location.state.tokenAccess,
        user: this.props.location.state.user,
        sideBarSelected: 'home',
      },
    });
  }

  render() {
    return (
      <Navbar className="all-navbar" fixed="top">
        <Navbar.Brand className="homelogoimg">
          <img src="https://fontmeme.com/permalink/191206/ae6df82115063423c0205aaf7499bff2.png" width="250" onClick={() => this.goBackProviderHome()} alt="viendas-font" border="0" className="pointerImg" role="presentation" />
        </Navbar.Brand>
        <Navbar.Toggle />
        <Nav className="mr-auto">
          <div className="navbar_changelanguague">
            <ChangeLanguage />
          </div>
        </Nav>
      </Navbar>
    );
  }
}

export default withTranslation()(NavBar);
