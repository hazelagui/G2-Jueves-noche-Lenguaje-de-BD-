-- ================================================================
-- SISTEMA DE GESTION DE EVENTOS Y CONCIERTOS
-- Script 4: Vistas
-- Al menos 10 vistas
-- ================================================================

-- Vista 1: Información completa de eventos con artistas y locaciones
CREATE OR REPLACE VIEW vista_eventos_completos AS
SELECT 
    e.id_evento,
    e.nombre AS nombre_evento,
    e.fecha_evento,
    a.nombre AS nombre_artista,
    a.genero_musical,
    l.nombre AS nombre_locacion,
    l.direccion,
    l.capacidad,
    fn_total_ventas_evento(e.id_evento) AS total_ventas,
    fn_contar_asistentes_evento(e.id_evento) AS total_asistentes
FROM EVENTO e
LEFT JOIN ARTISTA a ON e.id_artista = a.id_artista
LEFT JOIN LOCACION l ON e.id_locacion = l.id_locacion;

-- Vista 2: Reporte de ventas por evento
CREATE OR REPLACE VIEW vista_ventas_por_evento AS
SELECT 
    e.id_evento,
    e.nombre AS nombre_evento,
    COUNT(c.id_compra) AS total_compras,
    SUM(p.monto) AS ingresos_totales,
    AVG(p.monto) AS promedio_venta
FROM EVENTO e
LEFT JOIN ENTRADA ent ON e.id_evento = ent.id_evento
LEFT JOIN COMPRA c ON ent.id_entrada = c.id_entrada
LEFT JOIN PAGO p ON c.id_compra = p.id_compra
GROUP BY e.id_evento, e.nombre;

-- Vista 3: Asistentes con sus compras
CREATE OR REPLACE VIEW vista_asistentes_compras AS
SELECT 
    a.id_asistente,
    a.nombre,
    a.correo,
    a.telefono,
    COUNT(c.id_compra) AS total_compras,
    SUM(p.monto) AS total_gastado
FROM ASISTENTE a
LEFT JOIN COMPRA c ON a.id_asistente = c.id_asistente
LEFT JOIN PAGO p ON c.id_compra = p.id_compra
GROUP BY a.id_asistente, a.nombre, a.correo, a.telefono;

-- Vista 4: Eventos próximos (futuros)
CREATE OR REPLACE VIEW vista_eventos_proximos AS
SELECT 
    e.id_evento,
    e.nombre AS nombre_evento,
    e.fecha_evento,
    a.nombre AS artista,
    l.nombre AS locacion,
    l.capacidad
FROM EVENTO e
INNER JOIN ARTISTA a ON e.id_artista = a.id_artista
INNER JOIN LOCACION l ON e.id_locacion = l.id_locacion
WHERE e.fecha_evento > SYSDATE
ORDER BY e.fecha_evento;

-- Vista 5: Artistas con mayor número de eventos
CREATE OR REPLACE VIEW vista_artistas_mas_eventos AS
SELECT 
    a.id_artista,
    a.nombre,
    a.genero_musical,
    a.pais_origen,
    COUNT(e.id_evento) AS total_eventos,
    fn_ingresos_por_artista(a.id_artista) AS ingresos_totales
FROM ARTISTA a
LEFT JOIN EVENTO e ON a.id_artista = e.id_artista
GROUP BY a.id_artista, a.nombre, a.genero_musical, a.pais_origen
ORDER BY total_eventos DESC;

-- Vista 6: Departamentos con empleados
CREATE OR REPLACE VIEW vista_departamentos_empleados AS
SELECT 
    d.id_departamento,
    d.nombre AS nombre_departamento,
    d.ubicacion,
    COUNT(em.id_empleado) AS total_empleados,
    AVG(em.salario) AS salario_promedio,
    SUM(em.salario) AS costo_total_salarios
FROM DEPARTAMENTO d
LEFT JOIN EMPLEADO em ON d.id_departamento = em.id_departamento
GROUP BY d.id_departamento, d.nombre, d.ubicacion;

-- Vista 7: Patrocinadores por evento
CREATE OR REPLACE VIEW vista_patrocinadores_eventos AS
SELECT 
    e.id_evento,
    e.nombre AS nombre_evento,
    p.id_patrocinador,
    p.nombre AS nombre_patrocinador,
    p.tipo_patrocinio
FROM EVENTO e
INNER JOIN EVENTO_PATROCINADOR ep ON e.id_evento = ep.id_evento
INNER JOIN PATROCINADOR p ON ep.id_patrocinador = p.id_patrocinador;

-- Vista 8: Inventario por locación
CREATE OR REPLACE VIEW vista_inventario_locaciones AS
SELECT 
    l.id_locacion,
    l.nombre AS nombre_locacion,
    i.id_inventario,
    i.nombre_item,
    i.cantidad
FROM LOCACION l
LEFT JOIN INVENTARIO i ON l.id_locacion = i.id_locacion;

-- Vista 9: Resumen de pagos
CREATE OR REPLACE VIEW vista_resumen_pagos AS
SELECT 
    p.id_pago,
    p.metodo_pago,
    p.monto,
    p.fecha_pago,
    a.nombre AS nombre_asistente,
    e.nombre AS nombre_evento
FROM PAGO p
INNER JOIN COMPRA c ON p.id_compra = c.id_compra
INNER JOIN ASISTENTE a ON c.id_asistente = a.id_asistente
INNER JOIN ENTRADA ent ON c.id_entrada = ent.id_entrada
INNER JOIN EVENTO e ON ent.id_evento = e.id_evento;

