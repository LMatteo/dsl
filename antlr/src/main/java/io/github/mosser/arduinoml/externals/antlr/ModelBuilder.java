package io.github.mosser.arduinoml.externals.antlr;

import io.github.mosser.arduinoml.externals.antlr.grammar.*;


import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.Actuator;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelBuilder extends ArduinomlBaseListener {

    /********************
     ** Business Logic **
     ********************/

    private App theApp = null;
    private boolean built = false;

    public App retrieve() {
        if (built) { return theApp; }
        throw new RuntimeException("Cannot retrieve a model that was not created!");
    }

    /*******************
     ** Symbol tables **
     *******************/

    private Map<String, Sensor>       sensors     = new HashMap<>();
    private Map<String, Actuator>     actuators   = new HashMap<>();
    private Map<String, State>        states      = new HashMap<>();
    private List<Binding>             bindings    = new ArrayList<>();

    private class Binding { // used to support state resolution for transitions
        String state;
        String to; // name of the next state, as its instance might not have been compiled yet
        List<Condition> conditions;
    }


    private State currentState = null;

    /**************************
     ** Listening mechanisms **
     **************************/

    @Override
    public void enterRoot(ArduinomlParser.RootContext ctx) {
        built = false;
        theApp = new App();
    }

    @Override public void exitRoot(ArduinomlParser.RootContext ctx) {
        // Resolving states in transitions
        bindings.forEach((binding) ->  {
            Transition t = new Transition();
            t.setConditions(binding.conditions);
            t.setNext(states.get(binding.to));
            states.get(binding.state).setTransition(t);
        });
        this.built = true;
    }

    @Override
    public void enterDeclaration(ArduinomlParser.DeclarationContext ctx) {
        theApp.setName(ctx.name.getText());
    }

    private void checkPinNumber(int pinNumber) {
        if (pinNumber <= 0 || pinNumber > 12) {
            throw new RuntimeException("pin number should be a value between 1 and 12 included");
        }
    }

    @Override
    public void enterSensor(ArduinomlParser.SensorContext ctx) {
        Sensor sensor = new Sensor();
        sensor.setName(ctx.location().id.getText());
        int pinNumber = Integer.parseInt(ctx.location().port.getText());
        checkPinNumber(pinNumber);
        sensor.setPin(pinNumber);
        this.theApp.getBricks().add(sensor);
        sensors.put(sensor.getName(), sensor);
    }

    @Override
    public void enterActuator(ArduinomlParser.ActuatorContext ctx) {
        Actuator actuator = new Actuator();
        actuator.setName(ctx.location().id.getText());
        int pintNumber = Integer.parseInt(ctx.location().port.getText());
        checkPinNumber(pintNumber);
        actuator.setPin(pintNumber);
        this.theApp.getBricks().add(actuator);
        actuators.put(actuator.getName(), actuator);
    }

    @Override
    public void enterState(ArduinomlParser.StateContext ctx) {
        State local = new State();
        local.setName(ctx.name.getText());
        this.currentState = local;
        this.states.put(local.getName(), local);
    }

    @Override
    public void exitState(ArduinomlParser.StateContext ctx) {
        this.theApp.getStates().add(this.currentState);
        this.currentState = null;
    }

    @Override
    public void enterAction(ArduinomlParser.ActionContext ctx) {
        Action action = new Action();
        action.setActuator(actuators.get(ctx.receiver.getText()));
        action.setValue(SIGNAL.valueOf(ctx.value.getText()));
        currentState.getActions().add(action);
    }

    @Override
    public void enterTransition(ArduinomlParser.TransitionContext ctx) {
        // Creating a placeholder as the next state might not have been compiled yet.
        Binding toBeResolvedLater = new Binding();
        toBeResolvedLater.to      = ctx.next.getText();
        List<ArduinomlParser.Transition_conditionContext> transitionConditions = ctx.transition_condition();
        List<Condition> conditions = new ArrayList<>();
        for (ArduinomlParser.Transition_conditionContext transition: transitionConditions) {
            ArduinomlParser.Time_transitionContext time_transitionContext = transition.time_transition();
            ArduinomlParser.Sensor_conditionContext sensorConditionContext = transition.sensor_condition();
            if (transition.sensor_condition() != null) {
                SensorCondition sensorCondition = new SensorCondition();
                sensorCondition.setSensor(sensors.get(sensorConditionContext.trigger.getText()));
                sensorCondition.setValue(SIGNAL.valueOf(sensorConditionContext.value.getText()));
                conditions.add(sensorCondition);
            } else {
                TimeCondition timeCondition = new TimeCondition();
                timeCondition.setTime(Integer.parseInt(time_transitionContext.time.getText()));
                conditions.add(timeCondition);
            }
        }
        toBeResolvedLater.conditions = conditions;
        toBeResolvedLater.state = currentState.getName();
        bindings.add(toBeResolvedLater);
    }


    @Override
    public void enterInitial(ArduinomlParser.InitialContext ctx) {
        this.theApp.setInitial(this.currentState);
    }

}

