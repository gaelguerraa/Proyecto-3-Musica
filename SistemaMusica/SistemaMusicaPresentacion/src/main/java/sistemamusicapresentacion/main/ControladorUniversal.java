/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapresentacion.main;

import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Artista;
import sistemamusicapresentacion.albumes.frmAgregarAlbum;
import sistemamusicapresentacion.albumes.frmAgregarCancionesAlbum;
import sistemamusicapresentacion.albumes.frmAlbumesDetalles;
import sistemamusicapresentacion.albumes.frmAlbumesPrincipal;
import sistemamusicapresentacion.artistas.frmAgregarBanda;
import sistemamusicapresentacion.artistas.frmAgregarIntegrante;
import sistemamusicapresentacion.artistas.frmAgregarSolista;
import sistemamusicapresentacion.artistas.frmArtistasDetalles;
import sistemamusicapresentacion.artistas.frmArtistasPrincipal;
import sistemamusicapresentacion.canciones.frmCanciones;
import sistemamusicapresentacion.usuario.frmCambiarDatos;
import sistemamusicapresentacion.usuario.frmFavoritosUsuario;
import sistemamusicapresentacion.usuario.frmRestringidosUsuario;
import sistemamusicapresentacion.usuario.frmUsuarioPrincipal;

/**
 *
 * @author gael_
 */
public class ControladorUniversal {


    private frmIniciarSesion mostrarIniciarSesion;
    private frmRegistrarUsuario mostrarRegistrarUsuario;
    


    public ControladorUniversal() {


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
     *
     * @param usuario Usuario a mostrar en la pantalla
     */
    public void mostrarModuloPrincipalUsuarios(UsuarioDTO usuario) {
        frmUsuarioPrincipal frmUsuarioPrincipal = new frmUsuarioPrincipal(this, usuario);
        frmUsuarioPrincipal.setVisible(true);
    }

    /**
     * Metodo para mostrar el modulo de Favoritos del usuario
     *
     * @param usuario Usuario referenciado
     */
    public void mostrarModuloFavoritosUsuario(UsuarioDTO usuario) {
        frmFavoritosUsuario frmFavoritosUsuario = new frmFavoritosUsuario(this, usuario);
        frmFavoritosUsuario.setVisible(true);
    }

    /**
     * Metodo para mostrar el modulo de Modificar el usuario
     *
     * @param usuario Usuario referenciado
     */
    public void mostrarModuloModificarUsuario(UsuarioDTO usuario) {
        frmCambiarDatos frmCambiarDatos = new frmCambiarDatos(this, usuario);
        frmCambiarDatos.setVisible(true);
    }

    /**
     * Metodo para mostrar las Restricciones del usuario
     *
     * @param usuario Usuario referenciado
     */
    public void mostrarModuloRestringidosUsuario(UsuarioDTO usuario) {
        frmRestringidosUsuario frmRestringidosUsuario = new frmRestringidosUsuario(this, usuario);
        frmRestringidosUsuario.setVisible(true);
    }

    public void mostrarArtistasPrincipal(UsuarioDTO usuario){
        frmArtistasPrincipal artistasPrincipal = new frmArtistasPrincipal(this, usuario);
        artistasPrincipal.setVisible(true);
    }
    
    public void mostrarAgregarSolista(UsuarioDTO usuario){
        frmAgregarSolista agregarSolista = new frmAgregarSolista(this, usuario);
        agregarSolista.setVisible(true);
    }
    
    public void mostrarAgregarBanda(UsuarioDTO usuario){
        frmAgregarBanda agregarBanda = new frmAgregarBanda(this, usuario);
        agregarBanda.setVisible(true);
    }
    
    public void mostrarAgregarIntegrante(Artista banda, UsuarioDTO usuario){
        frmAgregarIntegrante agregarIntegrante = new frmAgregarIntegrante(this, banda, usuario);
        agregarIntegrante.setVisible(true);
    }
    
    public void mostrarArtistasDetalles(Artista artistaSeleccionado, UsuarioDTO usuario){
        frmArtistasDetalles artistasDetalles = new frmArtistasDetalles(this, artistaSeleccionado, usuario);
        artistasDetalles.setVisible(true);
    }

    public void mostrarCanciones(UsuarioDTO usuario){
        frmCanciones cancionesVentana= new frmCanciones(this, usuario);
        cancionesVentana.setVisible(true);
    }
    
       public void mostrarAlbumesPrincipal(UsuarioDTO usuario){
        frmAlbumesPrincipal albumesPrincipal = new frmAlbumesPrincipal(this, usuario);
        albumesPrincipal.setVisible(true);
    }
    
    public void mostrarAlbumDetalles(UsuarioDTO usuario){
        frmAlbumesDetalles albumDetalles = new frmAlbumesDetalles(this, usuario);
        albumDetalles.setVisible(true);
    } 
    
    public void mostrarAgregarAlbum(UsuarioDTO usuario){
        frmAgregarAlbum agregarAlbum= new frmAgregarAlbum(this, usuario);
        agregarAlbum.setVisible(true);
    }
    
    public void agregarCanciones(UsuarioDTO usuario){
        frmAgregarCancionesAlbum agregarCanciones = new frmAgregarCancionesAlbum(this, usuario);
        agregarCanciones.setVisible(true);
    }

}
