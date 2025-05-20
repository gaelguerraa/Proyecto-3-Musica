/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sistemamusicapersistencia.dao.test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import sistemamusica.dtos.AlbumDTO;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.CancionDTO;
import sistemamusicadominio.Album;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Genero;
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

        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> albumes = baseDatos.getCollection("albumes");
        MongoCollection<Document> artistas = baseDatos.getCollection("artistas");

        // Limpia la colección para un entorno limpio
        albumes.deleteMany(new Document());
        artistas.deleteMany(new Document());
    }

    @Test
    public void testAgregarAlbumConCanciones() {
        final String NOMBRE_ALBUM_ESPERADO = "Blanco y Negro";
        final int CANTIDAD_CANCIONES = 2;
        final String NOMBRE_CANCION_ESPERADA = "Invierno de cristal";

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

        assertNotNull(albumObtenido);
        assertEquals(NOMBRE_ALBUM_ESPERADO, albumObtenido.getNombre());
        assertEquals(CANTIDAD_CANCIONES, albumObtenido.getCanciones().size());
        assertEquals(NOMBRE_CANCION_ESPERADA, albumObtenido.getCanciones().get(0).getTitulo());
    }

    @Test
    public void testObtenerAlbumes() {
        final String NOMBRE_ALBUM_ESPERADO = "21st Century Breakdown";

        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> albumes = db.getCollection("albumes");
        MongoCollection<Document> artistas = db.getCollection("artistas");

        // Limpia la colección para un entorno limpio
        albumes.deleteMany(new Document());
        artistas.deleteMany(new Document());

        // Insertar artista
        ObjectId idArtista = new ObjectId();
        Document artista = new Document("_id", idArtista)
                .append("tipo", TipoArtista.BANDA.toString())
                .append("nombre", "Green Day")
                .append("genero", Genero.ROCK.toString());
        artistas.insertOne(artista);

        // Insertar álbum con canciones embebidas
        Document cancion = new Document("titulo", "Know Your Enemy")
                .append("duracion", 3.11f)
                .append("idArtista", idArtista);

        Document album = new Document("nombre", "21st Century Breakdown")
                .append("fechaLanzamiento", new Date())
                .append("genero", Genero.ROCK.toString())
                .append("imagenPortada", "imagen.jpg")
                .append("idArtista", idArtista)
                .append("canciones", Arrays.asList(cancion));
        albumes.insertOne(album);

        // Probar método
        AlbumesDAO dao = new AlbumesDAO();
        List<Album> resultado = dao.obtenerAlbumes();

        assertFalse(resultado.isEmpty());
        assertEquals(NOMBRE_ALBUM_ESPERADO, resultado.get(0).getNombre());
    }

    @Test
    public void testObtenerAlbumesPorGenero() {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection("albumes");
        MongoCollection<Document> coleccionArtistas = db.getCollection("artistas");

        ObjectId artistaId = new ObjectId();
        Document artista = new Document("_id", artistaId)
                .append("nombre", "Artista de Rock Prueba");

        coleccionArtistas.insertOne(artista);

        Document album = new Document("_id", new ObjectId())
                .append("nombre", "Album Rock Test")
                .append("fechaLanzamiento", new Date())
                .append("genero", Genero.ROCK.toString())
                .append("imagenPortada", "imagen.jpg")
                .append("idArtista", artistaId)
                .append("canciones", Arrays.asList(
                        new Document("_id", new ObjectId())
                                .append("titulo", "Cancion 1")
                                .append("duracion", 3.5f)
                                .append("idArtista", artistaId)
                ));

        coleccion.insertOne(album);

        AlbumesDAO dao = new AlbumesDAO();
        List<Album> albumes = dao.obtenerAlbumesPorGenero("ROCK");

        assertNotNull(albumes);
        assertFalse(albumes.isEmpty(), "La lista no debe estar vacia");
        assertTrue(albumes.stream().anyMatch(a -> a.getNombre().equals("Album Rock Test")));

    }

    @Test
    public void testObtenerAlbumesPorNombre() {
        final int CANTIDAD_ALBUMES_ENCONTRADOS_ESPERADOS = 1;

        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection("albumes");
        MongoCollection<Document> coleccionArtistas = db.getCollection("artistas");

        ObjectId artistaId = new ObjectId();
        Document artista = new Document("_id", artistaId)
                .append("nombre", "Artista de Pop Prueba");

        coleccionArtistas.insertOne(artista);

        Document album = new Document("_id", new ObjectId())
                .append("nombre", "Greatest 80s Hits Test")
                .append("fechaLanzamiento", new Date())
                .append("genero", Genero.POP.toString())
                .append("imagenPortada", "imagen.jpg")
                .append("idArtista", artistaId)
                .append("canciones", Arrays.asList(
                        new Document("_id", new ObjectId())
                                .append("titulo", "Cancion 1")
                                .append("duracion", 3.5f)
                                .append("idArtista", artistaId)
                ));

        coleccion.insertOne(album);

        Document album2 = new Document("_id", new ObjectId())
                .append("nombre", "Album Rock Test")
                .append("fechaLanzamiento", new Date())
                .append("genero", Genero.ROCK.toString())
                .append("imagenPortada", "imagen.jpg")
                .append("idArtista", artistaId)
                .append("canciones", Arrays.asList(
                        new Document("_id", new ObjectId())
                                .append("titulo", "Cancion 1")
                                .append("duracion", 3.5f)
                                .append("idArtista", artistaId)
                ));

        coleccion.insertOne(album2);

        AlbumesDAO dao = new AlbumesDAO();
        List<Album> albumes = dao.obtenerAlbumesPorNombre("Hits");

        assertNotNull(albumes);
        assertFalse(albumes.isEmpty(), "La lista no debe estar vacia");
        assertTrue(albumes.stream().anyMatch(a -> a.getNombre().contains("Hits")));
        assertEquals(CANTIDAD_ALBUMES_ENCONTRADOS_ESPERADOS, albumes.size());

    }

    @Test
    public void testObtenerAlbumesPorFecha() {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection("albumes");
        MongoCollection<Document> coleccionArtistas = db.getCollection("artistas");

        ObjectId artistaId = new ObjectId();
        Date fechaLanzamiento = java.sql.Date.valueOf("2024-10-15");
        Document artista = new Document("_id", artistaId)
                .append("nombre", "Artista de Prueba");

        coleccionArtistas.insertOne(artista);

        Document album = new Document("_id", new ObjectId())
                .append("nombre", "Fecha Exacta Test")
                .append("fechaLanzamiento", fechaLanzamiento)
                .append("genero", "JAZZ")
                .append("imagenPortada", "jazz.jpg")
                .append("idArtista", artistaId)
                .append("canciones", Arrays.asList(
                        new Document("_id", new ObjectId())
                                .append("titulo", "Cancion 1")
                                .append("duracion", 3.5f)
                                .append("idArtista", artistaId)
                ));

        coleccion.insertOne(album);

        AlbumesDAO dao = new AlbumesDAO();
        List<Album> albumes = dao.obtenerAlbumesPorFecha("2024-10-15");

        assertNotNull(albumes);
        assertFalse(albumes.isEmpty(), "La lista no debe estar vacia");
        assertTrue(albumes.stream().anyMatch(a -> a.getNombre().equals("Fecha Exacta Test")));

    }

}
