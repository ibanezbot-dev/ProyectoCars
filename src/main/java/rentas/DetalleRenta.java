package rentas;
import karts.KartRentable;
/**
 * Representa la asignación de un kart específico y su cantidad de horas dentro
 * de una renta. Su constructor es de acceso de paquete para garantizar la
 * composición con {@link Renta}.
 * 
 * @author ibane
 */
public class DetalleRenta {
    
    private final KartRentable kart;
    private final int horas;

    /**
     * Construye un detalle de renta validando que el kart no sea nulo y que
     * las horas sean un entero positivo.
     * @param kart  kart asignado al detalle
     * @param horas cantidad de horas rentadas para este kart
     * @throws IllegalArgumentException si el kart es nulo o las horas no son positivas
     */
    DetalleRenta(KartRentable kart, int horas) {
        if (kart == null) {
            throw new IllegalArgumentException("El kart del detalle de renta no puede ser nulo.");
        }
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas del detalle deben ser al menos 1.");
        }
        this.kart = kart;
        this.horas = horas;
    }

    /**
     * Obtiene el kart asociado a este detalle.
     * @return kart rentado
     */
    public KartRentable getKart() {
        return kart;
    }

    /**
     * Obtiene la cantidad de horas asignadas al kart.
     * @return horas de renta del kart
     */
    public int getHoras() {
        return horas;
    }

    /**
     * Calcula el subtotal correspondiente a este kart multiplicando su tarifa
     * por hora por las horas asignadas.
     * @return subtotal del detalle
     */
    public double calcularSubtotal() {
        return kart.getTarifaPorHora() * horas;
    }
}
