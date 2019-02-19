package io.github.mosser.arduinoml.kernel.generator.config;

import io.github.mosser.arduinoml.kernel.generator.config.json.Config;
import io.github.mosser.arduinoml.kernel.generator.config.json.GraphEntry;

import java.util.ArrayList;
import java.util.List;

public class GraphConfig extends BrickConfig {

    private final String type = "Graph";
    private String GraphId;
    private String color;

    public String getType() {
        return type;
    }

    public String getGraphId() {
        return GraphId;
    }

    public void setGraphId(String graphId) {
        GraphId = graphId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void accept(Config config) {
        List<GraphEntry> entries = config.graphEntries.get(GraphId);
        if (entries == null) {
            entries = new ArrayList<>();
        }
        GraphEntry graphEntry = new GraphEntry();
        graphEntry.setBrickId(getBrickId());
        graphEntry.setColor(getColor());
        entries.add(graphEntry);
        config.graphEntries.put(GraphId, entries);
    }
}
