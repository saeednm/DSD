package com.youscada.youstream.kafka.consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class ConsumerConfigHelper {

    @Value("${kafka.server.ip}")
    private String kafkaServerIp;

    @Value("${kafka.server.port}")
    private String kafkaServerPort;

    public Map<String, Object> createConsumerConfigs (String kafkaListenerId) {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                String.join(":", kafkaServerIp, kafkaServerPort)
        );
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaListenerId);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return propsMap;
    }

}
