import React, { Component } from 'react';
import logo from './IoT.png'
import powerButtonImage from './power-button-blue.png'
import terminalButtonImage from './terminal-button.png'
import './App.css';
import _ from 'lodash'
import $ from 'jquery'

class PluginNode extends Component {
    render() {
        var self = this;

        let title = <div className="plugin-node-title">{self.props.node.name}</div>;
        let buttons = <div className="power-button-container">
            <img onClick={self.props.powerF} className="button power-button" src={powerButtonImage}/>
            <img onClick={self.props.startListeningF} className="button console-button" src={terminalButtonImage}/>
        </div>;

        let description = <div className="node-description">
            {self.props.node.description}
        </div>;

        let status;

        if(self.props.node.active === true)
            status = <div className="plugin-node-status-active">
                Status: Active
            </div>;
        else {
            status = <div className="plugin-node-status-notactive">
                Status: Not Active
            </div>;
        }

        return (
            <div className="plugin-node">
                {title}
                {description}
                {status}
                {buttons}
            </div>
        );
    }

}

class PluginNodeList extends Component {
    render() {
        if(this.props.nodeList === undefined || this.props.nodeList.length === 0) {
            return <div>
                <p>No active nodes :(</p>
            </div>
        }

        var self = this;
        var listElements = _.map(this.props.nodeList, function(x) {
            return <PluginNode
                startListeningF={self.props.startListeningF.bind(self, x.id)}
                powerF={self.props.powerF.bind(self, x.id)}
                node={x} />
        });

        return (
            <div>
                {listElements}
            </div>
        );
    }
}

class Terminal extends Component {
    constructor(props) {
        super(props);

        var self = this;
        self.state = {
            text: ''
        };
    }

    componentDidMount() {
        var self = this;
        setInterval(function() {
            if(self.props.kafkaTopic !== '') {
                $.ajax({
                    url: "http://localhost:8081/?topic=" + self.props.kafkaTopic, success: function (result) {
                        self.setState(
                            {text: result}
                        );
                    }
                });
            }
        }, 100);
    }

    render() {
        var self = this;

        let terminalText;

        let terminalMain;

        var text = _.chunk(self.state.text, 10);
        text = _.map(text, function(x) {
            return x.join("") + "&nbsp";
        });

        text = self.state.text;

        if(this.props.isVisible === true) {
            terminalText = "Terminal↡";
            terminalMain =<div className="terminal-main visible">
                <div className="terminalHeader">$ Hello from {self.props.kafkaTopic}.</div>
                <div className="terminal-text"> {text} </div>
            </div>
        } else {
            terminalText = "Terminal↟";
            terminalMain =<div className="terminal-main invisible">
                <div className="terminalHeader">$ Hello from {self.props.kafkaTopic}.</div>
                <div className="terminal-text"> {text} </div>
            </div>
        }

        return (
            <div className="terminal">
                <div onClick={this.props.changeVisibilityF} className="terminal-header">
                    {terminalText}
                </div>

                {terminalMain}
            </div>
        );
    }
}

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            pluginNodes: [],
            isTerminalVisible: false,
            listeningKafkaTopic: "",
            listeningNodeId: ""
        };
    }

    componentDidMount() {
        var self = this;
        setInterval(function() {
            $.ajax({
                url: "http://localhost:1337", success: function (result) {
                    self.setState(
                        { pluginNodes: result }
                    );
                }
            });
        }, 100);
    }

    changeTerminalVisiblity() {
        var isTerminalVisible = this.state.isTerminalVisible;
        this.setState(
            { isTerminalVisible: !isTerminalVisible }
        );
    }

    startListening(id) {
        console.log("Starting to listen");

        var self = this;

        if(self.state.isTerminalVisible === true && self.state.listeningNodeId === id) {
            self.changeTerminalVisiblity();
        } else if(self.state.isTerminalVisible === false) {
            self.changeTerminalVisiblity();
        }

        if(self.state.pluginNodes.length > 0) {
            let listeningKafkaTopic;

            for(var i = 0; i < self.state.pluginNodes.length; i++) {
                if(this.state.pluginNodes[i].id === id) {
                    listeningKafkaTopic = self.state.pluginNodes[i].outputTopicName;
                    break;
                }
            }

            self.setState({
                listeningKafkaTopic: listeningKafkaTopic,
                listeningNodeId: id
            });

            console.log(self.state);
        }
    }

    toggleStartStop(node_id) {
        if(this.state.pluginNodes.length > 0) {
            for(var i = 0; i < this.state.pluginNodes.length; i++) {
                if(this.state.pluginNodes[i].id === node_id) {
                    var isActive = this.state.pluginNodes[i].isActive;
                    this.state.pluginNodes[i].isActive = !isActive;

                    $.ajax({
                        type: "POST",
                        url: "http://localhost:1337/toggle",
                        data: {id: node_id}
                    });

                    break;
                }
            }
        }
    }

    render() {
        var self = this;

        let pluginNodeComponentList = <p className="intro">
            <PluginNodeList
                nodeList={self.state.pluginNodes}
                startListeningF={self.startListening.bind(this)}
                powerF={self.toggleStartStop.bind(this)} />
        </p>;

        return (
            <div className="main-container">
                <div className="header">
                    <div className="logo-container">
                        <img src={logo} className="logo" alt="logo" />
                    </div>
                    <span className="title">Welcome to IOT</span>
                </div>

                {pluginNodeComponentList}

                <Terminal
                    changeVisibilityF={this.changeTerminalVisiblity.bind(this)}
                    isVisible={self.state.isTerminalVisible}
                    kafkaTopic={self.state.listeningKafkaTopic} />

                <div className="footer">
                </div>
            </div>
        );
    }
}

export default App;
