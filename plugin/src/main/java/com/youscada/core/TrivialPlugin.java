package com.youscada.core;

import com.youscada.domain.ys.YSPacket;

import java.util.*;

/*
* Main data transformation class
* Plugin does its core job here.
* */
public class TrivialPlugin implements Plugin {

    public YSPacket transform(YSPacket ysPacket) {
        // currently trivial transformation
        // just returns the same packet
        return ysPacket;
    }

    public void setProperty(String property, String value) {
        properties.put(property, value);
    }

    public String getProperty(String property) {
        if(properties.size() == 0) {
            throw new IllegalArgumentException("No properties");
        }

        if(!properties.keySet().contains(property)) {
            throw new IllegalArgumentException("No such property");
        }

        return properties.get(property);
    }

    public Set<String> getPropertyList() {
        return properties.keySet();
    }

    private Map<String, String> properties = new HashMap<>();

}
