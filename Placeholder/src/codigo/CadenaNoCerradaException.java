package codigo;

public class CadenaNoCerradaException extends PlaceholderException {
    public CadenaNoCerradaException(int l, int c) {
        super("Se esperaba un cierre de comillas para la cadena (didxa).", l, c);
    }
}