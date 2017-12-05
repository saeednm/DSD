package com.youscada.domain.ys.value;

// YSBooleanValue : Generic boolean value
public class YSBooleanValue extends YSValue<Boolean> {


    public YSBooleanValue(Boolean value){
        super(value);
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return this.value;
    }

}
