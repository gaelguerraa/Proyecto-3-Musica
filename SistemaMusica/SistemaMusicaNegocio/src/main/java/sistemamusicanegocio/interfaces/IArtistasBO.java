/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicanegocio.interfaces;

import sistemamusica.dtos.ArtistaDTO;
import sistemamusicadominio.Artista;
import sistemamusicanegocio.exception.NegocioException;

/**
 *
 * @author gael_
 */
public interface IArtistasBO {
    public abstract Artista registrarSolista(ArtistaDTO nuevoSolista) throws NegocioException;
    public abstract Artista registrarBanda(ArtistaDTO nuevaBanda) throws NegocioException;
}
