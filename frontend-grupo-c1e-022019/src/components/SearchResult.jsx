import React from 'react';
import '../dist/css/SearchResult.css';
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';
import API from '../service/api';

class SearchResult extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      results: [],
      pictures: [
        'https://www.seriouseats.com/recipes/images/2015/07/20150728-homemade-whopper-food-lab-35-1500x1125.jpg',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSZdKVn9EaecGuHITxS6GZO8d7eGSLO66qHcA-sG9fI-dc3-PWZ',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTjttrBPwqIuSqoO0Ms-VnJOVW_wDA2DKau1xUMDaUhMRCWiKJY',
        'https://placeralplato.com/files/2016/01/Pizza-con-pepperoni.jpg',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQYb3pgd8BIbJsxFNFCUEc5KBxkvFytZhyEZeKRk5ElefPWkCeW',
        'https://argentina.gastronomia.com/media/cache/noticia_grande/uploads/noticias/lengua.S3hpbFR2NG5mOEY0SEZ5RS8vMTQ5NDQ2NjkzMy8.jpg',
        'https://sifu.unileversolutions.com/image/es-AR/recipe-topvisual/2/1260-709/noquis-de-papas-50339442.jpg',
      ],
    };
  }

  componentDidMount() {
    const body = {
      name: `${this.props.location.state.input}`,
      city: 'QUILMES',
      category: 'SUSHI',
      priceOrder: 'min',
      rankOrder: 'max',
    };
    API.get('/menu/search/name', body)
      .then((response) => this.setState({ results: response }))
      .catch((error) => console.log(error));
  }

  mapResults() {
    return (
      <Container className="card_container">
        <Row className="card_row">
          {this.state.results.map(
            (menu) => (
              this.renderMenu(menu)
            ),
          )}
        </Row>
      </Container>
    );
  }

  renderMenu(menu) {
    const randomNumber = Math.floor(Math.random() * (this.state.pictures.length));
    return (
      <div className="menu_card">
        <Col>
          <Card>
            <Card.Img className="card_img" variant="top" src={this.state.pictures[randomNumber]} />
            <Card.Body>
              <Card.Title>{menu.name}</Card.Title>
              <Card.Text>
                {menu.description}
              </Card.Text>
              <Button variant="primary">Comprar!</Button>
            </Card.Body>
          </Card>
        </Col>
      </div>
    );
  }

  render() {
    return (
      <div>
        <h1 className="title">
          Resultados de {`"${this.props.location.state.input}"`}
        </h1>
        {this.mapResults()}
      </div>
    );
  }
}

export default SearchResult;
