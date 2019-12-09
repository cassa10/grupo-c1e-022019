/* eslint-disable react/jsx-props-no-spreading */
import React, { createRef } from 'react'
import {
  Map, Marker, Popup, TileLayer, withLeaflet,
} from 'react-leaflet';
import MeasureControlDefault from 'react-leaflet-measure';

const MeasureControl = withLeaflet(MeasureControlDefault);

class MapViendasYa extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
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

  render() {
    const marker = this.state.hasLocation ? (
      <Marker position={this.state.latlng}>
        <Popup>You are here</Popup>
      </Marker>
    ) : null
    return (
      <div>
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
          draggable={false}
          position={[this.props.fixCoord[0],this.props.fixCoord[1]]}
          ref={this.refmarker}>
          <Popup minWidth={90}>
            <span onClick={this.toggleDraggable}>
              {this.state.draggable ? 'Draggable' : 'Fixed'}
            </span>
          </Popup>
        </Marker>
          <MeasureControl {...this.state.measureOptions.position} />
        </Map>
        <button onClick={() => this.useMyLocation()}>Use my Location</button>
      </div>
    );
  }
}
export default MapViendasYa;
