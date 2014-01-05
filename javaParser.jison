/* 
Description: Lexical and Langauge grammars for Java.  
Based on http://www.cs.dartmouth.edu/~mckeeman/cs118/notation/java11.html 
*/

/**************************/
/* Lexical Grammar        */
/**************************/
%lex
%%

\s+                     /* skip whitspace */
"class"                 return 'CLASS'
[0-9]+("."[0-9]+)?\b    return 'NUMBER'
"interface"             return 'INTERFACE'
"public"                return 'PUBLIC'
"static"                return 'STATIC'
"extends"               return 'EXTENDS'
"implements"            return 'IMPLEMENTS'
"identifier"            return 'IDENTIFIER'
"*"                   return 'OP_MULT'
"/"                   return 'OP_DIV'
"-"                   return 'OP_SUB'
"+"                   return 'OP_ADD'
"^"                   return 'OP_XOR'
"("                   return 'OPEN_PAREN'
")"                   return 'CLOSE_PAREN'
"{"                   return 'OPEN_CURLY'
"}"                   return 'CLOSE_CURLY'
"IF"                  return 'IF'

<<EOF>>                 return 'EOF'
.                       return 'INVALID'

/*
\s+                   //skip whitspace
[0-9]+("."[0-9]+)?\b  return 'NUMBER'
"*"                   return 'OP_MULT'
"/"                   return 'OP_DIV'
"-"                   return 'OP_SUB'
"+"                   return 'OP_ADD'
"^"                   return 'OP_XOR'
"("                   return 'OPEN_PAREN'
")"                   return 'CLOSE_PAREN'
"{"                   return 'OPEN_CURLY'
"}"                   return 'CLOSE_CURLY'
"IF"                  return 'IF'
<<EOF>>               return 'EOF'
.                     return 'INVALID'
*/

/*
%token ABSTRACT
%token BOOLEAN BREAK BYTE BYVALUE
%token CASE CAST CATCH CHAR CLASS CONST CONTINUE
%token DEFAULT DO DOUBLE
%token ELSE EXTENDS
%token FINAL FINALLY FLOAT FOR FUTURE
%token GENERIC GOTO
%token IF IMPLEMENTS IMPORT INNER INSTANCEOF INT INTERFACE
%token LONG
%token NATIVE NEW JNULL
%token OPERATOR OUTER
%token PACKAGE PRIVATE PROTECTED PUBLIC
%token REST RETURN
%token SHORT STATIC SUPER SWITCH SYNCHRONIZED
%token THIS THROW THROWS TRANSIENT TRY
%token VAR VOID VOLATILE
%token WHILE
%token OP_INC OP_DEC
%token OP_SHL OP_SHR OP_SHRR
%token OP_GE OP_LE OP_EQ OP_NE
%token OP_LAND OP_LOR
%token OP_DIM
%token ASS_MUL ASS_DIV ASS_MOD ASS_ADD ASS_SUB
%token ASS_SHL ASS_SHR ASS_SHRR ASS_AND ASS_XOR ASS_OR
%token IDENTIFIER LITERAL BOOLLIT
*/

/lex

/************************************/
/* Op association and  precedence   */
/************************************/

/*
%left '+' '-'
%left '*' '/'
%left '^'
%left UMINUS
*/


%start CompilationUnit

%% /* language grammar */

/**************************/
/* General                */
/**************************/

CompilationUnit
    : ProgramFile EOF
        {return yytext}
    ;

ProgramFile /* TODO: consider supporting PackageStatemens and Imports */
    : ClassDeclarations
    ;

QualifiedName
    : IDENTIFIER
    | QualifiedName '.' IDENTIFIER
    ;

SemiColons
    : ';'
    | SemiColons ';'
    ;

/************************/
/* Types                */
/************************/
TypeName
    : PrimitiveType
    | QualifiedName
    ;

PrimitiveType
    : BOOLEAN
    | CHAR
    | BYTE
    | SHORT
    | INT
    | LONG
    | FLOAT
    | DOUBLE
    | VOID
    ;


/*********************************/
/* Method and Class Declarations */
/*********************************/

ClassDeclarations
    : ClassDeclaration
    ;

ClassDeclaration
    : ClassHeader OPEN_CURLY Statements CLOSE_CURLY
    | ClassHeader OPEN_CURLY CLOSE_CURLY
    ;

ClassHeader
    : Modifiers ClassWord IDENTIFIER Extends Interfaces
    | Modifiers ClassWord IDENTIFIER Extends
    | Modifiers ClassWord IDENTIFIER       Interfaces
    |           ClassWord IDENTIFIER Extends Interfaces
    | Modifiers ClassWord IDENTIFIER
    |           ClassWord IDENTIFIER Extends
    |           ClassWord IDENTIFIER       Interfaces
    |           ClassWord IDENTIFIER
    ;

ClassWord
    : CLASS
    | INTERFACE
    ;

Modifiers
    : Modifier
    | Modifiers Modifier
    ;

Modifier
    : PUBLIC
    | STATIC
    ;

/* Should this use TypeName?*/
Extends
    : EXTENDS IDENTIFIER 
    | Extends ',' IDENTIFIER
    ;

Interfaces
    : IMPLEMENTS ClassNameList
    ;

ClassNameList
    : QualifiedName
    | ClassNameList ',' QualifiedName
    ;


/*
expressions
    : e EOF
        {return $1;}
    ;

e
    : e '+' e
        {$$ = $1+$3;}
    | e '-' e
        {$$ = $1-$3;}
    | e '*' e
        {$$ = $1*$3;}
    | e '/' e
        {$$ = $1/$3;}
    | e '^' e
        {$$ = Math.pow($1, $3);}
    | '-' e %prec UMINUS
        {$$ = -$2;}
    | '(' e ')'
        {$$ = $2;}
    | NUMBER
        {$$ = Number(yytext);}
    | E
        {$$ = Math.E;}
    | PI
        {$$ = Math.PI;}
    ;

*/
