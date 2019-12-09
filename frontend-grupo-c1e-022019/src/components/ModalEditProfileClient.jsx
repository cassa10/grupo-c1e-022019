/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import '../dist/css/SearchResult.css';
import {
  Modal, Button, Container, Row, Col,
} from 'react-bootstrap';
import Swal from 'sweetalert2';
import { withTranslation } from 'react-i18next';
import editProfileIcon from '../dist/icons/edit-user-icon.png';
import API from '../service/api';
import '../dist/css/ModalEditProfileClient.css';

class ModalEditProfileClient extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modalOpen: false,
      googleId: '',
      tokenAccess: '',
      user: {
        id: 0,
        firstName: '',
        lastName: '',
        phoneNumber: '',
        address: '',
        location: '',
        imageUrl: '',
      },
      profileImg: '',
      newProfileImg: '',
      newFirstName: '',
      newLastName: '',
      newPhoneNumber: '',
      newAddress: '',
      newLocation: '',
      areaPhoneNumber: '+549',
      showInputImage: false,
    };
  }

  componentDidMount() {
    const body = {
      googleId: this.props.location.state.googleId,
      tokenAccess: this.props.location.state.tokenAccess,
      idClient: this.props.location.state.user.id,
    };

    this.setState({
      googleId: body.googleId,
      tokenAccess: body.tokenAccess,
      user: this.props.location.state.user,
      profileImg: this.props.location.state.profileImg,
    });

    API.get('/client', body)
      .then((response) => this.handleResponseAPI(response))
      .catch((error) => console.log(error));
  }

  setShow(b) {
    this.setState({ modalOpen: b });
  }

  getBodyToPostNewProfile() {
    const firstNameObtained = this.state.newFirstName.trim().length > 0
      ? this.state.newFirstName : this.state.user.firstName;
    const lastNameObtained = this.state.newLastName.trim().length > 0
      ? this.state.newLastName : this.state.user.lastName;
    const addressObtained = this.state.newAddress.trim().length > 0
      ? this.state.newAddress : this.state.user.address;
    const locationObtained = this.state.newLocation.trim().length > 0
      ? this.state.newLocation : this.state.user.location;
    const imageUrlObtained = this.state.newProfileImg.trim().length > 0
      ? this.state.newProfileImg : this.state.user.imageUrl;
    const phoneNumberObtained = this.state.newPhoneNumber.trim().length > 0
      ? `${this.state.areaPhoneNumber}${this.state.newPhoneNumber}` : this.state.user.phoneNumber;
    return ({
      googleId: this.state.googleId,
      tokenAccess: this.state.tokenAccess,
      id: this.state.user.id,
      firstName: firstNameObtained,
      lastName: lastNameObtained,
      address: addressObtained,
      location: locationObtained,
      imageUrl: imageUrlObtained,
      phoneNumber: phoneNumberObtained,
    });
  }

  handleResponseAPI(response) {
    this.setState({ user: response });
    this.setState({ profileImg: response.imageUrl });
  }

  validForm() {
    const validFirstName = this.state.user.firstName.length > 0
        || this.state.newFirstName.trim().length > 0;
    const validLastName = this.state.user.lastName.length > 0
        || this.state.newLastName.trim().length > 0;
    const validAddress = (this.state.user.address && this.state.user.address.length > 0)
        || this.state.newAddress.trim().length > 0;
    const validLocation = (this.state.user.location && this.state.user.location.length > 0)
    || this.state.newLocation.trim().length > 0;
    const validPhoneNumber = (this.state.user.phoneNumber && this.state.user.phoneNumber.length > 0)
    || this.state.newPhoneNumber.trim().length > 0;
    const validImageProfile = this.state.user.imageUrl.length > 0
    || this.state.newProfileImg.trim().length > 0;
    return (
      validFirstName && validLastName && validAddress
      && validLocation && validPhoneNumber && validImageProfile
    );
  }

  handleEditBasicInfoProfile(t) {
    if (this.validForm()) {
      Swal.fire({
        title: t('¿Estas seguro?'),
        text: t('Tus datos van a cambiar'),
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: 'Green',
        cancelButtonColor: 'Red',
        confirmButtonText: t('Yeah, edit!'),
        cancelButtonText: t('cancel'),
      })
        .then((result) => this.editConfirmed(result.value, t))
        .catch((error) => console.log(error.response));
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: t('Please, complete all important fields (*)'),
      });
    }
  }

  editConfirmed(isConfirmed, t) {
    if (isConfirmed) {
      this.postNewProfile(t);
    }
  }

  postNewProfile(t) {
    const body = this.getBodyToPostNewProfile();
    API.post('/client/basicInfo', body)
      .then(() => this.editDone(t))
      .catch((error) => console.log(error));
  }

  editDone(t) {
    Swal.fire({
      title: t('Edited!'),
      icon: 'success',
    });
    window.location.reload();
  }

  createColInput(t, input, defaultValue, handlerInput) {
    return (
      <Col>
        <label className="label_edit_profile">{t(input)} (*)</label>
        <input
          type="text"
          className="form-control input_edit_profile"
          id={input}
          defaultValue={defaultValue}
          onChange={handlerInput}
        />
      </Col>
    );
  }

  createAppropiatePhoneNumber(t) {
    const handlerPhoneNumber = (e) => this.setState({ newPhoneNumber: e.target.value });
    if (this.state.user.phoneNumber && this.state.user.phoneNumber > 0) {
      return (
        this.createColInput(t, 'Telephone', this.state.user.phoneNumber.slice(4, this.state.user.phoneNumber.length + 1), handlerPhoneNumber)
      );
    }
    return (
      this.createColInput(t, 'Telephone', '', handlerPhoneNumber)
    );
  }

  createImageVisualizator() {
    if (this.state.newProfileImg.trim().length > 0 && this.state.showInputImage) {
      return (
        <img className="logo_img" src={this.state.newProfileImg} alt="profileImg" />
      );
    }
    return (
      <img className="logo_img" src={this.state.profileImg} alt="profileImg" />
    );
  }

  createProfileImageEditor(t) {
    return (
      <Row className="text-center">
        {this.createImageVisualizator()}
        <Col xs={2} sm={3} md={3} lg={2} xl={2} className="text-left button_update_image">
          <Button onClick={() => this.updateImageVisualizator()}>
            {t('Upload')}
          </Button>
        </Col>
        <Col className="button_update_image">
          {this.createInputLogo(t)}
        </Col>
      </Row>
    );
  }

  updateImageVisualizator() {
    if (this.state.newProfileImg) {
      this.setState({ showInputImage: true });
    }
  }

  handleImageProfile(e) {
    if (e.target.value.trim().length < 1) {
      this.setState({ showInputImage: false });
    }
    this.setState({ newProfileImg: e.target.value });
  }

  createInputLogo(t) {
    return (
      <input type="text" className="form-control" id="inputProvider" placeholder={t('Profile image link')} onChange={(e) => this.handleImageProfile(e)} />
    );
  }

  closeModalAndResetInputs(bool) {
    this.setState({
      newProfileImg: '',
      newFirstName: '',
      newLastName: '',
      newPhoneNumber: '',
      newAddress: '',
      newLocation: '',
      showInputImage: false,
    });
    this.setShow(bool);
  }

  render() {
    const { t } = this.props;
    const handleClose = () => this.closeModalAndResetInputs(false);
    const handleShow = () => this.setShow(true);
    const handlerFirstName = (e) => this.setState({ newFirstName: e.target.value });
    const handlerLastName = (e) => this.setState({ newLastName: e.target.value });
    const handlerLocation = (e) => this.setState({ newLocation: e.target.value });
    const handlerAddress = (e) => this.setState({ newAddress: e.target.value });
    return (
      <div>
        <Button className="buy-button" variant="success" onClick={handleShow}>
          <img src={editProfileIcon} alt="profile-edit" />
        </Button>
        <Modal size="lg" show={this.state.modalOpen} onHide={handleClose}>
          <Container>
            <Modal.Header closeButton>
              <Row>
                <Col className="title-update-profile">
                  {t('Update profile')}
                </Col>
              </Row>
            </Modal.Header>
            <Modal.Body>
              <Row>
                {this.createColInput(t, 'Firstname', this.state.user.firstName, handlerFirstName)}
                {this.createColInput(t, 'Lastname', this.state.user.lastName, handlerLastName)}
              </Row>
              <Row>
                {this.createColInput(t, 'Location', this.state.user.location, handlerLocation)}
                {this.createColInput(t, 'Address', this.state.user.address, handlerAddress)}
              </Row>
              <Row>
                <Col>
                  <label className="label_edit_profile">{t('Area (Tel)')}</label>
                  <input className="form-control" type="text" placeholder="International (+54 9)" disabled />
                </Col>
                {this.createAppropiatePhoneNumber(t)}
              </Row>
              {this.createProfileImageEditor(t)}
            </Modal.Body>
            <Modal.Footer>
              <Row>
                <Col>
                  <Button variant="danger" onClick={handleClose}>
                    {t('cancel')}
                  </Button>
                </Col>
                <Col>
                  <Button variant="success" onClick={() => this.handleEditBasicInfoProfile(t)}>
                    {t('edit')}
                  </Button>
                </Col>
              </Row>
            </Modal.Footer>
          </Container>
        </Modal>
      </div>
    );
  }
}

export default withTranslation()(ModalEditProfileClient);
