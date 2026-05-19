parser grammar TinyParser;

options { tokenVocab=TinyLexer; }

/* =========================
   Start Rule
========================= */

program
    : statement* EOF
    ;

/* =========================
   Statements
========================= */

statement
    : ifStatement
    | whileStatement
    | assignment
    | returnStatement
    | block
    ;

/* =========================
   If Statement
========================= */

ifStatement
    : IF LPAREN expression RPAREN block (ELSE block)?
    ;

/* =========================
   While
========================= */

whileStatement
    : WHILE LPAREN expression RPAREN block
    ;

/* =========================
   Assignment
========================= */

assignment
    : ID ASSIGN expression SEMICOLON
    ;

/* =========================
   Return
========================= */

returnStatement
    : RETURN expression? SEMICOLON
    ;

/* =========================
   Block
========================= */

block
    : LBRACE statement* RBRACE
    ;

/* =========================
   Expressions
========================= */

expression
    : expression PLUS expression
    | expression MINUS expression
    | expression MULT expression
    | expression DIV expression
    | expression MOD expression
    | expression EQ expression
    | expression NEQ expression
    | expression LT expression
    | expression GT expression
    | expression LE expression
    | expression GE expression
    | expression AND expression
    | expression OR expression
    | NOT expression
    | LPAREN expression RPAREN
    | INT
    | FLOAT
    | STRING
    | BOOLEAN
    | ID
    ;