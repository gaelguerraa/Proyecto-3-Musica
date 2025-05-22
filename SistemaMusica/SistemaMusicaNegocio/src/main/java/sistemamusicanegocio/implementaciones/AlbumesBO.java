/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.implementaciones;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import sistemamusica.dtos.AlbumDTO;
import sistemamusica.dtos.CancionDTO;
import sistemamusicadominio.Album;
import sistemamusicadominio.Cancion;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.interfaces.IAlbumesBO;
import sistemamusicapersistencia.interfaces.IAlbumesDAO;

/**
 *
 * @author PC
 */
public class AlbumesBO implements IAlbumesBO {

    IAlbumesDAO albumesDAO;

    /**
     * Consturcotr que inizializa los atributos de la clase al valor de sus
     * parametros
     *
     * @param albumesDAO Instancia de albumesDAO
     */
    public AlbumesBO(IAlbumesDAO albumesDAO) {
        this.albumesDAO = albumesDAO;
    }

    /**
     * Metodo que agrega un nuevo album a la base de datos
     *
     * @param nuevoAlbum Nuevo album a agregar a la abse de datos
     * @return Album agregado a la base de datos
     * @throws NegocioException Si ocurre una incidencia al agregar el album a
     * la base de datos
     */
    @Override
    public AlbumDTO agregarAlbum(AlbumDTO nuevoAlbum) throws NegocioException {

        Album album = albumesDAO.agregarAlbum(nuevoAlbum);

        if (album != null) {
            AlbumDTO albumObtenido = new AlbumDTO();
            albumObtenido.setNombre(album.getNombre());
            albumObtenido.setFechaLanzamiento(album.getFechaLanzamiento());
            albumObtenido.setGenero(album.getGenero());
            albumObtenido.setImagenPortada(album.getImagenPortada());
            albumObtenido.setIdArtista(album.getIdArtista().toString());
            if (album.getCanciones() != null) {
                if (album.getCanciones().size() < 2) {
                    throw new NegocioException("Necesita al menos 3 canciones para agregar un album.");
                } else {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : album.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumObtenido.setCanciones(cancionesDTO);
                }
            }

            return albumObtenido;
        } else {
            throw new NegocioException("No se pudo agregar el album a la base de datos.");
        }

    }

    /**
     * Metodo para obtener todos los albumes de la base de datos
     *
     * @return Lista con todos los albumes que se encuentren en la base de datos
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    @Override
    public List<AlbumDTO> obtenerAlbumes(String idUsuario) throws NegocioException {
        List<Album> albumes = albumesDAO.obtenerAlbumes(idUsuario);

        if (albumes != null) {
            List<AlbumDTO> albumesDTO = new ArrayList<>();

            for (Album a : albumes) {
                AlbumDTO albumDTO = new AlbumDTO();
                albumDTO.setNombre(a.getNombre());
                albumDTO.setFechaLanzamiento(a.getFechaLanzamiento());
                albumDTO.setGenero(a.getGenero());
                albumDTO.setImagenPortada(a.getImagenPortada());
                albumDTO.setIdArtista(a.getIdArtista().toString());
                if (a.getCanciones() != null) {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : a.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumDTO.setCanciones(cancionesDTO);
                }

                albumesDTO.add(albumDTO);
            }

            return albumesDTO;
        } else {
            throw new NegocioException("No se podueron obtener los albumes de la base de datos.");
        }
    }

    /**
     * Metodo para obtener todos los albumes de la base de datos y se obtiene
     * por genero
     *
     * @param idUsuario
     * @param generoBuscado Genero por el que se busca
     * @return Lista con todos los albumes que coinciden con el genero
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    @Override
    public List<AlbumDTO> obtenerAlbumesPorGenero(String idUsuario, String generoBuscado) throws NegocioException {
        List<Album> albumes = albumesDAO.obtenerAlbumesPorGenero(idUsuario, generoBuscado);

        if (albumes != null) {
            List<AlbumDTO> albumesDTO = new ArrayList<>();

            for (Album a : albumes) {
                AlbumDTO albumDTO = new AlbumDTO();
                albumDTO.setNombre(a.getNombre());
                albumDTO.setFechaLanzamiento(a.getFechaLanzamiento());
                albumDTO.setGenero(a.getGenero());
                albumDTO.setImagenPortada(a.getImagenPortada());
                albumDTO.setIdArtista(a.getIdArtista().toString());
                if (a.getCanciones() != null) {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : a.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumDTO.setCanciones(cancionesDTO);
                }

                albumesDTO.add(albumDTO);
            }

            return albumesDTO;
        } else {
            throw new NegocioException("No se podueron obtener los albumes de la base de datos.");
        }
    }

    /**
     * Metodo para obtener todos los albumes de la base de datos y se obtiene
     * por nombre
     *
     * @param idUsuario
     * @param nombreBuscado Nombre de album por el que se busca
     * @return Lista con todos los albumes que coinciden con el genero
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    @Override
    public List<AlbumDTO> obtenerAlbumesPorNombre(String idUsuario, String nombreBuscado) throws NegocioException {
        List<Album> albumes = albumesDAO.obtenerAlbumesPorNombre(idUsuario, nombreBuscado);

        if (albumes != null) {
            List<AlbumDTO> albumesDTO = new ArrayList<>();

            for (Album a : albumes) {
                AlbumDTO albumDTO = new AlbumDTO();
                albumDTO.setNombre(a.getNombre());
                albumDTO.setFechaLanzamiento(a.getFechaLanzamiento());
                albumDTO.setGenero(a.getGenero());
                albumDTO.setImagenPortada(a.getImagenPortada());
                albumDTO.setIdArtista(a.getIdArtista().toString());
                if (a.getCanciones() != null) {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : a.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumDTO.setCanciones(cancionesDTO);
                }

                albumesDTO.add(albumDTO);
            }

            return albumesDTO;
        } else {
            throw new NegocioException("No se pudieron obtener los albumes de la base de datos.");
        }

    }

    /**
     * Metodo para obtener un album de la base de datos y se obtiene por nombre
     *
     * @param nombreBuscado Nombre de album por el que se busca
     * @return Album que coincide con el nombre
     * @throws NegocioException Si ocurre una incidencia al obtener el album de
     * la base de datos
     */
    @Override
    public AlbumDTO obtenerAlbumPorNombre(String nombreBuscado) throws NegocioException {
        Album album = albumesDAO.obtenerAlbumPorNombre(nombreBuscado);

        if (album != null) {
            AlbumDTO albumDTO = new AlbumDTO();
            albumDTO.setId(album.getId().toString());
            albumDTO.setNombre(album.getNombre());
            albumDTO.setFechaLanzamiento(album.getFechaLanzamiento());
            albumDTO.setGenero(album.getGenero());
            albumDTO.setImagenPortada(album.getImagenPortada());
            albumDTO.setIdArtista(album.getIdArtista().toString());
            if (album.getCanciones() != null) {
                List<CancionDTO> cancionesDTO = new ArrayList<>();

                for (Cancion c : album.getCanciones()) {
                    CancionDTO cancionDTO = new CancionDTO();
                    cancionDTO.setId(c.getId().toString());
                    cancionDTO.setTitulo(c.getTitulo());
                    cancionDTO.setDuracion(c.getDuracion());
                    cancionDTO.setIdArtista(c.getIdArtista().toString());

                    cancionesDTO.add(cancionDTO);
                }

                albumDTO.setCanciones(cancionesDTO);
            }

            return albumDTO;
        } else {
            throw new NegocioException("No se pudo obtener el album.");
        }
    }

