package com.youscada.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.value.YSValue;

public class NConsecutiveOccurrencePlugin implements Plugin {
	private Map<String, Double> properties = new HashMap<>();
	int counter;
	
	public NConsecutiveOccurrencePlugin(){
		properties.put("Threshold", 0.5);
		properties.put("Number", 2.0);
		counter=0;
	}

	@Override
	public YSPacket transform(YSPacket ysPacket) {
		System.out.println("Consecutive plugin");
		YSValue currentvalue= ysPacket.getValues().get(0).getValue();
		Double packet_value = null;
	    packet_value = (Double) currentvalue.getValue();
	        	
	    if(packet_value > properties.get("Threshold"))
	       {
	        		System.out.println("exceed happened");
	        		counter++;
	        		if(counter== (properties.get("Number").intValue())){
	        			ysPacket.getTags().clear();
	                    if(ysPacket.getTags().isEmpty()){
	                    	ysPacket.addTag("Consecutive Occurrence of Threshold exceeds" );
	                    }
	        				counter=0;
	        				return ysPacket;
	        		}
	        		//return ysPacket;
	       } else {counter=0;}
	    
	    
		return null;
	}

	@Override
	public Set<String> getPropertyList() {
		return properties.keySet();
	}

	@Override
	public String getProperty(String property) {
		if(properties.size() == 0) {
			throw new IllegalArgumentException("No properties");
	    }

		if(!properties.keySet().contains(property)) {
			throw new IllegalArgumentException("No such property");
		}
		
		return String.valueOf(properties.get(property));	

	}

	@Override
	public void setProperty(String property, String value) {
		if(!properties.keySet().contains(property)) {
            throw new IllegalArgumentException("No such time property");
        }
		
		properties.put(property, Double.valueOf(value));

	}

}
