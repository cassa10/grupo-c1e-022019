import React from 'react';
import API from '../service/api';
import '../dist/css/Home.css';
import MapViendasYa from './MapViendasYa';

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
      .then(response => this.setState({ tmp: response }))
      .catch(error => error);
  }

  render() {
    return (
      <div className="container">
        <div className="row">
          <div className="col-12 titulo-banner">
            <h4 className="titulo-materias-divider">
              INICIO: {this.state.tmp}
            </h4>
              <MapViendasYa /> 
          </div>
        </div>
      </div>
    );
  }
}
export default Home;
