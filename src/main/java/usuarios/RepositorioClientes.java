package usuarios;
import java.util.List;
/**
 * Contrato interno (acceso de paquete) para el almacenamiento y consulta
 * de clientes registrados. Permite sustituir la persistencia por un doble
 * de prueba en pruebas unitarias.
 * 
 * @author ibane
 */
interface RepositorioClientes {
    
    /**
     * Almacena un cliente en el repositorio.
     *
     * @param cliente cliente a guardar
     * @throws IllegalArgumentException si el cliente es nulo
     */
    void guardar(Cliente cliente);

    /**
     * Busca un cliente por su identificador.
     * @param id identificador del cliente
     * @return el cliente encontrado o {@code null} si no existe
     */
    Cliente buscar(String id);

    /**
     * Verifica si existe un cliente registrado con el identificador indicado.
     * @param id identificador a comprobar
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existe(String id);

    /**
     * Devuelve una lista con todos los clientes almacenados.
     *
     * @return copia defensiva de la lista de clientes
     */
    List<Cliente> obtenerTodos();
}
