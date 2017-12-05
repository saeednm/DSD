package com.youscada.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.youscada.domain.ys.YSPacket;

public class DateTimeRangeFilterPlugin implements Plugin {
	//Date string format is: dd/MM/yyyy HH:mm
	//For example: 19/11/2015 10:25

	
	Map<String,Date> properties = new HashMap<>();
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	
	public DateTimeRangeFilterPlugin(){
		
		this.properties.put("startDate", new Date());
		this.properties.put("endDate", new Date());
		
	}
	
	@Override
	public YSPacket transform(YSPacket ysPacket) {
		
		long utc_timestamp = ysPacket.getTime().getUtcTimestamp();
		utc_timestamp += ysPacket.getTime().getUtcOffset() * 3600000;
		
		if(ysPacket.getTime().isDst()){
			
			utc_timestamp += 3600000;
			
		}
		
		//date of ysPacket
		//Date datum = new Date(utc_timestamp * 1000);
		Date datum = new Date(utc_timestamp);
		
		if(datum.after(this.properties.get("startDate")) 
				&& datum.before(this.properties.get("endDate"))){
			
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
		
        if(!properties.keySet().contains(property)) {
            throw new IllegalArgumentException("No such property");
        }
		
		return this.formatter.format(properties.get(property));
	}

	@Override
	public void setProperty(String property, String value) {
		
		if(!properties.keySet().contains(property)) {
            throw new IllegalArgumentException("No such property");
        }
		
		Date datum;
		
		//check if it is correct date format
		try{
			
			datum = this.formatter.parse(value);
			this.properties.put(property, datum);
			
		}catch(Exception ex){
			
			System.out.println("Wrong date format: " + ex.getMessage());
			
		}
	}

}
