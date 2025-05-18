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
public class AlbumFavoritoDTO {
    private String nombreAlbum;
    private String nombreArtista;
    private String genero;
    private Date fechaLanzamiento;
    private Date fechaAgregacion;

    public AlbumFavoritoDTO() {
    }

    public AlbumFavoritoDTO(String nombreAlbum, String nombreArtista, String genero, Date fechaLanzamiento, Date fechaAgregacion) {
        this.nombreAlbum = nombreAlbum;
        this.nombreArtista = nombreArtista;
        this.genero = genero;
        this.fechaLanzamiento = fechaLanzamiento;
        this.fechaAgregacion = fechaAgregacion;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Date getFechaAgregacion() {
        return fechaAgregacion;
    }

    public void setFechaAgregacion(Date fechaAgregacion) {
        this.fechaAgregacion = fechaAgregacion;
    }

    
    
}
