# Informe Técnico de Entorno de Ejecución — Ragnarok Gym

**Módulo**: Sistemas Informáticos
**Proyecto**: Ragnarok Gym Center
**Autor**: David Ramírez de Arellano Aparisi
**Curso**: 1º DAW

## 1. Qué hace la aplicación

Ragnarok Gym es una aplicación para gestionar un gimnasio. Sirve para llevar el control de los clientes, los entrenadores, las clases, las reservas y los pagos. Está hecha en Java y guarda todos los datos en una base de datos MariaDB.

Solo la usa el personal del gimnasio, nadie más. No se puede abrir desde fuera, ni desde casa, ni desde un móvil. Es una herramienta interna.

## 2. Dónde se va a ejecutar

Lo más sencillo sería instalarlo todo en un ordenador y ya está, pero hay un problema: si la recepcionista mete un cliente, el dueño no podría verlo desde su ordenador porque estaría guardado solo en el de ella.

Por eso la aplicación se ejecuta así:

- Hay un **servidor** en las oficinas del gimnasio. Ahí se guarda toda la información.
- Cada trabajador tiene la aplicación instalada en **su propio ordenador**. Cuando la usa, la aplicación se conecta al servidor para pedir o guardar datos.

De esta forma, todos ven la misma información actualizada.

El dibujo del sistema está en el archivo `esquema_sistema.svg` dentro de la carpeta `capturas/`.

## 3. Qué ordenadores hacen falta

**El servidor** tiene que tener al menos:
- 4 GB de RAM (mejor 8)
- 100 GB de disco
- Un procesador normalito, no hace falta que sea potente

**Los ordenadores del personal** pueden ser más básicos:
- 2 GB de RAM
- 20 GB de disco libre
- Cualquier procesador de los últimos años sirve

La aplicación es muy ligera, así que no hacen falta ordenadores caros.

## 4. Qué sistema operativo usar

**En el servidor**: Ubuntu Server. Es gratis, seguro y consume pocos recursos.

**En los ordenadores del personal**: Da igual, Windows o Ubuntu. La aplicación está hecha en Java y funciona en los dos.

## 5. Cómo se instala

### Instalar el servidor

**1. Instalar Ubuntu Server en el ordenador que va a ser el servidor**

Se descarga desde la web de Ubuntu y se instala siguiendo los pasos. Durante la instalación hay que ponerle una IP fija (por ejemplo `192.168.1.15`) para que siempre se pueda encontrar en la misma dirección.

**2. Instalar MariaDB**

```bash
sudo apt update
sudo apt install mariadb-server -y
```

**3. Ponerle una contraseña al usuario root de MariaDB**

```bash
sudo mariadb-secure-installation
```

El asistente va haciendo preguntas. Decir que sí a casi todo (poner contraseña, eliminar usuarios anónimos, eliminar base de datos de prueba, recargar permisos). Solo se dice "no" cuando pregunta si usar autenticación por unix_socket.

**4. Permitir que se conecten desde otros ordenadores**

Por defecto, MariaDB solo deja conectarse desde el propio servidor. Hay que cambiar eso.

Abrir el archivo de configuración:

```bash
sudo nano /etc/mysql/mariadb.conf.d/50-server.cnf
```

Buscar la línea que pone `bind-address = 127.0.0.1` y cambiarla por `bind-address = 0.0.0.0`.

Guardar, salir, y reiniciar MariaDB:

```bash
sudo systemctl restart mariadb
```

**5. Crear la base de datos**

Entrar a MariaDB:

```bash
sudo mariadb -u root -p
```

Y crear la base de datos:

```sql
CREATE DATABASE gimnasio_ragnarok;
```

Luego cargar los archivos SQL del proyecto:

```bash
sudo mariadb -u root -p gimnasio_ragnarok < gimnasio_creacion_tablas.sql
sudo mariadb -u root -p gimnasio_ragnarok < gimnasio_insercion_datos.sql
```

**6. Crear los usuarios**

Ver la sección 7.

### Instalar la app en los ordenadores del personal

**1. Instalar Java**

Se descarga de la web de Oracle y se instala.

**2. Copiar el archivo de la aplicación**

Copiar `RagnarokGym.jar` al ordenador, en cualquier carpeta.

**3. Configurar la conexión**

Dentro del código de la aplicación, en el archivo `DBConnection.java`, hay que poner:
- La IP del servidor.
- El usuario y la contraseña que le toque a ese puesto.

