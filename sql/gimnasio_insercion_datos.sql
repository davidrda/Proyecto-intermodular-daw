-- Inserción de datos

INSERT INTO servicios(nombre, precio, fecha_alta) VALUES
    ('Musculación', 39.99, '2026-01-07'),
    ('Lucha',       49.99, '2026-02-16'),
    ('Full Access', 79.99, '2026-03-24');
INSERT INTO clientes(nombre, apellidos, dni, email, telefono, fecha_alta) VALUES
    ('Carlos',  'Martínez López',   '12345678A', 'carlos.martinez@gmail.com',  '611000001', '2026-01-10'),
    ('Laura',   'Sánchez Pérez',    '23456789B', 'laura.sanchez@hotmail.com',  '622000002', '2026-01-24'),
    ('Miguel',  'García Fernández', '34567890C', 'miguel.garcia@gmail.com',    '633000003', '2026-03-01'),
    ('Sofía',   'Ruiz Gómez',       '45678901D', 'sofia.ruiz@yahoo.es',        '644000004', '2026-02-25'),
    ('Andrés',  'Torres Navarro',   '56789012E', 'andres.torres@gmail.com',    '655000005', '2026-03-16');

INSERT INTO entrenadores(nombre, apellidos, dni, email, telefono, especialidad, fecha_contratacion) VALUES
    ('Javier',  'Moreno Blanco',  '78901234G', 'javier.moreno@ragnarok.com', '677000007', 'Musculación', '2022-09-01'),
    ('Patricia','Vega Domínguez', '89012345H', 'patricia.vega@ragnarok.com', '688000008', 'Lucha',        '2022-09-01');
INSERT INTO salas(nombre, tipo, capacidad) VALUES
    ('Sala The Forge', 'Musculación', 70),
    ('Sala Colisseum', 'Lucha',       30),
    ('Sala Valhalla',  'Cardio',      15);

INSERT INTO pagos(id_servicio, id_cliente, metodo_pago, fecha_pago, importe, estado) VALUES
    (1, 1, 'tarjeta',  '2026-01-10', 39.99, 'pagado'),
    (2, 2, 'efectivo', '2026-01-24', 49.99, 'pagado'),
    (3, 3, 'tarjeta',  '2026-03-01', 79.99, 'pagado'),
    (1, 4, 'tarjeta',  '2026-02-25', 39.99, 'pagado'),
    (2, 5, 'tarjeta',  '2026-03-16', 49.99, 'pendiente');

INSERT INTO clases(id_entrenador, id_sala, nombre, nivel, duracion_minutos) VALUES
    (2, 2, 'MMA',                  'Principiante', 60),
    (2, 2, 'MMA',                  'Avanzado',     90),
    (2, 2, 'Boxeo',                'Principiante', 60),
    (2, 2, 'Boxeo',                'Avanzado',     90),
    (2, 2, 'Brazilian Jiu Jitsu',  'Principiante', 60),
    (2, 2, 'Brazilian Jiu Jitsu',  'Avanzado',     90);  

INSERT INTO horarios(id_clase, dia_semana, hora_inicio, hora_fin) VALUES
    (1, 'Lunes',     '18:00:00', '19:00:00'),
    (2, 'Lunes',     '19:00:00', '20:30:00'),
    (3, 'Miércoles', '18:00:00', '19:00:00'),
    (4, 'Miércoles', '19:00:00', '20:30:00'),
    (5, 'Viernes',   '18:00:00', '19:00:00'),
    (6, 'Viernes',   '19:00:00', '20:30:00');

INSERT INTO reservas(id_cliente, id_horario, fecha_reserva, estado) VALUES
    (1, 1, '2026-03-16', 'confirmada'), 
    (3, 1, '2026-03-16', 'confirmada'),  
    (5, 2, '2026-03-16', 'confirmada'),  
    (2, 3, '2026-03-18', 'confirmada'),  
    (4, 3, '2026-03-18', 'pendiente'),   
    (1, 4, '2026-03-18', 'confirmada'),  
    (3, 5, '2026-03-20', 'confirmada'),  
    (2, 6, '2026-03-20', 'cancelada');

