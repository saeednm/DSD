package com.youscada.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.youscada.domain.ys.YSPacket;

public class TimePeriodExceededNoDataReceivedPlugin implements Plugin {
	private Map<String, Object> properties = new HashMap<>();
	Timer timer;
	TimerTask timertask;
	
	public TimePeriodExceededNoDataReceivedPlugin(){
		properties.put("timeSeconds", 15L);
		
	}

	private Long lastTime = null;
	@Override
	public YSPacket transform(YSPacket ysPacket) {
		
		
		if(lastTime == null) {
            lastTime = System.currentTimeMillis();
        }

        long currentTime = System.currentTimeMillis();
        if(isNewPeriod(currentTime)) {	
            lastTime = currentTime;
            ysPacket.getTags().clear();
            if(ysPacket.getTags().isEmpty()){
            	ysPacket.addTag("Time period exceeded no data received");
            }
            	return ysPacket;
        }
		
		return null;
		
	}
	
	private boolean isNewPeriod(long currentTime) {
        return currentTime - lastTime >= 1000L * (Long)properties.get("timeSeconds");
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

        return properties.get(property).toString();
	}

	@Override
	public void setProperty(String property, String value) {
		properties.put(property, value);

	}

}
