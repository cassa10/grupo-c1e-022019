import React,{ createRef }  from 'react';
import { withTranslation } from 'react-i18next';
import '../dist/css/Sign-up-provider.css';
import {
  Map, Marker, Popup, TileLayer, withLeaflet,
} from 'react-leaflet';
import MeasureControlDefault from 'react-leaflet-measure';
import { Button } from 'react-bootstrap';

const MeasureControl = withLeaflet(MeasureControlDefault);

class SignUpProvider extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      googleId: '',
      tokenAccess: '',
      user: {},
      sideBarSelected: 'switch',
      name: '',
      logo: '',
      city: '',
      address: {
        coord: {
          latitude: 0.0,
          longitude: 0.0,
        },
        location: '',
      },
      description: '',
      webURL: '',
      email: '',
      telNumber: '',
      deliveryMaxDistanceInKM: 0.0,
      schedule: {
        daysAndHours: {},
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
      latlng: {
        lat: -34.706667,
        lng: -58.2775,
      },
      draggable: true,
    };
  }

  componentDidMount() {
    this.setState({
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
      user: this.props.location.state.user,
      sideBarSelected: this.props.location.state.sideBarSelected,
      email: this.props.location.state.user.email,
    });
  }

  cancelToSignUpProvider() {
    window.scrollTo(0, 0);
    this.props.history.push({
      pathname: '/home',
      state: {
        googleId: this.state.googleId,
        tokenAccess: this.state.tokenAccess,
        user: this.state.user,
        sideBarSelected: 'home',
      },
    });
  }

  signUpProvider() {
    console.log('DO NOT IMPLEMENTED');
  }

  handlerProviderName(e) {
    this.setState({ name: e.target.value });
  }

  handlerProviderLogo(e) {
    this.setState({ logo: e.target.value });
  }

  handlerProviderCity(e) {
    this.setState({ city: e.target.value });
  }

  handlerProviderDescription(e) {
    this.setState({ description: e.target.value });
  }

  handlerProviderAddressLocation(e) {
    this.setState({ address: { coord: this.state.address.coord, location: e.target.value } });
  }

  handlerProviderTelephoneNumber(e) {
    this.setState({ telNumber: e.target.value });
  }

  handlerProviderWebURL(e) {
    this.setState({ webURL: e.target.value });
  }

  createButtonsOfForm(t) {
    return (
      <div className="col-12 signup-buttons">
        <button
          type="button"
          className="btn btn-danger sign-up-button"
          onClick={() => this.signUpProvider()}
        >
          {t('Sign up')}
        </button>
        <button
          type="button"
          className="btn btn-primary"
          onClick={() => this.cancelToSignUpProvider()}
        >
          {t('Go back')}
        </button>
      </div>
    );
  }

  createInputOfName(t) {
    return (
      <input type="text" className="form-control input-name-provider" id="inputNameProvider" placeholder={t('Name provider')} onChange={(e) => this.handlerProviderName(e)} />
    );
  }

  createInputOfAddress(t) {
    return (
      <input type="text" className="form-control input-address-location-provider" id="inputAddressLocationProvider" placeholder={t('Address')} onChange={(e) => this.handlerProviderAddressLocation(e)} />
    );
  }

  createInputOfCity(t) {
    return (
      <input type="text" className="form-control input-city-provider" id="inputCityProvider" placeholder={t('City')} onChange={(e) => this.handlerProviderCity(e)} />
    );
  }

  createInputOfDescription(t) {
    return (
      <input type="text" className="form-control input-description-provider" id="inputDescriptionProvider" placeholder={t('Description')} onChange={(e) => this.handlerProviderDescription(e)} />
    );
  }

  createInputOfTelephoneNumber(t) {
    return (
      <input type="text" className="form-control input-tel-number-provider" id="inputTelephoneProvider" placeholder={t('Telephone')} onChange={(e) => this.handlerProviderTelephoneNumber(e)} />
    );
  }

  createAMarker(position, title, description) {
    return (
      <Marker position={position}>
        <Popup>{title}<br />{description}</Popup>
      </Marker>
    );
  }

    mapRef = createRef()

    // $FlowFixMe: ref
    refmarker = createRef()

  toggleDraggable = () => {
    this.setState({ draggable: !this.state.draggable })
  }

  useMyLocation(){
    const map = this.mapRef.current
    map.leafletElement.locate()
  }  

  handleLocationFound = (e) => {
    this.setState({
      hasLocation: true,
      latlng: e.latlng,
    })
  }

  updatePosition = () => {
    const marker = this.refmarker.current
    if (marker != null) {
      this.setState({
        latlng: marker.leafletElement.getLatLng(),
      })
    }
  }

  createInputFromMap(t) {
    const marker = this.state.hasLocation ? (
      <Marker position={this.state.latlng}>
        <Popup>{t('You are here')}</Popup>
      </Marker>
    ) : null
    return (
      <div className="col-12 map">
      <h3>{t('Ubicacion')}</h3>
        <Map 
        center={this.state.latlng} 
        zoom={15}
        onClick={this.handleClick}
        onLocationfound={this.handleLocationFound}
        ref={this.mapRef}
        >
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
          />
          {marker}
          <Marker
          draggable={this.state.draggable}
          onDragend={this.updatePosition}
          position={this.state.latlng}
          ref={this.refmarker}>
          <Popup minWidth={90}>
            <span onClick={this.toggleDraggable}>
              {this.state.draggable ? 'Draggable' : 'Fixed'}
            </span>
          </Popup>
        </Marker>
          <MeasureControl {...this.state.measureOptions.position} />
        </Map>
        <Button onClick={() => this.useMyLocation()}>{t('Use my location')}</Button>
      </div>
    );
    
  }

  render() {
    const { t } = this.props;
    return (
      <div className="container">
        <div className="row">
          <div className="col-12 sign-up-provider form-signup-provider">
            <h2 className="text-provider-sign-up">
              {t('Fill out this form for sign up as provider')}
            </h2>
          </div>
          <form className="form-inline form-signup-provider">
            {this.createInputOfName(t)}
            {this.createInputOfAddress(t)}
            {this.createInputOfCity(t)}
            {this.createInputOfTelephoneNumber(t)}
            {this.createInputOfDescription(t)}
            {this.createInputFromMap(t)}
            {this.createButtonsOfForm(t)}
          </form>
        </div>
      </div>
    );
  }
}

export default withTranslation()(SignUpProvider);
