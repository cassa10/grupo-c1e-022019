/* eslint-disable import/no-named-as-default */
/* eslint-disable import/no-named-as-default-member */
/* eslint-disable react/jsx-props-no-spreading */
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'open-iconic/font/css/open-iconic-bootstrap.min.css';
import '../dist/css/App.css';
import React, { Suspense } from 'react';
import { Switch, Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import PrivateRouteNavs from './PrivateRoutesComponents/PrivateRouteNavs';
import Home from './Home';
import NavBar from './NavBar';
import NavBarProvider from './NavBarProvider';
import CreateMenu from './CreateMenu';
import LogIn from './LogIn';
import SearchResult from './SearchResult';
import ErrorPage from './ErrorPage';
import EditMenu from './EditMenu';
import ProviderHome from './ProviderHome';
import SideBarClient from './SideBarClient';
import SideBarProvider from './SideBarProvider';
import ScheduleTasks from './ScheduleTasks';
import SignUpProvider from './SignUpProvider';
import Profile from './Profile';
import ProviderProfile from './ProviderProfile';
import MapViendasYa from './MapViendasYa';


export default class App extends React.Component {
  render() {
    return (
      <Suspense fallback={<div />}>
        <BrowserRouter>
          <Switch>
            <Route exact path="/" render={(props) => <LogIn {...props} />} />
<<<<<<< HEAD
            <PrivateRouteNavs exact path="/home" navbar={NavBar} sidebar={SideBar} component={Home} />
            <PrivateRouteNavs exact path="/search" navbar={NavBar} sidebar={SideBar} component={SearchResult} />
            <PrivateRouteNavs exact path="/profile" navbar={NavBar} sidebar={SideBar} component={Profile} />
            <Route exact path="/provider/signup" navbar={NavBar} sidebar={SideBar} component={SignUpProvider} />

            <PrivateRouteNavs exact path="/provider" navbar={NavBar} sidebar={SideBar} component={ProviderHome} />

=======
            {
            // Client Routes
            }
            <PrivateRouteNavs exact path="/home" navbar={NavBar} sidebar={SideBarClient} component={Home} />
            <PrivateRouteNavs exact path="/search" navbar={NavBar} sidebar={SideBarClient} component={SearchResult} />
            <PrivateRouteNavs exact path="/profile" navbar={NavBar} sidebar={SideBarClient} component={Profile} />
            <PrivateRouteNavs exact path="/provider/signup" navbar={NavBar} sidebar={SideBarClient} component={SignUpProvider} />
            {
            // Provider Routes
            }
            <PrivateRouteNavs exact path="/provider" navbar={NavBarProvider} sidebar={SideBarProvider} component={ProviderHome} />
            <PrivateRouteNavs exact path="/provider/profile" navbar={NavBarProvider} sidebar={SideBarProvider} component={ProviderProfile} />
>>>>>>> 07e778ee107a4994d883f23dae98eaa56cb247a7
            <Route exact path="/create_menu" render={(props) => <CreateMenu {...props} />} />
            <Route exact path="/edit_menu" render={(props) => <EditMenu {...props} />} />

            <Route exact path="/map" render={(props) => <MapViendasYa {...props} />} />
            <Route exact path="/schedule/tasks" render={(props) => <ScheduleTasks {...props} />} />
            <Route path="/" render={(props) => <ErrorPage {...props} />} />
          </Switch>
        </BrowserRouter>
      </Suspense>
    );
  }
}
