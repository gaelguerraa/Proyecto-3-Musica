/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicanegocio.interfaces;

import java.util.Date;
import java.util.List;
import sistemamusica.dtos.AlbumFavoritoDTO;
import sistemamusica.dtos.ArtistaFavoritoDTO;
import sistemamusica.dtos.CancionFavoritaDTO;
import sistemamusica.dtos.FavoritoDTO;
import sistemamusica.dtos.GeneroFavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Favorito;
import sistemamusicadominio.Usuario;
import sistemamusicanegocio.exception.NegocioException;

/**
 *
 * @author gael_
 */
public interface IUsuariosBO {


    public UsuarioDTO agregarUsuario(UsuarioDTO nuevoUsuario) throws NegocioException;
    public Usuario consultarPorId(String idUsuario);
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados) throws NegocioException;
    public UsuarioDTO iniciarSesion(String username, String contrasenia) throws NegocioException;
    
    public abstract boolean agregarFavorito(String idUsuario, FavoritoDTO favorito) throws NegocioException;
    public abstract boolean eliminarFavorito(String idUsuario, String idContenido) throws NegocioException;
    public abstract List<AlbumFavoritoDTO> obtenerAlbumesFavoritos(String idUsario, String nombreAlbum);
    public abstract List<ArtistaFavoritoDTO> obtenerArtistasFavoritos(String idUsuario, String nombreArtista);
    public abstract List<CancionFavoritaDTO> obtenerCancionesFavoritas(String idUsuario, String nombreCancion);
    public abstract List<GeneroFavoritoDTO> obtenerGenerosFavoritos(String idUsuario, String genero);
    public abstract List<GeneroFavoritoDTO> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin);
    public abstract List<GeneroFavoritoDTO> obtenerTodosFavoritos(String idUsuario);
    public abstract List<Favorito> consultarFavoritos(String idUsuario);
    public abstract void agregarGeneroRestringido(String idUsuario, String genero);
    public abstract void eliminarGeneroRestringido(String idUsuario, String genero);
    public abstract List<String> mostrarGenerosRestringidos(String idUsuario);

}
