package io.github.mosser.arduinoml.kernel.generator.config;

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
}
