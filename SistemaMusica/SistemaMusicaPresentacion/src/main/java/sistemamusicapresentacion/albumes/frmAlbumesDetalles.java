/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistemamusicapresentacion.albumes;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistemamusica.dtos.AlbumDTO;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.CancionDTO;
import sistemamusica.dtos.FavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.TipoContenido;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.fabrica.FabricaObjetosNegocio;
import sistemamusicanegocio.interfaces.IAlbumesBO;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicanegocio.interfaces.IUsuariosBO;
import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class frmAlbumesDetalles extends javax.swing.JFrame {

    private IUsuariosBO usuariosBO = FabricaObjetosNegocio.crearUsuariosBO();
    UsuarioDTO usuarioActual;
    ControladorUniversal universal;
    AlbumDTO albumRecibido;
    private final IArtistasBO artistasBO = FabricaObjetosNegocio.crearArtistasBO();
    private final IAlbumesBO albumesBO = FabricaObjetosNegocio.crearAlbumesBO();

    /**
     * Creates new form frmAlbumesDetalles
     *
     * @param universal
     * @param albumRecibido
     * @param usuarioActual
     */
    public frmAlbumesDetalles(ControladorUniversal universal, AlbumDTO albumRecibido, UsuarioDTO usuarioActual) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Album Detalles");
        this.usuarioActual = usuarioActual;
        this.universal = universal;
        this.albumRecibido = albumRecibido;
        mostrarInfoAlbum();
        llenarTablaCanciones();
    }

    /**
     * Metodo para mostrar la informacion del album
     *
     */
    private void mostrarInfoAlbum() {

        try {
            ArtistaDTO artistaInfo = artistasBO.buscarArtistaPorId(albumRecibido.getIdArtista());

            txtNombreArtista.setText(artistaInfo.getNombre());
            txtAlbum.setText(albumRecibido.getNombre());

            try {
                String rutaImagen = albumRecibido.getImagenPortada();

                if (rutaImagen == null || rutaImagen.isEmpty()) {
                    labelFoto.setIcon(null); // Limpiar si no hay imagen
                    labelFoto.setText("Sin imagen");
                    return;
                }

                ImageIcon imagenOriginal = new ImageIcon(rutaImagen);

                Image imagenEscalada = imagenOriginal.getImage()
                        .getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                labelFoto.setIcon(null);
                labelFoto.setPreferredSize(new Dimension(80, 80));
                labelFoto.setText(""); // Quitar el texto por si habia "Sin imagen"
            } catch (Exception e) {
                labelFoto.setIcon(null);
                labelFoto.setText("Error al cargar la imagen");
                System.err.println("Error al mostrar imagen del artista: " + e.getMessage());
            }
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar info del album: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarTablaCanciones() {
        try {
            List<CancionDTO> canciones
                    = albumesBO.obtenerCancionesPorIdAlbum(albumRecibido.getId());
            System.out.println("ID ALBUM: " + albumRecibido.getId());

            DefaultTableModel modelo = (DefaultTableModel) tablaAlbumDetalles.getModel();
            modelo.setRowCount(0);
            
            for (CancionDTO c : canciones){
                Object[] fila = {
                    c.getId(),
                    c.getTitulo(),
                    c.getDuracion()
                };
                modelo.addRow(fila);
            }
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar las canciones: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String obtenerCancionSeleccionada(){
        int filaSeleccionada = tablaAlbumDetalles.getSelectedRow();
        if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una cancion de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        
        String idCancion = (String) tablaAlbumDetalles.getValueAt(filaSeleccionada, 0); 
        return idCancion;
    }
    
    public void agregarCancionAFavoritos() {
        try {
            
            String idCancion = obtenerCancionSeleccionada();
            CancionDTO cancionDTO = albumesBO.buscarCancionPorId(idCancion);
            
            String idUsuario = usuarioActual.getId();
            FavoritoDTO nuevoFavorito = new FavoritoDTO(
                    cancionDTO.getTitulo(),
                    albumRecibido.getGenero().toString(),
                    TipoContenido.CANCION,
                    cancionDTO.getId(),
                    new Date()
            );

            boolean cancionAgregada = usuariosBO.agregarFavorito(idUsuario, nuevoFavorito);

            if (cancionAgregada) {
                JOptionPane.showMessageDialog(null, "Cancion agregada a tus favoritos", "Operación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Esta cancion ya está en tus favoritos", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NegocioException e) {

            JOptionPane.showMessageDialog(null, "Error al agregar la cancion : " + e.getMessage(), "Error de negocio",
                    JOptionPane.ERROR_MESSAGE);

            System.err.println("Error de negocio al agregar favorito: " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "️Ocurrió un error inesperado", "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.err.println("Error inesperado al agregar favorito: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
     public void agregarAlbumAFavoritos() {
        try {
            String idUsuario = usuarioActual.getId();
            FavoritoDTO nuevoFavorito = new FavoritoDTO(
                    albumRecibido.getNombre(),
                    albumRecibido.getGenero().toString(),
                    TipoContenido.ALBUM,
                    albumRecibido.getId(),
                    new Date()
            );

            boolean albumAgregado = usuariosBO.agregarFavorito(idUsuario, nuevoFavorito);

            if (albumAgregado) {
                JOptionPane.showMessageDialog(null, "Album agregado a tus favoritos", "Operación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Este album ya está en tus favoritos", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NegocioException e) {

            JOptionPane.showMessageDialog(null, "Error al agregar el album: " + e.getMessage(), "Error de negocio",
                    JOptionPane.ERROR_MESSAGE);

            System.err.println("Error de negocio al agregar favorito: " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "️Ocurrió un error inesperado", "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.err.println("Error inesperado al agregar favorito: " + e.getMessage());
            e.printStackTrace();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAlbumDetalles = new javax.swing.JTable();
        labelMusicio1 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        txtNombreArtista = new javax.swing.JTextField();
        txtAlbum = new javax.swing.JTextField();
        labelAlbum = new javax.swing.JLabel();
        labelFoto = new javax.swing.JLabel();
        btnAgregarFavoritos = new javax.swing.JButton();
        btnAgregarCancionFavoritos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(850, 550));

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
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });

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

        tablaAlbumDetalles.setFont(new java.awt.Font("Gotham Black", 1, 12)); // NOI18N
        tablaAlbumDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nombre", "Duracion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaAlbumDetalles);

        labelMusicio1.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelMusicio1.setForeground(new java.awt.Color(30, 215, 96));
        labelMusicio1.setText("Music.io");

        btnVolver.setBackground(new java.awt.Color(30, 215, 96));
        btnVolver.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        txtNombreArtista.setEditable(false);
        txtNombreArtista.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        txtNombreArtista.setText("Artista");

        txtAlbum.setEditable(false);
        txtAlbum.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        txtAlbum.setText("Album");
        txtAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlbumActionPerformed(evt);
            }
        });

        labelAlbum.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelAlbum.setForeground(new java.awt.Color(30, 215, 96));
        labelAlbum.setText("Canciones Albumes");

        labelFoto.setForeground(new java.awt.Color(255, 255, 255));
        labelFoto.setText("Mostrar Foto de album aqui");

        btnAgregarFavoritos.setBackground(new java.awt.Color(255, 204, 0));
        btnAgregarFavoritos.setFont(new java.awt.Font("Gotham Black", 0, 14)); // NOI18N
        btnAgregarFavoritos.setText("Agregar Album a Favoritos");
        btnAgregarFavoritos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarFavoritosActionPerformed(evt);
            }
        });

        btnAgregarCancionFavoritos.setBackground(new java.awt.Color(255, 204, 0));
        btnAgregarCancionFavoritos.setFont(new java.awt.Font("Gotham Black", 0, 14)); // NOI18N
        btnAgregarCancionFavoritos.setText("Agregar Cancion a Favoritos");
        btnAgregarCancionFavoritos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCancionFavoritosActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoLayout.createSequentialGroup()
                                .addComponent(btnVolver)
                                .addGap(26, 26, 26)
                                .addComponent(txtNombreArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(labelFoto)
                                .addGap(280, 280, 280))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoLayout.createSequentialGroup()
                                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(panelFondoLayout.createSequentialGroup()
                                        .addComponent(btnAgregarCancionFavoritos)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAgregarFavoritos))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40))))
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(labelAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelVerde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnVolver)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNombreArtista)
                                    .addComponent(txtAlbum))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(labelFoto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarFavoritos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarCancionFavoritos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnArtistasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArtistasActionPerformed
        universal.mostrarArtistasPrincipal(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnArtistasActionPerformed

    private void btnCancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancionesActionPerformed
        universal.mostrarCanciones(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnCancionesActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        universal.mostrarAlbumesPrincipal(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlbumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlbumActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        universal.mostrarModuloPrincipalUsuarios(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnUsuarioActionPerformed

    private void btnAgregarFavoritosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarFavoritosActionPerformed
        agregarAlbumAFavoritos();
    }//GEN-LAST:event_btnAgregarFavoritosActionPerformed

    private void btnAgregarCancionFavoritosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCancionFavoritosActionPerformed
        agregarCancionAFavoritos();
    }//GEN-LAST:event_btnAgregarCancionFavoritosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCancionFavoritos;
    private javax.swing.JButton btnAgregarFavoritos;
    private javax.swing.JButton btnAlbumes;
    private javax.swing.JButton btnArtistas;
    private javax.swing.JButton btnCanciones;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVolver;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAlbum;
    private javax.swing.JLabel labelAlbumes;
    private javax.swing.JLabel labelArtistas;
    private javax.swing.JLabel labelCanciones;
    private javax.swing.JLabel labelFoto;
    private javax.swing.JLabel labelMusicio1;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelVerde;
    private javax.swing.JTable tablaAlbumDetalles;
    private javax.swing.JTextField txtAlbum;
    private javax.swing.JTextField txtNombreArtista;
    // End of variables declaration//GEN-END:variables
}
