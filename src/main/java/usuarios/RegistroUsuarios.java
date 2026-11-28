package usuarios;
import java.util.ArrayList;
import java.util.List;
/**
 * Gestiona el alta y consulta de clientes del sistema de renta de go karts.
 *
 * @author ibane
 */
public class RegistroUsuarios {
    
    private final RepositorioClientes repositorio;

    /**
     * Crea un registro de usuarios respaldado por un repositorio en memoria.
     */
    public RegistroUsuarios() {
        this(new RepositorioClientesMemoria());
    }

    /**
     * Constructor con acceso de paquete para inyectar un repositorio especifico
     * o un doble de prueba en JUnit.
     * @param repositorio implementacion del repositorio de clientes
     * @throws IllegalArgumentException si el repositorio es nulo
     */
    RegistroUsuarios(RepositorioClientes repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("El repositorio de clientes no puede ser nulo.");
        }
        this.repositorio = repositorio;
    }

    /**
     * Registra un nuevo cliente verificando que no sea nulo y que su ID
     * no se encuentre previamente registrado.
     * @param cliente cliente a registrar
     * @throws IllegalArgumentException si el cliente es nulo o su ID es invalido
     * @throws IllegalStateException    si ya existe un cliente con el mismo ID
     */
    public void registrar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente a registrar no puede ser nulo.");
        }
        String id = cliente.getId();
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El cliente debe tener un ID valido.");
        }
        String idNormalizado = id.trim();
        if (repositorio.existe(idNormalizado)) {
            throw new IllegalStateException("Ya existe un cliente registrado con el ID: " + idNormalizado);
        }
        repositorio.guardar(cliente);
    }

    /**
     * Busca un cliente registrado a partir de su identificador.
     * @param id identificador del cliente a buscar
     * @return el cliente encontrado o {@code null} si no esta registrado
     * @throws IllegalArgumentException si el ID es nulo o esta vacio
     */
    public Cliente buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de busqueda no puede ser nulo ni estar vacio.");
        }
        return repositorio.buscar(id.trim());
    }

    /**
     * Devuelve una copia defensiva con todos los clientes registrados.
     * @return lista con los clientes actuales
     */
    public List<Cliente> listarClientes() {
        return new ArrayList<>(repositorio.obtenerTodos());
    }
}
