/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistemamusicapresentacion.artistas;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.FavoritoDTO;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.TipoContenido;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.fabrica.FabricaObjetosNegocio;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicanegocio.interfaces.IUsuariosBO;
import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class frmArtistasDetalles extends javax.swing.JFrame {

    UsuarioDTO usuarioActual;
    private IUsuariosBO usuariosBO = FabricaObjetosNegocio.crearUsuariosBO();
    private IArtistasBO artistasBO = FabricaObjetosNegocio.crearArtistasBO();
    ControladorUniversal universal;
    ArtistaDTO artistaSeleccionado;

    /**
     * Creates new form frmArtistasDetalles
     *
     * @param universal
     * @param artistaSeleccionado
     * @param usuarioActual
     */
    public frmArtistasDetalles(ControladorUniversal universal, ArtistaDTO artistaSeleccionado, UsuarioDTO usuarioActual) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Artistas Detalles");
        this.universal = universal;
        this.usuarioActual = usuarioActual;
        this.artistaSeleccionado = artistasBO.buscarArtistaPorNombre(artistaSeleccionado.getNombre());
        this.mostrarInfoArtista(artistaSeleccionado);
        cbMostrarInactivos.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (cbMostrarInactivos.getSelectedItem().toString().equalsIgnoreCase("si")) {
                        LlenarTablaIntegrantesTodos();
                    } else {
                        LlenarTablaIntegrantesActivos();
                    }
                }
            }
        });
        if (cbMostrarInactivos.getSelectedItem().toString().equalsIgnoreCase("si")) {
            LlenarTablaIntegrantesTodos();
        } else {
            LlenarTablaIntegrantesActivos();
        }

    }

    public void mostrarInfoArtista(ArtistaDTO artista) {
        this.txtNombreArtista.setText(artista.getNombre());
        this.txtGenero.setText(artista.getGenero().toString());
        this.txtTipoArtista.setText(artista.getTipo().toString());

        try {
            String rutaImagen = artista.getImagen();

            if (rutaImagen == null || rutaImagen.isEmpty()) {
                labelFoto.setIcon(null); // Limpiar si no hay imagen
                labelFoto.setText("Sin imagen");
                return;
            }

            ImageIcon imagenOriginal = new ImageIcon(rutaImagen);

            Image imagenEscalada = imagenOriginal.getImage()
                    .getScaledInstance(80, 80, Image.SCALE_SMOOTH);

            labelFoto.setIcon(new ImageIcon(imagenEscalada));
            labelFoto.setPreferredSize(new Dimension(80, 80));
            labelFoto.setText(""); // Quitar texto por si había "Sin imagen"

        } catch (Exception e) {
            labelFoto.setIcon(null);
            labelFoto.setText("Error al cargar imagen");
            System.err.println("Error al mostrar imagen del artista: " + e.getMessage());
        }
    }

    private void LlenarTablaIntegrantesTodos() {
        String idArtista = artistaSeleccionado.getId().toString();
        List<IntegranteDTO> integrantes = artistasBO.consultarTodosLosIntegrantes(idArtista);

        DefaultTableModel modelo = (DefaultTableModel) tablaArtistas.getModel();
        modelo.setRowCount(0);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        for (IntegranteDTO i : integrantes) {
            Object[] fila = {
                i.getNombre(),
                i.getRol(),
                i.getFechaIngreso() != null ? formatoFecha.format(i.getFechaIngreso()) : "N/A",
                i.getFechaSalida() != null ? formatoFecha.format(i.getFechaSalida()) : "N/A"
            };
            modelo.addRow(fila);
        }
    }

    private void LlenarTablaIntegrantesActivos() {
        String idArtista = artistaSeleccionado.getId().toString();
        List<IntegranteDTO> integrantes = artistasBO.consultarIntegrantesActivos(idArtista);

        DefaultTableModel modelo = (DefaultTableModel) tablaArtistas.getModel();
        modelo.setRowCount(0);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        for (IntegranteDTO i : integrantes) {
            Object[] fila = {
                i.getNombre(),
                i.getRol(),
                i.getFechaIngreso() != null ? formatoFecha.format(i.getFechaIngreso()) : "N/A",
                i.getFechaSalida() != null ? formatoFecha.format(i.getFechaSalida()) : "N/A"
            };
            modelo.addRow(fila);
        }
    }

    public void agregarArtistaAFavoritos() {
        try {
            String idUsuario = usuarioActual.getId();
            FavoritoDTO nuevoFavorito = new FavoritoDTO(
                    artistaSeleccionado.getNombre(),
                    artistaSeleccionado.getGenero().toString(),
                    TipoContenido.ARTISTA,
                    artistaSeleccionado.getId().toString(),
                    new Date()
            );

            boolean artistaAgregado = usuariosBO.agregarFavorito(idUsuario, nuevoFavorito);

            if (artistaAgregado) {
                JOptionPane.showMessageDialog(null, "Artista agregado a tus favoritos", "Operación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Este artista ya está en tus favoritos", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NegocioException e) {

            JOptionPane.showMessageDialog(null, "Error al agregar artista: " + e.getMessage(), "Error de negocio",
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
        tablaArtistas = new javax.swing.JTable();
        labelMusicio1 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        txtNombreArtista = new javax.swing.JTextField();
        txtGenero = new javax.swing.JTextField();
        cbMostrarInactivos = new javax.swing.JComboBox<>();
        labelArtista = new javax.swing.JLabel();
        labelPregunta = new javax.swing.JLabel();
        labelFoto = new javax.swing.JLabel();
        btnAgregarFavoritos = new javax.swing.JButton();
        txtTipoArtista = new javax.swing.JTextField();

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addComponent(btnUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        tablaArtistas.setFont(new java.awt.Font("Gotham Black", 1, 12)); // NOI18N
        tablaArtistas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Rol", "Fecha de Ingreso", "Fecha de Salida "
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
        jScrollPane1.setViewportView(tablaArtistas);

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
        txtNombreArtista.setText("Nombre Artista");

        txtGenero.setEditable(false);
        txtGenero.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        txtGenero.setText("Genero Artista");

        cbMostrarInactivos.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        cbMostrarInactivos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        labelArtista.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelArtista.setForeground(new java.awt.Color(30, 215, 96));
        labelArtista.setText("Artistas");

        labelPregunta.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelPregunta.setForeground(new java.awt.Color(30, 215, 96));
        labelPregunta.setText("mostrar miembros inactivos?");

        labelFoto.setForeground(new java.awt.Color(255, 255, 255));
        labelFoto.setText("Foto de artista");

        btnAgregarFavoritos.setBackground(new java.awt.Color(255, 204, 0));
        btnAgregarFavoritos.setFont(new java.awt.Font("Gotham Black", 0, 14)); // NOI18N
        btnAgregarFavoritos.setText("Agregar a Favoritos");
        btnAgregarFavoritos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarFavoritosActionPerformed(evt);
            }
        });

        txtTipoArtista.setEditable(false);
        txtTipoArtista.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        txtTipoArtista.setText("Tipo Artista");

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addComponent(panelVerde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addComponent(btnVolver)
                                .addGap(26, 26, 26)
                                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTipoArtista)
                                    .addComponent(txtNombreArtista))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(labelFoto)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoLayout.createSequentialGroup()
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbMostrarInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelPregunta))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAgregarFavoritos))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelVerde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtTipoArtista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnVolver)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreArtista)
                            .addComponent(txtGenero)
                            .addComponent(labelFoto))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addComponent(labelPregunta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMostrarInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAgregarFavoritos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        universal.mostrarArtistasPrincipal(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnAlbumesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlbumesActionPerformed
        universal.mostrarAlbumesPrincipal(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnAlbumesActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        universal.mostrarModuloPrincipalUsuarios(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnUsuarioActionPerformed

    private void btnAgregarFavoritosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarFavoritosActionPerformed
        agregarArtistaAFavoritos();
    }//GEN-LAST:event_btnAgregarFavoritosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarFavoritos;
    private javax.swing.JButton btnAlbumes;
    private javax.swing.JButton btnArtistas;
    private javax.swing.JButton btnCanciones;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbMostrarInactivos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAlbumes;
    private javax.swing.JLabel labelArtista;
    private javax.swing.JLabel labelArtistas;
    private javax.swing.JLabel labelCanciones;
    private javax.swing.JLabel labelFoto;
    private javax.swing.JLabel labelMusicio1;
    private javax.swing.JLabel labelPregunta;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelVerde;
    private javax.swing.JTable tablaArtistas;
    private javax.swing.JTextField txtGenero;
    private javax.swing.JTextField txtNombreArtista;
    private javax.swing.JTextField txtTipoArtista;
    // End of variables declaration//GEN-END:variables
}
