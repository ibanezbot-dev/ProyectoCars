package rentas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import karts.KartRentable;
import usuarios.Cliente;
/**
 * Coordina la creación, validación, finalización y conteo histórico de las
 * rentas de go karts.
 * 
 * @author ibane
 */
public class GestorRentas {
    
    private static final int MIN_KARTS = 1;
    private static final int MAX_KARTS = 3;
    private static final int MIN_HORAS = 1;
    private final List<Renta> rentas;
    private int totalRentas;

    /**
     * Inicializa un gestor de rentas vacío con el contador en cero.
     */
    public GestorRentas() {
        rentas = new ArrayList<>();
        totalRentas = 0;
    }

    /**
     * Valida todas las reglas de negocio antes de ocupar los karts o
     * incrementar el contador, y crea una nueva renta activa.
     * @param cliente       cliente que solicita la renta
     * @param kartsConHoras mapa con entre 1 y 3 karts y sus horas solicitadas
     * @return la nueva renta creada y registrada
     * @throws IllegalArgumentException si el cliente, el mapa, la cantidad de
     *                                  karts, los números repetidos o las horas
     *                                  son inválidos
     * @throws IllegalStateException    si alguno de los karts ya está en uso
     */
    public Renta crearRenta(Cliente cliente,
            Map<KartRentable, Integer> kartsConHoras) {
        validarCliente(cliente);
        validarKartsYHoras(cliente, kartsConHoras);

        for (KartRentable kart : kartsConHoras.keySet()) {
            kart.ocupar();
        }
        totalRentas++;
        Renta nuevaRenta = new Renta(totalRentas, cliente, kartsConHoras);
        rentas.add(nuevaRenta);
        return nuevaRenta;
    }

    /**
     * Finaliza una renta existente buscándola por su número consecutivo y
     * libera sus karts.
     * @param numeroRenta número identificador de la renta a finalizar
     * @throws IllegalArgumentException si el número no es positivo o no existe
     * @throws IllegalStateException    si la renta ya había sido finalizada
     */
    public void finalizarRenta(int numeroRenta) {
        if (numeroRenta <= 0) {
            throw new IllegalArgumentException("El número de renta debe ser un entero positivo.");
        }
        Renta rentaEncontrada = buscarRentaPorNumero(numeroRenta);
        if (rentaEncontrada == null) {
            throw new IllegalArgumentException("No existe ninguna renta registrada con el número: "+ numeroRenta);
        }
        rentaEncontrada.finalizar();
    }

    /**
     * Obtiene el total histórico de rentas realizadas en el sistema.
     * @return contador total de rentas
     */
    public int getTotalRentas() {
        return totalRentas;
    }

    /**
     * Devuelve una copia defensiva con todas las rentas registradas.
     * @return lista de rentas realizadas
     */
    public List<Renta> listarRentas() {
        return new ArrayList<>(rentas);
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo para crear una renta.");
        }
    }

    private void validarKartsYHoras(Cliente cliente, Map<KartRentable, Integer> kartsConHoras) {
        if (kartsConHoras == null || kartsConHoras.size() < MIN_KARTS || kartsConHoras.size() > MAX_KARTS) {
            throw new IllegalArgumentException("Una renta debe incluir entre " + MIN_KARTS + " y " + MAX_KARTS + " karts.");
        }

        int maxHorasCliente = cliente.getMaxHoras(); 
        Set<Integer> numerosVistos = new HashSet<>();

        for (Map.Entry<KartRentable, Integer> entrada : kartsConHoras.entrySet()) {
            KartRentable kart = entrada.getKey();
            Integer horas = entrada.getValue();

            if (kart == null) {
                throw new IllegalArgumentException("Ningún kart dentro de la renta puede ser nulo.");
            }
            if (horas == null) {
                throw new IllegalArgumentException("Las horas para el kart número " + kart.getNumero() + " no pueden ser nulas.");
            }
            if (horas < MIN_HORAS || horas > maxHorasCliente) {
                throw new IllegalArgumentException("Las horas para el kart número " + kart.getNumero() + " deben estar entre " + MIN_HORAS + " y " + maxHorasCliente + " para este cliente.");
            }
            if (!numerosVistos.add(kart.getNumero())) {
                throw new IllegalArgumentException("No se permite incluir dos karts con el mismo número: " + kart.getNumero());
            }
            if (!kart.estaLibre()) {
                throw new IllegalStateException("El kart número " + kart.getNumero() + " ya se encuentra en uso y no puede rentarse.");
            }
        }
    }

    private Renta buscarRentaPorNumero(int numeroRenta) {
        for (Renta renta : rentas) {
            if (renta.getNumeroRenta() == numeroRenta) {
                return renta;
            }
        }
        return null;
    }
}
