package karts;
import java.util.ArrayList;
import java.util.List;
/**
 * Administra la coleccion de karts registrados en el establecimiento,
 * garantizando numeros unicos y copias defensivas en las consultas.
 * 
 * @author ibane
 */
public class FlotaKarts {
    
    private final List<KartRentable> karts;

    /**
     * Inicializa una flota de karts vacia.
     */
    public FlotaKarts() {
        karts = new ArrayList<>();
    }

    /**
     * Agrega un kart a la flota verificando que no sea nulo, que tenga
     * un numero valido y que no exista otro kart con el mismo numero.
     * @param kart kart a agregar a la flota
     * @throws IllegalArgumentException si el kart es nulo o su numero es invalido
     * @throws IllegalStateException    si ya existe un kart con el mismo numero
     */
    public void agregarKart(KartRentable kart) {
        if (kart == null) {
            throw new IllegalArgumentException("El kart a agregar a la flota no puede ser nulo.");
        }
        if (kart.getNumero() <= 0) {
            throw new IllegalArgumentException("El kart debe tener un numero identificador positivo.");
        }
        if (existeNumero(kart.getNumero())) {
            throw new IllegalStateException("Ya existe un kart registrado con el numero: " + kart.getNumero());
        }
        karts.add(kart);
    }

    /**
     * Busca un kart en la flota a partir de su numero identificador.
     * @param numero numero del kart a buscar
     * @return el kart encontrado o {@code null} si no esta en la flota
     * @throws IllegalArgumentException si el numero es menor o igual a cero
     */
    public KartRentable buscarPorNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException(
                    "El numero de busqueda debe ser un entero positivo.");
        }
        for (KartRentable kart : karts) {
            if (kart.getNumero() == numero) {
                return kart;
            }
        }
        return null;
    }

    /**
     * Devuelve una copia defensiva con todos los karts registrados en la flota.
     * @return lista con todos los karts de la flota
     */
    public List<KartRentable> listarTodos() {
        return new ArrayList<>(karts);
    }

    /**
     * Devuelve una nueva lista unicamente con los karts que actualmente
     * se encuentran libres.
     * @return lista de karts disponibles para renta
     */
    public List<KartRentable> listarDisponibles() {
        List<KartRentable> disponibles = new ArrayList<>();
        for (KartRentable kart : karts) {
            if (kart.estaLibre()) {
                disponibles.add(kart);
            }
        }
        return disponibles;
    }

    /**
     * Verifica si ya existe en la flota un kart con el numero especificado.
     *
     * @param numero numero identificador a comprobar
     * @return {@code true} si ya esta registrado; {@code false} en caso contrario
     */
    private boolean existeNumero(int numero) {
        for (KartRentable kart : karts) {
            if (kart.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }
}
