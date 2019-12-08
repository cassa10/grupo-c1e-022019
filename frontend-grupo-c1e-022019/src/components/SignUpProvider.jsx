import React,{ createRef }  from 'react';
import { withTranslation } from 'react-i18next';
import '../dist/css/Sign-up-provider.css';
import {
  Map, Marker, Popup, TileLayer, withLeaflet,
} from 'react-leaflet';
import MeasureControlDefault from 'react-leaflet-measure';
import {
  Button, Container, Row, Col, 
} from 'react-bootstrap';
import Swal from 'sweetalert2';
import API from '../service/api';

const MeasureControl = withLeaflet(MeasureControlDefault);

class SignUpProvider extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      defaultProviderLogo: 'https://static1.eyellowpages.ph/uploads/yp_business/photo/15145/thumb_images.png',
      email: '',
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
      telInternational: '+549',
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
      haveToRenderise: false,
      shake: false,
    };
  }

  mapRef = createRef()

  // $FlowFixMe: ref
  refmarker = createRef()

  componentDidMount() {
    this.setState({
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
      user: this.props.location.state.user,
      sideBarSelected: this.props.location.state.sideBarSelected,
      email: this.props.location.state.user.email,
    });
  }
  isValidForm() {
    return(
      this.state.name.trim().length > 0 &&
      this.state.address.location.trim().length > 0 &&
      this.state.city.trim().length > 0 &&
      this.state.telNumber.trim().length >= 8 &&
      this.state.description.trim().length >= 30 &&
      this.state.description.trim().length <= 200 &&
      this.state.deliveryMaxDistanceInKM > 0
    );
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

  getAppropiateLogo() {
    if (this.state.logo.trim().length > 0) {
      return (this.state.logo);
    } else {
      // Return a default logo
      return (this.state.defaultProviderLogo);
    }
  }

  goToProviderHome(body, response) {
    window.scrollTo(0, 0);
    this.props.history.push({
      pathname: '/provider',
      state: {
        googleId: body.googleId,
        tokenAccess: body.tokenAccess,
        user: response,
        sideBarSelected: 'home',
      },
    });
  }

  shakeAndFeedBack(t){
    window.scrollTo(0, 0);
    this.setState({shake: true})
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: t("Please, complete all fields"),
    });
  }

  handleSignUpProvider(isConfirmed) {
    if (isConfirmed) {
      const body = {
        googleId: this.state.googleId,
        tokenAccess: this.state.tokenAccess,
        name: this.state.name,
        logo: this.getAppropiateLogo(),
        city: this.state.city,
        address: {
          coord: {
            latitude: this.state.latlng.lat,
            longitude: this.state.latlng.lng,
          },
          location: this.state.address.location,
        },
        description: this.state.description,
        webURL: this.state.webURL,
        telNumber: `${this.state.telInternational} ${this.state.telNumber}`,
        deliveryMaxDistanceInKM: this.state.deliveryMaxDistanceInKM,
        email: this.state.email,
      };
      console.log(body)
      API.post('/provider', body)
        .then((response) => this.goToProviderHome(body, response))
        .catch((error) => console.log(error));
    }
  }

  confirmSignUpProviderAlert(t) {
    if (this.isValidForm()) {
      Swal.fire({
        title: t('Estas seguro?'),
        showCancelButton: true,
        confirmButtonColor: 'Green',
        cancelButtonColor: 'Red',
        confirmButtonText: t('Ok'),
        cancelButtonText: t('Cancel'),
      })
        .then((result) => this.handleSignUpProvider(result.value))
        .catch((error) => console.log(error.response));
    } else {
      this.shakeAndFeedBack(t);
    }
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

  handlerDeliveryMaxDistanceInKM(e) {
    this.setState({ deliveryMaxDistanceInKM: e.target.value });
  }

  createButtonsOfForm(t) {
    return (
      <Row className="text-center signup-buttons">
        <Col>
          <Button
            type="button"
            className="btn btn-danger sign-up-button"
            onClick={() => this.confirmSignUpProviderAlert(t)}
          >
            {t('Sign up')}
          </Button>
          <Button
            type="button"
            className="btn btn-primary"
            onClick={() => this.cancelToSignUpProvider()}
          >
            {t('Go back')}
          </Button>
        </Col>
      </Row>
    );
  }

  cssInvalidString(text){
    if(text === '' && this.state.shake ){
      return "invalid"
    }
    return '';
  }

  cssInvalidNumber(n){
    if(n <= 0  && this.state.shake){
      return "invalid";
    }
    return '';
  }

  shake(field){
    if((this.cssInvalidNumber(field) || this.cssInvalidString(field) )&& this.state.shake ){
      return "shakeBaby"
    }
    return ""
  }

  cssInvalidDescription(description) {
    if((description.length < 30 || description.length > 200) && this.state.shake){
      return "invalid";
    }
    return '';
  }

  shakeDescription(description) {
    if (this.cssInvalidDescription(description) && this.state.shake) {
      return "shakeBaby";
    }
    return "";
  }

  createInputOfName(t) {
    return (
      <input type="text" className={`${this.shake(this.state.name)} ${this.cssInvalidString(this.state.name)} form-control input-weburl-provider`} id="inputNameProvider" placeholder={t('Name provider')} onChange={(e) => this.handlerProviderName(e)} />
    );
  }

  createInputOfAddress(t) {
    return (
      <input type="text" className={`${this.shake(this.state.address.location)} ${this.cssInvalidString(this.state.address.location)} form-control input-weburl-provider`} id="inputAddressLocationProvider" placeholder={t('Address')} onChange={(e) => this.handlerProviderAddressLocation(e)} />
    );
  }

  createInputOfCity(t) {
    return (
      <input type="text" className={`${this.shake(this.state.city)} ${this.cssInvalidString(this.state.city)} form-control input-weburl-provider`} id="inputCityProvider" placeholder={t('City')} onChange={(e) => this.handlerProviderCity(e)} />
    );
  }

  createInputOfDescription(t) {
    return (
      <textarea type="text" className={`${this.shakeDescription(this.state.description)} ${this.cssInvalidDescription(this.state.description)} form-control input-weburl-provider`} id="inputDescriptionProvider" placeholder={`${t('Description')} (min: 30, max: 200) `} onChange={(e) => this.handlerProviderDescription(e)} />
    );
  }

  createInputOfTelephoneNumber(t) {
    return (
      <input type="text" className={`${this.shake(this.state.telNumber)} ${this.cssInvalidString(this.state.telNumber)} form-control input-weburl-provider`} id="inputTelephoneProvider" placeholder={t('Telephone')} onChange={(e) => this.handlerProviderTelephoneNumber(e)} />
    );
  }

  createInputOfWebURL(t) {
    return (
      <input type="text" className={`${this.shake(this.state.webURL)} ${this.cssInvalidString(this.state.webURL)} form-control input-weburl-provider`} id="inputWebURLProvider" placeholder={t('Weburl')} onChange={(e) => this.handlerProviderWebURL(e)} />
    );
  }

  createInputLogo(t) {
    return (
      <input type="text" className={` form-control input-weburl-provider`} id="inputWebURLProvider" placeholder={`${t('Logo')} (${t('eg')}: ${this.state.defaultProviderLogo})`} onChange={(e) => this.handlerProviderLogo(e)} />
    );
  }

  createInputOfDeliveryMaxDistanceInKM(t) {
    return (
      <input type="number" className={`${this.cssInvalidNumber(this.state.deliveryMaxDistanceInKM)} form-control input-weburl-provider`} id="inputWebURLProvider" placeholder={t('Max distance of delivery in km')} onChange={(e) => this.handlerDeliveryMaxDistanceInKM(e)} />
    );
  }

  createAMarker(position, title, description) {
    return (
      <Marker position={position}>
        <Popup>{title}<br />{description}</Popup>
      </Marker>
    );
  }

  toggleDraggable = () => {
    this.setState({ draggable: !this.state.draggable })
  }

  useMyLocation(){
    const map = this.mapRef.current
    map.leafletElement.locate()
  }  

  handleLocationFound = (e) => {
    this.setState({
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
      <Col className="map">
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
      </Col>
    );
    
  }

  createImageVisualizator() {
    if (this.state.logo.trim().length > 0 && this.state.haveToRenderise) {
      return (
        <img className="logo_img" src={this.state.logo} alt="logo" />
      );
    } else {
      return (
        <img className="logo_img" src={this.state.defaultProviderLogo} alt="logo" />
      );
    }
  }

  verLogo() {
    if (this.state.logo) {
      this.setState({ haveToRenderise: true });
    }
    
  }

  render() {
    const { t } = this.props;
    return (
      <Container>
        <Row className="sign-up-provider form-signup-provider">
          <Col>
              <h2 className="text-provider-sign-up">
                {t('Fill out this form for sign up as provider')}
              </h2>
          </Col>
        </Row>
        <Row className="form-signup-provider">
          <Container>
            <Row className="rowline_form">
              <Col>
                {this.createInputOfName(t)}
              </Col>
              <Col>
                {this.createInputOfWebURL(t)}
              </Col>
            </Row>
            <Row className="rowline_form">
              <Col>
                {this.createInputOfAddress(t)}
              </Col>
              <Col>
                {this.createInputOfCity(t)}
              </Col>
            </Row>
            <Row className="rowline_form">
              <Col>
                {this.createInputOfDescription(t)}
              </Col>
            </Row>
            <Row className="rowline_form">
              <Col>
                {this.createInputOfDeliveryMaxDistanceInKM(t)}
              </Col>
              <Col>
                <input className="form-control" type="text" placeholder="International (+54 9)" disabled />
              </Col>
              <Col> 
                {this.createInputOfTelephoneNumber(t)}
              </Col>
            </Row>
            <Row className="text-center rowline_form">
              <Col xs={2} sm={3} md={3} lg={2} xl={2}>
                <Button onClick={() => this.verLogo()}>{t('Ver logo')}</Button>
              </Col>

              {this.createImageVisualizator(t)}

              <Col>
                {this.createInputLogo(t)}
              </Col>

            </Row>
            <Row>
              <Col className="text-center ubicacion_title">
                <h3>{t('Ubicacion')}</h3>
              </Col>
            </Row>
            <Row>
              {this.createInputFromMap(t)}
            </Row>
            <Row>
              <Col className="text-center">
                <Button onClick={() => this.useMyLocation()}>{t('Use my location')}</Button>
              </Col>
            </Row>
            {this.createButtonsOfForm(t)}
            </Container>
        </Row>
    </Container>
    );
  }
}

export default withTranslation()(SignUpProvider);
