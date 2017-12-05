package com.youscada.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.youscada.domain.ys.YSPacket;


public class TagFilterPlugin implements Plugin {

	private Set<String> tags = new HashSet<String>();
	
	public TagFilterPlugin() {
	}
	
	@Override
	public YSPacket transform(YSPacket ysPacket) {
		
		//Packet pass if has at least one tag from the specified set
		
		Boolean pass = false;
		
		for(String elem : ysPacket.getTags()){
			
			if(this.tags.contains(elem)){
				pass = true;
			}
			
		}
		
		return pass ? ysPacket : null;
	}

	@Override
	public Set<String> getPropertyList() {
		
		return tags;
	}

	@Override
	public String getProperty(String property) {
		//no much sense to impleent here
		if(tags.size() == 0) {
            throw new IllegalArgumentException("No properties");
        }

        if(!tags.contains(property)) {
            throw new IllegalArgumentException("No such property");
        }
		
		return property;
	}

	@Override
	public void setProperty(String property, String value) {
		
		//adding tags to propertu tag
		
		if(property != "tags") {
            throw new IllegalArgumentException("No such property");
        }
		
		tags.add(value);

	}
	
	public void addTag(String tag){
		tags.add(tag);
	}
	
	public void removeTag(String tag){
		
		if(tags.size() == 0) {
            throw new IllegalArgumentException("No tags");
        }

        if(!tags.contains(tag)) {
            throw new IllegalArgumentException("No such tag");
        }
		
        tags.remove(tag);
        
	}

}
