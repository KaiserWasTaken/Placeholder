public class VariableNoUtilizadaException extends PlaceholderException {
    public VariableNoUtilizadaException(String id, int linea, int columna) {
        super("Aviso semántico: La variable '" + id + "' fue declarada pero nunca se utiliza.", linea, columna);
    }
}