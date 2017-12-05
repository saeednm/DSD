package com.youscada.domain.ys.value;

// YSString : Generic string value
public class YSStringValue extends YSValue<String> {

    public YSStringValue(String value) {
        super(value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
