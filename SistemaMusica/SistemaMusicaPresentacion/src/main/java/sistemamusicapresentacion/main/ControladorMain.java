/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemamusicapresentacion.main;

import sistemamusicapresentacion.albumes.ControladorAlbumes;
import sistemamusicapresentacion.artistas.ControladorArtistas;
import sistemamusicapresentacion.canciones.ControladorCanciones;

/**
 *
 * @author PC
 */
public class ControladorMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ControladorArtistas controladorArtistas = new ControladorArtistas();
        ControladorAlbumes controladorAlbumes = new ControladorAlbumes();
        ControladorCanciones controladorCanciones = new ControladorCanciones();

        ControladorUniversal control = new ControladorUniversal(controladorArtistas, controladorAlbumes, controladorCanciones);
        control.mostrarModuloIniciarSesion();

    }

}
