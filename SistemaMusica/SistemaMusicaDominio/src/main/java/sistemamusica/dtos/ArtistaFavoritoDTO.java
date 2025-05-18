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
public class ArtistaFavoritoDTO {
    private String nombreArtista;
    private String tipoArtista;
    private String generoArtista;
    private Date fechaAgregacion;

    public ArtistaFavoritoDTO() {
    }

    public ArtistaFavoritoDTO(String nombreArtista, String tipoArtista, String generoArtista, Date fechaAgregacion) {
        this.nombreArtista = nombreArtista;
        this.tipoArtista = tipoArtista;
        this.generoArtista = generoArtista;
        this.fechaAgregacion = fechaAgregacion;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getTipoArtista() {
        return tipoArtista;
    }

    public void setTipoArtista(String tipoArtista) {
        this.tipoArtista = tipoArtista;
    }

    public String getGeneroArtista() {
        return generoArtista;
    }

    public void setGeneroArtista(String generoArtista) {
        this.generoArtista = generoArtista;
    }

    public Date getFechaAgregacion() {
        return fechaAgregacion;
    }

    public void setFechaAgregacion(Date fechaAgregacion) {
        this.fechaAgregacion = fechaAgregacion;
    }
    
    
}
