package app;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import karts.FlotaKarts;
import karts.KartAdulto;
import karts.KartDoble;
import karts.KartInfantil;
import karts.KartRentable;
import rentas.GestorRentas;
import rentas.Recibo;
import rentas.Renta;
import usuarios.Cliente;
import usuarios.RegistroUsuarios;
import usuarios.Socio;
import usuarios.Visitante;

/**
 * Clase principal de consola para probar el funcionamiento del sistema de
 * renta de go karts tanto de manera interactiva como mediante una demostración
 * automática.
 *
 * @author ibane
 */
public class Main {

    /**
     * Constructor privado para evitar instanciación de la clase utilitaria de entrada.
     */
    private Main() {
    }

    /**
     * Punto de entrada de la aplicación de consola.
     *
     * @param args argumentos de línea de comandos; si se pasa {@code --demo},
     *             ejecuta únicamente la demostración automática y finaliza
     */
    public static void main(String[] args) {
        FlotaKarts flota = new FlotaKarts();
        RegistroUsuarios registro = new RegistroUsuarios();
        GestorRentas gestor = new GestorRentas();
        Recibo recibo = new Recibo();

        cargarDatosIniciales(flota, registro);

        if (args != null && args.length > 0 && "--demo".equalsIgnoreCase(args[0])) {
            ejecutarDemostracionAutomatica(recibo);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("==========================================");
        System.out.println("     SISTEMA DE RENTA DE GO KARTS");
        System.out.println("==========================================");
        System.out.println("Datos iniciales cargados: 6 karts y 2 clientes (S-1 Ana, V-1 Luis).");

        while (continuar) {
            mostrarMenu();
            if (!scanner.hasNextLine()) {
                break;
            }
            String opcion = scanner.nextLine().trim();

            try {
                switch (opcion) {
                    case "1":
                        mostrarFlota(flota);
                        break;
                    case "2":
                        agregarKart(scanner, flota);
                        break;
                    case "3":
                        registrarCliente(scanner, registro);
                        break;
                    case "4":
                        mostrarClientes(registro);
                        break;
                    case "5":
                        crearRenta(scanner, flota, registro, gestor, recibo);
                        break;
                    case "6":
                        finalizarRenta(scanner, gestor, recibo);
                        break;
                    case "7":
                        mostrarHistorialRentas(gestor, recibo);
                        break;
                    case "8":
                        ejecutarDemostracionAutomatica(recibo);
                        break;
                    case "0":
                        continuar = false;
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida. Selecciona una opción del menú.");
                        break;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Carga karts y clientes de ejemplo para facilitar pruebas inmediatas.
     *
     * @param flota    flota donde se registran los karts iniciales
     * @param registro registro donde se dan de alta los clientes iniciales
     */
    private static void cargarDatosIniciales(FlotaKarts flota, RegistroUsuarios registro) {
        flota.agregarKart(new KartInfantil(1));
        flota.agregarKart(new KartInfantil(2));
        flota.agregarKart(new KartAdulto(3));
        flota.agregarKart(new KartAdulto(4));
        flota.agregarKart(new KartDoble(5));
        flota.agregarKart(new KartDoble(6));

        registro.registrar(new Socio("S-1", "Ana López"));
        registro.registrar(new Visitante("V-1", "Luis Pérez"));
    }

    /**
     * Imprime el menú principal de opciones disponibles.
     */
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("--------------- MENÚ ---------------");
        System.out.println("1. Ver flota de karts (todos y disponibles)");
        System.out.println("2. Agregar kart a la flota");
        System.out.println("3. Registrar cliente (Socio / Visitante)");
        System.out.println("4. Ver clientes registrados");
        System.out.println("5. Crear nueva renta y generar recibo");
        System.out.println("6. Finalizar renta activa");
        System.out.println("7. Ver historial de rentas");
        System.out.println("8. Ejecutar demostración automática");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opción: ");
    }

    /**
     * Muestra todos los karts registrados junto con el conteo de disponibles.
     *
     * @param flota flota de karts a consultar
     */
    private static void mostrarFlota(FlotaKarts flota) {
        List<KartRentable> todos = flota.listarTodos();
        List<KartRentable> disponibles = flota.listarDisponibles();

        System.out.println("\n--- Flota de karts (" + disponibles.size() + " disponibles de " + todos.size() + ") ---");
        if (todos.isEmpty()) {
            System.out.println("No hay karts registrados.");
            return;
        }
        for (KartRentable kart : todos) {
            System.out.printf("Kart #%d | Tipo: %-8s | Tarifa: $%.2f/h | Estado: %s%n",
                    kart.getNumero(),
                    kart.getTipo(),
                    kart.getTarifaPorHora(),
                    kart.getEstado());
        }
    }

    /**
     * Solicita datos por consola y agrega un nuevo kart a la flota.
     *
     * @param scanner lector de entrada estándar
     * @param flota   flota de karts donde se agregará el nuevo kart
     */
    private static void agregarKart(Scanner scanner, FlotaKarts flota) {
        System.out.print("Número del nuevo kart: ");
        int numero = leerEntero(scanner);

        System.out.println("Tipo de kart: 1) Infantil ($200/h)  2) Adulto ($300/h)  3) Doble ($400/h)");
        System.out.print("Elige tipo: ");
        String tipo = scanner.nextLine().trim();

        KartRentable nuevoKart;
        switch (tipo) {
            case "1":
                nuevoKart = new KartInfantil(numero);
                break;
            case "2":
                nuevoKart = new KartAdulto(numero);
                break;
            case "3":
                nuevoKart = new KartDoble(numero);
                break;
            default:
                throw new IllegalArgumentException("Tipo de kart no válido. Usa 1, 2 o 3.");
        }

        flota.agregarKart(nuevoKart);
        System.out.println("Kart #" + numero + " (" + nuevoKart.getTipo() + ") agregado correctamente.");
    }

    /**
     * Solicita datos por consola y registra un nuevo socio o visitante.
     *
     * @param scanner  lector de entrada estándar
     * @param registro registro de usuarios
     */
    private static void registrarCliente(Scanner scanner, RegistroUsuarios registro) {
        System.out.println("Tipo de cliente: 1) Socio (máx. 2 h, 10% desc.)  2) Visitante (máx. 1 h, 0% desc.)");
        System.out.print("Elige tipo: ");
        String tipo = scanner.nextLine().trim();

        System.out.print("ID del cliente (ej. S-2 o V-2): ");
        String id = scanner.nextLine();
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();

        Cliente cliente;
        if ("1".equals(tipo)) {
            cliente = new Socio(id, nombre);
        } else if ("2".equals(tipo)) {
            cliente = new Visitante(id, nombre);
        } else {
            throw new IllegalArgumentException("Tipo de cliente no válido. Usa 1 o 2.");
        }

        registro.registrar(cliente);
        System.out.println("Cliente registrado: " + cliente.getNombre() + " (ID: " + cliente.getId() + ").");
    }

    /**
     * Imprime la lista de clientes registrados con sus reglas de horas y descuento.
     *
     * @param registro registro de usuarios a consultar
     */
    private static void mostrarClientes(RegistroUsuarios registro) {
        List<Cliente> clientes = registro.listarClientes();
        System.out.println("\n--- Clientes registrados (" + clientes.size() + ") ---");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Cliente cliente : clientes) {
            System.out.printf("ID: %-6s | Nombre: %-18s | Máx. horas: %d | Descuento: %.0f%%%n",
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getMaxHoras(),
                    cliente.getPorcentajeDescuento() * 100.0);
        }
    }

    /**
     * Crea una renta interactiva solicitando cliente, cantidad de karts y horas.
     *
     * @param scanner  lector de entrada estándar
     * @param flota    flota de karts
     * @param registro registro de clientes
     * @param gestor   gestor de rentas
     * @param recibo   generador de recibos
     */
    private static void crearRenta(Scanner scanner, FlotaKarts flota,
            RegistroUsuarios registro, GestorRentas gestor, Recibo recibo) {
        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine();
        Cliente cliente = registro.buscarPorId(idCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("No se encontró un cliente registrado con ID: " + idCliente.trim());
        }

        mostrarFlota(flota);
        System.out.print("¿Cuántos karts deseas rentar? (1 a 3): ");
        int cantidad = leerEntero(scanner);
        if (cantidad < 1 || cantidad > 3) {
            throw new IllegalArgumentException("La cantidad de karts debe estar entre 1 y 3.");
        }

        Map<KartRentable, Integer> seleccion = new LinkedHashMap<>();
        for (int i = 1; i <= cantidad; i++) {
            System.out.print("Número del kart #" + i + ": ");
            int numeroKart = leerEntero(scanner);
            KartRentable kart = flota.buscarPorNumero(numeroKart);
            if (kart == null) {
                throw new IllegalArgumentException("No existe un kart con el número: " + numeroKart);
            }
            if (seleccion.containsKey(kart)) {
                throw new IllegalArgumentException("El kart #" + numeroKart + " ya fue agregado a esta renta.");
            }
            System.out.print("Horas para el kart #" + numeroKart + " (máx. " + cliente.getMaxHoras() + "): ");
            int horas = leerEntero(scanner);
            seleccion.put(kart, horas);
        }

        Renta renta = gestor.crearRenta(cliente, seleccion);
        System.out.println("\nRenta creada exitosamente:\n");
        System.out.println(recibo.generar(renta));
    }

    /**
     * Solicita el número de una renta activa, la finaliza y muestra el recibo actualizado.
     *
     * @param scanner lector de entrada estándar
     * @param gestor  gestor de rentas
     * @param recibo  generador de recibos
     */
    private static void finalizarRenta(Scanner scanner, GestorRentas gestor, Recibo recibo) {
        System.out.print("Número de renta a finalizar: ");
        int numeroRenta = leerEntero(scanner);
        gestor.finalizarRenta(numeroRenta);

        System.out.println("Renta #" + numeroRenta + " finalizada y karts liberados.");
        for (Renta renta : gestor.listarRentas()) {
            if (renta.getNumeroRenta() == numeroRenta) {
                System.out.println();
                System.out.println(recibo.generar(renta));
                break;
            }
        }
    }

    /**
     * Muestra todas las rentas registradas en el historial.
     *
     * @param gestor gestor de rentas
     * @param recibo generador de recibos
     */
    private static void mostrarHistorialRentas(GestorRentas gestor, Recibo recibo) {
        List<Renta> rentas = gestor.listarRentas();
        System.out.println("\n--- Historial de rentas (Total: " + gestor.getTotalRentas() + ") ---");
        if (rentas.isEmpty()) {
            System.out.println("Aún no se han realizado rentas.");
            return;
        }
        for (Renta renta : rentas) {
            System.out.println();
            System.out.println(recibo.generar(renta));
        }
    }

    /**
     * Ejecuta una demostración autocontenida del flujo completo del sistema sin
     * alterar el estado del menú interactivo.
     *
     * @param recibo generador de recibos
     */
    private static void ejecutarDemostracionAutomatica(Recibo recibo) {
        System.out.println("\n==========================================");
        System.out.println("       DEMOSTRACIÓN AUTOMÁTICA");
        System.out.println("==========================================");

        FlotaKarts flotaDemo = new FlotaKarts();
        RegistroUsuarios registroDemo = new RegistroUsuarios();
        GestorRentas gestorDemo = new GestorRentas();

        KartRentable k1 = new KartInfantil(101);
        KartRentable k2 = new KartAdulto(102);
        KartRentable k3 = new KartDoble(103);
        flotaDemo.agregarKart(k1);
        flotaDemo.agregarKart(k2);
        flotaDemo.agregarKart(k3);

        Cliente socio = new Socio("S-DEMO", "Sofía Ramírez");
        Cliente visitante = new Visitante("V-DEMO", "Carlos Ruiz");
        registroDemo.registrar(socio);
        registroDemo.registrar(visitante);

        System.out.println("1) Flota inicial disponible: " + flotaDemo.listarDisponibles().size() + " karts.");

        Map<KartRentable, Integer> seleccionSocio = new LinkedHashMap<>();
        seleccionSocio.put(k1, 2);
        seleccionSocio.put(k2, 2);
        Renta rentaSocio = gestorDemo.crearRenta(socio, seleccionSocio);
        System.out.println("\n2) Renta de Socio creada:");
        System.out.println(recibo.generar(rentaSocio));
        System.out.println("\n   Karts disponibles tras rentar 2: " + flotaDemo.listarDisponibles().size());

        Map<KartRentable, Integer> seleccionVisitante = new LinkedHashMap<>();
        seleccionVisitante.put(k3, 1);
        Renta rentaVisitante = gestorDemo.crearRenta(visitante, seleccionVisitante);
        System.out.println("\n3) Renta de Visitante creada:");
        System.out.println(recibo.generar(rentaVisitante));
        System.out.println("\n   Karts disponibles ahora: " + flotaDemo.listarDisponibles().size());

        System.out.println("\n4) Prueba de validación (intentar rentar un kart ocupado):");
        try {
            Map<KartRentable, Integer> intentoInvalido = new LinkedHashMap<>();
            intentoInvalido.put(k1, 1);
            gestorDemo.crearRenta(visitante, intentoInvalido);
        } catch (IllegalStateException e) {
            System.out.println("   Bloqueado correctamente -> " + e.getMessage());
        }

        gestorDemo.finalizarRenta(rentaSocio.getNumeroRenta());
        System.out.println("\n5) Renta #" + rentaSocio.getNumeroRenta() + " finalizada:");
        System.out.println(recibo.generar(rentaSocio));
        System.out.println("\n   Karts disponibles tras liberar: " + flotaDemo.listarDisponibles().size());
        System.out.println("   Total histórico de rentas: " + gestorDemo.getTotalRentas());
        System.out.println("==========================================");
    }

    /**
     * Lee un número entero desde la entrada estándar validando el formato.
     *
     * @param scanner lector de entrada estándar
     * @return valor entero ingresado por el usuario
     * @throws IllegalArgumentException si el texto ingresado no es un entero válido
     */
    private static int leerEntero(Scanner scanner) {
        String entrada = scanner.nextLine().trim();
        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Debes ingresar un número entero válido.");
        }
    }
}
