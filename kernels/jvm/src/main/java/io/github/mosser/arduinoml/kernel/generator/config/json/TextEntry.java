package io.github.mosser.arduinoml.kernel.generator.config.json;

public class TextEntry implements ConfigEntry {
    private String brickId;

    public String getBrickId() {
        return brickId;
    }

    public void setBrickId(String brickId) {
        this.brickId = brickId;
    }

}
