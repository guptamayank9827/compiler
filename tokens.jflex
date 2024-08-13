/*-***
 *
 * This file defines a stand-alone lexical analyzer for a subset of the Pascal
 * programming language.  This is the same lexer that will later be integrated
 * with a CUP-based parser.  Here the lexer is driven by the simple Java test
 * program in ./PascalLexerTest.java, q.v.
 *
 */
import java_cup.runtime.*;


%%
/*-*
 * LEXICAL FUNCTIONS:
 */
%cup
%line
%column
%unicode
%class Lexer

%{
/**
 * Return a new Symbol with the given token id, and with the current line and
 * column numbers.
 */
Symbol newSym(int tokenId) {
    return new Symbol(tokenId, yyline, yycolumn);
}


/**
 * Return a new Symbol with the given token id, the current line and column
 * numbers, and the given token value.  The value is used for tokens such as
 * identifiers and numbers.
 */
Symbol newSym(int tokenId, Object value) {
    return new Symbol(tokenId, yyline, yycolumn, value);
}
%}


/*-*
 * PATTERN DEFINITIONS:
*/
digit = [0-9]
letter = [a-zA-Z]
whitespace = [ \n\t\r]
id = {letter}({letter}|{digit})*
integer = {digit}+
float = {digit}+.{digit}+

allChars = [^\r\n]
chars = [[^'] && [^\\]]
terminator = (\r | \n | \r\n)
commentLine = {allChars}*{terminator}?

character = \'(\\' | \\\\ | \\n | \\t | ({chars}))\'
string = \"({allChars} | \\n | \\t | \\\" | \\\' | \\\\ )*\"

singleComment = \\\\{commentLine}
multiComment = \\\*{commentLine}*\*\\{terminator}?
comment = ({singleComment} | {multiComment})

%%
/**
 * LEXICAL RULES:
*/

class           {return newSym(sym.CLASS, "class");}
final           {return newSym(sym.FINAL, "final");}
void            {return newSym(sym.VOID, "void");}

true            {return newSym(sym.TRUE, "true");}
false           {return newSym(sym.FALSE, "false");}

int             {return newSym(sym.INT, "int");}
char            {return newSym(sym.CHAR, "char");}
bool            {return newSym(sym.BOOL, "bool");}
float           {return newSym(sym.FLOAT, "float");}

if              {return newSym(sym.IF, "if");}
else            {return newSym(sym.ELSE, "else");}
while           {return newSym(sym.WHILE, "while");}

printline       {return newSym(sym.PRINTLINE, "printline");}
print           {return newSym(sym.PRINT, "print");}
read            {return newSym(sym.READ, "read");}
return          {return newSym(sym.RETURN, "return");}

"("             {return newSym(sym.OPEN_PARAN, "(");}
")"             {return newSym(sym.CLOSE_PARAN, ")");}
"["             {return newSym(sym.OPEN_SQUARE, "[");}
"]"             {return newSym(sym.CLOSE_SQUARE, "]");}
"{"             {return newSym(sym.OPEN_CURLY, "{");}
"}"             {return newSym(sym.CLOSE_CURLY, "}");}

"&&"            {return newSym(sym.AND, "&&");}
"||"            {return newSym(sym.OR, "||");}
"++"            {return newSym(sym.INCREMENT, "++");}
"--"            {return newSym(sym.DECREMENT, "--");}

"+"             {return newSym(sym.PLUS, "+");}
"-"             {return newSym(sym.MINUS, "-");}
"*"             {return newSym(sym.MULTIPLY, "*");}
"/"             {return newSym(sym.DIVIDE, "/");}

"=="            {return newSym(sym.EQUAL, "==");}
"<>"            {return newSym(sym.NOT_EQUAL, "<>");}
"<="            {return newSym(sym.LESS_EQUAL, "<=");}
">="            {return newSym(sym.GREATER_EQUAL, ">=");}
"<"             {return newSym(sym.LESS, "<");}
">"             {return newSym(sym.GREATER, ">");}

"="             {return newSym(sym.ASSIGN, "=");}
";"             {return newSym(sym.SEMICOLON, ";");}
","             {return newSym(sym.COMMA, ",");}
"~"             {return newSym(sym.TILDE, "~");}
"?"             {return newSym(sym.QUESTION, "?");}
":"             {return newSym(sym.COLON, ":");}

{id}            {return newSym(sym.ID, yytext());}
{integer}       {return newSym(sym.INT_LIT, new Integer(yytext()));}
{character}     {return newSym(sym.CHAR_LIT, yytext());}
{string}        {return newSym(sym.STR_LIT, yytext());}
{float}         {return newSym(sym.FLOAT_LIT, new Float(yytext()));}
{comment}       {/* Ignore comments. */}

{whitespace}    { /* Ignore whitespace. */ }
.               { System.out.println("Illegal char, '" + yytext() + "' line: " + yyline + ", column: " + yychar); }