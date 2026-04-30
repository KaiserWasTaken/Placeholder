/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class VariableNoDeclaradaException extends PlaceholderException {
    public VariableNoDeclaradaException(String id, int l, int c) {
        super("La variable '" + id + "' no ha sido definida.", l, c);
    }
}
