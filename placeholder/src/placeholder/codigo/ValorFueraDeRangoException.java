/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package placeholder.codigo;

/**
 *
 * @author carlo
 */
public class ValorFueraDeRangoException extends PlaceholderException {
    public ValorFueraDeRangoException(String valor, int linea, int columna) {
        super("Error léxico/semántico: El valor '" + valor + "' excede el límite permitido.", linea, columna);
    }
}
