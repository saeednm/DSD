package com.youscada;

import com.youscada.kafka.producers.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessagingTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void testStart() {
        kafkaProducer.publishMessage("pluginConfigTopic", "start", "{ \"id\": \"123\" }");
    }

    @Test
    public void testStop() {
        kafkaProducer.publishMessage("pluginConfigTopic", "stop", "{ \"id\": \"123\" }");
    }

    @Test
    public void testRawData() {
        kafkaProducer.publishMessage("fieldRawDataTopic", "xxx", "{ \"value\": \"mesina\" }");
    }

}
