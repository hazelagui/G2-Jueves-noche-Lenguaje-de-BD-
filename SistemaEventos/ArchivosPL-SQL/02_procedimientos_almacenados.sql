-- ================================================================
-- SISTEMA DE GESTION DE EVENTOS Y CONCIERTOS
-- Script 2: Procedimientos Almacenados (CRUD)
-- Al menos 25 procedimientos almacenados
-- ================================================================

-- ==================== ARTISTA ====================

-- Procedimiento 1: Insertar Artista
CREATE OR REPLACE PROCEDURE sp_insertar_artista(
    p_nombre IN VARCHAR2,
    p_genero_musical IN VARCHAR2,
    p_pais_origen IN VARCHAR2,
    p_id_artista OUT NUMBER
) AS
BEGIN
    SELECT seq_artista.NEXTVAL INTO p_id_artista FROM DUAL;
    
    INSERT INTO ARTISTA (id_artista, nombre, genero_musical, pais_origen)
    VALUES (p_id_artista, p_nombre, p_genero_musical, p_pais_origen);
    
END;
/

-- Procedimiento 2: Actualizar Artista
CREATE OR REPLACE PROCEDURE sp_actualizar_artista(
    p_id_artista IN NUMBER,
    p_nombre IN VARCHAR2,
    p_genero_musical IN VARCHAR2,
    p_pais_origen IN VARCHAR2
) AS
BEGIN
    UPDATE ARTISTA
    SET nombre = p_nombre,
        genero_musical = p_genero_musical,
        pais_origen = p_pais_origen,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_artista = p_id_artista;
    
END;
/

-- Procedimiento 3: Eliminar Artista
CREATE OR REPLACE PROCEDURE sp_eliminar_artista(
    p_id_artista IN NUMBER
) AS
BEGIN
    DELETE FROM ARTISTA WHERE id_artista = p_id_artista;
END;
/

-- ==================== LOCACION ====================

-- Procedimiento 4: Insertar Locación
CREATE OR REPLACE PROCEDURE sp_insertar_locacion(
    p_nombre IN VARCHAR2,
    p_direccion IN VARCHAR2,
    p_capacidad IN NUMBER,
    p_id_locacion OUT NUMBER
) AS
BEGIN
    SELECT seq_locacion.NEXTVAL INTO p_id_locacion FROM DUAL;
    
    INSERT INTO LOCACION (id_locacion, nombre, direccion, capacidad)
    VALUES (p_id_locacion, p_nombre, p_direccion, p_capacidad);
    
END;
/

-- Procedimiento 5: Actualizar Locación
CREATE OR REPLACE PROCEDURE sp_actualizar_locacion(
    p_id_locacion IN NUMBER,
    p_nombre IN VARCHAR2,
    p_direccion IN VARCHAR2,
    p_capacidad IN NUMBER
) AS
BEGIN
    UPDATE LOCACION
    SET nombre = p_nombre,
        direccion = p_direccion,
        capacidad = p_capacidad,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_locacion = p_id_locacion;
    
END;
/

-- Procedimiento 6: Eliminar Locación
CREATE OR REPLACE PROCEDURE sp_eliminar_locacion(
    p_id_locacion IN NUMBER
) AS
BEGIN
    DELETE FROM LOCACION WHERE id_locacion = p_id_locacion;
END;
/

-- ==================== EVENTO ====================

-- Procedimiento 7: Insertar Evento
CREATE OR REPLACE PROCEDURE sp_insertar_evento(
    p_nombre IN VARCHAR2,
    p_fecha_evento IN DATE,
    p_id_locacion IN NUMBER,
    p_id_artista IN NUMBER,
    p_id_evento OUT NUMBER
) AS
BEGIN
    SELECT seq_evento.NEXTVAL INTO p_id_evento FROM DUAL;
    
    INSERT INTO EVENTO (id_evento, nombre, fecha_evento, id_locacion, id_artista)
    VALUES (p_id_evento, p_nombre, p_fecha_evento, p_id_locacion, p_id_artista);
    
END;
/

