public class FaltaOperandoException extends PlaceholderException {
    public FaltaOperandoException(String operador, int linea, int columna) {
        super("Error sintáctico/semántico: El operador '" + operador + "' requiere dos operandos y solo se encontró uno.", linea, columna);
    }
}