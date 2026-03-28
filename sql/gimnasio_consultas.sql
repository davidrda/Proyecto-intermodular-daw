-- 1. Mostrar todos los clientes registrados en el gimnasio
    SELECT * 
    FROM clientes;

-- 2. Mostrar todos los servicios disponibles ordenados por precio de menor a mayor
    SELECT * 
    FROM servicios 
    ORDER BY precio ASC;

-- 3. Mostrar todas las reservas que están en estado 'confirmada'
    SELECT *
    FROM reservas
    WHERE estado = 'confirmada';

-- 4. Mostrar todas las clases de nivel 'Principiante'
    SELECT * 
    FROM clases 
    WHERE nivel = 'Principiante';

-- 5. Mostrar los clientes que se han dado de alta en enero del 2026
    SELECT *
    FROM clientes
    WHERE fecha_alta like '%2026-01%';

-- 6. Mostrar el nombre y precio de los servicios que cuestan más de 40€
    SELECT nombre, precio
    FROM servicios
    WHERE precio > 40;


-- 7. Mostrar todas las clases que se imparten los Lunes y Miércoles
    SELECT c.nombre, h.dia_semana
    FROM horarios h

    INNER JOIN clases c 
    ON c.id_clase = h.id_clase

    WHERE h.dia_semana IN ('Lunes', 'Miércoles');


-- 8. Mostrar el nombre de cada cliente junto al estado de su pago
    SELECT c.nombre, c.apellidos, pg.estado
    FROM clientes c 

    INNER JOIN pagos pg 
    ON c.id_cliente = pg.id_cliente;    



-- 9. Mostrar el nombre de cada cliente junto al servicio que ha contratado
    SELECT c.nombre AS cliente, s.nombre AS servicio
    FROM clientes c

    inner JOIN pagos pg
    ON c.id_cliente = pg.id_cliente

    INNER JOIN servicios s
    ON s.id_servicio = pg.id_servicio;


-- 10. Mostrar el nombre de cada clase junto al nombre de la sala donde se imparte
    SELECT c.nombre AS clase, s.nombre AS sala 
    FROM salas s 

    INNER JOIN clases c  
    ON s.id_sala = c.id_sala;


-- 11. Mostrar el nombre del cliente y la clase que ha reservado
    SELECT c.nombre AS nombre_cliente, cl.nombre AS clase
    FROM clientes c

    INNER JOIN reservas r
    ON c.id_cliente = r.id_cliente

    INNER JOIN horarios h
    ON h.id_horario = r.id_horario

    INNER JOIN clases cl
    ON cl.id_clase = h.id_clase;


-- 12. Contar cuántos clientes hay en total
    SELECT COUNT(*) AS total_clientes
    FROM clientes;

-- 13. Contar cuántas reservas hay de cada estado (confirmada, pendiente, cancelada)
    SELECT estado, COUNT(estado) AS total
    FROM reservas
    GROUP BY estado;

-- 14. Mostrar el importe total recaudado sumando todos los pagos en estado 'pagado'
    SELECT estado, COUNT(*) AS total
    FROM reservas
    GROUP BY estado;        

-- 15. Mostrar cuántas clases hay de cada disciplina según su nivel
    SELECT nombre, nivel, COUNT(*) AS total_clases
    FROM clases
    GROUP BY nombre, nivel;

-- 16. Mostrar las clases con mayor duración en minutos
    SELECT nombre, nivel, duracion_minutos
    FROM clases
    ORDER BY duracion_minutos DESC
    LIMIT 3;

-- 17. Mostrar cuántas reservas ha hecho cada cliente
    SELECT c.id_cliente, c.nombre, COUNT(*) reservas_confirmadas
    FROM clientes c 

    INNER JOIN reservas r
    ON c.id_cliente = r.id_cliente

    WHERE estado = 'confirmada'
    GROUP BY c.id_cliente, c.nombre;


-- 18. Mostrar los clientes que tienen pagos pendientes
    SELECT c.id_cliente, c.nombre, c.apellidos, c.dni, c.email, pg.estado AS estado_pago
    FROM clientes c 

    RIGHT JOIN pagos pg 
    ON c.id_cliente = pg.id_cliente

    WHERE pg.estado = 'pendiente';


-- 19. Mostrar el nombre del cliente, la clase reservada y el día de la semana en que es

    SELECT c.nombre AS cliente, cl.nombre AS clase_reservada, h.dia_semana AS dia
    FROM clientes c

    INNER JOIN reservas r 
    ON c.id_cliente = r.id_cliente

    INNER JOIN horarios h 
    ON h.id_horario = r.id_horario

    INNER JOIN clases cl
    ON cl.id_clase = h.id_clase

    ORDER BY h.dia_semana;