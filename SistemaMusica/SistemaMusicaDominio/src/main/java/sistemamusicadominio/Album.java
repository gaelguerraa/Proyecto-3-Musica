/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import java.time.LocalDate;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Entidad Album
 *
 * @author gael_
 */
public class Album {

    private ObjectId id;
    private String nombre;
    private LocalDate fechaLanzamiento;
    private Genero genero;
    private String imagenPortada;
    private ObjectId idArtista;
    private List<Cancion> canciones;

    /**
     * Constructor por omision
     */
    public Album() {
    }

    /**
     * Consturctor que inicializa los atributos de la clase al valor de sus
     * parametros
     *
     * @param nombre Nombre del album
     * @param fechaLanzamiento Fecha de lanzamiento del album
     * @param genero Genero del album
     * @param imagenPortada Liga de la imagen de la portada del album
     * @param idArtista ID del artista que realizo el album
     * @param canciones Canciones con las que cuenta el album
     */
    public Album(String nombre, LocalDate fechaLanzamiento, Genero genero, String imagenPortada, ObjectId idArtista, List<Cancion> canciones) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.genero = genero;
        this.imagenPortada = imagenPortada;
        this.idArtista = idArtista;
        this.canciones = canciones;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public ObjectId getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(ObjectId idArtista) {
        this.idArtista = idArtista;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

}
