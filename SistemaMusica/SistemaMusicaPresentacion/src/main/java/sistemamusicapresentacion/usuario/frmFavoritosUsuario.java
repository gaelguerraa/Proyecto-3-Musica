/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistemamusicapresentacion.usuario;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import sistemamusica.dtos.AlbumFavoritoDTO;
import sistemamusica.dtos.ArtistaFavoritoDTO;
import sistemamusica.dtos.CancionFavoritaDTO;
import sistemamusica.dtos.GeneroFavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Genero;
import sistemamusicanegocio.fabrica.FabricaObjetosNegocio;
import sistemamusicanegocio.interfaces.IUsuariosBO;
import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class frmFavoritosUsuario extends javax.swing.JFrame {

    private final IUsuariosBO usuariosBO = FabricaObjetosNegocio.crearUsuariosBO();
    ControladorUniversal control;
    UsuarioDTO usuario;

    /**
     * Creates new form frmFavoritosUsuario
     */
    public frmFavoritosUsuario(ControladorUniversal control, UsuarioDTO usuario) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Favoritos");
        this.control = control;
        this.usuario = usuario;
        LlenarComboboxGenero();
        mostrarTodo();
        this.llenarTablaFavoritos();
    }
    
    public void LlenarComboboxGenero(){
        cbGenero.removeAllItems(); 
        for(Genero genero : Genero.values()){
            cbGenero.addItem(genero.name());
        }
    }
    

    
    public void mostrarTodo(){
        String idUsuario = usuario.getId();
        List<GeneroFavoritoDTO> resultados = usuariosBO.obtenerTodosFavoritos(idUsuario);
        
        DefaultTableModel modelo = (DefaultTableModel) tablaFavoritos.getModel();
            modelo.setRowCount(0); // Limpiar tabla

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for(GeneroFavoritoDTO gf : resultados) {
                modelo.addRow(new Object[]{
                    gf.getTipo(),
                    gf.getNombre(),
                    gf.getGenero(),
                    gf.getFechaAgregacion() != null ? sdf.format(gf.getFechaAgregacion()) : ""
                });
            }
    }

    public void buscarPorGenero() {
    
            String idUsuario = usuario.getId();
            String genero = (String) this.cbGenero.getSelectedItem();

            List<GeneroFavoritoDTO> resultados = usuariosBO.obtenerGenerosFavoritos(idUsuario, genero);

            DefaultTableModel modelo = (DefaultTableModel) tablaFavoritos.getModel();
            modelo.setRowCount(0); // Limpiar tabla

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for(GeneroFavoritoDTO gf : resultados) {
                modelo.addRow(new Object[]{
                    gf.getTipo(),
                    gf.getNombre(),
                    gf.getGenero(),
                    gf.getFechaAgregacion() != null ? sdf.format(gf.getFechaAgregacion()) : ""
                });
            }

    }
    
    private void llenarTablaFavoritos() {
    String tipoSeleccionado = (String) comboboxFiltro.getSelectedItem(); // "CANCION", "ALBUM", "ARTISTA"
    String nombreBuscado = txtBuscador.getText().trim();
    String idUsuario = usuario.getId();
    DefaultTableModel modeloTabla = (DefaultTableModel) tablaFavoritos.getModel();
    modeloTabla.setRowCount(0); // Limpiar la tabla

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    switch (tipoSeleccionado) {
        case "CANCION":
            String[] columnasCancion = {"Título", "Duración", "Artista", "Álbum", "Género", "Lanzamiento", "Agregado el"};
            modeloTabla.setColumnIdentifiers(columnasCancion);
            List<CancionFavoritaDTO> canciones = usuariosBO.obtenerCancionesFavoritas(idUsuario, nombreBuscado);
            for (CancionFavoritaDTO c : canciones) {
                modeloTabla.addRow(new Object[]{
                    c.getTitulo(),
                    c.getDuracion(),
                    c.getNombreArtista(),
                    c.getNombreAlbum(),
                    c.getGenero(),
                    c.getFechaLanzamiento(),
                    c.getFechaAgregacion() != null ? sdf.format(c.getFechaAgregacion()) : ""
                });
            }
            break;

        case "ALBUM":
            String[] columnasAlbum = {"Nombre", "Artista", "Género", "Lanzamiento", "Agregado el"};
            modeloTabla.setColumnIdentifiers(columnasAlbum);
            List<AlbumFavoritoDTO> albumes = usuariosBO.obtenerAlbumesFavoritos(idUsuario, nombreBuscado);
            for (AlbumFavoritoDTO a : albumes) {
                modeloTabla.addRow(new Object[]{
                    a.getNombreAlbum(),
                    a.getNombreArtista(),
                    a.getGenero(),
                    a.getFechaLanzamiento(),
                    a.getFechaAgregacion() != null ? sdf.format(a.getFechaAgregacion()) : ""
                });
            }
            break;

        case "ARTISTA":
            String[] columnasArtista = {"Nombre", "Género", "Tipo", "Agregado el"};
            modeloTabla.setColumnIdentifiers(columnasArtista);
            List<ArtistaFavoritoDTO> artistas = usuariosBO.obtenerArtistasFavoritos(idUsuario, nombreBuscado);
            for (ArtistaFavoritoDTO a : artistas) {
                modeloTabla.addRow(new Object[]{
                    a.getNombreArtista(),
                    a.getGeneroArtista(),
                    a.getTipoArtista(),
                    a.getFechaAgregacion() != null ? sdf.format(a.getFechaAgregacion()) : ""
                });
            }
            break;
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

        panelFondo = new javax.swing.JPanel();
        panelVerde = new javax.swing.JPanel();
        btnArtistas = new javax.swing.JButton();
        labelArtistas = new javax.swing.JLabel();
        labelAlbumes = new javax.swing.JLabel();
        btnAlbumes = new javax.swing.JButton();
        btnCanciones = new javax.swing.JButton();
        labelCanciones = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        btnUsuario = new javax.swing.JButton();
        labelMusicio1 = new javax.swing.JLabel();
        labelFavoritos = new javax.swing.JLabel();
        comboboxFiltro = new javax.swing.JComboBox<>();
        txtBuscador = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFavoritos = new javax.swing.JTable();
        btnBorrarFavorito = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        nombre = new javax.swing.JLabel();
        filtro = new javax.swing.JLabel();
        cbGenero = new javax.swing.JComboBox<>();
        btnBuscar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelFondo.setBackground(new java.awt.Color(0, 0, 0));

        panelVerde.setBackground(new java.awt.Color(30, 215, 96));

        btnArtistas.setBackground(new java.awt.Color(30, 215, 96));
        btnArtistas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/BlackStar.png"))); // NOI18N
        btnArtistas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArtistasActionPerformed(evt);
            }
        });

        labelArtistas.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N
        labelArtistas.setText("Artistas");

        labelAlbumes.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N
        labelAlbumes.setText("Albumes");

        btnAlbumes.setBackground(new java.awt.Color(30, 215, 96));
        btnAlbumes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/musica.png"))); // NOI18N
        btnAlbumes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlbumesActionPerformed(evt);
            }
        });

        btnCanciones.setBackground(new java.awt.Color(30, 215, 96));
        btnCanciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/disco.png"))); // NOI18N
        btnCanciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancionesActionPerformed(evt);
            }
        });

        labelCanciones.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N
        labelCanciones.setText("Canciones");

        labelUsuario.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N
        labelUsuario.setText("Usuario");

        btnUsuario.setBackground(new java.awt.Color(30, 215, 96));
        btnUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario.png"))); // NOI18N

        javax.swing.GroupLayout panelVerdeLayout = new javax.swing.GroupLayout(panelVerde);
        panelVerde.setLayout(panelVerdeLayout);
        panelVerdeLayout.setHorizontalGroup(
            panelVerdeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVerdeLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panelVerdeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCanciones)
                    .addGroup(panelVerdeLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelVerdeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelUsuario)
                            .addComponent(btnUsuario)))
                    .addGroup(panelVerdeLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelVerdeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAlbumes)
                            .addGroup(panelVerdeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnCanciones)
                                .addComponent(btnAlbumes)
                                .addComponent(btnArtistas))
                            .addComponent(labelArtistas))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelVerdeLayout.setVerticalGroup(
            panelVerdeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVerdeLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnArtistas)
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
                .addGap(15, 15, 15))
        );

        labelMusicio1.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelMusicio1.setForeground(new java.awt.Color(30, 215, 96));
        labelMusicio1.setText("Music.io");

        labelFavoritos.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelFavoritos.setForeground(new java.awt.Color(30, 215, 96));
        labelFavoritos.setText("Favoritos");

        comboboxFiltro.setBackground(new java.awt.Color(30, 215, 96));
        comboboxFiltro.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        comboboxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ARTISTAS", "CANCIONES", "ALBUMES" }));

        btnBuscar.setBackground(new java.awt.Color(30, 215, 96));
        btnBuscar.setFont(new java.awt.Font("Gotham Black", 0, 18)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tablaFavoritos.setFont(new java.awt.Font("Gotham Black", 1, 12)); // NOI18N
        tablaFavoritos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo", "Nombre", "Genero", "Fecha de Agregacion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaFavoritos);

        btnBorrarFavorito.setBackground(new java.awt.Color(30, 215, 96));
        btnBorrarFavorito.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        btnBorrarFavorito.setText("Borrar Favorito");
        btnBorrarFavorito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarFavoritoActionPerformed(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(30, 215, 96));
        btnVolver.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        nombre.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        nombre.setForeground(new java.awt.Color(30, 215, 96));
        nombre.setText("nombre:");

        filtro.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        filtro.setForeground(new java.awt.Color(30, 215, 96));
        filtro.setText("contenido:");

        cbGenero.setBackground(new java.awt.Color(30, 215, 96));
        cbGenero.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        cbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ARTISTAS", "CANCIONES", "ALBUMES" }));

        btnBuscar1.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscar1.setFont(new java.awt.Font("Gotham Black", 0, 10)); // NOI18N
        btnBuscar1.setForeground(new java.awt.Color(30, 215, 96));
        btnBuscar1.setText("Buscar por Genero");
        btnBuscar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addComponent(panelVerde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addComponent(btnVolver)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBorrarFavorito, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(220, 220, 220))
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addComponent(filtro)
                                .addGap(82, 82, 82)
                                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelFondoLayout.createSequentialGroup()
                                        .addComponent(nombre)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(labelFavoritos, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addComponent(comboboxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelVerde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoLayout.createSequentialGroup()
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoLayout.createSequentialGroup()
                                .addComponent(labelFavoritos, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(filtro)
                                    .addComponent(nombre)))
                            .addComponent(btnBuscar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbGenero, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(comboboxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrarFavorito)
                    .addComponent(btnVolver))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnArtistasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArtistasActionPerformed
        control.mostrarArtistasPrincipal(usuario);
        this.dispose();
    }//GEN-LAST:event_btnArtistasActionPerformed

    private void btnCancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancionesActionPerformed
        control.mostrarCanciones(usuario);
        this.dispose();
    }//GEN-LAST:event_btnCancionesActionPerformed

    private void btnBorrarFavoritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarFavoritoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBorrarFavoritoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        control.mostrarModuloPrincipalUsuarios(usuario);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnAlbumesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlbumesActionPerformed
        control.mostrarAlbumesPrincipal(usuario);
        this.dispose();
    }//GEN-LAST:event_btnAlbumesActionPerformed

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed
        buscarPorGenero();
    }//GEN-LAST:event_btnBuscar1ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        llenarTablaFavoritos();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlbumes;
    private javax.swing.JButton btnArtistas;
    private javax.swing.JButton btnBorrarFavorito;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnCanciones;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbGenero;
    private javax.swing.JComboBox<String> comboboxFiltro;
    private javax.swing.JLabel filtro;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAlbumes;
    private javax.swing.JLabel labelArtistas;
    private javax.swing.JLabel labelCanciones;
    private javax.swing.JLabel labelFavoritos;
    private javax.swing.JLabel labelMusicio1;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JLabel nombre;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelVerde;
    private javax.swing.JTable tablaFavoritos;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
