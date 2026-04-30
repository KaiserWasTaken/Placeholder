/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class VariableNoUtilizadaException extends PlaceholderException {
    public VariableNoUtilizadaException(String id, int linea, int columna) {
        super("Aviso semántico: La variable '" + id + "' fue declarada pero nunca se utiliza.", linea, columna);
    }
}