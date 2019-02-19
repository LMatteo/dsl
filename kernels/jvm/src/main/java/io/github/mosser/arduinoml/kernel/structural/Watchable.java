package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.generator.config.BrickConfig;

import java.util.ArrayList;
import java.util.List;

public abstract class Watchable  {


    private List<BrickConfig> configs = new ArrayList<>();


    public abstract String generate();

    public void addConfig(BrickConfig config) { this.configs.add(config); }


    public List<BrickConfig> getConfigs() { return this.configs; }

}
