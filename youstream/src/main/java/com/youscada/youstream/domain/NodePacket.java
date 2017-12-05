package com.youscada.youstream.domain;

public class NodePacket {
    private String id;
    private String name;
    private String description;
    private boolean isActive;
    private String inputTopicName;
    private String outputTopicName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getInputTopicName() {
        return inputTopicName;
    }

    public void setInputTopicName(String inputTopicName) {
        this.inputTopicName = inputTopicName;
    }

    public String getOutputTopicName() {
        return outputTopicName;
    }

    public void setOutputTopicName(String outputTopicName) {
        this.outputTopicName = outputTopicName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodePacket that = (NodePacket) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "NodePacket{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", inputTopicName='" + inputTopicName + '\'' +
                ", outputTopicName='" + outputTopicName + '\'' +
                '}';
    }
}
