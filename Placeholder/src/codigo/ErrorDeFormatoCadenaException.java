package codigo;
public class ErrorDeFormatoCadenaException extends PlaceholderException {
    public ErrorDeFormatoCadenaException(String msg, int linea, int columna) {
        super("Error léxico: Formato de cadena inválido. " + msg, linea, columna);
    }
}