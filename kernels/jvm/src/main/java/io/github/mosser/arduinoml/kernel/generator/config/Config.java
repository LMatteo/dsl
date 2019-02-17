package io.github.mosser.arduinoml.kernel.generator.config;

import io.github.mosser.arduinoml.kernel.structural.Brick;
import io.github.mosser.arduinoml.kernel.structural.Watchable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    public Map<String, List<String>> modes = new HashMap<>();
    public List<BrickConfig> watchables = new ArrayList<>();

    public void addState(String modeName, String stateName) {
        List<String> states = modes.get(modeName);
        if (states == null) {
            states = new ArrayList<>();
        }
        states.add(stateName);
        modes.put(modeName, states);
    }

    public void addWatchable(Watchable watchable) {
        this.watchables.add(watchable.getConfig());
    }


}
