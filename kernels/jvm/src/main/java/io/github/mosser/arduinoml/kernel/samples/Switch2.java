package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Actuator;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;

import java.util.Arrays;

public class Switch2 {

    public static void main(String[] args) {

        // Declaring elementary bricks
        Sensor button = new Sensor();
        button.setName("button");
        button.setPin(9);

        Sensor button2 = new Sensor();
        button2.setName("button2");
        button2.setPin(10);

        Actuator led = new Actuator();
        led.setName("LED");
        led.setPin(12);

        // Declaring states
        State on = new State();
        on.setName("on");

        State off = new State();
        off.setName("off");

        // Creating actions
        Action switchTheLightOn = new Action();
        switchTheLightOn.setActuator(led);
        switchTheLightOn.setValue(SIGNAL.HIGH);

        Action switchTheLightOff = new Action();
        switchTheLightOff.setActuator(led);
        switchTheLightOff.setValue(SIGNAL.LOW);

        // Binding actions to states
        on.setActions(Arrays.asList(switchTheLightOn));
        off.setActions(Arrays.asList(switchTheLightOff));

        TimeCondition timeCondition = new TimeCondition();
        timeCondition.setTime(1000);

        // Creating transitions
        Transition on2off = new Transition();
//        on2off.setNext(off);
        SensorCondition sensorCondition3 = new SensorCondition();
        sensorCondition3.setSensor(button);
        sensorCondition3.setValue(SIGNAL.LOW);
        on2off.setConditon(sensorCondition3);

        Transition on2off2 = new Transition();
//        on2off2.setNext(off);
        SensorCondition sensorCondition4 = new SensorCondition();
        sensorCondition4.setSensor(button2);
        sensorCondition4.setValue(SIGNAL.LOW);
        on2off2.setConditon(sensorCondition4);
        on2off2.setConditon(timeCondition);


        Transition off2on = new Transition();

//        off2on.setNext(on);
        SensorCondition sensorCondition = new SensorCondition();
        sensorCondition.setSensor(button);
        sensorCondition.setValue(SIGNAL.HIGH);
        SensorCondition sensorCondition2 = new SensorCondition();
        sensorCondition2.setSensor(button2);
        sensorCondition2.setValue(SIGNAL.HIGH);
        off2on.setConditon(sensorCondition);
        off2on.setConditon(sensorCondition2);

        // Binding transitions to states
//        on.setTransition(on2off);
//        on.setTransition(on2off2);
//        off.setTransition(off2on);

        // Building the App
        App theSwitch = new App();
        theSwitch.setName("Switch!");
        theSwitch.setBricks(Arrays.asList(button, button2, led));
//        theSwitch.setStates(Arrays.asList(on, off));
//        theSwitch.setInitial(off);

        // Generating Code
        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);

        // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }

}
