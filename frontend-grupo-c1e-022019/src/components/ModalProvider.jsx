/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import '../dist/css/SearchResult.css';
import {
  Map, Marker, Popup, TileLayer, withLeaflet,
} from 'react-leaflet';
import {
  Modal, Button, Container, Row, Col,
} from 'react-bootstrap';
import { withTranslation } from 'react-i18next';
import MeasureControlDefault from 'react-leaflet-measure';
import StarRatingComponent from 'react-star-rating-component';
import providerInfoIcon from '../dist/icons/provider-info-icon.png';
import API from '../service/api';
import '../dist/css/ModalProvider.css';


const MeasureControl = withLeaflet(MeasureControlDefault);

class ModalProvider extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modalOpen: false,
      provider: {
        name: '',
        description: '',
        address: {
          coord: {},
          location: 'asd',
        },
        webURL: '',
        deliveryMaxDistanceInKM: 0,
        telNumber: '',
        city: '',
        rank: 0,
        logo: '',
      },
      latlng: {
        lat: -34.706667,
        lng: -58.2775,
      },
      measureOptions: {
        position: 'topright',
        primaryLengthUnit: 'meters',
        secondaryLengthUnit: 'kilometers',
        primaryAreaUnit: 'sqmeters',
        secondaryAreaUnit: 'acres',
        activeColor: '#db4a29',
        completedColor: '#9b2d14',
      },
    };
  }

  componentDidMount() {
    const body = {
      googleId: this.props.googleId,
      tokenAccess: this.props.tokenAccess,
      providerId: this.props.providerId,
    };

    API.get('/provider/public', body)
      .then((response) => this.handleResponseAPI(response))
      .catch((error) => console.log(error));
  }

  setShow(b) {
    this.setState({ modalOpen: b });
  }

  handleResponseAPI(response) {
    this.setState({ provider: response });
    if (response.address.valid) {
      const coordinates = {
        lat: response.address.coord.latitude,
        lng: response.address.coord.longitude,
      };
      this.setState({ latlng: coordinates });
    }
  }

  showStars() {
    return (
      <StarRatingComponent
        className="stars rate_provider"
        name="rate2"
        editing={false}
        starCount={5}
        value={this.state.provider.providerRank}
      />
    );
  }

  showProviderData(title, data) {
    return (
      <div>
        <Row className="info_title_data">
          <Col>
            {title}
          </Col>
        </Row>
        <Row className="info_data">
          <Col>
            {data}
          </Col>
        </Row>
      </div>
    );
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
        <Modal size="lg" show={this.state.modalOpen} onHide={handleClose}>
          <Container>
            <Modal.Header closeButton className="header_provider">
              <img className="logo_img" src={this.state.provider.logo} alt={t('logo')} /><h1>{this.state.provider.name}</h1> {this.showStars()}
            </Modal.Header>
            <Modal.Title className="description_provider">
              {`"${this.state.provider.description}"`}
            </Modal.Title>
            <Modal.Body>
              <Row>
                <Col md={3} lg={3} xl={3}>
                  {this.showProviderData(t('Max distance'), `${this.state.provider.deliveryMaxDistanceInKM} km`)}
                  {this.showProviderData(t('Telephone'), this.state.provider.telNumber)}
                  {this.showProviderData(t('Website'), this.state.provider.webURL)}
                  {this.showProviderData(t('Address'), `${this.state.provider.address.location}, ${this.state.provider.city}`)}

                </Col>
                <Col>
                  <Map
                    center={this.state.latlng}
                    zoom={16}
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
                    <MeasureControl {...this.state.measureOptions.position} />
                  </Map>
                </Col>
              </Row>
            </Modal.Body>
          </Container>
        </Modal>
      </div>
    );
  }
}

export default withTranslation()(ModalProvider);
