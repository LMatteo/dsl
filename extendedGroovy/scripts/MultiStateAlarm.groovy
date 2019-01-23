sensor "button" pin 8
actuator "LED1" pin 11
actuator "buzzer" pin 12

state "buzz" means buzzer becomes high and LED1 becomes low
state "light" means buzzer becomes low and LED1 becomes high
state "init" means buzzer becomes low and LED1 becomes low

from init to buzz when button becomes high
from buzz to light when button becomes high
from light to init when button becomes high

initial init

export "MultiStateAlarm"


//
//
//              button HIGH       button HIGH
//        init+------------->buzz+-------------->light
//        ^                                     +
//        |                                     |
//        |                                     |
//        |                                     |
//        |                                     |
//        +-------------------------------------+
//                       button HIGH

