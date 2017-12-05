package com.youscada.kafka.consumers.field;

import com.youscada.PluginManager;
import com.youscada.domain.ys.YSPacket;
import com.youscada.util.JsonHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class FieldDataListener {

    // duplicate properties, but library requires them to be constant
    // so cant be loaded from config file :(
    private static final String KAFKA_TOPIC = "fieldRawDataTopic";
    private static final String LISTENER_ID = "transformPlugin12";

    @Autowired
    private PluginManager pluginManager;

    @Autowired
    private JsonHelper jsonHelper;

    @KafkaListener(id = LISTENER_ID, topics = KAFKA_TOPIC)
    public void listen(ConsumerRecord<?, ?> record) {
        YSPacket YSPacket = null;
        try {
            YSPacket = transformToInputData(record);
            pluginManager.dispatch(YSPacket);
        } catch (ParseException e) {
            System.out.println(
                    String.format("Error while parsing %s", record.value().toString())
            );
            e.printStackTrace();
        }
    }

    private YSPacket transformToInputData(ConsumerRecord<?, ?> record) throws ParseException {
        YSPacket YSPacket = jsonHelper.YSPacketFromJson(record.value().toString());
        return YSPacket;
    }

}
