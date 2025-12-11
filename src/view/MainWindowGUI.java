/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


package view;
import controller.ArtistaController;
import model.Artista;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import model.*;
import controller.*;
import java.util.List;



/**
 *
 * @author dalfaro
 */
public class MainWindowGUI extends javax.swing.JFrame {

    
    
    private ArtistaController artistaController;
    private EventoController eventoController;

    /**
     * Creates new form MainWindowGUI
     */
    public MainWindowGUI() {
    initComponents();
    verificarConexionBD();
    artistaController = new ArtistaController();
    eventoController = new EventoController();
    configurarComboLocacion();
    cargarPanelEventos();
    inicializarAcciones();
    inicializarEventosTabla();
    inicializarEventosTablaSeleccion();
    inicializarEventosTablaParaEditar();
}
    
private void cargarPanelEventos(){
    cargarLocacionesEnCombo();
    cargarEventosEnTabla();
    cargarArtistasEnCombo();
    cargarArtistasEnTabla();
}

private void configurarComboLocacion() {
    jComboBox_idLocation_panelEvento.setModel(
        new javax.swing.DefaultComboBoxModel<>()
    );
}


    
private void inicializarAcciones() {
    jButton_insertarArtista.addActionListener(e -> jButton_insertarArtistaActionPerformed(e));
    jButton_limpiarTextFields.addActionListener(e -> limpiarCampos());
    jButton_updatearArtista.addActionListener(e -> jButton_updatearArtistaActionPerformed(e));
    jButton_LimpiarEventos.addActionListener(e -> limpiarCamposEvento());

}


    
    private void inicializarEventosTabla() {
    jTable_artistas.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int fila = jTable_artistas.getSelectedRow();
            if (fila >= 0) {
                jTextField_NombreArtista.setText(jTable_artistas.getValueAt(fila, 1).toString());
                jTextField_GeneroArtista.setText(jTable_artistas.getValueAt(fila, 2).toString());
                jTextField_paisOrigenArtista.setText(jTable_artistas.getValueAt(fila, 3).toString());
            }
        }
    });
}
    
    private void limpiarCamposEvento() {
    jTextField_NombreEvento.setText("");
    jTextField_FechaEvento.setText("");
    if (jComboBox_idLocation_panelEvento.getItemCount() > 0) {
        jComboBox_idLocation_panelEvento.setSelectedIndex(0);
    }
    if (jComboBox_idArtista_panelEvento.getItemCount() > 0) {
        jComboBox_idArtista_panelEvento.setSelectedIndex(0);
    }
    jTable_Eventos.clearSelection();
}
    
private void inicializarEventosTablaParaEditar() {
    jTable_Eventos.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {

            int fila = jTable_Eventos.getSelectedRow();
            if (fila >= 0) {

                jTextField_NombreEvento.setText(jTable_Eventos.getValueAt(fila, 1).toString());
                jTextField_FechaEvento.setText(jTable_Eventos.getValueAt(fila, 2).toString());

                // Seleccionar locación en el combo
                int idLoc = Integer.parseInt(jTable_Eventos.getValueAt(fila, 3).toString());
                seleccionarItemCombo(jComboBox_idLocation_panelEvento, idLoc);

                // Seleccionar artista en el combo
                int idArt = Integer.parseInt(jTable_Eventos.getValueAt(fila, 4).toString());
                seleccionarItemCombo(jComboBox_idArtista_panelEvento, idArt);
            }
        }
    });
}

