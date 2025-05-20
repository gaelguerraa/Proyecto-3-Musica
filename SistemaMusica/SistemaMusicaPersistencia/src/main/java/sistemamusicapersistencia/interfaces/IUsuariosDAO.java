/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import sistemamusica.dtos.AlbumFavoritoDTO;
import sistemamusica.dtos.ArtistaFavoritoDTO;
import sistemamusica.dtos.CancionFavoritaDTO;
import sistemamusica.dtos.FavoritoDTO;
import sistemamusica.dtos.GeneroFavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Favorito;
import sistemamusicadominio.Genero;
import sistemamusicadominio.Usuario;

/**
 *
 * @author gael_
 */
public interface IUsuariosDAO {

    public Usuario agregarUsuario(UsuarioDTO nuevoUsuario);
    public Usuario consultarPorId(String idUsuario);
    public Usuario consultarPorUsername(String username);
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados);
    public Usuario consultarInicioSesion(String username, String contrasenia);
    
    public abstract boolean agregarFavorito(String idUsuario, FavoritoDTO favoritoDTO);
    public abstract boolean eliminarFavorito(String idUsuario, String idContenido);
    public abstract List<Document> obtenerAlbumesFavoritos(String idUsario, String nombreAlbum);
    public abstract List<Document> obtenerArtistasFavoritos(String idUsuario, String nombreArtista);
    public abstract List<Document> obtenerCancionesFavoritas(String idUsuario, String nombreCancion);
    public abstract List<Document> obtenerGenerosFavoritos(String idUsuario, String genero);
    public abstract List<Document> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin);
    public abstract List<Document> obtenerTodosFavoritos(String idUsuario);
    public abstract List<Favorito> consultarFavoritos(String idUsuario);
    
    public abstract void agregarGeneroRestringido(String idUsuario, String genero);
    public abstract void eliminarGeneroRestringido(String idUsuario, String genero);
    public abstract List<String> mostrarGenerosRestringidos(String idUsuario);

}
