package rentas;

import java.util.HashMap;
import java.util.Map;
import karts.KartAdulto;
import karts.KartDoble;
import karts.KartInfantil;
import karts.KartRentable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import usuarios.Socio;
import usuarios.Visitante;
import usuarios.Cliente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas unitarias de la creación y finalización de rentas.
 *
 * @author marcial
 */
public class GestorRentasTest {

    private GestorRentas gestor;

    /**
     * Crea un gestor nuevo antes de cada prueba.
     */
    @Before
    public void setUp() {
        gestor = new GestorRentas();
    }

    /**
     * Libera el gestor utilizado por la prueba.
     */
    @After
    public void tearDown() {
        gestor = null;
    }

    /**
     * Comprueba la creación de una renta y el descuento de socio.
     */
    @Test
    public void debeCrearRentaDeSocioConDescuento() {
        // Given: un socio y un kart adulto por dos horas
        Socio socio = new Socio("S-1", "Ana");
        KartAdulto kart = new KartAdulto(1);
        Map<KartRentable, Integer> seleccion = new HashMap<>();
        seleccion.put(kart, 2);

        // When: se crea la renta
        Renta renta = gestor.crearRenta(socio, seleccion);

        // Then: el subtotal es 600, el descuento 60 y el total 540
        assertEquals(600.0, renta.calcularSubtotal(), 0.001);
        assertEquals(60.0, renta.calcularDescuento(), 0.001);
        assertEquals(540.0, renta.calcularTotal(), 0.001);
        assertFalse(kart.estaLibre());
        assertEquals(1, gestor.getTotalRentas());
    }

    /**
     * Comprueba que el visitante tenga máximo una hora y sin descuento.
     */
    @Test
    public void debeCrearRentaDeVisitanteSinDescuento() {
        // Given: un visitante y un kart infantil por una hora
        Visitante visitante = new Visitante("V-1", "Luis");
        KartInfantil kart = new KartInfantil(2);
        Map<KartRentable, Integer> seleccion = new HashMap<>();
        seleccion.put(kart, 1);

        // When: se crea y calcula la renta
        Renta renta = gestor.crearRenta(visitante, seleccion);

        // Then: el total es igual a la tarifa de 200
        assertEquals(200.0, renta.calcularTotal(), 0.001);
        assertEquals(0.0, renta.calcularDescuento(), 0.001);
    }

    /**
     * Comprueba que no se pueda rentar un kart ya ocupado.
     */
    @Test
    public void debeRechazarKartYaEnUso() {
        // Given: un kart ocupado previamente
        KartDoble kart = new KartDoble(3);
        kart.ocupar();
        Map<KartRentable, Integer> seleccion = new HashMap<>();
        seleccion.put(kart, 1);

        // When: se intenta incluirlo en una renta
        // Then: se lanza IllegalStateException
        assertThrows(IllegalStateException.class,
                () -> gestor.crearRenta(new Visitante("V-2", "Eva"), seleccion));
        assertEquals(0, gestor.getTotalRentas());
    }

    /**
     * Comprueba que se respeten máximo de karts y máximo de horas.
     */
    @Test
    public void debeRechazarSeleccionInvalida() {
        // Given: un visitante con una selección que excede sus reglas
        Map<KartRentable, Integer> demasiadasHoras = new HashMap<>();
        demasiadasHoras.put(new KartAdulto(4), 2);

        // When y Then: dos horas exceden el máximo del visitante
        assertThrows(IllegalArgumentException.class,
                () -> gestor.crearRenta(new Visitante("V-3", "Leo"),
                        demasiadasHoras));

        // Given: cuatro karts, fuera del máximo permitido
        Map<KartRentable, Integer> demasiadosKarts = new HashMap<>();
        demasiadosKarts.put(new KartAdulto(5), 1);
        demasiadosKarts.put(new KartAdulto(6), 1);
        demasiadosKarts.put(new KartAdulto(7), 1);
        demasiadosKarts.put(new KartAdulto(8), 1);

        // When y Then: se rechaza la renta
        assertThrows(IllegalArgumentException.class,
                () -> gestor.crearRenta(new Socio("S-4", "Pablo"),
                        demasiadosKarts));
    }

    /**
     * Comprueba que finalizar una renta libere sus karts.
     */
    @Test
    public void debeFinalizarRentaYLiberarKart() {
        // Given: una renta activa
        KartAdulto kart = new KartAdulto(9);
        Map<KartRentable, Integer> seleccion = new HashMap<>();
        seleccion.put(kart, 1);
        Renta renta = gestor.crearRenta(
                new Socio("S-5", "Sara"), seleccion);

        // When: se finaliza
        gestor.finalizarRenta(renta.getNumeroRenta());

        // Then: la renta deja de estar activa y el kart queda libre
        assertFalse(renta.estaActiva());
        assertTrue(kart.estaLibre());
    }

    /**
     * Comprueba que el gestor use la abstracción Cliente mediante un doble.
     */
    @Test
    public void debeUsarDobleDeCliente() {
        // Given: un cliente falso que permite dos horas sin descuento
        ClienteDoble cliente = new ClienteDoble();
        KartAdulto kart = new KartAdulto(10);
        Map<KartRentable, Integer> seleccion = new HashMap<>();
        seleccion.put(kart, 2);

        // When: se crea la renta
        Renta renta = gestor.crearRenta(cliente, seleccion);

        // Then: se respeta el doble y se registra la renta
        assertEquals(600.0, renta.calcularSubtotal(), 0.001);
        assertEquals(540.0, renta.calcularTotal(), 0.001);
    }

    /**
     * Doble de prueba de Cliente que representa a un socio configurable.
     */
    private static class ClienteDoble implements Cliente {

        @Override
        public String getId() {
            return "DOBLE-1";
        }

        @Override
        public String getNombre() {
            return "Cliente de prueba";
        }

        @Override
        public int getMaxHoras() {
            return 2;
        }

        @Override
        public double getPorcentajeDescuento() {
            return 0.10;
        }

        @Override
        public double calcularDescuento(double subtotal) {
            return subtotal * getPorcentajeDescuento();
        }
    }
}
