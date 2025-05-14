/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusica.dtos;

import java.util.Date;
import sistemamusicadominio.TipoContenido;

/**
 *
 * @author gael_
 */
public class FavoritoDTO {
    private TipoContenido tipo; 
    private String idElemento; 
    private Date fechaAgregado;

    public FavoritoDTO() {
    }

    public FavoritoDTO(TipoContenido tipo, String idElemento, Date fechaAgregado) {
        this.tipo = tipo;
        this.idElemento = idElemento;
        this.fechaAgregado = fechaAgregado;
    }

    public TipoContenido getTipo() {
        return tipo;
    }

    public void setTipo(TipoContenido tipo) {
        this.tipo = tipo;
    }

    public String getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(String idElemento) {
        this.idElemento = idElemento;
    }

    public Date getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(Date fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
    
    
}
