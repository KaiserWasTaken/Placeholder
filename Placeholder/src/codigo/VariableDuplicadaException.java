package codigo;

public class VariableDuplicadaException extends PlaceholderException {
    public VariableDuplicadaException(String id, int l, int c) {
        super("La variable '" + id + "' ya ha sido declarada previamente.", l, c);
    }
}
