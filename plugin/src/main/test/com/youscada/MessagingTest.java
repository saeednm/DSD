package com.youscada;

import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.YSTime;
import com.youscada.domain.ys.value.YSNumberValue;
import com.youscada.domain.ys.value.YSValue;
import com.youscada.kafka.producers.KafkaProducer;
import com.youscada.util.JsonHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessagingTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private JsonHelper jsonHelper;

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

    @Test
    public void testEmptyYSPacketJsonString() {
        String json = "{\"qos\":0,\"values\":[{\"value\":123, \"measureUnit\":\"volt\"}],\"time\":" +
                "{\"utcTimestamp\":123,\"utcOffset\":1,\"isDst\":true}," +
                "\"datapointId\":\"mydatapoint\",\"deviceId\":\"mydevice\",\"tags\":[\"mytag\"]}";

        kafkaProducer.publishMessage("fieldRawDataTopic", "xxx", json);
    }

    @Test
    public void testEmptyYSPacket() {
        List<YSData> ysDataList = new ArrayList<>();

        YSNumberValue ysNumberValue = new YSNumberValue(123f);
        ysNumberValue.setMeasureUnit(new YSMeasureUnit("02000000"));
        ysDataList.add(new YSData( ysNumberValue ));

        List<String> tags = new ArrayList<>();
        tags.add("mytag");

        YSPacket ysPacket = new YSPacket("mydevice", "mydatapoint", new YSTime(123, 1, true), 0, ysDataList, tags);
        kafkaProducer.publishMessage("fieldRawDataTopic", "xxx", jsonHelper.toJsonString(ysPacket));
    }

}
