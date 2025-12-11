-- ================================================================
-- SISTEMA DE GESTION DE EVENTOS Y CONCIERTOS
-- Script 7: Triggers de Auditoría
-- Registro automático de operaciones INSERT, UPDATE, DELETE
-- ================================================================

-- ==================== TRIGGERS PARA ARTISTA ====================

-- Trigger 1: Auditar INSERT en ARTISTA
CREATE OR REPLACE TRIGGER trg_audit_artista_insert
AFTER INSERT ON ARTISTA
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_nuevos := 'ID=' || :NEW.id_artista || 
                        ', Nombre=' || :NEW.nombre || 
                        ', Género=' || :NEW.genero_musical || 
                        ', País=' || :NEW.pais_origen;
    
    sp_insertar_auditoria(
        'ARTISTA',
        'INSERT',
        :NEW.id_artista,
        NULL,
        v_valores_nuevos,
        'Nuevo artista registrado',
        v_id_auditoria
    );
END;
/

-- Trigger 2: Auditar UPDATE en ARTISTA
CREATE OR REPLACE TRIGGER trg_audit_artista_update
AFTER UPDATE ON ARTISTA
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_anteriores VARCHAR2(4000);
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_anteriores := 'Nombre=' || :OLD.nombre || 
                            ', Género=' || :OLD.genero_musical || 
                            ', País=' || :OLD.pais_origen;
    
    v_valores_nuevos := 'Nombre=' || :NEW.nombre || 
                        ', Género=' || :NEW.genero_musical || 
                        ', País=' || :NEW.pais_origen;
    
    sp_insertar_auditoria(
        'ARTISTA',
        'UPDATE',
        :NEW.id_artista,
        v_valores_anteriores,
        v_valores_nuevos,
        'Artista actualizado',
        v_id_auditoria
    );
END;
/

-- Trigger 3: Auditar DELETE en ARTISTA
CREATE OR REPLACE TRIGGER trg_audit_artista_delete
BEFORE DELETE ON ARTISTA
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_anteriores VARCHAR2(4000);
BEGIN
    v_valores_anteriores := 'ID=' || :OLD.id_artista || 
                            ', Nombre=' || :OLD.nombre || 
                            ', Género=' || :OLD.genero_musical || 
                            ', País=' || :OLD.pais_origen;
    
    sp_insertar_auditoria(
        'ARTISTA',
        'DELETE',
        :OLD.id_artista,
        v_valores_anteriores,
        NULL,
        'Artista eliminado',
        v_id_auditoria
    );
END;
/

-- ==================== TRIGGERS PARA EVENTO ====================

-- Trigger 4: Auditar INSERT en EVENTO
CREATE OR REPLACE TRIGGER trg_audit_evento_insert
AFTER INSERT ON EVENTO
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_nuevos := 'ID=' || :NEW.id_evento || 
                        ', Nombre=' || :NEW.nombre || 
                        ', Fecha=' || TO_CHAR(:NEW.fecha_evento, 'DD/MM/YYYY') ||
                        ', Locación=' || :NEW.id_locacion ||
                        ', Artista=' || :NEW.id_artista;
    
    sp_insertar_auditoria(
        'EVENTO',
        'INSERT',
        :NEW.id_evento,
        NULL,
        v_valores_nuevos,
        'Nuevo evento creado',
        v_id_auditoria
    );
END;
/

-- Trigger 5: Auditar UPDATE en EVENTO
CREATE OR REPLACE TRIGGER trg_audit_evento_update
AFTER UPDATE ON EVENTO
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_anteriores VARCHAR2(4000);
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_anteriores := 'Nombre=' || :OLD.nombre || 
                            ', Fecha=' || TO_CHAR(:OLD.fecha_evento, 'DD/MM/YYYY');
    
    v_valores_nuevos := 'Nombre=' || :NEW.nombre || 
                        ', Fecha=' || TO_CHAR(:NEW.fecha_evento, 'DD/MM/YYYY');
    
    sp_insertar_auditoria(
        'EVENTO',
        'UPDATE',
        :NEW.id_evento,
        v_valores_anteriores,
        v_valores_nuevos,
        'Evento actualizado',
        v_id_auditoria
    );
