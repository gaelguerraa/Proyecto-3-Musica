/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Integrante;

/**
 *
 * @author PC
 */
public interface IIntegrantesDAO {

    /**
     * Metodo que agrega un integrante a la base de datos
     *
     * @param nuevoIntegrante Nuevo integrante a agregar a la base de datos
     * @return Integrante agregado en la base de datos
     */
    public Integrante agregarIntegrante(IntegranteDTO nuevoIntegrante);

}
