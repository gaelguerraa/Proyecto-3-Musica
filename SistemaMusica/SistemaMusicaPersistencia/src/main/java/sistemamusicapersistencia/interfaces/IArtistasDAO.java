/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import java.util.List;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusicadominio.Artista;

/**
 *
 * @author gael_
 */
public interface IArtistasDAO {
    public abstract Artista registrarSolista(ArtistaDTO nuevoSolista);
    public abstract Artista registrarBanda(ArtistaDTO nuevaBanda);
    public abstract List<Artista> buscarArtistasPorNombre(String nombre);
    public abstract List<Artista> buscarArtistasPorGenero(String genero);
    public abstract List<Artista> buscarArtistasPorNombreGenero(String nombre, String genero);
    public abstract List<Artista> buscarArtistas();
}
