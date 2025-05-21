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
    public abstract List<ArtistaDTO> buscarArtistasPorNombre(String idUsuario, String nombre);
    public abstract List<ArtistaDTO> buscarArtistasPorGenero(String idUsuario, String genero);
    public abstract List<ArtistaDTO> buscarArtistasPorNombreGenero(String idUsuario, String nombre, String genero);
    public abstract List<ArtistaDTO> buscarArtistas(String idUsuario);
    public abstract ArtistaDTO buscarArtistaPorNombre(String nombre);
    
    public abstract boolean agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante) throws NegocioException;
    public abstract List<IntegranteDTO> consultarTodosLosIntegrantes(String idArtista);
    public abstract List<IntegranteDTO> consultarIntegrantesActivos(String idArtista);
}
