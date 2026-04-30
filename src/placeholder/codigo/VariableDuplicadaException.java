/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class VariableDuplicadaException extends PlaceholderException {
    public VariableDuplicadaException(String id, int l, int c) {
        super("La variable '" + id + "' ya ha sido declarada previamente.", l, c);
    }
}
