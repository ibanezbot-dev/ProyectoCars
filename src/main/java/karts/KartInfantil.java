package karts;

/**
 * Representa un go kart de tipo infantil con tarifa fija de $200 por hora.
 *
 * @author ibane
 */
public class KartInfantil extends Kart {

    private static final String TIPO = "Infantil";
    private static final double TARIFA_POR_HORA = 200.0;

    /**
     * Crea un kart infantil disponible con el número indicado.
     *
     * @param numero número identificador del kart (debe ser mayor que cero)
     * @throws IllegalArgumentException si el número es menor o igual a cero
     */
    public KartInfantil(int numero) {
        super(numero);
    }

    /**
     * Devuelve el tipo correspondiente al kart infantil.
     *
     * @return {@code "Infantil"}
     */
    @Override
    public String getTipo() {
        return TIPO;
    }

    /**
     * Devuelve la tarifa fija por hora del kart infantil.
     *
     * @return {@code 200.0}
     */
    @Override
    public double getTarifaPorHora() {
        return TARIFA_POR_HORA;
    }
}
