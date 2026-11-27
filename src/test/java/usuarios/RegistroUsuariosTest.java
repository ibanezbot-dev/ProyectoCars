package usuarios;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * Pruebas unitarias del registro de usuarios y de su repositorio.
 *
 * @author marcial
 */
public class RegistroUsuariosTest {

    private RepositorioClientesDoble repositorioDoble;
    private RegistroUsuarios registro;

    /**
     * Inicializa un registro respaldado por un doble de prueba.
     */
    @Before
    public void setUp() {
        repositorioDoble = new RepositorioClientesDoble();
        registro = new RegistroUsuarios(repositorioDoble);
    }

    /**
     * Libera los objetos usados por las pruebas.
     */
    @After
    public void tearDown() {
        repositorioDoble = null;
        registro = null;
    }

    /**
     * Comprueba el registro y búsqueda usando un repositorio falso.
     */
    @Test
    public void debeRegistrarClienteMedianteRepositorioDoble() {
        // Given: un socio y un repositorio falso sin clientes
        Socio socio = new Socio("S-10", "María");

        // When: se registra y busca por identificador
        registro.registrar(socio);
        Cliente encontrado = registro.buscarPorId("S-10");

        // Then: el doble conserva al cliente
        assertEquals(socio, encontrado);
        assertEquals(1, repositorioDoble.guardados);
    }

    /**
     * Comprueba que no se permitan identificadores duplicados.
     */
    @Test
    public void debeRechazarClienteDuplicado() {
        // Given: un cliente ya registrado
        registro.registrar(new Visitante("V-20", "Pedro"));

        // When: se intenta registrar otro con el mismo ID
        // Then: se rechaza como estado inválido
        assertThrows(IllegalStateException.class,
                () -> registro.registrar(new Visitante("V-20", "Otro")));
    }

    /**
     * Comprueba que una búsqueda inexistente no encuentre cliente.
     */
    @Test
    public void debeBuscarSinEncontrarClienteInexistente() {
        // Given: un registro vacío

        // When: se busca un identificador no registrado
        Cliente resultado = registro.buscarPorId("S-99");

        // Then: el resultado es nulo
        assertNull(resultado);
    }

    /**
     * Comprueba la copia defensiva de la lista de clientes.
     */
    @Test
    public void debeDevolverCopiaDefensivaDeClientes() {
        // Given: un cliente registrado y una lista obtenida
        registro.registrar(new Socio("S-30", "Carla"));
        List<Cliente> primera = registro.listarClientes();

        // When: se modifica la copia
        primera.clear();

        // Then: el registro conserva al cliente
        assertEquals(1, registro.listarClientes().size());
    }

    /**
     * Comprueba las validaciones de parámetros nulos o vacíos en el registro.
     */
    @Test
    public void debeRechazarParametrosInvalidosEnRegistroYBusqueda() {
        // Given: entradas nulas o en blanco

        // When y Then: constructor, alta y búsqueda rechazan datos inválidos
        assertThrows(IllegalArgumentException.class,
                () -> new RegistroUsuarios(null));
        assertThrows(IllegalArgumentException.class,
                () -> registro.registrar(null));
        assertThrows(IllegalArgumentException.class,
                () -> registro.buscarPorId(null));
        assertThrows(IllegalArgumentException.class,
                () -> registro.buscarPorId("   "));
    }

    /**
     * Comprueba el funcionamiento de RegistroUsuarios con el repositorio en memoria por defecto.
     */
    @Test
    public void debeOperarConRepositorioEnMemoriaPorDefecto() {
        // Given: un registro construido con su repositorio en memoria por defecto
        RegistroUsuarios registroReal = new RegistroUsuarios();
        Socio socio = new Socio("S-40", "Elena");

        // When: se registra y se busca con espacios alrededor del ID
        registroReal.registrar(socio);
        Cliente encontrado = registroReal.buscarPorId("  S-40  ");

        // Then: se recupera el cliente y se rechazan duplicados
        assertEquals(socio, encontrado);
        assertEquals(1, registroReal.listarClientes().size());
        assertThrows(IllegalStateException.class,
                () -> registroReal.registrar(new Visitante("S-40", "Duplicado")));
    }

    /**
     * Doble de prueba del repositorio en memoria usado para aislar las pruebas.
     */
    private static class RepositorioClientesDoble implements RepositorioClientes {

        private final List<Cliente> clientes = new ArrayList<>();
        private int guardados;

        @Override
        public void guardar(Cliente cliente) {
            guardados++;
            clientes.add(cliente);
        }

        @Override
        public Cliente buscar(String id) {
            for (Cliente cliente : clientes) {
                if (cliente.getId().equals(id)) {
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
}
