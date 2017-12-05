package com.youscada.core.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.youscada.core.TagFilterPlugin;
import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.YSTime;
import com.youscada.domain.ys.value.YSValue;

public class TagFilterPluginTest {

	@Test
	public void test() {
		TagFilterPlugin tags = new TagFilterPlugin();
		
		//define packets
		YSPacket packet = new YSPacket("device", "dev_id", new YSTime(10, 10, false), 0, null);
		YSPacket packet1 = new YSPacket("device", "dev_id", new YSTime(10, 10, false), 0, null);
		YSPacket packet2 = new YSPacket("device", "dev_id", new YSTime(10, 10, false), 0, null);
		
		packet.addTag("Milano");
		packet1.addTag("Torino");
		packet2.addTag("Milano");
		packet2.addTag("Torino");
		
		//define tags on filtering plugin
		tags.addTag("Milano");
		
		//test
		YSPacket output = tags.transform(packet);
		assertEquals(packet, output);
		
		output = tags.transform(packet1);
		assertEquals(null, output);
		
		output = tags.transform(packet2);
		assertEquals(packet2, output);
	}

}
