/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapresentacion.canciones;

import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class ControladorCanciones {
    frmCanciones cancionesVentana;
    
    ControladorUniversal universal;

    public ControladorCanciones() {
    }
    
    public void mostrarCanciones(){
        this.cancionesVentana= new frmCanciones(this, universal);
        cancionesVentana.setVisible(true);
    }
}
