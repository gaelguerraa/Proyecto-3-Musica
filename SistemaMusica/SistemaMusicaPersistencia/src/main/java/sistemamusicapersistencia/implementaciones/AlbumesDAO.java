/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import sistemamusica.dtos.AlbumDTO;
import sistemamusica.dtos.CancionDTO;
import sistemamusicadominio.Album;
import sistemamusicadominio.Cancion;
import sistemamusicapersistencia.interfaces.IAlbumesDAO;

/**
 *
 * @author PC
 */
public class AlbumesDAO implements IAlbumesDAO {

    private final String COLECCION = "albumes";
    private final String CAMPO_ID = "_id";
    private final String CAMPO_NOMBRE = "nombre";
    private final String CAMPO_FECHA_LANZAMIENTO = "fechaLanzamiento";
    private final String CAMPO_GENERO = "genero";
    private final String CAMPO_IMAGEN_PORTADA = "imagenPortada";
    private final String CAMPO_ID_ARTISTA = "idArtista";
    private final String CAMPO_CANCIONES = "canciones";

    /**
     * Metodo que agrega un nuevo album a la base de datos
     *
     * @param nuevoAlbum Nuevo album a agregar a la base de datos (DTO)
     * @return Album agregado en la base de datos
     */
    @Override
    public Album agregarAlbum(AlbumDTO nuevoAlbum) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Album> coleccion = baseDatos.getCollection(
                COLECCION, Album.class);

        try {
            Album album = new Album();
            album.setNombre(nuevoAlbum.getNombre());
            album.setFechaLanzamiento(nuevoAlbum.getFechaLanzamiento());
            album.setGenero(nuevoAlbum.getGenero());
            album.setImagenPortada(nuevoAlbum.getImagenPortada());
            album.setIdArtista(new ObjectId(nuevoAlbum.getIdArtista()));

            // Si hay cancones en el DTO, se agregan al album
            if (nuevoAlbum.getCanciones() != null
                    && !nuevoAlbum.getCanciones().isEmpty()) {
                List<Cancion> canciones = new ArrayList<>();
                for (CancionDTO cancion : nuevoAlbum.getCanciones()) {
                    Cancion nuevaCancion = new Cancion();

                    nuevaCancion.setTitulo(cancion.getTitulo());
                    nuevaCancion.setDuracion(cancion.getDuracion());

                    if (cancion.getIdArtista() != null) {
                        nuevaCancion.setIdArtista(new ObjectId(cancion.getIdArtista()));
                    }

                    canciones.add(nuevaCancion);
                }

                album.setCanciones(canciones);
            } else {
                album.setCanciones(new ArrayList<>()); // Album sin canciones
            }

            coleccion.insertOne(album);
            return album;
        } catch (Exception e) {
            System.out.println("Error al agregar el album: " + e);
            return null;
        }
    }

}
