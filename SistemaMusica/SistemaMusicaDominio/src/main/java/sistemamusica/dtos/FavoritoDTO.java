/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;

import java.util.Date;
import sistemamusicadominio.TipoContenido;

/**
 *
 * @author gael_
 */
public class FavoritoDTO {
    private String nombreContenido;
    private String generoContenido;
    private TipoContenido tipo;
    private String idContenido;
    private Date fechaAgregacion;

    public FavoritoDTO() {
    }

    public FavoritoDTO(String nombreContenido, String generoContenido, TipoContenido tipo, String idContenido, Date fechaAgregacion) {
        this.nombreContenido = nombreContenido;
        this.generoContenido = generoContenido;
        this.tipo = tipo;
        this.idContenido = idContenido;
        this.fechaAgregacion = fechaAgregacion;
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

    public String getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(String idContenido) {
        this.idContenido = idContenido;
    }

    public Date getFechaAgregacion() {
        return fechaAgregacion;
    }

    public void setFechaAgregacion(Date fechaAgregacion) {
        this.fechaAgregacion = fechaAgregacion;
    }

    
    
    
    
}
