package io.github.mosser.arduinoml.externals.antlr;

import io.github.mosser.arduinoml.externals.antlr.grammar.*;


import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.*;

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

    private Map<String, Mode>         modes        = new HashMap<>();
    private Map<String, Sensor>       sensors      = new HashMap<>();
    private Map<String, Actuator>     actuators    = new HashMap<>();
    private Map<String, State>        states       = new HashMap<>();
    private List<Binding>             bindings     = new ArrayList<>();
    private List<Binding>             modeBindings = new ArrayList<>();
    private List<Watchable>           watchables   = new ArrayList<>();


    private class Binding { // used to support currentStateOrMode resolution for transitions
        String currentStateOrMode;
        String to; // name of the next currentStateOrMode, as its instance might not have been compiled yet
        List<Condition> conditions;
    }


    private State currentState = null;
    private Mode  currentMode  = null;
    private List<Condition> currentConditions = new ArrayList<>();
    private Binding currentToBeResolvedLater;


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
        modeBindings.forEach((bindings) -> {
            ModeTransition transition = new ModeTransition();
            Mode next = modes.get(bindings.to);
            if (next == null) {
                throw new RuntimeException("Error in mode transition : Unknown next mode : " + bindings.to);
            }
            transition.setNext(next);
            transition.setConditions(bindings.conditions);
            modes.get(bindings.currentStateOrMode).addModeTransitions(transition);
        });
        this.theApp.setWatchs(this.watchables);
        this.built = true;
    }

    @Override
    public void enterModee(ArduinomlParser.ModeeContext ctx) {
        Mode mode = new Mode();
        mode.setName(ctx.name.getText());
        this.currentMode = mode;
        this.modes.put(mode.getName(), mode);
    }

    @Override
    public void exitModee(ArduinomlParser.ModeeContext ctx) {
        currentToBeResolvedLater.currentStateOrMode = currentMode.getName();
        modeBindings.add(currentToBeResolvedLater);
        bindings.forEach((binding) ->  {
            StateTransition t = new StateTransition();
            t.setConditions(binding.conditions);
            State next = states.get(binding.to);
            if (next == null) {
                throw new RuntimeException("Error in state transition : Unknown next state : " + binding.to);
            }
            t.setNext(states.get(binding.to));
            states.get(binding.currentStateOrMode).setStateTransition(t);
        });

        this.bindings.clear();
        this.states.clear();
        this.currentConditions.clear();
        this.currentToBeResolvedLater = null;
        this.theApp.getModes().add(this.currentMode);
        this.currentMode = null;
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


    private void initSensor(Sensor sensor, String name, int pinNumber, boolean isWatched) {
        sensor.setName(name);
        checkPinNumber(pinNumber);
        sensor.setPin(pinNumber);
        this.theApp.getBricks().add(sensor);
        sensors.put(sensor.getName(), sensor);
        if (isWatched) {
            this.watchables.add(sensor);
        }
    }

    @Override
    public void enterDigital_sensor(ArduinomlParser.Digital_sensorContext ctx) {
        DigitalSensor digitalSensor = new DigitalSensor();
        initSensor(digitalSensor, ctx.id.getText(), Integer.parseInt(ctx.port.getText()), ctx.watch() != null);
    }

    @Override
    public void enterAnalog_sensor(ArduinomlParser.Analog_sensorContext ctx) {
        AnalogSensor analogSensor = new AnalogSensor();
        initSensor(analogSensor, ctx.id.getText(), Integer.parseInt(ctx.port.getText()), ctx.watch() != null);
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
        if (ctx.watch() != null) {
            this.watchables.add(actuator);
        }
    }

    @Override
    public void enterState(ArduinomlParser.StateContext ctx) {
        State local = new State();
        local.setName(ctx.name.getText());
        this.currentState = local;
        this.currentMode.addState(this.currentState);
        this.states.put(local.getName(), local);
    }

    @Override
    public void exitState(ArduinomlParser.StateContext ctx) {
        currentToBeResolvedLater.currentStateOrMode = currentState.getName();
        bindings.add(currentToBeResolvedLater );
        this.currentConditions.clear();
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
        // Creating a placeholder as the next currentStateOrMode might not have been compiled yet.
        currentToBeResolvedLater = new Binding();
        currentToBeResolvedLater .to = ctx.next.getText();
    }

    @Override
    public void exitTransition(ArduinomlParser.TransitionContext ctx) {
        currentToBeResolvedLater.conditions = new ArrayList<>(currentConditions);
    }


    @Override
    public void enterDigital_sensor_condition(ArduinomlParser.Digital_sensor_conditionContext ctx) {
        DigitalSensorCondition sensorCondition = new DigitalSensorCondition();
        sensorCondition.setSensor((DigitalSensor) sensors.get(ctx.trigger.getText()));
        sensorCondition.setValue(SIGNAL.valueOf(ctx.value.getText()));
        currentConditions.add(sensorCondition);
    }

    @Override
    public void enterAnalog_sensor_condition(ArduinomlParser.Analog_sensor_conditionContext ctx) {
        AnalogSensorCondition sensorCondition = new AnalogSensorCondition();
        sensorCondition.setSensor((AnalogSensor) sensors.get(ctx.trigger.getText()));
        int sensorValue = Integer.parseInt(ctx.value.getText());
        if (sensorValue < 0 || sensorValue > 100) {
            throw new RuntimeException("Sensor condition value \"" + sensorValue + "\"should be between 0 and 100");
        }
        sensorValue = Math.round(((float)sensorValue / (float)100) * 1024) - 1;
        sensorCondition.setValue(sensorValue);
        sensorCondition.setGreater(ctx.operator.getText().charAt(0) == '>');
        currentConditions.add(sensorCondition);
        super.enterAnalog_sensor_condition(ctx);
    }

    @Override
    public void enterTime_transition(ArduinomlParser.Time_transitionContext ctx) {
        TimeCondition timeCondition = new TimeCondition();
        timeCondition.setTime(Integer.parseInt(ctx.time.getText()));
        currentConditions.add(timeCondition);
    }

    @Override
    public void enterInitial(ArduinomlParser.InitialContext ctx) {
        if (this.currentState == null) {
            this.theApp.setInitial(this.currentMode);
        } else {
            this.currentMode.setInitial(this.currentState);
        }
    }

}

