package karts;

/**
 * Representa un go kart de tipo adulto con tarifa fija de $300 por hora.
 *
 * @author ibane
 */
public class KartAdulto extends Kart {

    private static final String TIPO = "Adulto";
    private static final double TARIFA_POR_HORA = 300.0;

    /**
     * Crea un kart adulto disponible con el numero indicado.
     *
     * @param numero numero identificador del kart (debe ser mayor que cero)
     * @throws IllegalArgumentException si el numero es menor o igual a cero
     */
    public KartAdulto(int numero) {
        super(numero);
    }

    /**
     * Devuelve el tipo correspondiente al kart adulto.
     *
     * @return {@code "Adulto"}
     */
    @Override
    public String getTipo() {
        return TIPO;
    }

    /**
     * Devuelve la tarifa fija por hora del kart adulto.
     *
     * @return {@code 300.0}
     */
    @Override
    public double getTarifaPorHora() {
        return TARIFA_POR_HORA;
    }
}
