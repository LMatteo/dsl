package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;

import java.util.HashMap;
import java.util.Map;

public class SensorCondition implements Condition {
    private Map<Sensor, SIGNAL> sensorValue = new HashMap<>();

    public Map<Sensor, SIGNAL> getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(Sensor sensor, SIGNAL value) {
        this.sensorValue.put(sensor, value);
    }
}
