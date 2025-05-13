/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author gael_
 */
public class AlbumDTO {
    private String id;
    private String nombre;
    private LocalDate fechaLanzamiento;
    private String genero;
    private String imagenPortada;
    private String idArtista;
    private List<CancionDTO> canciones;

    public AlbumDTO() {
    }

    public AlbumDTO(String nombre, LocalDate fechaLanzamiento, String genero, String imagenPortada, String idArtista, List<CancionDTO> canciones) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.genero = genero;
        this.imagenPortada = imagenPortada;
        this.idArtista = idArtista;
        this.canciones = canciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public String getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(String idArtista) {
        this.idArtista = idArtista;
    }

    public List<CancionDTO> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<CancionDTO> canciones) {
        this.canciones = canciones;
    }
    
    
}
