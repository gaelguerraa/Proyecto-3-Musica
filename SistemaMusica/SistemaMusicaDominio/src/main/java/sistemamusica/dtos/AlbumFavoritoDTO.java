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
    
    private String idFavorito;
    private String idAlbum;
    private String nombreAlbum;
    private String nombreArtista;
    private String genero;
    private Date fechaLanzamiento;
    private Date fechaAgregacion;

    public AlbumFavoritoDTO() {
    }

    public AlbumFavoritoDTO(String idFavorito, String idAlbum, String nombreAlbum, String nombreArtista, String genero, Date fechaLanzamiento, Date fechaAgregacion) {
        this.idFavorito = idFavorito;
        this.idAlbum = idAlbum;
        this.nombreAlbum = nombreAlbum;
        this.nombreArtista = nombreArtista;
        this.genero = genero;
        this.fechaLanzamiento = fechaLanzamiento;
        this.fechaAgregacion = fechaAgregacion;
    }

    public String getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(String idFavorito) {
        this.idFavorito = idFavorito;
    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
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
