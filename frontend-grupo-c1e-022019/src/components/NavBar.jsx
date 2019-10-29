import Button from 'react-bootstrap/Button';
import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';

class NavBar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  render() {
    return (
      <Navbar bg="dark" fixed="top">
        <Container>
          <Row>
            <Navbar.Brand href="#home">ViendasApp</Navbar.Brand>
          </Row>
          <Row>
            <form>
              <label>
                <input type="text" name="name" />
              </label>
              <Button className="searchButton" variant="primary">Buscar</Button>
            </form>
          </Row>
        </Container>
      </Navbar>
    );
  }
}

export default NavBar;