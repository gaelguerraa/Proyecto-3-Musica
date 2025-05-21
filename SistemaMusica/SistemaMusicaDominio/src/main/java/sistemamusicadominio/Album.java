/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import java.util.Date;
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
    private Date fechaLanzamiento;
    private Genero genero;
    private String imagenPortada;
    private ObjectId idArtista;
    private List<Cancion> canciones;

    /**
     * Constructor por omision
     */
    public Album() {
    }

    public Album(String nombre, Date fechaLanzamiento, Genero genero, String imagenPortada, ObjectId idArtista, List<Cancion> canciones) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.genero = genero;
        this.imagenPortada = imagenPortada;
        this.idArtista = idArtista;
        this.canciones = canciones;
    }

    public Album(String nombre, Date fechaLanzamiento, Genero genero, String imagenPortada, ObjectId idArtista) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.genero = genero;
        this.imagenPortada = imagenPortada;
        this.idArtista = idArtista;
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

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
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

    @Override
    public String toString() {
        return "Album{" + "id=" + id + ", nombre=" + nombre + ", fechaLanzamiento=" + fechaLanzamiento + ", genero=" + genero + ", imagenPortada=" + imagenPortada + ", idArtista=" + idArtista + ", canciones=" + canciones + '}';
    }

}
