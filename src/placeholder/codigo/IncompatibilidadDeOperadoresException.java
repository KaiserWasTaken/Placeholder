/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class IncompatibilidadDeOperadoresException extends PlaceholderException {
    public IncompatibilidadDeOperadoresException(String op, String tipo, int linea, int columna) {
        super("Error semántico: El operador '" + op + "' no puede aplicarse a datos de tipo " + tipo + ".", linea, columna);
    }
}