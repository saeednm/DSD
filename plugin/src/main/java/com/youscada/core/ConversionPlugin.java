package com.youscada.core;

import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSMeasureUnit;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.value.YSNumberValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by saeed on 12/4/16.
 */
public class ConversionPlugin implements Plugin{

    public ConversionPlugin() {
        properties.put("prefix", "MEGA");
    }


    @Override
    public YSPacket transform(YSPacket ysPacket) {

        // Return null (no new packet) if the packet does not contain any value
        if (ysPacket.getValues().isEmpty())
            return null;

        // Return null if the packet does not contain numerical values
        if (ysPacket.getValues().get(0).getValue() instanceof YSNumberValue)
            return null;

        // current value
        YSNumberValue currentValue = (YSNumberValue)ysPacket.getValues().get(0).getValue(); // YSNumberValue
        // current measure unit
        YSMeasureUnit currentMeasureUnit = ysPacket.getValues().get(0).getMeasureUnit();
        // current Prefix
        String currentPrefix = currentMeasureUnit.getMeasureUnit().substring(2,4);

        // new prefix
        String newPrefix = properties.containsKey("prefix") ?
                prefixesCodec.get(properties.get("prefix")) : "00";
        // new measure unit
        String newMeasureUnitString = currentMeasureUnit.getMeasureUnit().substring(0,2) +
                newPrefix + currentMeasureUnit.getMeasureUnit().substring(5);
        YSMeasureUnit newMeasureUnit = new YSMeasureUnit(newMeasureUnitString);
        // new value
        YSNumberValue newValue = new YSNumberValue(currentValue.getValue() *
                calculateCoefficient(Integer.parseInt(currentPrefix),
                        Integer.parseInt(newPrefix)));
        // new data
        YSData newData = new YSData(newValue);
        newData.setMeasureUnit(newMeasureUnit);

        // add converted data to packet values
        ysPacket.getValues().clear();
        ysPacket.addValue(newData);

        // return converted packet
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

        return properties.get(property);
    }

    @Override
    public void setProperty(String property, String value) {

            properties.put(property, value);
    }

    // prefixes
    private static final int YOTTA = 10;
    private static final int ZETTA = 9;
    private static final int EXA = 8;
    private static final int PETA = 7;
    private static final int TERA = 6;
    private static final int GIGA = 5;
    private static final int MEGA = 4;
    private static final int KILO = 3;
    private static final int HECTO = 2;
    private static final int DECA = 1;
    private static final int DECI = -1;
    private static final int CENTI = -2;
    private static final int MILLI = -3;
    private static final int MICRO = -4;
    private static final int NANO = -5;
    private static final int PICO = -6;
    private static final int FEMTO = -7;
    private static final int ATTO = -8;
    private static final int ZEPTO = -9;
    private static final int YOCTO = -10;

    private int[] coefficients = {YOTTA,ZETTA,EXA,PETA,TERA,GIGA,MEGA,KILO,HECTO,DECA,
            DECI,CENTI,MILLI,MICRO,NANO,PICO,FEMTO,ATTO,ZEPTO,YOCTO};

    private static final Map<String, Integer> prefixes ;
    static
    {
        prefixes = new HashMap<String, Integer>();
        prefixes.put("YOTTA", 10);
        prefixes.put("ZETTA", 9);
        prefixes.put("EXA", 8);
        prefixes.put("PETA", 7);
        prefixes.put("TERA", 6);
        prefixes.put("GIGA", 5);
        prefixes.put("MEGA", 4);
        prefixes.put("KILO", 3);
        prefixes.put("HECTO", 2);
        prefixes.put("DECA", 1);
        prefixes.put("DECI", -1);
        prefixes.put("CENTI", -2);
        prefixes.put("MILLI", -3);
        prefixes.put("MICRO", -4);
        prefixes.put("NANO", -5);
        prefixes.put("PICO", -6);
        prefixes.put("FEMTO", -7);
        prefixes.put("ATTO", -8);
        prefixes.put("ZEPTO", -9);
        prefixes.put("YOCTO", -10);

    }
    private static final Map<String, String> prefixesCodec ;
    static
    {
        prefixesCodec = new HashMap<String, String>();
        prefixesCodec.put("YOTTA",  "01");
        prefixesCodec.put("ZETTA",  "02");
        prefixesCodec.put("EXA",    "03");
        prefixesCodec.put("PETA",   "04");
        prefixesCodec.put("TERA",   "05");
        prefixesCodec.put("GIGA",   "06");
        prefixesCodec.put("MEGA",   "07");
        prefixesCodec.put("KILO",   "08");
        prefixesCodec.put("HECTO",  "09");
        prefixesCodec.put("DECA",   "0a");
        prefixesCodec.put("DECI",   "0b");
        prefixesCodec.put("CENTI",  "0c");
        prefixesCodec.put("MILLI",  "0d");
        prefixesCodec.put("MICRO",  "0e");
        prefixesCodec.put("NANO",   "0f");
        prefixesCodec.put("PICO",   "10");
        prefixesCodec.put("FEMTO",  "11");
        prefixesCodec.put("ATTO",   "12");
        prefixesCodec.put("ZEPTO",  "13");
        prefixesCodec.put("YOCTO",  "14");

    }



    private  Double calculateCoefficient ( int first , int second ) {
        if ((first - second) < 10 || (first - second) > -10) {
            return Math.pow(10, (first - second));
        }
        return 0.0;
    }
    private Map<String, String> properties = new HashMap<>();
}
