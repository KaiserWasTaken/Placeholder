package codigo;

public class AsignacionInvalidaException extends PlaceholderException {
    public AsignacionInvalidaException(int l, int c) {
        super("Se encontró un operador 'ali' sin una variable válida a la izquierda.", l, c);
    }
}
