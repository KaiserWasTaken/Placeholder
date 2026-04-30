/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class FaltaPuntoYComaException extends PlaceholderException {
    public FaltaPuntoYComaException(int l, int c) {
        super("Se esperaba un ';' al final de la instrucción.", l, c);
    }
}
