package com.youscada.kafka.consumers.field;

import com.youscada.kafka.consumers.ConsumerConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;

@Configuration
@EnableKafka
public class FieldDataConsumerConfig {
    @Value("${kafka.server.ip}")
    private String kafkaServerIp;

    @Value("${kafka.server.port}")
    private String kafkaServerPort;

    @Autowired
    ConsumerConfigHelper consumerConfigHelper;

    private static final String KAFKA_GROUP = "transformPlugins";

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setConcurrency(3);
        return factory;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        return consumerConfigHelper.createConsumerConfigs(KAFKA_GROUP);
    }

    @Bean
    public FieldDataListener fieldDataListener() {
        return new FieldDataListener();
    }
}
