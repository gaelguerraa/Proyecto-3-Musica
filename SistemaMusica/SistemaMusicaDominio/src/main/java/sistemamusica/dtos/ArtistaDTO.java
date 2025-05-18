/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;

import java.util.List;
import sistemamusicadominio.Genero;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.TipoArtista;

/**
 *
 * @author gael_
 */
public class ArtistaDTO {
    private String id;
    private TipoArtista tipo; 
    private String nombre;
    private String imagen;
    private Genero genero;
    private List<Integrante> integrantes; // solo si es banda

    public ArtistaDTO() {
    }


    public ArtistaDTO(TipoArtista tipo, String nombre, String imagen, Genero genero) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.genero = genero;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoArtista getTipo() {
        return tipo;
    }

    public void setTipo(TipoArtista tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }
    
    
}
