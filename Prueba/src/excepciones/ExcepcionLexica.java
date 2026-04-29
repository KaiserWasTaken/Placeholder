package excepciones;

public class ExcepcionLexica extends PlaceholderException {
    public ExcepcionLexica(String mensaje, int linea, int columna) {
        super(mensaje, linea, columna);
    }
}