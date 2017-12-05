package com.youscada.youstream.util;

import com.youscada.youstream.domain.NodePacket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonHelper {
    public NodePacket nodePacketFromJson(String json) throws ParseException {
        NodePacket nodePacket = new NodePacket();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json);
        JSONObject jsonObject = (JSONObject) obj;

        if(jsonObject.get("id") != null) {
            nodePacket.setId((String) jsonObject.get("id"));
        }

        if(jsonObject.get("name") != null) {
            nodePacket.setName((String) jsonObject.get("name"));
        }

        if(jsonObject.get("description") != null) {
            nodePacket.setDescription((String) jsonObject.get("description"));
        }

        if(jsonObject.get("isActive") != null) {
            nodePacket.setActive((boolean) jsonObject.get("isActive"));
        }

        if(jsonObject.get("outputTopicName") != null) {
            nodePacket.setOutputTopicName((String) jsonObject.get("outputTopicName"));
        }

        if(jsonObject.get("inputTopicName") != null) {
            nodePacket.setInputTopicName((String) jsonObject.get("inputTopicName"));
        }

        return nodePacket;
    }
}
