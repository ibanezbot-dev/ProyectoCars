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
     *
     * @param id     identificador único del socio
     * @param nombre nombre del socio
     * @throws IllegalArgumentException si el id o el nombre son nulos o están en blanco
     */
    public Socio(String id, String nombre) {
        super(id, nombre);
    }

    /**
     * Devuelve el máximo de horas por kart permitido para un socio.
     *
     * @return {@code 2}
     */
    @Override
    public int getMaxHoras() {
        return MAX_HORAS;
    }

    /**
     * Devuelve el porcentaje de descuento aplicable a un socio.
     *
     * @return {@code 0.10} (10%)
     */
    @Override
    public double getPorcentajeDescuento() {
        return PORCENTAJE_DESCUENTO;
    }
}
