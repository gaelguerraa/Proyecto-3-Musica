/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import java.util.List;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Integrante;

/**
 *
 * @author gael_
 */
public interface IArtistasDAO {

    public abstract Artista registrarArtista(ArtistaDTO nuevoArtista);
    public abstract List<Artista> buscarArtistasPorNombre(String idUsuario, String nombre);
    public abstract List<Artista> buscarArtistasPorGenero(String idUsuario, String genero);
    public abstract List<Artista> buscarArtistasPorNombreGenero(String idUsuario, String nombre, String genero);
    public abstract List<Artista> buscarArtistas(String idUsuario);
    public abstract Artista buscarArtistaPorNombre(String nombre);
    
    public abstract Integrante agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante);
    public abstract List<Integrante> consultarTodosLosIntegrantes(String idArtista);
    public abstract List<Integrante> consultarIntegrantesActivos(String idArtista);

}
