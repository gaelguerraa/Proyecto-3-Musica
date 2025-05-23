/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistemamusicapresentacion.albumes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistemamusica.dtos.AlbumDTO;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.fabrica.FabricaObjetosNegocio;
import sistemamusicanegocio.interfaces.IAlbumesBO;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class frmAlbumesPrincipal extends javax.swing.JFrame {

    UsuarioDTO usuarioActual;
    ControladorUniversal universal;
    private IAlbumesBO albumesBO = FabricaObjetosNegocio.crearAlbumesBO();
    private IArtistasBO artistasBO = FabricaObjetosNegocio.crearArtistasBO();

    /**
     * Creates new form frmAlbumesPrincipal
     *
     * @param universal
     * @param usuarioActual
     */
    public frmAlbumesPrincipal(ControladorUniversal universal, UsuarioDTO usuarioActual) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Albumes");
        this.usuarioActual = usuarioActual;
        this.universal = universal;
        seleccionarAlbum();
        llenarTablaAlbumes();
    }

    /**
     * Mertodo para seleccionar un album de la tabla de albumes
     */
    private void seleccionarAlbum() {
        this.tablaAlbumes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tablaAlbumes.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        String nombreAlbum = (String) tablaAlbumes.getValueAt(selectedRow, 0);
                        AlbumDTO albumSeleccionado = albumesBO.obtenerAlbumPorNombre(nombreAlbum);
                        System.out.println(nombreAlbum);
                        System.out.println("ID ALBUM SELECCIONADO: " + albumSeleccionado.getId());
                        universal.mostrarAlbumDetalles(usuarioActual, albumSeleccionado);
                        this.dispose();
                    } catch (NegocioException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error al seleccionar album: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void llenarTablaAlbumes() {
        String filtroSeleccionado = (String) comboboxFiltro.getSelectedItem();
        String textoBuscado = txtBuscador.getText().trim();

        List<AlbumDTO> albumesConsultados = new ArrayList<>();

        try {
            if (textoBuscado.isEmpty()) {
                albumesConsultados = albumesBO.obtenerAlbumes(usuarioActual.getId());
            } else {
                switch (filtroSeleccionado) {
                    case "Genero":
                        albumesConsultados
                                = albumesBO.obtenerAlbumesPorGenero(usuarioActual.getId(),textoBuscado);
                        break;
                    case "Nombre":
                        albumesConsultados
                                = albumesBO.obtenerAlbumesPorNombre(usuarioActual.getId(),textoBuscado);
                        break;
                    case "Fecha de Lanzamiento":
                        albumesConsultados
                                = albumesBO.obtenerAlgumesPorFecha(usuarioActual.getId(), textoBuscado);
                        break;
                    default:
                        albumesConsultados = albumesBO.obtenerAlbumes(usuarioActual.getId());
                        break;
                }
            }

            DefaultTableModel modeloTabla = (DefaultTableModel) tablaAlbumes.getModel();
            modeloTabla.setRowCount(0);

            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

            for (AlbumDTO album : albumesConsultados) {

                ArtistaDTO artistaId = artistasBO.buscarArtistaPorId(album.getIdArtista());

                String fechaFormateada = formatoFecha.format(album.getFechaLanzamiento());

                Object[] fila = {
                    album.getNombre(),
                    album.getGenero(),
                    artistaId.getNombre(),
                    fechaFormateada
                };
                modeloTabla.addRow(fila);
            }
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar albumes: "
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        textoAlbumes = new javax.swing.JLabel();
        comboboxFiltro = new javax.swing.JComboBox<>();
        txtBuscador = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAlbumes = new javax.swing.JTable();
        btnAgregarAlbum = new javax.swing.JButton();

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

        labelMusicio1.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelMusicio1.setForeground(new java.awt.Color(30, 215, 96));
        labelMusicio1.setText("Music.io");

        textoAlbumes.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        textoAlbumes.setForeground(new java.awt.Color(30, 215, 96));
        textoAlbumes.setText("Albumes");

        comboboxFiltro.setBackground(new java.awt.Color(30, 215, 96));
        comboboxFiltro.setFont(new java.awt.Font("Gotham Black", 1, 14)); // NOI18N
        comboboxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Genero", "Fecha de Lanzamiento", "Nombre" }));

        btnBuscar.setBackground(new java.awt.Color(30, 215, 96));
        btnBuscar.setFont(new java.awt.Font("Gotham Black", 0, 18)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tablaAlbumes.setFont(new java.awt.Font("Gotham Black", 1, 12)); // NOI18N
        tablaAlbumes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Genero", "Artista", "Fecha Lanzamiento"
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
        jScrollPane1.setViewportView(tablaAlbumes);

        btnAgregarAlbum.setBackground(new java.awt.Color(30, 215, 96));
        btnAgregarAlbum.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        btnAgregarAlbum.setText("Agregar Album");
        btnAgregarAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAlbumActionPerformed(evt);
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
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addComponent(comboboxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelFondoLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(textoAlbumes, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(107, 107, 107)
                                        .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                                    .addGroup(panelFondoLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(12, Short.MAX_VALUE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregarAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(223, 223, 223))))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelVerde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoAlbumes, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboboxFiltro)
                    .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarAlbum)
                .addContainerGap(22, Short.MAX_VALUE))
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
        universal.mostrarArtistasPrincipal(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnArtistasActionPerformed

    private void btnCancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancionesActionPerformed
        universal.mostrarCanciones(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnCancionesActionPerformed

    private void btnAgregarAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAlbumActionPerformed
        universal.mostrarAgregarAlbum(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnAgregarAlbumActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        universal.mostrarModuloPrincipalUsuarios(usuarioActual);
        this.dispose();
    }//GEN-LAST:event_btnUsuarioActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        llenarTablaAlbumes();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarAlbum;
    private javax.swing.JButton btnAlbumes;
    private javax.swing.JButton btnArtistas;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCanciones;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JComboBox<String> comboboxFiltro;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAlbumes;
    private javax.swing.JLabel labelArtistas;
    private javax.swing.JLabel labelCanciones;
    private javax.swing.JLabel labelMusicio1;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelVerde;
    private javax.swing.JTable tablaAlbumes;
    private javax.swing.JLabel textoAlbumes;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
