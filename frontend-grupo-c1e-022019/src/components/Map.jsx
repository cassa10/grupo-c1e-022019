import React from 'react';
import '../dist/css/Map.css';
import { Map, Marker, Popup, TileLayer } from 'react-leaflet';

class Mapa extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  render() {
    const position = [-34.706667, -58.2775];
    return (
      <Map center={position} zoom={13}>
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
        />
        <Marker position={position}>
          <Popup>A pretty CSS3 popup.<br />Easily customizable.</Popup>
        </Marker>
      </Map>
    );
  }
}
export default Mapa;
