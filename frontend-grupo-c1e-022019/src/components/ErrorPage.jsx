/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import React from 'react';
import { withTranslation } from 'react-i18next';
import '../dist/css/ErrorPage.css';

class ErrorPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      msg: '',
    };
  }

  componentDidMount() {
    if (this.state.msg === '') {
      this.setState({ msg: 'Invalid Path' });
    }
  }

  goHome() {
    this.props.history.push({
      pathname: '/home',
    });
  }

  render() {
    const { t } = this.props;
    return (
      <div>
        <div className="row">
          <div className="col">
            <img className="logo" src="https://fontmeme.com/permalink/191102/03a545ac680d1396fcfae624d4ee0c3a.png" onClick={() => this.goHome()} alt="viendasYa-logo" border="0" />
          </div>
        </div>
        <div className="card-title text-center errorpage">
          {t('Intro logging error message')}
        </div>
      </div>
    );
  }
}

export default withTranslation()(ErrorPage);
