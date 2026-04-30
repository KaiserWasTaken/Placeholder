/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class SimboloInvalidoException extends PlaceholderException {
    public SimboloInvalidoException(String lexema, int l, int c) {
        super("El símbolo '" + lexema + "' no es reconocido por Placeholder.", l, c);
    }
} 
    

