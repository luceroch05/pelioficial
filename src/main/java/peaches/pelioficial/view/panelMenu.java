/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package peaches.pelioficial.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.KeyEvent;
//import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import peaches.pelioficial.model.Prestamo;
import peaches.pelioficial.model.Socio;
import peaches.pelioficial.service.ActorService;
import peaches.pelioficial.service.CintaService;
import peaches.pelioficial.service.DirectorService;
import peaches.pelioficial.service.PeliculaService;
import peaches.pelioficial.service.PrestamoService;
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
        PrestamoService prestamoService = new PrestamoService(DatabaseConnector.conectar());
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
        rellenarComboBoxes();
        this.framePrincipal = framePrincipal;
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
    
    public void actualizarTablaConResultados(List<Cinta> resultados) {
        DefaultTableModel modelo = (DefaultTableModel) tableCintas.getModel();
        modelo.setRowCount(0);
        for (Cinta cinta : resultados) {
            modelo.addRow(new Object[] {
                cinta.getCintaId(),
                cinta.getTituloPelicula(),
                cinta.getEstado()
            });
        }
    }
    
    private void actualizarTablaPrestamosConBusqueda(String textoBusqueda) {
        List<Prestamo> prestamos = prestamoService.buscarPrestamosPorNombreSocio(textoBusqueda);
        DefaultTableModel modelo = (DefaultTableModel) tablePrestamos.getModel();
        modelo.setRowCount(0);
        for (Prestamo prestamo : prestamos) {
            modelo.addRow(new Object[]{
                prestamo.getPrestamoId(),
                prestamo.getNombreSocio(),
                prestamo.getTituloPelicula(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucion() != null ? prestamo.getFechaDevolucion().toString() : "N/A",
                prestamo.getEstadoCinta()
            });
        }
    }
    
    private void actualizarTablaPrestamos() {
        List<Prestamo> prestamos = prestamoService.obtenerTodosLosPrestamos();
        DefaultTableModel modelo = (DefaultTableModel) tablePrestamos.getModel();
        modelo.setRowCount(0); // Limpiar la tabla primero

        for (Prestamo prestamo : prestamos) {
            modelo.addRow(new Object[]{
                prestamo.getPrestamoId(),
                prestamo.getNombreSocio(), // Este método obtiene el nombre del socio, asumiendo que tienes un objeto Socio asociado
                prestamo.getTituloPelicula(), // Este método obtiene el título de la película, asumiendo que tienes un objeto Pelicula asociado
                prestamo.getFechaPrestamo().toString(), // Asumiendo que es un objeto LocalDate o similar
                prestamo.getFechaDevolucion() != null ? prestamo.getFechaDevolucion().toString() : "N/A", // Comprueba si hay fecha de devolución antes de llamar a toString()
                prestamo.getEstadoCinta() // Este método obtiene el estado de la cinta
            });
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
        btnPrestamos = new javax.swing.JButton();
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
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtDireccionSocio = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        txtTelefonoSocio = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        txtNombreSocio = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        txtIdSocio = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSocios = new javax.swing.JTable();
        btnEditarSocio = new javax.swing.JButton();
        btnEliminarSocio = new javax.swing.JButton();
        txtBuscarSocio = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        btnDirectoresSocios = new javax.swing.JButton();
        btnActoresSocios = new javax.swing.JButton();
        btnGenerosSocios = new javax.swing.JButton();
        lblDirectoresSeleccionados = new javax.swing.JLabel();
        lblActoresSeleccionados = new javax.swing.JLabel();
        lblGenerosSeleccionados = new javax.swing.JLabel();
        pPrestamos = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtPrestamoId = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnBuscarSocio = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnBuscarCinta = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        pickerFechaPrestamo = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        pickerFechaDevolucion = new com.toedter.calendar.JDateChooser();
        btnAgregarPrestamo = new javax.swing.JButton();
        btnEditarPrestamo = new javax.swing.JButton();
        btnEliminarPrestamo = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablePrestamos = new javax.swing.JTable();
        txtBuscarPrestamo = new javax.swing.JTextField();
        pDevoluciones = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtIdPrestamo = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        txtFechaEntrega = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        pDirectores = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNombreDirector = new javax.swing.JTextField();
        btnAgregarDirector = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDirectores = new javax.swing.JTable();
        btnEditarDirector = new javax.swing.JButton();
        btnEliminarDirector = new javax.swing.JButton();
        txtBuscarDirector = new javax.swing.JTextField();
        pActores = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtNombreActor = new javax.swing.JTextField();
        txtBuscarActor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableActores = new javax.swing.JTable();
        btnAgregarActor = new javax.swing.JButton();
        btnEditarActor = new javax.swing.JButton();
        btnEliminarActor = new javax.swing.JButton();
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
        pCintas = new javax.swing.JPanel();
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
        jLabel25 = new javax.swing.JLabel();
        txtBuscarCinta = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCintas = new javax.swing.JTable();

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
        jPanel1.add(btnDirectores, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 40));

        btnregistrarsocio.setBackground(new java.awt.Color(0, 0, 0));
        btnregistrarsocio.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnregistrarsocio.setForeground(new java.awt.Color(255, 255, 255));
        btnregistrarsocio.setText("SOCIO");
        btnregistrarsocio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnregistrarsocio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnregistrarsocioMouseClicked(evt);
            }
        });
        jPanel1.add(btnregistrarsocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 250, 40));

        btnPrestamos.setBackground(new java.awt.Color(0, 0, 0));
        btnPrestamos.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnPrestamos.setForeground(new java.awt.Color(255, 255, 255));
        btnPrestamos.setText("PRESTAMOS");
        btnPrestamos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnPrestamos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrestamosMouseClicked(evt);
            }
        });
        btnPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrestamosActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrestamos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 250, 40));

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
        jPanel1.add(btnActores, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 40));

        btnPeliculas.setText("PELICULAS");
        btnPeliculas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPeliculasMouseClicked(evt);
            }
        });
        jPanel1.add(btnPeliculas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, 40));

        btnCintas.setText("CINTAS");
        btnCintas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCintasMouseClicked(evt);
            }
        });
        jPanel1.add(btnCintas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 250, 40));

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

        pSocios.setBackground(new java.awt.Color(255, 255, 255));
        pSocios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel1.setText("Nombre:");
        pSocios.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setText("Dirección:");
        pSocios.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setText("Teléfono:");
        pSocios.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setText("Directores favoritos:");
        pSocios.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        btnRegistrarSocio.setBackground(new java.awt.Color(0, 0, 0));
        btnRegistrarSocio.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnRegistrarSocio.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarSocio.setText("Registrar");
        btnRegistrarSocio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarSocioActionPerformed(evt);
            }
        });
        pSocios.add(btnRegistrarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 250, 40));

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

        pSocios.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 60, 30));

        txtDireccionSocio.setForeground(new java.awt.Color(204, 204, 204));
        txtDireccionSocio.setText("Av. Del Coso 123");
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
        pSocios.add(txtDireccionSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 240, 30));
        txtDireccionSocio.setForeground(new Color(204, 204, 204));
        txtDireccionSocio.addFocusListener(new Placeholders("Av. Del Coso 123", new Color(204, 204, 204), Color.BLACK));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        pSocios.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 320, 10));

        txtTelefonoSocio.setForeground(new java.awt.Color(204, 204, 204));
        txtTelefonoSocio.setText("987654321");
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
        pSocios.add(txtTelefonoSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 240, 30));
        txtTelefonoSocio.setForeground(new Color(204, 204, 204));
        txtTelefonoSocio.addFocusListener(new Placeholders("987654321", new Color(204, 204, 204), Color.BLACK));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        pSocios.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 320, 10));

        txtNombreSocio.setForeground(new java.awt.Color(204, 204, 204));
        txtNombreSocio.setText("Introduzca su nombre");
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
        pSocios.add(txtNombreSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 240, 30));
        txtNombreSocio.setForeground(new Color(204, 204, 204));
        txtNombreSocio.addFocusListener(new Placeholders("Introduzca su nombre", new Color(204, 204, 204), Color.BLACK));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        pSocios.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 320, 10));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel11.setText("Actores favoritos:");
        pSocios.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel12.setText("Géneros preferidos:");
        pSocios.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        pSocios.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 320, 10));

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        pSocios.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 320, 10));

        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        pSocios.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 320, 10));

        jLabel18.setText("ID Socio:");
        pSocios.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        txtIdSocio.setEditable(false);
        pSocios.add(txtIdSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 240, -1));

        tableSocios.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
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
        tableSocios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSociosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableSocios);
        actualizarTablaSocios();

        pSocios.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 590, 140));

        btnEditarSocio.setText("Editar");
        btnEditarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarSocioActionPerformed(evt);
            }
        });
        pSocios.add(btnEditarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, -1, -1));

        btnEliminarSocio.setText("Eliminar");
        btnEliminarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarSocioActionPerformed(evt);
            }
        });
        pSocios.add(btnEliminarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, -1, -1));

        txtBuscarSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarSocioKeyReleased(evt);
            }
        });
        pSocios.add(txtBuscarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, 80, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Buscar por ID");
        pSocios.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, -1, -1));

        btnDirectoresSocios.setText("Elegir Directores");
        btnDirectoresSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectoresSociosActionPerformed(evt);
            }
        });
        pSocios.add(btnDirectoresSocios, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, -1, -1));

        btnActoresSocios.setText("Elegir Actores");
        btnActoresSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActoresSociosActionPerformed(evt);
            }
        });
        pSocios.add(btnActoresSocios, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 240, 110, -1));

        btnGenerosSocios.setText("Elegir Generos");
        btnGenerosSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerosSociosActionPerformed(evt);
            }
        });
        pSocios.add(btnGenerosSocios, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 280, 110, -1));
        pSocios.add(lblDirectoresSeleccionados, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, -1, -1));
        pSocios.add(lblActoresSeleccionados, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, -1, -1));
        pSocios.add(lblGenerosSeleccionados, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 280, -1, -1));

        tabbedPane.addTab("tab1", pSocios);

        pPrestamos.setBackground(new java.awt.Color(255, 255, 255));
        pPrestamos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setText("ID Prestamo");
        pPrestamos.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        txtPrestamoId.setEditable(false);
        pPrestamos.add(txtPrestamoId, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 140, -1));

        jLabel9.setText("Socio");
        pPrestamos.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        btnBuscarSocio.setText("Buscar Socio");
        btnBuscarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarSocioActionPerformed(evt);
            }
        });
        pPrestamos.add(btnBuscarSocio, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, -1));

        jLabel10.setText("Cinta");
        pPrestamos.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        btnBuscarCinta.setText("Buscar Cinta");
        btnBuscarCinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCintaActionPerformed(evt);
            }
        });
        pPrestamos.add(btnBuscarCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        jLabel26.setText("Fecha Prestamo");
        pPrestamos.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));
        pPrestamos.add(pickerFechaPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 140, -1));

        jLabel27.setText("Fecha Devolucion");
        pPrestamos.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));
        pPrestamos.add(pickerFechaDevolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 140, -1));

        btnAgregarPrestamo.setText("Agregar");
        btnAgregarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPrestamoActionPerformed(evt);
            }
        });
        pPrestamos.add(btnAgregarPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, -1));

        btnEditarPrestamo.setText("Editar");
        pPrestamos.add(btnEditarPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, -1));

        btnEliminarPrestamo.setText("Eliminar");
        pPrestamos.add(btnEliminarPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, -1));

        tablePrestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Prestamo", "Socio", "Pelicula", "Fecha Prestamo", "Fecha Devolucion", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tablePrestamos);

        pPrestamos.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 630, 220));

        txtBuscarPrestamo.getDocument().addDocumentListener(new DocumentListener() {
            public void buscarPrestamo() {
                String textoBusqueda = txtBuscarPrestamo.getText().trim();
                if (textoBusqueda.length() > 0) {
                    // Este método debe ser implementado para buscar en la base de datos
                    // y actualizar el modelo de la tabla con los resultados.
                    actualizarTablaPrestamosConBusqueda(textoBusqueda);
                } else {
                    // Este método debe ser implementado para restablecer la vista
                    // y mostrar todos los préstamos.
                    actualizarTablaPrestamos();
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarPrestamo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarPrestamo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarPrestamo();
            }
        });
        pPrestamos.add(txtBuscarPrestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 220, -1));

        tabbedPane.addTab("tab2", pPrestamos);

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

        pDirectores.setBackground(new java.awt.Color(255, 255, 255));
        pDirectores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Nombre:");
        pDirectores.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        txtNombreDirector.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreDirectorKeyTyped(evt);
            }
        });
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
        pDirectores.add(txtBuscarDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 240, -1));

        tabbedPane.addTab("tab4", pDirectores);

        pActores.setBackground(new java.awt.Color(255, 255, 255));
        pActores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setText("Nombre:");
        pActores.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        txtNombreActor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreActorKeyTyped(evt);
            }
        });
        pActores.add(txtNombreActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 280, -1));

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
        pActores.add(txtBuscarActor, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 280, -1));

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

        tabbedPane.addTab("tab5", pActores);

        pPeliculas.setBackground(new java.awt.Color(255, 255, 255));
        pPeliculas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setText("ID Pelicula");
        pPeliculas.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        txtIdPelicula.setEditable(false);
        pPeliculas.add(txtIdPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 120, 20));

        jLabel21.setText("Titulo");
        pPeliculas.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));
        pPeliculas.add(txtTituloPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 120, -1));

        jLabel22.setText("Director");
        pPeliculas.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        pPeliculas.add(cboDirectoresPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 120, -1));

        jLabel23.setText("Generos");
        pPeliculas.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        btnAbrirListaGeneros.setText("Abrir generos");
        btnAbrirListaGeneros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirListaGenerosActionPerformed(evt);
            }
        });
        pPeliculas.add(btnAbrirListaGeneros, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, -1, -1));

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

        pPeliculas.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 530, 150));

        btnAgregarPelicula.setText("Agregar");
        btnAgregarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPeliculaActionPerformed(evt);
            }
        });
        pPeliculas.add(btnAgregarPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));

        btnEditarPelicula.setText("Editar");
        btnEditarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarPeliculaActionPerformed(evt);
            }
        });
        pPeliculas.add(btnEditarPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, -1, -1));

        btnEliminarPelicula.setText("Eliminar");
        btnEliminarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPeliculaActionPerformed(evt);
            }
        });
        pPeliculas.add(btnEliminarPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, -1, -1));

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
        pPeliculas.add(txtBuscarPelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 260, -1));

        tabbedPane.addTab("tab6", pPeliculas);

        pCintas.setBackground(new java.awt.Color(255, 255, 255));
        pCintas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setText("ID Cinta");
        pCintas.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        txtIdCinta.setEditable(false);
        pCintas.add(txtIdCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 70, -1));

        jLabel17.setText("Pelicula");
        pCintas.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        txtPeliculaCinta.setEditable(false);
        pCintas.add(txtPeliculaCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 150, -1));

        btnBuscarPeliculaCinta.setText("Buscar Pelicula");
        btnBuscarPeliculaCinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPeliculaCintaActionPerformed(evt);
            }
        });
        pCintas.add(btnBuscarPeliculaCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, -1, -1));

        jLabel24.setText("Estado");
        pCintas.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        cboEstadoCinta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "Prestado", "Dañada", "Perdida" }));
        pCintas.add(cboEstadoCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 150, -1));

        btnAgregarCinta.setText("Agregar");
        btnAgregarCinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCintaActionPerformed(evt);
            }
        });
        pCintas.add(btnAgregarCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        btnEditarCinta.setText("Editar");
        btnEditarCinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCintaActionPerformed(evt);
            }
        });
        pCintas.add(btnEditarCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, -1, -1));

        btnEliminarCinta.setText("Eliminar");
        btnEliminarCinta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCintaActionPerformed(evt);
            }
        });
        pCintas.add(btnEliminarCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, -1));

        jLabel25.setText("Buscar");
        pCintas.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        txtBuscarCinta.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                buscar();
            }
            public void removeUpdate(DocumentEvent e) {
                buscar();
            }
            public void insertUpdate(DocumentEvent e) {
                buscar();
            }

            private void buscar() {
                String texto = txtBuscarCinta.getText();
                if (texto.trim().length() > 0) {
                    List<Cinta> resultados = cintaService.buscarCintasPorNombrePelicula(texto);
                    actualizarTablaConResultados(resultados);
                } else {
                    actualizarTablaCintas();
                }
            }
        });
        pCintas.add(txtBuscarCinta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 150, -1));

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

        pCintas.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 440, 170));

        tabbedPane.addTab("tab7", pCintas);

        add(tabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, -20, 890, 550));
    }// </editor-fold>//GEN-END:initComponents

    private void btnregistrarsocioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnregistrarsocioMouseClicked
        tabbedPane.setSelectedIndex(0);
        actualizarTablaSocios();
    }//GEN-LAST:event_btnregistrarsocioMouseClicked

    private void btnPrestamosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrestamosMouseClicked
        tabbedPane.setSelectedIndex(1);      
        actualizarTablaPrestamos();
    }//GEN-LAST:event_btnPrestamosMouseClicked

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

    private void txtFechaEntregaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaEntregaFocusLost
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

    private void btnPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrestamosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrestamosActionPerformed

    private void btnAgregarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPrestamoActionPerformed
        // Suponiendo que ya tienes los métodos para obtener Socio y Cinta por ID
        Socio socio = socioService.obtenerSocioId(Integer.parseInt(txtIdSocio.getText()));
        Cinta cinta = cintaService.obtenerCintaPorId(Integer.parseInt(txtIdCinta.getText()));
        LocalDate fechaPrestamo = pickerFechaPrestamo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaDevolucion = pickerFechaDevolucion.getDate() != null ?
            pickerFechaDevolucion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setSocio(socio);
        nuevoPrestamo.setCinta(cinta);
        nuevoPrestamo.setFechaPrestamo(fechaPrestamo);
        nuevoPrestamo.setFechaDevolucion(fechaDevolucion);

        boolean exito = prestamoService.agregarPrestamo(nuevoPrestamo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Préstamo agregado exitosamente.");
            actualizarTablaPrestamos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar el préstamo.");
        }
    }//GEN-LAST:event_btnAgregarPrestamoActionPerformed

    private void btnBuscarCintaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCintaActionPerformed
        // Crear un JDialog para buscar cintas
        JDialog buscarCintaDialog = new JDialog(this.framePrincipal, "Buscar Cinta", true); // 'this.frame' debería ser tu JFrame principal
        buscarCintaDialog.setLayout(new BorderLayout());

        // Crear modelo de tabla y tabla
        DefaultTableModel modeloTablaCintas = new DefaultTableModel(new Object[]{"ID", "Título", "Estado"}, 0);
        JTable tablaCintas = new JTable(modeloTablaCintas);

        // Llenar la tabla con datos
        List<Cinta> cintasDisponibles = cintaService.obtenerCintasDisponibles();
        for (Cinta cinta : cintasDisponibles) {
            modeloTablaCintas.addRow(new Object[]{cinta.getCintaId(), cinta.getTituloPelicula(), cinta.getEstado()});
        }

        // Agregar un JScrollPane a la tabla
        JScrollPane scrollPane = new JScrollPane(tablaCintas);
        buscarCintaDialog.add(scrollPane, BorderLayout.CENTER);

        // Panel para los botones
        JPanel botonPanel = new JPanel();
        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.addActionListener(e -> {
            // Lógica para obtener la cinta seleccionada y cerrar el diálogo
            int filaSeleccionada = tablaCintas.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int cintaId = (int) modeloTablaCintas.getValueAt(filaSeleccionada, 0);
                // Aquí podrías establecer el ID de la cinta en un campo de texto en tu panel de préstamos
                txtIdCinta.setText(String.valueOf(cintaId));
                buscarCintaDialog.dispose();
            }
        });
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> buscarCintaDialog.dispose());

        botonPanel.add(btnSeleccionar);
        botonPanel.add(btnCancelar);
        buscarCintaDialog.add(botonPanel, BorderLayout.SOUTH);

        // Configurar tamaño y visibilidad
        buscarCintaDialog.pack();
        buscarCintaDialog.setLocationRelativeTo(null); // Centrar en pantalla
        buscarCintaDialog.setVisible(true);
    }//GEN-LAST:event_btnBuscarCintaActionPerformed

    private void btnBuscarSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarSocioActionPerformed
        // Obtener el JFrame contenedor
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);


        // Crear un JDialog para buscar socios
        JDialog buscarSocioDialog = new JDialog(frame, "Buscar Socio", true);
        buscarSocioDialog.setLayout(new BorderLayout());

        // Crear modelo de tabla y tabla
        DefaultTableModel modeloTablaSocios = new DefaultTableModel(new Object[]{"ID", "Nombre", "Dirección", "Teléfono"}, 0);
        JTable tablaSocios = new JTable(modeloTablaSocios);

        // Llenar la tabla con datos
        List<Socio> socios = socioService.obtenerTodosLosSocios();
        for (Socio socio : socios) {
            modeloTablaSocios.addRow(new Object[]{socio.getSocioId(), socio.getNombre(), socio.getDireccion(), socio.getTelefono()});
        }

        // Agregar un JScrollPane a la tabla
        JScrollPane scrollPane = new JScrollPane(tablaSocios);
        buscarSocioDialog.add(scrollPane, BorderLayout.CENTER);

        // Panel para los botones
        JPanel botonPanel = new JPanel();
        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.addActionListener(e -> {
            // Lógica para obtener el socio seleccionado y cerrar el diálogo
            int filaSeleccionada = tablaSocios.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int socioId = (int) modeloTablaSocios.getValueAt(filaSeleccionada, 0);
                // Aquí podrías establecer el ID del socio en un campo de texto en tu panel de préstamos
                txtIdSocio.setText(String.valueOf(socioId));
                buscarSocioDialog.dispose();
            }
        });
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> buscarSocioDialog.dispose());

        botonPanel.add(btnSeleccionar);
        botonPanel.add(btnCancelar);
        buscarSocioDialog.add(botonPanel, BorderLayout.SOUTH);

        // Configurar tamaño y visibilidad
        buscarSocioDialog.pack();
        buscarSocioDialog.setLocationRelativeTo(null);
        buscarSocioDialog.setVisible(true);
    }//GEN-LAST:event_btnBuscarSocioActionPerformed
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
    private javax.swing.JButton btnAgregarPrestamo;
    private javax.swing.JButton btnBuscarCinta;
    private javax.swing.JButton btnBuscarPeliculaCinta;
    private javax.swing.JButton btnBuscarSocio;
    private javax.swing.JButton btnCintas;
    private javax.swing.JButton btnDirectores;
    private javax.swing.JButton btnDirectoresSocios;
    private javax.swing.JButton btnEditarActor;
    private javax.swing.JButton btnEditarCinta;
    private javax.swing.JButton btnEditarDirector;
    private javax.swing.JButton btnEditarPelicula;
    private javax.swing.JButton btnEditarPrestamo;
    private javax.swing.JButton btnEditarSocio;
    private javax.swing.JButton btnEliminarActor;
    private javax.swing.JButton btnEliminarCinta;
    private javax.swing.JButton btnEliminarDirector;
    private javax.swing.JButton btnEliminarPelicula;
    private javax.swing.JButton btnEliminarPrestamo;
    private javax.swing.JButton btnEliminarSocio;
    private javax.swing.JButton btnGenerosSocios;
    private javax.swing.JButton btnPeliculas;
    private javax.swing.JButton btnPrestamos;
    private javax.swing.JButton btnRegistrarSocio;
    private javax.swing.JButton btndevoluciones;
    private javax.swing.JButton btnregistrarsocio;
    private javax.swing.JComboBox<Director> cboDirectoresPelicula;
    private javax.swing.JComboBox<String> cboEstadoCinta;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblActoresSeleccionados;
    private javax.swing.JLabel lblDirectoresSeleccionados;
    private javax.swing.JLabel lblGenerosSeleccionados;
    private javax.swing.JPanel pActores;
    private javax.swing.JPanel pCintas;
    private javax.swing.JPanel pDevoluciones;
    private javax.swing.JPanel pDirectores;
    private javax.swing.JPanel pPeliculas;
    private javax.swing.JPanel pPrestamos;
    private javax.swing.JPanel pSocios;
    private javax.swing.JPanel panelBarra;
    private com.toedter.calendar.JDateChooser pickerFechaDevolucion;
    private com.toedter.calendar.JDateChooser pickerFechaPrestamo;
    public javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tableActores;
    private javax.swing.JTable tableCintas;
    private javax.swing.JTable tableDirectores;
    private javax.swing.JTable tablePeliculas;
    private javax.swing.JTable tablePrestamos;
    private javax.swing.JTable tableSocios;
    private javax.swing.JTextField txtBuscarActor;
    private javax.swing.JTextField txtBuscarCinta;
    private javax.swing.JTextField txtBuscarDirector;
    private javax.swing.JTextField txtBuscarPelicula;
    private javax.swing.JTextField txtBuscarPrestamo;
    private javax.swing.JTextField txtBuscarSocio;
    private javax.swing.JTextField txtDireccionSocio;
    private javax.swing.JTextField txtFechaEntrega;
    private javax.swing.JTextField txtIdCinta;
    private javax.swing.JTextField txtIdPelicula;
    private javax.swing.JTextField txtIdPrestamo;
    private javax.swing.JTextField txtIdSocio;
    private javax.swing.JTextField txtNombreActor;
    private javax.swing.JTextField txtNombreDirector;
    private javax.swing.JTextField txtNombreSocio;
    private javax.swing.JTextField txtPeliculaCinta;
    private javax.swing.JTextField txtPrestamoId;
    private javax.swing.JTextField txtTelefonoSocio;
    private javax.swing.JTextField txtTituloPelicula;
    // End of variables declaration//GEN-END:variables
   }
