/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import org.bson.types.ObjectId;

/**
 *
 * @author gael_
 */
public class Favorito {
    private String tipo; 
    private ObjectId idContenido;
    private String fecha;

    public Favorito() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ObjectId getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(ObjectId idContenido) {
        this.idContenido = idContenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
