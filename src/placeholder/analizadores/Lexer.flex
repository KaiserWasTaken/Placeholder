package placeholder.analizadores;

import java_cup.runtime.*;
import placeholder.codigo.*;

%%
%class Lexer
%cup
%line
%column
%public

/* Macros */
Letra = [a-zA-Z]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*
Entero = {Digito}+
Decimal = {Digito}+"."{Digito}+
Espacio = [ \t\f\r\n]+
Cadena = \"([^\\\"]|\\.)*\"

%%

/* Palabras Reservadas (Tipos de datos) */
"pidxi"     { return new Symbol(sym.PR_ENTERO, yyline, yycolumn, yytext()); }
"pixa"      { return new Symbol(sym.PR_DECIMAL, yyline, yycolumn, yytext()); }
"didxa"     { return new Symbol(sym.PR_CADENA, yyline, yycolumn, yytext()); }

/* Operadores Aritméticos */
"ruchi"     { return new Symbol(sym.OP_SUMA, yyline, yycolumn, yytext()); }
"ruya"      { return new Symbol(sym.OP_RESTA, yyline, yycolumn, yytext()); }
"stipa"     { return new Symbol(sym.OP_MULT, yyline, yycolumn, yytext()); }
"chup"      { return new Symbol(sym.OP_DIV, yyline, yycolumn, yytext()); }
"tzo"       { return new Symbol(sym.OP_CONCAT, yyline, yycolumn, yytext()); }

/* Asignación y Puntuación */
"ali"       { return new Symbol(sym.OP_ASIG, yyline, yycolumn, yytext()); }
";"         { return new Symbol(sym.PUNTO_COMA, yyline, yycolumn, yytext()); }

/* Tokens Dinámicos */
{Identificador} { return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext()); }
{Entero}        { return new Symbol(sym.VAL_ENTERO, yyline, yycolumn, yytext()); }
{Decimal}       { return new Symbol(sym.VAL_DECIMAL, yyline, yycolumn, yytext()); }
{Cadena}        { return new Symbol(sym.VAL_CADENA, yyline, yycolumn, yytext()); }

/* Ignorar espacios en blanco */
{Espacio}       { /* No hacer nada */ }

/* Error Léxico: Si nada de lo anterior coincide */
. { 
    throw new ExcepcionLexica("El símbolo '" + yytext() + "' no pertenece al lenguaje Placeholder", yyline, yycolumn); 
}