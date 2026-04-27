package codigo;

import analizadores.*;
import java.io.FileReader;
import java_cup.runtime.Symbol;

public class Main {
    public static void main(String[] args) {
        String rutaArchivo = "Placeholder\\src\\codigo\\prueba.txt"; // Asegúrate de que este archivo exista
        
        try {
            System.out.println("--- Iniciando Compilación de Placeholder ---");
            
            // 1. Inicializar el Lexer con el archivo de prueba
            Lexer lexer = new Lexer(new FileReader(rutaArchivo));
            
            // 2. Inicializar el Parser pasándole el Lexer
            Parser parser = new Parser(lexer);
            
            // 3. Ejecutar el análisis
            parser.parse();
            
            System.out.println("¡ÉXITO! El código es válido en Placeholder.");

        } catch (SimboloInvalidoException e) {
            System.err.println("[ERROR LÉXICO] " + e.getMessage());
        } catch (FaltaPuntoYComaException e) {
            System.err.println("[ERROR SINTÁCTICO] " + e.getMessage());
        } catch (VariableNoDeclaradaException e) {
            System.err.println("[ERROR SEMÁNTICO] " + e.getMessage());
        } catch (TiposIncompatiblesException e) {
            System.err.println("[ERROR SEMÁNTICO] " + e.getMessage());
        } catch (PlaceholderException e) {
            // Atrapa cualquier otra de tus 10 excepciones específicas
            System.err.println("[ERROR DE COMPILACIÓN] " + e.getMessage());
        } catch (Exception e) {
            // Errores generales de Java (ej. archivo no encontrado)
            System.err.println("[ERROR SISTEMA] " + e.getMessage());
        }
    }
}