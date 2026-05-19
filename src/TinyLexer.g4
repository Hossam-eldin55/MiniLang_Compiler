lexer grammar TinyLexer;

/* =========================
   Keywords
========================= */

IF      : 'if';
ELSE    : 'else';
WHILE   : 'while';
RETURN  : 'return';
TRUE    : 'true';
FALSE   : 'false';

/* =========================
   Operators
========================= */

PLUS    : '+';
MINUS   : '-';
MULT    : '*';
DIV     : '/';
MOD     : '%';


EQ      : '==';
NEQ     : '!=';
LT      : '<';
GT      : '>';
LE      : '<=';
GE      : '>=';

ASSIGN  : '=';
PLUS_ASSIGN  : '+=';
MINUS_ASSIGN : '-=';
MULT_ASSIGN  : '*=';
DIV_ASSIGN   : '/=';

AND     : '&&';
OR      : '||';
NOT     : '!';

/* =========================
   Punctuation
========================= */

LPAREN  : '(';
RPAREN  : ')';
LBRACE  : '{';
RBRACE  : '}';
SEMICOLON : ';';
COMMA   : ',';

/* =========================
   Literals
========================= */

INT
    : [0-9]+
    ;

FLOAT
    : [0-9]+ '.' [0-9]+
    ;

STRING
    : '"' ( ~["\\] | '\\' . )* '"'
    ;

BOOLEAN
    : TRUE
    | FALSE
    ;

/* =========================
   Identifier
========================= */

ID
    : [a-zA-Z_][a-zA-Z_0-9]*
    ;

/* =========================
   Comments
========================= */

LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

/* =========================
   Whitespace
========================= */

WS
    : [ \t\r\n]+ -> skip
    ;

Error:.;