package codigo;

public class TiposIncompatiblesException extends PlaceholderException {
    public TiposIncompatiblesException(String esperado, String recibido, int l, int c) {
        super("Incompatibilidad de tipos: Se esperaba " + esperado + " pero se recibió " + recibido + ".", l, c);
    }
}
