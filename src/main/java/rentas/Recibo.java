package rentas;
import java.util.Locale;
import karts.KartRentable;
import usuarios.Cliente;
/**
 * Genera la representación textual del comprobante de una renta sin realizar
 * impresiones directas en consola.
 * 
 * @author ibane
 */
public class Recibo {
    
    /**
     * Construye el texto completo del recibo correspondiente a la renta dada.
     * @param renta renta de la cual se generará el comprobante
     * @return texto formateado del recibo
     * @throws IllegalArgumentException si la renta es nula
     */
    public String generar(Renta renta) {
        if (renta == null) {
            throw new IllegalArgumentException("La renta para generar el recibo no puede ser nula.");
        }

        Cliente cliente = renta.getCliente();
        String estadoRenta = renta.estaActiva() ? "Activa" : "Finalizada";
        double porcentaje = cliente.getPorcentajeDescuento() * 100.0;
        StringBuilder texto = new StringBuilder();
        texto.append("=== RECIBO DE RENTA #")
                .append(renta.getNumeroRenta())
                .append(" ===\n");
        texto.append("Estado: ").append(estadoRenta).append('\n');
        texto.append("Cliente: ").append(cliente.getNombre())
                .append(" (ID: ").append(cliente.getId()).append(")\n");
        texto.append("Detalle de karts:\n");

        for (DetalleRenta detalle : renta.getDetalles()) {
            KartRentable kart = detalle.getKart();
            texto.append(String.format(Locale.US,
                    "- Kart #%d (%s): %d h x $%.2f = $%.2f%n",
                    kart.getNumero(),
                    kart.getTipo(),
                    detalle.getHoras(),
                    kart.getTarifaPorHora(),
                    detalle.calcularSubtotal()));
        }

        texto.append(String.format(Locale.US,
                "Subtotal: $%.2f%n", renta.calcularSubtotal()));
        texto.append(String.format(Locale.US,
                "Descuento (%.0f%%): -$%.2f%n",
                porcentaje, renta.calcularDescuento()));
        texto.append(String.format(Locale.US,
                "Total a pagar: $%.2f", renta.calcularTotal()));

        return texto.toString();
    }
}
