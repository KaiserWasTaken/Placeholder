package codigo;

public class VariableNoDeclaradaException extends PlaceholderException {
    public VariableNoDeclaradaException(String id, int l, int c) {
        super("La variable '" + id + "' no ha sido definida.", l, c);
    }
}
