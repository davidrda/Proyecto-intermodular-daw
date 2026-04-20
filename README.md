# Proyecto Intermodular DAW — Ragnarok Gym Center

Este repositorio contiene el desarrollo del Proyecto Intermodular de 1º del ciclo formativo de Desarrollo de Aplicaciones Web (DAW).

El proyecto consiste en diseñar y desarrollar un sistema completo para **Ragnarok Gym Center**, un gimnasio ficticio, cubriendo desde la maquetación del portal web hasta la aplicación de gestión en Java conectada a una base de datos relacional, así como la documentación del entorno de ejecución.

## Fases del proyecto

El desarrollo se estructura en varias fases que replican el flujo de trabajo real de un desarrollador web:

- **Fase web** — Portal corporativo del gimnasio en HTML y CSS. Diseño con maquetación semántica, Flexbox, Grid y variables CSS. Ubicado en `/web`.
- **Fase base de datos** — Modelo relacional con MariaDB. Gestión de clientes, entrenadores, clases, horarios, reservas, salas, servicios y pagos. Scripts SQL de creación, inserción y consultas en `/sql`.
- **Fase Java** — Aplicación de consola con arquitectura en capas (view, service, dao, model) que se conecta a la base de datos vía JDBC. Ubicada en `/sources/RagnarokGym`. Más información en el [README del proyecto Java](sources/RagnarokGym/README.md).
- **Fase sistemas** — Informe técnico de entorno de ejecución, con guía de instalación, usuarios y permisos, mantenimiento y evidencias. Ubicado en `/docs/sistemas`.

## Estructura del repositorio

```
/web          → portal web (HTML/CSS)
/sql          → scripts de base de datos (MariaDB)
/sources      → código fuente de la aplicación Java
/diagrams     → diagramas E-R y modelo relacional
/docs         → documentación técnica del proyecto
```

## Tecnologías utilizadas

- **Frontend**: HTML5, CSS3
- **Base de datos**: MariaDB, DBeaver
- **Backend**: Java 25, JDBC, Lombok, Maven
- **Sistemas**: Ubuntu Server, VirtualBox
- **Control de versiones**: Git, GitHub

## Autor

David Ramírez de Arellano Aparisi — 1º DAW
