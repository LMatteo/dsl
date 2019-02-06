grammar Arduinoml;


/******************
 ** Parser rules **
 ******************/

root            :   declaration bricks modes EOF;


declaration     :   'application' name=IDENTIFIER;

bricks             :   (sensor|actuator)+;
    sensor         :   digital_sensor | analog_sensor ;
    digital_sensor :   'digital sensor'  id=DIGITAL_IDENTIFIER ':' port=NUMBER ;
    analog_sensor  :   'analog sensor' id=ANALOG_IDENTIFIER ':' port=NUMBER ;
    actuator       :   'actuator' location ;
    location       :   id=IDENTIFIER ':' port=NUMBER;


modes               :   modee+;
    modee           :   initial? 'mode' name=IDENTIFIER '{' states transition+ '}';


states                        :   state+;
    state                     :   initial? name=IDENTIFIER '{'  action+ transition+ '}';
    action                    :   receiver=IDENTIFIER '<=' value=SIGNAL;
    transition                :   transition_condition+ '=>' next=IDENTIFIER ;
    initial                   :   '->';

transition_condition          :   sensor_condition | time_transition;
    time_transition           :   'elapsed time is' time=NUMBER;

sensor_condition              :   digital_sensor_condition | analog_sensor_condition ;
    digital_sensor_condition  :   trigger=DIGITAL_IDENTIFIER 'is' value=SIGNAL ;
    analog_sensor_condition   :   trigger=ANALOG_IDENTIFIER operator=COMPARISON_OPERATOR value=NUMBER ;


/*****************
 ** Lexer rules **
 *****************/

NUMBER              :   DIGIT (DIGIT|'0')*;
COMPARISON_OPERATOR :   '>' | '<';
DIGITAL_IDENTIFIER  :   'D_'IDENTIFIER;
ANALOG_IDENTIFIER   :   'A_'IDENTIFIER;
IDENTIFIER          :   LOWERCASE (LOWERCASE|UPPERCASE)+;
SIGNAL              :   'HIGH' | 'LOW';


/*************
 ** Helpers **
 *************/

fragment DIGIT      : [1-9];
fragment LOWERCASE  : [a-z];                                 // abstract rule, does not really exists
fragment UPPERCASE  : [A-Z];
NEWLINE             : ('\r'? '\n' | '\r')+      -> skip;
WS                  : ((' ' | '\t')+)           -> skip;     // who cares about whitespaces?
COMMENT             : '#' ~( '\r' | '\n' )*     -> skip;     // Single line comments, starting with a #
