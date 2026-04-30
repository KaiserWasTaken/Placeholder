/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class TiposIncompatiblesException extends PlaceholderException {
    public TiposIncompatiblesException(String esperado, String recibido, int l, int c) {
        super("Incompatibilidad de tipos: Se esperaba " + esperado + " pero se recibió " + recibido + ".", l, c);
    }
}

