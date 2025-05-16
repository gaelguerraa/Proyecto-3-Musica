/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import java.util.List;
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

    /**
     * Metodo para consultar todos los integrantes
     *
     * @return Lista con todos los integrantes de la base de datos
     */
    public List<Integrante> consultarTodos();

    /**
     * Metodo para consultar un integrante segun su ID
     *
     * @param idIntegrante ID del integrante a buscar
     * @return Integrante con el id solicitada
     */
    public Integrante consultarPorId(String idIntegrante);
    
    public abstract List<Integrante> consultarTodosLosIntegrantes(String idArtista);
    public abstract List<Integrante> consultarIntegrantesActivos(String idArtista);

}
