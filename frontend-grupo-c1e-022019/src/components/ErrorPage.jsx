/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import React from 'react';
import { withTranslation } from 'react-i18next';
import {
  Container, Row, Col,
} from 'react-bootstrap';
import '../dist/css/ErrorPage.css';

class ErrorPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: {},
    };
  }

  componentDidMount() {
    if (this.props.location.state) {
      this.setState({ error: this.props.location.state.error });
      if (this.props.location.state.error && this.props.location.state.error.response) {
        this.handleAppropiateApiError(this.props.location.state.error.response);
      }
    } else {
      this.setState({ msg: 'Invalid Path' });
    }
  }

  handleAppropiateApiError(response) {
    if (response.status === 401) {
      this.goLogin();
    } else {
      this.setState({ msg: response.data });
    }
  }

  goLogin() {
    this.props.history.push({
      pathname: '/',
    });
  }

  goHome() {
    if (this.props.location.state && this.props.location.state.user) {
      if (this.props.location.state.user.typeClient) {
        this.props.history.push({
          pathname: '/home',
          state: {
            googleId: this.props.location.state.googleId,
            tokenAccess: this.props.location.state.tokenAccess,
            user: this.props.location.state.user,
          },
        });
      } else {
        this.props.history.push({
          pathname: '/provider',
          state: {
            googleId: this.props.location.state.googleId,
            tokenAccess: this.props.location.state.tokenAccess,
            user: this.props.location.state.user,
          },
        });
      }
    } else {
      this.goLogin();
    }
  }

  render() {
    const { t } = this.props;
    return (
      <Container>
        <Row />
        <Row className="background-error">
          <Col>
            <div className="text-center errorpage">
              {t('Intro logging error message')} :(
            </div>
          </Col>
        </Row>
        <Row className="background-error-message">
          <Col className="text-center errormessage">
            {`"${t(this.state.msg)}"`}
          </Col>
        </Row>
        <Row className="background-error-message">
          <Col className="text-center errorback">
            <button type="button" className="btn btn-link button_back" onClick={() => this.goHome()}>{t('Go back')}</button>
          </Col>
        </Row>
      </Container>
    );
  }
}

export default withTranslation()(ErrorPage);
