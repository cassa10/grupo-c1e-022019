/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
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
      centerCoordinates: [-34.706667, -58.2775],
    };
  }

  createAMarker(position, title, description) {
    return (
      <Marker position={position}>
        <Popup>{title}<br />{description}</Popup>
      </Marker>
    );
  }

  render() {
    return (
      <div>
        <Map center={this.state.centerCoordinates} zoom={15}>
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
          />
          {this.createAMarker([-34.706667, -58.2775], 'UNQ', 'Universidad Nacional de Quilmes')}
          <MeasureControl {...this.state.measureOptions.position} />
        </Map>
      </div>
    );
  }
}
export default MapViendasYa;
