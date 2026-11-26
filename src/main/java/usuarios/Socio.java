package usuarios;
/**
 * Representa a un cliente con membresía de socio.
 * Puede rentar hasta 2 horas por kart y recibe un 10 % de descuento.
 *
 * @author ibane
 */
public class Socio extends Usuario {
    
    private static final int MAX_HORAS = 2;
    private static final double PORCENTAJE_DESCUENTO = 0.10;

    /**
     * Crea un nuevo socio con su identificador y nombre.
     * @param id     identificador único del socio
     * @param nombre nombre del socio
     */
    public Socio(String id, String nombre) {
        super(id, nombre);
    }

    @Override
    public int getMaxHoras() {
        return MAX_HORAS;
    }

    @Override
    public double getPorcentajeDescuento() {
        return PORCENTAJE_DESCUENTO;
    }
}
