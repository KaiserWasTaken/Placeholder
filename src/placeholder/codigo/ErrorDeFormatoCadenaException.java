/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class ErrorDeFormatoCadenaException extends PlaceholderException {
    public ErrorDeFormatoCadenaException(String msg, int linea, int columna) {
        super("Error léxico: Formato de cadena inválido. " + msg, linea, columna);
    }
}
