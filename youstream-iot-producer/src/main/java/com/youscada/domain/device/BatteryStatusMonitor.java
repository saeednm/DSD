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
public class BatteryStatusMonitor implements IDevice {

    private String deviceId = "bsm_" + new Random().nextInt(1000);
    private String datapointId = "dtpt_" + new Random().nextInt(1000);
    private List<String> tags = Arrays.asList("battery status monitor", "voltage", "current", "power", "electric charge");

    @Override
    public List<YSPacket> generateData() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        /* YSPacket#1 - Voltage (milli)Volt */
        YSNumberValue voltageValue = new YSNumberValue(new Random().nextFloat() % 101.0f);
        YSData voltageData = new YSData(voltageValue);
        voltageData.setMeasureUnit(new YSMeasureUnit("020d0004"));
        YSPacket voltagePacket = new YSPacket(this.deviceId, this.datapointId,
                new YSTime((Long)System.currentTimeMillis(), 1, false),
                new Random().nextInt(4),
                Arrays.asList(voltageData),
                this.tags);

        /* YSPacket#1 - Current (milli)Ampere */
        YSNumberValue currentValue = new YSNumberValue(new Random().nextFloat() % 101.0f);
        YSData currentData = new YSData(currentValue);
        currentData.setMeasureUnit(new YSMeasureUnit("020d0007"));
        YSPacket currentPacket = new YSPacket(this.deviceId, this.datapointId,
                new YSTime((Long)System.currentTimeMillis(), 1, false),
                new Random().nextInt(4),
                Arrays.asList(currentData),
                this.tags);

        /* YSPacket#1 - Power (kilo)Watt */
        YSNumberValue powerValue = new YSNumberValue(new Random().nextFloat() % 101.0f);
        YSData powerData = new YSData(powerValue);
        powerData.setMeasureUnit(new YSMeasureUnit("02080007"));
        YSPacket powerPacket = new YSPacket(this.deviceId, this.datapointId,
                new YSTime((Long)System.currentTimeMillis(), 1, false),
                new Random().nextInt(4),
                Arrays.asList(powerData),
                this.tags);

        /* YSPacket#1 - Electric Charge (ampere/hour) */
        YSNumberValue electricChargeValue = new YSNumberValue(new Random().nextFloat() % 101.0f);
        YSData electricChargeData = new YSData(electricChargeValue);
        electricChargeData.setMeasureUnit(new YSMeasureUnit("02000006"));
        YSPacket electricChargePacket = new YSPacket(this.deviceId, this.datapointId,
                new YSTime((Long)System.currentTimeMillis(), 1, false),
                new Random().nextInt(4),
                Arrays.asList(electricChargeData),
                this.tags);

        return Arrays.asList(voltagePacket, currentPacket, powerPacket, electricChargePacket);

    }
}
