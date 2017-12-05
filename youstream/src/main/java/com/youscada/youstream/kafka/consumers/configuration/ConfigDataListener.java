package com.youscada.youstream.kafka.consumers.configuration;

import com.youscada.youstream.YouStreamCore;
import com.youscada.youstream.domain.ConfigData;
import com.youscada.youstream.util.JsonHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class ConfigDataListener {

    final static Logger logger = Logger.getLogger(ConfigDataListener.class);

    private static final String KAFKA_TOPIC = "pluginConfigTopic";
    private static final String LISTENER_ID = "pluginManager";

    @Autowired
    private YouStreamCore youStreamCore;

    @Autowired
    private JsonHelper jsonHelper;

    @KafkaListener(id = LISTENER_ID, topics = KAFKA_TOPIC)
    public void listen(ConsumerRecord<?, ?> record) {
        try {
            ConfigData configData = transformToConfigData(record);
            youStreamCore.dispatch(configData);
        } catch (ParseException e) {
            logger.error(String.format(
                    "Could not parse %s", record.value().toString()
            ));
        }
    }

    private ConfigData transformToConfigData(ConsumerRecord<?, ?> record) throws ParseException {
        ConfigData configData = new ConfigData();

        configData.setKey(record.key() == null ? "noop" : record.key().toString());
        configData.setNodePacket(
                jsonHelper.nodePacketFromJson(record.value().toString())
        );

        return configData;
    }

}
