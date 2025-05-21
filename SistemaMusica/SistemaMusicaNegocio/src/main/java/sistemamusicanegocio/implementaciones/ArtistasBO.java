/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.implementaciones;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicapersistencia.interfaces.IArtistasDAO;

/**
 *
 * @author gael_
 */
public class ArtistasBO implements IArtistasBO {

    IArtistasDAO artistasDAO;
    
    /**
     * Constructor que recibe una implementación de IArtistasDAO.
     * 
     * @param artistasDAO Objeto que implementa la interfaz IArtistasDAO para el acceso a datos.
     */
    public ArtistasBO(IArtistasDAO artistasDAO) {
        this.artistasDAO=artistasDAO;
    }

    /**
     * Registra un nuevo artista después de validar sus datos.
     * 
     * @param nuevoArtista DTO con la información del artista a registrar.
     * @return El artista registrado.
     * @throws NegocioException Si los datos del artista no son válidos o si ya existe un artista con ese nombre.
     */   
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
    
    /**
     * Agrega un nuevo integrante a un artista existente.
     * 
     * @param idArtista ID del artista al que se agregará el integrante.
     * @param nuevoIntegrante DTO con la información del nuevo integrante.
     * @return El integrante agregado.
     * @throws NegocioException Si los datos del integrante no son válidos.
     */
    @Override
    public boolean agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante ) throws NegocioException{
        if (nuevoIntegrante == null || nuevoIntegrante.getNombre() == null || nuevoIntegrante.getNombre().isEmpty()) {
            throw new NegocioException("El nombre del integrante no puede estar vacío.");
        }
        if (!nuevoIntegrante.getNombre().matches("^[a-zA-Z0-9 ]+$")) {
            throw new NegocioException("El nombre del integrante solo puede contener caracteres alfanuméricos y espacios.");
        }
        return artistasDAO.agregarIntegrante(idArtista, nuevoIntegrante);
    }

    /**
     * Busca artistas por nombre.
     * 
     * @param idUsuario ID del usuario que realiza la búsqueda.
     * @param nombre Nombre o parte del nombre a buscar.
     * @return Lista de artistas que coinciden con el criterio de búsqueda.
     */
    @Override
     public List<ArtistaDTO> buscarArtistasPorNombre(String idUsuario, String nombre) {
        List<Artista> artistas = artistasDAO.buscarArtistasPorNombre(idUsuario, nombre);

        List<ArtistaDTO> artistasDTO = new ArrayList<>();

        if (artistas != null) {
            for (Artista a : artistas) {
                ArtistaDTO dto = new ArtistaDTO();
                dto.setNombre(a.getNombre());
                dto.setGenero(a.getGenero());
                dto.setTipo(a.getTipo());
                artistasDTO.add(dto); 
            }
        }

        return artistasDTO; 
    }


    /**
     * Busca artistas por género musical.
     * 
     * @param idUsuario ID del usuario que realiza la búsqueda.
     * @param genero Género musical a buscar.
     * @return Lista de artistas que coinciden con el género especificado.
     */
    @Override
    public List<ArtistaDTO> buscarArtistasPorGenero(String idUsuario,String genero) {
        List<Artista> artistas = artistasDAO.buscarArtistasPorGenero(idUsuario, genero);

        List<ArtistaDTO> artistasDTO = new ArrayList<>();

        if (artistas != null) {
            for (Artista a : artistas) {
                ArtistaDTO dto = new ArtistaDTO();
                dto.setNombre(a.getNombre());
                dto.setGenero(a.getGenero());
                dto.setTipo(a.getTipo());
                artistasDTO.add(dto); 
            }
        }

        return artistasDTO; 
    }

    /**
     * Busca artistas por nombre y género musical.
     * 
     * @param idUsuario ID del usuario que realiza la búsqueda.
     * @param nombre Nombre o parte del nombre a buscar.
     * @param genero Género musical a buscar.
     * @return Lista de artistas que coinciden con ambos criterios de búsqueda.
     */
    @Override
    public List<ArtistaDTO> buscarArtistasPorNombreGenero(String idUsuario,String nombre, String genero) {
        List<Artista> artistas = artistasDAO.buscarArtistasPorNombreGenero(idUsuario, nombre, genero);

        List<ArtistaDTO> artistasDTO = new ArrayList<>();

        if (artistas != null) {
            for (Artista a : artistas) {
                ArtistaDTO dto = new ArtistaDTO();
                dto.setNombre(a.getNombre());
                dto.setGenero(a.getGenero());
                dto.setTipo(a.getTipo());
                artistasDTO.add(dto); 
            }
        }

        return artistasDTO; 
    }

    /**
     * Obtiene todos los artistas disponibles para un usuario.
     * 
     * @param idUsuario ID del usuario que realiza la consulta.
     * @return Lista de todos los artistas disponibles.
     */
    @Override
    public List<ArtistaDTO> buscarArtistas(String idUsuario) {
        List<Artista> artistas = artistasDAO.buscarArtistas(idUsuario);

        List<ArtistaDTO> artistasDTO = new ArrayList<>();

        if (artistas != null) {
            for (Artista a : artistas) {
                ArtistaDTO dto = new ArtistaDTO();
                dto.setNombre(a.getNombre());
                dto.setGenero(a.getGenero());
                dto.setTipo(a.getTipo());
                artistasDTO.add(dto); 
            }
        }

        return artistasDTO; 
    }

    /**
     * Busca un artista por su nombre exacto.
     * 
     * @param nombre Nombre exacto del artista a buscar.
     * @return El artista encontrado o null si no existe.
     */
    @Override
    public ArtistaDTO buscarArtistaPorNombre(String nombre) {
        Artista artista = artistasDAO.buscarArtistaPorNombre(nombre);
        ArtistaDTO artistaDTO = new ArtistaDTO();
        
        if (artista != null){
            artistaDTO.setId(artista.getId().toString());
            artistaDTO.setTipo(artista.getTipo());
            artistaDTO.setNombre(artista.getNombre());
            artistaDTO.setImagen(artista.getImagen());
            artistaDTO.setGenero(artista.getGenero());
            artistaDTO.setIntegrantes(artista.getIntegrantes());
        } 
        return artistaDTO;
    }
    
    /**
     * Consulta todos los integrantes de un artista.
     * 
     * @param idArtista ID del artista cuyos integrantes se consultarán.
     * @return Lista de todos los integrantes del artista.
     */
    @Override
    public List<IntegranteDTO> consultarTodosLosIntegrantes(String idArtista) {
        List<Document> integrantes = artistasDAO.consultarTodosLosIntegrantes(idArtista);

        List<IntegranteDTO> integrantesDTO = new ArrayList<>();

        if (integrantes != null) {
            for (Document i : integrantes) {
                IntegranteDTO dto = new IntegranteDTO();
                dto.setNombre(i.getString("nombre"));
                dto.setRol(RolIntegrante.valueOf(i.getString("rol")));
                dto.setFechaIngreso(i.getDate("fechaIngreso"));
                dto.setFechaSalida(i.getDate("fechaSalida")); // Puede ser null

                integrantesDTO.add(dto);
            }
        }

        return integrantesDTO;
    }

    /**
     * Consulta los integrantes activos de un artista.
     * 
     * @param idArtista ID del artista cuyos integrantes activos se consultarán.
     * @return Lista de los integrantes activos del artista.
     */
    @Override
    public List<IntegranteDTO> consultarIntegrantesActivos(String idArtista) {
        List<Document> integrantes = artistasDAO.consultarIntegrantesActivos(idArtista);

        List<IntegranteDTO> integrantesDTO = new ArrayList<>();

        if (integrantes != null) {
            for (Document i : integrantes) {
                IntegranteDTO dto = new IntegranteDTO();
                dto.setNombre(i.getString("nombre"));
                dto.setRol(RolIntegrante.valueOf(i.getString("rol")));
                dto.setFechaIngreso(i.getDate("fechaIngreso"));
                dto.setFechaSalida(i.getDate("fechaSalida"));

                integrantesDTO.add(dto);
            }
        }

        return integrantesDTO;
        }

}
