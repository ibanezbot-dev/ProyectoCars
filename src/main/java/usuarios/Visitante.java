package usuarios;
/**
 * Representa a un cliente visitante sin membresía.
 * Puede rentar hasta 1 hora por kart y no recibe descuento.
 * 
 * @author ibane
 */
public class Visitante extends Usuario {
    
    private static final int MAX_HORAS = 1;
    private static final double PORCENTAJE_DESCUENTO = 0.0;

    /**
     * Crea un nuevo visitante con su identificador y nombre.
     * @param id     identificador único del visitante
     * @param nombre nombre del visitante
     */
    public Visitante(String id, String nombre) {
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
