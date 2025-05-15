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
    private frmIniciarSesion mostrarIniciarSesion;
    private frmRegistrarUsuario mostrarRegistrarUsuario;

    /**
     * Constructor que inicializa los atributos de la clase al valor de sus
     * parametros
     *
     * @param controladorArtistas clase control de Artistas
     * @param controladorAlbumes clase control de Albumes
     * @param controladorUsuario clase control de Usuarios
     * @param controladorCanciones clase control de Canciones
     */
    public ControladorUniversal(ControladorArtistas controladorArtistas, ControladorAlbumes controladorAlbumes, ControladorUsuario controladorUsuario, ControladorCanciones controladorCanciones) {
        this.controladorArtistas = new ControladorArtistas();
        this.controladorAlbumes = new ControladorAlbumes();
        this.controladorUsuario = new ControladorUsuario();
        this.controladorCanciones = new ControladorCanciones();
    }

    /**
     * Metodo para mostrar el modulo de Iniciar sesion
     */
    public void mostrarModuloIniciarSesion() {
        this.mostrarIniciarSesion = new frmIniciarSesion(this);
        this.mostrarIniciarSesion.setVisible(true);
    }

    /**
     * Metodo para mostrar el modulo de Registrar usuario
     */
    public void mostrarModuloRegistrarUsuario() {
        this.mostrarRegistrarUsuario = new frmRegistrarUsuario(this);
        this.mostrarRegistrarUsuario.setVisible(true);
    }

    /**
     * Metodo para mostrar el modulo de Usuarios
     */
    public void mostrarModuloUsuarios() {
        controladorUsuario.mostrarUsuarioPrincipal();
    }

    /**
     * Metodo para mostrar el modulo de Artistas
     */
    public void mostrarModuloArtistas() {
        controladorArtistas.mostrarArtistasPrincipal();
    }

    /**
     * Metodo para mostrar el modulo de Albumes
     */
    public void mostrarModuloAlbumes() {
        controladorAlbumes.mostrarAlbumesPrincipal();
    }

    /**
     * Metodo para mostrar el modulo de canciones
     */
    public void mostrarModuloCanciones() {
        controladorCanciones.mostrarCanciones();
    }

}
