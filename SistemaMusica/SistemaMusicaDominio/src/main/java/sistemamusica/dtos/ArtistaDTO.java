/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;

import java.util.List;
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
    private String genero;
    private List<IntegranteDTO> integrantes; // solo si es banda

    public ArtistaDTO() {
    }

    //para solistas
    public ArtistaDTO(TipoArtista tipo, String nombre, String imagen, String genero) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.genero = genero;
    }
    

    //para bandas
    public ArtistaDTO(TipoArtista tipo, String nombre, String imagen, String genero, List<IntegranteDTO> integrantes) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.genero = genero;
        this.integrantes = integrantes;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<IntegranteDTO> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<IntegranteDTO> integrantes) {
        this.integrantes = integrantes;
    }
    
    
}
