package com.youscada.domain.ys;

import com.google.common.collect.ImmutableMap;

public class YSMeasureUnit {

    private String measureUnit = UNDEFINED;

    public YSMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit != null && measureUnitNames.containsKey(measureUnit) ? measureUnit : UNDEFINED;
    }

    public YSMeasureUnit(){
        this.measureUnit = UNDEFINED;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit != null && measureUnitNames.containsKey(measureUnit) ? measureUnit : UNDEFINED;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public String getMeasureUnitName() {
        return measureUnitNames.get(this.measureUnit);
    }

    // undefined unit measure
    public static final String UNDEFINED        = "00000000"; // undefined (string)
    // boolean unit measures
    public static final String BOOLEAN          = "01000000"; // boolean
    public static final String DIGITAL_INPUT    = "01000001"; // digital input
    public static final String DIGITAL_OUTPUT   = "01000002"; // digital output
    // number unit measures (double)
    public static final String NUMBER           = "02000000"; // generic number
    public static final String PERCENTAGE       = "02000001"; // percentage
    public static final String CELSIUS          = "02000002"; // celsius
    public static final String FAHRENHEIT        = "02000003"; // fahreneit
    public static final String VOLT             = "02000004"; // voltage
    public static final String AMPERE           = "02000005"; // current
    public static final String MILLI_AMPERE     = "020d0005"; // current - milli
    public static final String MICRO_AMPERE     = "020e0005"; // current - micro
    public static final String AMPERE_HOUR      = "02000006"; // electric charge
    public static final String WATT             = "02000007"; // power
    public static final String MEGA_WATT        = "02070007"; // power - mega
    public static final String KILO_WATT        = "02080007"; // power - kilo
    public static final String WATT_HOUR        = "02000008"; // energy
    public static final String MEGA_WATT_HOUR   = "02070008"; // energy - mega
    public static final String KILO_WATT_HOUR   = "02080008"; // energy - kilo
    public static final String HERTZ            = "02000009"; // frequency
    public static final String MEGA_HERTZ       = "02070009"; // frequency - mega
    // string values
    public static final String STRING           = "03000000"; // generic string
    // object values - CURRENTLY NOT SUPPORTED
    public static final String OBJECT           = "04000000"; // generic object
    public static final String ORDERED_TERN     = "04000001"; // ordered tern {x, y, z}

    // measure unit names
    private static final ImmutableMap<String, String> measureUnitNames = ImmutableMap.<String, String>builder()
            .put(UNDEFINED,        "Undefined")
            .put(BOOLEAN,          "Boolean")
            .put(DIGITAL_INPUT,    "Digital Input")
            .put(DIGITAL_OUTPUT,   "Digital Output")
            .put(NUMBER,           "Number")
            .put(PERCENTAGE,       "Percentage")
            .put(CELSIUS,          "Celsius")
            .put(FAHRENHEIT,       "Fahreneit")
            .put(VOLT,             "Volt")
            .put(AMPERE,           "Ampere")
            .put(MILLI_AMPERE,     "Milliampere")
            .put(MICRO_AMPERE,     "Microampere")
            .put(AMPERE_HOUR,      "Ampere/Hour")
            .put(WATT,             "Watt")
            .put(MEGA_WATT,        "Megawatt")
            .put(KILO_WATT,        "Kilowatt")
            .put(WATT_HOUR,        "Watt/Hour")
            .put(MEGA_WATT_HOUR,   "Megawatt/Hour")
            .put(KILO_WATT_HOUR,   "Kilowatt/Hour")
            .put(HERTZ,            "Hertz")
            .put(STRING,           "String")
            .put(OBJECT,           "Object")
            .put(ORDERED_TERN,    "Ordered Tern")
            .build();



}