-- Procedimiento 8: Actualizar Evento
CREATE OR REPLACE PROCEDURE sp_actualizar_evento(
    p_id_evento IN NUMBER,
    p_nombre IN VARCHAR2,
    p_fecha_evento IN DATE,
    p_id_locacion IN NUMBER,
    p_id_artista IN NUMBER
) AS
BEGIN
    UPDATE EVENTO
    SET nombre = p_nombre,
        fecha_evento = p_fecha_evento,
        id_locacion = p_id_locacion,
        id_artista = p_id_artista,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_evento = p_id_evento;
    
END;
/

-- Procedimiento 9: Eliminar Evento
CREATE OR REPLACE PROCEDURE sp_eliminar_evento(
    p_id_evento IN NUMBER
) AS
BEGIN
    DELETE FROM EVENTO WHERE id_evento = p_id_evento;
END;
/

-- ==================== ASISTENTE ====================

-- Procedimiento 10: Insertar Asistente
CREATE OR REPLACE PROCEDURE sp_insertar_asistente(
    p_nombre IN VARCHAR2,
    p_correo IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_id_asistente OUT NUMBER
) AS
BEGIN
    SELECT seq_asistente.NEXTVAL INTO p_id_asistente FROM DUAL;
    
    INSERT INTO ASISTENTE (id_asistente, nombre, correo, telefono)
    VALUES (p_id_asistente, p_nombre, p_correo, p_telefono);
    
END;
/

-- Procedimiento 11: Actualizar Asistente
CREATE OR REPLACE PROCEDURE sp_actualizar_asistente(
    p_id_asistente IN NUMBER,
    p_nombre IN VARCHAR2,
    p_correo IN VARCHAR2,
    p_telefono IN VARCHAR2
) AS
BEGIN
    UPDATE ASISTENTE
    SET nombre = p_nombre,
        correo = p_correo,
        telefono = p_telefono,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_asistente = p_id_asistente;
    
END;
/

-- Procedimiento 12: Eliminar Asistente
CREATE OR REPLACE PROCEDURE sp_eliminar_asistente(
    p_id_asistente IN NUMBER
) AS
BEGIN
    DELETE FROM ASISTENTE WHERE id_asistente = p_id_asistente;
END;
/

-- ==================== ENTRADA ====================

-- Procedimiento 13: Insertar Entrada
CREATE OR REPLACE PROCEDURE sp_insertar_entrada(
    p_precio IN NUMBER,
    p_tipo_entrada IN VARCHAR2,
    p_id_evento IN NUMBER,
    p_id_entrada OUT NUMBER
) AS
BEGIN
    SELECT seq_entrada.NEXTVAL INTO p_id_entrada FROM DUAL;
    
    INSERT INTO ENTRADA (id_entrada, precio, tipo_entrada, id_evento)
    VALUES (p_id_entrada, p_precio, p_tipo_entrada, p_id_evento);
    
END;
/

-- Procedimiento 14: Actualizar Entrada
CREATE OR REPLACE PROCEDURE sp_actualizar_entrada(
    p_id_entrada IN NUMBER,
    p_precio IN NUMBER,
    p_tipo_entrada IN VARCHAR2,
    p_id_evento IN NUMBER
) AS
BEGIN
    UPDATE ENTRADA
    SET precio = p_precio,
        tipo_entrada = p_tipo_entrada,
        id_evento = p_id_evento,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_entrada = p_id_entrada;
    
END;
/

-- Procedimiento 15: Eliminar Entrada
CREATE OR REPLACE PROCEDURE sp_eliminar_entrada(
    p_id_entrada IN NUMBER
) AS
BEGIN
    DELETE FROM ENTRADA WHERE id_entrada = p_id_entrada;
END;
/

-- ==================== COMPRA ====================

-- Procedimiento 16: Insertar Compra
CREATE OR REPLACE PROCEDURE sp_insertar_compra(
    p_id_asistente IN NUMBER,
    p_id_entrada IN NUMBER,
    p_fecha_compra IN DATE,
    p_id_compra OUT NUMBER
) AS
BEGIN
    SELECT seq_compra.NEXTVAL INTO p_id_compra FROM DUAL;
    
    INSERT INTO COMPRA (id_compra, id_asistente, id_entrada, fecha_compra)
    VALUES (p_id_compra, p_id_asistente, p_id_entrada, p_fecha_compra);
    
