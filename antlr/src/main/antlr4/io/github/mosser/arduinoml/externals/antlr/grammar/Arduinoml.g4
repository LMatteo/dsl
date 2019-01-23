grammar Arduinoml;


/******************
 ** Parser rules **
 ******************/

root            :   declaration bricks states EOF;

declaration     :   'application' name=IDENTIFIER;

bricks          :   (sensor|actuator)+;
    sensor      :   'sensor'   location ;
    actuator    :   'actuator' location ;
    location    :   id=IDENTIFIER ':' port=NUMBER;

states                   :   state+;
    state                :   initial? name=IDENTIFIER '{'  action+ transition+ '}';
    action               :   receiver=IDENTIFIER '<=' value=SIGNAL;
    transition           :   transition_condition+ '=>' next=IDENTIFIER ;
    transition_condition :   sensor_condition | time_transition;
    time_transition     :   'elapsed time is' time=NUMBER;
    sensor_condition     :   trigger=IDENTIFIER 'is' value=SIGNAL ();
    initial              :   '->';

/*****************
 ** Lexer rules **
 *****************/

NUMBER          :   DIGIT (DIGIT|'0')*;
IDENTIFIER      :   LOWERCASE (LOWERCASE|UPPERCASE)+;
SIGNAL          :   'HIGH' | 'LOW';


/*************
 ** Helpers **
 *************/

fragment DIGIT      : [1-9];
fragment LOWERCASE  : [a-z];                                 // abstract rule, does not really exists
fragment UPPERCASE  : [A-Z];
NEWLINE             : ('\r'? '\n' | '\r')+      -> skip;
WS                  : ((' ' | '\t')+)           -> skip;     // who cares about whitespaces?
COMMENT             : '#' ~( '\r' | '\n' )*     -> skip;     // Single line comments, starting with a #
