/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;


import java.util.Date;
import java.util.List;
import sistemamusicadominio.Genero;

/**
 *
 * @author gael_
 */
public class AlbumDTO {
    private String id;
    private String nombre;
    private Date fechaLanzamiento;
    private Genero genero;
    private String imagenPortada;
    private String idArtista;
    private List<String> idCanciones;

    public AlbumDTO() {
    }

    public AlbumDTO(String nombre, Date fechaLanzamiento, Genero genero, String imagenPortada, String idArtista, List<String> idCanciones) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.genero = genero;
        this.imagenPortada = imagenPortada;
        this.idArtista = idArtista;
        this.idCanciones = idCanciones;
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

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
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

    public List<String> getidCanciones() {
        return idCanciones;
    }

    public void setCanciones(List<String> idCanciones) {
        this.idCanciones = idCanciones;
    }
    
    
}
