/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class IdentificadorInvalidoException extends PlaceholderException {
    public IdentificadorInvalidoException(String id, int linea, int columna) {
        super("Error léxico: El nombre '" + id + "' no es un identificador válido.", linea, columna);
    }
}
