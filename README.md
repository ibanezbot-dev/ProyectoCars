# ProyectoCars

Sistema en Java para la gestión de renta de go karts, clientes (socios y visitantes), disponibilidad de flota y generación de recibos.

## Reglas de negocio principales

- **Tipos de go karts y tarifas por hora:**
  - **Infantil:** `$200.00 / h`
  - **Adulto:** `$300.00 / h`
  - **Doble:** `$400.00 / h`
- **Tipos de clientes:**
  - **Socio:** hasta **2 horas** por kart y **10%** de descuento.
  - **Visitante:** hasta **1 hora** por kart y **0%** de descuento.
- **Restricciones de renta:**
  - Entre **1 y 3 karts** por renta, sin números de kart repetidos.
  - Todos los karts deben encontrarse libres antes de confirmar la renta.
  - Al crear una renta los karts pasan a `"En uso"`; al finalizarla vuelven a `"Libre"`.

## Requisitos y tecnologías

- **Java:** JDK 25 (configurable en `pom.xml` mediante `<maven.compiler.release>` si se usa JDK 17 o 21).
- **Gestor de dependencias y compilación:** Apache Maven.
- **Pruebas unitarias:** JUnit `4.13.2` y Maven Surefire Plugin `3.5.2`.

## Estructura del proyecto

```text
ProyectoCars/
├── pom.xml
├── README.md
└── src/
    ├── main/java/
    │   ├── karts/
    │   ├── usuarios/
    │   └── rentas/
    └── test/java/
        ├── karts/
        ├── usuarios/
        └── rentas/
```

---

## Descripción de clases de producción (`src/main/java`)

### Paquete `karts`
Modela los distintos tipos de go karts, sus tarifas, estados de disponibilidad y la administración de la flota (`package-info.java`).

| Clase / Interfaz | Tipo | Descripción |
| --- | --- | --- |
| `KartRentable` | Interfaz | Contrato público para cualquier go kart que pueda consultarse y rentarse (`getNumero`, `getTipo`, `getTarifaPorHora`, `getEstado`, `estaLibre`, `ocupar`, `liberar`). |
| `Kart` | Clase abstracta | Implementa `KartRentable`. Centraliza el número identificador positivo y las transiciones de estado (`"Libre"` / `"En uso"`), lanzando `IllegalStateException` ante cambios de estado inválidos. |
| `KartInfantil` | Clase | Subclase de `Kart` que representa un go kart de tipo `"Infantil"` con tarifa fija de `$200.00` por hora. |
| `KartAdulto` | Clase | Subclase de `Kart` que representa un go kart de tipo `"Adulto"` con tarifa fija de `$300.00` por hora. |
| `KartDoble` | Clase | Subclase de `Kart` que representa un go kart de tipo `"Doble"` con tarifa fija de `$400.00` por hora. |
| `FlotaKarts` | Clase | Administra la colección de karts registrados (`agregarKart`, `buscarPorNumero`, `listarTodos`, `listarDisponibles`). Garantiza números únicos y devuelve copias defensivas de las listas. |

### Paquete `usuarios`
Modela a los clientes del establecimiento, sus beneficios y el registro/almacenamiento en memoria (`package-info.java`).

| Clase / Interfaz | Tipo | Descripción |
| --- | --- | --- |
| `Cliente` | Interfaz | Contrato público de los clientes que realizan rentas (`getId`, `getNombre`, `getMaxHoras`, `getPorcentajeDescuento`, `calcularDescuento`). |
| `Usuario` | Clase abstracta | Implementa `Cliente`. Valida y normaliza (`trim`) identificador y nombre no vacíos, e implementa `calcularDescuento(double)` de forma polimórfica validando subtotales no negativos. |
| `Socio` | Clase | Subclase de `Usuario` para clientes con membresía. Permite hasta `2` horas por kart y otorga `10%` (`0.10`) de descuento. |
| `Visitante` | Clase | Subclase de `Usuario` para clientes sin membresía. Permite hasta `1` hora por kart y aplica `0%` (`0.0`) de descuento. |
| `RepositorioClientes` | Interfaz *(paquete)* | Contrato interno de persistencia y consulta de clientes (`guardar`, `buscar`, `existe`, `obtenerTodos`) para desacoplar el almacenamiento y facilitar dobles de prueba. |
| `RepositorioClientesMemoria` | Clase | Implementación en memoria de `RepositorioClientes` (con constructor de acceso de paquete). Almacena clientes en un `ArrayList`, normaliza IDs al buscar y devuelve copias defensivas. |
| `RegistroUsuarios` | Clase | Gestiona el alta y consulta de clientes (`registrar`, `buscarPorId`, `listarClientes`). Valida datos no nulos/vacíos y evita duplicados. Usa `RepositorioClientesMemoria` por defecto o un `RepositorioClientes` inyectado en pruebas. |

