package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.NamedElement;
import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Mode implements NamedElement, Visitable {

    List<State> states = new ArrayList<>();
    List<ModeTransition> modeTransitions = new ArrayList<>();

    public void addState(State state) {
        states.add(state);
    }

    public void addModeTransitions(ModeTransition modeTransition) {
        modeTransitions.add(modeTransition);
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
