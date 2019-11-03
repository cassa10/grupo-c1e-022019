import React from 'react';
import { withTranslation } from 'react-i18next';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import '../dist/css/CreateMenu.css';
import Row from 'react-bootstrap/Row';

class RegisterMenuForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  nameField(t) {
    return (
      <Form.Group controlId="formBasicEmail">
        <Form.Label>{t('Menu name')}</Form.Label>
        <Form.Control type="text" placeholder={t('Submit the name here')} />
        <Form.Text className="text-muted">
          {t('This name will appear in your posts')}
        </Form.Text>
      </Form.Group>
    );
  }

  descriptionField(t) {
    return (
      <Form.Group controlId="formBasicPassword">
        <Form.Label>{t('Description')}</Form.Label>
        <Form.Control type="text" placeholder={t('Submit the discription here')} />
      </Form.Group>
    );
  }

  categoryField(t) {
    return (

      <Form.Group controlId="formBasicCheckbox">
        <Form.Label>{t('Choose yout product category')}</Form.Label>
        <Row className="line_of_checkboxs">
          <Form.Check inline label="Pizza" type="checkbox" id="inline-checkbox-1" />
          <Form.Check inline label={t('Burger')} type="checkbox" id="inline-checkbox-1" />
          <Form.Check inline label={t('Green')} type="checkbox" id="inline-checkbox-1" />
          <Form.Check inline label={t('Vegan')} type="checkbox" id="inline-checkbox-1" />
          <Form.Check inline label={t('Beer')} type="checkbox" id="inline-checkbox-1" />
          <Form.Check inline label="Empanadas" type="checkbox" id="inline-checkbox-1" />
          <Form.Check inline label="Sushi" type="checkbox" id="inline-checkbox-1" />
        </Row>
      </Form.Group>
    );
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <Form className="register_menu_form">
          {this.nameField(t)}

          {this.descriptionField(t)}

          {this.categoryField(t)}
          <Button variant="primary" type="submit">
        Submit
          </Button>
        </Form>
      </div>
    );
  }
}

export default withTranslation()(RegisterMenuForm);
