import React from 'react';
import {
  Button, Container, Col, Row,
} from 'react-bootstrap';
import '../dist/css/ScheduleTask.css';
import Swal from 'sweetalert2';
import API from '../service/api';

class ScheduleTasks extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      password: '',
    };
  }

  handlePassword(e) {
    this.setState({ password: e.target.value });
  }

  requestExpireTokens(isConfirmed) {
    if (isConfirmed) {
      API.get('/schedule/tokens/expire', { password: this.state.password })
        .then((response) => this.requestResponse('Done!', response, 'success'))
        .catch((error) => (error.response
          ? this.requestResponse('Ups', error.response.data)
          : this.requestResponse('Ups', 'Server shutted down, sorry ', 'error')));
    }
  }

  requestResponse(title, response, type) {
    Swal.fire(
      title,
      response,
      type,
    );
  }

  requestConfirmOrders(isConfirmed) {
    if (isConfirmed) {
      API.get('/schedule/order/confirm', { password: this.state.password })
        .then((response) => this.requestResponse('Done!', response, 'success'))
        .catch((error) => (error.response
          ? this.requestResponse('Ups', error.response.data)
          : this.requestResponse('Ups', 'Server shutted down, sorry ', 'error')));
    }
  }

  confirmButton(functionAPI) {
    Swal.fire({
      title: 'Estas seguro?',
      text: 'Mire que pueden pasar cosas...',
      showCancelButton: true,
      confirmButtonColor: 'Green',
      cancelButtonColor: 'Red',
      confirmButtonText: 'Do it!',
      cancelButtonText: 'Cancel',
    })
      .then(functionAPI)
      .catch((error) => console.log(error.response));
  }

  render() {
    const confirmExpireTokens = (result) => { this.requestExpireTokens(result.value); };
    const confirmOrders = (result) => { this.requestConfirmOrders(result.value); };
    return (
      <div>
        <Container className="schedule-task">
          <span className="text-center"><h1>Schedule Tasks</h1></span>
          <Row>
            <Col className="text-center schedule_form">
              <input type="text" placeholder="Password" onChange={(e) => this.handlePassword(e)} />
            </Col>
          </Row>
          <Row className="text-center schedule_form">
            <Col>
              <Button variant="danger" onClick={() => this.confirmButton(confirmExpireTokens)}>
                Expire Tokens
              </Button>
            </Col>
          </Row>
          <Row className="text-center schedule_form">
            <Col>
              <Button variant="danger" onClick={() => this.confirmButton(confirmOrders)}>
                Confirm Orders
              </Button>
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

export default ScheduleTasks;
