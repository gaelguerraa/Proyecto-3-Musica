/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;

import java.util.Date;

/**
 *
 * @author gael_
 */
public class GeneroFavoritoDTO {
    private String idContenido;
    private String tipo; // "artista" o "album"
    private String nombre;
    private String genero;
    private Date fechaAgregacion;

    public GeneroFavoritoDTO() {
    }

    public GeneroFavoritoDTO(String tipo, String nombre, String genero, Date fechaAgregacion) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.genero = genero;
        this.fechaAgregacion = fechaAgregacion;
    }

    public GeneroFavoritoDTO(String idContenido, String tipo, String nombre, String genero, Date fechaAgregacion) {
        this.idContenido = idContenido;
        this.tipo = tipo;
        this.nombre = nombre;
        this.genero = genero;
        this.fechaAgregacion = fechaAgregacion;
    }

    public String getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(String idContenido) {
        this.idContenido = idContenido;
    }
    
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaAgregacion() {
        return fechaAgregacion;
    }

    public void setFechaAgregacion(Date fechaAgregacion) {
        this.fechaAgregacion = fechaAgregacion;
    }
    
    
}
