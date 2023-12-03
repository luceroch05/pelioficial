/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package peaches.pelioficial.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import peaches.pelioficial.model.Actor;
import peaches.pelioficial.model.Director;
import peaches.pelioficial.model.Genero;
import peaches.pelioficial.model.Socio;
import peaches.pelioficial.model.SocioTableModel;
import peaches.pelioficial.service.ActorService;
import peaches.pelioficial.service.DirectorService;
import peaches.pelioficial.service.PeliculaService;
import peaches.pelioficial.service.SocioService;
import peaches.pelioficial.util.DatabaseConnector;
import peaches.pelioficial.util.ImageUtils;
import peaches.pelioficial.util.Placeholders;
import peaches.pelioficial.util.UIUtils;
import peaches.pelioficial.util.ValidadorFormulario;

/**
 *
 * @author Lucero
 */
public class panelMenu extends javax.swing.JPanel {
        int xMouse,yMouse;
        private SocioTableModel tableModel;
        SocioService socioService = new SocioService();
        PeliculaService peliculaService = new PeliculaService(DatabaseConnector.conectar());
        DirectorService directorService = new DirectorService(DatabaseConnector.conectar());
        ActorService actorService = new ActorService(DatabaseConnector.conectar());
        private framePrincipal framePrincipal;
        
