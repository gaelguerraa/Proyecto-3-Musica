/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author gael_
 */
public class Artista {
    private ObjectId _id;
    private TipoArtista tipo; 
    private String nombre;
    private String imagen;
    private String genero;
    private List<Integrante> integrantes; // Solo para bandas

    public Artista() {
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }
    
    
}
