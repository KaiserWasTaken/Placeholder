/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class FaltaOperandoException extends PlaceholderException {
    public FaltaOperandoException(String operador, int linea, int columna) {
        super("Error sintáctico/semántico: El operador '" + operador + "' requiere dos operandos y solo se encontró uno.", linea, columna);
    }
}
