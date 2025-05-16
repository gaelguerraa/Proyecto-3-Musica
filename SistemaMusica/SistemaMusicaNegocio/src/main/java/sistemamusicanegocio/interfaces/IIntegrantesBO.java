/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicanegocio.interfaces;

import java.util.List;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Integrante;
import sistemamusicanegocio.exception.NegocioException;

/**
 *
 * @author gael_
 */
public interface IIntegrantesBO {
    public Integrante agregarIntegrante(IntegranteDTO nuevoIntegrante) throws NegocioException;
    public List<Integrante> consultarTodos();
    public Integrante consultarPorId(String idIntegrante);
    public abstract List<Integrante> consultarTodosLosIntegrantes(String idArtista);
    public abstract List<Integrante> consultarIntegrantesActivos(String idArtista);
}
