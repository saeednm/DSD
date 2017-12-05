import React from "react";

var options = {

    chart: {
            type: 'pie'
    },
    title: {
        text: "Senzor"
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
            }
        }
    },
    xAxis: {
      categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 
                    'Jul', 'Aug']
    },
    series: [{
        data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 254.4,100]
    }]

};

var element;
var Chart = require("./DemoChart");

//Create and render element
element = React.createElement(Chart, 
    { 
        container: 'chart',
        type: 'pie',
        options: options 
});

module.exports = element;