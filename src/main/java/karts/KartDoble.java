package karts;

/**
 * Representa un go kart de tipo doble con tarifa fija de $400 por hora.
 *
 * @author ibane
 */
public class KartDoble extends Kart {

    private static final String TIPO = "Doble";
    private static final double TARIFA_POR_HORA = 400.0;

    /**
     * Crea un kart doble disponible con el numero indicado.
     *
     * @param numero numero identificador del kart (debe ser mayor que cero)
     * @throws IllegalArgumentException si el numero es menor o igual a cero
     */
    public KartDoble(int numero) {
        super(numero);
    }

    /**
     * Devuelve el tipo correspondiente al kart doble.
     *
     * @return {@code "Doble"}
     */
    @Override
    public String getTipo() {
        return TIPO;
    }

    /**
     * Devuelve la tarifa fija por hora del kart doble.
     *
     * @return {@code 400.0}
     */
    @Override
    public double getTarifaPorHora() {
        return TARIFA_POR_HORA;
    }
}
