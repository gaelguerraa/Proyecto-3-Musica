/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicanegocio.interfaces;

import java.util.List;
import org.bson.Document;
import sistemamusica.dtos.AlbumDTO;
import sistemamusica.dtos.CancionDTO;
import sistemamusicanegocio.exception.NegocioException;

/**
 *
 * @author PC
 */
public interface IAlbumesBO {

    /**
     * Metodo que agrega un nuevo album a la base de datos
     *
     * @param nuevoAlbum Nuevo album a agregar a la abse de datos
     * @return Album agregado a la base de datos
     * @throws NegocioException Si ocurre una incidencia al agregar el album a
     * la base de datos
     */
    public AlbumDTO agregarAlbum(AlbumDTO nuevoAlbum) throws NegocioException;

    /**
     * Metodo para obtener todos los albumes de la base de datos
     *
     * @param idUsuario
     * @return Lista con todos los albumes que se encuentren en la base de datos
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    public List<AlbumDTO> obtenerAlbumes(String idUsuario) throws NegocioException;

    /**
     * Metodo para obtener todos los albumes de la base de datos y se obtiene
     * por genero
     *
     * @param idUsuario
     * @param generoBuscado Genero por el que se busca
     * @return Lista con todos los albumes que coinciden con el genero
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    public List<AlbumDTO> obtenerAlbumesPorGenero(String idUsuario, String generoBuscado) throws NegocioException;

    /**
     * Metodo para obtener todos los albumes de la base de datos y se obtiene
     * por nombre
     *
     * @param idUsuario
     * @param nombreBuscado Nombre de album por el que se busca
     * @return Lista con todos los albumes que coinciden con el nombre
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    public List<AlbumDTO> obtenerAlbumesPorNombre(String idUsuario, String nombreBuscado) throws NegocioException;

    /**
     * Metodo para obtener un album de la base de datos y se obtiene por nombre
     *
     * @param nombreBuscado Nombre de album por el que se busca
     * @return Album que coincide con el nombre
     * @throws NegocioException Si ocurre una incidencia al obtener el album de
     * la base de datos
     */
    public AlbumDTO obtenerAlbumPorNombre(String nombreBuscado) throws NegocioException;

    /**
     * Metodo para obtener todos los algumes de la base de datos y se obtiene
     * por fecha
     *
     * @param idUsuario
     * @param fechaTexto Fecha por la que se va a buscar los albumes en la base
     * de datos
     * @return Lista con todos los albumes de quinciden con la fecha
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    public List<AlbumDTO> obtenerAlgumesPorFecha(String idUsuario, String fechaTexto) throws NegocioException;

    /**
     * Metodo para obtener la lista de todas las canciones pertenecientes a un
     * album
     *
     * @param idAlbum ID del album a obtener sus canciones
     * @return Lista de todas las canciones pertenecientes al album
     * @throws NegocioException Si ocurre una incidencia al obtener las
     * canciones de la base de datos
     */
    public List<CancionDTO> obtenerCancionesPorIdAlbum(String idAlbum) throws NegocioException;
    
    public CancionDTO buscarCancionPorId(String idCancion);
    
    public List<CancionDTO> buscarCancionesPorNombre(String nombre, String idUsuario);
    public List<CancionDTO> obtenerTodasLasCanciones(String idUsuario);

}
