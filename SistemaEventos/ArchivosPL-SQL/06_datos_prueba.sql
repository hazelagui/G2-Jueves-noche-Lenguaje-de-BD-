-- ================================================================
-- SISTEMA DE GESTION DE EVENTOS Y CONCIERTOS
-- Script 6: Datos de Prueba
-- ================================================================

-- Insertar Artistas
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_artista('Coldplay', 'Rock', 'Reino Unido', v_id);
    sp_insertar_artista('Bad Bunny', 'Reggaeton', 'Puerto Rico', v_id);
    sp_insertar_artista('Taylor Swift', 'Pop', 'Estados Unidos', v_id);
    sp_insertar_artista('Metallica', 'Heavy Metal', 'Estados Unidos', v_id);
    sp_insertar_artista('J Balvin', 'Reggaeton', 'Colombia', v_id);
END;
/

-- Insertar Locaciones
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_locacion('Estadio Nacional', 'San José, Costa Rica', 35000, v_id);
    sp_insertar_locacion('Arena Alajuela', 'Alajuela, Costa Rica', 15000, v_id);
    sp_insertar_locacion('Teatro Nacional', 'San José, Costa Rica', 1200, v_id);
    sp_insertar_locacion('Parque Viva', 'La Guácima, Costa Rica', 10000, v_id);
END;
/

-- Insertar Departamentos
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_departamento('Logística', 'Oficina Central', v_id);
    sp_insertar_departamento('Marketing', 'Oficina Central', v_id);
    sp_insertar_departamento('Ventas', 'Oficina Central', v_id);
    sp_insertar_departamento('Tecnología', 'Oficina Central', v_id);
END;
/

-- Insertar Empleados
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_empleado('Juan Pérez', 'Coordinador', 2500.00, 1, v_id);
    sp_insertar_empleado('María González', 'Diseñadora', 2200.00, 2, v_id);
    sp_insertar_empleado('Carlos Rodríguez', 'Vendedor', 1800.00, 3, v_id);
    sp_insertar_empleado('Ana Martínez', 'Desarrolladora', 3000.00, 4, v_id);
END;
/

-- Insertar Patrocinadores
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_patrocinador('Coca Cola', 'Oro', v_id);
    sp_insertar_patrocinador('Pepsi', 'Plata', v_id);
    sp_insertar_patrocinador('Banco Nacional', 'Platino', v_id);
END;
/

-- Insertar Proveedores
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_proveedor('SoundPro CR', '2234-5678', v_id);
    sp_insertar_proveedor('Iluminación Total', '2245-6789', v_id);
    sp_insertar_proveedor('Seguridad Privada', '2256-7890', v_id);
END;
/

-- Insertar Eventos
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_evento('Coldplay Costa Rica 2025', TO_DATE('2025-12-15', 'YYYY-MM-DD'), 1, 1, v_id);
    sp_insertar_evento('Bad Bunny Tour', TO_DATE('2025-11-20', 'YYYY-MM-DD'), 2, 2, v_id);
    sp_insertar_evento('Taylor Swift Eras Tour', TO_DATE('2025-12-30', 'YYYY-MM-DD'), 1, 3, v_id);
END;
/

-- Insertar Asistentes
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_asistente('Laura Fernández', 'laura@email.com', '8888-1111', v_id);
    sp_insertar_asistente('Pedro Castro', 'pedro@email.com', '8888-2222', v_id);
    sp_insertar_asistente('Sofia Vargas', 'sofia@email.com', '8888-3333', v_id);
    sp_insertar_asistente('Diego Mora', 'diego@email.com', '8888-4444', v_id);
END;
/

-- Insertar Entradas
DECLARE
    v_id NUMBER;
BEGIN
    -- Coldplay
    sp_insertar_entrada(50000, 'General', 1, v_id);
    sp_insertar_entrada(80000, 'VIP', 1, v_id);
    -- Bad Bunny
    sp_insertar_entrada(45000, 'General', 2, v_id);
    sp_insertar_entrada(75000, 'VIP', 2, v_id);
    -- Taylor Swift
    sp_insertar_entrada(60000, 'General', 3, v_id);
    sp_insertar_entrada(100000, 'VIP', 3, v_id);
END;
/

-- Insertar Compras
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_compra(1, 1, SYSDATE, v_id);
    sp_insertar_compra(2, 3, SYSDATE, v_id);
    sp_insertar_compra(3, 5, SYSDATE, v_id);
    sp_insertar_compra(4, 2, SYSDATE, v_id);
END;
/

-- Insertar Pagos
DECLARE
    v_id NUMBER;
BEGIN
    sp_insertar_pago(1, 'Tarjeta de Crédito', 50000, SYSDATE, v_id);
    sp_insertar_pago(2, 'SINPE Móvil', 45000, SYSDATE, v_id);
    sp_insertar_pago(3, 'Tarjeta de Débito', 60000, SYSDATE, v_id);
    sp_insertar_pago(4, 'Efectivo', 80000, SYSDATE, v_id);
END;
/

-- Asociar Patrocinadores a Eventos
BEGIN
    sp_asociar_patrocinador_evento(1, 1);
    sp_asociar_patrocinador_evento(1, 3);
    sp_asociar_patrocinador_evento(2, 2);
END;
/

-- Insertar Tickets Virtuales
DECLARE
    v_id NUMBER;
    v_codigo VARCHAR2(100);
BEGIN
    v_codigo := fn_generar_codigo_qr(1, 1);
    sp_insertar_ticket_virtual(1, 1, v_codigo, v_id);
    
    v_codigo := fn_generar_codigo_qr(2, 2);
    sp_insertar_ticket_virtual(2, 2, v_codigo, v_id);
    
    v_codigo := fn_generar_codigo_qr(3, 3);
    sp_insertar_ticket_virtual(3, 3, v_codigo, v_id);
END;
/

COMMIT;

-- Verificación de datos insertados
SELECT 'Artistas: ' || COUNT(*) AS resultado FROM ARTISTA
UNION ALL
SELECT 'Eventos: ' || COUNT(*) FROM EVENTO
UNION ALL
SELECT 'Asistentes: ' || COUNT(*) FROM ASISTENTE
UNION ALL
SELECT 'Compras: ' || COUNT(*) FROM COMPRA
UNION ALL
SELECT 'Pagos: ' || COUNT(*) FROM PAGO;
