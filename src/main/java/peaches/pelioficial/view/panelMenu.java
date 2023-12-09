/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package peaches.pelioficial.view;
import java.util.Date;
import java.text.ParseException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import peaches.pelioficial.model.Actor;
import peaches.pelioficial.model.Cinta;
import peaches.pelioficial.model.Director;
import peaches.pelioficial.model.Genero;
import peaches.pelioficial.model.Pelicula;
import peaches.pelioficial.model.Socio;
import peaches.pelioficial.service.ActorService;
import peaches.pelioficial.service.CintaService;
import peaches.pelioficial.service.DirectorService;
import peaches.pelioficial.service.PeliculaService;
import peaches.pelioficial.service.SocioService;
import peaches.pelioficial.util.DatabaseConnector;
import peaches.pelioficial.util.Placeholders;
import peaches.pelioficial.util.SeleccionElementosDialog;
import peaches.pelioficial.util.SeleccionPeliculaDialog;

/**
 *
 * @author Lucero
 */
public class panelMenu extends javax.swing.JPanel {
        int xMouse,yMouse;
        SocioService socioService = new SocioService();
        PeliculaService peliculaService = new PeliculaService(DatabaseConnector.conectar());
        DirectorService directorService = new DirectorService(DatabaseConnector.conectar());
        ActorService actorService = new ActorService(DatabaseConnector.conectar());
        CintaService cintaService = new CintaService();
        private framePrincipal framePrincipal;
        
        private Set<Director> directoresSeleccionados = new HashSet<>();
        private Set<Actor> actoresSeleccionados = new HashSet<>();
        private Set<Genero> generosSeleccionados = new HashSet<>();
        private List<Director> listaDirectores = directorService.obtenerTodosLosDirectores();
        private List<Actor> listaActores = actorService.obtenerTodosLosActores();
        private List<Genero> listaGeneros = peliculaService.obtenerTodosLosGeneros();
        