END;
/

-- Trigger 6: Auditar DELETE en EVENTO
CREATE OR REPLACE TRIGGER trg_audit_evento_delete
BEFORE DELETE ON EVENTO
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_anteriores VARCHAR2(4000);
BEGIN
    v_valores_anteriores := 'ID=' || :OLD.id_evento || 
                            ', Nombre=' || :OLD.nombre || 
                            ', Fecha=' || TO_CHAR(:OLD.fecha_evento, 'DD/MM/YYYY');
    
    sp_insertar_auditoria(
        'EVENTO',
        'DELETE',
        :OLD.id_evento,
        v_valores_anteriores,
        NULL,
        'Evento eliminado',
        v_id_auditoria
    );
END;
/

-- ==================== TRIGGERS PARA COMPRA ====================

-- Trigger 7: Auditar INSERT en COMPRA
CREATE OR REPLACE TRIGGER trg_audit_compra_insert
AFTER INSERT ON COMPRA
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_nuevos := 'ID=' || :NEW.id_compra || 
                        ', Asistente=' || :NEW.id_asistente || 
                        ', Entrada=' || :NEW.id_entrada ||
                        ', Fecha=' || TO_CHAR(:NEW.fecha_compra, 'DD/MM/YYYY HH24:MI:SS');
    
    sp_insertar_auditoria(
        'COMPRA',
        'INSERT',
        :NEW.id_compra,
        NULL,
        v_valores_nuevos,
        'Nueva compra registrada',
        v_id_auditoria
    );
END;
/

-- Trigger 8: Auditar DELETE en COMPRA
CREATE OR REPLACE TRIGGER trg_audit_compra_delete
BEFORE DELETE ON COMPRA
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_anteriores VARCHAR2(4000);
BEGIN
    v_valores_anteriores := 'ID=' || :OLD.id_compra || 
                            ', Asistente=' || :OLD.id_asistente || 
                            ', Entrada=' || :OLD.id_entrada;
    
    sp_insertar_auditoria(
        'COMPRA',
        'DELETE',
        :OLD.id_compra,
        v_valores_anteriores,
        NULL,
        'Compra cancelada',
        v_id_auditoria
    );
END;
/

-- ==================== TRIGGERS PARA PAGO ====================

-- Trigger 9: Auditar INSERT en PAGO
CREATE OR REPLACE TRIGGER trg_audit_pago_insert
AFTER INSERT ON PAGO
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_nuevos := 'ID=' || :NEW.id_pago || 
                        ', Compra=' || :NEW.id_compra || 
                        ', Método=' || :NEW.metodo_pago ||
                        ', Monto=' || :NEW.monto;
    
    sp_insertar_auditoria(
        'PAGO',
        'INSERT',
        :NEW.id_pago,
        NULL,
        v_valores_nuevos,
        'Pago procesado',
        v_id_auditoria
    );
END;
/

-- ==================== TRIGGERS PARA ASISTENTE ====================

-- Trigger 10: Auditar INSERT en ASISTENTE
CREATE OR REPLACE TRIGGER trg_audit_asistente_insert
AFTER INSERT ON ASISTENTE
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_nuevos := 'ID=' || :NEW.id_asistente || 
                        ', Nombre=' || :NEW.nombre || 
                        ', Correo=' || :NEW.correo;
    
    sp_insertar_auditoria(
        'ASISTENTE',
        'INSERT',
        :NEW.id_asistente,
        NULL,
        v_valores_nuevos,
        'Nuevo asistente registrado',
        v_id_auditoria
    );
END;
/

