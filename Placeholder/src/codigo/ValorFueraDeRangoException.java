public class ValorFueraDeRangoException extends PlaceholderException {
    public ValorFueraDeRangoException(String valor, int linea, int columna) {
        super("Error léxico/semántico: El valor '" + valor + "' excede el límite permitido.", linea, columna);
    }
}