    /**
     * Creates new form panelMenu
     */
    public panelMenu(framePrincipal framePrincipal) {
        initComponents();
        initTable();
        cargarDirectores();
        cargarActores();
        rellenarCboGenero();
        this.framePrincipal = framePrincipal;
     

        // Formatear la fecha como una cadena
     

        tableSocios.addMouseListener(new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent Mouse_evt)
        {
            JTable table = (JTable) Mouse_evt.getSource();
            Point point = Mouse_evt.getPoint();
            int row = table.rowAtPoint(point);
            if(Mouse_evt.getClickCount()==1)
            {
                txtCodSocio.setText(tableSocios.getValueAt(tableSocios.getSelectedRow(),0).toString());
                actualizarFecha();
            }
            
        }
        
        });
    }
        
    
        private void actualizarFecha() {
        // Obtener la fecha actual
           Date fechaActual = new Date(System.currentTimeMillis());

        // Formatear la fecha como una cadena
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = formatoFecha.format(fechaActual);

        // Establecer la fecha en el JTextField
        txtFechaPrestamo.setText(fechaFormateada);
    }

    public void cargarDirectores(){
        DirectorService directorService = new DirectorService(DatabaseConnector.conectar());
        List<Director> directores = directorService.obtenerTodosLosDirectores();
        for(Director director : directores){
            comboBoxDirectores.addItem(director.getNombre());
        }
    }
    
    public void cargarActores(){
        ActorService actorService = new ActorService(DatabaseConnector.conectar());
        List<Actor> actores = actorService.obtenerTodosLosActores();
        for(Actor actor : actores){
            comboBoxActores.addItem(actor.getNombre());
        }
    }
    
    private void initTable(){
        List<Socio> socios = socioService.obtenerTodosLosSocios();
        tableModel = new SocioTableModel(socios);
        tableSocios.setModel(tableModel);
    }
    
    public void actualizarVista(){
        List<Socio> sociosActualizados = socioService.obtenerTodosLosSocios();
        tableModel.setSocios(sociosActualizados);
    }
    
    public JTabbedPane getTabbedPane(){
        return tabbedPane;
    }
    
    private void rellenarCboGenero(){
        try{
           List<Genero> generos = peliculaService.obtenerTodosLosGeneros();
           for(Genero genero : generos){
               comboBoxGeneros.addItem(genero.getNombre());
           }
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los generos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void actualizarTablaDirectores(){
        List<Director> directores = directorService.obtenerTodosLosDirectores();
        DefaultTableModel model = (DefaultTableModel) tableDirectores.getModel();
        model.setRowCount(0);
        for(Director director : directores){
            model.addRow(new Object[]{director.getDirectorId(), director.getNombre()});
        }
    }
    
    public void actualizarTablaActores(){
        List<Actor> actores = actorService.obtenerTodosLosActores();
        DefaultTableModel model = (DefaultTableModel) tableActores.getModel();
        model.setRowCount(0);
        for(Actor actor : actores){
            model.addRow(new Object[]{actor.getActorId(), actor.getNombre()});
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnDirectores = new javax.swing.JButton();
        btnregistrarsocio = new javax.swing.JButton();
        btnprestaciones = new javax.swing.JButton();
        btndevoluciones = new javax.swing.JButton();
        btnpeliprestadas = new javax.swing.JButton();
        btnActores = new javax.swing.JButton();
        panelBarra = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tabbedPane = new javax.swing.JTabbedPane();
        pRegistrarSocio = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRegistrarSocio = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        txtTelefono = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        txtNombre = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        comboBoxDirectores = new javax.swing.JComboBox<>();
        comboBoxActores = new javax.swing.JComboBox<>();
        comboBoxGeneros = new javax.swing.JComboBox<>();
        pPrestaciones = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCodSocio = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtFechaPrestamo = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        txtFechaDevolucion = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        pDevoluciones = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtIdPrestamo = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        txtFechaEntrega = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        pSocio = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSocios = new javax.swing.JTable();
        btnEliminarSocio = new javax.swing.JButton();
        pDirectores = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNombreDirector = new javax.swing.JTextField();
        btnAgregarDirector = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDirectores = new javax.swing.JTable();
        btnEditarDirector = new javax.swing.JButton();
        btnEliminarDirector = new javax.swing.JButton();
        txtBuscarDirector = new javax.swing.JTextField();
        btnBuscarDirector = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        pActores = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtNombreActor = new javax.swing.JTextField();
        txtBuscarActor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableActores = new javax.swing.JTable();
        btnAgregarActor = new javax.swing.JButton();
        btnBuscarActor = new javax.swing.JButton();
        btnEditarActor = new javax.swing.JButton();
        btnEliminarActor = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField1.setText("jTextField1");

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDirectores.setBackground(new java.awt.Color(0, 0, 0));
        btnDirectores.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnDirectores.setForeground(new java.awt.Color(255, 255, 255));
        btnDirectores.setText("DIRECTORES");
        btnDirectores.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnDirectores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDirectoresMouseClicked(evt);
            }
        });
        jPanel1.add(btnDirectores, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 40));

        btnregistrarsocio.setBackground(new java.awt.Color(0, 0, 0));
        btnregistrarsocio.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnregistrarsocio.setForeground(new java.awt.Color(255, 255, 255));
        btnregistrarsocio.setText("REGISTRAR SOCIO");
        btnregistrarsocio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnregistrarsocio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnregistrarsocioMouseClicked(evt);
            }
        });
        jPanel1.add(btnregistrarsocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 250, 40));

        btnprestaciones.setBackground(new java.awt.Color(0, 0, 0));
        btnprestaciones.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnprestaciones.setForeground(new java.awt.Color(255, 255, 255));
        btnprestaciones.setText("PRESTACIONES");
        btnprestaciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnprestaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnprestacionesMouseClicked(evt);
            }
        });
        jPanel1.add(btnprestaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 250, 40));

        btndevoluciones.setBackground(new java.awt.Color(0, 0, 0));
        btndevoluciones.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btndevoluciones.setForeground(new java.awt.Color(255, 255, 255));
        btndevoluciones.setText("DEVOLUCIONES");
        btndevoluciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btndevoluciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndevolucionesMouseClicked(evt);
            }
        });
        jPanel1.add(btndevoluciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 40));

        btnpeliprestadas.setBackground(new java.awt.Color(0, 0, 0));
        btnpeliprestadas.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnpeliprestadas.setForeground(new java.awt.Color(255, 255, 255));
        btnpeliprestadas.setText("SOCIOS");
        btnpeliprestadas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnpeliprestadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnpeliprestadasMouseClicked(evt);
            }
        });
        jPanel1.add(btnpeliprestadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 40));

        btnActores.setBackground(new java.awt.Color(0, 0, 0));
        btnActores.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnActores.setForeground(new java.awt.Color(255, 255, 255));
        btnActores.setText("ACTORES");
        btnActores.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnActores.setMaximumSize(new java.awt.Dimension(84, 22));
        btnActores.setMinimumSize(new java.awt.Dimension(84, 22));
        btnActores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActoresMouseClicked(evt);
            }
        });
        jPanel1.add(btnActores, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, 40));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 16, 250, 510));

        panelBarra.setBackground(new java.awt.Color(0, 0, 0));
        panelBarra.setPreferredSize(new java.awt.Dimension(920, 18));
        panelBarra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelBarraMouseDragged(evt);
            }
        });
        panelBarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelBarraMousePressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs/Imgs Login/closeButton.png"))); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBarraLayout = new javax.swing.GroupLayout(panelBarra);
        panelBarra.setLayout(panelBarraLayout);
        panelBarraLayout.setHorizontalGroup(
            panelBarraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBarraLayout.createSequentialGroup()
                .addGap(0, 900, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelBarraLayout.setVerticalGroup(
            panelBarraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        add(panelBarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 20));

        tabbedPane.setBackground(new java.awt.Color(255, 204, 204));

        pRegistrarSocio.setBackground(new java.awt.Color(255, 255, 255));
        pRegistrarSocio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel1.setText("Nombre:");
        pRegistrarSocio.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setText("Dirección:");
        pRegistrarSocio.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, -1, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setText("Teléfono:");
        pRegistrarSocio.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setText("Directores favoritos:");
        pRegistrarSocio.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        btnRegistrarSocio.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarSocio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnRegistrarSocio.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarSocio.setText("Registrar");
        btnRegistrarSocio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrarSocio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistrarSocioMouseClicked(evt);
            }
        });
        btnRegistrarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarSocioActionPerformed(evt);
            }
        });
        pRegistrarSocio.add(btnRegistrarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 410, 250, 40));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel7MouseDragged(evt);
            }
        });
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel7MousePressed(evt);
            }
        });
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 10, 20));

        pRegistrarSocio.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 60, 30));

        txtDireccion.setForeground(new java.awt.Color(204, 204, 204));
        txtDireccion.setText("Av. Del Coso 123");
        txtDireccion.setToolTipText("");
        txtDireccion.setBorder(null);
        txtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDireccionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDireccionFocusLost(evt);
            }
        });
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        pRegistrarSocio.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 410, 30));
        txtDireccion.setForeground(new Color(204, 204, 204));
        txtDireccion.addFocusListener(new Placeholders("Av. Del Coso 123", new Color(204, 204, 204), Color.BLACK));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        pRegistrarSocio.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 550, 10));

        txtTelefono.setForeground(new java.awt.Color(204, 204, 204));
        txtTelefono.setText("987654321");
        txtTelefono.setBorder(null);
        txtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefonoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelefonoFocusLost(evt);
            }
        });
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        pRegistrarSocio.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 460, 30));
        txtTelefono.setForeground(new Color(204, 204, 204));
        txtTelefono.addFocusListener(new Placeholders("987654321", new Color(204, 204, 204), Color.BLACK));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        pRegistrarSocio.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 550, 10));

        txtNombre.setForeground(new java.awt.Color(204, 204, 204));
        txtNombre.setText("Introduzca su nombre");
        txtNombre.setBorder(null);
        txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNombreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreFocusLost(evt);
            }
        });
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        pRegistrarSocio.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 310, 30));
        txtNombre.setForeground(new Color(204, 204, 204));
        txtNombre.addFocusListener(new Placeholders("Introduzca su nombre", new Color(204, 204, 204), Color.BLACK));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        pRegistrarSocio.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 540, 10));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel11.setText("Actores favoritos:");
        pRegistrarSocio.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, -1));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel12.setText("Géneros preferidos:");
        pRegistrarSocio.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        pRegistrarSocio.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 550, 10));

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        pRegistrarSocio.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 550, 10));

        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        pRegistrarSocio.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, 550, 10));

        comboBoxDirectores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxDirectoresActionPerformed(evt);
            }
        });
        pRegistrarSocio.add(comboBoxDirectores, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 230, -1));

        pRegistrarSocio.add(comboBoxActores, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, 230, -1));

        pRegistrarSocio.add(comboBoxGeneros, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, 230, -1));

        tabbedPane.addTab("tab1", pRegistrarSocio);

        pPrestaciones.setBackground(new java.awt.Color(255, 255, 255));
        pPrestaciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setText("Código Socio:");
        pPrestaciones.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 110, 20));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setText("Fecha de Préstamo");
        pPrestaciones.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 150, 20));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel10.setText("Fecha de Devolución");
        pPrestaciones.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 160, 20));

        txtCodSocio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodSocio.setForeground(new java.awt.Color(204, 204, 204));
        txtCodSocio.setText("Codigo");
        txtCodSocio.setBorder(null);
        txtCodSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodSocioActionPerformed(evt);
            }
        });
        pPrestaciones.add(txtCodSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 160, 30));
        txtCodSocio.setForeground(Color.GRAY);
        txtCodSocio.addFocusListener(new Placeholders("Codigo", new Color(204, 204, 204), Color.BLACK));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        pPrestaciones.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 210, 20));

        txtFechaPrestamo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFechaPrestamo.setForeground(new java.awt.Color(204, 204, 204));
        txtFechaPrestamo.setText("dd/mm/yy");
        txtFechaPrestamo.setBorder(null);
        txtFechaPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaPrestamoActionPerformed(evt);
            }
        });
        pPrestaciones.add(txtFechaPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 160, 30));
        txtFechaPrestamo.setForeground(Color.GRAY);
        txtFechaPrestamo.addFocusListener(new Placeholders("dd/mm/yy", new Color(204, 204, 204), Color.BLACK));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        pPrestaciones.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 210, 20));

        txtFechaDevolucion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFechaDevolucion.setForeground(new java.awt.Color(204, 204, 204));
        txtFechaDevolucion.setText("dd/mm/yy");
        txtFechaDevolucion.setBorder(null);
        txtFechaDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaDevolucionActionPerformed(evt);
            }
        });
        pPrestaciones.add(txtFechaDevolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 160, 30));
        txtFechaDevolucion.setForeground(Color.GRAY);
        txtFechaDevolucion.addFocusListener(new Placeholders("dd/mm/yy", new Color(204, 204, 204), Color.BLACK));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        pPrestaciones.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 260, 210, 20));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("VER CARTELERA");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pPrestaciones.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 220, 30));

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("SOLICITAR");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        pPrestaciones.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, 220, 30));

        tabbedPane.addTab("tab2", pPrestaciones);

        pDevoluciones.setBackground(new java.awt.Color(255, 255, 255));
        pDevoluciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel13.setText("ID Prestamo");
        pDevoluciones.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 110, 20));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel14.setText("Fecha de Entrega:");
        pDevoluciones.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 150, 20));

        txtIdPrestamo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdPrestamo.setForeground(new java.awt.Color(204, 204, 204));
        txtIdPrestamo.setText("Codigo");
        txtIdPrestamo.setBorder(null);
        txtIdPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPrestamoActionPerformed(evt);
            }
        });
        pDevoluciones.add(txtIdPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 160, 30));
        txtIdPrestamo.setForeground(Color.GRAY);
        txtIdPrestamo.addFocusListener(new Placeholders("Codigo", new Color(204, 204, 204), Color.BLACK));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        pDevoluciones.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 210, 20));

        txtFechaEntrega.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFechaEntrega.setForeground(new java.awt.Color(204, 204, 204));
        txtFechaEntrega.setText("dd/mm/yy");
        txtFechaEntrega.setBorder(null);
        txtFechaEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaEntregaActionPerformed(evt);
            }
        });
        pDevoluciones.add(txtFechaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 160, 30));
        txtFechaEntrega.setForeground(Color.GRAY);
        txtFechaEntrega.addFocusListener(new Placeholders("dd/mm/yy", new Color(204, 204, 204), Color.BLACK));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        pDevoluciones.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 210, 20));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("SOLICITAR");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pDevoluciones.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 220, 30));

        tabbedPane.addTab("tab3", pDevoluciones);

        pSocio.setBackground(new java.awt.Color(255, 255, 255));

        tableSocios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tableSocios.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tableSocios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "IDSocio", "Nombre", "Dirección", "Teléfono", "Director favorito", "Actor favorito", "Género favorito"
            }
        ));
        jScrollPane1.setViewportView(tableSocios);

        btnEliminarSocio.setText("Eliminar");
        btnEliminarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarSocioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pSocioLayout = new javax.swing.GroupLayout(pSocio);
        pSocio.setLayout(pSocioLayout);
        pSocioLayout.setHorizontalGroup(
            pSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pSocioLayout.createSequentialGroup()
                .addGroup(pSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pSocioLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pSocioLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(btnEliminarSocio)))
                .addContainerGap(241, Short.MAX_VALUE))
        );
        pSocioLayout.setVerticalGroup(
            pSocioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pSocioLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(btnEliminarSocio)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        tabbedPane.addTab("tab4", pSocio);

        pDirectores.setBackground(new java.awt.Color(255, 255, 255));
        pDirectores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Nombre:");
        pDirectores.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));
        pDirectores.add(txtNombreDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 240, -1));

        btnAgregarDirector.setText("Agregar");
        btnAgregarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDirectorActionPerformed(evt);
            }
        });
        pDirectores.add(btnAgregarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, -1));

        tableDirectores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDirectores.getColumnModel().getColumn(0).setMinWidth(0);
        tableDirectores.getColumnModel().getColumn(0).setMaxWidth(0);
        tableDirectores.getColumnModel().getColumn(0).setWidth(0);
        actualizarTablaDirectores();
        jScrollPane2.setViewportView(tableDirectores);

        pDirectores.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 400, 160));

        btnEditarDirector.setText("Editar");
        btnEditarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarDirectorActionPerformed(evt);
            }
        });
        pDirectores.add(btnEditarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        btnEliminarDirector.setText("Eliminar");
        btnEliminarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDirectorActionPerformed(evt);
            }
        });
        pDirectores.add(btnEliminarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, -1, -1));
        pDirectores.add(txtBuscarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 240, -1));

        btnBuscarDirector.setText("Buscar");
        btnBuscarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarDirectorActionPerformed(evt);
            }
        });
        pDirectores.add(btnBuscarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, -1, -1));

        jLabel15.setText("Buscar:");
        pDirectores.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        tabbedPane.addTab("tab5", pDirectores);

        pActores.setBackground(new java.awt.Color(255, 255, 255));
        pActores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setText("Nombre:");
        pActores.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        jLabel17.setText("Buscar:");
        pActores.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));
        pActores.add(txtNombreActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 280, -1));
        pActores.add(txtBuscarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 280, -1));

        tableActores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableActores.getColumnModel().getColumn(0).setMinWidth(0);
        tableActores.getColumnModel().getColumn(0).setMaxWidth(0);
        tableActores.getColumnModel().getColumn(0).setWidth(0);
        jScrollPane3.setViewportView(tableActores);
        actualizarTablaActores();

        pActores.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 480, 200));

        btnAgregarActor.setText("Agregar");
        btnAgregarActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActorActionPerformed(evt);
            }
        });
        pActores.add(btnAgregarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, -1, -1));

        btnBuscarActor.setText("Buscar");
        btnBuscarActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActorActionPerformed(evt);
            }
        });
        pActores.add(btnBuscarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, -1, -1));

        btnEditarActor.setText("Editar");
        btnEditarActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActorActionPerformed(evt);
            }
        });
        pActores.add(btnEditarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, -1, -1));

        btnEliminarActor.setText("Eliminar");
        btnEliminarActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActorActionPerformed(evt);
            }
        });
        pActores.add(btnEliminarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, -1, -1));

        tabbedPane.addTab("tab6", pActores);

        add(tabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, -20, 890, 550));
    }// </editor-fold>//GEN-END:initComponents

    private void btnregistrarsocioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnregistrarsocioMouseClicked
        tabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_btnregistrarsocioMouseClicked

    private void btnprestacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnprestacionesMouseClicked
        tabbedPane.setSelectedIndex(1);        // TODO add your handling code here:
    }//GEN-LAST:event_btnprestacionesMouseClicked

    private void btndevolucionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndevolucionesMouseClicked
         tabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_btndevolucionesMouseClicked

    private void btnDirectoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDirectoresMouseClicked
        tabbedPane.setSelectedIndex(4); 
        actualizarTablaDirectores();
    }//GEN-LAST:event_btnDirectoresMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void panelBarraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBarraMouseDragged
        int x = evt.getXOnScreen();
        int y  = evt.getYOnScreen();
        this.setLocation(x -xMouse, y- yMouse);
    }//GEN-LAST:event_panelBarraMouseDragged

    private void panelBarraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBarraMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_panelBarraMousePressed

    private void btnEliminarSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarSocioActionPerformed
        int selectedRow = tableSocios.getSelectedRow();
        if(selectedRow >= 0){
            int socioId = (int) tableModel.getValueAt(selectedRow, 0);
            socioService.eliminarSocio(socioId);
            actualizarVista();
        }else{
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un socio para eliminar.");
        }

    }//GEN-LAST:event_btnEliminarSocioActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtFechaEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaEntregaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaEntregaActionPerformed

    private void txtIdPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPrestamoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPrestamoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setLayout(new BorderLayout());
        framePrincipal.mostrarPanelCartelera();
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtFechaDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaDevolucionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaDevolucionActionPerformed

    private void txtFechaPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaPrestamoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaPrestamoActionPerformed

    private void txtCodSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodSocioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodSocioActionPerformed

    private void comboBoxDirectoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxDirectoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxDirectoresActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusLost

    }//GEN-LAST:event_txtNombreFocusLost

    private void txtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusGained

    }//GEN-LAST:event_txtNombreFocusGained

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtTelefonoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFocusLost

    }//GEN-LAST:event_txtTelefonoFocusLost

    private void txtTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFocusGained

    }//GEN-LAST:event_txtTelefonoFocusGained

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void txtDireccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusLost

    }//GEN-LAST:event_txtDireccionFocusLost

    private void txtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusGained

    }//GEN-LAST:event_txtDireccionFocusGained

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MousePressed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseDragged
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel7MouseDragged

    private void btnRegistrarSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarSocioActionPerformed
        //             panelCartelera pc= new panelCartelera();
        //
        //            // Cambia el layout del contenedor principal a BorderLayout
        //            this.setLayout(new BorderLayout());
        //
        //            // Remueve todos los componentes del contenedor principal
        //            this.removeAll();
        //
        //            // Agrega el panelMenu al centro del contenedor principal
        //            this.add(pc, BorderLayout.CENTER);
        //
        //            // Revalida y repinta el contenedor principal
        //            this.revalidate();
        //            this.repaint();
        
        
