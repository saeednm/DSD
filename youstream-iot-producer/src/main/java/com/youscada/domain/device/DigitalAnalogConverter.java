package com.youscada.domain.device;

import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSMeasureUnit;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.YSTime;
import com.youscada.domain.ys.value.YSBooleanValue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by lorenzoaddazi on 06/12/16.
 */
public class DigitalAnalogConverter implements IDevice {

    private String deviceId = "acd_" + new Random().nextInt(1000);
    private String datapointId = "dtpt_" + new Random().nextInt(1000);
    private List<String> tags = Arrays.asList("digital-to-analog converter");
    @Override
    public List<YSPacket> generateData() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        /* YSPacket#1 - Received Digital Value - Digital Input */
        YSBooleanValue digitalInputValue = new YSBooleanValue(new Random().nextBoolean());
        YSData digitalInputData = new YSData(digitalInputValue);
        digitalInputData.setMeasureUnit(new YSMeasureUnit("01000001"));
        YSPacket digitalInputPacket =
                new YSPacket(this.deviceId, this.datapointId,
                        new YSTime((Long)System.currentTimeMillis(), 1, false),
                        new Random().nextInt(4),
                        Arrays.asList(digitalInputData),
                        this.tags);

        return Arrays.asList(digitalInputPacket);
    }
}
