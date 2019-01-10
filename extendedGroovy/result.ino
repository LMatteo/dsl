time transition!!!!!
// Wiring code generated from an ArduinoML model
// Application name: Switch!

void setup(){
  pinMode(9, INPUT);  // button [Sensor]
  pinMode(12, OUTPUT); // led [Actuator]
}

long time = 0; long debounce = 200;

long timer = 0; int timeSet = 0;

void state_on() {
  digitalWrite(12,HIGH);
  boolean guard = millis() - time > debounce;
	if(!timeSet){ 
		timer=millis();
		timeSet=1;
	}
  if( 100 >= millis() - timer ) {
    state_off();
    timeSet=0
  } else {
    state_on();
  }
}

void state_off() {
  digitalWrite(12,LOW);
  boolean guard = millis() - time > debounce;
  if( digitalRead(9) == HIGH && guard ) {
    time = millis();
    state_on();
  } else {
    state_off();
  }
}

void loop() {
  state_off();
}

