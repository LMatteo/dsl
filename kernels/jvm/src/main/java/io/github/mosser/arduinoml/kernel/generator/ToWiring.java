package io.github.mosser.arduinoml.kernel.generator;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Map;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {

    private final static String CURRENT_STATE = "current_state";
    private final static String CURRENT_MODE = "current_mode";

    public ToWiring() {
        this.result = new StringBuffer();
    }

    private void w(String s) {
        result.append(String.format("%s\n", s));
    }

    private void wnl(String s) {
        result.append(s);
    }

    @Override
    public void visit(App app) {
        w("// Wiring code generated from an ArduinoML model");
        w(String.format("// Application name: %s\n", app.getName()));

        w("void setup(){");
        for (Brick brick : app.getBricks()) {
            brick.accept(this);
        }
        w("}\n");

        w("long time = 0; long debounce = 200; long last_transition_time = 0;\n");

        for (Mode mode : app.getModes()) {
            mode.accept(this);
        }

        if (app.getInitial() != null) {
            w("void loop() {");
            w(String.format("  mode_%s();", app.getInitial().getName()));
            w("}");
        }
    }

    @Override
    public void visit(Actuator actuator) {
        w(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]", actuator.getPin(), actuator.getName()));
    }


    @Override
    public void visit(Sensor sensor) {
        w(String.format("  pinMode(%d, INPUT);  // %s [Sensor]", sensor.getPin(), sensor.getName()));
    }

    @Override
    public void visit(State state) {
        w(String.format("void state_%s_%s() {", state.getName(), ((Mode) context.get(CURRENT_MODE)).getName()));
        for (Action action : state.getActions()) {
            action.accept(this);
        }

        w("  boolean guard = millis() - time > debounce;");
        context.put(CURRENT_STATE, state);
        for (ModeTransition modeTransition : state.getMode().getModeTransitions()) {
            modeTransition.accept(this);
        }
        for (StateTransition stateTransition : state.getStateTransitions()) {
            stateTransition.accept(this);
        }

        w(String.format("  state_%s_%s();", ((State) context.get(CURRENT_STATE)).getName(), ((Mode) context.get(CURRENT_MODE)).getName()));
        w("}\n");
    }

    @Override
    public void visit(Mode mode) {
        context.put(CURRENT_MODE, mode);
        for (State state : mode.getStates()) {
            state.accept(this);
        }
    }

    @Override
    public void visit(ModeTransition modeTransition) {
        wnl("  if (");
        for (Condition c : modeTransition.getCondition()) {
            c.accept(this);
            wnl(" &&");
        }
        w(" guard){");
        w("    time = millis();");
        w("    last_transition_time = millis();");
        w(String.format("    state_%s_%s();", modeTransition.getNext().getInitial().getName(), modeTransition.getNext().getName()));
        w("  }");
    }

    @Override
    public void visit(StateTransition stateTransition) {
        wnl("  if (");
        for (Condition c : stateTransition.getCondition()) {
            c.accept(this);
            wnl(" &&");
        }
        w(" guard){");
        w("    time = millis();");
        w("    last_transition_time = millis();");
        w(String.format("    state_%s_%s();", stateTransition.getNext().getName(), stateTransition.getNext().getMode().getName()));
        w("  }");
    }

    @Override
    public void visit(TimeCondition timeCondition) {
        wnl(String.format(" ( millis() - last_transition_time > %d )", timeCondition.getTime()));
    }

    @Override
    public void visit(SensorCondition sensorCondition) {
        wnl(String.format(" digitalRead(%d) == %s", sensorCondition.getSensor().getPin(), sensorCondition.getValue()));
    }

    @Override
    public void visit(Action action) {
        w(String.format("  digitalWrite(%d,%s);", action.getActuator().getPin(), action.getValue()));
    }

}
