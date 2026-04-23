package codigo;

public class FaltaPuntoYComaException extends PlaceholderException {
    public FaltaPuntoYComaException(int l, int c) {
        super("Se esperaba un ';' al final de la instrucción.", l, c);
    }
}