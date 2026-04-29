public class IdentificadorInvalidoException extends PlaceholderException {
    public IdentificadorInvalidoException(String id, int linea, int columna) {
        super("Error léxico: El nombre '" + id + "' no es un identificador válido.", linea, columna);
    }
}