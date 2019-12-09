import React, { Component } from 'react';
import { withTranslation } from 'react-i18next';
import RegisterMenuForm from './RegisterMenuForm';
import '../dist/css/CreateMenu.css';


class CreateMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <h1 className="register_menu_title"> {t('register your menu')}
        </h1>
        <RegisterMenuForm fatherProps={this.props} />
      </div>
    );
  }
}
export default withTranslation()(CreateMenu);
