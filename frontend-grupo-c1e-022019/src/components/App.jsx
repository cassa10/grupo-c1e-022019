import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'open-iconic/font/css/open-iconic-bootstrap.min.css';
import React, { Suspense } from 'react';
import { Switch, Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import Home from './Home';
import MapViendasYa from './MapViendasYa';
import SignUpProvider from './SignUpProvider';
import NavBar from './NavBar';

export default class App extends React.Component {
  render() {
    return (
      <Suspense fallback={<div>Loading</div>}>
        <BrowserRouter>
          <Switch>
            <Route exact path="/" render={(props) => <div><NavBar /><Home {...props} /> </div>} />
            <Route exact path="/map" render={() => <MapViendasYa />} />
            <Route exact path="/signup_provider" render={(props) => <div><NavBar /><SignUpProvider {...props}/></div>} />
          </Switch>
        </BrowserRouter>
      </Suspense>
    );
  }
}
