import React, { Component } from 'react';
import { withTranslation } from 'react-i18next';
import EditMenuForm from './EditMenuForm';
import '../dist/css/CreateMenu.css';


class EditMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <h1 className="register_menu_title"> {t('Edit your menu')}
        </h1>
        <EditMenuForm menu={this.props.location.state.menu} fatherProps={this.props} />
      </div>
    );
  }
}
export default withTranslation()(EditMenu);
