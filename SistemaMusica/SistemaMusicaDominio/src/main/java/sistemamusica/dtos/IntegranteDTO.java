/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;

import java.util.Date;
import sistemamusicadominio.RolIntegrante;

/**
 *
 * @author gael_
 */
public class IntegranteDTO {
    private String nombre;
    private RolIntegrante rol;
    private Date fechaIngreso;
    private Date fechaSalida; // puede ser null
    private boolean activo;

    public IntegranteDTO() {
    }

    public IntegranteDTO(String nombre, RolIntegrante rol, Date fechaIngreso, Date fechaSalida, boolean activo) {
        this.nombre = nombre;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.activo = activo;
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

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
