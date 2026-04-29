import analizadores.*;
import excepciones.*;
import java_cup.runtime.*;
import java.io.StringReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String[] pruebas = {
            "pidxi x ali 5;",
            "pixa y ali 3.14;",
            "didxa z ali \"hola mundo\";",
            "pidxi a ali 2 ruchi 3;",
            "pidxi b ali @;",
            "pixa c ali 1.5 chup 0;",
            "pidxi x 5;"
        };

        for (String codigo : pruebas) {
            System.out.println("─────────────────────────────");
            System.out.println("Entrada: " + codigo);
            analizarCodigo(codigo);
        }
    }

    static void analizarCodigo(String codigo) {
        try {
            StringReader reader = new StringReader(codigo);
            Lexer lexer = new Lexer(reader);

            ArrayList<String[]> tabla = new ArrayList<>();
            Symbol token;

            while ((token = lexer.next_token()).sym != sym.EOF) {
                String tipo  = sym.terminalNames[token.sym];
                String valor = token.value != null ? token.value.toString() : "-";
                String linea = String.valueOf(token.left + 1);
                String col   = String.valueOf(token.right + 1);
                tabla.add(new String[]{tipo, valor, linea, col});
            }

            System.out.printf("%-20s %-15s %-8s %-8s%n",
                "TIPO", "LEXEMA", "LINEA", "COLUMNA");
            for (String[] fila : tabla) {
                System.out.printf("%-20s %-15s %-8s %-8s%n",
                    fila[0], fila[1], fila[2], fila[3]);
            }

            // Validacion semantica: division por cero
            for (int i = 0; i < tabla.size() - 1; i++) {
                if (tabla.get(i)[0].equals("OP_DIV")) {
                    String siguiente = tabla.get(i + 1)[1];
                    if (siguiente.equals("0") || siguiente.equals("0.0")) {
                        int l = Integer.parseInt(tabla.get(i + 1)[2]);
                        int c = Integer.parseInt(tabla.get(i + 1)[3]);
                        throw new ExcepcionSemantica(
                            "No es posible dividir entre cero (chup 0)",
                            l - 1, c - 1);
                    }
                }
            }

            // Analisis sintactico
            reader = new StringReader(codigo);
            lexer  = new Lexer(reader);
            Parser parser = new Parser(lexer,
                new java_cup.runtime.DefaultSymbolFactory());
            parser.parse();
            System.out.println("Analisis correcto.");

        } catch (ExcepcionLexica e) {
            System.out.println("Error Lexico:     " + e.getMessage());
        } catch (ExcepcionSintactica e) {
            System.out.println("Error Sintactico: " + e.getMessage());
        } catch (ExcepcionSemantica e) {
            System.out.println("Error Semantico:  " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}