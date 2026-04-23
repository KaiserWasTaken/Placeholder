package codigo;

public class OperacionNoValidaException extends PlaceholderException {
    public OperacionNoValidaException(String op, String tipo, int l, int c) {
        super("El operador '" + op + "' no puede aplicarse a tipos " + tipo + ".", l, c);
    }
}