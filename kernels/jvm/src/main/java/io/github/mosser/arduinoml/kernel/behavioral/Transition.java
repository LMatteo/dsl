package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transition implements Visitable {
    private List<Condition> condition = new ArrayList<>();
    private State next;

    public State getNext() {
        return next;
    }

    public void setNext(State next) {
        this.next = next;
    }

    public List<Condition> getCondition() {
        return condition;
    }

    public void setConditon(Condition conditon) {
        this.condition.add(conditon);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
