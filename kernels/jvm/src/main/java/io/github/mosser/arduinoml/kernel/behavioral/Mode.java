package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.NamedElement;
import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Mode implements NamedElement, Visitable {
    private String name;
    private List<State> states = new ArrayList<>();
    private List<ModeTransition> modeTransitions = new ArrayList<>();
    private State initial;

    public void addState(State state) {
        states.add(state);
        state.setMode(this);
    }

    public void addModeTransitions(ModeTransition modeTransition) {
        modeTransitions.add(modeTransition);
    }

    public List<ModeTransition> getModeTransitions() {
        return modeTransitions;
    }

    public List<State> getStates() {
        return states;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public State getInitial() {
        return initial;
    }

    public void setInitial(State initial) {
        this.initial = initial;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
