package excepciones;

public class ExcepcionSemantica extends PlaceholderException {
    public ExcepcionSemantica(String mensaje, int linea, int columna) {
        super(mensaje, linea, columna);
    }
}