package com.youscada;

import com.youscada.core.Plugin;
import com.youscada.domain.ConfigData;
import com.youscada.domain.NodePacket;
import com.youscada.domain.ys.YSPacket;
import com.youscada.kafka.producers.KafkaProducer;
import com.youscada.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class PluginManager {

    // read values from config file
    @Value("${kafka.config.start.message}")
    private String startMessage;

    @Value("${kafka.config.stop.message}")
    private String stopMessage;

    @Value("${kafka.config.register.message}")
    private String registerMessage;

    @Value("${kafka.config.unregister.message}")
    private String unregisterMessage;

    @Value("${kafka.config.topic}")
    private String kafkaConfigTopic;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private Plugin plugin;

    @Autowired
    private NodePacket nodePacket;

    @Autowired
    private JsonHelper jsonHelper;

    @Value("${kafka.data.output.key}")
    private String outputDataKey;

    public void dispatch(YSPacket YSPacket) {
        if(nodePacket.isActive()) {
            YSPacket outputPacket = plugin.transform(YSPacket);

            if(outputPacket != null) {
                System.out.println("Writing to output topic");
                System.out.println(nodePacket.getOutputTopicName());
                // You can print the content of this packet using :
                // jsonHelper.toJsonString(outputPacket); //here is as well
                // System.out.println(outputPacket.getValue());
                System.out.println(jsonHelper.toJsonString(outputPacket));

                kafkaProducer.publishMessage(
                        nodePacket.getOutputTopicName(),
                        outputDataKey,
                        jsonHelper.toJsonString(outputPacket)
                );
            }
        }
    }

    public void dispatch(ConfigData configData) {
        String id = nodePacket.getId();
        String receivedId = configData.getNodePacket().getId();

        if(receivedId.equals(id)) {
            if(configData.getKey().equals(startMessage)) {
                System.out.println("Starting");
                nodePacket.setActive(true);
            } else if(configData.getKey().equals(stopMessage)) {
                System.out.println("Stopping");
                nodePacket.setActive(false);
            }
        }
    }

    @PostConstruct
    private void register() {
        kafkaProducer.publishMessage(
                kafkaConfigTopic,
                registerMessage,
                jsonHelper.toJsonString(nodePacket)
        );
    }

    @PreDestroy
    private void shutdown() {
        kafkaProducer.publishMessage(
                kafkaConfigTopic,
                unregisterMessage,
                jsonHelper.toJsonString(nodePacket)
        );
    }

}
