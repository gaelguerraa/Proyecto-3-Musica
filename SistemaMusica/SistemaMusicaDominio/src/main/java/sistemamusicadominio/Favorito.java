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

    private TipoContenido tipo;
    private ObjectId idContenido;
    private Date fechaAgregacion;

    /**
     * Constructor por omision
     */
    public Favorito() {
    }

    /**
     * Constructor que inicializa los atributos de la clase al valor de sus
     * parametros
     *
     * @param tipo Tipo de contenido
     * @param idContenido ID del contenido
     * @param fechaAgregacion Fecha en la que se agrego a favoritos
     */
    public Favorito(TipoContenido tipo, ObjectId idContenido, Date fechaAgregacion) {
        this.tipo = tipo;
        this.idContenido = idContenido;
        this.fechaAgregacion = fechaAgregacion;
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
