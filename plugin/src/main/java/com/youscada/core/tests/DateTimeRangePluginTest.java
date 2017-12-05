package com.youscada.core.tests;

import static org.junit.Assert.*;

import com.youscada.core.DateTimeRangeFilterPlugin;
import com.youscada.domain.ys.*;

import org.junit.Test;

public class DateTimeRangePluginTest {

	@Test
	public void test() {
		
		DateTimeRangeFilterPlugin dtr_plugin = new DateTimeRangeFilterPlugin();
		
		//define packets
		YSPacket packet = new YSPacket("device", "dev_id", new YSTime(1482139965000L, 1, false), 0, null); // 19/12/2016 11:32:45
		YSPacket packet1 = new YSPacket("device", "dev_id", new YSTime(1482262365000L, 1, false), 1, null);// 20/12/2016 21:32:45
		YSPacket packet2 = new YSPacket("device", "dev_id", new YSTime(1482219165000L, 1, false), 0, null);// 20/12/2016 08:32:45
		YSPacket packet3 = new YSPacket("device", "dev_id", new YSTime(1482247965000L, 1, true), 0, null);// 20/12/2016 18:32:45
		
		//dtplugin config
		dtr_plugin.setProperty("startDate", "19/12/2016 11:33");
		dtr_plugin.setProperty("endDate", "20/12/2016 20:33");
		
		//tests
		YSPacket output = dtr_plugin.transform(packet);		
		assertEquals(null, output);
		
		output = dtr_plugin.transform(packet1);		
		assertEquals(null, output);
		
		output = dtr_plugin.transform(packet2);		
		assertEquals(packet2, output);
		
		output = dtr_plugin.transform(packet3);		
		assertEquals(packet3, output);
	}

}
