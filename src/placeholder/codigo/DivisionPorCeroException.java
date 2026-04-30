/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class DivisionPorCeroException extends PlaceholderException {
    public DivisionPorCeroException(int l, int c) {
        super("Error aritmético: No es posible dividir entre cero (chup 0).", l, c);
    }
}

