package com.youscada.core;

import com.youscada.domain.ys.YSPacket;

import java.util.Set;

public interface Plugin {
    // core method that transforms the packet
    // if you want to do nothing with the packet,
    // i.e in filtering, just return null
    YSPacket transform(YSPacket ysPacket);

    // you can use a map of properties to configure
    // your plugin. Here just return all the configuration
    // parameters in a list. This is for REST API you dont
    // need to worry about it too much for now
    // if no parameters just return empty set
    Set<String> getPropertyList();

    // if required property is not present
    // throw an exception
    String getProperty(String property);

    void setProperty(String property, String value);
}
