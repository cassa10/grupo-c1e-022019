import React from 'react';
import API from '../service/api';
import '../dist/css/Home.css';

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

  goToMap(){
    this.props.history.push({
      pathname: '/map'
    });
  }

  render() {
    return (
      <div className="container">
        <div className="row">
          <div className="col-12 titulo-banner">
            <h4 className="titulo-materias-divider">
              INICIO: {this.state.tmp}
            </h4>
          </div>
          <div className="row">
          <button type="button" className="btn btn-primary" onClick={()=> this.goToMap()}>Go to map</button>
          </div>
        </div>
      </div>
    );
  }
}
export default Home;
