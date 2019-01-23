
sensor "button" onPin 8
actuator "LED1" pin 11
actuator "buzzer" pin 12

state "on" means LED1 becomes high and buzzer becomes high
state "off" means LED1 becomes low and buzzer becomes low

from on to off when button becomes low
from off to on when button becomes high

initial off

export "VerySimpleAlarm"

//     Button LOW
// +-----------------+
// |                 |
// +                 v
// on               off
// ^                 +
// |                 |
// +-----------------+
//    Button HIGH

