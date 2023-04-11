import React from 'react';
import logo from '../logo.svg';
class TestComponent extends React.Component {

    state = {};

    componentDidMount() {
        this.testReact()
    }

    testReact = () => {
        fetch('/testReact')
        .then(response => response.text())
        .then(message => {
            this.setState({message: message});
        });
    };

    render() {
        return (
            <div className="App">
            <header className="App-header">
            <img src={logo} className="App-logo" alt="logo"/>
            <h3 className="App-title">{this.state.message}</h3>
            </header>
            </div>
        );
    }
}

export default TestComponent