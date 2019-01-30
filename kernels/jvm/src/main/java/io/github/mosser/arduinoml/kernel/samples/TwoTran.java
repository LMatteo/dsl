package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.Action;
import io.github.mosser.arduinoml.kernel.behavioral.State;
import io.github.mosser.arduinoml.kernel.behavioral.Transition;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Actuator;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;

import java.util.Arrays;

public class TwoTran {

    public static void main(String[] args) {

        // Declaring elementary bricks
        Sensor button = new Sensor();
        button.setName("button");
        button.setPin(8);

        Sensor button2 = new Sensor();
        button2.setName("button2");
        button2.setPin(9);

        Actuator led = new Actuator();
        led.setName("LED");
        led.setPin(12);

        Actuator led2 = new Actuator();
        led2.setName("LED2");
        led2.setPin(11);

        // Declaring states
        State init = new State();
        init.setName("init");

        State a = new State();
        a.setName("a");

        State b = new State();
        b.setName("b");

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
        init.setActions(Arrays.asList(switchTheLight2Off, switchTheLightOff));

        a.setActions(Arrays.asList(switchTheLightOn));

        b.setActions(Arrays.asList(switchTheLight2On));

        // Creating transitions
        Transition init2a = new Transition();
//        init2a.setNext(a);
//        init2a.setSensor(button);
//        init2a.setValue(SIGNAL.HIGH);

        Transition init2b = new Transition();
//        init2b.setNext(b);
//        init2b.setSensor(button2);
//        init2b.setValue(SIGNAL.HIGH);

        Transition a2init = new Transition();
//        a2init.setNext(init);
//        a2init.setSensor(button);
//        a2init.setValue(SIGNAL.LOW);

        Transition b2init = new Transition();
//        b2init.setNext(init);
//        b2init.setSensor(button2);
//        b2init.setValue(SIGNAL.LOW);

        // Binding transitions to states
//        init.setTransition(init2a);
//        init.setTransition(init2b);
//        a.setTransition(a2init);
//        b.setTransition(b2init);

        // Building the App
        App whatever = new App();
        whatever.setName("init2ab");
        whatever.setBricks(Arrays.asList(button, button2, led, led2));
//        whatever.setStates(Arrays.asList(init, a, b));
//        whatever.setInitial(init);

        // Generating Code
        Visitor codeGenerator = new ToWiring();
        whatever.accept(codeGenerator);

        // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }

}
