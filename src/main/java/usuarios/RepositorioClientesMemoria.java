package usuarios;
import java.util.List;
import java.util.ArrayList;
/**
 * Implementación en memoria de {@code RepositorioClientes} cuyo constructor
 * tiene visibilidad restringida al paquete {@code usuarios}.
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

    /**
     * Almacena un cliente en la colección en memoria.
     *
     * @param cliente cliente a guardar
     * @throws IllegalArgumentException si el cliente es nulo
     */
    @Override
    public void guardar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente a guardar no puede ser nulo.");
        }
        clientes.add(cliente);
    }

    /**
     * Busca un cliente por su identificador aplicando normalización de espacios.
     *
     * @param id identificador del cliente a buscar
     * @return el cliente encontrado o {@code null} si el ID es nulo, está vacío
     *         o no existe en el repositorio
     */
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

    /**
     * Verifica si existe un cliente registrado con el identificador indicado.
     *
     * @param id identificador a comprobar
     * @return {@code true} si existe un cliente con ese ID; {@code false} en caso contrario
     */
    @Override
    public boolean existe(String id) {
        return buscar(id) != null;
    }

    /**
     * Devuelve una copia defensiva con todos los clientes almacenados en memoria.
     *
     * @return nueva lista con los clientes registrados
     */
    @Override
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }
}
