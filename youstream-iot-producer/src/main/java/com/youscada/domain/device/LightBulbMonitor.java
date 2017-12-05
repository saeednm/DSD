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
public class LightBulbMonitor implements IDevice {

    private String deviceId = "lbm_" + new Random().nextInt(1000);
    private String datapointId = "dtpt_" + new Random().nextInt(1000);
    private List<String> tags = Arrays.asList("light bulb", "energy consumption", "light intensity level");

    @Override
    public List<YSPacket> generateData() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        /* YSPacket#1 - Light Intensity Level - Percentage */
        YSNumberValue lightIntensityValue = new YSNumberValue(new Random().nextFloat() % 101.0f);
        YSData lightIntensityData = new YSData(lightIntensityValue);
        lightIntensityData.setMeasureUnit(new YSMeasureUnit("02000001"));
        YSPacket lightIntensityPacket =
                new YSPacket(this.deviceId, this.datapointId,
                        new YSTime((Long)System.currentTimeMillis(), 1, false),
                        new Random().nextInt(4),
                        Arrays.asList(lightIntensityData),
                        this.tags);

        /* YSPacket#2 - Energy Consumption - (0-99 Milli)Watt/Hour */
        YSNumberValue energyConsumptionValue = new YSNumberValue(new Random().nextFloat() % 100.0f);
        YSData energyConsumptionData = new YSData(energyConsumptionValue);
        energyConsumptionData.setMeasureUnit(new YSMeasureUnit("020d0008"));
        YSPacket energyConsumptionPacket =
                new YSPacket(this.deviceId, this.datapointId,
                        new YSTime((Long)System.currentTimeMillis(), 1, false),
                        new Random().nextInt(4),
                        Arrays.asList(energyConsumptionData),
                        this.tags);

        return Arrays.asList(lightIntensityPacket, energyConsumptionPacket);
    }
}
