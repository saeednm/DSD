package com.youscada.domain.ys;

// unit : packet
import java.util.ArrayList;
import java.util.List;

// YSPacket : YouStream Packet [type : DATAPOINT(0)]
public class YSPacket {

    // payload attributes
    private String deviceId;

    private String datapointId;

    private YSTime time;

    private long qos;

    private List<YSData> values;

    private List<String> tags;

    /**
     * Constructor - Mandatory Fields Only
     * TODO : Safe checks
     **/
    public YSPacket(String deviceId, String datapointId, YSTime time, long qos, List<YSData> values) {
        this.deviceId = deviceId;
        this.datapointId = datapointId;
        this.time = time;
        this.qos = qos;
        this.values = values;
        this.tags = new ArrayList<String>();
    }

    /**
     * Constructor - All fields
     * TODO : Safe Checks
     **/
    public YSPacket(String deviceId, String datapointId, YSTime time, long qos, List<YSData> values, List<String> tags) {
        this.deviceId = deviceId;
        this.datapointId = datapointId;
        this.time = time;
        this.qos = qos;
        this.values = values;
        this.tags = tags;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDatapointId(String datapointId) {
        this.datapointId = datapointId;
    }

    public String getDatapointId() {
        return this.datapointId;
    }

    public void setTime(YSTime time) {
        this.time = time;
    }

    public YSTime getTime() {
        return this.time;
    }

    public void setQos(long qos) {
        this.qos = qos;
    }

    public long getQos() {
        return this.qos;
    }

    public void addValue(YSData value) {
        this.values.add(value);
    }

    public List<YSData> getValues() {
        return this.values;
    }

    public void addTag(String tag) {
        System.out.println(tag);
        this.tags.add(tag);
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    public List<String> getTags(){
        return this.tags;
    }

}

