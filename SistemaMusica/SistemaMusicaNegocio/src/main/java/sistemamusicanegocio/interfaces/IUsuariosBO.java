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

    /**
     * Metodo para agregar un nuevo usuario a la base de datos
     *
     * @param nuevoUsuario Nuevo usuario a agregar a la base de datos
     * @return Usuario que ha sido agregado
     * @throws NegocioException Si ocurre una incidencia al agregar el usuario
     */
    public UsuarioDTO agregarUsuario(UsuarioDTO nuevoUsuario) throws NegocioException;

    /**
     * Metodo para consultar un usuario en base a un id en la base de datos
     *
     * @param idUsuario ID del usuario a buscar
     * @return Usuario obtenido con el id mencionado
     */
    public Usuario consultarPorId(String idUsuario);

    /**
     * Metodo para modificar un usuario especificado por su id
     *
     * @param idUsuario ID del usuario a modificar
     * @param datosActualizados Datos a actualizar del usuario
     * @return Usuario modificado de la base de datos
     */
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados);

    /**
     * Metodo para que el usuario pueda iniciar sesion, mandando su nombre de
     * usuario y su contrasenia
     *
     * @param username Nombre del usuario
     * @param contrasenia Contrasenia del usuario
     * @return UsuarioDTO de solo lectura para mostrar datos del usuario
     * @throws NegocioException si ocurre una incidencia
     */
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

}
