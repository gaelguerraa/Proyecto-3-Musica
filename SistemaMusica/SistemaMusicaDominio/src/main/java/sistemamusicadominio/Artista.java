/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import java.util.List;
import org.bson.types.ObjectId;

/**
 * Clase entidad del Artista
 *
 * @author gael_
 */
public class Artista {

    private ObjectId id;
    private TipoArtista tipo;
    private String nombre;
    private String imagen;
    private Genero genero;
    private List<Integrante> integrantes; // Solo para bandas

    /**
     * Constructor por omision
     */
    public Artista() {
    }

    /**
     * Constructor que inicializa los atributos de la clase al valor de sus
     * parametros
     *
     * @param tipo Tipo del artista
     * @param nombre Nombre del artista
     * @param imagen Liga de la imagen del artista
     * @param genero Genero del artista
     * @param integrantes Integrantes del artista (si es una banda)
     */
    public Artista(TipoArtista tipo, String nombre, String imagen, Genero genero, List<Integrante> integrantes) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.genero = genero;
        this.integrantes = integrantes;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public TipoArtista getTipo() {
        return tipo;
    }

    public void setTipo(TipoArtista tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }

}
