package karts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas unitarias de las tarifas, tipos y estados de los karts.
 *
 * @author marcial
 */
public class KartTest {

    private Kart infantil;
    private Kart adulto;
    private Kart doble;

    /**
     * Crea los tres tipos de kart antes de cada prueba.
     */
    @Before
    public void setUp() {
        infantil = new KartInfantil(1);
        adulto = new KartAdulto(2);
        doble = new KartDoble(3);
    }

    /**
     * Libera las referencias utilizadas por cada prueba.
     */
    @After
    public void tearDown() {
        infantil = null;
        adulto = null;
        doble = null;
    }

    /**
     * Comprueba las tarifas y tipos establecidos por la regla de negocio.
     */
    @Test
    public void debeExponerTarifasPorTipo() {
        // Given: una instancia de cada tipo de kart

        // When: se consultan su tipo y tarifa
        String tipoInfantil = infantil.getTipo();
        double tarifaInfantil = infantil.getTarifaPorHora();
        String tipoAdulto = adulto.getTipo();
        double tarifaAdulto = adulto.getTarifaPorHora();
        String tipoDoble = doble.getTipo();
        double tarifaDoble = doble.getTarifaPorHora();

        // Then: coinciden con las tarifas de 200, 300 y 400
        assertEquals("Infantil", tipoInfantil);
        assertEquals(200.0, tarifaInfantil, 0.001);
        assertEquals("Adulto", tipoAdulto);
        assertEquals(300.0, tarifaAdulto, 0.001);
        assertEquals("Doble", tipoDoble);
        assertEquals(400.0, tarifaDoble, 0.001);
    }

    /**
     * Comprueba que un número de kart no positivo sea rechazado.
     */
    @Test
    public void debeRechazarNumeroNoPositivo() {
        // Given: se intenta crear un kart con número cero

        // When: se invoca el constructor
        // Then: se lanza una excepción con información clara
        assertThrows(IllegalArgumentException.class,
                () -> new KartInfantil(0));
    }

    /**
     * Comprueba que el cambio de estado libre a en uso funcione.
     */
    @Test
    public void debePoderOcuparUnKartLibre() {
        // Given: el kart inicialmente se encuentra libre
        assertTrue(infantil.estaLibre());

        // When: se ocupa
        infantil.ocupar();

        // Then: queda en uso y su estado cambia
        assertFalse(infantil.estaLibre());
        assertEquals("En uso", infantil.getEstado());
    }

    /**
     * Comprueba que liberar un kart ocupadolo lo vuelva disponible.
     */
    @Test
    public void debePoderLiberarUnKartEnUso() {
        // Given: el kart adulto está ocupado
        adulto.ocupar();

        // When: se libera
        adulto.liberar();

        // Then: vuelve a estar disponible
        assertTrue(adulto.estaLibre());
        assertEquals("Libre", adulto.getEstado());
    }
}
