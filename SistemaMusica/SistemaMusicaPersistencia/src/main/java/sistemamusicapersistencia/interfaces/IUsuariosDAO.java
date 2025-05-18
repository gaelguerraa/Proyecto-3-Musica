/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import java.util.Date;
import java.util.List;
import sistemamusica.dtos.AlbumFavoritoDTO;
import sistemamusica.dtos.ArtistaFavoritoDTO;
import sistemamusica.dtos.CancionFavoritaDTO;
import sistemamusica.dtos.GeneroFavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Favorito;
import sistemamusicadominio.Usuario;

/**
 *
 * @author gael_
 */
public interface IUsuariosDAO {

    /**
     * Metodo para agregar un nuevo usuario a la base de datos
     *
     * @param nuevoUsuario Nuevo usuario a agregar a la base de datos
     * @return Usuario que ha sido agregado
     */
    public Usuario agregarUsuario(UsuarioDTO nuevoUsuario);

    /**
     * Metodo para consultar un usuario en base a un id en la base de datos
     *
     * @param idUsuario ID del usuario a buscar
     * @return Usuario obtenido con el id mencionado
     */
    public Usuario consultarPorId(String idUsuario);
    
    /**
     * Metodo para consultar un usuario en base a un username en la base de
     * datos
     *
     * @param username Nombre del usuario
     * @return Usuario obtenido con el username mencionado
     */
    public Usuario consultarPorUsername(String username);

    /**
     * Metodo para modificar un usuario especificado por su id
     *
     * @param idUsuario ID del usuario a modificar
     * @param datosActualizados Datos a actualizar del usuario
     * @return Usuario modificado de la base de datos
     */
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados);

    /**
     * Metodo para obtener un usuario en base a un nombre de usuario y una
     * contrasenia
     *
     * @param username Nombre de usuario
     * @param contrasenia Contrasenia del usuario
     * @return Un usuario que coincida con ambos filtros
     */
    public Usuario consultarInicioSesion(String username, String contrasenia);
    
    public abstract boolean agregarFavorito(String idUsuario, Favorito favorito);
    public abstract boolean eliminarFavorito(String idUsuario, String idContenido);
    public abstract List<AlbumFavoritoDTO> obtenerAlbumesFavoritos(String idUsario, String nombreAlbum);
    public abstract List<ArtistaFavoritoDTO> obtenerArtistasFavoritos(String idUsuario, String nombreArtista);
    public abstract List<CancionFavoritaDTO> obtenerCancionesFavoritas(String idUsuario, String nombreCancion);
    public abstract List<GeneroFavoritoDTO> obtenerGenerosFavoritos(String idUsuario, String genero);
    public abstract List<GeneroFavoritoDTO> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin);
    public abstract List<GeneroFavoritoDTO> obtenerTodosFavoritos(String idUsuario);

}
