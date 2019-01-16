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

states               :   state+;
    state            :   initial? name=IDENTIFIER '{'  action+ (transition|transition_timed)+ '}';
    action           :   receiver=IDENTIFIER '<=' value=SIGNAL;
    transition       :   trigger=IDENTIFIER 'is' value=SIGNAL '=>' next=IDENTIFIER ;
    transition_timed :   'elapsed time is' time=NUMBER '=>' next=IDENTIFIER;
    initial          :   '->';

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
