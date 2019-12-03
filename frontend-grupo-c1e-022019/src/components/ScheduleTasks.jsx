import React from 'react';

class ScheduleTasks extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      password: '',
    };
  }

  render() {
    return (
      <div>
        <h1>Schedule Tasks:</h1>
      </div>
    );
  }
}

export default ScheduleTasks;
