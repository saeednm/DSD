package com.youscada.domain.device;

import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSMeasureUnit;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.YSTime;
import com.youscada.domain.ys.value.YSNumberValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by lorenzoaddazi on 06/12/16.
 */
public class PerceivedTemperatureSensor implements IDevice{

    private String deviceId = "pts_" + new Random().nextInt(1000);
    private String datapointId = "dtpt_" + new Random().nextInt(1000);
    private List<String> tags = Arrays.asList("temperature", "humidity", "sensor");

    @Override
    public List<YSPacket> generateData() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        /* YSPacket#1 - Temperature - Fahrenheit */
        YSNumberValue tFahreneitValue = new YSNumberValue((new Random().nextFloat() % 181.0f) + 32.0f);
        YSData tFahreneitData = new YSData(tFahreneitValue);
        tFahreneitData.setMeasureUnit(new YSMeasureUnit("02000003"));
        YSPacket tFahrenheit = new YSPacket(this.deviceId, this.datapointId,
                        new YSTime((Long)System.currentTimeMillis(), 1, false),
                        new Random().nextInt(4),
                        Arrays.asList(tFahreneitData),
                        this.tags);

        /* YSPacket#2 - Temperature - Celsius */
        YSNumberValue tCelsiusValue = new YSNumberValue(new Random().nextFloat() % 101.0f);
        YSData tCelsiusData = new YSData(tCelsiusValue);
        tCelsiusData.setMeasureUnit(new YSMeasureUnit("02000002"));
        YSPacket tCelsius =
                new YSPacket(this.deviceId, this.datapointId,
                        new YSTime((Long)System.currentTimeMillis(), 1, false),
                        new Random().nextInt(4),
                        Arrays.asList(tCelsiusData),
                        this.tags);

        /* YSPacket#3 - Humidity - Percentage */
        YSNumberValue humidityValue = new YSNumberValue(new Random().nextFloat() % 101.0f);
        YSData humidityData = new YSData(humidityValue);
        humidityData.setMeasureUnit(new YSMeasureUnit("02000001"));
        YSPacket humidity =
                new YSPacket(this.deviceId, this.datapointId,
                        new YSTime((Long)System.currentTimeMillis(), 1, false),
                        new Random().nextInt(4),
                        Arrays.asList(humidityData),
                        this.tags);

        /* YSPacket#4 - Battery Level */
        YSNumberValue batteryLevelValue = new YSNumberValue(new Random().nextFloat() % 101.0f);
        YSData batterLevelData = new YSData(batteryLevelValue);
        batterLevelData.setMeasureUnit(new YSMeasureUnit("02000001"));
        YSPacket batteryLevel =
                new YSPacket(this.deviceId, this.datapointId,
                        new YSTime((Long)System.currentTimeMillis(), 1, false),
                        new Random().nextInt(4),
                        Arrays.asList(batterLevelData),
                        this.tags);

        /* YS Packets - Aggregate and Return */
        return Arrays.asList(tCelsius, tFahrenheit, humidity, batteryLevel);
    }

}
