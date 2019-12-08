import React from 'react';
import { withTranslation } from 'react-i18next';
import SideNav, {
  NavItem, NavIcon, NavText,
} from '@trendmicro/react-sidenav';
import Swal from 'sweetalert2';
import '@trendmicro/react-sidenav/dist/react-sidenav.css';
import '../dist/css/Sidebar.css';
import homeIcon from '../dist/icons/home-icon-sidebar.png';
import walletIcon from '../dist/icons/wallet-icon-sidebar.png';
import switchUserIcon from '../dist/icons/switching-user-sidebar.png';
import logoutIcon from '../dist/icons/logout-icon-sidebar.png';
import formatCredit from './formatter/formatCredit.js';
import API from '../service/api';

class SideBarClient extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      existAsProvider: false,
      googleId: '',
      tokenAccess: '',
      user: {
        id: 0,
        credit: {
          amount: 0,
        },
        typeClient: false,
        typeProvider: false,
      },
      imgProfile: 'https://icon-library.net/images/user-icon-image/user-icon-image-20.jpg',
    };
  }

  componentDidMount() {
    const bodyRequest = {
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
    };

    this.setState({
      googleId: bodyRequest.googleId,
      tokenAccess: bodyRequest.tokenAccess,
    });

    API.get('/client', bodyRequest)
      .then((response) => this.handleGetClient(response))
      .catch((error) => console.log(error));
    // Lo necesito para saber si tiene que ir al form o al home provider:
    API.get('/exist_provider', bodyRequest)
      .then((response) => this.setState({ existAsProvider: response }))
      .catch((error) => console.log(error));
  }

  handleGetClient(response) {
    this.setState({ user: response });

    if (response.imageUrl) {
      this.setState({ imgProfile: response.imageUrl });
    }
  }

  showAppropiateCredit(t) {
    return (formatCredit(t, this.state.user.credit.amount));
  }

  showAppropiateSwitchText(t) {
    if (this.state.existAsProvider) {
      return (t('Switch Provider'));
    }
    return (t('Make me Provider'));
  }

  handleSelectedOption(t, selected) {
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
      this.handleLogoutSelected(t);
    }
  }

  handleHomeSelected() {
    window.scrollTo(0, 0);
    this.props.history.push({
      pathname: '/home',
      state: {
        googleId: this.props.location.state.googleId,
        tokenAccess: this.props.location.state.tokenAccess,
        user: this.state.user,
        sideBarSelected: 'home',
      },
    });
  }

  handleProfileOrBalanceSelected() {
    window.scrollTo(0, 0);
    this.props.history.push({
      pathname: '/profile',
      state: {
        googleId: this.state.googleId,
        tokenAccess: this.state.tokenAccess,
        user: this.state.user,
        sideBarSelected: 'profile',
        isClient: true,
      },
    });
  }

  goLogin() {
    window.scrollTo(0, 0);
    this.props.history.push({
      pathname: '/',
    });
  }

  handleLogout(isConfirmed) {
    if (isConfirmed) {
      API.post('/logout', {
        googleId: this.state.googleId,
        tokenAccess: this.state.tokenAccess,
      })
        .then(() => this.goLogin())
        .catch((error) => console.log(error));
    }
  }

  confirmLogoutAlert(t) {
    Swal.fire({
      title: t('Estas seguro?'),
      text: t('Mire que su sesion se va a cerrar'),
      showCancelButton: true,
      confirmButtonColor: 'Green',
      cancelButtonColor: 'Red',
      confirmButtonText: t('Ok'),
      cancelButtonText: t('Cancel'),
    })
      .then((result) => this.handleLogout(result.value))
      .catch((error) => console.log(error.response));
  }

  handleLogoutSelected(t) {
    this.confirmLogoutAlert(t);
  }

  handleSwitchSelected() {
    window.scrollTo(0, 0);
    if (this.state.existAsProvider) {
      this.props.history.push({
        pathname: '/provider',
        state: {
          googleId: this.state.googleId,
          tokenAccess: this.state.tokenAccess,
          user: this.state.user,
          sideBarSelected: 'home',
        },
      });
    } else {
      this.props.history.push({
        pathname: '/provider/signup',
        state: {
          googleId: this.state.googleId,
          tokenAccess: this.state.tokenAccess,
          user: this.state.user,
          sideBarSelected: 'switch',
        },
      });
    }
  }

  render() {
    const { t } = this.props;
    return (
      <SideNav
        className="sidebar"
        onSelect={(selected) => {
          this.handleSelectedOption(t, selected);
        }}
      >
        <SideNav.Toggle />
        <SideNav.Nav defaultSelected={this.props.location.state.sideBarSelected}>
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
    );
  }
}

export default withTranslation()(SideBarClient);