    /**
     * Creates new form panelMenu
     */
    public panelMenu(framePrincipal framePrincipal) {
        initComponents();
//        initTable();
        rellenarComboBoxes();
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
    
    public JTabbedPane getTabbedPane(){
        return tabbedPane;
    }
    
    private void rellenarComboBoxes() {
        rellenarCboDirectores();
        rellenarCboActores();
        rellenarCboGeneros();
    }
    
    private void rellenarCboDirectores() {
        List<Director> directores = directorService.obtenerTodosLosDirectores();
        for (Director director : directores) {
            cboDirectoresPelicula.addItem(director);
        }
    }
    
    private void rellenarCboActores() {
        List<Actor> actores = actorService.obtenerTodosLosActores();
        for (Actor actor : actores) {
        }
    }
    
    private void rellenarCboGeneros() {
        List<Genero> generos = peliculaService.obtenerTodosLosGeneros();
        for (Genero genero : generos) {
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
    
    public void actualizarTablaSocios() {
        DefaultTableModel model = (DefaultTableModel) tableSocios.getModel();
        model.setRowCount(0);
        List<Socio> listaSocios = socioService.obtenerTodosLosSocios();
        for (Socio socio : listaSocios) {
            String directores = String.join(", ", socio.getDirectoresFavoritos().stream()
                                                       .map(Director::getNombre)
                                                       .collect(Collectors.toList()));
            String actores = String.join(", ", socio.getActoresFavoritos().stream()
                                                    .map(Actor::getNombre)
                                                    .collect(Collectors.toList()));
            String generos = String.join(", ", socio.getGenerosFavoritos().stream()
                                                    .map(Genero::getNombre)
                                                    .collect(Collectors.toList()));

            model.addRow(new Object[]{
                socio.getSocioId(),
                socio.getNombre(),
                socio.getDireccion(),
                socio.getTelefono(),
                directores,
                actores,
                generos
            });
        }
    }
    
    private void cargarDatosSocios(int idSocio){
        Socio socio = socioService.obtenerSocioId(idSocio);
        if(socio != null){
            txtIdSocio.setText(String.valueOf(socio.getSocioId()));
            txtNombreSocio.setText(socio.getNombre());
            txtNombreSocio.setForeground(Color.black);
            txtDireccionSocio.setText(socio.getDireccion());
              txtDireccionSocio.setForeground(Color.black);

            txtTelefonoSocio.setText(socio.getTelefono());
              txtTelefonoSocio.setForeground(Color.black);

        }else{
            JOptionPane.showMessageDialog(this, "Socio no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarTablaSociosConUnSocio(Socio socio) {
        DefaultTableModel model = (DefaultTableModel) tableSocios.getModel();
        model.setRowCount(0);

        String directores = socio.getDirectoresFavoritos().stream()
                                 .map(Director::getNombre)
                                 .collect(Collectors.joining(", "));
        String actores = socio.getActoresFavoritos().stream()
                              .map(Actor::getNombre)
                              .collect(Collectors.joining(", "));
        String generos = socio.getGenerosFavoritos().stream()
                              .map(Genero::getNombre)
                              .collect(Collectors.joining(", "));

        model.addRow(new Object[]{
            socio.getSocioId(),
            socio.getNombre(),
            socio.getDireccion(),
            socio.getTelefono(),
            directores,
            actores,
            generos
        });
    }
    
    private void limpiarTablaSocios(){
        DefaultTableModel model = (DefaultTableModel) tableSocios.getModel();
        model.setRowCount(0);
    } 
    
    public void actualizarTablaPeliculas(){
        DefaultTableModel modelo = (DefaultTableModel) tablePeliculas.getModel();
        modelo.setRowCount(0);
        List<Pelicula> listaPeliculas = peliculaService.obtenerTodasLasPeliculas();
        for(Pelicula pelicula : listaPeliculas){
            List<Genero> generos = peliculaService.obtenerGenerosPorPelicula(pelicula.getPeliculaId());
            String nombresGeneros = generos.stream()
                                    .map(Genero::getNombre)
                                    .collect(Collectors.joining(", "));
            modelo.addRow(new Object[]{
                pelicula.getPeliculaId(),
                pelicula.getTitulo(),
                pelicula.getDirector().getNombre(),
                nombresGeneros
            });
        }
    }
    
    private void rellenarFormularioParaEdicion(int filaSeleccionada){
        int peliculaId = (int) tablePeliculas.getValueAt(filaSeleccionada, 0);
        Pelicula pelicula = peliculaService.obtenerPeliculaPorId(peliculaId);
        Director director = pelicula.getDirector();
        List<Genero> generos = peliculaService.obtenerGenerosPorPelicula(peliculaId);
        
        txtIdPelicula.setText(String.valueOf(pelicula.getPeliculaId()));
        txtTituloPelicula.setText(pelicula.getTitulo());
        for(int i = 0; i < cboDirectoresPelicula.getItemCount(); i++){
            Director dir = (Director) cboDirectoresPelicula.getItemAt(i);
            if(dir != null && dir.getDirectorId() == director.getDirectorId()){
                cboDirectoresPelicula.setSelectedIndex(i);
                break;
            }
        }
        
        actualizarTablaPeliculas();
    }
    
    public void actualizarTablaPeliculasConBusqueda(String text){
        DefaultTableModel modelo = (DefaultTableModel) tablePeliculas.getModel();
        modelo.setRowCount(0);
        
        List<Pelicula> listaPeliculas = peliculaService.buscarPeliculasPorTitulo(text);
        for(Pelicula pelicula : listaPeliculas){
            List<Genero> generos = peliculaService.obtenerGenerosPorPelicula(pelicula.getPeliculaId());
            String nombresGeneros = generos.stream()
                                    .map(Genero::getNombre)
                                    .collect(Collectors.joining(", "));
            modelo.addRow(new Object[]{
                pelicula.getPeliculaId(),
                pelicula.getTitulo(),
                pelicula.getDirector().getNombre(),
                nombresGeneros
            });
        }
    }
    
    private void limpiarCamposPelicula(){
        txtIdPelicula.setText("");
        txtTituloPelicula.setText("");
        cboDirectoresPelicula.setSelectedIndex(0);
        lblGenerosSeleccionados.setText("");
    }
    
    private void filtrarTablaDirectores(String texto){
        List<Director> directoresFiltrados = directorService.obtenerTodosLosDirectores().stream()
                                                                                        .filter(d -> d.getNombre().toLowerCase().contains(texto.toLowerCase()))
                                                                                        .collect(Collectors.toList());
        
        DefaultTableModel model = (DefaultTableModel) tableDirectores.getModel();
        model.setRowCount(0);
        
        for(Director director : directoresFiltrados){
            model.addRow(new Object[]{
                director.getDirectorId(),
                director.getNombre()
            });
        }
    }
    
    private void filtrarTablaActores(String texto){
        List<Actor> actoresFiltrados = actorService.obtenerTodosLosActores().stream()
                                                                            .filter(a -> a.getNombre().toLowerCase().contains(texto.toLowerCase()))
                                                                            .collect(Collectors.toList());
        
        DefaultTableModel model = (DefaultTableModel) tableActores.getModel();
        model.setRowCount(0);
        
        for(Actor actor : actoresFiltrados){
            model.addRow(new Object[]{
                actor.getActorId(),
                actor.getNombre()
            });
        }
    }
    
    private void actualizarSeleccionDirectores(Set<Director> seleccion){
        directoresSeleccionados = seleccion;
        lblDirectoresSeleccionados.setText(seleccion.size() + " Directores seleccionados");
    }
    
    private void actualizarSeleccionActores(Set<Actor> seleccion){
        actoresSeleccionados = seleccion;
        lblActoresSeleccionados.setText(seleccion.size() + " Actores seleccionados");
    }
    
    private void actualizarSeleccionGeneros(Set<Genero> seleccion){
        generosSeleccionados = seleccion;
        lblGenerosSeleccionados.setText(seleccion.size() + " Generos seleccionados");
    }
    
    private void limpiarCamposFormularioSocio(){
        txtIdSocio.setText("");
        txtNombreSocio.setText("");
        txtDireccionSocio.setText("");
        txtTelefonoSocio.setText("");
        
        lblDirectoresSeleccionados.setText("");
        lblActoresSeleccionados.setText("");
        lblGenerosSeleccionados.setText("");
        
        directoresSeleccionados.clear();
        actoresSeleccionados.clear();
        generosSeleccionados.clear();
    }
    
    private List<Director> obtenerDirectoresSeleccionados() {
        return new ArrayList<>(directoresSeleccionados);
    }

    private List<Actor> obtenerActoresSeleccionados() {
        return new ArrayList<>(actoresSeleccionados);
    }

    private List<Genero> obtenerGenerosSeleccionados() {
        return new ArrayList<>(generosSeleccionados);
    }
    
    private Cinta obtenerCintaSeleccionada() {
        int filaSeleccionada = tableCintas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel model = (DefaultTableModel) tableCintas.getModel();

            // Asumiendo que el ID de la cinta está en la columna 0
            int cintaId = Integer.parseInt(model.getValueAt(filaSeleccionada, 0).toString());

            // Aquí deberías buscar la cinta en tu lista de cintas o base de datos usando el cintaId
            // Por ejemplo, podrías tener un método en tu CintaService que busque la cinta por su ID:
            Cinta cinta = cintaService.obtenerCintaPorId(cintaId);

            return cinta;
        } else {
            JOptionPane.showMessageDialog(null, "No hay ninguna cinta seleccionada.");
            return null;
        }
    }
    
    private Cinta obtenerCintaSeleccionadaDeLaTabla() {
        int filaSeleccionada = tableCintas.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Suponiendo que la primera columna es la del ID de la cinta
            int cintaId = (int) tableCintas.getValueAt(filaSeleccionada, 0);
            return cintaService.obtenerCintaPorId(cintaId);
        } else {
            return null;
        }
    }
    
    public void actualizarTablaCintas() {
        DefaultTableModel modelo = (DefaultTableModel) tableCintas.getModel();
        modelo.setRowCount(0); // Limpiar la tabla primero
        List<Cinta> listaCintas = cintaService.obtenerTodasLasCintas(); // Asegúrate de que este método devuelve la lista actualizada de cintas
        for (Cinta cinta : listaCintas) {
            Pelicula pelicula = peliculaService.obtenerPeliculaPorId(cinta.getPeliculaId()); // Suponiendo que tienes un método para obtener la película por su ID
            modelo.addRow(new Object[] {
                cinta.getCintaId(),
                pelicula != null ? pelicula.getTitulo() : "Desconocido", // Asegúrate de que la película no sea null
                cinta.getEstado()
            });
        }
    }

    
    public List<Pelicula> obtenerListaPeliculas() {
        return peliculaService.obtenerTodasLasPeliculas();
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
        btnActores = new javax.swing.JButton();
        btnPeliculas = new javax.swing.JButton();
        btnCintas = new javax.swing.JButton();
        panelBarra = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tabbedPane = new javax.swing.JTabbedPane();
        pSocios = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRegistrarSocio = new javax.swing.JButton();
        txtDireccionSocio = new javax.swing.JTextField();
        txtTelefonoSocio = new javax.swing.JTextField();
        txtNombreSocio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtIdSocio = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSocios = new javax.swing.JTable();
        btnEditarSocio = new javax.swing.JButton();
        btnEliminarSocio = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtBuscarSocio = new javax.swing.JTextField();
        btnDirectoresSocios = new javax.swing.JButton();
        btnActoresSocios = new javax.swing.JButton();
        btnGenerosSocios = new javax.swing.JButton();
        lblDirectoresSeleccionados = new javax.swing.JLabel();
        lblActoresSeleccionados = new javax.swing.JLabel();
        lblGenerosSeleccionados = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        pPrestaciones = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCodSocio = new javax.swing.JTextField();
        txtFechaPrestamo = new javax.swing.JTextField();
        txtFechaDevolucion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        pDevoluciones = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtIdPrestamo = new javax.swing.JTextField();
        txtFechaEntrega = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        pDirectores = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNombreDirector = new javax.swing.JTextField();
        btnAgregarDirector = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDirectores = new javax.swing.JTable();
        btnEditarDirector = new javax.swing.JButton();
        btnEliminarDirector = new javax.swing.JButton();
        txtBuscarDirector = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        pActores = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtNombreActor = new javax.swing.JTextField();
        txtBuscarActor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableActores = new javax.swing.JTable();
        btnAgregarActor = new javax.swing.JButton();
        btnEditarActor = new javax.swing.JButton();
        btnEliminarActor = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtIdCinta = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtPeliculaCinta = new javax.swing.JTextField();
        btnBuscarPeliculaCinta = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        cboEstadoCinta = new javax.swing.JComboBox<>();
        btnAgregarCinta = new javax.swing.JButton();
        btnEditarCinta = new javax.swing.JButton();
        btnEliminarCinta = new javax.swing.JButton();
        txtBuscarCinta = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCintas = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        pPeliculas = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtIdPelicula = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtTituloPelicula = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cboDirectoresPelicula = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        btnAbrirListaGeneros = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePeliculas = new javax.swing.JTable();
        btnAgregarPelicula = new javax.swing.JButton();
        btnEditarPelicula = new javax.swing.JButton();
        btnEliminarPelicula = new javax.swing.JButton();
        txtBuscarPelicula = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField1.setText("jTextField1");

        setMinimumSize(new java.awt.Dimension(1200, 640));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1200, 840));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDirectores.setBackground(new java.awt.Color(0, 0, 0));
        btnDirectores.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btnDirectores.setForeground(new java.awt.Color(255, 255, 255));
        btnDirectores.setText("DIRECTORES");
        btnDirectores.setBorder(null);
        btnDirectores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDirectoresMouseClicked(evt);
            }
        });
        jPanel1.add(btnDirectores, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 160, 30));

        btnregistrarsocio.setBackground(new java.awt.Color(0, 0, 0));
        btnregistrarsocio.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btnregistrarsocio.setForeground(new java.awt.Color(255, 255, 255));
        btnregistrarsocio.setText("SOCIO");
        btnregistrarsocio.setBorder(null);
        btnregistrarsocio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnregistrarsocioMouseClicked(evt);
            }
        });
        jPanel1.add(btnregistrarsocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 160, 30));

        btnprestaciones.setBackground(new java.awt.Color(0, 0, 0));
        btnprestaciones.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btnprestaciones.setForeground(new java.awt.Color(255, 255, 255));
        btnprestaciones.setText("PRESTACIONES");
        btnprestaciones.setBorder(null);
        btnprestaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnprestacionesMouseClicked(evt);
            }
        });
        jPanel1.add(btnprestaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 160, 30));

        btndevoluciones.setBackground(new java.awt.Color(0, 0, 0));
        btndevoluciones.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btndevoluciones.setForeground(new java.awt.Color(255, 255, 255));
        btndevoluciones.setText("DEVOLUCIONES");
        btndevoluciones.setBorder(null);
        btndevoluciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndevolucionesMouseClicked(evt);
            }
        });
        jPanel1.add(btndevoluciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 160, 30));

        btnActores.setBackground(new java.awt.Color(0, 0, 0));
        btnActores.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btnActores.setForeground(new java.awt.Color(255, 255, 255));
        btnActores.setText("ACTORES");
        btnActores.setBorder(null);
        btnActores.setMaximumSize(new java.awt.Dimension(84, 22));
        btnActores.setMinimumSize(new java.awt.Dimension(84, 22));
        btnActores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActoresMouseClicked(evt);
            }
        });
        jPanel1.add(btnActores, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 160, 30));

        btnPeliculas.setBackground(new java.awt.Color(0, 0, 0));
        btnPeliculas.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btnPeliculas.setForeground(new java.awt.Color(255, 255, 255));
        btnPeliculas.setText("PELICULAS");
        btnPeliculas.setBorder(null);
        btnPeliculas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPeliculasMouseClicked(evt);
            }
        });
        jPanel1.add(btnPeliculas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 160, 30));

        btnCintas.setBackground(new java.awt.Color(0, 0, 0));
        btnCintas.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btnCintas.setForeground(new java.awt.Color(255, 255, 255));
        btnCintas.setText("CINTAS");
        btnCintas.setBorder(null);
        btnCintas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCintasMouseClicked(evt);
            }
        });
        jPanel1.add(btnCintas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 160, 30));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 16, 160, 830));

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
                .addGap(0, 1180, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelBarraLayout.setVerticalGroup(
            panelBarraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        add(panelBarra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 20));

        tabbedPane.setBackground(new java.awt.Color(255, 204, 204));

        pSocios.setBackground(new java.awt.Color(255, 255, 255));
        pSocios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel1.setText("NOMBRE");
        pSocios.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 80, 30));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel2.setText("DIRECCIÓN");
        pSocios.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 160, 30));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel3.setText("TELÉFONO");
        pSocios.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 120, 30));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel4.setText("DIRECTORES FAVORITOS");
        pSocios.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, 230, 30));

        btnRegistrarSocio.setBackground(new java.awt.Color(90, 122, 25));
        btnRegistrarSocio.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnRegistrarSocio.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarSocio.setText("Registrar");
        btnRegistrarSocio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarSocioActionPerformed(evt);
            }
        });
        pSocios.add(btnRegistrarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 400, 210, 40));

        txtDireccionSocio.setBackground(new java.awt.Color(245, 245, 245));
        txtDireccionSocio.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        txtDireccionSocio.setForeground(new java.awt.Color(204, 204, 204));
        txtDireccionSocio.setText("  Av. Del Coso 123");
        txtDireccionSocio.setToolTipText("");
        txtDireccionSocio.setBorder(null);
        txtDireccionSocio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDireccionSocioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDireccionSocioFocusLost(evt);
            }
        });
        txtDireccionSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionSocioActionPerformed(evt);
            }
        });
        pSocios.add(txtDireccionSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 220, 30));
        txtDireccionSocio.setForeground(new Color(204, 204, 204));
        txtDireccionSocio.addFocusListener(new Placeholders("Av. Del Coso 123", new Color(204, 204, 204), Color.BLACK));

        txtTelefonoSocio.setBackground(new java.awt.Color(245, 245, 245));
        txtTelefonoSocio.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        txtTelefonoSocio.setForeground(new java.awt.Color(204, 204, 204));
        txtTelefonoSocio.setText("  987654321");
        txtTelefonoSocio.setBorder(null);
        txtTelefonoSocio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefonoSocioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelefonoSocioFocusLost(evt);
            }
        });
        txtTelefonoSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoSocioActionPerformed(evt);
            }
        });
        txtTelefonoSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoSocioKeyTyped(evt);
            }
        });
        pSocios.add(txtTelefonoSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 220, 30));
        txtTelefonoSocio.setForeground(new Color(204, 204, 204));
        txtTelefonoSocio.addFocusListener(new Placeholders("987654321", new Color(204, 204, 204), Color.BLACK));

        txtNombreSocio.setBackground(new java.awt.Color(245, 245, 245));
        txtNombreSocio.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        txtNombreSocio.setForeground(new java.awt.Color(204, 204, 204));
        txtNombreSocio.setText("  Introduzca su nombre");
        txtNombreSocio.setBorder(null);
        txtNombreSocio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNombreSocioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreSocioFocusLost(evt);
            }
        });
        txtNombreSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreSocioActionPerformed(evt);
            }
        });
        txtNombreSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreSocioKeyTyped(evt);
            }
        });
        pSocios.add(txtNombreSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 220, 30));
        txtNombreSocio.setForeground(new Color(204, 204, 204));
        txtNombreSocio.addFocusListener(new Placeholders("Introduzca su nombre", new Color(204, 204, 204), Color.BLACK));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel11.setText("ACTORES FAVORITOS");
        pSocios.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, 210, 30));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel12.setText("GÉNEROS PREFERIDOS");
        pSocios.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 320, 220, 30));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Poppins Black", 0, 48)); // NOI18N
        jLabel18.setText("SOCIOS");
        pSocios.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 190, 40));

        txtIdSocio.setEditable(false);
        pSocios.add(txtIdSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 100, 30));

        tableSocios.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tableSocios.getTableHeader().setBackground(Color.black);
        tableSocios.getTableHeader().setForeground(Color.white);
        tableSocios.getTableHeader().setFont(new java.awt.Font("Poppins", 0, 12));
        tableSocios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "IDSocio", "Nombre", "Dirección", "Teléfono", "Directores favorito", "Actores favorito", "Generos favoritos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSocios.setMinimumSize(new java.awt.Dimension(105, 70));
        tableSocios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSociosMouseClicked(evt);
            }
        });
        tableSocios.getColumnModel().getColumn(0).setMinWidth(60);
        tableSocios.getColumnModel().getColumn(0).setMaxWidth(60);
        tableSocios.getColumnModel().getColumn(0).setWidth(60);
        jScrollPane1.setViewportView(tableSocios);
        actualizarTablaSocios();

        pSocios.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 880, 200));

        btnEditarSocio.setBackground(new java.awt.Color(64, 83, 121));
        btnEditarSocio.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnEditarSocio.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarSocio.setText("Editar");
        btnEditarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarSocioActionPerformed(evt);
            }
        });
        pSocios.add(btnEditarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, 200, 40));

        btnEliminarSocio.setBackground(new java.awt.Color(119, 45, 45));
        btnEliminarSocio.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnEliminarSocio.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarSocio.setText("Eliminar");
        btnEliminarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarSocioActionPerformed(evt);
            }
        });
        pSocios.add(btnEliminarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 180, 40));

        jLabel19.setFont(new java.awt.Font("Poppins SemiBold", 1, 18)); // NOI18N
        pSocios.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 160, 40));

        txtBuscarSocio.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        txtBuscarSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarSocioKeyReleased(evt);
            }
        });
        pSocios.add(txtBuscarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 480, 80, 30));

        btnDirectoresSocios.setBackground(new java.awt.Color(51, 51, 51));
        btnDirectoresSocios.setFont(new java.awt.Font("Poppins ExtraBold", 0, 14)); // NOI18N
        btnDirectoresSocios.setForeground(new java.awt.Color(255, 255, 255));
        btnDirectoresSocios.setText("Elegir Directores");
        btnDirectoresSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectoresSociosActionPerformed(evt);
            }
        });
        pSocios.add(btnDirectoresSocios, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, 180, 30));

        btnActoresSocios.setBackground(new java.awt.Color(51, 51, 51));
        btnActoresSocios.setFont(new java.awt.Font("Poppins ExtraBold", 0, 14)); // NOI18N
        btnActoresSocios.setForeground(new java.awt.Color(255, 255, 255));
        btnActoresSocios.setText("Elegir Actores");
        btnActoresSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActoresSociosActionPerformed(evt);
            }
        });
        pSocios.add(btnActoresSocios, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 260, 180, 30));

        btnGenerosSocios.setBackground(new java.awt.Color(51, 51, 51));
        btnGenerosSocios.setFont(new java.awt.Font("Poppins ExtraBold", 0, 14)); // NOI18N
        btnGenerosSocios.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerosSocios.setText("Elegir Generos");
        btnGenerosSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerosSociosActionPerformed(evt);
            }
        });
        pSocios.add(btnGenerosSocios, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 320, 180, 30));
        pSocios.add(lblDirectoresSeleccionados, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, -1, -1));
        pSocios.add(lblActoresSeleccionados, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, -1, -1));
        pSocios.add(lblGenerosSeleccionados, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 280, -1, -1));

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
        pSocios.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 60, 10, 20));

        jLabel26.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel26.setText("ID SOCIO");
        pSocios.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 90, 20));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs/Imgs Login/lupa (2).png"))); // NOI18N
        pSocios.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 480, 30, 30));

        tabbedPane.addTab("tab1", pSocios);

        pPrestaciones.setBackground(new java.awt.Color(255, 255, 255));
        pPrestaciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel8.setText("CÓDIGO SOCIO");
        pPrestaciones.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 170, 20));

        jLabel9.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel9.setText("FECHA  PRÉSTAMO");
        pPrestaciones.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 330, 210, 20));

        jLabel10.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel10.setText("FECHA DEVOLUCIÓN");
        pPrestaciones.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 380, 220, 20));

        txtCodSocio.setBackground(new java.awt.Color(245, 245, 245));
        txtCodSocio.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        txtCodSocio.setForeground(new java.awt.Color(204, 204, 204));
        txtCodSocio.setText("Codigo");
        txtCodSocio.setBorder(null);
        txtCodSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodSocioActionPerformed(evt);
            }
        });
        txtCodSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodSocioKeyTyped(evt);
            }
        });
        pPrestaciones.add(txtCodSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, 160, 30));
        txtCodSocio.setForeground(Color.GRAY);
        txtCodSocio.addFocusListener(new Placeholders("Codigo", new Color(204, 204, 204), Color.BLACK));

        txtFechaPrestamo.setBackground(new java.awt.Color(245, 245, 245));
        txtFechaPrestamo.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        txtFechaPrestamo.setForeground(new java.awt.Color(204, 204, 204));
        txtFechaPrestamo.setText("dd/mm/yy");
        txtFechaPrestamo.setBorder(null);
        txtFechaPrestamo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaPrestamoFocusLost(evt);
            }
        });
        txtFechaPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaPrestamoActionPerformed(evt);
            }
        });
        txtFechaPrestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaPrestamoKeyTyped(evt);
            }
        });
        pPrestaciones.add(txtFechaPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 320, 160, 30));
        txtFechaPrestamo.setForeground(Color.GRAY);
        txtFechaPrestamo.addFocusListener(new Placeholders("dd/mm/yy", new Color(204, 204, 204), Color.BLACK));

        txtFechaDevolucion.setBackground(new java.awt.Color(245, 245, 245));
        txtFechaDevolucion.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        txtFechaDevolucion.setForeground(new java.awt.Color(204, 204, 204));
        txtFechaDevolucion.setText("dd/mm/yy");
        txtFechaDevolucion.setBorder(null);
        txtFechaDevolucion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaDevolucionFocusLost(evt);
            }
        });
        txtFechaDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaDevolucionActionPerformed(evt);
            }
        });
        txtFechaDevolucion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaDevolucionKeyTyped(evt);
            }
        });
        pPrestaciones.add(txtFechaDevolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, 160, 30));
        txtFechaDevolucion.setForeground(Color.GRAY);
        txtFechaDevolucion.addFocusListener(new Placeholders("dd/mm/yy", new Color(204, 204, 204), Color.BLACK));

        jButton1.setBackground(new java.awt.Color(153, 70, 70));
        jButton1.setFont(new java.awt.Font("Poppins ExtraBold", 0, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("VER CARTELERA");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pPrestaciones.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 490, 210, 30));

        jButton3.setBackground(new java.awt.Color(135, 160, 86));
        jButton3.setFont(new java.awt.Font("Poppins ExtraBold", 0, 16)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("SOLICITAR");
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        pPrestaciones.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 490, 210, 30));

        jLabel28.setFont(new java.awt.Font("Poppins Black", 0, 48)); // NOI18N
        jLabel28.setText("PRESTACIONES");
        pPrestaciones.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 410, 40));

        tabbedPane.addTab("tab2", pPrestaciones);

        pDevoluciones.setBackground(new java.awt.Color(255, 255, 255));
        pDevoluciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel13.setText("ID PRÉSTAMO");
        pDevoluciones.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 110, 20));

        jLabel14.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel14.setText("FECHA ENTREGA");
        pDevoluciones.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 360, 150, 20));

        txtIdPrestamo.setBackground(new java.awt.Color(243, 243, 243));
        txtIdPrestamo.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        txtIdPrestamo.setForeground(new java.awt.Color(193, 193, 193));
        txtIdPrestamo.setText("   Codigo");
        txtIdPrestamo.setBorder(null);
        txtIdPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPrestamoActionPerformed(evt);
            }
        });
        pDevoluciones.add(txtIdPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, 160, 30));
        txtIdPrestamo.setForeground(Color.GRAY);
        txtIdPrestamo.addFocusListener(new Placeholders("Codigo", new Color(204, 204, 204), Color.BLACK));

        txtFechaEntrega.setBackground(new java.awt.Color(243, 243, 243));
        txtFechaEntrega.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        txtFechaEntrega.setForeground(new java.awt.Color(193, 193, 193));
        txtFechaEntrega.setText("  dd/mm/yy");
        txtFechaEntrega.setBorder(null);
        txtFechaEntrega.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaEntregaFocusLost(evt);
            }
        });
        txtFechaEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaEntregaActionPerformed(evt);
            }
        });
        pDevoluciones.add(txtFechaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 350, 160, 30));
        txtFechaEntrega.setForeground(Color.GRAY);
        txtFechaEntrega.addFocusListener(new Placeholders("dd/mm/yy", new Color(204, 204, 204), Color.BLACK));

        jButton2.setBackground(new java.awt.Color(67, 85, 103));
        jButton2.setFont(new java.awt.Font("Poppins ExtraBold", 0, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("SOLICITAR");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pDevoluciones.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 420, 390, 30));

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Poppins Black", 0, 48)); // NOI18N
        jLabel29.setText("DEVOLUCIONES");
        pDevoluciones.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 390, 40));

        tabbedPane.addTab("tab3", pDevoluciones);

        pDirectores.setBackground(new java.awt.Color(255, 255, 255));
        pDirectores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel6.setText("NOMBRE");
        pDirectores.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, -1, -1));

        txtNombreDirector.setBackground(new java.awt.Color(243, 243, 243));
        txtNombreDirector.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        txtNombreDirector.setBorder(null);
        txtNombreDirector.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreDirectorKeyTyped(evt);
            }
        });
        pDirectores.add(txtNombreDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 220, 40));

        btnAgregarDirector.setBackground(new java.awt.Color(128, 144, 153));
        btnAgregarDirector.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        btnAgregarDirector.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarDirector.setText("AGREGAR");
        btnAgregarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDirectorActionPerformed(evt);
            }
        });
        pDirectores.add(btnAgregarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 200, 180, 40));

        tableDirectores.getTableHeader().setBackground(new Color(255,142,142));
        tableDirectores.getTableHeader().setForeground(Color.white);
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

        pDirectores.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 400, 160));

        btnEditarDirector.setBackground(new java.awt.Color(146, 131, 119));
        btnEditarDirector.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        btnEditarDirector.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarDirector.setText("EDITAR");
        btnEditarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarDirectorActionPerformed(evt);
            }
        });
        pDirectores.add(btnEditarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 520, 200, 40));

        btnEliminarDirector.setBackground(new java.awt.Color(146, 131, 119));
        btnEliminarDirector.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        btnEliminarDirector.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarDirector.setText("ELIMINAR");
        btnEliminarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDirectorActionPerformed(evt);
            }
        });
        pDirectores.add(btnEliminarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 520, 180, 40));

        txtBuscarDirector.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e){
                actualizarTablaDirectoresConFiltro();
            }

            @Override
            public void removeUpdate(DocumentEvent e){
                actualizarTablaDirectoresConFiltro();
            }

            @Override
            public void changedUpdate(DocumentEvent e){
                actualizarTablaDirectoresConFiltro();
            }

            private void actualizarTablaDirectoresConFiltro(){
                String texto = txtBuscarDirector.getText();
                if(texto.trim().isEmpty()){
                    actualizarTablaDirectores();
                }else{
                    filtrarTablaDirectores(texto);
                }
            }
        });
        txtBuscarDirector.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        txtBuscarDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarDirectorActionPerformed(evt);
            }
        });
        pDirectores.add(txtBuscarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 170, -1));

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setFont(new java.awt.Font("Poppins Black", 0, 48)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(50, 50, 50));
        jLabel30.setText("DIRECTORES");
        pDirectores.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 320, 40));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs/Imgs Login/lupa (2).png"))); // NOI18N
        pDirectores.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, 30, 30));

        tabbedPane.addTab("tab4", pDirectores);

        pActores.setBackground(new java.awt.Color(255, 255, 255));
        pActores.setForeground(new java.awt.Color(255, 255, 255));
        pActores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jLabel16.setText("NOMBRE");
        pActores.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, -1, -1));

        txtNombreActor.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        txtNombreActor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreActorKeyTyped(evt);
            }
        });
        pActores.add(txtNombreActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, 280, -1));

        txtBuscarActor.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e){
                actualizarTablaActoresConFiltro();
            }

            @Override
            public void removeUpdate(DocumentEvent e){
                actualizarTablaActoresConFiltro();
            }

            @Override
            public void changedUpdate(DocumentEvent e){
                actualizarTablaActoresConFiltro();
            }

            private void actualizarTablaActoresConFiltro(){
                String texto = txtBuscarActor.getText();
                if(texto.trim().isEmpty()){
                    actualizarTablaActores();
                }else{
                    filtrarTablaActores(texto);
                }
            }
        });
        txtBuscarActor.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        pActores.add(txtBuscarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 280, 280, -1));

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
        tableActores.getColumnModel().getColumn(0).setMinWidth(20);
        tableActores.getColumnModel().getColumn(0).setMaxWidth(20);
        tableActores.getColumnModel().getColumn(0).setWidth(20);
        jScrollPane3.setViewportView(tableActores);
        actualizarTablaActores();

        pActores.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, 540, 220));

        btnAgregarActor.setBackground(new java.awt.Color(132, 88, 88));
        btnAgregarActor.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btnAgregarActor.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarActor.setText("Agregar");
        btnAgregarActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActorActionPerformed(evt);
            }
        });
        pActores.add(btnAgregarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 220, 150, -1));

        btnEditarActor.setBackground(new java.awt.Color(102, 139, 112));
        btnEditarActor.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btnEditarActor.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarActor.setText("Editar");
        btnEditarActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActorActionPerformed(evt);
            }
        });
        pActores.add(btnEditarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 570, 220, -1));

        btnEliminarActor.setBackground(new java.awt.Color(102, 139, 112));
        btnEliminarActor.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        btnEliminarActor.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarActor.setText("Eliminar");
        btnEliminarActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActorActionPerformed(evt);
            }
        });
        pActores.add(btnEliminarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 570, 210, -1));

        jLabel32.setBackground(new java.awt.Color(255, 255, 255));
        jLabel32.setFont(new java.awt.Font("Poppins Black", 0, 48)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(50, 50, 50));
        jLabel32.setText("ACTORES");
        pActores.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 230, 40));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs/Imgs Login/lupa (2).png"))); // NOI18N
        pActores.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 30, 30));

        tabbedPane.addTab("tab5", pActores);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        jLabel15.setText("ID CINTA");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, -1, -1));

        txtIdCinta.setEditable(false);
        txtIdCinta.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        jPanel2.add(txtIdCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 70, -1));

        jLabel17.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        jLabel17.setText("PELÍCULA");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, -1, -1));

        txtPeliculaCinta.setEditable(false);
        txtPeliculaCinta.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        jPanel2.add(txtPeliculaCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, 150, -1));

        btnBuscarPeliculaCinta.setBackground(new java.awt.Color(163, 142, 121));
        btnBuscarPeliculaCinta.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        btnBuscarPeliculaCinta.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPeliculaCinta.setText("Buscar Pelicula");
        btnBuscarPeliculaCinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPeliculaCintaActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscarPeliculaCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, -1, -1));

        jLabel24.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        jLabel24.setText("ESTADO");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, -1, -1));

        cboEstadoCinta.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        cboEstadoCinta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "Prestado", "Dañada", "Perdida" }));
        jPanel2.add(cboEstadoCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 240, 150, -1));

        btnAgregarCinta.setBackground(new java.awt.Color(109, 128, 70));
        btnAgregarCinta.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        btnAgregarCinta.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCinta.setText("Agregar");
        btnAgregarCinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCintaActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregarCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 620, 120, -1));

        btnEditarCinta.setBackground(new java.awt.Color(135, 56, 56));
        btnEditarCinta.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        btnEditarCinta.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarCinta.setText("Editar");
        btnEditarCinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCintaActionPerformed(evt);
            }
        });
        jPanel2.add(btnEditarCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 620, 130, -1));

        btnEliminarCinta.setBackground(new java.awt.Color(105, 125, 183));
        btnEliminarCinta.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        btnEliminarCinta.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCinta.setText("Eliminar");
        btnEliminarCinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCintaActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminarCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 620, 140, -1));

        txtBuscarCinta.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        jPanel2.add(txtBuscarCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, 110, -1));

        tableCintas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Cinta", "Pelicula", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tableCintas);

        jPanel2.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 380, 440, 210));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs/Imgs Login/lupa (2).png"))); // NOI18N
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, 30, 30));

        jLabel36.setBackground(new java.awt.Color(255, 255, 255));
        jLabel36.setFont(new java.awt.Font("Poppins Black", 0, 48)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(50, 50, 50));
        jLabel36.setText("CINTAS");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 230, 40));

        tabbedPane.addTab("tab7", jPanel2);

        pPeliculas.setBackground(new java.awt.Color(255, 255, 255));
        pPeliculas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel20.setText("ID PELÍCULA");
        pPeliculas.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, 90, 20));

        txtIdPelicula.setEditable(false);
        txtIdPelicula.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        pPeliculas.add(txtIdPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 130, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel21.setText("TÍTULO");
        pPeliculas.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 60, 20));

        txtTituloPelicula.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        pPeliculas.add(txtTituloPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, 130, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel22.setText("DIRECTOR");
        pPeliculas.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, 90, 20));

        cboDirectoresPelicula.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        pPeliculas.add(cboDirectoresPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 330, 130, 30));

        jLabel23.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel23.setText("GÉNEROS");
        pPeliculas.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 70, 20));

        btnAbrirListaGeneros.setBackground(new java.awt.Color(202, 165, 211));
        btnAbrirListaGeneros.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        btnAbrirListaGeneros.setForeground(new java.awt.Color(255, 255, 255));
        btnAbrirListaGeneros.setText("Abrir Géneros");
        btnAbrirListaGeneros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirListaGenerosActionPerformed(evt);
            }
        });
        pPeliculas.add(btnAbrirListaGeneros, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 400, 140, 30));

        tablePeliculas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Titulo", "Director", "Genero"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePeliculas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePeliculasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablePeliculas);

        pPeliculas.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 510, 580, 170));

        btnAgregarPelicula.setBackground(new java.awt.Color(147, 147, 204));
        btnAgregarPelicula.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnAgregarPelicula.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarPelicula.setText("AGREGAR");
        btnAgregarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPeliculaActionPerformed(evt);
            }
        });
        pPeliculas.add(btnAgregarPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 210, 130, 30));

        btnEditarPelicula.setBackground(new java.awt.Color(147, 147, 204));
        btnEditarPelicula.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnEditarPelicula.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarPelicula.setText("EDITAR");
        btnEditarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarPeliculaActionPerformed(evt);
            }
        });
        pPeliculas.add(btnEditarPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 270, 130, 30));

        btnEliminarPelicula.setBackground(new java.awt.Color(147, 147, 204));
        btnEliminarPelicula.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnEliminarPelicula.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarPelicula.setText("ELIMINAR");
        btnEliminarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPeliculaActionPerformed(evt);
            }
        });
        pPeliculas.add(btnEliminarPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 330, 130, 30));

        txtBuscarPelicula.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e){
                actualizarTablaConFiltro();
            }

            @Override
            public void removeUpdate(DocumentEvent e){
                actualizarTablaConFiltro();
            }

            @Override
            public void changedUpdate(DocumentEvent e){
                actualizarTablaConFiltro();
            }

            private void actualizarTablaConFiltro(){

                txtBuscarPelicula.getDocument().removeDocumentListener(this);

                String texto = txtBuscarPelicula.getText();
                if(texto.trim().isEmpty()){
                    actualizarTablaPeliculas();
                }else{
                    actualizarTablaPeliculasConBusqueda(texto);
                }

                txtBuscarPelicula.getDocument().addDocumentListener(this);
            }
        });
        txtBuscarPelicula.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        pPeliculas.add(txtBuscarPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 470, 70, 30));

        jLabel34.setBackground(new java.awt.Color(255, 255, 255));
        jLabel34.setFont(new java.awt.Font("Poppins Black", 0, 48)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(50, 50, 50));
        jLabel34.setText("PELÍCULAS");
        pPeliculas.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 290, 40));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imgs/Imgs Login/lupa (2).png"))); // NOI18N
        pPeliculas.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 470, 30, 30));

        tabbedPane.addTab("tab6", pPeliculas);

        add(tabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, -20, 1040, 860));
    }// </editor-fold>//GEN-END:initComponents

    private void btnregistrarsocioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnregistrarsocioMouseClicked
        tabbedPane.setSelectedIndex(0);
        actualizarTablaSocios();
    }//GEN-LAST:event_btnregistrarsocioMouseClicked

    private void btnprestacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnprestacionesMouseClicked
        tabbedPane.setSelectedIndex(1);        // TODO add your handling code here:
    }//GEN-LAST:event_btnprestacionesMouseClicked

    private void btndevolucionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndevolucionesMouseClicked
         tabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_btndevolucionesMouseClicked

    private void btnDirectoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDirectoresMouseClicked
        tabbedPane.setSelectedIndex(3); 
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

    private void txtNombreSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreSocioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreSocioActionPerformed

    private void txtNombreSocioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreSocioFocusLost

    }//GEN-LAST:event_txtNombreSocioFocusLost

    private void txtNombreSocioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreSocioFocusGained

    }//GEN-LAST:event_txtNombreSocioFocusGained

    private void txtTelefonoSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoSocioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoSocioActionPerformed

    private void txtTelefonoSocioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoSocioFocusLost

    }//GEN-LAST:event_txtTelefonoSocioFocusLost

    private void txtTelefonoSocioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoSocioFocusGained

    }//GEN-LAST:event_txtTelefonoSocioFocusGained

    private void txtDireccionSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionSocioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionSocioActionPerformed

    private void txtDireccionSocioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionSocioFocusLost

    }//GEN-LAST:event_txtDireccionSocioFocusLost

    private void txtDireccionSocioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionSocioFocusGained

    }//GEN-LAST:event_txtDireccionSocioFocusGained

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
        String nombre = txtNombreSocio.getText();
        String direccion = txtDireccionSocio.getText();
        String telefono = txtTelefonoSocio.getText();
        
        Socio nuevoSocio = new Socio();
        nuevoSocio.setNombre(nombre);
        nuevoSocio.setDireccion(direccion);
        nuevoSocio.setTelefono(telefono);
        
        List<Director> listaDirectoresFavoritos = new ArrayList<>(directoresSeleccionados);
        List<Actor> listaActoresFavoritos = new ArrayList<>(actoresSeleccionados);
        List<Genero> listaGenerosFavoritos = new ArrayList<>(generosSeleccionados);

        socioService.agregarSocioConFavoritos(nuevoSocio, listaDirectoresFavoritos, listaActoresFavoritos, listaGenerosFavoritos);

        actualizarTablaSocios();
        limpiarCamposFormularioSocio();
    }//GEN-LAST:event_btnRegistrarSocioActionPerformed

    private void btnAgregarDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDirectorActionPerformed
        String nombre = txtNombreDirector.getText();
        if(!nombre.trim().isEmpty()){
            directorService.agregarDirector(nombre);
            actualizarTablaDirectores();
        }else{
            JOptionPane.showMessageDialog(null, "El nombre del director no puede estar vacio.");
        }
    }//GEN-LAST:event_btnAgregarDirectorActionPerformed

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
        tabbedPane.setSelectedIndex(4); 
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

    private void btnEditarSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarSocioActionPerformed
        int idSocio = Integer.parseInt(txtIdSocio.getText());
        Socio socio = socioService.obtenerSocioId(idSocio);
        if(socio != null){
            socio.setNombre(txtNombreSocio.getText());
            socio.setDireccion(txtDireccionSocio.getText());
            socio.setTelefono(txtTelefonoSocio.getText());
            socioService.actualizarSocio(socio);
            actualizarTablaSocios();
            JOptionPane.showMessageDialog(this, "Socio actualizado correctamente.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "Error al actualizar el socio.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarSocioActionPerformed

    private void btnEliminarSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarSocioActionPerformed
        int fila = tableSocios.getSelectedRow();
        if(fila != -1){
            int idSocio = Integer.parseInt(tableSocios.getValueAt(fila, 0).toString());
            int confirmacion = JOptionPane.showConfirmDialog(null, "Estás seguro de que deseas eliminar el socio seleccionado?", "Eliminar Socio", JOptionPane.YES_NO_OPTION);
            if(confirmacion == JOptionPane.YES_OPTION){
                Socio socioParaEliminar = new Socio();
                socioParaEliminar.setSocioId(idSocio);
                socioService.eliminarSocio(socioParaEliminar);
                actualizarTablaSocios();
                JOptionPane.showMessageDialog(null, "Socio eliminado con exito.", "Eliminar Socio", JOptionPane.INFORMATION_MESSAGE);
                
                txtNombreSocio.setText("");
                txtDireccionSocio.setText("");
                txtTelefonoSocio.setText("");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un socio de la tabla.", "Eliminar Socio", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarSocioActionPerformed

    private void tableSociosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSociosMouseClicked
        if(evt.getClickCount() == 2){
            int fila  = tableSocios.getSelectedRow();
            if(fila != -1){
                int idSocio = Integer.parseInt(tableSocios.getValueAt(fila, 0).toString());
                cargarDatosSocios(idSocio);
            }
        }
    }//GEN-LAST:event_tableSociosMouseClicked

    private void txtBuscarSocioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarSocioKeyReleased
        String text = txtBuscarSocio.getText();
        if(!text.trim().isEmpty()){
            try{
                int idBusqueda = Integer.parseInt(text);
                Socio socioEncontrado = socioService.obtenerSocioId(idBusqueda);
                if(socioEncontrado != null){
                    actualizarTablaSociosConUnSocio(socioEncontrado);
                }else{
                    limpiarTablaSocios();
                }
            }catch(NumberFormatException ex){
                limpiarTablaSocios();
            }
        }else{
            actualizarTablaSocios();
        }
    }//GEN-LAST:event_txtBuscarSocioKeyReleased

    private void txtNombreSocioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreSocioKeyTyped
           validacionTexto(evt);
     
    }//GEN-LAST:event_txtNombreSocioKeyTyped

    private void txtTelefonoSocioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoSocioKeyTyped
validacionNumerica(evt); // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoSocioKeyTyped

    private void txtNombreActorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreActorKeyTyped
          validacionTexto(evt);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActorKeyTyped

    private void txtNombreDirectorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreDirectorKeyTyped
validacionTexto(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreDirectorKeyTyped

    private void txtCodSocioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodSocioKeyTyped
validacionNumerica(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodSocioKeyTyped

    private void txtFechaPrestamoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaPrestamoKeyTyped
// TODO add your handling code here:
    }//GEN-LAST:event_txtFechaPrestamoKeyTyped

    private void txtFechaDevolucionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaDevolucionKeyTyped
  
// TODO add your handling code here:
    }//GEN-LAST:event_txtFechaDevolucionKeyTyped

    private void txtFechaPrestamoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaPrestamoFocusLost
        validacionFecha(evt);
  
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaPrestamoFocusLost

    private void txtFechaDevolucionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaDevolucionFocusLost
    validacionFecha(evt);     // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaDevolucionFocusLost

    private void txtFechaEntregaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaEntregaFocusLost
 validacionFecha(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaEntregaFocusLost

    private void btnPeliculasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPeliculasMouseClicked
        tabbedPane.setSelectedIndex(5); 
        actualizarTablaPeliculas();
    }//GEN-LAST:event_btnPeliculasMouseClicked

    private void btnAbrirListaGenerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirListaGenerosActionPerformed
        
    }//GEN-LAST:event_btnAbrirListaGenerosActionPerformed

    private void btnAgregarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPeliculaActionPerformed
