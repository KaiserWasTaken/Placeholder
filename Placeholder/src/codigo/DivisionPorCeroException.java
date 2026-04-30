public class DivisionPorCeroException extends PlaceholderException {
    public DivisionPorCeroException(int l, int c) {
        super("Error aritmético: No es posible dividir entre cero (chup 0).", l, c);
    }
}
