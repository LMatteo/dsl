package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class StateTransition extends Transition implements Visitable {
    private State next;

    public State getNext() {
        return next;
    }

    public void setNext(State next) {
        this.next = next;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
