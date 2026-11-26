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
     * Crea un kart doble disponible con el número indicado.
     * @param numero número identificador del kart
     */
    public KartDoble(int numero) {
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
