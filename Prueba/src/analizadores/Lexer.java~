package analizadores;
import java_cup.runtime.*;
import excepciones.ExcepcionLexica;

%%
%class Lexer
%cup
%line
%column
%public

Letra      = [a-zA-Z]
Digito     = [0-9]
Identificador = {Letra}({Letra}|{Digito})*
Entero     = {Digito}+
Decimal    = {Digito}+"."{Digito}+
Espacio    = [ \t\f\r\n]+
Cadena     = \"([^\\\"]|\\.)*\"

%%

"pidxi"   { return new Symbol(sym.PR_ENTERO,  yyline, yycolumn, yytext()); }
"pixa"    { return new Symbol(sym.PR_DECIMAL, yyline, yycolumn, yytext()); }
"didxa"   { return new Symbol(sym.PR_CADENA,  yyline, yycolumn, yytext()); }

"ruchi"   { return new Symbol(sym.OP_SUMA,    yyline, yycolumn, yytext()); }
"ruya"    { return new Symbol(sym.OP_RESTA,   yyline, yycolumn, yytext()); }
"stipa"   { return new Symbol(sym.OP_MULT,    yyline, yycolumn, yytext()); }
"chup"    { return new Symbol(sym.OP_DIV,     yyline, yycolumn, yytext()); }
"tzo"     { return new Symbol(sym.OP_CONCAT,  yyline, yycolumn, yytext()); }

"ali"     { return new Symbol(sym.OP_ASIG,    yyline, yycolumn, yytext()); }
";"       { return new Symbol(sym.PUNTO_COMA, yyline, yycolumn, yytext()); }

{Identificador} { return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext()); }
{Entero}        { return new Symbol(sym.VAL_ENTERO,    yyline, yycolumn, yytext()); }
{Decimal}       { return new Symbol(sym.VAL_DECIMAL,   yyline, yycolumn, yytext()); }
{Cadena}        { return new Symbol(sym.VAL_CADENA,    yyline, yycolumn, yytext()); }

{Espacio}       { /* ignorar */ }

.  { throw new ExcepcionLexica(
         "El simbolo '" + yytext() + "' no pertenece al lenguaje",
         yyline, yycolumn); }