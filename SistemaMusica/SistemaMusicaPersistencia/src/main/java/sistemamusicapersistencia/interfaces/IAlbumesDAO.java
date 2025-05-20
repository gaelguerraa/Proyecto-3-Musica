/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import java.util.List;
import sistemamusica.dtos.AlbumDTO;
import sistemamusicadominio.Album;

/**
 *
 * @author PC
 */
public interface IAlbumesDAO {

    /**
     * Metodo que agrega un nuevo album a la base de datos
     *
     * @param nuevoAlbum Nuevo album a agregar a la base de datos (DTO)
     * @return Album agregado en la base de datos
     */
    public Album agregarAlbum(AlbumDTO nuevoAlbum);

    /**
     * Metodo para obtener todos los albumes de la base de datos
     *
     * @return Lista con todos los albumes de la base de datos
     */
    public List<Album> obtenerAlbumes();
    
    /**
     * Metodo para obtener alumnos por genero de la base de datos
     *
     * @param generoBuscado Genero por el que son buscados los albumes
     * @return Albumes obtenidos que compartan el mismo genero
     */
    public List<Album> obtenerAlbumesPorGenero(String generoBuscado);
    
    /**
     * Metodo para obtener alumnos por nombre de la base de datos
     *
     * @param nombreBuscado Nombre por el que son buscados los albumes
     * @return Albumes obtenidos que comparten el mismo nombre
     */
    public List<Album> obtenerAlbumesPorNombre(String nombreBuscado);
    
    /**
     * Metodo para obtener albumes por fecha de lanzamiento de la base de datos
     *
     * @param fechaTexto Fecha de lanzamiento del album por el cual se va a
     * buscar
     * @return Albumes obtenidos que compratan la misma fecha de lanzamiento
     */
    public List<Album> obtenerAlbumesPorFecha(String fechaTexto);

}
