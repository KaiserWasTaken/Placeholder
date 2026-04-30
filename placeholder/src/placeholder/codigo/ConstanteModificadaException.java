/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class ConstanteModificadaException extends PlaceholderException {
    public ConstanteModificadaException(String id, int linea, int columna) {
        super("Error semántico: Intento de modificar la constante '" + id + "'.", linea, columna);
    }
}

