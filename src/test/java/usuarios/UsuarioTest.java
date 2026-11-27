package usuarios;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Pruebas unitarias de socios y visitantes.
 *
 * @author marcial
 */
public class UsuarioTest {

    private Socio socio;
    private Visitante visitante;

    /**
     * Crea los tipos de cliente usados en las pruebas.
     */
    @Before
    public void setUp() {
        socio = new Socio("S-1", "Ana");
        visitante = new Visitante("V-1", "Luis");
    }

    /**
     * Libera las referencias de cliente después de cada prueba.
     */
    @After
    public void tearDown() {
        socio = null;
        visitante = null;
    }

    /**
     * Comprueba los límites de horas de socios y visitantes.
     */
    @Test
    public void debeAplicarLimitesDeHorasPorTipoCliente() {
        // Given: un socio y un visitante

        // When: se consultan sus máximos
        int maximoSocio = socio.getMaxHoras();
        int maximoVisitante = visitante.getMaxHoras();

        // Then: el socio llega a dos horas y el visitante a una
        assertEquals(2, maximoSocio);
        assertEquals(1, maximoVisitante);
    }

    /**
     * Comprueba el descuento del diez por ciento del socio.
     */
    @Test
    public void debeCalcularDescuentoDelSocio() {
        // Given: un subtotal de cien pesos para el socio

        // When: se calcula el descuento
        double descuento = socio.calcularDescuento(100.0);

        // Then: corresponde al diez por ciento
        assertEquals(10.0, descuento, 0.001);
        assertEquals(0.10, socio.getPorcentajeDescuento(), 0.001);
    }

    /**
     * Comprueba que los visitantes no tengan descuento.
     */
    @Test
    public void visitanteNoDebeRecibirDescuento() {
        // Given: un subtotal para el visitante

        // When: se calcula el descuento
        double descuento = visitante.calcularDescuento(500.0);

        // Then: el monto sigue siendo cero
        assertEquals(0.0, descuento, 0.001);
        assertEquals(0.0, visitante.getPorcentajeDescuento(), 0.001);
    }

    /**
     * Comprueba que las excepciones invaliden datos incompletos.
     */
    @Test
    public void debeRechazarSubtotalNegativoYDatosVacios() {
        // Given: datos inválidos para usuario y descuento

        // When y Then: ambos casos deben lanzar excepciones claras
        assertThrows(IllegalArgumentException.class,
                () -> socio.calcularDescuento(-1.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Socio(" ", "Ana"));
        assertThrows(IllegalArgumentException.class,
                () -> new Visitante("V-2", ""));
    }
}
