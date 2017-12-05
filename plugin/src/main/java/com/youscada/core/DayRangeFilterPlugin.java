package com.youscada.core;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.youscada.domain.ys.YSPacket;

public class DayRangeFilterPlugin implements Plugin {
	//For example: from 8 to 10 every day
	//time range from 0-24
	
	
	private Map<String, Integer> properties = new HashMap<>();
	
	public DayRangeFilterPlugin(){
		
		properties.put("startTimeH", 0);
		properties.put("startTimeM", 0);
		properties.put("endTimeH", 23);
		properties.put("endTimeM", 0);
		
	}
	
	@Override
	public YSPacket transform(YSPacket ysPacket) {
		
		//create date object - packet
		long tmp = ysPacket.getTime().getUtcTimestamp();
		long offset = ysPacket.getTime().getUtcOffset();
		
		
		if (ysPacket.getTime().isDst())
			tmp = tmp + (offset + 1) * 3600000;
		else 
			tmp = tmp + offset * 3600000;
		
		Date packet = new Date(tmp);
		//check hours and minutes
		
		
		int hour = packet.getHours();
		int minutes = packet.getMinutes();
		
		//packet inside boundaries
		
		if(properties.get("startTimeH") == properties.get("endTimeH")){
			
			if( hour == properties.get("startTimeH") && minutes >= properties.get("startTimeM") && 
					minutes <= properties.get("endTimeM"))
				
				return ysPacket;
			
		}else if(hour > properties.get("startTimeH") && hour < properties.get("endTimeH")){
			
			return ysPacket;
			
		}else if(hour == properties.get("startTimeH") && minutes >= properties.get("startTimeM")){
			
			return ysPacket;
			
		} else if(hour == properties.get("endTimeH") && minutes <= properties.get("endTimeM")){
			
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
		// Two properties available: startTime and endTime
		
		if(!properties.keySet().contains(property)) {
			throw new IllegalArgumentException("No such property");
		}
		
		return String.valueOf(properties.get(property));	
		
	}

	@Override
	public void setProperty(String property, String value) {
		//update a property
		if(!properties.keySet().contains(property)) {
            throw new IllegalArgumentException("No such time property");
        }
		
		properties.put(property, Integer.parseInt(value));		
		
	}

}
