package rentas;

import java.util.HashMap;
import java.util.Map;
import karts.KartAdulto;
import karts.KartInfantil;
import karts.KartRentable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import usuarios.Socio;
import usuarios.Visitante;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas unitarias de la generación de recibos.
 *
 * @author marcial
 */
public class ReciboTest {

    private Recibo recibo;

    /**
     * Crea el generador de recibos antes de cada prueba.
     */
    @Before
    public void setUp() {
        recibo = new Recibo();
    }

    /**
     * Libera el generador después de cada prueba.
     */
    @After
    public void tearDown() {
        recibo = null;
    }

    /**
     * Comprueba que el recibo incluya datos y el total final.
     */
    @Test
    public void debeGenerarReciboCompletoParaSocio() {
        // Given: una renta de socio con un kart adulto
        GestorRentas gestor = new GestorRentas();
        KartAdulto kart = new KartAdulto(1);
        Map<KartRentable, Integer> seleccion = new HashMap<>();
        seleccion.put(kart, 2);
        Renta renta = gestor.crearRenta(
                new Socio("S-1", "Ana"), seleccion);

        // When: se genera el recibo
        String texto = recibo.generar(renta);

        // Then: contiene tipo, subtotal, descuento y total
        assertTrue(texto.contains("Kart #1 (Adulto): 2 h"));
        assertTrue(texto.contains("Subtotal: $600.00"));
        assertTrue(texto.contains("Descuento (10%)"));
        assertTrue(texto.contains("Total a pagar: $540.00"));
    }

    /**
     * Comprueba que un visitante aparezca sin descuento en el recibo.
     */
    @Test
    public void debeGenerarReciboSinDescuentoParaVisitante() {
        // Given: una renta de visitante
        GestorRentas gestor = new GestorRentas();
        KartInfantil kart = new KartInfantil(2);
        Map<KartRentable, Integer> seleccion = new HashMap<>();
        seleccion.put(kart, 1);
        Renta renta = gestor.crearRenta(
                new Visitante("V-1", "Luis"), seleccion);

        // When: se genera el recibo
        String texto = recibo.generar(renta);

        // Then: el descuento mostrado es cero
        assertTrue(texto.contains("Descuento (0%)"));
        assertTrue(texto.contains("Total a pagar: $200.00"));
    }

    /**
     * Comprueba que no se genere un recibo sin renta.
     */
    @Test
    public void debeRechazarRentaNula() {
        // Given: no existe una renta

        // When: se solicita generar el recibo
        // Then: se lanza una excepción clara
        assertThrows(IllegalArgumentException.class,
                () -> recibo.generar(null));
    }

    /**
     * Comprueba que el recibo refleje una renta finalizada.
     */
    @Test
    public void debeMostrarRentaFinalizada() {
        // Given: una renta que es finalizada
        GestorRentas gestor = new GestorRentas();
        KartInfantil kart = new KartInfantil(3);
        Map<KartRentable, Integer> seleccion = new HashMap<>();
        seleccion.put(kart, 1);
        Renta renta = gestor.crearRenta(
                new Visitante("V-2", "Eva"), seleccion);
        gestor.finalizarRenta(renta.getNumeroRenta());

        // When: se genera el recibo
        String texto = recibo.generar(renta);

        // Then: el estado mostrado es Finalizada
        assertTrue(texto.contains("Estado: Finalizada"));
    }
}