private void seleccionarItemCombo(javax.swing.JComboBox<String> combo, int idBuscado) {
    for (int i = 0; i < combo.getItemCount(); i++) {
        String item = combo.getItemAt(i);
        int id = extraerIdDesdeCombo(item); // ya existe y funciona
        if (id == idBuscado) {
            combo.setSelectedIndex(i);
            return;
        }
    }
}
    
    
    
    private int extraerIdDesdeCombo(String texto) {
    try {
        texto = texto.toLowerCase(); // normalize
        texto = texto.replace("id:", "").trim();

        // detenernos donde aparezca coma o espacio
        int fin = texto.indexOf(',');
        if (fin == -1) fin = texto.indexOf(' ');
        if (fin == -1) fin = texto.length();

        String numero = texto.substring(0, fin).trim();
        return Integer.parseInt(numero);
    } catch (Exception e) {
        System.out.println("Error parseando ID desde combo: " + texto);
        return -1;
    }
}
    private void verificarConexionBD() {
    try {
        java.sql.Connection conn = util.DatabaseConnection.getConnection();

        if (conn != null && !conn.isClosed()) {
            JOptionPane.showMessageDialog(
                    this,
                    "✔ Conexión exitosa a Oracle.",
                    "Estado de conexión",
                    JOptionPane.INFORMATION_MESSAGE
            );
            conn.close();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "✖ No se pudo conectar a Oracle.",
                    "Estado de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                this,
                "✖ Error de conexión:\n" + e.getMessage(),
                "Estado de conexión",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
    
private void cargarLocacionesEnCombo() {
    LocacionController locController = new LocacionController();

    jComboBox_idLocation_panelEvento.removeAllItems();

    for (Locacion loc : locController.listarLocaciones()) {
        jComboBox_idLocation_panelEvento.addItem(loc.toString());
    }
}

private void cargarArtistasEnCombo() {
    jComboBox_idArtista_panelEvento.removeAllItems();

    for (Artista art : artistaController.listarArtistas()) {
        jComboBox_idArtista_panelEvento.addItem(art.toString());
    }
}

private void cargarEventosEnTabla() {
    DefaultTableModel model = (DefaultTableModel) jTable_Eventos.getModel();
    model.setRowCount(0);

    for (Evento e : eventoController.listarEventos()) {
        model.addRow(new Object[]{
            e.getIdEvento(),
            e.getNombre(),
            e.getFechaEvento(),
            e.getIdLocacion(),
            e.getIdArtista(),
            e.getFechaCreacion()
        });
    }
}

private int extraerIdDesdeTexto(String texto) {
    try {
        texto = texto.toLowerCase();

        // buscamos la posición después de "id:"
        int pos = texto.indexOf("id:");
        if (pos == -1) return -1;

        texto = texto.substring(pos + 3).trim(); // después de "id:"

        // detenemos en el primer espacio o coma
        int fin = texto.indexOf(',');
        int fin2 = texto.indexOf(' ');
        
        if (fin == -1 || (fin2 != -1 && fin2 < fin)) fin = fin2;
        if (fin == -1) fin = texto.length();

        String numero = texto.substring(0, fin).trim();

        return Integer.parseInt(numero);
    } catch (Exception e) {
        System.out.println("Error extrayendo ID desde: " + texto);
        return -1;
    }
}

private void seleccionarItemComboPorID(javax.swing.JComboBox<String> combo, int idBuscado) {

    for (int i = 0; i < combo.getItemCount(); i++) {
        String item = combo.getItemAt(i); // ejemplo: "id: 4, nombre=Parque Viva"

        // Extraer el número después de "id:"
        try {
            String parteID = item.split(":")[1].split(",")[0].trim();
            int id = Integer.parseInt(parteID);

            if (id == idBuscado) {
                combo.setSelectedIndex(i);
                return;
            }
        } catch (Exception e) {
            System.out.println("No se pudo parsear item del combo: " + item);
        }
    }
}



private void inicializarEventosTablaSeleccion() {

    jTable_Eventos.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {

            int fila = jTable_Eventos.getSelectedRow();
            if (fila < 0) return;

            // --- Extraer valores de la tabla ---
            String nombre = jTable_Eventos.getValueAt(fila, 1).toString();
            String fecha = jTable_Eventos.getValueAt(fila, 2).toString();
            int idLocacion = Integer.parseInt(jTable_Eventos.getValueAt(fila, 3).toString());
            int idArtista = Integer.parseInt(jTable_Eventos.getValueAt(fila, 4).toString());

            // --- Llenar textfields ---
            jTextField_NombreEvento.setText(nombre);
            jTextField_FechaEvento.setText(fecha);

            // --- Seleccionar ítem correcto en el combo de locación ---
            seleccionarItemComboPorID(jComboBox_idLocation_panelEvento, idLocacion);

            // --- Seleccionar ítem correcto en combo artista ---
            seleccionarItemComboPorID(jComboBox_idArtista_panelEvento, idArtista);
        }
    });
}






    private void inicializarEventos() {

    jTable_artistas.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int fila = jTable_artistas.getSelectedRow();
            if (fila >= 0) {
                jTextField_NombreArtista.setText(jTable_artistas.getValueAt(fila, 1).toString());
                jTextField_GeneroArtista.setText(jTable_artistas.getValueAt(fila, 2).toString());
                jTextField_paisOrigenArtista.setText(jTable_artistas.getValueAt(fila, 3).toString());
            }
        }
    });

    jButton_insertarArtista.addActionListener(evt -> jButton_insertarArtistaActionPerformed(evt));
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel_inicioGlobal = new javax.swing.JPanel();
        jlabel_tituloInicio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_InicioDescripcion = new javax.swing.JTextArea();
        jPanel_ArtistaGlobal = new javax.swing.JPanel();
        jPanel_DatosdelArtista = new javax.swing.JPanel();
        jTextField_NombreArtista = new javax.swing.JTextField();
        jTextField_GeneroArtista = new javax.swing.JTextField();
        jTextField_paisOrigenArtista = new javax.swing.JTextField();
        jLabel_nombre = new javax.swing.JLabel();
        jLabel_genero = new javax.swing.JLabel();
        jLabel_paisDeOrigen = new javax.swing.JLabel();
        jButton_insertarArtista = new javax.swing.JButton();
        jButton_updatearArtista = new javax.swing.JButton();
        jButton_borrarArtista = new javax.swing.JButton();
        jButton_limpiarTextFields = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane_panelDetabla = new javax.swing.JScrollPane();
        jTable_artistas = new javax.swing.JTable();
        jPanel_EventoGlobal = new javax.swing.JPanel();
        jPanel_DatosdelEvento = new javax.swing.JPanel();
        jTextField_NombreEvento = new javax.swing.JTextField();
        jTextField_FechaEvento = new javax.swing.JTextField();
        jLabel_NombreEvento_panelEvento = new javax.swing.JLabel();
        jLabel_FechaEvento = new javax.swing.JLabel();
        jLabel_IDLocation_EnPanelDeEvento = new javax.swing.JLabel();
        jLabel_IDArtista_enPanelEvento = new javax.swing.JLabel();
        jComboBox_idLocation_panelEvento = new javax.swing.JComboBox<>();
        jComboBox_idArtista_panelEvento = new javax.swing.JComboBox<>();
        jButton_CrearEvento = new javax.swing.JButton();
        jButton_eliminarEvento = new javax.swing.JButton();
        jButton_ModificarEvento = new javax.swing.JButton();
        jButton_LimpiarEventos = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_Eventos = new javax.swing.JTable();
        jPanel_ReporteGlobal = new javax.swing.JPanel();
        jPanel_reporteGlobal = new javax.swing.JPanel();
        jLabel_generarReporte = new javax.swing.JLabel();
        jButton_generarReporteConciertosProximos = new javax.swing.JButton();
        jPanel_report = new javax.swing.JPanel();
        jScrollPane_reporteDisplay = new javax.swing.JScrollPane();
        jTextArea_repotesDisplay = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlabel_tituloInicio.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jlabel_tituloInicio.setText("Sistema de gestion de eventos y conciertos");

        jTextArea_InicioDescripcion.setEditable(false);
        jTextArea_InicioDescripcion.setColumns(20);
        jTextArea_InicioDescripcion.setRows(5);
        jTextArea_InicioDescripcion.setText("Bienvenido al Sistema de Gestión de Eventos y Conciertos\n\nEste sistema permite:\n  - Gestionar artistas, eventos y locaciones\n  - Administrar entradas y asistentes\n  - Generar reportes y estadísticas\n  - Control completo mediante base de datos Oracle\n\nSeleccione una pestaña para comenzar.\n\nProyecto Final - SC-504 Lenguaje de Base de Datos\nUniversidad Fidélitas - III Cuatrimestre 2025");
        jScrollPane1.setViewportView(jTextArea_InicioDescripcion);

        javax.swing.GroupLayout jPanel_inicioGlobalLayout = new javax.swing.GroupLayout(jPanel_inicioGlobal);
        jPanel_inicioGlobal.setLayout(jPanel_inicioGlobalLayout);
        jPanel_inicioGlobalLayout.setHorizontalGroup(
            jPanel_inicioGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_inicioGlobalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_inicioGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel_inicioGlobalLayout.createSequentialGroup()
                        .addComponent(jlabel_tituloInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(1043, Short.MAX_VALUE))))
        );
        jPanel_inicioGlobalLayout.setVerticalGroup(
            jPanel_inicioGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_inicioGlobalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlabel_tituloInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Inicio", jPanel_inicioGlobal);

        jPanel_DatosdelArtista.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255), 2));

        jTextField_NombreArtista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_NombreArtistaActionPerformed(evt);
            }
        });

        jLabel_nombre.setText("Nombre:");

        jLabel_genero.setText("Género:");

        jLabel_paisDeOrigen.setText("País de Origen:");

        jButton_insertarArtista.setText("Insertar");

        jButton_updatearArtista.setText("Update");

        jButton_borrarArtista.setText("Delete");
        jButton_borrarArtista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_borrarArtistaActionPerformed(evt);
            }
        });

        jButton_limpiarTextFields.setText("Limpiar");

        jLabel1.setText("Administrar Artistas");

        javax.swing.GroupLayout jPanel_DatosdelArtistaLayout = new javax.swing.GroupLayout(jPanel_DatosdelArtista);
        jPanel_DatosdelArtista.setLayout(jPanel_DatosdelArtistaLayout);
        jPanel_DatosdelArtistaLayout.setHorizontalGroup(
            jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DatosdelArtistaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton_insertarArtista)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_DatosdelArtistaLayout.createSequentialGroup()
                        .addGroup(jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_genero, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_paisDeOrigen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField_paisOrigenArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTextField_GeneroArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel_DatosdelArtistaLayout.createSequentialGroup()
                            .addGroup(jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel_DatosdelArtistaLayout.createSequentialGroup()
                                    .addComponent(jButton_updatearArtista)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton_borrarArtista))
                                .addComponent(jLabel_nombre))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField_NombreArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_limpiarTextFields)))))
                .addGap(55, 55, 55)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_DatosdelArtistaLayout.setVerticalGroup(
            jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DatosdelArtistaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_nombre)
                    .addComponent(jTextField_NombreArtista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_DatosdelArtistaLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_genero)
                            .addComponent(jTextField_GeneroArtista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_DatosdelArtistaLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_paisDeOrigen)
                    .addComponent(jTextField_paisOrigenArtista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel_DatosdelArtistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_insertarArtista)
                    .addComponent(jButton_updatearArtista)
                    .addComponent(jButton_borrarArtista)
                    .addComponent(jButton_limpiarTextFields))
                .addGap(31, 31, 31))
        );

        jTable_artistas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Género", "País de origen", "Fecha de creación"
            }
        ));
        jScrollPane_panelDetabla.setViewportView(jTable_artistas);

        javax.swing.GroupLayout jPanel_ArtistaGlobalLayout = new javax.swing.GroupLayout(jPanel_ArtistaGlobal);
        jPanel_ArtistaGlobal.setLayout(jPanel_ArtistaGlobalLayout);
        jPanel_ArtistaGlobalLayout.setHorizontalGroup(
            jPanel_ArtistaGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_DatosdelArtista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_ArtistaGlobalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_panelDetabla, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1036, Short.MAX_VALUE))
        );
        jPanel_ArtistaGlobalLayout.setVerticalGroup(
            jPanel_ArtistaGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ArtistaGlobalLayout.createSequentialGroup()
                .addComponent(jPanel_DatosdelArtista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane_panelDetabla, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 123, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Artistas", jPanel_ArtistaGlobal);

        jTextField_FechaEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_FechaEventoActionPerformed(evt);
            }
        });

        jLabel_NombreEvento_panelEvento.setText("Nombre Evento");

        jLabel_FechaEvento.setText("Fecha Evento");

        jLabel_IDLocation_EnPanelDeEvento.setText("ID Location");

        jLabel_IDArtista_enPanelEvento.setText("ID Artista");

        jComboBox_idLocation_panelEvento.setModel(new javax.swing.DefaultComboBoxModel());
        jComboBox_idLocation_panelEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_idLocation_panelEventoActionPerformed(evt);
            }
        });

        jComboBox_idArtista_panelEvento.setModel(new javax.swing.DefaultComboBoxModel());

        jButton_CrearEvento.setText(" Crear Evento");
        jButton_CrearEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CrearEventoActionPerformed(evt);
            }
        });

        jButton_eliminarEvento.setText("Eliminar Evento");
        jButton_eliminarEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_eliminarEventoActionPerformed(evt);
            }
        });

        jButton_ModificarEvento.setText("Modificar Evento");
        jButton_ModificarEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ModificarEventoActionPerformed(evt);
            }
        });

        jButton_LimpiarEventos.setText("Limpiar");

        jLabel2.setText("Administrar Eventos");

        javax.swing.GroupLayout jPanel_DatosdelEventoLayout = new javax.swing.GroupLayout(jPanel_DatosdelEvento);
        jPanel_DatosdelEvento.setLayout(jPanel_DatosdelEventoLayout);
        jPanel_DatosdelEventoLayout.setHorizontalGroup(
            jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DatosdelEventoLayout.createSequentialGroup()
                .addGroup(jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_DatosdelEventoLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel_NombreEvento_panelEvento, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addComponent(jLabel_FechaEvento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel_DatosdelEventoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton_CrearEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_ModificarEvento, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(jTextField_NombreEvento)
                            .addComponent(jTextField_FechaEvento))))
                .addGroup(jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_DatosdelEventoLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_DatosdelEventoLayout.createSequentialGroup()
                                .addComponent(jButton_eliminarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_LimpiarEventos))
                            .addGroup(jPanel_DatosdelEventoLayout.createSequentialGroup()
                                .addComponent(jLabel_IDArtista_enPanelEvento)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox_idArtista_panelEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel_DatosdelEventoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_IDLocation_EnPanelDeEvento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_idLocation_panelEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_DatosdelEventoLayout.setVerticalGroup(
            jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DatosdelEventoLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_NombreEvento_panelEvento)
                    .addComponent(jTextField_NombreEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_IDArtista_enPanelEvento)
                    .addComponent(jComboBox_idArtista_panelEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_FechaEvento)
                    .addComponent(jTextField_FechaEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_IDLocation_EnPanelDeEvento)
                    .addComponent(jComboBox_idLocation_panelEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_DatosdelEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_CrearEvento)
                    .addComponent(jButton_eliminarEvento)
                    .addComponent(jButton_ModificarEvento)
                    .addComponent(jButton_LimpiarEventos))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 777, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 453, Short.MAX_VALUE)
        );

        jTable_Eventos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Evento", "Nombre", "Fecha evento", "ID Locación", "ID Artista", "Fecha creación"
            }
        ));
        jScrollPane2.setViewportView(jTable_Eventos);

        javax.swing.GroupLayout jPanel_EventoGlobalLayout = new javax.swing.GroupLayout(jPanel_EventoGlobal);
        jPanel_EventoGlobal.setLayout(jPanel_EventoGlobalLayout);
        jPanel_EventoGlobalLayout.setHorizontalGroup(
            jPanel_EventoGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_EventoGlobalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_EventoGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_DatosdelEvento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel_EventoGlobalLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1054, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_EventoGlobalLayout.setVerticalGroup(
            jPanel_EventoGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_EventoGlobalLayout.createSequentialGroup()
                .addComponent(jPanel_DatosdelEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_EventoGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_EventoGlobalLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)))
        );

        jTabbedPane1.addTab("Eventos", jPanel_EventoGlobal);

        jLabel_generarReporte.setText("Generar Reporte:");

        jButton_generarReporteConciertosProximos.setText("Ver proximos conciertos");
        jButton_generarReporteConciertosProximos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_generarReporteConciertosProximosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_reporteGlobalLayout = new javax.swing.GroupLayout(jPanel_reporteGlobal);
        jPanel_reporteGlobal.setLayout(jPanel_reporteGlobalLayout);
        jPanel_reporteGlobalLayout.setHorizontalGroup(
            jPanel_reporteGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_reporteGlobalLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel_generarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_generarReporteConciertosProximos, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(665, Short.MAX_VALUE))
        );
        jPanel_reporteGlobalLayout.setVerticalGroup(
            jPanel_reporteGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_reporteGlobalLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel_reporteGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_generarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_generarReporteConciertosProximos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jTextArea_repotesDisplay.setColumns(20);
        jTextArea_repotesDisplay.setRows(5);
        jScrollPane_reporteDisplay.setViewportView(jTextArea_repotesDisplay);

        javax.swing.GroupLayout jPanel_reportLayout = new javax.swing.GroupLayout(jPanel_report);
        jPanel_report.setLayout(jPanel_reportLayout);
        jPanel_reportLayout.setHorizontalGroup(
            jPanel_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_reportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_reporteDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_reportLayout.setVerticalGroup(
            jPanel_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_reportLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane_reporteDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel_ReporteGlobalLayout = new javax.swing.GroupLayout(jPanel_ReporteGlobal);
        jPanel_ReporteGlobal.setLayout(jPanel_ReporteGlobalLayout);
        jPanel_ReporteGlobalLayout.setHorizontalGroup(
            jPanel_ReporteGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ReporteGlobalLayout.createSequentialGroup()
                .addGroup(jPanel_ReporteGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ReporteGlobalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel_reporteGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_ReporteGlobalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel_report, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(869, Short.MAX_VALUE))
        );
        jPanel_ReporteGlobalLayout.setVerticalGroup(
            jPanel_ReporteGlobalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ReporteGlobalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_reporteGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel_report, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Reportes", jPanel_ReporteGlobal);

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(1768, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButton1)
                .addContainerGap(620, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Auditoria", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_borrarArtistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_borrarArtistaActionPerformed
    String input = JOptionPane.showInputDialog(
            this,
            "Ingrese el ID del artista que desea eliminar:",
            "Eliminar Artista",
            JOptionPane.QUESTION_MESSAGE
    );

    if (input == null) {
        return;
    }

    input = input.trim();
    if (input.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Debe ingresar un ID válido.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    try {
        int idArtista = Integer.parseInt(input);

        boolean eliminado = artistaController.eliminarArtista(idArtista);

        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Artista eliminado correctamente.");
            cargarArtistasEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el artista o no pudo eliminarse.",
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.",
                                      "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_jButton_borrarArtistaActionPerformed

    private void jTextField_FechaEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_FechaEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_FechaEventoActionPerformed

    private void jTextField_NombreArtistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_NombreArtistaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_NombreArtistaActionPerformed

    private void jButton_CrearEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CrearEventoActionPerformed
           try {
        String nombre = jTextField_NombreEvento.getText().trim();
        String fechaTexto = jTextField_FechaEvento.getText().trim();

        if (nombre.isEmpty() || fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        java.sql.Date fecha = java.sql.Date.valueOf(fechaTexto);

        // --- Obtener IDs correctamente con el parser universal ---
        int idLoc = extraerIdDesdeTexto((String) jComboBox_idLocation_panelEvento.getSelectedItem());
        int idArt = extraerIdDesdeTexto((String) jComboBox_idArtista_panelEvento.getSelectedItem());

        if (idLoc <= 0 || idArt <= 0) {
            JOptionPane.showMessageDialog(this, "Error leyendo ID de locación o artista.");
            return;
        }

        Evento evento = new Evento();
        evento.setNombre(nombre);
        evento.setFechaEvento(fecha);
        evento.setIdLocacion(idLoc);
        evento.setIdArtista(idArt);

        boolean insertado = eventoController.insertarEvento(evento);

        if (insertado) {
            JOptionPane.showMessageDialog(this, "Evento creado con éxito.");
            cargarEventosEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error creando evento.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    }//GEN-LAST:event_jButton_CrearEventoActionPerformed

    private void jButton_eliminarEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_eliminarEventoActionPerformed
        int fila = jTable_Eventos.getSelectedRow();

    if (fila < 0) {
        JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar un evento de la tabla para eliminar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    try {
        // Columna 0 = ID Evento (según el modelo de la tabla)
        int idEvento = Integer.parseInt(jTable_Eventos.getValueAt(fila, 0).toString());

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el evento con ID " + idEvento + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return; // el usuario canceló
        }

        boolean eliminado = eventoController.eliminarEvento(idEvento);

        if (eliminado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Evento eliminado correctamente."
            );
            cargarEventosEnTabla();
            limpiarCamposEvento();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el evento.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(
                this,
                "Error al eliminar el evento: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
        ex.printStackTrace();
    }
    }//GEN-LAST:event_jButton_eliminarEventoActionPerformed

    private void jComboBox_idLocation_panelEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_idLocation_panelEventoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_idLocation_panelEventoActionPerformed

    private void jButton_ModificarEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ModificarEventoActionPerformed
        try {
        int fila = jTable_Eventos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un evento para modificar.");
            return;
        }

        // Obtener ID del evento desde la tabla
        int idEvento = Integer.parseInt(jTable_Eventos.getValueAt(fila, 0).toString());

        String nombre = jTextField_NombreEvento.getText().trim();
        String fechaTexto = jTextField_FechaEvento.getText().trim();

        if (nombre.isEmpty() || fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        java.sql.Date fecha = java.sql.Date.valueOf(fechaTexto);

        // Obtener locación
        String locStr = (String) jComboBox_idLocation_panelEvento.getSelectedItem();
        int idLoc = extraerIdDesdeCombo(locStr);

        // Obtener artista
        String artStr = (String) jComboBox_idArtista_panelEvento.getSelectedItem();
        int idArt = extraerIdDesdeCombo(artStr);

        Evento evento = new Evento();
        evento.setIdEvento(idEvento);
        evento.setNombre(nombre);
        evento.setFechaEvento(fecha);
        evento.setIdLocacion(idLoc);
        evento.setIdArtista(idArt);

        boolean actualizado = eventoController.actualizarEvento(evento);

        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Evento actualizado correctamente.");
            cargarEventosEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar evento.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    }//GEN-LAST:event_jButton_ModificarEventoActionPerformed

    private void jButton_generarReporteConciertosProximosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_generarReporteConciertosProximosActionPerformed
        // TODO add your handling code here:
        
        try {
        // Limpiar el área de texto
        jTextArea_repotesDisplay.setText("");

        // Obtener datos desde el controller
        List<Evento> lista = eventoController.listarEventosProximos();

        if (lista.isEmpty()) {
            jTextArea_repotesDisplay.setText("No hay conciertos próximos.\n");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("======= CONCIERTOS PRÓXIMOS =======\n\n");

        for (Evento e : lista) {
            sb.append("ID Evento: ").append(e.getIdEvento()).append("\n");
            sb.append("Nombre: ").append(e.getNombre()).append("\n");
            sb.append("Fecha: ").append(e.getFechaEvento()).append("\n");
            sb.append("Artista: ").append(e.getNombreArtista()).append("\n");
            sb.append("Locación: ").append(e.getNombreLocacion()).append("\n");
            sb.append("---------------------------------------\n");
        }

        jTextArea_repotesDisplay.setText(sb.toString());

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
                "Error generando reporte: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    }//GEN-LAST:event_jButton_generarReporteConciertosProximosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindowGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindowGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindowGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindowGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            new MainWindowGUI().setVisible(true);
            }
        });
        
        
    }
    private void cargarArtistasEnTabla() {
    DefaultTableModel model = (DefaultTableModel) jTable_artistas.getModel();
    model.setRowCount(0);

    for (Artista artista : artistaController.listarArtistas()) {
        model.addRow(new Object[]{
            artista.getIdArtista(),
            artista.getNombre(),
            artista.getGeneroMusical(),
            artista.getPaisOrigen(),
            artista.getFechaCreacion()
        });
    }
}
    
    private void jButton_updatearArtistaActionPerformed(java.awt.event.ActionEvent evt) {                                                         
    int fila = jTable_artistas.getSelectedRow();
    
    if (fila < 0) {
        JOptionPane.showMessageDialog(this,
                "Debe seleccionar un artista de la tabla para actualizar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int id = Integer.parseInt(jTable_artistas.getValueAt(fila, 0).toString());
        String nombre = jTextField_NombreArtista.getText().trim();
        String genero = jTextField_GeneroArtista.getText().trim();
        String pais = jTextField_paisOrigenArtista.getText().trim();

        if (nombre.isEmpty() || genero.isEmpty() || pais.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }

        Artista artista = new Artista();
        artista.setIdArtista(id);
        artista.setNombre(nombre);
        artista.setGeneroMusical(genero);
        artista.setPaisOrigen(pais);

        if (artistaController.actualizarArtista(artista)) {
            JOptionPane.showMessageDialog(this, "Artista actualizado correctamente.");
            cargarArtistasEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el artista.");
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
                "Error al actualizar: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    limpiarCampos();
}
    
    private void jButton_insertarArtistaActionPerformed(java.awt.event.ActionEvent evt) {                                                         
    String nombre = jTextField_NombreArtista.getText().trim();
    String genero = jTextField_GeneroArtista.getText().trim();
    String pais = jTextField_paisOrigenArtista.getText().trim();

    if (nombre.isEmpty() || genero.isEmpty() || pais.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
        return;
    }

    Artista artista = new Artista();
    artista.setNombre(nombre);
    artista.setGeneroMusical(genero);
    artista.setPaisOrigen(pais);

    if (artistaController.insertarArtista(artista)) {
        JOptionPane.showMessageDialog(this, "Artista insertado exitosamente");
        cargarArtistasEnTabla();
        limpiarCampos();
    } else {
        JOptionPane.showMessageDialog(this, "Error al insertar artista");
    }
}
    
    private void limpiarCampos() {
    jTextField_NombreArtista.setText("");
    jTextField_GeneroArtista.setText("");
    jTextField_paisOrigenArtista.setText("");
}

    private void jButton_limpiarTextFieldsActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        limpiarCampos();
        
}
    



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_CrearEvento;
    private javax.swing.JButton jButton_LimpiarEventos;
    private javax.swing.JButton jButton_ModificarEvento;
    private javax.swing.JButton jButton_borrarArtista;
    private javax.swing.JButton jButton_eliminarEvento;
    private javax.swing.JButton jButton_generarReporteConciertosProximos;
    private javax.swing.JButton jButton_insertarArtista;
    private javax.swing.JButton jButton_limpiarTextFields;
    private javax.swing.JButton jButton_updatearArtista;
    private javax.swing.JComboBox<String> jComboBox_idArtista_panelEvento;
    private javax.swing.JComboBox<String> jComboBox_idLocation_panelEvento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_FechaEvento;
    private javax.swing.JLabel jLabel_IDArtista_enPanelEvento;
    private javax.swing.JLabel jLabel_IDLocation_EnPanelDeEvento;
    private javax.swing.JLabel jLabel_NombreEvento_panelEvento;
    private javax.swing.JLabel jLabel_generarReporte;
    private javax.swing.JLabel jLabel_genero;
    private javax.swing.JLabel jLabel_nombre;
    private javax.swing.JLabel jLabel_paisDeOrigen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel_ArtistaGlobal;
    private javax.swing.JPanel jPanel_DatosdelArtista;
    private javax.swing.JPanel jPanel_DatosdelEvento;
    private javax.swing.JPanel jPanel_EventoGlobal;
    private javax.swing.JPanel jPanel_ReporteGlobal;
    private javax.swing.JPanel jPanel_inicioGlobal;
    private javax.swing.JPanel jPanel_report;
    private javax.swing.JPanel jPanel_reporteGlobal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane_panelDetabla;
    private javax.swing.JScrollPane jScrollPane_reporteDisplay;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_Eventos;
    private javax.swing.JTable jTable_artistas;
    private javax.swing.JTextArea jTextArea_InicioDescripcion;
    private javax.swing.JTextArea jTextArea_repotesDisplay;
    private javax.swing.JTextField jTextField_FechaEvento;
    private javax.swing.JTextField jTextField_GeneroArtista;
    private javax.swing.JTextField jTextField_NombreArtista;
    private javax.swing.JTextField jTextField_NombreEvento;
    private javax.swing.JTextField jTextField_paisOrigenArtista;
    private javax.swing.JLabel jlabel_tituloInicio;
    // End of variables declaration//GEN-END:variables
}
