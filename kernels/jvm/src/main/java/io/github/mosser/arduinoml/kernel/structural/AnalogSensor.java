package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class AnalogSensor extends Sensor {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String generate() {
        return String.format("Serial.print(\",%s:\");Serial.print(analogRead(%s));", getName(), getPin());
    }
}
