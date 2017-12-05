package com.youscada.util;

import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.YSTime;
import com.youscada.domain.ys.value.YSBooleanValue;
import com.youscada.domain.ys.value.YSStringValue;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lorenzoaddazi on 02/12/16.
 */
public class JsonHelperTest extends TestCase {
    public void testToJsonString() throws Exception {

        String deviceId = "testDeviceId";
        String datapointId = "testDatapointId";
        YSTime time = new YSTime(1, 2, true);
        int qos = 1;
        List<YSData> values = new ArrayList<YSData>();
        YSStringValue testValue1 = new YSStringValue("testValue1");
        YSBooleanValue testValue2 = new YSBooleanValue(true);
        YSData testData1 = new YSData(testValue1);
        YSData testData2 = new YSData(testValue2);
        values.add(testData1);
        values.add(testData2);
        List<String> tags = new ArrayList<String>();
        tags.add("testTag1");
        tags.add("testTag2");
        YSPacket ysPacket = new YSPacket(deviceId, datapointId, time, qos, values, tags);
        JsonHelper help = new JsonHelper();
        System.out.println(help.toJsonString(ysPacket));
    }

    public void testYSPacketFromJson() throws Exception {
        String deviceId = "testDeviceId";
        String datapointId = "testDatapointId";
        YSTime time = new YSTime(1, 2, true);
        int qos = 1;
        List<YSData> values = new ArrayList<YSData>();
        YSStringValue testValue1 = new YSStringValue("testValue1");
        YSBooleanValue testValue2 = new YSBooleanValue(true);
        YSData testData1 = new YSData(testValue1);
        System.out.println(testData1.getMeasureUnit());
        YSData testData2 = new YSData(testValue2);
        values.add(testData1);
        values.add(testData2);
        List<String> tags = new ArrayList<String>();
        tags.add("testTag1");
        tags.add("testTag2");
        YSPacket ysPacket = new YSPacket(deviceId, datapointId, time, qos, values, tags);
        JsonHelper help = new JsonHelper();
        System.out.println(help.toJsonString(ysPacket));

        System.out.println(help.YSPacketFromJson(help.toJsonString(ysPacket)));

    }

}