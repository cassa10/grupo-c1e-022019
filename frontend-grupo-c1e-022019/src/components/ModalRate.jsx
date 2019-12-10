import React from 'react';
import '../dist/css/Profile.css';
import Swal from 'sweetalert2';
import {
  Modal, Button,
} from 'react-bootstrap';
import { withTranslation } from 'react-i18next';
import StarRatingComponent from 'react-star-rating-component';
import API from '../service/api';

class ModalRate extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modalOpen: false,
      rating: 0,
    };
  }


  onStarClick(nextValue) {
    this.setState({ rating: nextValue });
  }


  setShowSee(b) {
    this.setState({ modalOpen: b });
  }

  rateSuccessful(t) {
    Swal.fire(
      t('Done!'),
      t('Order ranked!'),
      'success',
    )
      .then(() => window.location.reload())
      .catch((error) => console.log(error));
  }

  rate(t) {
    if (this.state.rating > 0 && this.state.rating <= 5) {
      const body = {
        googleId: this.props.googleId,
        tokenAccess: this.props.tokenAccess,
        idClient: this.props.order.idClient,
        idOrder: this.props.order.id,
        rate: this.state.rating,
      };
      console.log(body);
      API.post('/order/rate', body)
        .then(() => this.rateSuccessful(t))
        .catch((e) => console.log(e.response.data));
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: t('Please, put some value'),
      });
    }
  }


  render() {
    const { t } = this.props;
    const { rating } = this.state;
    const handleClose = () => this.setShowSee(false);
    const handleShow = () => this.setShowSee(true);
    return (
      <div>
        <Button className="buy-button" variant="info" onClick={handleShow}>
          Rate
        </Button>
        <Modal show={this.state.modalOpen} onHide={handleClose}>
          <Modal.Header closeButton>
            <h1>Rate {this.props.order.menuName}</h1>
          </Modal.Header>
          <Modal.Body>
            <h2>Please,leave us your opinion</h2>
            <StarRatingComponent
              className="rating-stars"
              name="rate1"
              starCount={5}
              value={rating}
                  // eslint-disable-next-line react/jsx-no-bind
              onStarClick={this.onStarClick.bind(this)}
            />
            <Button onClick={() => this.rate(t)}>{t('rate')} </Button>
          </Modal.Body>
        </Modal>

      </div>
    );
  }
}

export default withTranslation()(ModalRate);
