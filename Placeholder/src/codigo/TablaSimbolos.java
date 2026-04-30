import java.util.HashMap;

public class TablaSimbolos {
    // El primer String es el nombre (id) y el segundo es el tipo (pidxi, didxa, etc.)
    private HashMap<String, String> tabla;

    public TablaSimbolos() {
        this.tabla = new HashMap<>();
    }

    // Guarda una nueva variable
    public void insertar(String id, String tipo, int l, int c) throws VariableDuplicadaException {
        if (tabla.containsKey(id)) {
            throw new VariableDuplicadaException(id, l, c);
        }
        tabla.put(id, tipo);
    }

    // Verifica si una variable ya existe para poder usarla
    public void verificarExistencia(String id, int l, int c) throws VariableNoDeclaradaException {
        if (!tabla.containsKey(id)) {
            throw new VariableNoDeclaradaException(id, l, c);
        }
    }

    // Obtiene el tipo de una variable para validaciones semánticas
    public String getTipo(String id) {
        return tabla.get(id);
    }
}