### Paquete `rentas`
Gestiona la creación, cálculo de costos, finalización y comprobantes de las rentas (`package-info.java`).

| Clase | Tipo | Descripción |
| --- | --- | --- |
| `DetalleRenta` | Clase | Representa un kart específico y su cantidad de horas dentro de una renta. Su constructor es de acceso de paquete para garantizar la composición con `Renta` y calcula el subtotal individual (`tarifa * horas`). |
| `Renta` | Clase | Representa una renta activa o finalizada compuesta por uno o más `DetalleRenta` (constructor de paquete). Calcula subtotal, descuento (delegando a `Cliente`) y total, además de liberar todos sus karts al ejecutarse `finalizar()`. |
| `GestorRentas` | Clase | Orquesta el ciclo de vida de las rentas (`crearRenta`, `finalizarRenta`, `getTotalRentas`, `listarRentas`). Valida cliente, cantidad de karts (1 a 3), horas permitidas, unicidad de números y disponibilidad antes de ocupar karts o incrementar el contador histórico. |
| `Recibo` | Clase | Genera la representación textual formateada (`Locale.US`) del comprobante de una `Renta`, incluyendo número de renta, estado, cliente, desglose por kart, subtotal, descuento y total a pagar. |

---

## Descripción de clases de prueba (`src/test/java`)

La suite cuenta con **7 clases de prueba** y **41 pruebas unitarias** en JUnit 4:

| Clase de prueba | Paquete | Pruebas | Cobertura principal |
| --- | --- | ---: | --- |
| `KartTest` | `karts` | 6 | Tarifas y tipos de los tres karts, rechazo de números `<= 0`, ocupación/liberación válidas y rechazo de transiciones inválidas (`IllegalStateException`). |
| `FlotaKartsTest` | `karts` | 7 | Alta y búsqueda por número, rechazo de duplicados y parámetros inválidos, copias defensivas, filtrado de disponibles y polimorfismo mediante `KartRentableDoble`. |
| `UsuarioTest` | `usuarios` | 4 | Límites de horas y descuentos para `Socio` y `Visitante`, y rechazo de subtotales negativos e IDs/nombres vacíos. |
| `RegistroUsuariosTest` | `usuarios` | 6 | Alta, búsqueda, rechazo de duplicados, copias defensivas y validación de parámetros usando `RepositorioClientesDoble`, además de integración con `RepositorioClientesMemoria` por defecto. |
| `RepositorioClientesMemoriaTest` | `usuarios` | 4 | Guardado, búsqueda y verificación de existencia con normalización de espacios, manejo de IDs nulos/vacíos, rechazo de cliente nulo y copia defensiva en `obtenerTodos()`. |
| `GestorRentasTest` | `rentas` | 10 | Creación de rentas para socio, visitante, múltiples karts y `ClienteDoble`; rechazo de karts ocupados y parámetros inválidos; finalización válida, inválida o repetida; y copia defensiva del historial. |
| `ReciboTest` | `rentas` | 4 | Formato de recibos para socio (10% de descuento), visitante (0% de descuento), rentas finalizadas y rechazo de renta nula. |

---

## Comandos útiles

### Ejecutar todas las pruebas
```bash
mvn clean test
```

### Generar documentación Javadoc
```bash
javadoc -d target/site/apidocs -sourcepath src/main/java -subpackages karts:rentas:usuarios -encoding UTF-8 -charset UTF-8 -Xdoclint:all
```
La documentación generada se guarda en `target/site/apidocs/index.html`.
