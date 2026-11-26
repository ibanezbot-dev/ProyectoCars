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
     * @param numero número identificador del kart
     */
    public KartInfantil(int numero) {
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
