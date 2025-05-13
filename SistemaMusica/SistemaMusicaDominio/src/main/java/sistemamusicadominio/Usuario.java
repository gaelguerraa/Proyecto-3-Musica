/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import java.util.List;
import org.bson.types.ObjectId;

/**
 * Clase entidad del usuario
 *
 * @author gael_
 */
public class Usuario {

    private ObjectId id;
    private String username;
    private String email;
    private String contrasenia;
    private String imagenPerfil;
    private List<Favorito> favoritos;
    private List<String> restricciones;

    /**
     * Constructor por omision
     */
    public Usuario() {
    }

    /**
     * Clase que inicializa los atributos de la clase al valor de sus parametros
     *
     * @param username Nombre de usuario
     * @param email Correo del usuario
     * @param contrasenia Contrasenia del usuario
     * @param imagenPerfil Liga de la imagen de perfil del usuario
     * @param favoritos Favoritos con los que cuenta el usuario
     * @param restricciones Restricciones con las que cuenta el usuario
     */
    public Usuario(String username, String email, String contrasenia, String imagenPerfil, List<Favorito> favoritos, List<String> restricciones) {
        this.username = username;
        this.email = email;
        this.contrasenia = contrasenia;
        this.imagenPerfil = imagenPerfil;
        this.favoritos = favoritos;
        this.restricciones = restricciones;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId _id) {
        this.id = _id;
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

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }

    public List<String> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(List<String> restricciones) {
        this.restricciones = restricciones;
    }

}
