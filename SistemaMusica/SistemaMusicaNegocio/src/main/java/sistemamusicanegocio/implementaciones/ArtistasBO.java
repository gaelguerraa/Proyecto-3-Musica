/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.implementaciones;

import java.util.List;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusicadominio.Artista;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicapersistencia.interfaces.IArtistasDAO;

/**
 *
 * @author gael_
 */
public class ArtistasBO implements IArtistasBO {

    IArtistasDAO artistasDAO;
    
    public ArtistasBO(IArtistasDAO artistasDAO) {
        this.artistasDAO=artistasDAO;
    }

       
    @Override
    public Artista registrarSolista(ArtistaDTO nuevoSolista) throws NegocioException{
        if (nuevoSolista == null || nuevoSolista.getNombre() == null || nuevoSolista.getNombre().isEmpty()) {
            throw new NegocioException("El nombre del solista no puede estar vacío.");
        }
        if (nuevoSolista.getGenero() == null || nuevoSolista.getGenero() == null) {
            throw new NegocioException("El género del solista no puede estar vacío.");
        }
        if (!nuevoSolista.getNombre().matches("^[a-zA-Z0-9 ]+$")) {
            throw new NegocioException("El nombre del solista solo puede contener caracteres alfanuméricos y espacios.");
        }
        if (nuevoSolista.getNombre().length() > 30) {
            throw new NegocioException("El nombre del solista no puede tener más de 30 caracteres.");
        }
        return artistasDAO.registrarSolista(nuevoSolista);
    }

    @Override
    public Artista registrarBanda(ArtistaDTO nuevaBanda) throws NegocioException {
        if (nuevaBanda == null || nuevaBanda.getNombre() == null || nuevaBanda.getNombre().isEmpty()) {
            throw new NegocioException("El nombre de la banda no puede estar vacío.");
        }
        if (nuevaBanda.getGenero() == null || nuevaBanda.getGenero()== null) {
            throw new NegocioException("El género de la banda no puede estar vacío.");
        }
        if (nuevaBanda.getIntegrantes() == null || nuevaBanda.getIntegrantes().isEmpty()) {
            throw new NegocioException("La banda debe tener al menos un integrante.");
        }
        if (!nuevaBanda.getNombre().matches("^[a-zA-Z0-9 ]+$")) {
            throw new NegocioException("El nombre de la banda solo puede contener caracteres alfanuméricos y espacios.");
        }
        if (nuevaBanda.getNombre().length() > 30) {
            throw new NegocioException("El nombre de la banda no puede tener más de 30 caracteres.");
        }
        if(nuevaBanda.getIntegrantes() == null){
            throw new NegocioException("Una banda debe tener integrantes.");
        }
        return artistasDAO.registrarBanda(nuevaBanda);
    }

    @Override
    public List<Artista> buscarArtistasPorNombre(String nombre) {
        return artistasDAO.buscarArtistasPorNombre(nombre);
    }

    @Override
    public List<Artista> buscarArtistasPorGenero(String genero) {
        return artistasDAO.buscarArtistasPorGenero(genero);
    }

    @Override
    public List<Artista> buscarArtistasPorNombreGenero(String nombre, String genero) {
        return artistasDAO.buscarArtistasPorNombreGenero(nombre, genero);
    }

    @Override
    public List<Artista> buscarArtistas() {
        return artistasDAO.buscarArtistas();
    }
    
}
