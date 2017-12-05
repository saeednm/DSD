package com.youscada.domain.ys.value;

// YSNumber : Generico numerical value (Float to cover both integers and decimal values)
public class YSNumberValue extends YSValue<Double> {

    public YSNumberValue(double value){
        super(value);
    }

}