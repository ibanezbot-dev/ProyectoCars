package usuarios;
import java.util.List;
import java.util.ArrayList;
/**
 * Implementación en memoria de {@link RepositorioClientes} con visibilidad
 * restringida al paquete {@code usuarios}.
 *
 * @author ibane
 */
public class RepositorioClientesMemoria implements RepositorioClientes {
    private final List<Cliente> clientes;

    /**
     * Inicializa el repositorio con una colección vacía en memoria.
     */
    RepositorioClientesMemoria() {
        clientes = new ArrayList<>();
    }

    @Override
    public void guardar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente a guardar no puede ser nulo.");
        }
        clientes.add(cliente);
    }

    @Override
    public Cliente buscar(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        String idBuscado = id.trim();
        for (Cliente cliente : clientes) {
            if (idBuscado.equals(cliente.getId())) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public boolean existe(String id) {
        return buscar(id) != null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }
}
