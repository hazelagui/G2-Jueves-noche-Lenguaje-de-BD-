-- ================================================================
-- SISTEMA DE GESTION DE EVENTOS Y CONCIERTOS
-- Script Maestro - Ejecutar todos los scripts en orden
-- ================================================================

PROMPT ========================================
PROMPT Instalación del Sistema de Eventos
PROMPT ========================================

PROMPT 
PROMPT [1/6] Creando tablas...
@@01_crear_tablas.sql

PROMPT 
PROMPT [2/6] Creando procedimientos almacenados...
@@02_procedimientos_almacenados.sql

PROMPT 
PROMPT [3/6] Creando funciones...
@@03_funciones.sql

PROMPT 
PROMPT [4/6] Creando vistas...
@@04_vistas.sql

PROMPT 
PROMPT [5/7] Creando cursores...
@@05_cursores.sql

PROMPT 
PROMPT [6/7] Creando triggers de auditoría...
@@07_triggers_auditoria.sql

PROMPT 
PROMPT [7/7] Insertando datos de prueba...
@@06_datos_prueba.sql

PROMPT 
PROMPT ========================================
PROMPT Instalación completada exitosamente
PROMPT ========================================

PROMPT 
PROMPT Resumen de objetos creados:
PROMPT 

SELECT 'Tablas' AS tipo, COUNT(*) AS cantidad FROM user_tables
UNION ALL
SELECT 'Procedimientos', COUNT(*) FROM user_procedures WHERE object_type = 'PROCEDURE'
UNION ALL
SELECT 'Funciones', COUNT(*) FROM user_objects WHERE object_type = 'FUNCTION'
UNION ALL
SELECT 'Vistas', COUNT(*) FROM user_views
UNION ALL
SELECT 'Secuencias', COUNT(*) FROM user_sequences;

PROMPT 
PROMPT El sistema está listo para usar.
PROMPT Puede ejecutar la aplicación Java ahora.
