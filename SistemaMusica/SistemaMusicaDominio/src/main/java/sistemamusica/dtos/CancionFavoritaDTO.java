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
public class CancionFavoritaDTO {
    
    private String idFavorito;
    private String idCancion;
    private String titulo;
    private float duracion;
    private String nombreArtista;
    private String nombreAlbum;
    private String genero;
    private Date fechaLanzamiento;
    private Date fechaAgregacion;

    public CancionFavoritaDTO() {
    }

    public CancionFavoritaDTO(String idFavorito, String idCancion, String titulo, float duracion, String nombreArtista, String nombreAlbum, String genero, Date fechaLanzamiento, Date fechaAgregacion) {
        this.idFavorito = idFavorito;
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.duracion = duracion;
        this.nombreArtista = nombreArtista;
        this.nombreAlbum = nombreAlbum;
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

    public String getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(String idCancion) {
        this.idCancion = idCancion;
    }

    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
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
