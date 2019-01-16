sensor "button" onPin 9
actuator "led" pin 12

state "on" means led becomes high
state "off" means led becomes low

initial off

from on to off when time becomes 100
from off to on when button becomes high

export "Switch!"