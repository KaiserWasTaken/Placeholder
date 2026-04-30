# 🌐 Placeholder

> Un lenguaje de programación de propósito didáctico construido con JFlex y CUP, cuya sintaxis está inspirada en el **Zapoteco**, lengua originaria de Oaxaca, México.

---

## ¿Qué es Placeholder?

Placeholder es un lenguaje de programación minimalista con analizadores léxico y sintáctico generados automáticamente. Permite declarar variables de tres tipos de datos y operar con ellas mediante expresiones aritméticas y de concatenación.

Su característica principal es que **todas sus palabras reservadas y operadores están escritos en Zapoteco**, lo que lo convierte en un ejercicio tanto de teoría de lenguajes formales como de valoración de lenguas indígenas.

---

##  Tabla de equivalencias

| Concepto             | Placeholder |
|----------------------|-------------|
| `int`                | `pidxi`     |
| `double`             | `pixa`      |
| `string`             | `didxa`     |
| `+` (suma)           | `ruchi`     |
| `-` (resta)          | `ruya`      |
| `*` (multiplicación) | `stipa`     |
| `/` (división)       | `chup`      |
| `tzo` (concatenación)| `tzo`       |
| `=` (asignación)     | `ali`       |
| `;` (fin sentencia)  | `;`         |

---

##  Sintaxis del lenguaje

Toda declaración sigue la estructura:

```
<tipo> <identificador> ali <expresion>;
```

### Ejemplos

```
pidxi edad ali 25;
pixa precio ali 19.99;
didxa nombre ali "Oaxaca";

pidxi resultado ali 10 ruchi 5 stipa 2;
didxa saludo ali "Hola" tzo " mundo";
```

---

##  Arquitectura del proyecto

El compilador está dividido en dos capas principales:

```
Placeholder/
├── src/
│   ├── analizadores/
│   │   ├── Lexer.flex       # Especificación del analizador léxico (JFlex)
│   │   ├── Lexer.java       # Analizador léxico generado
│   │   ├── Parser.cup       # Especificación de la gramática (CUP)
│   │   ├── Parser.java      # Analizador sintáctico generado
│   │   └── sym.java         # Constantes de tokens
│   └── codigo/
│       ├── PlaceholderException.java
│       ├── ExcepcionLexica.java
│       ├── ExcepcionSintactica.java
│       ├── VariableNoDeclaradaException.java
│       ├── VariableDuplicadaException.java
│       ├── TiposIncompatiblesException.java
│       ├── OperacionNoValidaException.java
│       ├── DivisionPorCeroException.java
│       ├── CadenaNoCerradaException.java
│       ├── AsignacionInvalidaException.java
│       ├── DeclaracionIncompletaException.java
│       └── FaltaPuntoYComaException.java
├── lib/                     # Dependencias (.jar)
└── bin/                     # Clases compiladas
```

---

##  Tokens reconocidos

| Categoría            | Tokens                                              |
|----------------------|-----------------------------------------------------|
| Palabras reservadas  | `pidxi`, `pixa`, `didxa`                            |
| Operadores           | `ruchi`, `ruya`, `stipa`, `chup`, `tzo`, `ali`      |
| Valores              | Enteros, decimales, cadenas entre comillas (`"..."`) |
| Identificadores      | `[a-zA-Z][a-zA-Z0-9]*`                              |
| Puntuación           | `;`                                                 |

---

##  Manejo de errores

El lenguaje detecta y reporta errores con número de línea y columna exactos.

| Tipo de error              | Descripción                                                   |
|----------------------------|---------------------------------------------------------------|
| `ExcepcionLexica`          | Símbolo no reconocido por el lenguaje                         |
| `ExcepcionSintactica`      | Estructura gramatical incorrecta                              |
| `VariableNoDeclaradaException` | Uso de variable sin declarar previamente                  |
| `VariableDuplicadaException`   | Declaración de una variable ya existente                  |
| `TiposIncompatiblesException`  | Asignación con tipo de dato incorrecto                    |
| `OperacionNoValidaException`   | Operador aplicado a tipos no compatibles                  |
| `DivisionPorCeroException`     | División entre cero (`chup 0`)                            |
| `CadenaNoCerradaException`     | Cadena sin comilla de cierre                              |
| `AsignacionInvalidaException`  | Uso de `ali` sin variable válida a la izquierda           |
| `DeclaracionIncompletaException` | Falta el identificador tras el tipo de dato             |
| `FaltaPuntoYComaException`     | Instrucción sin `;` al final                              |

---

##  Requisitos y configuración

- **Java** 11 o superior
- **JFlex** 1.9.1
- **CUP** v0.11b
- **VS Code** con la extensión *Extension Pack for Java* (recomendado)

Las dependencias `.jar` deben estar en la carpeta `lib/`. El proyecto está preconfigurado para VS Code a través de `.vscode/settings.json`.

---

##  Compilación y ejecución

**1. Generar el Lexer desde la especificación:**
```bash
jflex src/analizadores/Lexer.flex
```

**2. Generar el Parser desde la gramática:**
```bash
cup -destdir src/analizadores src/analizadores/Parser.cup
```

**3. Compilar el proyecto:**
```bash
javac -cp lib/*:src -d bin src/**/*.java
```

**4. Ejecutar:**
```bash
java -cp lib/*:bin Main
```

---

##  Tecnologías utilizadas

| Herramienta | Rol                              |
|-------------|----------------------------------|
| JFlex       | Generación del analizador léxico |
| CUP         | Generación del analizador sintáctico (LALR) |
| Java        | Lenguaje de implementación       |

---

##  Inspiración

Los identificadores de este lenguaje provienen del **Zapoteco del Valle de Oaxaca**, una de las lenguas indígenas más habladas de México. Este proyecto busca ser un pequeño reconocimiento a esa herencia lingüística dentro del contexto de la teoría de compiladores.
