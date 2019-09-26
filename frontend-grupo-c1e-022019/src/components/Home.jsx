import React from 'react';
import API from '../service/api';
import '../dist/css/Home.css';
import Map from './Map';

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  useAPI() {
  // EJEMPLO DE UTILIZACION DE LA API AXIOS
  // BORRAR CUANDO SE UTILICE LA API
    API.get(`/materias/${this.props.location.state.username}`)
      .then(response => response)
      .catch(error => error);
  }

  render() {
    return (
      <div className="container">
        <div className="row">
          <div className="col-12 titulo-banner">
            <h4 className="titulo-materias-divider">
              INICIO
            </h4>
            <Map />
          </div>
        </div>
      </div>
    );
  }
}
export default Home;
