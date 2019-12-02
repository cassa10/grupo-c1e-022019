/* eslint-disable react/jsx-props-no-spreading */
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'open-iconic/font/css/open-iconic-bootstrap.min.css';
import '../dist/css/App.css';
import React, { Suspense } from 'react';
import { Switch, Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import PrivateRouteNavbar from './PrivateRoutesComponents/PrivateRouteNavbar';
import Home from './Home';
import SignUpProvider from './SignUpProvider';
import NavBar from './NavBar';
import CreateMenu from './CreateMenu';
import LogIn from './LogIn';
import SearchResult from './SearchResult';
import ErrorPage from './ErrorPage';
// import MapViendasYa from './MapViendasYa';
import ProviderProfile from './ProviderProfile';
import SideBar from './SideBar';


export default class App extends React.Component {
  render() {
    return (
      <Suspense fallback={<div />}>
        <BrowserRouter>
          <Switch>
            <Route exact path="/" render={(props) => <LogIn {...props} />} />
            <PrivateRouteNavbar exact path="/home" navbar={NavBar} component={Home} />
            {
              // (Cambiar este Route cuando tengamos el Navbar del provider y
              // una forma de llegar al create menu desde la ui.)
              // eslint-disable-next-line max-len
              // <PrivateRouteNavbar exact path="/create_menu" navbar={NavBarProvider} component={CreateMenu} />
            }
            <Route exact path="/create_menu" render={(props) => <CreateMenu {...props} />} />
            <Route exact path="/provider_profile" render={(props) => <div> <SideBar {...props} /> <ProviderProfile {...props} /> </div>} />
            <PrivateRouteNavbar exact path="/search" navbar={NavBar} component={SearchResult} />
            <PrivateRouteNavbar exact path="/signup_provider" navbar={NavBar} component={SignUpProvider} />
            <Route path="/" render={(props) => <ErrorPage {...props} />} />
          </Switch>
        </BrowserRouter>
      </Suspense>
    );
  }
}
