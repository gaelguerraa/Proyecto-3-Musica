/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import java.util.Date;
import org.bson.types.ObjectId;

/**
 * Clase entidad de Favoritos
 *
 * @author gael_
 */
public class Favorito {

    private ObjectId idFavorito;
    private String nombreContenido;
    private String generoContenido;
    private TipoContenido tipo;
    private ObjectId idContenido;
    private Date fechaAgregacion;

    /**
     * Constructor por omision
     */
    public Favorito() {
    }

    public Favorito(String nombreContenido, String generoContenido, TipoContenido tipo, ObjectId idContenido, Date fechaAgregacion) {
        this.nombreContenido = nombreContenido;
        this.generoContenido = generoContenido;
        this.tipo = tipo;
        this.idContenido = idContenido;
        this.fechaAgregacion = fechaAgregacion;
    }

    public ObjectId getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(ObjectId idFavorito) {
        this.idFavorito = idFavorito;
    }

    public String getNombreContenido() {
        return nombreContenido;
    }

    public void setNombreContenido(String nombreContenido) {
        this.nombreContenido = nombreContenido;
    }

    public String getGeneroContenido() {
        return generoContenido;
    }

    public void setGeneroContenido(String generoContenido) {
        this.generoContenido = generoContenido;
    }

    
    public TipoContenido getTipo() {
        return tipo;
    }

    public void setTipo(TipoContenido tipo) {
        this.tipo = tipo;
    }

    public ObjectId getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(ObjectId idContenido) {
        this.idContenido = idContenido;
    }

    public Date getFechaAgregacion() {
        return fechaAgregacion;
    }

    public void setFechaAgregacion(Date fechaAgregacion) {
        this.fechaAgregacion = fechaAgregacion;
    }

}
