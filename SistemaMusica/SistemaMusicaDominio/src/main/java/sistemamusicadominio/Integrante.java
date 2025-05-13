/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicadominio;

import java.time.LocalDate;
import org.bson.types.ObjectId;

/**
 *
 * @author gael_
 */
public class Integrante {

    private ObjectId id;
    private String nombre;
    private RolIntegrante rol;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida; // puede ser null
    private boolean activo;

    public Integrante() {
    }

    public Integrante(String nombre, RolIntegrante rol, LocalDate fechaIngreso, LocalDate fechaSalida, boolean activo) {
        this.nombre = nombre;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.activo = activo;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RolIntegrante getRol() {
        return rol;
    }

    public void setRol(RolIntegrante rol) {
        this.rol = rol;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
