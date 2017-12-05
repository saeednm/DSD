package com.youscada.util;

import com.youscada.domain.ys.YSData;
import com.youscada.domain.ys.YSMeasureUnit;
import com.youscada.domain.ys.YSPacket;
import com.youscada.domain.ys.YSTime;
import com.youscada.domain.ys.value.YSBooleanValue;
import com.youscada.domain.ys.value.YSNumberValue;
import com.youscada.domain.ys.value.YSStringValue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    private JSONParser jsonParser = new JSONParser();

    /** Serialize YSPacket to JSON **/
    public String toJsonString(YSPacket ysPacket) {
        return toJsonObject(ysPacket).toJSONString();
    }

    /** Translate List<T> to JSONArray **/
    private <T> JSONArray toJsonObjects(java.util.List<T> list) {
        // Init Json Array
        JSONArray jsonArray = new JSONArray();

        // Fill the array with YSData -> JSONObject
        for (T listItem : list) {
            jsonArray.add(toJsonObject(listItem));
        }

        // Return Json Array
        return jsonArray;
    }

    private <T> Object toJsonObject(T ysElement) {
        if(ysElement instanceof YSData) {
            return toJsonObject((YSData)ysElement);
        } else if (ysElement instanceof YSTime) {
            return toJsonObject((YSTime)ysElement);
        } else if (ysElement instanceof YSPacket) {
            return toJsonObject((YSPacket)ysElement);
        } else if (ysElement instanceof String) {
            return ysElement;
        }
        return null;
    }

    /** Translate YSData to JSON Object **/
    private JSONObject toJsonObject(YSData ysData) {
        // Init Json Object
        JSONObject ysDataJsonObject = new JSONObject();

        // Value (Boolean, Float, String) - Object still not managed
        ysDataJsonObject.put("value", ysData.getValue().getValue());
        // Unit (byte[] -> String)

//        String measureUnitCodecString = "";
//        if (ysData.getMeasureUnit() != null) {
//            measureUnitCodecString = "0x";
//            for (byte unitCodecByte : ysData.getMeasureUnit().getMeasureUnitCodec()) {
//                measureUnitCodecString += String.format("%20X", unitCodecByte);
//            }
//        }

        // ysDataJsonObject.put("unit", measureUnitCodecString);
        ysDataJsonObject.put("unit", ysData.getMeasureUnit().getMeasureUnit());

        // Return Json Object
        return ysDataJsonObject;
    }

    /** Translate YSTime to JSON Object **/
    private JSONObject toJsonObject(YSTime ysTime) {
        // Init Json Object
        JSONObject ysTimeJsonObject = new JSONObject();

        // UTC Timestamp (Integer)
        ysTimeJsonObject.put("utcTimestamp", ysTime.getUtcTimestamp());
        // UTC Offset (Integer)
        ysTimeJsonObject.put("utcOffset", ysTime.getUtcOffset());
        // DST (Boolean)
        ysTimeJsonObject.put("isDst", ysTime.isDst());

        // Return Json Object
        return ysTimeJsonObject;
    }

    private JSONObject toJsonObject(YSPacket ysPacket) {
        // Init Json Object
        JSONObject ysPacketJsonObject = new JSONObject();

        // Device Id (String)
        ysPacketJsonObject.put("deviceId", ysPacket.getDeviceId());
        // Datapoint Id (String)
        ysPacketJsonObject.put("datapointId", ysPacket.getDatapointId());
        // Time (YSTime)
        ysPacketJsonObject.put("time", toJsonObject(ysPacket.getTime()));
        // QOS (Integer)
        ysPacketJsonObject.put("qos", ysPacket.getQos());
        // Values ([YSData])
        ysPacketJsonObject.put("values", toJsonObjects(ysPacket.getValues()));
        // Tags ([String])
        ysPacketJsonObject.put("tags", toJsonObjects(ysPacket.getTags()));

        // Return Json Object
        return ysPacketJsonObject;
    }

    public YSPacket YSPacketFromJson(String ysPacketJsonString) throws ParseException {
        /* Parse JSON Object */
        JSONObject ysPacketJsonObject = (JSONObject) this.jsonParser.parse(ysPacketJsonString);
        /*  Create YSPacket from JSON Object */
        return YSPacketFromJsonObject(ysPacketJsonObject);
    }

    /** Retrieve YSPacket from JSON Object **/
    private YSPacket YSPacketFromJsonObject(JSONObject ysPacketJsonObject) {

        /* Create YSPacket using Mandatory Fields - deviceId, datapointId, time, qos, values */
        List<YSData> ysPacketValues = new ArrayList<YSData>();
        for (Object valueJsonObject : (JSONArray)ysPacketJsonObject.get("values")) {
            ysPacketValues.add(YSDataFromJsonObject((JSONObject)valueJsonObject));
        }

        YSPacket ysPacket = new YSPacket((String)ysPacketJsonObject.get("deviceId"),
                (String)ysPacketJsonObject.get("datapointId"),
                YSTimeFromJsonObject((JSONObject) ysPacketJsonObject.get("time")),
                (Long)ysPacketJsonObject.get("qos"),
                ysPacketValues);

        /* Retrieve Optional Fields - tags */
        if (ysPacketJsonObject.containsKey("tags")) {
            for (Object tag : (JSONArray)ysPacketJsonObject.get("tags")) {
                ysPacket.addTag((String)tag);
            }
        }
        /* Return YSPacket */
        return ysPacket;
    }

    /** Retrieve YSTime from JSON Object **/
    private YSTime YSTimeFromJsonObject(JSONObject ysTimeJsonObject) {
        return new YSTime((Long)ysTimeJsonObject.get("utcTimestamp"),
                (Long)ysTimeJsonObject.get("utcOffset"),
                (Boolean)ysTimeJsonObject.get("isDst"));
    }

    /** Retrieve YSData from JSON Object **/
    private YSData YSDataFromJsonObject(JSONObject ysDataJsonObject) {
        /* Check Measure Unit (Does it exist? What is its value?) */
        if (ysDataJsonObject.containsKey("unit")) {
            /* Create YSData with Mandatory and Optional Fields - Unit, Value */
            YSMeasureUnit ysMeasureUnit = new YSMeasureUnit((String)ysDataJsonObject.get("unit"));
            switch(ysMeasureUnit.getMeasureUnit().substring(0, 3) + "000000") {
                // boolean unit measure value
                case YSMeasureUnit.BOOLEAN: {
                    YSData ysData = new YSData(new YSBooleanValue((Boolean) ysDataJsonObject.get("value")));
                    ysData.setMeasureUnit(ysMeasureUnit);
                    return ysData;
                }
                // number unit measure value
                case YSMeasureUnit.NUMBER: {
                    YSData ysData = new YSData(new YSNumberValue((Double)ysDataJsonObject.get("value")));
                    ysData.setMeasureUnit(ysMeasureUnit);
                    return ysData;
                }
                // string unit measure value
                case YSMeasureUnit.STRING: {
                    YSData ysData = new YSData(new YSStringValue(ysDataJsonObject.get("value").toString()));
                    ysData.setMeasureUnit(ysMeasureUnit);
                    return ysData;
                }
                // undefined unit measure value
                case YSMeasureUnit.UNDEFINED: {
                    YSData ysData = new YSData(new YSStringValue(ysDataJsonObject.get("value").toString()));
                    return ysData;
                }

            }
/*
            String ysDataMeasureUnitString = ((String)ysDataJsonObject.get("unit")).replaceAll("0x", "");
            byte[] ysDataMeasureUnitCodec = new byte[4];
            for (int i = 0, j = 0; j + 2 <= ysDataMeasureUnitString.length(); i += 1, j += 2) {
                ysDataMeasureUnitCodec[i] = Byte.parseByte(ysDataMeasureUnitString.substring(j, j + 2));
            }
            YSMeasureUnit ysMeasureUnit = new YSMeasureUnit(ysDataMeasureUnitCodec);
*/

            /* Infer YSValue from YSMeasureUnit */
/*
            switch (Byte.valueOf(ysMeasureUnit.getMeasureUnitCodec()[3]).intValue()) {
                case YSMeasureUnit.BOOLEAN: {
                    YSData ysData = new YSData(new YSBooleanValue((Boolean) ysDataJsonObject.get("value")));
                    ysData.setMeasureUnit(ysMeasureUnit);
                    return ysData;
                }
                case YSMeasureUnit.NUMBER: {
                    YSData ysData = new YSData(new YSNumberValue((Float) ysDataJsonObject.get("value")));
                    ysData.setMeasureUnit(ysMeasureUnit);
                    return ysData;
                }
                case YSMeasureUnit.STRING: {
                    YSData ysData = new YSData(new YSStringValue((String)ysDataJsonObject.get("value")));
                    ysData.setMeasureUnit(ysMeasureUnit);
                    return ysData;
                }
                // case YSMeasureUnit.OBJECT: {
                // TODO : Object values not yet supported
                // }
                default:
                    // this should not happen ...
                    return null;
            }
*/

        }

        /* Create YSData with Mandatory Fields Only - Unit (Default : YSStringValue, UNDEFINED) */
        return new YSData(new YSStringValue(ysDataJsonObject.get("value").toString()));
    }

}
