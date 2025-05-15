/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistemamusicapresentacion.artistas;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Genero;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicadominio.TipoArtista;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.fabrica.FabricaObjetosNegocio;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicanegocio.interfaces.IIntegrantesBO;
import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class frmAgregarBanda extends javax.swing.JFrame {

    UsuarioDTO usuarioActual;
    private IArtistasBO artistasBO = FabricaObjetosNegocio.crearArtistasBO();
    private IIntegrantesBO integrantesBO = FabricaObjetosNegocio.crearIntegrantesBO();
    ControladorUniversal controlador;
    
    private List<Integrante> integrantes = new ArrayList<>();
    private String rutaImagenSeleccionada;
    
    /**
     * Creates new form frmAgregarBanda
     */
    public frmAgregarBanda(ControladorUniversal controlador) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Agregar Banda");
        this.controlador=controlador;
        this.artistasBO=artistasBO;
        this.integrantesBO=integrantesBO;
        LlenarComboboxGenero();
        LlenarComboboxRol();
    }
    
    public void LlenarComboboxGenero(){
        cbGenero.removeAllItems(); // Limpia los elementos actuales, por si ya hay
        for(Genero genero : Genero.values()){
            cbGenero.addItem(genero.name());
        }
    }
    
    public void LlenarComboboxRol(){
        cbRol.removeAllItems(); // Limpia los elementos actuales, por si ya hay
        for(RolIntegrante rol : RolIntegrante.values()){
            cbRol.addItem(rol.name());
        }
    }
    
    public void guardarIntegrante(){
        try {
            String nombre = txtNombreIntegrante.getText().trim();
            String rolSeleccionado = (String) this.cbRol.getSelectedItem();
            RolIntegrante rol = RolIntegrante.valueOf(rolSeleccionado);
            LocalDate fechaIng = fechaIngreso.getDate();
            LocalDate fechaSal = fechaSalida.getDate(); // Puede ser null si sigue activo
            boolean activo = checkActivo.isSelected();

            Date fechaIngDate = (fechaIng != null) 
                ? Date.from(fechaIng.atStartOfDay(ZoneId.systemDefault()).toInstant()) 
                : null;

            Date fechaSalDate = (fechaSal != null) 
                ? Date.from(fechaSal.atStartOfDay(ZoneId.systemDefault()).toInstant()) 
                : null;

            IntegranteDTO integrante = new IntegranteDTO(nombre, rol, fechaIngDate, fechaSalDate, activo);
            Integrante integrantePersistido = integrantesBO.agregarIntegrante(integrante);

            // Añadirlo a la lista local
            integrantes.add(integrantePersistido);

            // Mensaje de éxito (opcional)
            JOptionPane.showMessageDialog(this, "Integrante agregado con éxito.");
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar integrante: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String subirImagen(){
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona una imagen");

        // Filtro de archivos de imagen
        FileNameExtensionFilter filtroImagenes = new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif");
        selector.setFileFilter(filtroImagenes);

        int resultado = selector.showOpenDialog(null);

        String rutaImagen = null;
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = selector.getSelectedFile();
            rutaImagen = archivoSeleccionado.getAbsolutePath();

        }
        return rutaImagen;
    }
    
    public void agregarBanda(){
        String nombre = this.txtNombre.getText().trim();
        TipoArtista tipo =  TipoArtista.BANDA;
        
        String generoSeleccionado = (String) this.cbGenero.getSelectedItem();
        Genero genero = Genero.valueOf(generoSeleccionado);
        
        if (rutaImagenSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una imagen.");
            return;
        }
        
        ArtistaDTO nuevaBanda = new ArtistaDTO(tipo,nombre, rutaImagenSeleccionada, genero, integrantes);
        
        try {
                artistasBO.registrarBanda(nuevaBanda);
                JOptionPane.showMessageDialog(this, "Artista de banda registrado con éxito.");
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar el artista: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        panelFondo1 = new javax.swing.JPanel();
        panelVerde1 = new javax.swing.JPanel();
        btnArtistas1 = new javax.swing.JButton();
        labelArtistas = new javax.swing.JLabel();
        labelAlbumes = new javax.swing.JLabel();
        btnAlbumes = new javax.swing.JButton();
        btnCanciones = new javax.swing.JButton();
        labelCanciones = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        btnUsuario = new javax.swing.JButton();
        labelMusicio1 = new javax.swing.JLabel();
        labelRol = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        labelAgregarSolista = new javax.swing.JLabel();
        cbGenero = new javax.swing.JComboBox<>();
        labelFotoSubir = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();
        btnFoto = new javax.swing.JButton();
        btnRegistrarArtista = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnAgregarMiembro = new javax.swing.JButton();
        labelNombre1 = new javax.swing.JLabel();
        txtNombreIntegrante = new javax.swing.JTextField();
        cbRol = new javax.swing.JComboBox<>();
        labelContrasenia1 = new javax.swing.JLabel();
        labelFechaIngreso = new javax.swing.JLabel();
        fechaIngreso = new com.github.lgooddatepicker.components.DatePicker();
        labelFechaISalida = new javax.swing.JLabel();
        fechaSalida = new com.github.lgooddatepicker.components.DatePicker();
        checkActivo = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(850, 550));
        setPreferredSize(new java.awt.Dimension(850, 550));

        panelFondo1.setBackground(new java.awt.Color(0, 0, 0));

        panelVerde1.setBackground(new java.awt.Color(30, 215, 96));

        btnArtistas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/BlackStar.png"))); // NOI18N
        btnArtistas1.setBackground(new java.awt.Color(30, 215, 96));
        btnArtistas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArtistas1ActionPerformed(evt);
            }
        });

        labelArtistas.setText("Artistas");
        labelArtistas.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N

        labelAlbumes.setText("Albumes");
        labelAlbumes.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N

        btnAlbumes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/musica.png"))); // NOI18N
        btnAlbumes.setBackground(new java.awt.Color(30, 215, 96));

        btnCanciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/disco.png"))); // NOI18N
        btnCanciones.setBackground(new java.awt.Color(30, 215, 96));
        btnCanciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancionesActionPerformed(evt);
            }
        });

        labelCanciones.setText("Canciones");
        labelCanciones.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N

        labelUsuario.setText("Usuario");
        labelUsuario.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N

        btnUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario.png"))); // NOI18N
        btnUsuario.setBackground(new java.awt.Color(30, 215, 96));
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelVerde1Layout = new javax.swing.GroupLayout(panelVerde1);
        panelVerde1.setLayout(panelVerde1Layout);
        panelVerde1Layout.setHorizontalGroup(
            panelVerde1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVerde1Layout.createSequentialGroup()
                .addGroup(panelVerde1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVerde1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(panelVerde1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCanciones)
                            .addGroup(panelVerde1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(panelVerde1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelAlbumes)
                                    .addGroup(panelVerde1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnCanciones)
                                        .addComponent(btnAlbumes)
                                        .addComponent(btnArtistas1))
                                    .addComponent(labelArtistas)))))
                    .addGroup(panelVerde1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panelVerde1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelUsuario)
                            .addComponent(btnUsuario))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelVerde1Layout.setVerticalGroup(
            panelVerde1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVerde1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnArtistas1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelArtistas, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(btnAlbumes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelAlbumes, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCanciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCanciones, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        labelMusicio1.setText("Music.io");
        labelMusicio1.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelMusicio1.setForeground(new java.awt.Color(30, 215, 96));

        labelRol.setText("rol:");
        labelRol.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelRol.setForeground(new java.awt.Color(30, 215, 96));

        labelAgregarSolista.setText("Agregar Banda");
        labelAgregarSolista.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelAgregarSolista.setForeground(new java.awt.Color(30, 215, 96));

        cbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbGenero.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        cbGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGeneroActionPerformed(evt);
            }
        });

        labelFotoSubir.setText("subir foto");
        labelFotoSubir.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        labelFotoSubir.setForeground(new java.awt.Color(30, 215, 96));

        labelNombre.setText("nombre de la banda:");
        labelNombre.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelNombre.setForeground(new java.awt.Color(30, 215, 96));

        btnFoto.setText("boton para subir foto");
        btnFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFotoActionPerformed(evt);
            }
        });

        btnRegistrarArtista.setText("Guardar Artista");
        btnRegistrarArtista.setBackground(new java.awt.Color(30, 215, 96));
        btnRegistrarArtista.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        btnRegistrarArtista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarArtistaActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.setBackground(new java.awt.Color(30, 215, 96));
        btnVolver.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnAgregarMiembro.setText("Agregar Miembro");
        btnAgregarMiembro.setBackground(new java.awt.Color(30, 215, 96));
        btnAgregarMiembro.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        btnAgregarMiembro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMiembroActionPerformed(evt);
            }
        });

        labelNombre1.setText("nombre del integrante:");
        labelNombre1.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelNombre1.setForeground(new java.awt.Color(30, 215, 96));

        cbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbRol.setFont(new java.awt.Font("Gotham Black", 1, 12)); // NOI18N
        cbRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRolActionPerformed(evt);
            }
        });

        labelContrasenia1.setText("genero:");
        labelContrasenia1.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelContrasenia1.setForeground(new java.awt.Color(30, 215, 96));

        labelFechaIngreso.setText("fecha de ingreso:");
        labelFechaIngreso.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelFechaIngreso.setForeground(new java.awt.Color(30, 215, 96));

        labelFechaISalida.setText("fecha de salida:");
        labelFechaISalida.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelFechaISalida.setForeground(new java.awt.Color(30, 215, 96));

        checkActivo.setText("Miembro Activo?");
        checkActivo.setBackground(new java.awt.Color(0, 0, 0));
        checkActivo.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N
        checkActivo.setForeground(new java.awt.Color(30, 215, 96));
        checkActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFondo1Layout = new javax.swing.GroupLayout(panelFondo1);
        panelFondo1.setLayout(panelFondo1Layout);
        panelFondo1Layout.setHorizontalGroup(
            panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondo1Layout.createSequentialGroup()
                .addComponent(panelVerde1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFondo1Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelNombre)
                                    .addComponent(labelContrasenia1)))
                            .addGroup(panelFondo1Layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(labelFotoSubir)))
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFondo1Layout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelNombre1)
                                    .addComponent(txtNombreIntegrante, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelRol))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelFondo1Layout.createSequentialGroup()
                                .addGap(0, 90, Short.MAX_VALUE)
                                .addComponent(labelFechaIngreso)
                                .addGap(97, 97, 97)
                                .addComponent(labelFechaISalida)
                                .addGap(62, 62, 62))))
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondo1Layout.createSequentialGroup()
                                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRegistrarArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAgregarMiembro, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(93, 93, 93))
                            .addGroup(panelFondo1Layout.createSequentialGroup()
                                .addComponent(btnFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(checkActivo)
                                .addGap(141, 141, 141))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondo1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondo1Layout.createSequentialGroup()
                                .addComponent(fechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(234, 234, 234))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondo1Layout.createSequentialGroup()
                                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(fechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelFondo1Layout.createSequentialGroup()
                                        .addComponent(labelAgregarSolista, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(24, 24, 24))))))
        );
        panelFondo1Layout.setVerticalGroup(
            panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelVerde1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelFondo1Layout.createSequentialGroup()
                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(labelAgregarSolista, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNombre)
                            .addComponent(labelNombre1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreIntegrante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelRol)
                            .addComponent(labelContrasenia1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelFotoSubir)
                            .addComponent(labelFechaIngreso)
                            .addComponent(labelFechaISalida))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)
                        .addGap(41, 41, 41))
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(checkActivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(btnAgregarMiembro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRegistrarArtista)
                        .addGap(23, 23, 23))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnArtistas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArtistas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnArtistas1ActionPerformed

    private void btnCancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancionesActionPerformed

    private void cbGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGeneroActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        controlador.mostrarArtistasPrincipal(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnRegistrarArtistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarArtistaActionPerformed
        agregarBanda();
    }//GEN-LAST:event_btnRegistrarArtistaActionPerformed

    private void btnFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFotoActionPerformed
        this.rutaImagenSeleccionada = subirImagen();
        if (rutaImagenSeleccionada != null) {
            JOptionPane.showMessageDialog(this, "Imagen seleccionada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "No se seleccionó ninguna imagen.");
        }
    }//GEN-LAST:event_btnFotoActionPerformed

    private void btnAgregarMiembroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMiembroActionPerformed
        guardarIntegrante();
    }//GEN-LAST:event_btnAgregarMiembroActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsuarioActionPerformed

    private void cbRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbRolActionPerformed

    private void checkActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActivoActionPerformed
        if (checkActivo.isSelected()) {
            fechaSalida.setEnabled(false);  // Deshabilitar el JDatePicker o JTextField
            fechaSalida.setDate(null);     // Opcional: Limpiar la fecha seleccionada
        } else {
            fechaSalida.setEnabled(true);   // Habilitar el JDatePicker o JTextField
        }
    }//GEN-LAST:event_checkActivoActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarMiembro;
    private javax.swing.JButton btnAlbumes;
    private javax.swing.JButton btnArtistas1;
    private javax.swing.JButton btnCanciones;
    private javax.swing.JButton btnFoto;
    private javax.swing.JButton btnRegistrarArtista;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbGenero;
    private javax.swing.JComboBox<String> cbRol;
    private javax.swing.JCheckBox checkActivo;
    private com.github.lgooddatepicker.components.DatePicker fechaIngreso;
    private com.github.lgooddatepicker.components.DatePicker fechaSalida;
    private javax.swing.JLabel labelAgregarSolista;
    private javax.swing.JLabel labelAlbumes;
    private javax.swing.JLabel labelArtistas;
    private javax.swing.JLabel labelCanciones;
    private javax.swing.JLabel labelContrasenia1;
    private javax.swing.JLabel labelFechaISalida;
    private javax.swing.JLabel labelFechaIngreso;
    private javax.swing.JLabel labelFotoSubir;
    private javax.swing.JLabel labelMusicio1;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelNombre1;
    private javax.swing.JLabel labelRol;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel panelFondo1;
    private javax.swing.JPanel panelVerde1;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreIntegrante;
    // End of variables declaration//GEN-END:variables
}
