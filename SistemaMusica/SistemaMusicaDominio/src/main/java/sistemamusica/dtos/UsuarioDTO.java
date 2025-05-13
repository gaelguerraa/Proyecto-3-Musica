/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;

import java.util.List;

/**
 *
 * @author gael_
 */
public class UsuarioDTO {
    private String id; 
    private String username;
    private String email;
    private String contrasenia;
    private String imagenPerfil;
    private List<FavoritoDTO> favoritos;
    private List<String> restricciones;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String username, String email, String contrasenia, String imagenPerfil, List<FavoritoDTO> favoritos, List<String> restricciones) {
        this.username = username;
        this.email = email;
        this.contrasenia = contrasenia;
        this.imagenPerfil = imagenPerfil;
        this.favoritos = favoritos;
        this.restricciones = restricciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public List<FavoritoDTO> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<FavoritoDTO> favoritos) {
        this.favoritos = favoritos;
    }

    public List<String> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(List<String> restricciones) {
        this.restricciones = restricciones;
    }
    
    
}
