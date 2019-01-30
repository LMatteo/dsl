sensor "button1" pin 9
sensor "button2" pin 10
actuator "led" pin 11

state "on" means led becomes high
state "off" means led becomes low

from off to on when button1 becomes high and button2 becomes high
from on to off when button1 becomes low
from on to off when button2 becomes low

initial off

export "DualCheckAlarm"