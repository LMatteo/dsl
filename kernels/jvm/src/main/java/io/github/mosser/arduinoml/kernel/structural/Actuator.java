package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Actuator extends Brick {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String generate() {
        return String.format("Serial.print(\",%s:\");Serial.print(digitalRead(%s));", getName(), getPin());
    }
}
