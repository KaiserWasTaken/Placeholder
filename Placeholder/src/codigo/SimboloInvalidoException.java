public class SimboloInvalidoException extends PlaceholderException {
    public SimboloInvalidoException(String lexema, int l, int c) {
        super("El símbolo '" + lexema + "' no es reconocido por Placeholder.", l, c);
    }
} 
    
