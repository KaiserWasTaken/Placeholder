package codigo;

public class PlaceholderException extends Exception {
    private int linea;
    private int columna;

    public PlaceholderException(String mensaje, int linea, int columna) {
        super(mensaje);
        this.linea = linea + 1;
        this.columna = columna + 1;
    }

    @Override
    public String getMessage() {
        return String.format("%s [Línea: %d, Columna: %d]", super.getMessage(), linea, columna);
    }
}

