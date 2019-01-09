sensor "button" pin 9
sensor "button2" pin 10
actuator "led1" pin 12
actuator "led2" pin 13
actuator "led3" pin 14

state "on" means led1 becomes high
state "off" means led1 becomes low and led2 becomes low and led3 becomes low
state "middle" means led2 becomes low

initial off

from on to middle when button2 becomes high
from middle to off when button2 becomes low
from off to on when button becomes low

export "Switch!"