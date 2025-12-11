-- ================================================================
-- SISTEMA DE GESTION DE EVENTOS Y CONCIERTOS
-- Script 3: Funciones
-- Al menos 15 funciones
-- ================================================================

-- Función 1: Obtener nombre de artista por ID
CREATE OR REPLACE FUNCTION fn_obtener_nombre_artista(
    p_id_artista IN NUMBER
) RETURN VARCHAR2 AS
    v_nombre VARCHAR2(100);
BEGIN
    SELECT nombre INTO v_nombre
    FROM ARTISTA
    WHERE id_artista = p_id_artista;
    
    RETURN v_nombre;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'Artista no encontrado';
END;
/

-- Función 2: Calcular total de ventas por evento
CREATE OR REPLACE FUNCTION fn_total_ventas_evento(
    p_id_evento IN NUMBER
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT NVL(SUM(p.monto), 0) INTO v_total
    FROM PAGO p
    INNER JOIN COMPRA c ON p.id_compra = c.id_compra
    INNER JOIN ENTRADA e ON c.id_entrada = e.id_entrada
    WHERE e.id_evento = p_id_evento;
    
    RETURN v_total;
END;
/

-- Función 3: Contar asistentes por evento
CREATE OR REPLACE FUNCTION fn_contar_asistentes_evento(
    p_id_evento IN NUMBER
) RETURN NUMBER AS
    v_contador NUMBER := 0;
BEGIN
    SELECT COUNT(DISTINCT c.id_asistente) INTO v_contador
    FROM COMPRA c
    INNER JOIN ENTRADA e ON c.id_entrada = e.id_entrada
    WHERE e.id_evento = p_id_evento;
    
    RETURN v_contador;
END;
/

-- Función 4: Verificar disponibilidad de locación
CREATE OR REPLACE FUNCTION fn_locacion_disponible(
    p_id_locacion IN NUMBER,
    p_fecha DATE
) RETURN VARCHAR2 AS
    v_eventos NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_eventos
    FROM EVENTO
    WHERE id_locacion = p_id_locacion
    AND TRUNC(fecha_evento) = TRUNC(p_fecha);
    
    IF v_eventos = 0 THEN
        RETURN 'DISPONIBLE';
    ELSE
        RETURN 'NO DISPONIBLE';
    END IF;
END;
/

-- Función 5: Calcular ingresos totales por artista
CREATE OR REPLACE FUNCTION fn_ingresos_por_artista(
    p_id_artista IN NUMBER
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT NVL(SUM(p.monto), 0) INTO v_total
    FROM PAGO p
    INNER JOIN COMPRA c ON p.id_compra = c.id_compra
    INNER JOIN ENTRADA ent ON c.id_entrada = ent.id_entrada
    INNER JOIN EVENTO ev ON ent.id_evento = ev.id_evento
    WHERE ev.id_artista = p_id_artista;
    
    RETURN v_total;
END;
/

-- Función 6: Obtener capacidad de locación
CREATE OR REPLACE FUNCTION fn_obtener_capacidad_locacion(
    p_id_locacion IN NUMBER
) RETURN NUMBER AS
    v_capacidad NUMBER;
BEGIN
    SELECT capacidad INTO v_capacidad
    FROM LOCACION
    WHERE id_locacion = p_id_locacion;
    
    RETURN v_capacidad;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
END;
/

-- Función 7: Calcular promedio de precios de entradas por evento
CREATE OR REPLACE FUNCTION fn_promedio_precio_entrada(
    p_id_evento IN NUMBER
) RETURN NUMBER AS
    v_promedio NUMBER := 0;
BEGIN
    SELECT NVL(AVG(precio), 0) INTO v_promedio
    FROM ENTRADA
    WHERE id_evento = p_id_evento;
    
    RETURN v_promedio;
END;
/

-- Función 8: Verificar si asistente ya compró entrada para evento
CREATE OR REPLACE FUNCTION fn_asistente_ya_compro(
    p_id_asistente IN NUMBER,
    p_id_evento IN NUMBER
) RETURN VARCHAR2 AS
    v_compras NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_compras
    FROM COMPRA c
    INNER JOIN ENTRADA e ON c.id_entrada = e.id_entrada
    WHERE c.id_asistente = p_id_asistente
    AND e.id_evento = p_id_evento;
    
    IF v_compras > 0 THEN
        RETURN 'SI';
    ELSE
        RETURN 'NO';
    END IF;
END;
/

-- Función 9: Contar eventos por artista
CREATE OR REPLACE FUNCTION fn_contar_eventos_artista(
    p_id_artista IN NUMBER
) RETURN NUMBER AS
    v_contador NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_contador
    FROM EVENTO
    WHERE id_artista = p_id_artista;
    
    RETURN v_contador;
END;
/

-- Función 10: Obtener nombre de evento por ID
CREATE OR REPLACE FUNCTION fn_obtener_nombre_evento(
    p_id_evento IN NUMBER
) RETURN VARCHAR2 AS
    v_nombre VARCHAR2(100);
BEGIN
    SELECT nombre INTO v_nombre
    FROM EVENTO
    WHERE id_evento = p_id_evento;
    
    RETURN v_nombre;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'Evento no encontrado';
END;
/

-- Función 11: Calcular total de empleados por departamento
CREATE OR REPLACE FUNCTION fn_total_empleados_depto(
    p_id_departamento IN NUMBER
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_total
    FROM EMPLEADO
    WHERE id_departamento = p_id_departamento;
    
    RETURN v_total;
END;
/

-- Función 12: Calcular presupuesto total de marketing por evento
CREATE OR REPLACE FUNCTION fn_presupuesto_marketing_evento(
    p_id_evento IN NUMBER
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT NVL(SUM(presupuesto), 0) INTO v_total
    FROM MARKETING
    WHERE id_evento = p_id_evento;
    
    RETURN v_total;
END;
/

-- Función 13: Obtener correo de asistente
CREATE OR REPLACE FUNCTION fn_obtener_correo_asistente(
    p_id_asistente IN NUMBER
) RETURN VARCHAR2 AS
    v_correo VARCHAR2(100);
BEGIN
    SELECT correo INTO v_correo
    FROM ASISTENTE
    WHERE id_asistente = p_id_asistente;
    
    RETURN v_correo;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
END;
/

-- Función 14: Calcular edad del registro (días desde creación)
CREATE OR REPLACE FUNCTION fn_dias_desde_creacion_evento(
    p_id_evento IN NUMBER
) RETURN NUMBER AS
    v_dias NUMBER := 0;
BEGIN
    SELECT TRUNC(SYSDATE - fecha_creacion) INTO v_dias
    FROM EVENTO
    WHERE id_evento = p_id_evento;
    
    RETURN v_dias;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN -1;
END;
/

-- Función 15: Verificar si evento tiene patrocinadores
CREATE OR REPLACE FUNCTION fn_evento_tiene_patrocinadores(
    p_id_evento IN NUMBER
) RETURN VARCHAR2 AS
    v_count NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM EVENTO_PATROCINADOR
    WHERE id_evento = p_id_evento;
    
    IF v_count > 0 THEN
        RETURN 'SI';
    ELSE
        RETURN 'NO';
    END IF;
END;
/

-- Función 16: Calcular salario promedio por departamento
CREATE OR REPLACE FUNCTION fn_salario_promedio_depto(
    p_id_departamento IN NUMBER
) RETURN NUMBER AS
    v_promedio NUMBER := 0;
BEGIN
    SELECT NVL(AVG(salario), 0) INTO v_promedio
    FROM EMPLEADO
    WHERE id_departamento = p_id_departamento;
    
    RETURN ROUND(v_promedio, 2);
END;
/

-- Función 17: Contar proveedores activos
CREATE OR REPLACE FUNCTION fn_contar_proveedores_activos
RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_total
    FROM PROVEEDOR;
    
    RETURN v_total;
END;
/

-- Función 18: Generar código QR único
CREATE OR REPLACE FUNCTION fn_generar_codigo_qr(
    p_id_evento IN NUMBER,
    p_id_asistente IN NUMBER
) RETURN VARCHAR2 AS
    v_codigo VARCHAR2(100);
BEGIN
    v_codigo := 'QR-' || p_id_evento || '-' || p_id_asistente || '-' || 
                TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS');
    RETURN v_codigo;
END;
/

COMMIT;

-- Función 19: Contar registros de auditoría por tabla
CREATE OR REPLACE FUNCTION fn_contar_auditorias_tabla(
    p_tabla IN VARCHAR2
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_total
    FROM AUDITORIA
    WHERE tabla_afectada = p_tabla;
    
    RETURN v_total;
END;
/

-- Función 20: Obtener última operación de una tabla
CREATE OR REPLACE FUNCTION fn_ultima_operacion_tabla(
    p_tabla IN VARCHAR2
) RETURN VARCHAR2 AS
    v_operacion VARCHAR2(100);
BEGIN
    SELECT operacion || ' - ' || TO_CHAR(fecha_operacion, 'DD/MM/YYYY HH24:MI:SS')
    INTO v_operacion
    FROM (
        SELECT operacion, fecha_operacion
        FROM AUDITORIA
        WHERE tabla_afectada = p_tabla
        ORDER BY fecha_operacion DESC
    )
    WHERE ROWNUM = 1;
    
    RETURN v_operacion;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'Sin operaciones';
END;
/

-- Función 21: Contar operaciones por usuario
CREATE OR REPLACE FUNCTION fn_contar_operaciones_usuario(
    p_usuario IN VARCHAR2
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_total
    FROM AUDITORIA
    WHERE usuario = p_usuario;
    
    RETURN v_total;
END;
/
