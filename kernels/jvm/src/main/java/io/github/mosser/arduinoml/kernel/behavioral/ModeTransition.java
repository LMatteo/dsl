package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class ModeTransition extends Transition implements Visitable {

    private Mode next;

    public void setNext(Mode nextMode) {
        next = nextMode;
    }

    public Mode getNext() {
        return next;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void setCondition(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
