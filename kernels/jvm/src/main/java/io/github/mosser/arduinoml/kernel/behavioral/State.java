package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.NamedElement;
import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class State implements NamedElement, Visitable {

    private String name;
    private List<Action> actions = new ArrayList<Action>();
    private List<StateTransition> stateTransitions = new ArrayList<StateTransition>();
    private Mode mode;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<StateTransition> getStateTransitions() {
        return stateTransitions;
    }

    public void setStateTransition(StateTransition transition) {
        this.stateTransitions.add(transition);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
