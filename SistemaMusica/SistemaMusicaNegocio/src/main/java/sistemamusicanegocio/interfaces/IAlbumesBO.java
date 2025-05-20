/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicanegocio.interfaces;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import sistemamusica.dtos.AlbumDTO;
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
     * @return Lista con todos los albumes que se encuentren en la base de datos
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    public List<AlbumDTO> obtenerAlbumes() throws NegocioException;

    /**
     * Metodo para obtener todos los albumes de la base de datos y se obtiene
     * por genero
     *
     * @param generoBuscado Genero por el que se busca
     * @return Lista con todos los albumes que coinciden con el genero
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    public List<AlbumDTO> obtenerAlbumesPorGenero(String generoBuscado) throws NegocioException;

    /**
     * Metodo para obtener todos los albumes de la base de datos y se obtiene
     * por nombre
     *
     * @param nombreBuscado Nombre de album por el que se busca
     * @return Lista con todos los albumes que coinciden con el nombre
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    public List<AlbumDTO> obtenerAlbumesPorNombre(String nombreBuscado) throws NegocioException;

    /**
     * Metodo para obtener todos los algumes de la base de datos y se obtiene
     * por fecha
     *
     * @param fechaTexto Fecha por la que se va a buscar los albumes en la base
     * de datos
     * @return Lista con todos los albumes de quinciden con la fecha
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    public List<AlbumDTO> obtenerAlgumesPorFecha(String fechaTexto) throws NegocioException;

}
