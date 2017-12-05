#!/bin/bash
x=1
while [ $x -le 100 ]
do
  echo "{ \"value\": \"Esmere mi moje maleeee\" }" | ~/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic fieldRawDataTopic > /dev/null
  x=$(( $x + 1 ))
done