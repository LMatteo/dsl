package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.AnalogSensor;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;

public class AnalogSensorCondition implements Condition {
    private AnalogSensor sensor;

    private boolean greater;

    private int value;

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(AnalogSensor sensor) {
        this.sensor = sensor;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isGreater() {
        return greater;
    }

    public void setGreater(boolean greater) {
        this.greater = greater;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
