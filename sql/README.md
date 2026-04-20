# Ragnarok Gym — Base de Datos

Base de datos relacional del proyecto Ragnarok Gym Center. Forma parte del Proyecto Intermodular de 1º DAW y se corresponde con el módulo de **Bases de Datos**.

La base de datos se llama `gimnasio_ragnarok` y está diseñada para guardar toda la información que maneja la aplicación Java: clientes, entrenadores, clases, horarios, reservas, salas, servicios y pagos.

## Tablas

El modelo tiene ocho tablas:

- **clientes** — Personas apuntadas al gimnasio.
- **entrenadores** — Personal que imparte las clases.
- **clases** — Actividades que se ofrecen (yoga, musculación, etc.).
- **horarios** — Días y horas en que se imparte cada clase.
- **salas** — Espacios físicos del gimnasio.
- **reservas** — Inscripciones de clientes a clases concretas.
- **servicios** — Planes o abonos que el gimnasio ofrece.
- **pagos** — Pagos realizados por los clientes.

Las relaciones entre tablas se modelan mediante claves foráneas. El diagrama entidad-relación y el modelo relacional se encuentran en la carpeta `/diagrams` del repositorio.

## Archivos

```
/sql
├── gimnasio_creacion_tablas.sql     → crea la BD y todas las tablas
├── gimnasio_insercion_datos.sql     → inserta datos de prueba
└── gimnasio_consultas.sql           → consultas de ejemplo
```

## Cómo usar los scripts

**1. Crear la base de datos y las tablas**

Desde MariaDB o MySQL:

```bash
mariadb -u root -p < gimnasio_creacion_tablas.sql
```

**2. Insertar los datos de prueba**

```bash
mariadb -u root -p gimnasio_ragnarok < gimnasio_insercion_datos.sql
```

**3. Ejecutar las consultas**

El archivo `gimnasio_consultas.sql` contiene 19 consultas de ejemplo, desde SELECTs básicos hasta JOINs entre varias tablas. Se pueden ejecutar una a una desde cualquier cliente SQL (DBeaver, MySQL Workbench, la terminal...).

## Tecnologías

- **MariaDB** como sistema gestor de base de datos.
- **DBeaver** para la administración y visualización.

## Autor

David Ramírez de Arellano Aparisi — 1º DAW