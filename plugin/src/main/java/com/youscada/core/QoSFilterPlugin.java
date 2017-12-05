package com.youscada.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import com.youscada.domain.ys.YSPacket;

public class QoSFilterPlugin implements Plugin{

	 // qos -quality of service of datapoint	
	// Properties : 0=GOOD, 1=BAD, 2=UNCERTAIN, 3=UNKNOWN
	
	//Here we have only one "QoS" : "GOOD" for example
	private Map<String, String> properties = new HashMap<>();
	
	
	private Map<String, Integer> prop_values = new HashMap<String, Integer>();	
	
	
	private static final String qos = "QoS";
	
	
	public QoSFilterPlugin(){
		//initial filter good QoS packets
		properties.put(qos, "GOOD");
		
		//initialize prop_Values
		prop_values.put("GOOD", 0);
		prop_values.put("BAD", 1);
		prop_values.put("UNCERTAIN", 2);
		prop_values.put("UNKNOWN", 3);
	}
	

	@Override
	public YSPacket transform(YSPacket ysPacket) {
		// Filter Plugin by quality of service
		// For example if GOOD Qos is set
		// Whe check if ysPacket == "QoS" -> "GOOD" -> 0
		if(ysPacket.getQos() == this.prop_values.get(this.getProperty(qos)))
			return ysPacket;
		else
			return null;
	}
	
	@Override
	public void setProperty(String property, String value) {
		
		//Change QoS Filter
		//For example :
		// setProperty("QoS","BAD")
		// setProperty("QoS","GOOD")
		
		if(!properties.keySet().contains(property)) {
            throw new IllegalArgumentException("No such property");
        }
		
		properties.put(property, value);
		
	}

	@Override
	public String getProperty(String property) {
		
		//Only possible is property: "QoS"
		//Returns : GOOD, BAD, UNCERTAIN or UNKNOWN
		
		if(properties.size() == 0) {
            throw new IllegalArgumentException("No properties");
        }

        if(!properties.keySet().contains(property)) {
            throw new IllegalArgumentException("No such property");
        }

        return properties.get(property);
	}
	
	@Override
	public Set<String> getPropertyList() {
		return properties.keySet();
	}



}
