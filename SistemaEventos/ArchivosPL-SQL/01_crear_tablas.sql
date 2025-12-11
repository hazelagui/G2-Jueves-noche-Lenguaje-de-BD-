-- ================================================================
-- SISTEMA DE GESTION DE EVENTOS Y CONCIERTOS
-- Script 1: Creación de Tablas
-- ================================================================

-- Eliminar tablas si existen (en orden inverso por dependencias)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE TICKET_VIRTUAL CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE PAGO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE COMPRA CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE CONTRATO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE EVENTO_PATROCINADOR CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ALOJAMIENTO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE TRANSPORTE CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE STAFF_TECNICO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE BANDA_SOPORTE CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE MARKETING CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE SEGURIDAD CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ENTRADA CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE REPORTES CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE EVENTO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE SERVICIO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ASISTENTE CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE PROVEEDOR CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE INVENTARIO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE EMPLEADO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE PATROCINADOR CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE DEPARTAMENTO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE LOCACION CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE AUDITORIA CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ARTISTA CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- ================================================================
-- TABLA: AUDITORIA
-- ================================================================
CREATE TABLE AUDITORIA (
    id_auditoria NUMBER PRIMARY KEY,
    tabla_afectada VARCHAR2(50) NOT NULL,
    operacion VARCHAR2(10) NOT NULL,
    id_registro NUMBER,
    usuario VARCHAR2(50) DEFAULT USER,
    fecha_operacion DATE DEFAULT SYSDATE,
    valores_anteriores VARCHAR2(4000),
    valores_nuevos VARCHAR2(4000),
    descripcion VARCHAR2(500),
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50)
);

CREATE SEQUENCE seq_auditoria START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: ARTISTA
-- ================================================================
CREATE TABLE ARTISTA (
    id_artista NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    genero_musical VARCHAR2(50),
    pais_origen VARCHAR2(50),
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50)
);

CREATE SEQUENCE seq_artista START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: LOCACION
-- ================================================================
CREATE TABLE LOCACION (
    id_locacion NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    direccion VARCHAR2(200),
    capacidad NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50)
);

CREATE SEQUENCE seq_locacion START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: DEPARTAMENTO
-- ================================================================
CREATE TABLE DEPARTAMENTO (
    id_departamento NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    ubicacion VARCHAR2(100),
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50)
);

CREATE SEQUENCE seq_departamento START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: EMPLEADO
-- ================================================================
CREATE TABLE EMPLEADO (
    id_empleado NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    puesto VARCHAR2(50),
    salario NUMBER(10,2),
    id_departamento NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_empleado_depto FOREIGN KEY (id_departamento) 
        REFERENCES DEPARTAMENTO(id_departamento)
);

CREATE SEQUENCE seq_empleado START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: PATROCINADOR
-- ================================================================
CREATE TABLE PATROCINADOR (
    id_patrocinador NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    tipo_patrocinio VARCHAR2(50),
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50)
);

CREATE SEQUENCE seq_patrocinador START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: PROVEEDOR
-- ================================================================
CREATE TABLE PROVEEDOR (
    id_proveedor NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    telefono VARCHAR2(20),
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50)
);

CREATE SEQUENCE seq_proveedor START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: INVENTARIO
-- ================================================================
CREATE TABLE INVENTARIO (
    id_inventario NUMBER PRIMARY KEY,
    nombre_item VARCHAR2(100) NOT NULL,
    cantidad NUMBER,
    id_locacion NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_inventario_locacion FOREIGN KEY (id_locacion) 
        REFERENCES LOCACION(id_locacion)
);

CREATE SEQUENCE seq_inventario START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: ASISTENTE
-- ================================================================
CREATE TABLE ASISTENTE (
    id_asistente NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    correo VARCHAR2(100),
    telefono VARCHAR2(20),
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50)
);

CREATE SEQUENCE seq_asistente START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: SERVICIO
-- ================================================================
CREATE TABLE SERVICIO (
    id_servicio NUMBER PRIMARY KEY,
    descripcion VARCHAR2(200),
    costo NUMBER(10,2),
    id_proveedor NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_servicio_proveedor FOREIGN KEY (id_proveedor) 
        REFERENCES PROVEEDOR(id_proveedor)
);

CREATE SEQUENCE seq_servicio START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: EVENTO
-- ================================================================
CREATE TABLE EVENTO (
    id_evento NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    fecha_evento DATE,
    id_locacion NUMBER,
    id_artista NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_evento_locacion FOREIGN KEY (id_locacion) 
        REFERENCES LOCACION(id_locacion),
    CONSTRAINT fk_evento_artista FOREIGN KEY (id_artista) 
        REFERENCES ARTISTA(id_artista)
);

CREATE SEQUENCE seq_evento START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: REPORTES
-- ================================================================
CREATE TABLE REPORTES (
    id_reporte NUMBER PRIMARY KEY,
    tipo_reporte VARCHAR2(50),
    fecha_generacion DATE DEFAULT SYSDATE,
    id_evento NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_reporte_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento)
);

