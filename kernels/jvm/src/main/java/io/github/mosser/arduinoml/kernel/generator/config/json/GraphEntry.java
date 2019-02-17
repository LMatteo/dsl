package io.github.mosser.arduinoml.kernel.generator.config.json;

public class GraphEntry implements ConfigEntry {

    private String brickId;
    private String color;

    public String getBrickId() {
        return brickId;
    }

    public void setBrickId(String brickId) {
        this.brickId = brickId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
