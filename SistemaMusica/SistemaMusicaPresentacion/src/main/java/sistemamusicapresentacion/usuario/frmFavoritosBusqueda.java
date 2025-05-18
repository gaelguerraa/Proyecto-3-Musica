/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistemamusicapresentacion.usuario;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistemamusica.dtos.GeneroFavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Favorito;
import sistemamusicadominio.Genero;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.fabrica.FabricaObjetosNegocio;
import sistemamusicanegocio.interfaces.IUsuariosBO;
import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class frmFavoritosBusqueda extends javax.swing.JFrame {

    private final IUsuariosBO usuariosBO = FabricaObjetosNegocio.crearUsuariosBO();
    ControladorUniversal control;
    UsuarioDTO usuario;
    
    /**
     * Creates new form frmFavoritosBusqueda
     */
    public frmFavoritosBusqueda(ControladorUniversal control, UsuarioDTO usuario) {
        this.control=control;
        this.usuario=usuario;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Busqueda Favoritos");
        mostrarTodo();
        LlenarComboboxGenero();
        buscarPorGenero();

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
                    gf.getIdContenido(),
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
                    gf.getIdContenido(),
                    gf.getTipo(),
                    gf.getNombre(),
                    gf.getGenero(),
                    gf.getFechaAgregacion() != null ? sdf.format(gf.getFechaAgregacion()) : ""
                });
            }

    }
    
    private void buscarPorFecha(){
        String idUsuario = usuario.getId();
        LocalDate fechaIng = fecha1.getDate();
        LocalDate fechaSal = fecha2.getDate(); 


        Date fechaIngDate = (fechaIng != null) 
            ? Date.from(fechaIng.atStartOfDay(ZoneId.systemDefault()).toInstant()) 
            : null;

        Date fechaSalDate = (fechaSal != null) 
            ? Date.from(fechaSal.atStartOfDay(ZoneId.systemDefault()).toInstant()) 
            : null;
            
        List<GeneroFavoritoDTO> resultados = usuariosBO.consultarFavoritosPorRangoFechas(idUsuario, fechaIngDate, fechaSalDate);
            
        DefaultTableModel modelo = (DefaultTableModel) tablaFavoritos.getModel();
            modelo.setRowCount(0); // Limpiar tabla

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for(GeneroFavoritoDTO gf : resultados) {
                modelo.addRow(new Object[]{
                    gf.getIdContenido(),
                    gf.getTipo(),
                    gf.getNombre(),
                    gf.getGenero(),
                    gf.getFechaAgregacion() != null ? sdf.format(gf.getFechaAgregacion()) : ""
                });
            }    
    } 
    
    private String obtenerFavoritoSeleccionado(){
        int filaSeleccionada = tablaFavoritos.getSelectedRow();
        if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un favorito de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        
        String id = (String) tablaFavoritos.getValueAt(filaSeleccionada, 0); 
        return id;

   

    }
    
    public void eliminarFavorito(){
        String idContenido = obtenerFavoritoSeleccionado();
        if (idContenido != null) {
            try {
                usuariosBO.eliminarFavorito(usuario.getId(), idContenido);
                JOptionPane.showMessageDialog(this, "Favorito eliminado correctamente");
                mostrarTodo(); // o llenarTablaFavoritos()
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar favorito: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFavoritos = new javax.swing.JTable();
        btnBorrarFavorito = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        cbGenero = new javax.swing.JComboBox<>();
        btnBuscar1 = new javax.swing.JButton();
        filtro = new javax.swing.JLabel();
        fecha1 = new com.github.lgooddatepicker.components.DatePicker();
        fecha2 = new com.github.lgooddatepicker.components.DatePicker();
        filtro1 = new javax.swing.JLabel();
        btnBuscarFecha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(838, 550));

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

        tablaFavoritos.setFont(new java.awt.Font("Gotham Black", 1, 12)); // NOI18N
        tablaFavoritos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Contenido", "Tipo", "Nombre", "Genero", "Fecha de Agregacion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        filtro.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        filtro.setForeground(new java.awt.Color(30, 215, 96));
        filtro.setText("genero:");

        filtro1.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        filtro1.setForeground(new java.awt.Color(30, 215, 96));
        filtro1.setText("fechas de agregacion: ");

        btnBuscarFecha.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarFecha.setFont(new java.awt.Font("Gotham Black", 0, 10)); // NOI18N
        btnBuscarFecha.setForeground(new java.awt.Color(30, 215, 96));
        btnBuscarFecha.setText("Buscar por Fecha");
        btnBuscarFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnBuscarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaActionPerformed(evt);
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
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addComponent(btnVolver)
                                .addGap(368, 368, 368)
                                .addComponent(btnBorrarFavorito, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filtro))
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelFondoLayout.createSequentialGroup()
                                        .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelFondoLayout.createSequentialGroup()
                                        .addComponent(filtro1)
                                        .addGap(34, 34, 34)
                                        .addComponent(btnBuscarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelFondoLayout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(labelFavoritos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(103, 103, 103)
                                .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelVerde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(filtro)
                        .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelFavoritos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbGenero)))
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(filtro1)
                            .addComponent(btnBuscarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrarFavorito)
                    .addComponent(btnVolver))
                .addGap(12, 12, 12))
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

    private void btnAlbumesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlbumesActionPerformed
        control.mostrarAlbumesPrincipal(usuario);
        this.dispose();
    }//GEN-LAST:event_btnAlbumesActionPerformed

    private void btnCancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancionesActionPerformed
        control.mostrarCanciones(usuario);
        this.dispose();
    }//GEN-LAST:event_btnCancionesActionPerformed

    private void btnBorrarFavoritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarFavoritoActionPerformed
        eliminarFavorito();
    }//GEN-LAST:event_btnBorrarFavoritoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        control.mostrarModuloFavoritosUsuario(usuario);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed
        buscarPorGenero();
    }//GEN-LAST:event_btnBuscar1ActionPerformed

    private void btnBuscarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFechaActionPerformed
        buscarPorFecha();
    }//GEN-LAST:event_btnBuscarFechaActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlbumes;
    private javax.swing.JButton btnArtistas;
    private javax.swing.JButton btnBorrarFavorito;
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnBuscarFecha;
    private javax.swing.JButton btnCanciones;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbGenero;
    private com.github.lgooddatepicker.components.DatePicker fecha1;
    private com.github.lgooddatepicker.components.DatePicker fecha2;
    private javax.swing.JLabel filtro;
    private javax.swing.JLabel filtro1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAlbumes;
    private javax.swing.JLabel labelArtistas;
    private javax.swing.JLabel labelCanciones;
    private javax.swing.JLabel labelFavoritos;
    private javax.swing.JLabel labelMusicio1;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelVerde;
    private javax.swing.JTable tablaFavoritos;
    // End of variables declaration//GEN-END:variables
}
