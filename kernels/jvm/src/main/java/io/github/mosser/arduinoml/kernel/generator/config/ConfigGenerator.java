package io.github.mosser.arduinoml.kernel.generator.config;

import com.google.gson.Gson;
import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.generator.config.json.Config;

public class ConfigGenerator {


    public String generateConfig(App app) {
        Config config = new Config();
        app.getWatchs().forEach(config::addWatchable);
        app.getModes().forEach(mode -> mode.getStates().forEach(state -> config.addState(mode.getName(), state.getName())));
        return new Gson().toJson(config);
    }

}