//        String titulo = txtTituloPelicula.getText();
//        Director director = (Director) cboDirectoresPelicula.getSelectedItem();
////        List<Genero> generosSeleccionados = this.generosSeleccionados;
//        
//        if(titulo.isEmpty() || director == null || generosSeleccionados == null || generosSeleccionados.isEmpty()){
//            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//                
//        Pelicula nuevaPelicula = new Pelicula();
//        nuevaPelicula.setTitulo(titulo);
//        nuevaPelicula.setDirector(director);
//        
//       int peliculaId = peliculaService.agregarPeliculaConGeneros(nuevaPelicula, generosSeleccionados);
//       if(peliculaId > 0){
//           actualizarTablaPeliculas();
//           limpiarCamposPelicula();
//       }else{
//           JOptionPane.showMessageDialog(this, "Error al agregar la pelicula.", "Error", JOptionPane.ERROR_MESSAGE);
//       }
//           
    }//GEN-LAST:event_btnAgregarPeliculaActionPerformed

    private void btnEliminarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPeliculaActionPerformed
        int filaSeleccionada = tablePeliculas.getSelectedRow();
        if(filaSeleccionada == -1){
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una pelicula para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int peliculaId = (int) tablePeliculas.getModel().getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this, "Estas seguro de que deseas eliminar la pelicula seleccionada?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
        if(confirmacion == JOptionPane.YES_OPTION){
            peliculaService.eliminarPelicula(peliculaId);
            actualizarTablaPeliculas();
        }
    }//GEN-LAST:event_btnEliminarPeliculaActionPerformed

    private void tablePeliculasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePeliculasMouseClicked
        if(evt.getClickCount() == 2 && tablePeliculas.getSelectedRow() != -1){
            rellenarFormularioParaEdicion(tablePeliculas.getSelectedRow());
        }
    }//GEN-LAST:event_tablePeliculasMouseClicked

    private void btnEditarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPeliculaActionPerformed
