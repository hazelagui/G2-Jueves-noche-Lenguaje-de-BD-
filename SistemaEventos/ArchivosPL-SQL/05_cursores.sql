-- ================================================================
-- SISTEMA DE GESTION DE EVENTOS Y CONCIERTOS
-- Script 5: Cursores (integrados en procedimientos)
-- Al menos 15 cursores
-- ================================================================

-- Cursor 1: Listar todos los artistas
CREATE OR REPLACE PROCEDURE sp_listar_artistas(
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_artista, nombre, genero_musical, pais_origen,
               fecha_creacion, creado_por
        FROM ARTISTA
        ORDER BY nombre;
END;
/

-- Cursor 2: Listar todos los eventos
CREATE OR REPLACE PROCEDURE sp_listar_eventos(
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_evento, nombre, fecha_evento, id_locacion, id_artista,
               fecha_creacion, creado_por
        FROM EVENTO
        ORDER BY fecha_evento DESC;
END;
/

-- Cursor 3: Listar asistentes con sus compras
CREATE OR REPLACE PROCEDURE sp_listar_asistentes_compras(
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT a.id_asistente, a.nombre, a.correo, a.telefono,
               COUNT(c.id_compra) AS total_compras
        FROM ASISTENTE a
        LEFT JOIN COMPRA c ON a.id_asistente = c.id_asistente
        GROUP BY a.id_asistente, a.nombre, a.correo, a.telefono
        ORDER BY total_compras DESC;
END;
/

-- Cursor 4: Obtener entradas por evento
CREATE OR REPLACE PROCEDURE sp_listar_entradas_evento(
    p_id_evento IN NUMBER,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_entrada, precio, tipo_entrada
        FROM ENTRADA
        WHERE id_evento = p_id_evento
        ORDER BY precio;
END;
/

-- Cursor 5: Listar eventos próximos
CREATE OR REPLACE PROCEDURE sp_listar_eventos_proximos(
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT e.id_evento, e.nombre, e.fecha_evento,
               a.nombre AS artista, l.nombre AS locacion
        FROM EVENTO e
        INNER JOIN ARTISTA a ON e.id_artista = a.id_artista
        INNER JOIN LOCACION l ON e.id_locacion = l.id_locacion
        WHERE e.fecha_evento > SYSDATE
        ORDER BY e.fecha_evento;
END;
/

-- Cursor 6: Obtener compras de un asistente
CREATE OR REPLACE PROCEDURE sp_compras_por_asistente(
    p_id_asistente IN NUMBER,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT c.id_compra, c.fecha_compra, e.precio, e.tipo_entrada,
               ev.nombre AS evento, p.monto, p.metodo_pago
        FROM COMPRA c
        INNER JOIN ENTRADA e ON c.id_entrada = e.id_entrada
        INNER JOIN EVENTO ev ON e.id_evento = ev.id_evento
        LEFT JOIN PAGO p ON c.id_compra = p.id_compra
        WHERE c.id_asistente = p_id_asistente
        ORDER BY c.fecha_compra DESC;
END;
/

-- Cursor 7: Listar patrocinadores de un evento
CREATE OR REPLACE PROCEDURE sp_patrocinadores_evento(
    p_id_evento IN NUMBER,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT p.id_patrocinador, p.nombre, p.tipo_patrocinio
        FROM PATROCINADOR p
        INNER JOIN EVENTO_PATROCINADOR ep ON p.id_patrocinador = ep.id_patrocinador
        WHERE ep.id_evento = p_id_evento;
END;
/

-- Cursor 8: Listar empleados por departamento
CREATE OR REPLACE PROCEDURE sp_empleados_por_departamento(
    p_id_departamento IN NUMBER,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_empleado, nombre, puesto, salario
        FROM EMPLEADO
        WHERE id_departamento = p_id_departamento
        ORDER BY nombre;
END;
/

-- Cursor 9: Listar locaciones disponibles
CREATE OR REPLACE PROCEDURE sp_listar_locaciones(
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_locacion, nombre, direccion, capacidad
        FROM LOCACION
        ORDER BY capacidad DESC;
END;
/

-- Cursor 10: Listar pagos por método de pago
CREATE OR REPLACE PROCEDURE sp_pagos_por_metodo(
    p_metodo_pago IN VARCHAR2,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT p.id_pago, p.monto, p.fecha_pago,
               a.nombre AS asistente
        FROM PAGO p
        INNER JOIN COMPRA c ON p.id_compra = c.id_compra
        INNER JOIN ASISTENTE a ON c.id_asistente = a.id_asistente
        WHERE p.metodo_pago = p_metodo_pago
        ORDER BY p.fecha_pago DESC;
END;
/

-- Cursor 11: Obtener inventario por locación
CREATE OR REPLACE PROCEDURE sp_inventario_por_locacion(
    p_id_locacion IN NUMBER,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_inventario, nombre_item, cantidad
        FROM INVENTARIO
        WHERE id_locacion = p_id_locacion
        ORDER BY nombre_item;
END;
/

-- Cursor 12: Listar tickets virtuales por evento
CREATE OR REPLACE PROCEDURE sp_tickets_por_evento(
    p_id_evento IN NUMBER,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT tv.id_ticket_virtual, tv.codigo_qr,
               a.nombre AS asistente, a.correo
        FROM TICKET_VIRTUAL tv
        INNER JOIN ASISTENTE a ON tv.id_asistente = a.id_asistente
        WHERE tv.id_evento = p_id_evento;
END;
/

-- Cursor 13: Listar proveedores con servicios
CREATE OR REPLACE PROCEDURE sp_proveedores_servicios(
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT p.id_proveedor, p.nombre, p.telefono,
               s.descripcion, s.costo
        FROM PROVEEDOR p
        LEFT JOIN SERVICIO s ON p.id_proveedor = s.id_proveedor
        ORDER BY p.nombre;
END;
/

-- Cursor 14: Obtener eventos por artista
CREATE OR REPLACE PROCEDURE sp_eventos_por_artista(
    p_id_artista IN NUMBER,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT e.id_evento, e.nombre, e.fecha_evento,
               l.nombre AS locacion, l.capacidad
        FROM EVENTO e
        INNER JOIN LOCACION l ON e.id_locacion = l.id_locacion
        WHERE e.id_artista = p_id_artista
        ORDER BY e.fecha_evento DESC;
END;
/

-- Cursor 15: Listar staff técnico por evento
CREATE OR REPLACE PROCEDURE sp_staff_por_evento(
    p_id_evento IN NUMBER,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_staff, nombre, rol
        FROM STAFF_TECNICO
        WHERE id_evento = p_id_evento
        ORDER BY rol, nombre;
END;
/

-- Cursor 16: Reporte de ventas diarias
CREATE OR REPLACE PROCEDURE sp_ventas_diarias(
    p_fecha IN DATE,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT p.id_pago, p.monto, p.metodo_pago,
               a.nombre AS asistente,
               ev.nombre AS evento
        FROM PAGO p
        INNER JOIN COMPRA c ON p.id_compra = c.id_compra
        INNER JOIN ASISTENTE a ON c.id_asistente = a.id_asistente
        INNER JOIN ENTRADA ent ON c.id_entrada = ent.id_entrada
        INNER JOIN EVENTO ev ON ent.id_evento = ev.id_evento
        WHERE TRUNC(p.fecha_pago) = TRUNC(p_fecha)
        ORDER BY p.fecha_pago;
END;
/

-- Cursor 17: Listar departamentos con estadísticas
CREATE OR REPLACE PROCEDURE sp_departamentos_estadisticas(
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT d.id_departamento, d.nombre, d.ubicacion,
               COUNT(e.id_empleado) AS total_empleados,
               NVL(AVG(e.salario), 0) AS salario_promedio
        FROM DEPARTAMENTO d
        LEFT JOIN EMPLEADO e ON d.id_departamento = e.id_departamento
        GROUP BY d.id_departamento, d.nombre, d.ubicacion
        ORDER BY total_empleados DESC;
END;
/

-- Cursor 18: Buscar asistente por nombre o correo
CREATE OR REPLACE PROCEDURE sp_buscar_asistente(
    p_criterio IN VARCHAR2,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_asistente, nombre, correo, telefono
        FROM ASISTENTE
        WHERE UPPER(nombre) LIKE '%' || UPPER(p_criterio) || '%'
           OR UPPER(correo) LIKE '%' || UPPER(p_criterio) || '%'
        ORDER BY nombre;
END;
/

COMMIT;
