/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapresentacion.albumes;

import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class ControladorAlbumes {
    
    frmAlbumesPrincipal albumesPrincipal;
    frmAgregarAlbum agregarAlbum;
    frmAlbumesDetalles albumDetalles;
    frmAgregarCancionesAlbum agregarCanciones;
    
    ControladorUniversal universal;

    public ControladorAlbumes() {
    }
    
    public void mostrarAlbumesPrincipal(){
        this.albumesPrincipal = new frmAlbumesPrincipal(this, universal);
        albumesPrincipal.setVisible(true);
    }
    
    public void mostrarAlbumDetalles(){
        this.albumDetalles = new frmAlbumesDetalles(this, universal);
        albumDetalles.setVisible(true);
    } 
    
    public void mostrarAgregarAlbum(){
        this.agregarAlbum = new frmAgregarAlbum(this);
        agregarAlbum.setVisible(true);
    }
    
    public void agregarCanciones(){
        this.agregarCanciones = new frmAgregarCancionesAlbum(this);
        agregarCanciones.setVisible(true);
    }
}
