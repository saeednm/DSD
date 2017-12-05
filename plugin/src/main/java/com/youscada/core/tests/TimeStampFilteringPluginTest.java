package com.youscada.core.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.youscada.core.TimeStampFilterPlugin;
import com.youscada.domain.ys.*;

public class TimeStampFilteringPluginTest {

	@Test
	public void test() {
		TimeStampFilterPlugin tsplugin = new TimeStampFilterPlugin();
		
		//define packets
		YSPacket packet = new YSPacket("device", "dev_id", new YSTime(1482193965000L, 1, false), 0, null);
		YSPacket packet1 = new YSPacket("device", "dev_id", new YSTime(10, 10, false), 1, null);
		YSPacket packet2 = new YSPacket("device", "dev_id", new YSTime(1482193965000L, 1, false), 0, null);
		
		//timestamp plugin config
		
		tsplugin.setProperty("timestamp", "1482193965000");
		
		//tests
		
		YSPacket output = tsplugin.transform(packet);
		assertEquals(packet, output);
		
		output = tsplugin.transform(packet1);
		assertEquals(null, output);
		
		output = tsplugin.transform(packet2);
		assertEquals(packet2, output);
	}

}
