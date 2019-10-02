import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'open-iconic/font/css/open-iconic-bootstrap.min.css';
import React from 'react';
import { Switch, Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import Home from './Home';
import MapViendasYa from './MapViendasYa';

export default class App extends React.Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route exact path="/" render={props => <div><Home {...props} /> </div>} />
          <Route exact path="/map" render={() => <MapViendasYa />} />
        </Switch>
      </BrowserRouter>
    );
  }
}
