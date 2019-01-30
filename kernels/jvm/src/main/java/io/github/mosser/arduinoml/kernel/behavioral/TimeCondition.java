package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class TimeCondition implements Condition {
    private int time;

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
