/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import java.time.LocalDate;
import org.bson.types.ObjectId;

/**
 *
 * @author gael_
 */
public class Favorito {
    private TipoContenido tipo; 
    private ObjectId idContenido;
    private LocalDate fecha;

    public Favorito() {
    }

    public Favorito(TipoContenido tipo, ObjectId idContenido, LocalDate fecha) {
        this.tipo = tipo;
        this.idContenido = idContenido;
        this.fecha = fecha;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    
}
