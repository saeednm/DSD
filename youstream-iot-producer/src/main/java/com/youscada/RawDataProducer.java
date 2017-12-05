package com.youscada;

import com.youscada.domain.device.*;
import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.YSTime;
import com.youscada.domain.ys.value.YSBooleanValue;
import com.youscada.domain.ys.value.YSStringValue;
import com.youscada.util.JsonHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

public class RawDataProducer {

	// Device Types
	private static final Map<String, String> deviceTypes = new HashMap<String, String>();
	private static final Map<String, Class> deviceClasses = new HashMap<String, Class>();

	static {
		/* Perceived Temperature Sensor - Sends temperature (fahrenheit and celsius), humidity (percentage) and battery level (percentage) values */
		deviceTypes.put("Perceived Temperature Sensor", "PTS");
		deviceClasses.put("PTS", PerceivedTemperatureSensor.class);
		/* Analog-to-Digital Converter - Sends converted digital output values */
		deviceTypes.put("Analog/Digital Converter", "ADC");
		deviceClasses.put("ADC", AnalogDigitalConverter.class);
		/* Digital-to-Analog Converter - Sends received digital input values */
		deviceTypes.put("Digital/Analog Converter", "DAC");
		deviceClasses.put("DAC", DigitalAnalogConverter.class);
		/* Light Bulb Monitor - Sends light intensity level (percentage) and energy consumption (watt/hour) values */
		deviceTypes.put("Light Bulb Monitor", "LBM");
		deviceClasses.put("LBM", LightBulbMonitor.class);
		/* Electric Motor Controller - Sends frequency values (hertz) */
		deviceTypes.put("Electric Motor Controller", "EMC");
		deviceClasses.put("EMC", ElectricMotorController.class);
		/* Battery Status Monitor - Sends voltage (volt), current (ampere), power (watt) and electric charge values (ampere/hour) */
		deviceTypes.put("Battery Status Monitor", "BSM");
		deviceClasses.put("BSM", BatteryStatusMonitor.class);

	}

	public static void main(String[] args) {

		String deviceType = args.length > 0 && args[0] != null? args[0] : null;
		if (deviceType != null && deviceTypes.containsValue(deviceType)) {

		    /* Setup and Create Kafka Producer */
			Properties properties = new Properties();
			properties.put("bootstrap.servers", "localhost:9092");
			properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			properties.put("serializer.class", "org.apache.kafka.common.serialization.StringSerializer");
			KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

			/* Listen for Shutdown Signals (e.g. CTRL + C) */
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					kafkaProducer.close();
				}
			});

			/* Delegate YSPacket(s) Construction to Device */
			try {
				IDevice device = (IDevice)deviceClasses.get(deviceType).newInstance();
				while(true) {
					// TimeUnit.SECONDS.sleep(1);
					device.generateData().forEach( ysPacket -> {
						kafkaProducer.send(new ProducerRecord<String, String>("fieldRawDataTopic", "deviceId-datapointId-timestamp", new JsonHelper().toJsonString(ysPacket)));
					});
				}
			} catch (InstantiationException | IllegalAccessException | InterruptedException e) { /* Do Nothing */ }

		}

	}

}
