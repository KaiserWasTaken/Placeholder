/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class OperacionNoValidaException extends PlaceholderException {
    public OperacionNoValidaException(String op, String tipo, int l, int c) {
        super("El operador '" + op + "' no puede aplicarse a tipos " + tipo + ".", l, c);
    }
}
