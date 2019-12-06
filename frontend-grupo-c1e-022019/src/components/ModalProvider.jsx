import React from 'react';
import '../dist/css/SearchResult.css';
import {
  Map, Marker, Popup, TileLayer, withLeaflet,
} from 'react-leaflet';
import {
  Card, Modal, Button,
} from 'react-bootstrap';
import { withTranslation } from 'react-i18next';
import providerInfoIcon from '../dist/icons/provider-info-icon.png';
import API from '../service/api';

class ModalProvider extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modalOpen: false,
      pictures: [
        'https://www.seriouseats.com/recipes/images/2015/07/20150728-homemade-whopper-food-lab-35-1500x1125.jpg',
      ],
      provider: {
        name: '',
        description: '',
        address: '',
        webURL: '',
        deliveryMaxDistanceInKM: 0,
        rank: 0,
      },
      latlng: {
        lat: -34.706667,
        lng: -58.2775,
      },
    };
  }

  componentDidMount() {
    const body = {
      googleId: 'FAKEID1',
      tokenAccess: 'FAKEACCESSTOKEN1',
      providerId: 1,
    };
    API.get('/provider/public', body)
      .then((data) => this.setState({ provider: data }))
      .catch((error) => console.log(error));
  }

  setShow(b) {
    this.setState({ modalOpen: b });
  }

  render() {
    const { t } = this.props;
    const handleClose = () => this.setShow(false);
    const handleShow = () => this.setShow(true);
    return (
      <div>
        <Button className="buy-button" variant="success" onClick={handleShow}>
          <img src={providerInfoIcon} alt="menu-info" />
        </Button>
        <Modal show={this.state.modalOpen} onHide={handleClose}>
          <Modal.Header closeButton>
            <h1>{this.state.provider.name}</h1>
          </Modal.Header>
          <Modal.Title>{this.state.provider.description}</Modal.Title>
          <Modal.Body>
            <Card.Img className="card_img" variant="left" src={this.state.pictures[0]} /><br />
            <Map
              center={this.state.latlng}
              zoom={15}
            >
              <TileLayer
                attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
              />

              <Marker position={this.state.latlng}>
                <Popup>{this.state.provider.name}
                  <br />
                  {this.state.provider.description}
                </Popup>
              </Marker>
            </Map>
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default withTranslation()(ModalProvider);
