import React from 'react';
import { withTranslation } from 'react-i18next';
import '../dist/css/Profile.css';
import StarRatingComponent from 'react-star-rating-component';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Badge from 'react-bootstrap/Badge';
import Card from 'react-bootstrap/Card';
import Swal from 'sweetalert2';
import formatCredit from './formatter/formatCredit.js';
import ModalSeeOrder from './ModalSeeOrder';
import API from '../service/api';

class OrderCard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      menuImage: 'https://www.seriouseats.com/recipes/images/2015/07/20150728-homemade-whopper-food-lab-35-1500x1125.jpg',
    };
  }


  showStars() {
    return (
      <StarRatingComponent
        className="stars"
        name="rate2"
        editing={false}
        starCount={5}
        value={this.props.order.stars}
      />
    );
  }

  showBadge() {
    return (
      <Badge pill variant="warning">
        {this.props.order.stateName}
      </Badge>
    );
  }

  orderCancelled(t) {
    Swal.fire(
      t('Done!'),
      'success',
    )
      .then(() => window.location.reload())
      .catch((error) => console.log(error));
  }

  makeApiCancel(t) {
    const body = {
      googleId: this.props.googleId,
      tokenAccess: this.props.tokenAccess,
      idClient: this.props.order.idClient,
      idOrder: this.props.order.id,
    };
    API.post('/order/cancel', body)
      .then(() => this.orderCancelled(t))
      .catch((e) => console.log(e));
  }

  cancelOrder(result, t) {
    if (result) {
      this.makeApiCancel(t);
    }
  }

  handleCancelOrder(t) {
    Swal.fire({
      title: t('¿Estas seguro?'),
      text: t('You are gonna miss this fantastic meal'),
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: 'Green',
      cancelButtonColor: 'Red',
      confirmButtonText: t('cancel'),
      cancelButtonText: t('Salir'),
    })
      .then((result) => this.cancelOrder(result.value, t))
      .catch((error) => console.log(error.response));
  }


  renderImg() {
    return <img className="profile-img" alt="providerimg" src={this.state.user.imageUrl} />;
  }


  render() {
    const { t } = this.props;
    return (
      <div className="order-card" key={this.props.order.id}>
        <Card>
          <Card.Header as="h4">{this.props.order.menuName}</Card.Header>
          <Card.Body>
            <Row>
              <Col lg={4.5}>
                <Card.Img className="card_img" variant="left" src={this.state.menuImage} />
                <div className="text-center font-weight-bold price-order">
                  {`${formatCredit(t, this.props.order.menuInfoPrice)}`}
                </div>
              </Col>
              <Col>
                {this.showStars()}
                {this.showBadge()}
                <h5>
                  Ordenaste {this.props.order.menusAmount}<br />
                </h5>
              </Col>
              <Col lg={2}>
                <ModalSeeOrder order={this.props.order} />
                <Button className="buy-button" variant="danger" onClick={() => this.handleCancelOrder(t)}>
                  {/* <img src={orderCancelIcon} alt="order-cancel" /> */}
                  Cancel
                </Button>
              </Col>
            </Row>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default withTranslation()(OrderCard);