-- Vista 10: Estadísticas generales del sistema
CREATE OR REPLACE VIEW vista_estadisticas_generales AS
SELECT 
    (SELECT COUNT(*) FROM EVENTO) AS total_eventos,
    (SELECT COUNT(*) FROM ARTISTA) AS total_artistas,
    (SELECT COUNT(*) FROM ASISTENTE) AS total_asistentes,
    (SELECT COUNT(*) FROM COMPRA) AS total_compras,
    (SELECT NVL(SUM(monto), 0) FROM PAGO) AS ingresos_totales,
    (SELECT COUNT(*) FROM LOCACION) AS total_locaciones,
    (SELECT COUNT(*) FROM PATROCINADOR) AS total_patrocinadores
FROM DUAL;

-- Vista 11: Tickets virtuales activos
CREATE OR REPLACE VIEW vista_tickets_virtuales AS
SELECT 
    tv.id_ticket_virtual,
    tv.codigo_qr,
    e.nombre AS nombre_evento,
    e.fecha_evento,
    a.nombre AS nombre_asistente,
    a.correo
FROM TICKET_VIRTUAL tv
INNER JOIN EVENTO e ON tv.id_evento = e.id_evento
INNER JOIN ASISTENTE a ON tv.id_asistente = a.id_asistente;

-- Vista 12: Proveedores con contratos activos
CREATE OR REPLACE VIEW vista_proveedores_contratos AS
SELECT 
    p.id_proveedor,
    p.nombre AS nombre_proveedor,
    p.telefono,
    COUNT(c.id_contrato) AS total_contratos,
    s.descripcion AS servicio,
    s.costo
FROM PROVEEDOR p
LEFT JOIN CONTRATO c ON p.id_proveedor = c.id_proveedor
LEFT JOIN SERVICIO s ON p.id_proveedor = s.id_proveedor
GROUP BY p.id_proveedor, p.nombre, p.telefono, s.descripcion, s.costo;

-- Vista 13: Personal técnico por evento
CREATE OR REPLACE VIEW vista_staff_por_evento AS
SELECT 
    e.id_evento,
    e.nombre AS nombre_evento,
    st.nombre AS nombre_staff,
    st.rol,
    bs.nombre AS banda_soporte
FROM EVENTO e
LEFT JOIN STAFF_TECNICO st ON e.id_evento = st.id_evento
LEFT JOIN BANDA_SOPORTE bs ON e.id_evento = bs.id_evento;

-- Vista 14: Tipos de entrada por evento con disponibilidad
CREATE OR REPLACE VIEW vista_entradas_disponibles AS
SELECT 
    e.id_evento,
    e.nombre AS nombre_evento,
    ent.id_entrada,
    ent.tipo_entrada,
    ent.precio,
    COUNT(c.id_compra) AS entradas_vendidas
FROM EVENTO e
INNER JOIN ENTRADA ent ON e.id_evento = ent.id_evento
LEFT JOIN COMPRA c ON ent.id_entrada = c.id_entrada
GROUP BY e.id_evento, e.nombre, ent.id_entrada, ent.tipo_entrada, ent.precio;

COMMIT;

-- Vista 15: Resumen de auditorías por tabla
CREATE OR REPLACE VIEW vista_auditoria_por_tabla AS
SELECT 
    tabla_afectada,
    COUNT(*) AS total_operaciones,
    SUM(CASE WHEN operacion = 'INSERT' THEN 1 ELSE 0 END) AS total_inserts,
    SUM(CASE WHEN operacion = 'UPDATE' THEN 1 ELSE 0 END) AS total_updates,
    SUM(CASE WHEN operacion = 'DELETE' THEN 1 ELSE 0 END) AS total_deletes,
    MAX(fecha_operacion) AS ultima_operacion
FROM AUDITORIA
GROUP BY tabla_afectada
ORDER BY total_operaciones DESC;

-- Vista 16: Auditoría de operaciones recientes (últimas 24 horas)
CREATE OR REPLACE VIEW vista_auditoria_reciente AS
SELECT 
    id_auditoria,
    tabla_afectada,
    operacion,
    id_registro,
    usuario,
    fecha_operacion,
    descripcion
FROM AUDITORIA
WHERE fecha_operacion >= SYSDATE - 1
ORDER BY fecha_operacion DESC;

-- Vista 17: Actividad de usuarios en el sistema
CREATE OR REPLACE VIEW vista_actividad_usuarios AS
SELECT 
    usuario,
    COUNT(*) AS total_operaciones,
    COUNT(DISTINCT tabla_afectada) AS tablas_modificadas,
    MIN(fecha_operacion) AS primera_operacion,
    MAX(fecha_operacion) AS ultima_operacion
FROM AUDITORIA
GROUP BY usuario
ORDER BY total_operaciones DESC;

-- Vista 18: Log completo de auditoría con detalles
CREATE OR REPLACE VIEW vista_log_auditoria_completo AS
SELECT 
    a.id_auditoria,
    a.tabla_afectada,
    a.operacion,
    a.id_registro,
    a.usuario,
    a.fecha_operacion,
    a.valores_anteriores,
    a.valores_nuevos,
    a.descripcion,
    CASE 
        WHEN a.operacion = 'INSERT' THEN 'Nuevo registro'
        WHEN a.operacion = 'UPDATE' THEN 'Modificación'
        WHEN a.operacion = 'DELETE' THEN 'Eliminación'
    END AS tipo_operacion
FROM AUDITORIA a
ORDER BY a.fecha_operacion DESC;
