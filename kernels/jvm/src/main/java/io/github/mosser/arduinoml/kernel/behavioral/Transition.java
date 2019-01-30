package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transition {
    private List<Condition> condition = new ArrayList<>();

    public List<Condition> getCondition() {
        return condition;
    }

    public void setConditions(List<Condition> conditions) {
        this.condition = conditions;
    }

    public void setConditon(Condition conditon) {
        this.condition.add(conditon);
    }

}
