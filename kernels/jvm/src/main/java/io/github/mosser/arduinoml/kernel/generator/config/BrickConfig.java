package io.github.mosser.arduinoml.kernel.generator.config;

import io.github.mosser.arduinoml.kernel.generator.config.json.Config;

public abstract class BrickConfig {
    private String brickId;

    public String getBrickId() {
        return brickId;
    }

    public void setBrickId(String brickId) {
        this.brickId = brickId;
    }

    public abstract void accept(Config config);
}