END;
/

-- Procedimiento 17: Actualizar Compra
CREATE OR REPLACE PROCEDURE sp_actualizar_compra(
    p_id_compra IN NUMBER,
    p_id_asistente IN NUMBER,
    p_id_entrada IN NUMBER,
    p_fecha_compra IN DATE
) AS
BEGIN
    UPDATE COMPRA
    SET id_asistente = p_id_asistente,
        id_entrada = p_id_entrada,
        fecha_compra = p_fecha_compra,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_compra = p_id_compra;
    
END;
/

-- Procedimiento 18: Eliminar Compra
CREATE OR REPLACE PROCEDURE sp_eliminar_compra(
    p_id_compra IN NUMBER
) AS
BEGIN
    DELETE FROM COMPRA WHERE id_compra = p_id_compra;
END;
/

-- ==================== PAGO ====================

-- Procedimiento 19: Insertar Pago
CREATE OR REPLACE PROCEDURE sp_insertar_pago(
    p_id_compra IN NUMBER,
    p_metodo_pago IN VARCHAR2,
    p_monto IN NUMBER,
    p_fecha_pago IN DATE,
    p_id_pago OUT NUMBER
) AS
BEGIN
    SELECT seq_pago.NEXTVAL INTO p_id_pago FROM DUAL;
    
    INSERT INTO PAGO (id_pago, id_compra, metodo_pago, monto, fecha_pago)
    VALUES (p_id_pago, p_id_compra, p_metodo_pago, p_monto, p_fecha_pago);
    
END;
/

-- Procedimiento 20: Actualizar Pago
CREATE OR REPLACE PROCEDURE sp_actualizar_pago(
    p_id_pago IN NUMBER,
    p_id_compra IN NUMBER,
    p_metodo_pago IN VARCHAR2,
    p_monto IN NUMBER,
    p_fecha_pago IN DATE
) AS
BEGIN
    UPDATE PAGO
    SET id_compra = p_id_compra,
        metodo_pago = p_metodo_pago,
        monto = p_monto,
        fecha_pago = p_fecha_pago,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_pago = p_id_pago;
    
END;
/

-- Procedimiento 21: Eliminar Pago
CREATE OR REPLACE PROCEDURE sp_eliminar_pago(
    p_id_pago IN NUMBER
) AS
BEGIN
    DELETE FROM PAGO WHERE id_pago = p_id_pago;
END;
/

-- ==================== PATROCINADOR ====================

-- Procedimiento 22: Insertar Patrocinador
CREATE OR REPLACE PROCEDURE sp_insertar_patrocinador(
    p_nombre IN VARCHAR2,
    p_tipo_patrocinio IN VARCHAR2,
    p_id_patrocinador OUT NUMBER
) AS
BEGIN
    SELECT seq_patrocinador.NEXTVAL INTO p_id_patrocinador FROM DUAL;
    
    INSERT INTO PATROCINADOR (id_patrocinador, nombre, tipo_patrocinio)
    VALUES (p_id_patrocinador, p_nombre, p_tipo_patrocinio);
    
END;
/

-- Procedimiento 23: Actualizar Patrocinador
CREATE OR REPLACE PROCEDURE sp_actualizar_patrocinador(
    p_id_patrocinador IN NUMBER,
    p_nombre IN VARCHAR2,
    p_tipo_patrocinio IN VARCHAR2
) AS
BEGIN
    UPDATE PATROCINADOR
    SET nombre = p_nombre,
        tipo_patrocinio = p_tipo_patrocinio,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_patrocinador = p_id_patrocinador;
    
END;
/

-- Procedimiento 24: Eliminar Patrocinador
CREATE OR REPLACE PROCEDURE sp_eliminar_patrocinador(
    p_id_patrocinador IN NUMBER
) AS
BEGIN
    DELETE FROM PATROCINADOR WHERE id_patrocinador = p_id_patrocinador;
