var express = require('express');
var app = express();
var fs = require("fs");
var _ = require("lodash");

var lastMessages = {};
var maxMessageCount = 500;

function startFetching(topic) {
    lastMessages[topic] = [];

    var kafka = require('kafka-node'),
        HighLevelConsumer = kafka.HighLevelConsumer,
        client = new kafka.Client(),
        consumer = new HighLevelConsumer(
            client,
            [
                { topic: topic }
            ],
            {}
        );

    consumer.on('message', function (message) {
        lastMessages[topic].push(message.value);
        lastMessages[topic] = _.takeRight(lastMessages[topic], maxMessageCount);
    });

    consumer.on('error', function(error) {
        console.log(error);
    });
}

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.get('/', function (req, res) {
    console.log(lastMessages);

    var topic = req.query.topic;

    var result = null;
    if(_.has(lastMessages, topic)) {
        result = lastMessages[topic];
        res.end(JSON.stringify(result));
    } else {
        startFetching(topic);
        setTimeout(function() {
            result = lastMessages[topic]
            res.end(JSON.stringify(result));
        }, 100); // hacky ? :S
    }

});

var server = app.listen(8081, function () {
    var host = server.address().address
    var port = server.address().port
});