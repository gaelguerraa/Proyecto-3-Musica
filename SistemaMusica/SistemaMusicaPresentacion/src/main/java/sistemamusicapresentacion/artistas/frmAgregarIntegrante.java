/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistemamusicapresentacion.artistas;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.fabrica.FabricaObjetosNegocio;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class frmAgregarIntegrante extends javax.swing.JFrame {

    UsuarioDTO usuario;
    ControladorUniversal controlador;
    Artista banda;
    private IArtistasBO artistasBO = FabricaObjetosNegocio.crearArtistasBO();
    private int contadorIntegrantes = 0;

    /**
     * Creates new form frmAgregarIntegrante
     *
     * @param controlador
     * @param banda
     * @param usuario
     */
    public frmAgregarIntegrante(ControladorUniversal controlador, Artista banda, UsuarioDTO usuario) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Agregar Integrantes");
        this.controlador = controlador;
        this.usuario = usuario;
        this.banda = banda;
        LlenarComboboxRol();
        btnRegistrarArtista.setEnabled(false);
    }

    public void LlenarComboboxRol() {
        cbRol.removeAllItems();
        for (RolIntegrante rol : RolIntegrante.values()) {
            cbRol.addItem(rol.name());
        }
    }

    public void guardarIntegrante() {
        String nombre = txtNombreIntegrante.getText().trim();
        String rolSeleccionado = (String) this.cbRol.getSelectedItem();
        LocalDate fechaIng = fechaIngreso.getDate();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del integrante es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (rolSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un rol para el integrante.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fechaIng == null) {
            JOptionPane.showMessageDialog(this, "La fecha de ingreso del integrante es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String idArtista = banda.getId().toString();
            RolIntegrante rol = RolIntegrante.valueOf(rolSeleccionado);
            LocalDate fechaSal = fechaSalida.getDate(); // Puede ser null si sigue activo
            boolean activo = checkActivo.isSelected();

            Date fechaIngDate = Date.from(fechaIng.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Date fechaSalDate = (fechaSal != null)
                    ? Date.from(fechaSal.atStartOfDay(ZoneId.systemDefault()).toInstant())
                    : null;

            IntegranteDTO integrante = new IntegranteDTO(nombre, rol, fechaIngDate, fechaSalDate, activo);
            boolean integrantePersistido = artistasBO.agregarIntegrante(idArtista, integrante);
            contadorIntegrantes++;

            JOptionPane.showMessageDialog(this, "Integrante agregado con éxito.");
            limpiarFormulario();
            if (contadorIntegrantes >= 2) {
                btnRegistrarArtista.setEnabled(true);
            }

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar integrante: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarFormulario() {
        this.txtNombreIntegrante.setText("");
        this.fechaIngreso.setDate(null);
        this.fechaSalida.setDate(null);
        this.checkActivo.setSelected(false);
    }

    public void limpiarFormularioFinal() {
        this.txtNombreIntegrante.setText("");
        this.fechaIngreso.setDate(null);
        this.fechaSalida.setDate(null);
        this.checkActivo.setSelected(false);
        this.contadorIntegrantes = 0;
        btnRegistrarArtista.setEnabled(false);
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
        labelMusicio1 = new javax.swing.JLabel();
        labelRol = new javax.swing.JLabel();
        labelAgregarSolista = new javax.swing.JLabel();
        btnRegistrarArtista = new javax.swing.JButton();
        btnAgregarMiembro = new javax.swing.JButton();
        labelNombre1 = new javax.swing.JLabel();
        txtNombreIntegrante = new javax.swing.JTextField();
        cbRol = new javax.swing.JComboBox<>();
        labelFechaIngreso = new javax.swing.JLabel();
        fechaIngreso = new com.github.lgooddatepicker.components.DatePicker();
        labelFechaISalida = new javax.swing.JLabel();
        fechaSalida = new com.github.lgooddatepicker.components.DatePicker();
        checkActivo = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(850, 550));
        setPreferredSize(new java.awt.Dimension(850, 550));

        panelFondo1.setBackground(new java.awt.Color(0, 0, 0));

        labelMusicio1.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelMusicio1.setForeground(new java.awt.Color(30, 215, 96));
        labelMusicio1.setText("Music.io");

        labelRol.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelRol.setForeground(new java.awt.Color(30, 215, 96));
        labelRol.setText("rol:");

        labelAgregarSolista.setFont(new java.awt.Font("Gotham Black", 1, 36)); // NOI18N
        labelAgregarSolista.setForeground(new java.awt.Color(30, 215, 96));
        labelAgregarSolista.setText("Agregar Integrantes");

        btnRegistrarArtista.setBackground(new java.awt.Color(30, 215, 96));
        btnRegistrarArtista.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        btnRegistrarArtista.setText("Guardar Artista");
        btnRegistrarArtista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarArtistaActionPerformed(evt);
            }
        });

        btnAgregarMiembro.setBackground(new java.awt.Color(30, 215, 96));
        btnAgregarMiembro.setFont(new java.awt.Font("Gotham Black", 1, 18)); // NOI18N
        btnAgregarMiembro.setText("Agregar Miembro");
        btnAgregarMiembro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMiembroActionPerformed(evt);
            }
        });

        labelNombre1.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelNombre1.setForeground(new java.awt.Color(30, 215, 96));
        labelNombre1.setText("nombre del integrante:");

        cbRol.setFont(new java.awt.Font("Gotham Black", 1, 12)); // NOI18N
        cbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRolActionPerformed(evt);
            }
        });

        labelFechaIngreso.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelFechaIngreso.setForeground(new java.awt.Color(30, 215, 96));
        labelFechaIngreso.setText("fecha de ingreso:");

        labelFechaISalida.setFont(new java.awt.Font("Gotham Bold", 1, 14)); // NOI18N
        labelFechaISalida.setForeground(new java.awt.Color(30, 215, 96));
        labelFechaISalida.setText("fecha de salida:");

        checkActivo.setBackground(new java.awt.Color(0, 0, 0));
        checkActivo.setFont(new java.awt.Font("Gotham Black", 0, 12)); // NOI18N
        checkActivo.setForeground(new java.awt.Color(30, 215, 96));
        checkActivo.setText("Miembro Activo?");
        checkActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFondo1Layout = new javax.swing.GroupLayout(panelFondo1);
        panelFondo1.setLayout(panelFondo1Layout);
        panelFondo1Layout.setHorizontalGroup(
            panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondo1Layout.createSequentialGroup()
                .addContainerGap(171, Short.MAX_VALUE)
                .addComponent(labelAgregarSolista, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(panelFondo1Layout.createSequentialGroup()
                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGap(345, 345, 345)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreIntegrante, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNombre1)
                            .addComponent(labelRol)
                            .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelFechaIngreso)
                            .addComponent(fechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(94, 94, 94)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFechaISalida)))
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGap(399, 399, 399)
                        .addComponent(checkActivo))
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRegistrarArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarMiembro, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFondo1Layout.setVerticalGroup(
            panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondo1Layout.createSequentialGroup()
                .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMusicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFondo1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(labelAgregarSolista, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(labelNombre1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreIntegrante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelRol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelFechaIngreso)
                            .addComponent(labelFechaISalida))
                        .addGap(12, 12, 12)
                        .addGroup(panelFondo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addComponent(checkActivo)
                .addGap(33, 33, 33)
                .addComponent(btnAgregarMiembro)
                .addGap(18, 18, 18)
                .addComponent(btnRegistrarArtista)
                .addContainerGap(73, Short.MAX_VALUE))
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

    private void btnRegistrarArtistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarArtistaActionPerformed
        if (contadorIntegrantes < 2) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos 2 integrantes antes de guardar el artista",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        controlador.mostrarArtistasPrincipal(usuario);
        limpiarFormularioFinal();
        this.dispose();
    }//GEN-LAST:event_btnRegistrarArtistaActionPerformed

    private void btnAgregarMiembroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMiembroActionPerformed
        guardarIntegrante();
        limpiarFormulario();
    }//GEN-LAST:event_btnAgregarMiembroActionPerformed

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
    private javax.swing.JButton btnRegistrarArtista;
    private javax.swing.JComboBox<String> cbRol;
    private javax.swing.JCheckBox checkActivo;
    private com.github.lgooddatepicker.components.DatePicker fechaIngreso;
    private com.github.lgooddatepicker.components.DatePicker fechaSalida;
    private javax.swing.JLabel labelAgregarSolista;
    private javax.swing.JLabel labelFechaISalida;
    private javax.swing.JLabel labelFechaIngreso;
    private javax.swing.JLabel labelMusicio1;
    private javax.swing.JLabel labelNombre1;
    private javax.swing.JLabel labelRol;
    private javax.swing.JPanel panelFondo1;
    private javax.swing.JTextField txtNombreIntegrante;
    // End of variables declaration//GEN-END:variables
}
