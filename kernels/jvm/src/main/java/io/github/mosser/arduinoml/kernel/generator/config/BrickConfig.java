package io.github.mosser.arduinoml.kernel.generator.config;

public abstract class BrickConfig {
    private String brickId;

    public String getBrickId() {
        return brickId;
    }

    public void setBrickId(String brickId) {
        this.brickId = brickId;
    }
}