//        String nombre = txtNombre.getText().trim();
//        String direccion = txtDireccion.getText().trim();
//        String telefono = txtTelefono.getText().trim();
//        String placeholderNombre = "Introduzca su nombre";
//        String placeholderDireccion = "Av. Del Coso 123";
//        String placeholderTelefono = "987654321";
//
//        boolean esValido = ValidadorFormulario.validarRegistroSocio(nombre, direccion, telefono, placeholderNombre, placeholderDireccion, placeholderTelefono);
//
//        if(esValido){
//            Socio socio = new Socio();
//            socio.setNombre(txtNombre.getText());
//            socio.setDireccion(txtDireccion.getText());
//            socio.setTelefono(txtTelefono.getText());
//            socio.setDirectoresFavoritos((String)comboBoxDirectores.getSelectedItem());
//            socio.setActoresFavoritos((String)comboBoxActores.getSelectedItem());
//            socio.setGenerosPreferidos((String)comboBoxGeneros.getSelectedItem());
//
//            try {
//                socioService.registrarSocio(socio);
//                JTextField[] camposParaLimpiar = {txtNombre, txtDireccion, txtTelefono};
//                JComboBox[] camposComboParaLimpiar = {comboBoxActores, comboBoxDirectores, comboBoxGeneros};
//                UIUtils.mostrarMensajeExitoYLimpiarCampos(camposParaLimpiar, camposComboParaLimpiar, "El socio ha sido registrado con exito.", "Registro Exitoso");
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(this, "Error al resgistrar el socio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
    }//GEN-LAST:event_btnRegistrarSocioActionPerformed

    private void btnRegistrarSocioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarSocioMouseClicked

    }//GEN-LAST:event_btnRegistrarSocioMouseClicked

    private void btnpeliprestadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnpeliprestadasMouseClicked
        tabbedPane.setSelectedIndex(3);
        actualizarVista();
    }//GEN-LAST:event_btnpeliprestadasMouseClicked

    private void btnAgregarDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDirectorActionPerformed
        String nombre = txtNombreDirector.getText();
        if(!nombre.trim().isEmpty()){
            directorService.agregarDirector(nombre);
            actualizarTablaDirectores();
        }else{
            JOptionPane.showMessageDialog(null, "El nombre del director no puede estar vacio.");
        }
    }//GEN-LAST:event_btnAgregarDirectorActionPerformed

    private void btnBuscarDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarDirectorActionPerformed
        String nombre = txtBuscarDirector.getText();
        List<Director> directores = directorService.obtenerTodosLosDirectores();
        DefaultTableModel model = (DefaultTableModel) tableDirectores.getModel();
        model.setRowCount(0);
        
        for(Director director : directores){
            if(director.getNombre().toLowerCase().contains(nombre.toLowerCase())){
                model.addRow(new Object[]{director.getDirectorId(), director.getNombre()});
            }
        }
    }//GEN-LAST:event_btnBuscarDirectorActionPerformed

    private void btnEditarDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarDirectorActionPerformed
        int row = tableDirectores.getSelectedRow();
        if(row != -1){
            int directorId = Integer.parseInt(tableDirectores.getValueAt(row, 0).toString());
            String nuevoNombre = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del director: ", "Editar Director", JOptionPane.PLAIN_MESSAGE);
            if(nuevoNombre != null && !nuevoNombre.trim().isEmpty()){
                directorService.editarDirector(directorId, nuevoNombre);
                actualizarTablaDirectores();
            }else{
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un director de la lista para editar.");
        }
    }//GEN-LAST:event_btnEditarDirectorActionPerformed

    private void btnEliminarDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDirectorActionPerformed
        int row = tableDirectores.getSelectedRow();
        if(row != -1){
            int confirmacion = JOptionPane.showConfirmDialog(null, "Está seguro de que desea eliminar este director?", "Eliminar Director", JOptionPane.YES_NO_OPTION);
            if(confirmacion == JOptionPane.YES_OPTION){
                int directorId = Integer.parseInt(tableDirectores.getValueAt(row, 0).toString());
                directorService.eliminarDirector(directorId);
                actualizarTablaDirectores();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un director de la lista para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarDirectorActionPerformed

    private void btnActoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActoresMouseClicked
        tabbedPane.setSelectedIndex(5); 
    }//GEN-LAST:event_btnActoresMouseClicked

    private void btnAgregarActorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActorActionPerformed
        String nombre = txtNombreActor.getText();
        if(!nombre.trim().isEmpty()){
            actorService.agregarActor(nombre);
            actualizarTablaActores();
        }else{
            JOptionPane.showMessageDialog(null, "El nombre del actor no puede estar vacio.");
        }
    }//GEN-LAST:event_btnAgregarActorActionPerformed

    private void btnBuscarActorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActorActionPerformed
        String nombre = txtBuscarActor.getText();
        List<Actor> actores = actorService.obtenerTodosLosActores();
        DefaultTableModel model = (DefaultTableModel) tableActores.getModel();
        model.setRowCount(0);
        
        for(Actor actor : actores){
            if(actor.getNombre().toLowerCase().contains(nombre.toLowerCase())){
                model.addRow(new Object[]{actor.getActorId(), actor.getNombre()});
            }
        }
    }//GEN-LAST:event_btnBuscarActorActionPerformed

    private void btnEditarActorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActorActionPerformed
        int row = tableActores.getSelectedRow();
        if(row != -1){
            int actorId = Integer.parseInt(tableActores.getValueAt(row, 0).toString());
            String nuevoNombre = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del actor: ", "Editar Actor", JOptionPane.PLAIN_MESSAGE);
            if(nuevoNombre != null && !nuevoNombre.trim().isEmpty()){
                actorService.editarActor(actorId, nuevoNombre);
                actualizarTablaActores();
            }else{
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un actor de la lista para editar.");
        }
    }//GEN-LAST:event_btnEditarActorActionPerformed

    private void btnEliminarActorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActorActionPerformed
        int row = tableActores.getSelectedRow();
        if(row != -1){
            int confirmacion = JOptionPane.showConfirmDialog(null, "Está seguro de que desea eliminar este actor?", "Eliminar Actor", JOptionPane.YES_NO_OPTION);
            if(confirmacion == JOptionPane.YES_OPTION){
                int actorId = Integer.parseInt(tableActores.getValueAt(row, 0).toString());
                actorService.eliminarActor(actorId);
                actualizarTablaActores();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un actor de la lista para eliminar.");
        }
    }//GEN-LAST:event_btnEliminarActorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActores;
    private javax.swing.JButton btnAgregarActor;
    private javax.swing.JButton btnAgregarDirector;
    private javax.swing.JButton btnBuscarActor;
    private javax.swing.JButton btnBuscarDirector;
    private javax.swing.JButton btnDirectores;
    private javax.swing.JButton btnEditarActor;
    private javax.swing.JButton btnEditarDirector;
    private javax.swing.JButton btnEliminarActor;
    private javax.swing.JButton btnEliminarDirector;
    private javax.swing.JButton btnEliminarSocio;
    private javax.swing.JButton btnRegistrarSocio;
    private javax.swing.JButton btndevoluciones;
    private javax.swing.JButton btnpeliprestadas;
    private javax.swing.JButton btnprestaciones;
    private javax.swing.JButton btnregistrarsocio;
    private javax.swing.JComboBox<String> comboBoxActores;
    private javax.swing.JComboBox<String> comboBoxDirectores;
    private javax.swing.JComboBox<String> comboBoxGeneros;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel pActores;
    private javax.swing.JPanel pDevoluciones;
    private javax.swing.JPanel pDirectores;
    private javax.swing.JPanel pPrestaciones;
    private javax.swing.JPanel pRegistrarSocio;
    private javax.swing.JPanel pSocio;
    private javax.swing.JPanel panelBarra;
    public javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tableActores;
    private javax.swing.JTable tableDirectores;
    private javax.swing.JTable tableSocios;
    private javax.swing.JTextField txtBuscarActor;
    private javax.swing.JTextField txtBuscarDirector;
    private javax.swing.JTextField txtCodSocio;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFechaDevolucion;
    private javax.swing.JTextField txtFechaEntrega;
    private javax.swing.JTextField txtFechaPrestamo;
    private javax.swing.JTextField txtIdPrestamo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreActor;
    private javax.swing.JTextField txtNombreDirector;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
