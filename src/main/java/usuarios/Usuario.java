package usuarios;
/**
 * Clase base abstracta que centraliza los datos y validaciones comunes
 * de los usuarios del sistema de renta de go karts.
 * 
 * @author ibane
 */
public abstract class Usuario implements Cliente {
    
    private final String id;
    private final String nombre;

    /**
     * Construye un usuario validando que su identificador y nombre no sean
     * nulos ni esten en blanco.
     * @param id     identificador unico del usuario
     * @param nombre nombre del usuario
     * @throws IllegalArgumentException si el id o el nombre son nulos o vacios
     */
    protected Usuario(String id, String nombre) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo ni estar vacio.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario no puede ser nulo ni estar vacio.");
        }
        this.id = id.trim();
        this.nombre = nombre.trim();
    }

    /**
     * {@inheritDoc}
     *
     * @return identificador unico normalizado del usuario
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     *
     * @return nombre normalizado del usuario
     */
    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Calcula el descuento aplicable al subtotal usando el porcentaje definido
     * por cada subclase concreta.
     *
     * @param subtotal monto base antes de descuento
     * @return monto del descuento a aplicar
     * @throws IllegalArgumentException si el subtotal es negativo
     */
    @Override
    public double calcularDescuento(double subtotal) {
        if (subtotal < 0.0) {
            throw new IllegalArgumentException("El subtotal para calcular el descuento no puede ser negativo.");
        }
        return subtotal * getPorcentajeDescuento();
    }

    /**
     * {@inheritDoc}
     *
     * @return cantidad maxima de horas permitidas por kart
     */
    @Override
    public abstract int getMaxHoras();

    /**
     * {@inheritDoc}
     *
     * @return porcentaje de descuento en formato decimal
     */
    @Override
    public abstract double getPorcentajeDescuento();
}
