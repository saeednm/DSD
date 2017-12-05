import React, { Component } from 'react';
import $ from 'jquery';
import _ from 'lodash'




function calculate_qos(json_string_array, qos){

        var data = JSON.parse(json_string_array);
        for(var i=0; i< data.length; i++){
            var q = JSON.parse(data[i]).qos;
            switch(q){
                case 0:
                    qos[0][1]+=1;
                    break;
                case 1:
                    qos[1][1]+=1;
                    break;
                case 2:
                    qos[2][1]+=1;
                    break;
                case 3:
                    qos[3][1]+=1;
                    break;
                default:
            }
        }

        return qos;

}

function get_timestamp_data(json_string_array){

    var res_arr = [];
    var data = JSON.parse(json_string_array);
    for(var i=0; i< data.length; i++){

            var q = JSON.parse(data[i]).values;
            var value = q[0].value;
            var time_json = JSON.parse(data[i]).time;

            var resUTC = 0.0;
            if(time_json.isDst){
                resUTC = time_json.utcTimestamp + time_json.utcOffset*1000000;
            } else{
                resUTC = time_json.utcTimestamp + time_json.utcOffset*1000000 + 1000000;
            }

            res_arr[i] = [ resUTC, parseFloat(value)];
        }

        return res_arr;
}

function check_if_present(arr, tag){
    var pin = false;
    for(var i=0; i< arr.length; i++){
        if(arr[i][0] === tag){
            pin = true;
        }
    }

    return pin;
}

function inc_tag(arr, tag){

    for(var i=0; i< arr.length; i++){
        if(arr[i].name === tag){
            arr[i].y += 1;
            break;
        }
    }

    return arr;

}

function get_taged_data(json_string_array){

    var res_arr = [];
    var data = JSON.parse(json_string_array);
    for(var i=0; i< data.length; i++){

            var q = JSON.parse(data[i]).tags;
            var present = check_if_present(res_arr, q[i]);

            if(present){

                res_arr = inc_tag(res_arr, q[i]);

            } else{

                res_arr[res_arr.length] = {"name" : q[i], "y": 1};

            }
    }

    return res_arr;
}

export class Terminal extends Component{
	constructor(props) {
        super(props);

        var self = this;
        self.qos = [[0, 0], [1, 0], [2, 0], [3, 0]];
        self.state = {
            text: '',
        };
        self.taged_data = [];
    }


    componentDidMount() {
        var self = this;
        setInterval(function() {
            if(self.props.kafkaTopic !== '') {
                $.ajax({
                    url: "http://localhost:8001/?topic=" + self.props.kafkaTopic, success: function (result) {
                        self.setState(
                            {text: result}
                        );                
                        // self.qos = calculate_qos(self.state.text, self.qos);   
                    }
                });
            }
        }, 1000);
    }

    render(){

    	var self = this;

        let terminalText;

        let terminalMain;

        var text = _.chunk(self.state.text, 10);
        text = _.map(text, function(x) {
            return x.join("") + "&nbsp";
        });

        text = self.state.text;



        if(text !== ""){
            self.qos = calculate_qos(text, self.qos);
        
            var options1 = {
                title: {
                    text: 'QOS Histogram'
                },
                xAxis: {
                    gridLineWidth: 1
                },
                yAxis: [{
                    title: {
                        text: 'Qos Packets Count'
                    }
                }, {
                    opposite: true,
                    title: {
                        text: 'Count'
                    }
                }],
                series: [{
                    name: 'Histogram',
                    type: 'column',
                    data: self.qos,//histogram(data, 10),
                    pointPadding: 0,
                    groupPadding: 0,
                    pointPlacement: 'between'
                }]

            };

            // var hist = require("./Histogram");
            var hist;
            var Chart = require("./DemoChart");

            hist = React.createElement(Chart, 
                { 
                    container: 'chart1',
                    type: 'column',
                    options: options1 
            });


            var timestamp_data = get_timestamp_data(text)

            var options2 = {
                title: {
                    text: 'Timestamp Spline'
                },
                subtitle: {
                    text: 'Time-Data values of sensors in Highcharts JS'
                },
                xAxis: {
                    type: 'datetime',
                    dateTimeLabelFormats: { // don't display the dummy year
                        month: '%e. %b',
                        year: '%b'
                    },
                    title: {
                        text: 'Date'
                    }
                },
                yAxis: {
                    title: {
                        text: 'Values'
                    },
                    min: 0
                },
                tooltip: {
                    headerFormat: '<b>{series.name}</b><br>',
                    pointFormat: '{point.x:%e. %b}: {point.y:.2f} m'
                },

                plotOptions: {
                    spline: {
                        marker: {
                            enabled: true
                        }
                    }
                },

                series: [{
                    name: 'Timestamp',
                    // Define the data points. All series have a dummy year
                    // of 1970/71 in order to be compared on the same x axis. Note
                    // that in JavaScript, months start at 0 for January, 1 for February etc.
                    data: timestamp_data
                }]
            };

            var tms;

            tms = React.createElement(Chart, 
                { 
                    container: 'tms',
                    type: 'spline',
                    options: options2 
            });

            //pie

            self.taged_data = get_taged_data(text);

            var options3 = {

                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                title: {
                    text: 'Tags present in the stream'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color:  'black'
                            }
                        }
                    }
                },
                series: [{
                    name: 'Tags',
                    colorByPoint: true,
                    data: self.taged_data
                }]

            };

            var pita;

            pita = React.createElement(Chart, 
                { 
                    container: 'pita',
                    type: 'pie',
                    options: options3 
            });

             terminalText = "Terminal↡";
                terminalMain =<div className="terminal-main visible">
                    <div className="terminalHeader">$ Hello from {self.props.kafkaTopic}.</div>
                    <div className="terminal-text"> {hist} {tms} {pita} </div>
                </div>;

        }

        return (
            <div className="terminal">
                <div className="terminal-header">
                    // {terminalText}
                </div>
                {terminalMain}
            </div>
        );


    }

};
