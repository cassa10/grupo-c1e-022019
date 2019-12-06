import React from 'react';
import '../dist/css/SearchResult.css';
import {
  Card, Modal, Badge, Button,
} from 'react-bootstrap';
import { withTranslation } from 'react-i18next';
import formatPrice from './formatter/formatCredit';
import formatNumber from './formatter/formatNumber';
import menuInfoIcon from '../dist/icons/info-icon.png';

class ModalSee extends React.Component {
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
          <Modal.Header closeButton>
            <h1>{this.props.menu.name}</h1>
          </Modal.Header>
          <Modal.Title>{this.props.menu.description}</Modal.Title>
          <Modal.Body>
            <Card.Img className="card_img" variant="left" src={this.state.pictures[0]} /><br />
            <h5>{t('Delivery')}: {this.props.menu.deliveryValue <= 0 ? this.createFreeBadge(t) : formatPrice(t, this.props.menu.deliveryValue)}<br />
              {t('Comprando mas de')}: {formatNumber(t, this.props.menu.firstMinAmount)} {t('unidades')}<br />
              {t('the price will be')} {formatPrice(t, this.props.menu.firstMinAmountPrice)} <br />
              {t('Comprando mas de')}: {formatNumber(t, this.props.menu.menuPriceCalculator.secondMinAmount)} {t('unidades')}<br />
              {t('the price will be')} {formatPrice(t, this.props.menu.menuPriceCalculator.secondMinAmountPrice)} <br />
              {t('Distancia de delivery')} : {formatNumber(t, this.props.menu.deliveryMaxDistanceInKM)} kms<br />
            </h5>
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default withTranslation()(ModalSee);
