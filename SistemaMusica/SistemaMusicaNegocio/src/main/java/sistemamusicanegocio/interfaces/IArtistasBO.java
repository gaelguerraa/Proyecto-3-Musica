/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicanegocio.interfaces;

import java.util.List;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Integrante;
import sistemamusicanegocio.exception.NegocioException;

/**
 *
 * @author gael_
 */
public interface IArtistasBO {

    public abstract Artista registrarArtista(ArtistaDTO nuevoArtista) throws NegocioException;
    public abstract List<Artista> buscarArtistasPorNombre(String idUsuario, String nombre);
    public abstract List<Artista> buscarArtistasPorGenero(String idUsuario, String genero);
    public abstract List<Artista> buscarArtistasPorNombreGenero(String idUsuario, String nombre, String genero);
    public abstract List<Artista> buscarArtistas(String idUsuario);
    public abstract Artista buscarArtistaPorNombre(String nombre);
    
    public abstract Integrante agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante) throws NegocioException;
    public abstract List<Integrante> consultarTodosLosIntegrantes(String idArtista);
    public abstract List<Integrante> consultarIntegrantesActivos(String idArtista);
}
