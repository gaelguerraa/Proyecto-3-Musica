/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.implementaciones;

import java.util.List;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Integrante;
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
    public Artista registrarArtista(ArtistaDTO nuevoArtista) throws NegocioException {
        if (nuevoArtista == null || nuevoArtista.getNombre() == null || nuevoArtista.getNombre().isEmpty()) {
            throw new NegocioException("El nombre de la banda no puede estar vacío.");
        }
        if (nuevoArtista.getGenero() == null || nuevoArtista.getGenero()== null) {
            throw new NegocioException("El género de la banda no puede estar vacío.");
        }
        if (!nuevoArtista.getNombre().matches("^[a-zA-Z0-9 ]+$")) {
            throw new NegocioException("El nombre de la banda solo puede contener caracteres alfanuméricos y espacios.");
        }
        if (nuevoArtista.getNombre().length() > 30) {
            throw new NegocioException("El nombre de la banda no puede tener más de 30 caracteres.");
        }
        Artista existente = artistasDAO.buscarArtistaPorNombre(nuevoArtista.getNombre());
        if (existente != null) {
            throw new NegocioException("Ya existe un artista registrado con ese nombre.");
        }
        return artistasDAO.registrarArtista(nuevoArtista);
    }
    
    @Override
    public Integrante agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante ) throws NegocioException{
        if (nuevoIntegrante == null || nuevoIntegrante.getNombre() == null || nuevoIntegrante.getNombre().isEmpty()) {
            throw new NegocioException("El nombre del integrante no puede estar vacío.");
        }
        if (!nuevoIntegrante.getNombre().matches("^[a-zA-Z0-9 ]+$")) {
            throw new NegocioException("El nombre del integrante solo puede contener caracteres alfanuméricos y espacios.");
        }
        return artistasDAO.agregarIntegrante(idArtista, nuevoIntegrante);
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

    @Override
    public Artista buscarArtistaPorNombre(String nombre) {
        return artistasDAO.buscarArtistaPorNombre(nombre);
    }
    
    @Override
    public List<Integrante> consultarTodosLosIntegrantes(String idArtista) {
        return artistasDAO.consultarTodosLosIntegrantes(idArtista);
    }

    @Override
    public List<Integrante> consultarIntegrantesActivos(String idArtista) {
        return artistasDAO.consultarIntegrantesActivos(idArtista);
    }
    
}
