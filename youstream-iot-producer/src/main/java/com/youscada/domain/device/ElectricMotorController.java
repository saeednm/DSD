package com.youscada.domain.device;

import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSMeasureUnit;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.YSTime;
import com.youscada.domain.ys.value.YSNumberValue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by lorenzoaddazi on 06/12/16.
 */
public class ElectricMotorController implements IDevice {

    private String deviceId = "emc_" + new Random().nextInt(1000);
    private String datapointId = "dtpt_" + new Random().nextInt(1000);
    private List<String> tags = Arrays.asList("electric motor controller", "frequency");

    @Override
    public List<YSPacket> generateData() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        /* YSPacket#1 - Frequency - Hertz */
        YSNumberValue frequencyValue = new YSNumberValue(new Random().nextFloat() % 99.0f);
        YSData frequencyData = new YSData(frequencyValue);
        frequencyData.setMeasureUnit(new YSMeasureUnit("02000009"));
        YSPacket frequencyPacket = new YSPacket(this.deviceId, this.datapointId,
                new YSTime((Long)System.currentTimeMillis(), 1, false),
                new Random().nextInt(4),
                Arrays.asList(frequencyData),
                this.tags);

        return Arrays.asList(frequencyPacket);

    }

}