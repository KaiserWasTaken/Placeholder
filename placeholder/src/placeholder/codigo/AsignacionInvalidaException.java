/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class AsignacionInvalidaException extends PlaceholderException {
    public AsignacionInvalidaException(int l, int c) {
        super("Se encontró un operador 'ali' sin una variable válida a la izquierda.", l, c);
    }
}
