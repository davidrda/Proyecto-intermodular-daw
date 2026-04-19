# Ragnarok Gym — Aplicación Java

Aplicación de consola para la gestión de un gimnasio ficticio (Ragnarok Gym Center). Permite administrar clientes, entrenadores, clases, reservas y pagos mediante un sistema de menús que se conecta a una base de datos MariaDB a través de JDBC.

Este subproyecto forma parte del Proyecto Intermodular de 1º DAW y es evaluado tanto en **Programación** (funcionalidad y CRUD) como en **Ampliación de Programación / MPO** (calidad del código y mejoras estructurales).

## Arquitectura

La aplicación sigue una **arquitectura en capas** que separa claramente las responsabilidades:

```
view  →  service  →  dao  →  base de datos
```

- **view** — Menús interactivos que recogen los datos del usuario por consola. No contienen lógica de negocio.
- **service** — Lógica de negocio y validaciones. Es el intermediario entre los menús y los DAOs.
- **dao** — Acceso a datos. Ejecuta las consultas SQL mediante `PreparedStatement` y mapea los resultados a objetos del modelo.
- **model** — POJOs que representan las entidades del dominio (Cliente, Entrenador, Clase, Reserva, Pago).
- **database** — Gestión de la conexión (`DBConnection` con patrón Singleton) y constantes del esquema (`SchemDB`).
- **util** — Clases utilitarias reutilizables (`Validador`).

### Flujo típico

Cuando el usuario inserta un cliente, el flujo es:

1. `MenuClientes` recoge los datos por consola y crea un objeto `Cliente`.
2. `ClienteService` valida los campos (no vacíos, sin números en nombre, etc.).
3. `ClienteDAO` ejecuta el INSERT en la base de datos.
4. Si algo falla en cualquier capa, la excepción sube hasta el menú y se muestra un mensaje claro al usuario.

## Estructura de paquetes

```
src/main/java/
├── dao/          → ClienteDAO, EntrenadorDAO, ClaseDAO, ReservaDAO, PagoDAO
├── database/     → DBConnection, SchemDB
├── model/        → Cliente, Entrenador, Clase, Reserva, Pago
├── service/      → ClienteService, EntrenadorService, ClaseService, ReservaService, PagoService
├── util/         → Validador
├── view/         → MenuBase, MenuPrincipal, MenuClientes, MenuEntrenadores, MenuClases, MenuReservas, MenuPagos
└── Main.java
```

## Mejoras estructurales (MPO)

Respecto a una implementación básica en la que cada menú y cada service replicarían su propio código, este proyecto incorpora **dos refactorizaciones** que aplican principios POO y eliminan duplicación:

### 1. Clase utilitaria `Validador`

En lugar de tener los mismos métodos de validación (`validarNoVacio`, `validarSinNumeros`, `validarSinLetras`) copiados en cada service, se ha extraído una clase `Validador` con métodos `public static` en el paquete `util`. Cualquier service llama a las validaciones con `Validador.validarNoVacio(...)` sin necesidad de instanciar la clase.

**Principios aplicados:** DRY (Don't Repeat Yourself), separación de responsabilidades, métodos estáticos.

### 2. Clase abstracta `MenuBase`

Todos los menús compartían el mismo esqueleto: un bucle `do-while`, un `Scanner`, un `switch` con cases comunes. Se ha creado una clase abstracta `MenuBase` que contiene toda esa estructura común y declara como abstractos los métodos específicos (`insertar`, `listarTodos`, `buscarPorId`, `actualizar`, `eliminar`, `getTextoMenu`). Cada menú hijo extiende `MenuBase` e implementa solo lo propio de su entidad.

Para los menús con opciones extras (`MenuClientes` con "dar de baja" y `MenuReservas` con "listar detalladas"), se sobreescribe el método `ejecutarOpcion()` para ampliar el switch con los cases adicionales.

**Principios aplicados:** herencia, abstracción, polimorfismo.

## Tecnologías

- **Java 25** — con Maven como gestor de dependencias
- **JDBC** — conexión a la base de datos con `PreparedStatement` y `try-with-resources`
- **MariaDB** — sistema gestor de base de datos
- **Lombok** — anotaciones `@Getter` y `@Setter` para reducir código boilerplate en los modelos

## Instalación y ejecución

### Requisitos previos

- Java 25 o superior
- Maven
- MariaDB (o MySQL) corriendo en `localhost:3306`

### Pasos

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/davidrda/Proyecto-intermodular-daw.git
   cd Proyecto-intermodular-daw
   ```

2. **Crear la base de datos y cargar los datos**

   Desde MariaDB/MySQL, ejecutar los scripts SQL en este orden:
   ```sql
   SOURCE sql/gimnasio_creacion_tablas.sql;
   SOURCE sql/gimnasio_insercion_datos.sql;
   ```

3. **Configurar la conexión**

   Revisar las credenciales en `sources/RagnarokGym/src/main/java/database/DBConnection.java` y ajustarlas si es necesario. Por defecto:
   - Usuario: `root`
   - Contraseña: *(vacía)*
   - Base de datos: `gimnasio_ragnarok`

4. **Abrir y ejecutar el proyecto**

   Abrir `sources/RagnarokGym` como proyecto Maven en IntelliJ IDEA. Maven descargará las dependencias automáticamente. Ejecutar `Main.java`.

## Autor

David Ramírez de Arellano Aparisi — 1º DAW