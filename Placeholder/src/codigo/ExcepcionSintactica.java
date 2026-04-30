package codigo;

public class ExcepcionSintactica extends RuntimeException {
    private final int linea;
    private final int columna;

    public ExcepcionSintactica(String mensaje, int l, int c) {
        super(mensaje);
        this.linea   = l + 1;
        this.columna = c + 1;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " [Línea: " + linea + ", Columna: " + columna + "]";
    }
}