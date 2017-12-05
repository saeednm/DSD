package com.youscada;

import com.youscada.core.Plugin;
import com.youscada.core.TrivialPlugin;
import com.youscada.core.ThresholdExceedPlugin;
import com.youscada.core.NConsecutiveOccurrencePlugin;
import com.youscada.core.TimePeriodExceededNoDataReceivedPlugin;
import com.youscada.domain.NodePacket;
import com.youscada.kafka.consumers.ConsumerConfigHelper;
import com.youscada.util.JsonHelper;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ConsumerConfigHelper consumerConfigHelper() {
        return new ConsumerConfigHelper();
    }

    @Bean
    public Plugin dataTransformer() {
        return new TimePeriodExceededNoDataReceivedPlugin();
    }

    @Value("${kafka.data.output.topic}")
    private String dataOutputTopic;

    @Value("${node.id}")
    private String id;

    @Value("${kafka.data.input.topic}")
    private String dataInputTopic;

    @Value("${node.name}")
    private String name;

    @Value("${node.description}")
    private String description;

    @Bean
    public NodePacket nodePacket() {
        NodePacket nodePacket = new NodePacket();

        nodePacket.setId(id);
        nodePacket.setName(name);
        nodePacket.setDescription(description);
        nodePacket.setInputTopicName(dataInputTopic);
        nodePacket.setOutputTopicName(dataOutputTopic);
        nodePacket.setActive(true);

        return nodePacket;
    }

    @Bean
    public JsonHelper jsonHelper() {
        return new JsonHelper();
    }

    @Bean
    public JSONParser jsonParser() {
        return new JSONParser();
    }


}
