/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class CadenaNoCerradaException extends PlaceholderException {
    public CadenaNoCerradaException(int l, int c) {
        super("Se esperaba un cierre de comillas para la cadena (didxa).", l, c);
    }
}