END;
/

-- Procedimiento 25: Asociar Patrocinador a Evento
CREATE OR REPLACE PROCEDURE sp_asociar_patrocinador_evento(
    p_id_evento IN NUMBER,
    p_id_patrocinador IN NUMBER
) AS
BEGIN
    INSERT INTO EVENTO_PATROCINADOR (id_evento, id_patrocinador)
    VALUES (p_id_evento, p_id_patrocinador);
    
END;
/

-- ==================== EMPLEADO ====================

-- Procedimiento 26: Insertar Empleado
CREATE OR REPLACE PROCEDURE sp_insertar_empleado(
    p_nombre IN VARCHAR2,
    p_puesto IN VARCHAR2,
    p_salario IN NUMBER,
    p_id_departamento IN NUMBER,
    p_id_empleado OUT NUMBER
) AS
BEGIN
    SELECT seq_empleado.NEXTVAL INTO p_id_empleado FROM DUAL;
    
    INSERT INTO EMPLEADO (id_empleado, nombre, puesto, salario, id_departamento)
    VALUES (p_id_empleado, p_nombre, p_puesto, p_salario, p_id_departamento);
    
END;
/

-- Procedimiento 27: Actualizar Empleado
CREATE OR REPLACE PROCEDURE sp_actualizar_empleado(
    p_id_empleado IN NUMBER,
    p_nombre IN VARCHAR2,
    p_puesto IN VARCHAR2,
    p_salario IN NUMBER,
    p_id_departamento IN NUMBER
) AS
BEGIN
    UPDATE EMPLEADO
    SET nombre = p_nombre,
        puesto = p_puesto,
        salario = p_salario,
        id_departamento = p_id_departamento,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_empleado = p_id_empleado;
    
END;
/

-- Procedimiento 28: Eliminar Empleado
CREATE OR REPLACE PROCEDURE sp_eliminar_empleado(
    p_id_empleado IN NUMBER
) AS
BEGIN
    DELETE FROM EMPLEADO WHERE id_empleado = p_id_empleado;
END;
/

-- ==================== DEPARTAMENTO ====================

-- Procedimiento 29: Insertar Departamento
CREATE OR REPLACE PROCEDURE sp_insertar_departamento(
    p_nombre IN VARCHAR2,
    p_ubicacion IN VARCHAR2,
    p_id_departamento OUT NUMBER
) AS
BEGIN
    SELECT seq_departamento.NEXTVAL INTO p_id_departamento FROM DUAL;
    
    INSERT INTO DEPARTAMENTO (id_departamento, nombre, ubicacion)
    VALUES (p_id_departamento, p_nombre, p_ubicacion);
    
END;
/

-- Procedimiento 30: Actualizar Departamento
CREATE OR REPLACE PROCEDURE sp_actualizar_departamento(
    p_id_departamento IN NUMBER,
    p_nombre IN VARCHAR2,
    p_ubicacion IN VARCHAR2
) AS
BEGIN
    UPDATE DEPARTAMENTO
    SET nombre = p_nombre,
        ubicacion = p_ubicacion,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_departamento = p_id_departamento;
    
    
END;
/

-- Procedimiento 31: Eliminar Departamento
CREATE OR REPLACE PROCEDURE sp_eliminar_departamento(
    p_id_departamento IN NUMBER
) AS
BEGIN
    DELETE FROM DEPARTAMENTO WHERE id_departamento = p_id_departamento;
    
END;
/

-- ==================== PROVEEDOR ====================

-- Procedimiento 32: Insertar Proveedor
CREATE OR REPLACE PROCEDURE sp_insertar_proveedor(
    p_nombre IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_id_proveedor OUT NUMBER
) AS
BEGIN
    SELECT seq_proveedor.NEXTVAL INTO p_id_proveedor FROM DUAL;
    
    INSERT INTO PROVEEDOR (id_proveedor, nombre, telefono)
    VALUES (p_id_proveedor, p_nombre, p_telefono);
    
    
END;
/

