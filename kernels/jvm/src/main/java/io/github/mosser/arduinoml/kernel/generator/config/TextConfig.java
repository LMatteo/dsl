package io.github.mosser.arduinoml.kernel.generator.config;

public class TextConfig extends BrickConfig {
    public final String type = "Text";
    public String BrickId;

    public String getType() {
        return type;
    }

    public String getBrickId() {
        return BrickId;
    }

    public void setBrickId(String brickId) {
        BrickId = brickId;
    }
}
