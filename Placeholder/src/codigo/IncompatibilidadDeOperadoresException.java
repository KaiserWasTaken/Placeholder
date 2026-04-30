public class IncompatibilidadDeOperadoresException extends PlaceholderException {
    public IncompatibilidadDeOperadoresException(String op, String tipo, int linea, int columna) {
        super("Error semántico: El operador '" + op + "' no puede aplicarse a datos de tipo " + tipo + ".", linea, columna);
    }
}