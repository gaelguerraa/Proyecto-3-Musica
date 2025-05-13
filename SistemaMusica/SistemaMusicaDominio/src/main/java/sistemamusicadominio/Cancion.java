/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

/**
 * Clase entidad de la Cancion
 *
 * @author gael_
 */
public class Cancion {

    private String titulo;
    private float duracion;

    /**
     * Constructor por omision
     */
    public Cancion() {
    }

    /**
     * Constructor que inicializa los atributos de la clase al valor de sus
     * parametros
     *
     * @param titulo Titulo de la cancion
     * @param duracion Duracion de la cancion
     */
    public Cancion(String titulo, float duracion) {
        this.titulo = titulo;
        this.duracion = duracion;
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

}
