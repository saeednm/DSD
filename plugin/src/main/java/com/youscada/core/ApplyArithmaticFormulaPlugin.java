package com.youscada.core;

import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSMeasureUnit;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.value.YSValue;

import java.util.*;

/**
 * Created by saeed on 12/9/16.
 */
public class ApplyArithmaticFormulaPlugin implements Plugin{

    public ApplyArithmaticFormulaPlugin() {
        properties.put("A", 2.0);
        properties.put("B", 0.0);
    }

    @Override
    public YSPacket transform(YSPacket ysPacket) {
        if (ysPacket.getValues().isEmpty())
            return null;

        List<YSData> packetValues=ysPacket.getValues();
        YSValue currentvalue= ysPacket.getValues().get(0).getValue();

        Double d = null;
        if (currentvalue.getValue() instanceof Double) {
            d = (Double) currentvalue.getValue();
        }else if (currentvalue.getValue() instanceof Integer){
            d = Double .valueOf((Integer) currentvalue.getValue());
        }
        else{
            return null;
        }

        Double resultValue = d * properties.get("A")+ properties.get("B");

        YSValue ConvertedValue = new YSValue(resultValue) {
            @Override
            public void setValue(Object value) {
                super.setValue(value);
            }
        }   ;


        ConvertedValue.setValue(resultValue);

//        currentvalue.setValue(resultValue);

        YSData convertedData= new YSData(ConvertedValue);
        convertedData.setMeasureUnit(packetValues.get(0).getMeasureUnit());

        packetValues.clear();
        packetValues.add(convertedData);

        return ysPacket;
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
        return String.valueOf(properties.get(property));
    }

    @Override
    public void setProperty(String property, String value) {
        properties.put(property, Double.parseDouble(value));
    }

    private Map<String, Double> properties = new HashMap<>();
}