    /**
     * Metodo para obtener todos los algumes de la base de datos y se obtiene
     * por fecha
     *
     * @param idUsuario
     * @param fechaTexto Fecha por la que se va a buscar los albumes en la base
     * de datos
     * @return Lista con todos los albumes de quinciden con la fecha
     * @throws NegocioException Si ocurre una incidencia al obtener los albumes
     * de la base de datos
     */
    @Override
    public List<AlbumDTO> obtenerAlgumesPorFecha(String idUsuario, String fechaTexto) throws NegocioException {
        validarFormatoFecha(fechaTexto);

        List<Album> albumes = albumesDAO.obtenerAlbumesPorFecha(idUsuario, fechaTexto);

        if (albumes != null) {
            List<AlbumDTO> albumesDTO = new ArrayList<>();

            for (Album a : albumes) {
                AlbumDTO albumDTO = new AlbumDTO();
                albumDTO.setNombre(a.getNombre());
                albumDTO.setFechaLanzamiento(a.getFechaLanzamiento());
                albumDTO.setGenero(a.getGenero());
                albumDTO.setImagenPortada(a.getImagenPortada());
                albumDTO.setIdArtista(a.getIdArtista().toString());
                if (a.getCanciones() != null) {
                    List<CancionDTO> cancionesDTO = new ArrayList<>();

                    for (Cancion c : a.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setId(c.getId().toString());
                        cancionDTO.setTitulo(c.getTitulo());
                        cancionDTO.setDuracion(c.getDuracion());
                        cancionDTO.setIdArtista(c.getIdArtista().toString());

                        cancionesDTO.add(cancionDTO);
                    }

                    albumDTO.setCanciones(cancionesDTO);
                }

                albumesDTO.add(albumDTO);
            }

            return albumesDTO;
        } else {
            throw new NegocioException("No se pudieron obtener los albumes de la base de datos.");
        }

    }

    /**
     * Metodo para obtener la lista de todas las canciones pertenecientes a un
     * album
     *
     * @param idAlbum ID del album a obtener sus canciones
     * @return Lista de todas las canciones pertenecientes al album
     * @throws NegocioException Si ocurre una incidencia al obtener las
     * canciones de la base de datos
     */
    @Override
    public List<CancionDTO> obtenerCancionesPorIdAlbum(String idAlbum) throws NegocioException {
        List<Cancion> canciones = albumesDAO.obtenerCancionesPorIdAlbum(idAlbum);

        if (canciones != null) {
            List<CancionDTO> cancionesDTO = new ArrayList<>();

            for (Cancion cancion : canciones) {
                CancionDTO cancionDTO = new CancionDTO();
                cancionDTO.setId(cancion.getId().toString());
                cancionDTO.setTitulo(cancion.getTitulo());
                cancionDTO.setDuracion(cancion.getDuracion());
                cancionDTO.setIdArtista(cancion.getIdArtista().toString());

                cancionesDTO.add(cancionDTO);
            }

            return cancionesDTO;
        } else {
            throw new NegocioException("No se pudieron obtener las canciones del album.");
        }
    }

    /**
     * Metodo para validar el formato de la fecha
     *
     * @param fechaTexto Fecha ignresada a validar
     * @throws NegocioException Si ocurre una incidencia al ingresar la fecha
     */
    private void validarFormatoFecha(String fechaTexto) throws NegocioException {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(fechaTexto, formato);
        } catch (DateTimeParseException e) {
            throw new NegocioException("La fecha debe tener el formato yyyy-MM-dd");
        }
    }

    @Override
    public CancionDTO buscarCancionPorId(String idCancion) {
        return albumesDAO.buscarCancionPorId(idCancion);
    }

}
