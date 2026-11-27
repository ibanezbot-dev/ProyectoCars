package karts;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * Pruebas unitarias de la administración de la flota de karts.
 *
 * @author marcial
 */
public class FlotaKartsTest {

    private FlotaKarts flota;

    /**
     * Inicializa una flota vacía antes de cada prueba.
     */
    @Before
    public void setUp() {
        flota = new FlotaKarts();
    }

    /**
     * Elimina la flota utilizada por la prueba.
     */
    @After
    public void tearDown() {
        flota = null;
    }

    /**
     * Comprueba el alta y la búsqueda de karts en la flota.
     */
    @Test
    public void debeAgregarYBuscarKartPorNumero() {
        // Given: una flota con un kart adulto registrado
        KartAdulto kart = new KartAdulto(10);
        flota.agregarKart(kart);

        // When: se busca por su número
        KartRentable encontrado = flota.buscarPorNumero(10);
        KartRentable inexistente = flota.buscarPorNumero(11);

        // Then: devuelve el kart correcto y null para el inexistente
        assertEquals(kart, encontrado);
        assertNull(inexistente);
    }

    /**
     * Comprueba que los números registrados sean únicos.
     */
    @Test
    public void debeRechazarNumeroDuplicado() {
        // Given: una flota que ya contiene el número 20
        flota.agregarKart(new KartInfantil(20));

        // When: se agrega otro kart con el mismo número
        // Then: se rechaza el duplicado
        assertThrows(IllegalStateException.class,
                () -> flota.agregarKart(new KartDoble(20)));
    }

    /**
     * Comprueba que las listas devuelvan copias defensivas.
     */
    @Test
    public void debeDevolverCopiasDeLasListas() {
        // Given: una flota con un kart registrado
        flota.agregarKart(new KartAdulto(30));
        List<KartRentable> primera = flota.listarTodos();

        // When: se solicita una segunda copia
        List<KartRentable> segunda = flota.listarTodos();

        // Then: las colecciones son independientes
        assertNotSame(primera, segunda);
        segunda.clear();
        assertEquals(1, flota.listarTodos().size());
    }

    /**
     * Comprueba que solo los karts libres aparezcan como disponibles.
     */
    @Test
    public void debeListarSoloKartsDisponibles() {
        // Given: dos karts, uno ocupado
        KartAdulto libre = new KartAdulto(40);
        KartAdulto ocupado = new KartAdulto(41);
        ocupado.ocupar();
        flota.agregarKart(libre);
        flota.agregarKart(ocupado);

        // When: se consultan los disponibles
        List<KartRentable> disponibles = flota.listarDisponibles();

        // Then: solo permanece el kart libre
        assertEquals(1, disponibles.size());
        assertEquals(40, disponibles.get(0).getNumero());
    }

    /**
     * Comprueba que la flota pueda administrar un doble de KartRentable.
     */
    @Test
    public void debeUsarDobleDeKartRentable() {
        // Given: un doble que simula un kart disponible
        KartRentableDoble doble = new KartRentableDoble(50);
        flota.agregarKart(doble);

        // When: se consulta el estado del doble
        KartRentable encontrado = flota.buscarPorNumero(50);

        // Then: la flota conserva la implementación sustituida
        assertEquals(doble, encontrado);
        assertEquals("Doble", encontrado.getTipo());
    }

    /**
     * Comprueba que no se acepten karts nulos ni con número no positivo.
     */
    @Test
    public void debeRechazarKartNuloONumeroInvalido() {
        // Given: un kart nulo y un doble con número cero

        // When y Then: ambos intentos son rechazados con IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> flota.agregarKart(null));
        assertThrows(IllegalArgumentException.class,
                () -> flota.agregarKart(new KartRentableDoble(0)));
    }

    /**
     * Comprueba que buscar por un número no positivo lance excepción.
     */
    @Test
    public void debeRechazarBusquedaConNumeroNoPositivo() {
        // Given: números de búsqueda inválidos

        // When y Then: cero y negativos lanzan IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> flota.buscarPorNumero(0));
        assertThrows(IllegalArgumentException.class,
                () -> flota.buscarPorNumero(-3));
    }

    /**
     * Doble de prueba que implementa el contrato público KartRentable.
     */
    private static class KartRentableDoble implements KartRentable {

        private final int numero;
        private boolean libre = true;

        /**
         * Crea el doble con su número identificador.
         * @param numero número del kart simulado
         */
        KartRentableDoble(int numero) {
            this.numero = numero;
        }

        @Override
        public int getNumero() {
            return numero;
        }

        @Override
        public String getTipo() {
            return "Doble";
        }

        @Override
        public double getTarifaPorHora() {
            return 400.0;
        }

        @Override
        public String getEstado() {
            return libre ? "Libre" : "En uso";
        }

        @Override
        public boolean estaLibre() {
            return libre;
        }

        @Override
        public void ocupar() {
            libre = false;
        }

        @Override
        public void liberar() {
            libre = true;
        }
    }
}
