/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.implementaciones;

import java.util.List;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Integrante;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.fabrica.FabricaObjetosNegocio;
import sistemamusicanegocio.interfaces.IIntegrantesBO;
import sistemamusicapersistencia.interfaces.IIntegrantesDAO;

/**
 *
 * @author gael_
 */
public class IntegrantesBO implements IIntegrantesBO {
    
    IIntegrantesDAO integrantesDAO;

    public IntegrantesBO(IIntegrantesDAO integrantesDAO) {
        this.integrantesDAO = integrantesDAO;
    }

    @Override
    public Integrante agregarIntegrante(IntegranteDTO nuevoIntegrante) throws NegocioException {
        if(nuevoIntegrante.getNombre().isEmpty() || nuevoIntegrante.getNombre() == null ){
            throw new NegocioException("El nombre del integrante no puede estar vacio.");
        }
        if(nuevoIntegrante.getFechaIngreso()== null ){
            throw new NegocioException("El integrante debe tener fecha de ingreso.");
        }
        if(nuevoIntegrante.getRol()== null ){
            throw new NegocioException("El integrante debe tener un rol.");
        }
        return integrantesDAO.agregarIntegrante(nuevoIntegrante);
    }

    @Override
    public List<Integrante> consultarTodos() {
        return integrantesDAO.consultarTodos();
    }

    @Override
    public Integrante consultarPorId(String idIntegrante) {
        return integrantesDAO.consultarPorId(idIntegrante);
    }

    @Override
    public List<Integrante> consultarTodosLosIntegrantes(String idArtista) {
        return integrantesDAO.consultarTodosLosIntegrantes(idArtista);
    }

    @Override
    public List<Integrante> consultarIntegrantesActivos(String idArtista) {
        return integrantesDAO.consultarIntegrantesActivos(idArtista);
    }
    
}
