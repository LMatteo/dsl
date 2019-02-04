package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Actuator;
import io.github.mosser.arduinoml.kernel.structural.DigitalSensor;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;

import java.util.Arrays;

public class Switch {

    public static void main(String[] args) {

        // Declaring elementary bricks
        DigitalSensor button = new DigitalSensor();
        button.setName("button");
        button.setPin(9);

        DigitalSensor button2 = new DigitalSensor();
        button2.setName("button2");
        button2.setPin(10);

        Actuator led = new Actuator();
        led.setName("LED");
        led.setPin(11);

        Actuator led2 = new Actuator();
        led2.setName("LED2");
        led2.setPin(12);

        // Declaring modes
        Mode A = new Mode();
        A.setName("A");

        Mode B = new Mode();
        B.setName("B");

        // Declaring states
        State on_A = new State();
        on_A.setName("on_A");

        State off_A = new State();
        off_A.setName("off_A");

        State on_B = new State();
        on_B.setName("on_B");

        State off_B = new State();
        off_B.setName("off_B");

        // Creating actions
        Action switchTheLightOn = new Action();
        switchTheLightOn.setActuator(led);
        switchTheLightOn.setValue(SIGNAL.HIGH);

        Action switchTheLightOff = new Action();
        switchTheLightOff.setActuator(led);
        switchTheLightOff.setValue(SIGNAL.LOW);

        Action switchTheLight2On = new Action();
        switchTheLight2On.setActuator(led2);
        switchTheLight2On.setValue(SIGNAL.HIGH);

        Action switchTheLight2Off = new Action();
        switchTheLight2Off.setActuator(led2);
        switchTheLight2Off.setValue(SIGNAL.LOW);

        // Binding actions to states
        on_A.setActions(Arrays.asList(switchTheLightOn));
        off_A.setActions(Arrays.asList(switchTheLightOff));

        on_B.setActions(Arrays.asList(switchTheLight2On));
        off_B.setActions(Arrays.asList(switchTheLight2Off));

        // Creating state transitions
        StateTransition on2off_A = new StateTransition();
        on2off_A.setNext(off_A);
        DigitalSensorCondition digitalSensorCondition1 = new DigitalSensorCondition();
        digitalSensorCondition1.setSensor(button);
        digitalSensorCondition1.setValue(SIGNAL.LOW);
        on2off_A.setConditon(digitalSensorCondition1);

        StateTransition off2on_A = new StateTransition();
        off2on_A.setNext(on_A);
        DigitalSensorCondition digitalSensorCondition2 = new DigitalSensorCondition();
        digitalSensorCondition2.setSensor(button);
        digitalSensorCondition2.setValue(SIGNAL.HIGH);
        off2on_A.setConditon(digitalSensorCondition2);

        StateTransition on2off_B = new StateTransition();
        on2off_B.setNext(off_B);
        DigitalSensorCondition digitalSensorCondition3 = new DigitalSensorCondition();
        digitalSensorCondition3.setSensor(button);
        digitalSensorCondition3.setValue(SIGNAL.LOW);
        on2off_B.setConditon(digitalSensorCondition3);

        StateTransition off2on_B = new StateTransition();
        off2on_B.setNext(on_B);
        DigitalSensorCondition digitalSensorCondition4 = new DigitalSensorCondition();
        digitalSensorCondition4.setSensor(button);
        digitalSensorCondition4.setValue(SIGNAL.HIGH);
        off2on_B.setConditon(digitalSensorCondition4);

        // Binding transitions to states
        on_A.setStateTransition(on2off_A);
        off_A.setStateTransition(off2on_A);

        on_B.setStateTransition(on2off_B);
        off_B.setStateTransition(off2on_B);

        // Creating mode transitions
        ModeTransition A2B = new ModeTransition();
        A2B.setNext(B);
        DigitalSensorCondition digitalSensorCondition5 = new DigitalSensorCondition();
        digitalSensorCondition5.setSensor(button2);
        digitalSensorCondition5.setValue(SIGNAL.HIGH);
        A2B.setConditon(digitalSensorCondition5);

        ModeTransition B2A = new ModeTransition();
        B2A.setNext(A);
        DigitalSensorCondition digitalSensorCondition6 = new DigitalSensorCondition();
        digitalSensorCondition6.setSensor(button2);
        digitalSensorCondition6.setValue(SIGNAL.LOW);
        B2A.setConditon(digitalSensorCondition6);

        // Binding transitions to modes
        A.addModeTransitions(A2B);
        B.addModeTransitions(B2A);

        // Binding states to modes
        A.addState(on_A);
        A.addState(off_A);
        A.setInitial(off_A);

        B.addState(on_B);
        B.addState(off_B);
        B.setInitial(off_B);


        // Building the App
        App theSwitch = new App();
        theSwitch.setName("Switch!");
        theSwitch.setBricks(Arrays.asList(button, button2, led, led2));
        theSwitch.setModes(Arrays.asList(A, B));
        theSwitch.setInitial(A);

        // Generating Code
        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);

        // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }

}
