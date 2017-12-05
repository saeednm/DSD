package com.youscada.domain.ys;

import com.youscada.domain.ys.value.YSValue;

public class YSData {

    private YSValue value;
    private YSMeasureUnit measureUnit = new YSMeasureUnit();

    /**
     * Constructor - Mandatory Fields Only
     * TODO : Safe Checks
     **/
    public YSData(YSValue value) {
        this.value = value;
        this.measureUnit = new YSMeasureUnit(); // UNDEFINED (00000000)
    }

    public void setValue(YSValue value) {
        this.value = value;
    }

    public YSValue getValue () {
        return this.value;
    }

    public void setMeasureUnit(YSMeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public YSMeasureUnit getMeasureUnit(){
        return this.measureUnit;
    }

}

