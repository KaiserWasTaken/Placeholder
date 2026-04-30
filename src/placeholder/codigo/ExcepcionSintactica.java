/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
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