## 6. Cómo se ejecuta

Abrir una terminal en el ordenador donde está instalada y escribir:

```bash
java -jar RagnarokGym.jar
```

Se abre el menú principal y ya se puede usar.

## 7. Usuarios y permisos

No todo el personal puede hacer lo mismo. La recepcionista no debería poder eliminar cosas, y un entrenador no debería poder cambiar pagos. Para controlar esto se crean tres usuarios distintos en MariaDB, cada uno con permisos diferentes.

### Usuario admin (el dueño)

Puede hacer todo.

```sql
CREATE USER 'admin'@'192.168.1.%' IDENTIFIED BY 'contraseña_admin';
GRANT ALL PRIVILEGES ON gimnasio_ragnarok.* TO 'admin'@'192.168.1.%';
```

### Usuario recepción

Puede ver, añadir y modificar clientes, reservas y pagos. Puede dar de baja a clientes. **No puede eliminar** nada.

```sql
CREATE USER 'recepcion'@'192.168.1.%' IDENTIFIED BY 'contraseña_recepcion';
GRANT SELECT, INSERT, UPDATE ON gimnasio_ragnarok.clientes TO 'recepcion'@'192.168.1.%';
GRANT SELECT, INSERT, UPDATE ON gimnasio_ragnarok.reservas TO 'recepcion'@'192.168.1.%';
GRANT SELECT, INSERT, UPDATE ON gimnasio_ragnarok.pagos TO 'recepcion'@'192.168.1.%';
GRANT SELECT ON gimnasio_ragnarok.clases TO 'recepcion'@'192.168.1.%';
GRANT SELECT ON gimnasio_ragnarok.entrenadores TO 'recepcion'@'192.168.1.%';
GRANT SELECT ON gimnasio_ragnarok.horarios TO 'recepcion'@'192.168.1.%';
GRANT SELECT ON gimnasio_ragnarok.salas TO 'recepcion'@'192.168.1.%';
GRANT SELECT ON gimnasio_ragnarok.servicios TO 'recepcion'@'192.168.1.%';
```

### Usuario entrenador

Solo puede consultar, no puede tocar nada.

```sql
CREATE USER 'entrenador'@'192.168.1.%' IDENTIFIED BY 'contraseña_entrenador';
GRANT SELECT ON gimnasio_ragnarok.* TO 'entrenador'@'192.168.1.%';
```

Al terminar, guardar los cambios:

```sql
FLUSH PRIVILEGES;
```

## 8. Mantenimiento

### Copias de seguridad

Es importante hacer copias de la base de datos por si pasa algo. He creado un script que hace una copia automáticamente:

```bash
#!/bin/bash
FECHA=$(date +%Y-%m-%d_%H-%M)
mkdir -p /home/user/backups
mariadb-dump -u root -p'contraseña' gimnasio_ragnarok > /home/user/backups/gimnasio_$FECHA.sql
echo "Backup creado: gimnasio_$FECHA.sql"
```

Este script se puede programar para que se ejecute todos los días de madrugada, cuando el gimnasio está cerrado.

### Actualizaciones

Una vez al mes se revisan las actualizaciones del sistema:

```bash
sudo apt update
sudo apt upgrade
```

### Revisar que todo funciona

De vez en cuando comprobar que MariaDB sigue activo:

```bash
sudo systemctl status mariadb
```

Y que queda espacio en el disco:

```bash
df -h
```

## 9. Seguridad

- El servidor solo acepta conexiones desde la red del gimnasio. No se puede entrar desde internet.
- Cada persona usa su propio usuario con permisos limitados. Si alguien se equivoca, no puede romper más de lo que le toca.
- Las contraseñas son largas y no fáciles de adivinar.
- Se hacen copias de seguridad todos los días.

## 10. Evidencias

Las capturas de que el sistema funciona están en la carpeta `capturas/`:

- **01_mariadb_activo.png**: MariaDB funcionando en el servidor Ubuntu.
- **02_app_conectada_servidor.png**: la aplicación conectándose al servidor desde un ordenador cliente.
- **03a_insercion_mac.png**: un cliente insertado desde la aplicación.
- **03b_verificacion_ubuntu.png**: ese mismo cliente aparece en el servidor.
- **04_backup_ejecutado.png**: el script de copias de seguridad funcionando.

---

**Fin del informe.**