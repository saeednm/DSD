package com.youscada.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.value.*;

public class ThresholdExceedPlugin implements Plugin {
	private Map<String, Double> properties = new HashMap<>();
	
	public ThresholdExceedPlugin(){
		properties.put("Threshold", 0.5);
		
	}
	

	@Override
	public YSPacket transform(YSPacket ysPacket) {
		System.out.println("threshold plugin");
		YSValue currentvalue= ysPacket.getValues().get(0).getValue();
		 Double packet_value = null;
	       
	            packet_value = (Double) currentvalue.getValue();
	        	if(packet_value > properties.get("Threshold"))
	        	{
	        		ysPacket.getTags().clear();
	                if(ysPacket.getTags().isEmpty()){
	                	ysPacket.addTag("Threshold exceeded");
	                }
	        		return ysPacket;
	        	} 
	        
				
		return null;
	}

	@Override
	public Set<String> getPropertyList() {
		return properties.keySet();
	}

	@Override
	public String getProperty(String property) {
		// A property of having threshold
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
