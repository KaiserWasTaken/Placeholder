package codigo;
public class ConstanteModificadaException extends PlaceholderException {
    public ConstanteModificadaException(String id, int linea, int columna) {
        super("Error semántico: Intento de modificar la constante '" + id + "'.", linea, columna);
    }
}