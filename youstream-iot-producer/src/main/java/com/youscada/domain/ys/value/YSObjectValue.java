package com.youscada.domain.ys.value;

import java.util.Map;

// YSObject : Generic object value encapsulating multiple fields (es. geographical position, 3D point)
public class YSObjectValue extends YSValue<Map<String, String>> {

    public YSObjectValue(Map<String, String> value) {
        super(value);
    }
    public void setValue(Map<String, String> value) {
        this.value = value;
    }

    public Map<String, String> getValue(){
        return this.value;
    }

    public void addAttribute(String name, String value) {
        this.value.put(name, value);
    }

    public void removeAttribute(String name) {
        this.value.remove(name);
    }

    public String getAttributeValue(String name) {
        return this.value.get(name);
    }

}
