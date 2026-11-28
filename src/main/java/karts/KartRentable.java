package karts;
/**
 * Contrato publico para los go karts que pueden ser consultados y rentados
 * desde otros paquetes del sistema.
 * 
 * @author ibane 
 */
public interface KartRentable {
    
    /**
     * Obtiene el numero identificador unico del kart.
     *
     * @return numero del kart
     */
    int getNumero();

    /**
     * Obtiene el nombre del tipo de kart (Infantil, Adulto o Doble).
     *
     * @return descripcion del tipo de kart
     */
    String getTipo();

    /**
     * Obtiene la tarifa de renta por hora del kart.
     *
     * @return tarifa por hora
     */
    double getTarifaPorHora();

    /**
     * Obtiene la representacion textual del estado actual del kart
     * ("Libre" o "En uso").
     *
     * @return estado actual del kart
     */
    String getEstado();

    /**
     * Indica si el kart se encuentra disponible para ser rentado.
     *
     * @return {@code true} si esta libre, {@code false} si esta en uso
     */
    boolean estaLibre();

    /**
     * Cambia el estado del kart a en uso.
     *
     * @throws IllegalStateException si el kart ya se encuentra en uso
     */
    void ocupar();

    /**
     * Cambia el estado del kart a libre.
     *
     * @throws IllegalStateException si el kart ya se encuentra libre
     */
    void liberar();
}
