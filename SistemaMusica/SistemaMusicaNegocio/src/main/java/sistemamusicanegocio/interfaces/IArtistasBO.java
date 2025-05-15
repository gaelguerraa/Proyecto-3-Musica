/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicanegocio.interfaces;

import java.util.List;
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
    public abstract List<Artista> buscarArtistasPorNombre(String nombre);
    public abstract List<Artista> buscarArtistasPorGenero(String genero);
    public abstract List<Artista> buscarArtistasPorNombreGenero(String nombre, String genero);
    public abstract List<Artista> buscarArtistas();
    public abstract Artista buscarArtistaPorNombre(String nombre);
}
