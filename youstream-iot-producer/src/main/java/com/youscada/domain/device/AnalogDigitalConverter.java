package com.youscada.domain.device;

import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSMeasureUnit;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.YSTime;
import com.youscada.domain.ys.value.YSBooleanValue;
import com.youscada.domain.ys.value.YSNumberValue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by lorenzoaddazi on 06/12/16.
 */
public class AnalogDigitalConverter implements IDevice {

    private String deviceId = "adc_" + new Random().nextInt(1000);
    private String datapointId = "dtpt_" + new Random().nextInt(1000);
    private List<String> tags = Arrays.asList("analog-to-digital converter");
    @Override
    public List<YSPacket> generateData() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        /* YSPacket#1 - Converted Digital Value - Digital Output */
        YSBooleanValue digitalOutputValue = new YSBooleanValue(new Random().nextBoolean());
        YSData digitalOutputData = new YSData(digitalOutputValue);
        digitalOutputData.setMeasureUnit(new YSMeasureUnit("01000002"));
        YSPacket digitalOutputPacket =
                new YSPacket(this.deviceId, this.datapointId,
                        new YSTime((Long)System.currentTimeMillis(), 1, false),
                        new Random().nextInt(4),
                        Arrays.asList(digitalOutputData),
                        this.tags);

        return Arrays.asList(digitalOutputPacket);
    }
}
