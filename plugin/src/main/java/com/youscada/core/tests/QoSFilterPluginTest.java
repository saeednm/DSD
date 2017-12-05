package com.youscada.core.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.youscada.core.QoSFilterPlugin;
import com.youscada.domain.ys.*;
import com.youscada.domain.ys.value.YSValue;

public class QoSFilterPluginTest {

	@Test
	public void test() {
		QoSFilterPlugin qos = new QoSFilterPlugin();
		
		//Packet 0-GOOD 
		YSPacket packet = new YSPacket("device", "dev_id", new YSTime(10, 10, false), 0, null);
		
		//test filter=GOOD
		qos.setProperty("QoS", "GOOD");
		
		//packet GOOD pass
		YSPacket output = qos.transform(packet);
		assertEquals(packet, output);
		
		//pacet BAD dont pass
		packet.setQos(1);
		output = qos.transform(packet);
		
		assertEquals(null, output);
		
		//pacet UNCERTAIN dont pass
		packet.setQos(2);
		output = qos.transform(packet);
				
		assertEquals(null, output);	
		
		//pacet UNKNOWN dont pass
		packet.setQos(3);
		output = qos.transform(packet);
						
		assertEquals(null, output);	
	}
	
	@Test
	public void testProperties(){
		
		QoSFilterPlugin qos = new QoSFilterPlugin();
		
		Set<String> output = qos.getPropertyList();
		
		assertEquals(1, output.size());
		
		assertEquals("QoS", output.iterator().next());
		
		qos.setProperty("QoS", "UNCERTAIN");
		
		assertEquals("UNCERTAIN", qos.getProperty("QoS"));
	}

}
