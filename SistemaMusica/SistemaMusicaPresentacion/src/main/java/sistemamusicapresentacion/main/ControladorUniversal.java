/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapresentacion.main;

import sistemamusicapresentacion.albumes.ControladorAlbumes;
import sistemamusicapresentacion.artistas.ControladorArtistas;
import sistemamusicapresentacion.canciones.ControladorCanciones;
import sistemamusicapresentacion.usuario.ControladorUsuario;

/**
 *
 * @author gael_
 */
public class ControladorUniversal {
    private final ControladorArtistas controladorArtistas;
    private final ControladorAlbumes controladorAlbumes;
    private final ControladorUsuario controladorUsuario;
    private final ControladorCanciones controladorCanciones;

    public ControladorUniversal(ControladorArtistas controladorArtistas, ControladorAlbumes controladorAlbumes, ControladorUsuario controladorUsuario, ControladorCanciones controladorCanciones) {
        this.controladorArtistas = new ControladorArtistas();
        this.controladorAlbumes = new ControladorAlbumes();
        this.controladorUsuario = new ControladorUsuario();
        this.controladorCanciones = new ControladorCanciones();
    }
    
     public void mostrarModuloUsuarios() {
        controladorUsuario.mostrarUsuarioPrincipal();
    }

    public void mostrarModuloArtistas() {
        controladorArtistas.mostrarArtistasPrincipal();
    }

    public void mostrarModuloAlbumes() {
        controladorAlbumes.mostrarAlbumesPrincipal();
    }

    public void mostrarModuloCanciones() {
        controladorCanciones.mostrarCanciones();
    }
    
    
    
}
