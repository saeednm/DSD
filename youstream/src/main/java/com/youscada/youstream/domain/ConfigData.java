package com.youscada.youstream.domain;

public class ConfigData {
    private String key;

    private NodePacket nodePacket;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public NodePacket getNodePacket() {
        return nodePacket;
    }

    public void setNodePacket(NodePacket nodePacket) {
        this.nodePacket = nodePacket;
    }
}
