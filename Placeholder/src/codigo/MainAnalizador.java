import java.io.*;
import java.util.*;

// IMPORTANTE: El nombre de la clase debe ser igual al del archivo
public class MainAnalizador {
    private static TablaSimbolos tabla = new TablaSimbolos();

    public static void main(String[] args) {
        String rutaArchivo = "prueba.txt"; 
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numLinea = 0;

            System.out.println("--- INICIANDO ANALISIS MANUAL ---");

            while ((linea = br.readLine()) != null) {
                numLinea++;
                String contenido = linea.trim();

                if (contenido.isEmpty() || contenido.startsWith("--")) continue;

                try {
                    procesarInstruccion(contenido, numLinea - 1);
                } catch (PlaceholderException e) {
                    System.err.println("[ERROR] " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("[ERROR SISTEMA] " + e.getMessage());
                }
            }
            System.out.println("--- ANALISIS FINALIZADO ---");

        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo prueba.txt: " + e.getMessage());
        }
    }

    public static void procesarInstruccion(String linea, int l) throws PlaceholderException {
        if (!linea.endsWith(";")) {
            throw new FaltaPuntoYComaException(l, linea.length());
        }

        String limpia = linea.replace(";", "").trim();
        String[] tokens = limpia.split("\\s+");
        String primerToken = tokens[0];

        if (primerToken.equals("pidxi") || primerToken.equals("didxa") || primerToken.equals("pixa")) {
            if (tokens.length < 2) throw new DeclaracionIncompletaException(primerToken, l, primerToken.length());
            
            String id = tokens[1];
            tabla.insertar(id, primerToken, l, tokens[0].length() + 1);

            if (linea.contains("ali")) {
                int posAli = buscarPosicion(tokens, "ali");
                String valor = (tokens.length > posAli + 1) ? tokens[posAli + 1] : "";

                if (linea.contains("chup 0")) throw new DivisionPorCeroException(l, linea.indexOf("chup"));

                if (primerToken.equals("pidxi") && valor.startsWith("\"")) {
                    throw new TiposIncompatiblesException("pidxi (int)", "didxa (String)", l, linea.indexOf(valor));
                }

                if (primerToken.equals("didxa") && linea.contains("ruchi")) {
                    throw new OperacionNoValidaException("ruchi", "didxa", l, linea.indexOf("ruchi"));
                }
            }
        } else {
            tabla.verificarExistencia(primerToken, l, 0);
        }
    }

    private static int buscarPosicion(String[] arr, String busqueda) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(busqueda)) return i;
        }
        return -1;
    }
}