CREATE SEQUENCE seq_reporte START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: ENTRADA
-- ================================================================
CREATE TABLE ENTRADA (
    id_entrada NUMBER PRIMARY KEY,
    precio NUMBER(10,2) NOT NULL,
    tipo_entrada VARCHAR2(20),
    id_evento NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_entrada_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento)
);

CREATE SEQUENCE seq_entrada START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: SEGURIDAD
-- ================================================================
CREATE TABLE SEGURIDAD (
    id_seguridad NUMBER PRIMARY KEY,
    nombre_empresa VARCHAR2(100),
    cantidad_personal NUMBER,
    id_evento NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_seguridad_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento)
);

CREATE SEQUENCE seq_seguridad START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: MARKETING
-- ================================================================
CREATE TABLE MARKETING (
    id_marketing NUMBER PRIMARY KEY,
    canal_publicidad VARCHAR2(50),
    presupuesto NUMBER(10,2),
    id_evento NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_marketing_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento)
);

CREATE SEQUENCE seq_marketing START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: BANDA_SOPORTE
-- ================================================================
CREATE TABLE BANDA_SOPORTE (
    id_banda NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    id_evento NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_banda_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento)
);

CREATE SEQUENCE seq_banda START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: STAFF_TECNICO
-- ================================================================
CREATE TABLE STAFF_TECNICO (
    id_staff NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    rol VARCHAR2(50),
    id_evento NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_staff_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento)
);

CREATE SEQUENCE seq_staff START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: TRANSPORTE
-- ================================================================
CREATE TABLE TRANSPORTE (
    id_transporte NUMBER PRIMARY KEY,
    tipo VARCHAR2(50),
    empresa VARCHAR2(100),
    id_evento NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_transporte_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento)
);

CREATE SEQUENCE seq_transporte START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: ALOJAMIENTO
-- ================================================================
CREATE TABLE ALOJAMIENTO (
    id_alojamiento NUMBER PRIMARY KEY,
    hotel VARCHAR2(100),
    noches NUMBER,
    id_evento NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_alojamiento_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento)
);

CREATE SEQUENCE seq_alojamiento START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: EVENTO_PATROCINADOR (Relación N:M)
-- ================================================================
CREATE TABLE EVENTO_PATROCINADOR (
    id_evento NUMBER,
    id_patrocinador NUMBER,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT pk_evento_patrocinador PRIMARY KEY (id_evento, id_patrocinador),
    CONSTRAINT fk_ep_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento),
    CONSTRAINT fk_ep_patrocinador FOREIGN KEY (id_patrocinador) 
        REFERENCES PATROCINADOR(id_patrocinador)
);

-- ================================================================
-- TABLA: COMPRA
-- ================================================================
CREATE TABLE COMPRA (
    id_compra NUMBER PRIMARY KEY,
    id_asistente NUMBER,
    id_entrada NUMBER,
    fecha_compra DATE DEFAULT SYSDATE,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_compra_asistente FOREIGN KEY (id_asistente) 
        REFERENCES ASISTENTE(id_asistente),
    CONSTRAINT fk_compra_entrada FOREIGN KEY (id_entrada) 
        REFERENCES ENTRADA(id_entrada)
);

CREATE SEQUENCE seq_compra START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: PAGO
-- ================================================================
CREATE TABLE PAGO (
    id_pago NUMBER PRIMARY KEY,
    id_compra NUMBER,
    metodo_pago VARCHAR2(30),
    monto NUMBER(10,2),
    fecha_pago DATE DEFAULT SYSDATE,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_pago_compra FOREIGN KEY (id_compra) 
        REFERENCES COMPRA(id_compra)
);

CREATE SEQUENCE seq_pago START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: TICKET_VIRTUAL
-- ================================================================
CREATE TABLE TICKET_VIRTUAL (
    id_ticket_virtual NUMBER PRIMARY KEY,
    id_evento NUMBER,
    id_asistente NUMBER,
    codigo_qr VARCHAR2(100),
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_ticket_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento),
    CONSTRAINT fk_ticket_asistente FOREIGN KEY (id_asistente) 
        REFERENCES ASISTENTE(id_asistente)
);

CREATE SEQUENCE seq_ticket START WITH 1 INCREMENT BY 1;

-- ================================================================
-- TABLA: CONTRATO
-- ================================================================
CREATE TABLE CONTRATO (
    id_contrato NUMBER PRIMARY KEY,
    id_evento NUMBER,
    id_proveedor NUMBER,
    fecha_fin DATE,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_modificacion DATE,
    creado_por VARCHAR2(50) DEFAULT USER,
    modificado_por VARCHAR2(50),
    CONSTRAINT fk_contrato_evento FOREIGN KEY (id_evento) 
        REFERENCES EVENTO(id_evento),
    CONSTRAINT fk_contrato_proveedor FOREIGN KEY (id_proveedor) 
        REFERENCES PROVEEDOR(id_proveedor)
);

CREATE SEQUENCE seq_contrato START WITH 1 INCREMENT BY 1;

COMMIT;
