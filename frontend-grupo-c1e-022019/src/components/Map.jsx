import React from 'react';
import '../dist/css/Map.css';
import { Map, Marker, Popup, TileLayer,withLeaflet } from 'react-leaflet';
import MeasureControlDefault from 'react-leaflet-measure';
const MeasureControl = withLeaflet(MeasureControlDefault);

class Mapa extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  render() {
    const position = [-34.706667, -58.2775];
    const measureOptions = {
      position: 'topright',
      primaryLengthUnit: 'meters',
      secondaryLengthUnit: 'kilometers',
      primaryAreaUnit: 'sqmeters',
      secondaryAreaUnit: 'acres',
      activeColor: '#db4a29',
      completedColor: '#9b2d14'
    };
    return (
      <div>
        <Map center={position} zoom={13}>
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
          />
          <Marker position={position}>
            <Popup>A pretty CSS3 popup.<br />Easily customizable.</Popup>
          </Marker>
          <MeasureControl {...measureOptions} />
        </Map>
      </div>
    );
  }
}
export default Mapa;
