import React from 'react';
import '../dist/css/SearchResult.css';
import {
  Card, Modal, Badge, Button, Container,
} from 'react-bootstrap';
import { withTranslation } from 'react-i18next';
import formatPrice from './formatter/formatCredit';
import menuInfoIcon from '../dist/icons/info-icon.png';

class ModalSeeOrder extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modalOpen: false,
      pictures: [
        'https://www.seriouseats.com/recipes/images/2015/07/20150728-homemade-whopper-food-lab-35-1500x1125.jpg',
      ],
    };
  }

  setShowSee(b) {
    this.setState({ modalOpen: b });
  }


  getDeliveryValue(t) {
    // Hardcodeado en free, no se xq tira menu info como undefined
    if (this.props.order.menuInfo.deliveryValue !== undefined) {
      // return (this.props.order.menuInfo.deliveryValue <= 0 ? this.createFreeBadge(t) : formatPrice(t, this.props.menu.deliveryValue));
      return this.createFreeBadge(t);
    }
    return <div />;
  }

  createFreeBadge(t) {
    return (
      <Badge pill variant="success">
        {t('Free')}
      </Badge>
    );
  }

  render() {
    const { t } = this.props;
    const handleClose = () => this.setShowSee(false);
    const handleShow = () => this.setShowSee(true);
    return (
      <div>
        <Button className="buy-button" variant="info" onClick={handleShow}>
          <img src={menuInfoIcon} alt="menu-info" />
        </Button>
        <Modal show={this.state.modalOpen} onHide={handleClose}>
          <Container>
            <Modal.Header closeButton>
              <h1>{/* this.props.order.menuName */}</h1>
            </Modal.Header>
            <Modal.Title>El menu esta {this.props.order.menuInfo.menuState.normal ? 'Normal' : 'Cancelado' }</Modal.Title>
            <Modal.Body>
              <Card.Img className="card_img" variant="left" src={this.state.pictures[0]} /><br />
              <h5>{t('Delivery')}: {this.getDeliveryValue(t)}<br />
                Estado : {this.props.order.stateName}<br />
                Precio : {formatPrice(t, this.props.order.menuInfoPrice)}
              </h5>
            </Modal.Body>
          </Container>
        </Modal>

      </div>
    );
  }
}

export default withTranslation()(ModalSeeOrder);
