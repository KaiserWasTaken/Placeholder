package excepciones;

public class ExcepcionSintactica extends PlaceholderException {
    public ExcepcionSintactica(String mensaje, int linea, int columna) {
        super(mensaje, linea, columna);
    }
}