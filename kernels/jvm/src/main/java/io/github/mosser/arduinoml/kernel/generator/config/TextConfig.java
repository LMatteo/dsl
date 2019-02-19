package io.github.mosser.arduinoml.kernel.generator.config;

import io.github.mosser.arduinoml.kernel.generator.config.json.Config;
import io.github.mosser.arduinoml.kernel.generator.config.json.TextEntry;

public class TextConfig extends BrickConfig {
    public final String type = "Text";

    public String getType() {
        return type;
    }

    @Override
    public void accept(Config config) {
        TextEntry textEntry = new TextEntry();
        textEntry.setBrickId(this.getBrickId());
        config.textEntries.add(textEntry);
    }
}
