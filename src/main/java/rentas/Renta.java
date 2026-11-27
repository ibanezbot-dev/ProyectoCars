package rentas;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import karts.KartRentable;
import usuarios.Cliente;
/**
 * Representa una renta de go karts realizada por un cliente. Compone sus
 * propios objetos {@link DetalleRenta} y delega el cálculo de descuento
 * al polimorfismo de {@link Cliente}.
 *
 * @author ibane
 */
public class Renta {
    
    private final int numeroRenta;
    private final Cliente cliente;
    private final List<DetalleRenta> detalles;
    private boolean activa;

    /**
     * Construye una renta activa con sus detalles correspondientes. El acceso
     * de paquete asegura que las rentas se creen a través de {@link GestorRentas}.
     * @param numeroRenta   número consecutivo asignado a la renta
     * @param cliente       cliente que contrata la renta
     * @param kartsConHoras mapa de karts y sus horas asignadas
     * @throws IllegalArgumentException si algún parámetro es inválido o vacío
     */
    Renta(int numeroRenta, Cliente cliente,
            Map<KartRentable, Integer> kartsConHoras) {
        if (numeroRenta <= 0) {
            throw new IllegalArgumentException("El número de renta debe ser un entero positivo.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente de la renta no puede ser nulo.");
        }
        if (kartsConHoras == null || kartsConHoras.isEmpty()) {
            throw new IllegalArgumentException("La renta debe incluir al menos un kart.");
        }
        this.numeroRenta = numeroRenta;
        this.cliente = cliente;
        detalles = new ArrayList<>();
        for (Map.Entry<KartRentable, Integer> entrada : kartsConHoras.entrySet()) {
            Integer horas = entrada.getValue();
            if (horas == null) {
                throw new IllegalArgumentException("La cantidad de horas de un kart no puede ser nula.");
            }
            detalles.add(new DetalleRenta(entrada.getKey(), horas));
        }
        activa = true;
    }

    /**
     * Obtiene el número identificador de la renta.
     * @return número de renta
     */
    public int getNumeroRenta() {
        return numeroRenta;
    }

    /**
     * Obtiene el cliente titular de la renta.
     * @return cliente de la renta
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Devuelve una copia defensiva de los detalles de la renta.
     * @return lista con los detalles de karts y horas
     */
    public List<DetalleRenta> getDetalles() {
        return new ArrayList<>(detalles);
    }

    /**
     * Indica si la renta sigue en curso.
     * @return {@code true} si está activa, {@code false} si ya finalizó
     */
    public boolean estaActiva() {
        return activa;
    }

    /**
     * Suma el costo individual de cada kart por sus horas asignadas antes
     * de aplicar descuentos.
     * @return subtotal de la renta
     */
    public double calcularSubtotal() {
        double subtotal = 0.0;
        for (DetalleRenta detalle : detalles) {
            subtotal += detalle.calcularSubtotal();
        }
        return subtotal;
    }

    /**
     * Calcula el monto de descuento aplicable según el tipo de cliente.
     * @return monto del descuento
     */
    public double calcularDescuento() {
        return cliente.calcularDescuento(calcularSubtotal());
    }

    /**
     * Calcula el monto final a pagar tras restar el descuento al subtotal.
     * @return costo total de la renta
     */
    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    /**
     * Finaliza la renta y libera todos los karts asociados para que vuelvan
     * a estar disponibles.
     * @throws IllegalStateException si la renta ya fue finalizada previamente
     */
    public void finalizar() {
        if (!activa) {
            throw new IllegalStateException("La renta número " + numeroRenta + " ya fue finalizada.");
        }
        for (DetalleRenta detalle : detalles) {
            detalle.getKart().liberar();
        }
        activa = false;
    }
}
