/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import org.bson.types.ObjectId;

/**
 * Clase entidad de la Cancion
 *
 * @author gael_
 */
public class Cancion {

    private ObjectId id;
    private String titulo;
    private float duracion;
    private ObjectId idAlbum;
    private ObjectId idArtista;

    /**
     * Constructor por omision
     */
    public Cancion() {
    }

    public Cancion(String titulo, float duracion, ObjectId idAlbum, ObjectId idArtista) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.idAlbum = idAlbum;
        this.idArtista = idArtista;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    public ObjectId getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(ObjectId idAlbum) {
        this.idAlbum = idAlbum;
    }

    public ObjectId getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(ObjectId idArtista) {
        this.idArtista = idArtista;
    }

    

}
