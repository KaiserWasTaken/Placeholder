package codigo;

public class DeclaracionIncompletaException extends PlaceholderException {
    public DeclaracionIncompletaException(String tipo, int l, int c) {
        super("Falta el identificador después del tipo de dato '" + tipo + "'.", l, c);
    }
}
