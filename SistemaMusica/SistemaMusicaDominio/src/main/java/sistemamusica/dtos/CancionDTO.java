/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;

/**
 *
 * @author gael_
 */
public class CancionDTO {
    private String id;
    private String titulo;
    private float duracion;
    private String idArtista;

    public CancionDTO() {
    }

    public CancionDTO(String titulo, float duracion, String idArtista) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.idArtista = idArtista;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(String idArtista) {
        this.idArtista = idArtista;
    }

    
    
    
}
