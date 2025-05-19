/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sistemamusicapersistencia.dao.test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.*;
import sistemamusica.dtos.AlbumDTO;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.CancionDTO;
import sistemamusicadominio.Album;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Genero;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.TipoArtista;
import sistemamusicapersistencia.implementaciones.AlbumesDAO;
import sistemamusicapersistencia.implementaciones.ArtistasDAO;
import sistemamusicapersistencia.implementaciones.ManejadorConexiones;

/**
 *
 * @author PC
 */
public class AlbumesDAOTest {

    private final String COLECCION_ALBUMES = "albumes";
    private final String COLECCION_ARTISTA = "artistas";
    private final String CAMPO_ID = "_id";
    private Album albumGuardado;
    private Artista artistaGuardado;

    public AlbumesDAOTest() {
    }

    @BeforeAll
    public static void activarModoPruebas() {
        ManejadorConexiones.activateTestMode();
    }

    @AfterEach
    public void limpiarBD() {
        if (albumGuardado != null) {
            MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
            MongoCollection<Album> coleccion = baseDatos.getCollection(
                    COLECCION_ALBUMES, Album.class);

            coleccion.deleteOne(Filters.eq(CAMPO_ID, albumGuardado.getId()));
            albumGuardado = null;
        }

        if (artistaGuardado != null) {
            MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
            MongoCollection<Artista> coleccion = baseDatos.getCollection(
                    COLECCION_ARTISTA, Artista.class);

            coleccion.deleteOne(Filters.eq(CAMPO_ID, artistaGuardado.getId()));
            artistaGuardado = null;
        }
    }

    @Test
    public void testAgregarAlbumConCanciones() {
        ArtistasDAO artistasDAO = new ArtistasDAO();

        ArtistaDTO artistaAgregar = new ArtistaDTO();
        artistaAgregar.setTipo(TipoArtista.SOLISTA);
        artistaAgregar.setNombre("Ricardo Arjona");
        artistaAgregar.setImagen("imagen perrona de arjona");
        artistaAgregar.setGenero(Genero.ROMANCE);
        Artista artistaAgregado = artistasDAO.registrarArtista(artistaAgregar);
        artistaGuardado = artistaAgregado;

        CancionDTO cancion1 = new CancionDTO();
        cancion1.setTitulo("Invierno de cristal");
        cancion1.setDuracion(2.42f);
        cancion1.setIdArtista(artistaAgregado.getId().toString());

        CancionDTO cancion2 = new CancionDTO();
        cancion2.setTitulo("Batichica");
        cancion2.setDuracion(1.38f);
        cancion2.setIdArtista(artistaAgregado.getId().toString());

        List<CancionDTO> canciones = Arrays.asList(cancion1, cancion2);

        AlbumDTO nuevoAlbum = new AlbumDTO();
        nuevoAlbum.setNombre("Blanco y Negro");
        nuevoAlbum.setFechaLanzamiento(new Date());
        nuevoAlbum.setGenero(Genero.ROMANCE);
        nuevoAlbum.setImagenPortada("Imagen mamalona blancoynegro");
        nuevoAlbum.setIdArtista(artistaAgregado.getId().toString());
        nuevoAlbum.setCanciones(canciones);

        AlbumesDAO albumesDAO = new AlbumesDAO();

        Album albumObtenido = albumesDAO.agregarAlbum(nuevoAlbum);
        albumGuardado = albumObtenido;
    }

}
