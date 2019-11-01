import React, { Suspense } from 'react';
import API from '../service/api';
import '../dist/css/Home.css';
import NavBar from './NavBar';


class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tmp: '',
    };
  }

  componentDidMount() {
    this.useAPI();
  }

  useAPI() {
    API.get('/')
      .then((response) => this.setState({ tmp: response }))
      .catch((error) => error);
  }

  goToMap() {
    this.props.history.push({
      pathname: '/map',
    });
  }

  render() {
    return (
      <Suspense fallback={<div>loading...</div>}>
        <div className="container">
          <NavBar />
          <div className="row">
            <div className="col-12 titulo-banner">
              <h4 className="titulo-materias-divider">
              INICIO: {this.state.tmp}
              </h4>
            </div>
            <div className="row">
              <button type="button" className="btn btn-primary" onClick={() => this.goToMap()}>Go to map</button>
            </div>
          </div>
        </div>
      </Suspense>
    );
  }
}
export default (Home);
