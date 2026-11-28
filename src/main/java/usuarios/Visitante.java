package usuarios;
/**
 * Representa a un cliente visitante sin membresia.
 * Puede rentar hasta 1 hora por kart y no recibe descuento.
 * 
 * @author ibane
 */
public class Visitante extends Usuario {
    
    private static final int MAX_HORAS = 1;
    private static final double PORCENTAJE_DESCUENTO = 0.0;

    /**
     * Crea un nuevo visitante con su identificador y nombre.
     *
     * @param id     identificador unico del visitante
     * @param nombre nombre del visitante
     * @throws IllegalArgumentException si el id o el nombre son nulos o estan en blanco
     */
    public Visitante(String id, String nombre) {
        super(id, nombre);
    }

    /**
     * Devuelve el maximo de horas por kart permitido para un visitante.
     *
     * @return {@code 1}
     */
    @Override
    public int getMaxHoras() {
        return MAX_HORAS;
    }

    /**
     * Devuelve el porcentaje de descuento aplicable a un visitante.
     *
     * @return {@code 0.0} (sin descuento)
     */
    @Override
    public double getPorcentajeDescuento() {
        return PORCENTAJE_DESCUENTO;
    }
}