-- Procedimiento 33: Actualizar Proveedor
CREATE OR REPLACE PROCEDURE sp_actualizar_proveedor(
    p_id_proveedor IN NUMBER,
    p_nombre IN VARCHAR2,
    p_telefono IN VARCHAR2
) AS
BEGIN
    UPDATE PROVEEDOR
    SET nombre = p_nombre,
        telefono = p_telefono,
        fecha_modificacion = SYSDATE,
        modificado_por = USER
    WHERE id_proveedor = p_id_proveedor;
    
    
END;
/

-- Procedimiento 34: Eliminar Proveedor
CREATE OR REPLACE PROCEDURE sp_eliminar_proveedor(
    p_id_proveedor IN NUMBER
) AS
BEGIN
    DELETE FROM PROVEEDOR WHERE id_proveedor = p_id_proveedor;
    
END;
/

-- ==================== TICKET_VIRTUAL ====================

-- Procedimiento 35: Insertar Ticket Virtual
CREATE OR REPLACE PROCEDURE sp_insertar_ticket_virtual(
    p_id_evento IN NUMBER,
    p_id_asistente IN NUMBER,
    p_codigo_qr IN VARCHAR2,
    p_id_ticket OUT NUMBER
) AS
BEGIN
    SELECT seq_ticket.NEXTVAL INTO p_id_ticket FROM DUAL;
    
    INSERT INTO TICKET_VIRTUAL (id_ticket_virtual, id_evento, id_asistente, codigo_qr)
    VALUES (p_id_ticket, p_id_evento, p_id_asistente, p_codigo_qr);
    
    
END;
/



-- ==================== AUDITORIA ====================

-- Procedimiento 36: Insertar registro de auditoría
CREATE OR REPLACE PROCEDURE sp_insertar_auditoria(
    p_tabla_afectada IN VARCHAR2,
    p_operacion IN VARCHAR2,
    p_id_registro IN NUMBER,
    p_valores_anteriores IN VARCHAR2,
    p_valores_nuevos IN VARCHAR2,
    p_descripcion IN VARCHAR2,
    p_id_auditoria OUT NUMBER
) AS
BEGIN
    SELECT seq_auditoria.NEXTVAL INTO p_id_auditoria FROM DUAL;
    
    INSERT INTO AUDITORIA (
        id_auditoria, 
        tabla_afectada, 
        operacion, 
        id_registro,
        valores_anteriores,
        valores_nuevos,
        descripcion
    )
    VALUES (
        p_id_auditoria, 
        p_tabla_afectada, 
        p_operacion, 
        p_id_registro,
        p_valores_anteriores,
        p_valores_nuevos,
        p_descripcion
    );
    
    
END;
/

-- Procedimiento 37: Listar auditorías por tabla
CREATE OR REPLACE PROCEDURE sp_listar_auditoria_por_tabla(
    p_tabla IN VARCHAR2,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_auditoria, tabla_afectada, operacion, id_registro,
               usuario, fecha_operacion, descripcion
        FROM AUDITORIA
        WHERE tabla_afectada = p_tabla
        ORDER BY fecha_operacion DESC;
END;
/

-- Procedimiento 38: Listar auditorías por fecha
CREATE OR REPLACE PROCEDURE sp_listar_auditoria_por_fecha(
    p_fecha_inicio IN DATE,
    p_fecha_fin IN DATE,
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_auditoria, tabla_afectada, operacion, id_registro,
               usuario, fecha_operacion, descripcion
        FROM AUDITORIA
        WHERE TRUNC(fecha_operacion) BETWEEN TRUNC(p_fecha_inicio) AND TRUNC(p_fecha_fin)
        ORDER BY fecha_operacion DESC;
END;
/

-- Procedimiento 39: Listar todas las auditorías
CREATE OR REPLACE PROCEDURE sp_listar_auditorias(
    p_resultado OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_resultado FOR
        SELECT id_auditoria, tabla_afectada, operacion, id_registro,
               usuario, fecha_operacion, valores_anteriores, valores_nuevos, descripcion
        FROM AUDITORIA
        ORDER BY fecha_operacion DESC;
END;
/