//        if(txtIdPelicula.getText().isEmpty()){
//            JOptionPane.showMessageDialog(panelMenu.this, "Por favor, selecciona una pelicula para editar.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        
//        int peliculaId = Integer.parseInt(txtIdPelicula.getText());
//        Pelicula peliculaEditada = new Pelicula();
//        peliculaEditada.setPeliculaId(peliculaId);
//        peliculaEditada.setTitulo(txtTituloPelicula.getText());
//        peliculaEditada.setDirector((Director) cboDirectoresPelicula.getSelectedItem());
//        
//        List<Genero> generosSeleccionados = this.generosSeleccionados;
//        
//        boolean exito = peliculaService.editarPeliculaConGeneros(peliculaEditada, generosSeleccionados);
//        
//        if(exito){
//            actualizarTablaPeliculas();
//            limpiarCamposPelicula();
//        }else{
//            JOptionPane.showMessageDialog(panelMenu.this , "Hubo un error al editar la pelicula.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
    }//GEN-LAST:event_btnEditarPeliculaActionPerformed

    private void btnDirectoresSociosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirectoresSociosActionPerformed
        Window windowAncestor = SwingUtilities.getWindowAncestor(this);
        if(windowAncestor instanceof Frame){
            Frame owner = (Frame) windowAncestor;
            SeleccionElementosDialog<Director> dialogo = new SeleccionElementosDialog<>(
                    owner,
                    true,
                    listaDirectores,
                    directoresSeleccionados,
                    Director::getNombre,
                    this::actualizarSeleccionDirectores
            );
            
            dialogo.setTitle("Seleccionar Directores");
            dialogo.setVisible(true);
        }else{
            
        }
    }//GEN-LAST:event_btnDirectoresSociosActionPerformed

    private void btnActoresSociosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActoresSociosActionPerformed
        Window windowAncestor = SwingUtilities.getWindowAncestor(this);
        if(windowAncestor instanceof Frame){
            Frame owner = (Frame) windowAncestor;
            SeleccionElementosDialog<Actor> dialogo = new SeleccionElementosDialog<>(
                    owner,
                    true,
                    listaActores,
                    actoresSeleccionados,
                    Actor::getNombre,
                    this::actualizarSeleccionActores
            );
            
            dialogo.setTitle("Seleccionar Actores");
            dialogo.setVisible(true);
        }else{
            
        }
    }//GEN-LAST:event_btnActoresSociosActionPerformed

    private void btnGenerosSociosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerosSociosActionPerformed
        Window windowAncestor = SwingUtilities.getWindowAncestor(this);
        if(windowAncestor instanceof Frame){
            Frame owner = (Frame) windowAncestor;
            SeleccionElementosDialog<Genero> dialogo = new SeleccionElementosDialog<>(
                    owner,
                    true,
                    listaGeneros,
                    generosSeleccionados,
                    Genero::getNombre,
                    this::actualizarSeleccionGeneros
            );
            
            dialogo.setTitle("Seleccionar Generos");
            dialogo.setVisible(true);
        }else{
            
        }
    }//GEN-LAST:event_btnGenerosSociosActionPerformed

    private void btnCintasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCintasMouseClicked
        tabbedPane.setSelectedIndex(6); 
        actualizarTablaCintas();
    }//GEN-LAST:event_btnCintasMouseClicked

    private void btnAgregarCintaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCintaActionPerformed
        String peliculaTexto = txtPeliculaCinta.getText();
        String estado = (String) cboEstadoCinta.getSelectedItem();

        int peliculaId = cintaService.obtenerPeliculaIdPorNombre(peliculaTexto);
        if (peliculaId == -1) {
            JOptionPane.showMessageDialog(null, "Película no encontrada.");
            return;
        }

        Cinta nuevaCinta = new Cinta();
        nuevaCinta.setPeliculaId(peliculaId);
        nuevaCinta.setEstado(estado);

        cintaService.agregarCinta(nuevaCinta);

        if (nuevaCinta.getCintaId() > 0) {
            actualizarTablaCintas();
            JOptionPane.showMessageDialog(null, "Cinta agregada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al agregar la cinta.");
        }
    }//GEN-LAST:event_btnAgregarCintaActionPerformed

    private void btnEditarCintaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCintaActionPerformed
        // Asumiendo que tienes un método para obtener la cinta seleccionada de la tabla
        Cinta cintaSeleccionada = obtenerCintaSeleccionada();
        if (cintaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una cinta para editar.");
            return;
        }

        // Asumiendo que tienes métodos para obtener los datos actualizados de la UI
        String peliculaTexto = txtPeliculaCinta.getText();
        int peliculaId = cintaService.obtenerPeliculaIdPorNombre(peliculaTexto);
        String estado = (String) cboEstadoCinta.getSelectedItem();
        
        // Actualizar los datos de la cinta seleccionada
        cintaSeleccionada.setPeliculaId(peliculaId);
        cintaSeleccionada.setEstado(estado);

        // Llamar al servicio para actualizar la cinta
        cintaService.actualizarCinta(cintaSeleccionada);

        // Actualizar la tabla y notificar al usuario
        actualizarTablaCintas();
        JOptionPane.showMessageDialog(null, "Cinta actualizada exitosamente.");
    }//GEN-LAST:event_btnEditarCintaActionPerformed

    private void btnEliminarCintaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCintaActionPerformed
        // Asumiendo que tienes un método que obtenga la cinta seleccionada de la tabla
        Cinta cintaSeleccionada = obtenerCintaSeleccionadaDeLaTabla();

        if (cintaSeleccionada != null) {
            // Confirmar antes de eliminar
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de que quieres eliminar la cinta seleccionada?", 
                "Eliminar Cinta", 
                JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean exito = cintaService.eliminarCinta(cintaSeleccionada.getCintaId());

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Cinta eliminada con éxito.", 
                        "Eliminar Cinta", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTablaCintas(); // Actualizar la tabla
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la cinta.", 
                        "Eliminar Cinta", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una cinta para eliminar.", 
                "Eliminar Cinta", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarCintaActionPerformed

    private void btnBuscarPeliculaCintaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPeliculaCintaActionPerformed
        // Obtener la lista de películas
        List<Pelicula> peliculas = obtenerListaPeliculas(); // Asumiendo que este método ya está definido y devuelve la lista de películas.

        // Encuentra el JFrame que contiene este panel
        Frame frameAncestor = (Frame) SwingUtilities.getWindowAncestor(this);

        // Crear y mostrar el diálogo
        SeleccionPeliculaDialog dialog = new SeleccionPeliculaDialog(frameAncestor, true, peliculas);
        dialog.setVisible(true);

        // Obtener la película seleccionada del diálogo
        Pelicula peliculaSeleccionada = dialog.getPeliculaSeleccionada();
        if (peliculaSeleccionada != null) {
            txtPeliculaCinta.setText(peliculaSeleccionada.getTitulo());
        }
    }//GEN-LAST:event_btnBuscarPeliculaCintaActionPerformed

    private void txtBuscarDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarDirectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarDirectorActionPerformed

