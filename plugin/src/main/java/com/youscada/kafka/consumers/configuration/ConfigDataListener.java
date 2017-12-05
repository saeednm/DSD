package com.youscada.kafka.consumers.configuration;

import com.youscada.PluginManager;
import com.youscada.domain.ConfigData;
import com.youscada.util.JsonHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class ConfigDataListener {

    // duplicate properties, but library requires them to be constant
    // so cant be loaded from config file :(
    private static final String KAFKA_TOPIC = "pluginConfigTopic";
    private static final String LISTENER_ID = "transformPlugin1";

    @Autowired
    private PluginManager pluginManager;

    @Autowired
    private JsonHelper jsonHelper;

    @KafkaListener(id = LISTENER_ID, topics = KAFKA_TOPIC)
    public void listen(ConsumerRecord<?, ?> record) {
        try {
            ConfigData configData = transformToConfigData(record);
            pluginManager.dispatch(configData);
        } catch (Exception e) {
            System.out.println(
                    String.format("Error while parsing %s", record.value().toString())
            );
            e.printStackTrace();
        }
    }

    private ConfigData transformToConfigData(ConsumerRecord<?, ?> record) throws ParseException {
        ConfigData configData = new ConfigData();

        configData.setKey(record.key() == null ? "noop" : record.key().toString());

        String value = record.value().toString();
        configData.setNodePacket(jsonHelper.nodePacketFromJson(value));

        return configData;
    }

}
