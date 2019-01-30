package io.github.mosser.arduinoml.kernel.behavioral;

import java.util.ArrayList;
import java.util.List;

public class ModeTransition {

    public List<Condition>  conditions = new ArrayList<>();
    public Mode next;

    public void setNext(Mode nextMode) {
        next = nextMode;
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }


    public void setCondition(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
