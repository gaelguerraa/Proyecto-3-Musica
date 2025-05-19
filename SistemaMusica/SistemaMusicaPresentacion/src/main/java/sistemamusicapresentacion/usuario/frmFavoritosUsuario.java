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
        tablaFavoritos = new javax.swing.JTable();
        tablaFavoritos.setFont(new java.awt.Font("Gotham Black", 1, 12)); // NOI18N
        tablaFavoritos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            } // Inicializar sin nombres de columna
        ) {
            @Override
            public Class getColumnClass(int columnIndex) {
                // Asegúrate de que columnIndex esté dentro de los límites del modelo actual
                if (columnIndex < getColumnCount()) {
                    // Puedes definir la clase de columna basada en el índice si es necesario
                    // Por ejemplo:
                    // if (columnIndex == ...) { return Integer.class; }
                    return String.class; // O la clase predeterminada que necesites
                }
                return Object.class; // Valor predeterminado si el índice está fuera de rango
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false; // Por defecto, las celdas no son editables
            }
        });
        jScrollPane1.setViewportView(tablaFavoritos);
        setLocationRelativeTo(null);
        setTitle("Favoritos");
        this.control = control;
        this.usuario = usuario;
        this.LlenarTablaFavoritos();
    }
    
    

    private void LlenarTablaFavoritos() {

        String nombreBuscado = txtBuscador.getText().trim();
        String filtro = (String) comboboxFiltro.getSelectedItem();
        String idUsuario = usuario.getId();
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaFavoritos.getModel();
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (filtro.equals("ARTISTAS")) {
            String[] columnasArtista = {"Id Favorito", "Id Artista", "Nombre", "Género", "Tipo", "Agregado el"};
            modeloTabla.setColumnIdentifiers(columnasArtista);
            List<ArtistaFavoritoDTO> artistas = usuariosBO.obtenerArtistasFavoritos(idUsuario, nombreBuscado);
            for (ArtistaFavoritoDTO a : artistas) {
                modeloTabla.addRow(new Object[]{
                    a.getIdFavorito(),
                    a.getIdArtista(),
                    a.getNombreArtista(),
                    a.getGeneroArtista(),
                    a.getTipoArtista(),
                    a.getFechaAgregacion() != null ? sdf.format(a.getFechaAgregacion()) : ""
                });
            }
        } else if (filtro.equals("ALBUMES")) {
            String[] columnasAlbum = {"Id Favorito", "Id Album","Nombre", "Artista", "Género", "Lanzamiento", "Agregado el"};
            modeloTabla.setColumnIdentifiers(columnasAlbum);
            List<AlbumFavoritoDTO> albumes = usuariosBO.obtenerAlbumesFavoritos(idUsuario, nombreBuscado);
            for (AlbumFavoritoDTO a : albumes) {
                modeloTabla.addRow(new Object[]{
                    a.getIdFavorito(),
                    a.getIdAlbum(),
                    a.getNombreAlbum(),
                    a.getNombreArtista(),
                    a.getGenero(),
                    a.getFechaLanzamiento(),
                    a.getFechaAgregacion() != null ? sdf.format(a.getFechaAgregacion()) : ""
                });
            }
        } else if (filtro.equals("CANCIONES")) {
           String[] columnasCancion = {"Id Favorito", "Título", "Duración", "Artista", "Álbum", "Género", "Lanzamiento", "Agregado el"};
            modeloTabla.setColumnIdentifiers(columnasCancion);
            List<CancionFavoritaDTO> canciones = usuariosBO.obtenerCancionesFavoritas(idUsuario, nombreBuscado);
            for (CancionFavoritaDTO c : canciones) {
                modeloTabla.addRow(new Object[]{
                    c.getIdFavorito(),
                    c.getTitulo(),
                    c.getDuracion(),
                    c.getNombreArtista(),
                    c.getNombreAlbum(),
                    c.getGenero(),
                    c.getFechaLanzamiento(),
                    c.getFechaAgregacion() != null ? sdf.format(c.getFechaAgregacion()) : ""
                });
            }

        }else{
            String[] columnaGlobal = {"Id Favorito", "Id Contenido", "Nombre", "Género", "Tipo", "Agregado el"};
            modeloTabla.setColumnIdentifiers(columnaGlobal);
            List<GeneroFavoritoDTO> todo = usuariosBO.obtenerTodosFavoritos(idUsuario);
            for(GeneroFavoritoDTO g : todo){
                modeloTabla.addRow(new Object[]{
                g.getIdFavorito(),
                g.getIdContenido(),
                g.getNombre(),
                g.getGenero(),
                g.getTipo(),
                g.getFechaAgregacion()!= null ? sdf.format(g.getFechaAgregacion()) : ""
                
            });
            }
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
        btnVolver = new javax.swing.JButton();
        nombre = new javax.swing.JLabel();
        filtro = new javax.swing.JLabel();
        bntBusquedaAvanzada = new javax.swing.JButton();

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
        comboboxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CUALQUIERA", "ARTISTAS", "CANCIONES", "ALBUMES" }));

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

            }
        ));
        jScrollPane1.setViewportView(tablaFavoritos);

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

        bntBusquedaAvanzada.setBackground(new java.awt.Color(30, 215, 96));
        bntBusquedaAvanzada.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        bntBusquedaAvanzada.setText("Busqueda Avanzada");
        bntBusquedaAvanzada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntBusquedaAvanzadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addComponent(panelVerde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filtro)
                            .addComponent(comboboxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelFondoLayout.createSequentialGroup()
                                        .addGap(146, 146, 146)
                                        .addComponent(labelFavoritos, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(panelFondoLayout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(nombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addComponent(btnVolver)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bntBusquedaAvanzada))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(155, 155, 155)))
                .addContainerGap())
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
                        .addComponent(labelFavoritos, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(filtro)
                            .addComponent(nombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboboxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(bntBusquedaAvanzada))
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

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        control.mostrarModuloPrincipalUsuarios(usuario);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnAlbumesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlbumesActionPerformed
        control.mostrarAlbumesPrincipal(usuario);
        this.dispose();
    }//GEN-LAST:event_btnAlbumesActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        LlenarTablaFavoritos();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void bntBusquedaAvanzadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntBusquedaAvanzadaActionPerformed
        control.mostrarModuloFavoritosBusqueda(usuario);
        this.dispose();
    }//GEN-LAST:event_bntBusquedaAvanzadaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntBusquedaAvanzada;
    private javax.swing.JButton btnAlbumes;
    private javax.swing.JButton btnArtistas;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCanciones;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVolver;
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
