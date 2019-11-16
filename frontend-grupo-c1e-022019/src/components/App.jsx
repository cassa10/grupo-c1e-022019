/* eslint-disable react/jsx-props-no-spreading */
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'open-iconic/font/css/open-iconic-bootstrap.min.css';
import '../dist/css/App.css';
import React, { Suspense } from 'react';
import { Switch, Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import Home from './Home';
import MapViendasYa from './MapViendasYa';
import SignUpProvider from './SignUpProvider';
import NavBar from './NavBar';
import CreateMenu from './CreateMenu';
import LogIn from './LogIn';
import SearchResult from './SearchResult';
import ErrorPage from './ErrorPage';

export default class App extends React.Component {
  render() {
    return (
      <Suspense fallback={<div />}>
        <BrowserRouter>
          <Switch>
            <Route exact path="/" render={() => <LogIn />} />
            <Route exact path="/home" render={(props) => <div><NavBar {...props} /><Home {...props} /> </div>} />
            <Route exact path="/map" render={() => <MapViendasYa />} />
            <Route exact path="/create_menu" render={(props) => <CreateMenu {...props} />} />
            <Route exact path="/search" render={(props) => <div><NavBar {...props} /><SearchResult {...props} /></div>} />
            <Route exact path="/signup_provider" render={(props) => <div><NavBar /><SignUpProvider {...props} /></div>} />
            <Route path="/" render={(props) => <ErrorPage {...props} />} />
          </Switch>
        </BrowserRouter>
      </Suspense>
    );
  }
}
