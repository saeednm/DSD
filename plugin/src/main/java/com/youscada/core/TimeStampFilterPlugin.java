package com.youscada.core;

import java.util.HashSet;
import java.util.Set;

import com.youscada.domain.ys.YSPacket;

public class TimeStampFilterPlugin implements Plugin {

	//Filter all packets with specified timestamp
	
	private long timestamp;
	
	public TimeStampFilterPlugin(){
		//initial value of timestamp
		
		this.timestamp=0L;
	}
	
	@Override
	public YSPacket transform(YSPacket ysPacket) {
		// TODO Filter date with timestamap set
		
		//precision is in seconds
		if(ysPacket.getTime().getUtcTimestamp()/1000 == this.timestamp/1000){
			//filter packets with selected timestamp
			return ysPacket;
			
		}
		
		return null;
	}

	@Override
	public Set<String> getPropertyList() {
		
		return new HashSet<String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			{
				add("timestamp");
			}
		};
	}

	@Override
	public String getProperty(String property) {
		
		if(property != "timestamp") {
	    	throw new IllegalArgumentException("No such property");
	    }	
		
		return String.valueOf(this.timestamp);
	}

	@Override
	public void setProperty(String property, String value) {
		if(property != "timestamp") {
	    	throw new IllegalArgumentException("No such property");
	    }
		
		this.timestamp = Long.parseLong(value);
	}

}
