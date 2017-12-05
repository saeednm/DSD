package com.youscada.core.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.youscada.core.DayRangeFilterPlugin;
import com.youscada.domain.ys.*;

public class DayRangeFilterPluginTest {

	@Test
	public void test() {

		DayRangeFilterPlugin dtplugin = new DayRangeFilterPlugin();
		
		//define packets
		YSPacket packet = new YSPacket("device", "dev_id", new YSTime(1482226365000L, 1, false), 0, null); // 11:32:45
		YSPacket packet1 = new YSPacket("device", "dev_id", new YSTime(1482262365000L, 1, false), 1, null);// 21:32:45
		YSPacket packet2 = new YSPacket("device", "dev_id", new YSTime(1482247965000L, 1, false), 0, null);// 17:32:45
		YSPacket packet3 = new YSPacket("device", "dev_id", new YSTime(1482247965000L, 1, true), 0, null);// 18:32:45
		
		//dtplugin config
		dtplugin.setProperty("startTimeH", "9");
		dtplugin.setProperty("startTimeM", "30");
		dtplugin.setProperty("endTimeH", "18");
		dtplugin.setProperty("endTimeM", "00");
		
		//tests
		YSPacket output = dtplugin.transform(packet);		
		assertEquals(packet, output);
		
		output = dtplugin.transform(packet1);		
		assertEquals(null, output);
		
		output = dtplugin.transform(packet2);		
		assertEquals(packet2, output);
		
		output = dtplugin.transform(packet3);		
		assertEquals(null, output);
	}

}
