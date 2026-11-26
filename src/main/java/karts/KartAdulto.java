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
     * Crea un kart adulto disponible con el número indicado.
     *
     * @param numero número identificador del kart
     */
    public KartAdulto(int numero) {
        super(numero);
    }

    @Override
    public String getTipo() {
        return TIPO;
    }

    @Override
    public double getTarifaPorHora() {
        return TARIFA_POR_HORA;
    }
}
