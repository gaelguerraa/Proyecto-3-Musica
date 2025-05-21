/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import java.util.List;
import org.bson.Document;
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

    public abstract Artista buscarArtistaPorId(String idArtista);

    public abstract List<Artista> buscarArtistasPorGenero(String idUsuario, String genero);

    public abstract List<Artista> buscarArtistasPorNombreGenero(String idUsuario, String nombre, String genero);

    public abstract List<Artista> buscarArtistas(String idUsuario);

    public abstract Artista buscarArtistaPorNombre(String nombre);

    public abstract boolean agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante);

    public abstract List<Document> consultarTodosLosIntegrantes(String idArtista);

    public abstract List<Document> consultarIntegrantesActivos(String idArtista);

}
