/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo

*/
public class DeclaracionIncompletaException extends PlaceholderException {
    public DeclaracionIncompletaException(String tipo, int l, int c) {
        super("Falta el identificador después del tipo de dato '" + tipo + "'.", l, c);
    }
}