private boolean validarFecha(String fechaTexto, String formato) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat(formato);
        formatoFecha.setLenient(false);

        try {
            Date fecha = formatoFecha.parse(fechaTexto);

            // Validar día en función del mes
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);

            int dia = cal.get(Calendar.DAY_OF_MONTH);
            int mes = cal.get(Calendar.MONTH) + 1; // Los meses en Calendar son 0-indexados

            // Validar días según el mes
            if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
                return false; // Meses con 30 días
            } else if (mes == 2) {
                int anio = cal.get(Calendar.YEAR);

                if ((anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0)) {
                    // Año bisiesto
                    if (dia > 29) {
                        return false;
                    }
                } else {
                    // No es un año bisiesto
                    if (dia > 28) {
                        return false;
                    }
                }
            } else if (dia > 31) {
                return false; // Meses con 31 días
            }

            // La fecha es válida
            return true;
        } catch (ParseException e) {
            // La fecha no es válida
            System.out.println("Error de validación: " + e.getMessage());
            return false;
        }
    }
    
    void validacionFecha(java.awt.event.FocusEvent evt)
    {
         javax.swing.JTextField textField = (javax.swing.JTextField) evt.getSource();
        String fechaTexto = textField.getText().trim();

        if (!fechaTexto.isEmpty()) {
            if (!validarFecha(fechaTexto, "dd/MM/yyyy")) {
                JOptionPane.showMessageDialog(null, "Fecha no válida. Utilice el formato dd/MM/yyyy");
                textField.requestFocus();
            } else {
                System.out.println("Fecha válida: " + fechaTexto);
            }
        }
    }
    
    
    void validacionTexto(java.awt.event.KeyEvent evt){
        char c = evt.getKeyChar();

 if ((Character.isLetter(c) || c == KeyEvent.VK_SPACE || c == KeyEvent.VK_BACK_SPACE)) {
        // Código a ejecutar si la condición es verdadera
    } else {
        evt.consume();
        JOptionPane.showMessageDialog(null, "Solo ingresar caracteres de tipo texto");
    }
    }
    
    void validacionNumerica(java.awt.event.KeyEvent evt){
             char c = evt.getKeyChar();

 if ((Character.isDigit(c) || c == KeyEvent.VK_SPACE || c == KeyEvent.VK_BACK_SPACE)) {
        // Código a ejecutar si la condición es verdadera
    } else {
        evt.consume();
        JOptionPane.showMessageDialog(null, "Solo ingresar caracteres de tipo numérico");  
 }
    }
    

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirListaGeneros;
    private javax.swing.JButton btnActores;
    private javax.swing.JButton btnActoresSocios;
    private javax.swing.JButton btnAgregarActor;
    private javax.swing.JButton btnAgregarCinta;
    private javax.swing.JButton btnAgregarDirector;
    private javax.swing.JButton btnAgregarPelicula;
    private javax.swing.JButton btnBuscarPeliculaCinta;
    private javax.swing.JButton btnCintas;
    private javax.swing.JButton btnDirectores;
    private javax.swing.JButton btnDirectoresSocios;
    private javax.swing.JButton btnEditarActor;
    private javax.swing.JButton btnEditarCinta;
    private javax.swing.JButton btnEditarDirector;
    private javax.swing.JButton btnEditarPelicula;
    private javax.swing.JButton btnEditarSocio;
    private javax.swing.JButton btnEliminarActor;
    private javax.swing.JButton btnEliminarCinta;
    private javax.swing.JButton btnEliminarDirector;
    private javax.swing.JButton btnEliminarPelicula;
    private javax.swing.JButton btnEliminarSocio;
    private javax.swing.JButton btnGenerosSocios;
    private javax.swing.JButton btnPeliculas;
    private javax.swing.JButton btnRegistrarSocio;
    private javax.swing.JButton btndevoluciones;
    private javax.swing.JButton btnprestaciones;
    private javax.swing.JButton btnregistrarsocio;
    private javax.swing.JComboBox<Director> cboDirectoresPelicula;
    private javax.swing.JComboBox<String> cboEstadoCinta;
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
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblActoresSeleccionados;
    private javax.swing.JLabel lblDirectoresSeleccionados;
    private javax.swing.JLabel lblGenerosSeleccionados;
    private javax.swing.JPanel pActores;
    private javax.swing.JPanel pDevoluciones;
    private javax.swing.JPanel pDirectores;
    private javax.swing.JPanel pPeliculas;
    private javax.swing.JPanel pPrestaciones;
    private javax.swing.JPanel pSocios;
    private javax.swing.JPanel panelBarra;
    public javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tableActores;
    private javax.swing.JTable tableCintas;
    private javax.swing.JTable tableDirectores;
    private javax.swing.JTable tablePeliculas;
    private javax.swing.JTable tableSocios;
    private javax.swing.JTextField txtBuscarActor;
    private javax.swing.JTextField txtBuscarCinta;
    private javax.swing.JTextField txtBuscarDirector;
    private javax.swing.JTextField txtBuscarPelicula;
    private javax.swing.JTextField txtBuscarSocio;
    private javax.swing.JTextField txtCodSocio;
    private javax.swing.JTextField txtDireccionSocio;
    private javax.swing.JTextField txtFechaDevolucion;
    private javax.swing.JTextField txtFechaEntrega;
    private javax.swing.JTextField txtFechaPrestamo;
    private javax.swing.JTextField txtIdCinta;
    private javax.swing.JTextField txtIdPelicula;
    private javax.swing.JTextField txtIdPrestamo;
    private javax.swing.JTextField txtIdSocio;
    private javax.swing.JTextField txtNombreActor;
    private javax.swing.JTextField txtNombreDirector;
    private javax.swing.JTextField txtNombreSocio;
    private javax.swing.JTextField txtPeliculaCinta;
    private javax.swing.JTextField txtTelefonoSocio;
    private javax.swing.JTextField txtTituloPelicula;
    // End of variables declaration//GEN-END:variables
   }
