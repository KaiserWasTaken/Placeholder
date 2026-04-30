/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class PlaceholderException extends RuntimeException { 
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

