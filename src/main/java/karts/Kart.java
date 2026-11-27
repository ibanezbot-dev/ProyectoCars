package karts;

/**
 * Clase base abstracta que centraliza el número identificador y el control
 * de disponibilidad de todos los tipos de go karts.
 *
 * @author ibane
 */
public abstract class Kart implements KartRentable {

    private static final String ESTADO_LIBRE = "Libre";
    private static final String ESTADO_EN_USO = "En uso";

    private final int numero;
    private boolean libre;

    /**
     * Construye un kart inicialmente libre validando que su número sea positivo.
     *
     * @param numero número identificador del kart (debe ser mayor que cero)
     * @throws IllegalArgumentException si el número es menor o igual a cero
     */
    protected Kart(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException(
                    "El número del kart debe ser un entero positivo.");
        }
        this.numero = numero;
        libre = true;
    }

    /**
     * {@inheritDoc}
     *
     * @return número identificador del kart
     */
    @Override
    public int getNumero() {
        return numero;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code "Libre"} si está disponible o {@code "En uso"} si está ocupado
     */
    @Override
    public String getEstado() {
        if (libre) {
            return ESTADO_LIBRE;
        }
        return ESTADO_EN_USO;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} si el kart está disponible; {@code false} si está en uso
     */
    @Override
    public boolean estaLibre() {
        return libre;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException si el kart ya se encuentra en uso
     */
    @Override
    public void ocupar() {
        if (!libre) {
            throw new IllegalStateException(
                    "El kart número " + numero + " ya se encuentra en uso.");
        }
        libre = false;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException si el kart ya se encuentra libre
     */
    @Override
    public void liberar() {
        if (libre) {
            throw new IllegalStateException(
                    "El kart número " + numero + " ya se encuentra libre.");
        }
        libre = true;
    }

    /**
     * {@inheritDoc}
     *
     * @return nombre descriptivo del tipo de kart
     */
    @Override
    public abstract String getTipo();

    /**
     * {@inheritDoc}
     *
     * @return tarifa de renta por hora en pesos
     */
    @Override
    public abstract double getTarifaPorHora();
}
