package usuarios;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas unitarias de {@link RepositorioClientesMemoria}.
 *
 * @author marcial
 */
public class RepositorioClientesMemoriaTest {

    private RepositorioClientesMemoria repositorio;

    /**
     * Inicializa un repositorio vacio antes de cada prueba.
     */
    @Before
    public void setUp() {
        repositorio = new RepositorioClientesMemoria();
    }

    /**
     * Libera el repositorio utilizado por la prueba.
     */
    @After
    public void tearDown() {
        repositorio = null;
    }

    /**
     * Comprueba que se guarden clientes y se localicen por su ID normalizado.
     */
    @Test
    public void debeGuardarBuscarYVerificarExistencia() {
        // Given: un socio guardado en memoria
        Socio socio = new Socio("S-100", "Sofia");
        repositorio.guardar(socio);

        // When: se consulta existencia y se busca con y sin espacios
        boolean existe = repositorio.existe("S-100");
        boolean noExiste = repositorio.existe("S-999");
        Cliente encontrado = repositorio.buscar(" S-100 ");

        // Then: localiza el cliente existente y reporta false para el inexistente
        assertTrue(existe);
        assertFalse(noExiste);
        assertEquals(socio, encontrado);
    }

    /**
     * Comprueba que buscar con ID nulo o vacio devuelva null sin fallar.
     */
    @Test
    public void debeDevolverNullAlBuscarIdNuloOBlanco() {
        // Given: un repositorio con un cliente registrado
        repositorio.guardar(new Visitante("V-100", "Hugo"));

        // When y Then: las busquedas con ID nulo o vacio devuelven null
        assertNull(repositorio.buscar(null));
        assertNull(repositorio.buscar("   "));
        assertFalse(repositorio.existe(null));
    }

    /**
     * Comprueba que guardar un cliente nulo sea rechazado.
     */
    @Test
    public void debeRechazarGuardarClienteNulo() {
        // Given: una referencia nula de cliente

        // When y Then: guardar lanza IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> repositorio.guardar(null));
    }

    /**
     * Comprueba que obtenerTodos devuelva una copia defensiva.
     */
    @Test
    public void debeDevolverCopiaDefensivaEnObtenerTodos() {
        // Given: un repositorio con un visitante
        repositorio.guardar(new Visitante("V-101", "Lucia"));
        List<Cliente> copia = repositorio.obtenerTodos();

        // When: se limpia la lista obtenida
        copia.clear();

        // Then: los datos almacenados en memoria no se ven afectados
        assertEquals(1, repositorio.obtenerTodos().size());
    }
}
