package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.generator.config.BrickConfig;

public abstract class Watchable  {


    private BrickConfig config;

    public abstract String generate();

    public void setConfig(BrickConfig config) {
        this.config = config;
    }

    public BrickConfig getConfig() {
        return this.config;
    }
}
