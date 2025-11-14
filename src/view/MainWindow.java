package view;

import controller.ArtistaController;
import controller.EventoController;
import controller.AuditoriaController;
import model.Artista;
import model.Evento;
import model.Auditoria;
import util.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Ventana principal del sistema de gestión de eventos
 * Implementa la arquitectura MVC
 */
public class MainWindow extends JFrame {
    
    private JTabbedPane tabbedPane;
    private ArtistaController artistaController;
    private EventoController eventoController;
    private AuditoriaController auditoriaController;
    
    public MainWindow() {
        // Inicializar controladores
        artistaController = new ArtistaController();
        eventoController = new EventoController();
        auditoriaController = new AuditoriaController();
        
        // Configurar ventana principal
        setTitle("Sistema de Gestión de Eventos y Conciertos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear panel principal con pestañas
        tabbedPane = new JTabbedPane();
        
        // Agregar pestañas
        tabbedPane.addTab("Inicio", crearPanelInicio());
        tabbedPane.addTab("Artistas", crearPanelArtistas());
        tabbedPane.addTab("Eventos", crearPanelEventos());
        tabbedPane.addTab("Reportes", crearPanelReportes());
        tabbedPane.addTab("Auditoría", crearPanelAuditoria());
        
        add(tabbedPane);
        
        // Verificar conexión al iniciar
        verificarConexion();
    }
    
    /**
     * Panel de inicio
     */
    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel titulo = new JLabel("Sistema de Gestión de Eventos y Conciertos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        JTextArea info = new JTextArea();
        info.setText("\nBienvenido al Sistema de Gestión de Eventos y Conciertos\n\n" +
                "Este sistema permite:\n" +
                "  • Gestionar artistas, eventos y locaciones\n" +
                "  • Administrar entradas y asistentes\n" +
                "  • Generar reportes y estadísticas\n" +
                "  • Control completo mediante base de datos Oracle\n\n" +
                "Seleccione una pestaña para comenzar.\n\n" +
                "Proyecto Final - SC-504 Lenguaje de Base de Datos\n" +
                "Universidad Fidélitas - III Cuatrimestre 2025");
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        info.setMargin(new Insets(20, 40, 20, 40));
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(info, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Panel de gestión de artistas
     */
    private JPanel crearPanelArtistas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Artista"));
        
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();
        
        JLabel lblGenero = new JLabel("Género Musical:");
        JTextField txtGenero = new JTextField();
        
        JLabel lblPais = new JLabel("País de Origen:");
        JTextField txtPais = new JTextField();
        
        formPanel.add(lblNombre);
        formPanel.add(txtNombre);
        formPanel.add(lblGenero);
        formPanel.add(txtGenero);
        formPanel.add(lblPais);
        formPanel.add(txtPais);
        
        // Panel de botones
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnInsertar = new JButton("Insertar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnRefrescar = new JButton("Refrescar Lista");
        
        btnPanel.add(btnInsertar);
        btnPanel.add(btnActualizar);
        btnPanel.add(btnEliminar);
        btnPanel.add(btnLimpiar);
        btnPanel.add(btnRefrescar);
        
        formPanel.add(new JLabel());
        formPanel.add(btnPanel);
        
        // Tabla de artistas
        String[] columnas = {"ID", "Nombre", "Género Musical", "País", "Fecha Creación"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(model);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Evento: Insertar artista
        btnInsertar.addActionListener(e -> {
            if (validarCampos(txtNombre, txtGenero, txtPais)) {
                Artista artista = new Artista();
                artista.setNombre(txtNombre.getText().trim());
                artista.setGeneroMusical(txtGenero.getText().trim());
                artista.setPaisOrigen(txtPais.getText().trim());
                
                if (artistaController.insertarArtista(artista)) {
                    JOptionPane.showMessageDialog(panel, "Artista insertado exitosamente");
                    cargarArtistas(model);
                    limpiarCampos(txtNombre, txtGenero, txtPais);
                } else {
                    JOptionPane.showMessageDialog(panel, "Error al insertar artista", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Evento: Refrescar lista
        btnRefrescar.addActionListener(e -> cargarArtistas(model));
        
        // Evento: Limpiar campos
        btnLimpiar.addActionListener(e -> limpiarCampos(txtNombre, txtGenero, txtPais));
        
        // Evento: Seleccionar fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int row = tabla.getSelectedRow();
                txtNombre.setText(tabla.getValueAt(row, 1).toString());
                txtGenero.setText(tabla.getValueAt(row, 2).toString());
                txtPais.setText(tabla.getValueAt(row, 3).toString());
            }
        });
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Cargar datos iniciales
        cargarArtistas(model);
        
        return panel;
    }
    
    /**
     * Panel de gestión de eventos
     */
    private JPanel crearPanelEventos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("Gestión de Eventos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Tabla de eventos
        String[] columnas = {"ID", "Nombre", "Fecha", "ID Locación", "ID Artista"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        JButton btnRefrescar = new JButton("Refrescar Lista");
        btnRefrescar.addActionListener(e -> cargarEventos(model));
        
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnRefrescar);
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        // Cargar datos iniciales
        cargarEventos(model);
        
        return panel;
    }
    
    /**
     * Panel de reportes
     */
    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea txtReporte = new JTextArea();
        txtReporte.setEditable(false);
        txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtReporte);
        
        JButton btnGenerarReporte = new JButton("Generar Reporte de Eventos Próximos");
        btnGenerarReporte.addActionListener(e -> {
            StringBuilder reporte = new StringBuilder();
            reporte.append("=== REPORTE DE EVENTOS PRÓXIMOS ===\n\n");
            
            List<Evento> eventos = eventoController.listarEventosProximos();
            
            for (Evento evento : eventos) {
                reporte.append("Evento: ").append(evento.getNombre()).append("\n");
                reporte.append("Fecha: ").append(evento.getFechaEvento()).append("\n");
                reporte.append("Artista: ").append(evento.getNombreArtista()).append("\n");
                reporte.append("Locación: ").append(evento.getNombreLocacion()).append("\n");
                reporte.append("---\n");
            }
            
            txtReporte.setText(reporte.toString());
        });
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnGenerarReporte, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Cargar artistas en la tabla
     */
    private void cargarArtistas(DefaultTableModel model) {
        model.setRowCount(0);
        List<Artista> artistas = artistaController.listarArtistas();
        
        for (Artista artista : artistas) {
            model.addRow(new Object[]{
                artista.getIdArtista(),
                artista.getNombre(),
                artista.getGeneroMusical(),
                artista.getPaisOrigen(),
                artista.getFechaCreacion()
            });
        }
    }
    
    /**
     * Cargar eventos en la tabla
     */
    private void cargarEventos(DefaultTableModel model) {
        model.setRowCount(0);
        List<Evento> eventos = eventoController.listarEventos();
        
        for (Evento evento : eventos) {
            model.addRow(new Object[]{
                evento.getIdEvento(),
                evento.getNombre(),
                evento.getFechaEvento(),
                evento.getIdLocacion(),
                evento.getIdArtista()
            });
        }
    }
    
    /**
     * Validar campos no vacíos
     */
    private boolean validarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios", 
                    "Validación", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }
    
    /**
     * Limpiar campos de texto
     */
    private void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }
    
    /**
     * Verificar conexión a la base de datos
     */
    private void verificarConexion() {
        if (DatabaseConnection.testConnection()) {
            JOptionPane.showMessageDialog(this, 
                "Conexión exitosa a la base de datos Oracle", 
                "Conexión", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al conectar con la base de datos.\nVerifique la configuración en DatabaseConnection.java", 
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Método principal
     */

    /**
     * Panel de auditoría
     */
    private JPanel crearPanelAuditoria() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("Registro de Auditoría del Sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Panel de opciones
        JPanel opcionesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel lblTabla = new JLabel("Filtrar por tabla:");
        String[] tablas = {"TODAS", "ARTISTA", "EVENTO", "ASISTENTE", "COMPRA", "PAGO", "ENTRADA"};
        JComboBox<String> cmbTabla = new JComboBox<>(tablas);
        
        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnTodas = new JButton("Ver Todas");
        JButton btnRefrescar = new JButton("Refrescar");
        
        opcionesPanel.add(lblTabla);
        opcionesPanel.add(cmbTabla);
        opcionesPanel.add(btnFiltrar);
        opcionesPanel.add(btnTodas);
        opcionesPanel.add(btnRefrescar);
        
        // Tabla de auditoría
        String[] columnas = {"ID", "Tabla", "Operación", "ID Registro", "Usuario", "Fecha", "Descripción"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(model);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Panel de estadísticas
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        
        JLabel lblTotalOps = new JLabel("Total operaciones: 0");
        JLabel lblUltimaOp = new JLabel("Última operación: N/A");
        JLabel lblTablaActiva = new JLabel("Tabla más activa: N/A");
        
        lblTotalOps.setFont(new Font("Arial", Font.BOLD, 12));
        lblUltimaOp.setFont(new Font("Arial", Font.BOLD, 12));
        lblTablaActiva.setFont(new Font("Arial", Font.BOLD, 12));
        
        statsPanel.add(lblTotalOps);
        statsPanel.add(lblUltimaOp);
        statsPanel.add(lblTablaActiva);
        
        // Eventos
        btnFiltrar.addActionListener(e -> {
            String tablaSeleccionada = (String) cmbTabla.getSelectedItem();
            if ("TODAS".equals(tablaSeleccionada)) {
                cargarAuditorias(model);
            } else {
                cargarAuditoriasPorTabla(model, tablaSeleccionada);
            }
            actualizarEstadisticasAuditoria(lblTotalOps, lblUltimaOp, lblTablaActiva, tablaSeleccionada);
        });
        
        btnTodas.addActionListener(e -> {
            cmbTabla.setSelectedIndex(0);
            cargarAuditorias(model);
            actualizarEstadisticasAuditoria(lblTotalOps, lblUltimaOp, lblTablaActiva, "TODAS");
        });
        
        btnRefrescar.addActionListener(e -> {
            String tablaSeleccionada = (String) cmbTabla.getSelectedItem();
            if ("TODAS".equals(tablaSeleccionada)) {
                cargarAuditorias(model);
            } else {
                cargarAuditoriasPorTabla(model, tablaSeleccionada);
            }
            actualizarEstadisticasAuditoria(lblTotalOps, lblUltimaOp, lblTablaActiva, tablaSeleccionada);
        });
        
        // Panel superior combinado
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titulo, BorderLayout.NORTH);
        topPanel.add(opcionesPanel, BorderLayout.CENTER);
        topPanel.add(statsPanel, BorderLayout.SOUTH);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Cargar datos iniciales
        cargarAuditorias(model);
        actualizarEstadisticasAuditoria(lblTotalOps, lblUltimaOp, lblTablaActiva, "TODAS");
        
        return panel;
    }
    
    /**
     * Cargar todas las auditorías
     */
    private void cargarAuditorias(DefaultTableModel model) {
        model.setRowCount(0);
        List<Auditoria> auditorias = auditoriaController.listarAuditorias();
        
        for (Auditoria auditoria : auditorias) {
            model.addRow(new Object[]{
                auditoria.getIdAuditoria(),
                auditoria.getTablaAfectada(),
                auditoria.getOperacion(),
                auditoria.getIdRegistro(),
                auditoria.getUsuario(),
                auditoria.getFechaOperacion(),
                auditoria.getDescripcion()
            });
        }
    }
    
    /**
     * Cargar auditorías por tabla
     */
    private void cargarAuditoriasPorTabla(DefaultTableModel model, String tabla) {
        model.setRowCount(0);
        List<Auditoria> auditorias = auditoriaController.listarAuditoriasPorTabla(tabla);
        
        for (Auditoria auditoria : auditorias) {
            model.addRow(new Object[]{
                auditoria.getIdAuditoria(),
                auditoria.getTablaAfectada(),
                auditoria.getOperacion(),
                auditoria.getIdRegistro(),
                auditoria.getUsuario(),
                auditoria.getFechaOperacion(),
                auditoria.getDescripcion()
            });
        }
    }
    
    /**
     * Actualizar estadísticas de auditoría
     */
    private void actualizarEstadisticasAuditoria(JLabel lblTotal, JLabel lblUltima, JLabel lblActiva, String tabla) {
        if ("TODAS".equals(tabla)) {
            List<Auditoria> todas = auditoriaController.listarAuditorias();
            lblTotal.setText("Total operaciones: " + todas.size());
            
            if (!todas.isEmpty()) {
                lblUltima.setText("Última operación: " + todas.get(0).getOperacion() + 
                                 " en " + todas.get(0).getTablaAfectada());
            }
        } else {
            int count = auditoriaController.contarAuditoriasPorTabla(tabla);
            lblTotal.setText("Operaciones en " + tabla + ": " + count);
            
            String ultimaOp = auditoriaController.obtenerUltimaOperacion(tabla);
            lblUltima.setText("Última operación: " + ultimaOp);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
