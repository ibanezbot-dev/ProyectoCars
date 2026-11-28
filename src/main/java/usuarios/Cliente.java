package usuarios;
/**
 * Contrato publico para los clientes que pueden realizar rentas de go karts.
 * 
 * @author ibane
 */
public interface Cliente {
    
    /**
     * Obtiene el identificador unico del cliente.
     * @return identificador del cliente
     */
    String getId();

    /**
     * Obtiene el nombre completo del cliente.
     * @return nombre del cliente
     */
    String getNombre();

    /**
     * Obtiene la cantidad maxima de horas permitidas por kart para este cliente.
     * @return maximo de horas permitidas
     */
    int getMaxHoras();

    /**
     * Obtiene el porcentaje de descuento aplicable al cliente en formato decimal
     * (por ejemplo, {@code 0.10} para un 10%).
     *
     * @return porcentaje de descuento en formato decimal
     */
    double getPorcentajeDescuento();

    /**
     * Calcula el monto de descuento correspondiente a un subtotal dado.
     *
     * @param subtotal costo base de la renta antes de descuento
     * @return monto del descuento calculado
     * @throws IllegalArgumentException si el subtotal es negativo
     */
    double calcularDescuento(double subtotal);
}
