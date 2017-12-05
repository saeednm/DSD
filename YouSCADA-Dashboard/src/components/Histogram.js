import React from "react";


export class Histogram extends React.Component{

    render (){

        // var tests= [[0,25],[1,75],[2,100],[3,40]];

        var options2 = {
                title: {
                    text: 'Highcharts Histogram'
                },
                xAxis: {
                    gridLineWidth: 1
                },
                yAxis: [{
                    title: {
                        text: 'Histogram Count'
                    }
                }, {
                    opposite: true,
                    title: {
                        text: 'Y value'
                    }
                }],
                series: [{
                    name: 'Histogram',
                    type: 'column',
                    data: JSON.parse(this.props.tests),//tests,//histogram(data, 10),
                    pointPadding: 0,
                    groupPadding: 0,
                    pointPlacement: 'between'
                }]

        };

        var element;
        var Chart = require("./DemoChart");

        //Create and render element
        element = React.createElement(Chart, 
            { 
                container: 'chart1',
                type: 'column',
                options: options2 
        });


        return (

            <div>
                {element}
            </div>

        );
    }
}
// module.exports = element;