-- Trigger 11: Auditar UPDATE en ASISTENTE
CREATE OR REPLACE TRIGGER trg_audit_asistente_update
AFTER UPDATE ON ASISTENTE
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_anteriores VARCHAR2(4000);
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_anteriores := 'Nombre=' || :OLD.nombre || ', Correo=' || :OLD.correo;
    v_valores_nuevos := 'Nombre=' || :NEW.nombre || ', Correo=' || :NEW.correo;
    
    sp_insertar_auditoria(
        'ASISTENTE',
        'UPDATE',
        :NEW.id_asistente,
        v_valores_anteriores,
        v_valores_nuevos,
        'Asistente actualizado',
        v_id_auditoria
    );
END;
/

-- Trigger 12: Auditar DELETE en ASISTENTE
CREATE OR REPLACE TRIGGER trg_audit_asistente_delete
BEFORE DELETE ON ASISTENTE
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_anteriores VARCHAR2(4000);
BEGIN
    v_valores_anteriores := 'ID=' || :OLD.id_asistente || 
                            ', Nombre=' || :OLD.nombre || 
                            ', Correo=' || :OLD.correo;
    
    sp_insertar_auditoria(
        'ASISTENTE',
        'DELETE',
        :OLD.id_asistente,
        v_valores_anteriores,
        NULL,
        'Asistente eliminado',
        v_id_auditoria
    );
END;
/

-- ==================== TRIGGERS PARA ENTRADA ====================

-- Trigger 13: Auditar INSERT en ENTRADA
CREATE OR REPLACE TRIGGER trg_audit_entrada_insert
AFTER INSERT ON ENTRADA
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_nuevos := 'ID=' || :NEW.id_entrada || 
                        ', Precio=' || :NEW.precio || 
                        ', Tipo=' || :NEW.tipo_entrada ||
                        ', Evento=' || :NEW.id_evento;
    
    sp_insertar_auditoria(
        'ENTRADA',
        'INSERT',
        :NEW.id_entrada,
        NULL,
        v_valores_nuevos,
        'Nueva entrada creada',
        v_id_auditoria
    );
END;
/

-- Trigger 14: Auditar UPDATE en ENTRADA
CREATE OR REPLACE TRIGGER trg_audit_entrada_update
AFTER UPDATE ON ENTRADA
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_anteriores VARCHAR2(4000);
    v_valores_nuevos VARCHAR2(4000);
BEGIN
    v_valores_anteriores := 'Precio=' || :OLD.precio || ', Tipo=' || :OLD.tipo_entrada;
    v_valores_nuevos := 'Precio=' || :NEW.precio || ', Tipo=' || :NEW.tipo_entrada;
    
    sp_insertar_auditoria(
        'ENTRADA',
        'UPDATE',
        :NEW.id_entrada,
        v_valores_anteriores,
        v_valores_nuevos,
        'Entrada actualizada',
        v_id_auditoria
    );
END;
/

-- Trigger 15: Auditar DELETE en ENTRADA
CREATE OR REPLACE TRIGGER trg_audit_entrada_delete
BEFORE DELETE ON ENTRADA
FOR EACH ROW
DECLARE
    v_id_auditoria NUMBER;
    v_valores_anteriores VARCHAR2(4000);
BEGIN
    v_valores_anteriores := 'ID=' || :OLD.id_entrada || 
                            ', Precio=' || :OLD.precio || 
                            ', Tipo=' || :OLD.tipo_entrada;
    
    sp_insertar_auditoria(
        'ENTRADA',
        'DELETE',
        :OLD.id_entrada,
        v_valores_anteriores,
        NULL,
        'Entrada eliminada',
        v_id_auditoria
    );
END;
/

COMMIT;

-- Verificar triggers creados
SELECT trigger_name, table_name, triggering_event 
FROM user_triggers 
WHERE trigger_name LIKE 'TRG_AUDIT%'
ORDER BY table_name, triggering_event;
