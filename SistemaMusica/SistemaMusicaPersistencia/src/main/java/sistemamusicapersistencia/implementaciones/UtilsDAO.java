/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.github.javafaker.Faker;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.bson.types.ObjectId;
import sistemamusicadominio.Album;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Cancion;
import sistemamusicadominio.Genero;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicadominio.TipoArtista;
import sistemamusicapersistencia.interfaces.IUtilsDAO;

/**
 *
 * @author gael_
 */
public class UtilsDAO implements IUtilsDAO {
    
    @Override
    public void insertarDatos() {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> colArtistas = db.getCollection("artistas", Artista.class);
        MongoCollection<Album> colAlbumes = db.getCollection("albumes", Album.class);
        MongoCollection<Cancion> colCanciones = db.getCollection("canciones", Cancion.class);

        Faker faker = new Faker();
        Random random = new Random();

        List<Artista> artistas = new ArrayList<>();
        List<Album> albumes = new ArrayList<>();
        List<Cancion> canciones = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            boolean esSolista = i <= 15;

            Artista artista = new Artista();
            artista.setId(new ObjectId());
            artista.setNombre(faker.artist().name());
            artista.setGenero(Genero.values()[random.nextInt(Genero.values().length)]);
            artista.setImagen("https://dummyimage.com/300x300");
            artista.setTipo(esSolista ? TipoArtista.SOLISTA : TipoArtista.BANDA);

            // Si es banda, agregamos entre 2 y 4 integrantes
            if (!esSolista) {
                List<Integrante> integrantes = new ArrayList<>();
                int cantidadIntegrantes = 2 + random.nextInt(3); // 2 a 4 integrantes
                for (int j = 0; j < cantidadIntegrantes; j++) {
                    Integrante integrante = new Integrante();
                    integrante.setNombre(faker.name().fullName());
                    integrante.setRol(RolIntegrante.values()[random.nextInt(RolIntegrante.values().length)]);
                    integrante.setFechaIngreso(faker.date().past(3000, TimeUnit.DAYS));
                    integrante.setFechaSalida(null);
                    integrante.setActivo(true);
                    integrantes.add(integrante);
                }
                artista.setIntegrantes(integrantes);
            }

            artistas.add(artista);

            // Crear al menos 2 álbumes por artista
            for (int j = 0; j < 2; j++) {
                Album album = new Album();
                album.setId(new ObjectId());
                album.setNombre(faker.book().title());
                album.setGenero(artista.getGenero());
                album.setFechaLanzamiento(faker.date().past(2000, TimeUnit.DAYS));
                album.setImagenPortada("https://dummyimage.com/200x200");
                album.setIdArtista(artista.getId());

                List<Cancion> cancionesAlbum = new ArrayList<>();

                // Crear al menos 3 canciones por álbum
                for (int k = 0; k < 3; k++) {
                    Cancion cancion = new Cancion();
                    cancion.setId(new ObjectId());
                    cancion.setTitulo(faker.rockBand().name() + " " + faker.music().instrument());
                    cancion.setDuracion(2 + random.nextFloat() * 4); // Entre 2 y 6 min
                    cancion.setIdArtista(artista.getId());

                    canciones.add(cancion);
                    cancionesAlbum.add(cancion);
                }

                album.setCanciones(cancionesAlbum);
                albumes.add(album);
            }
        }

        // Inserción en Mongo
        colArtistas.insertMany(artistas);
        colAlbumes.insertMany(albumes);
        colCanciones.insertMany(canciones);

        System.out.println("Datos de prueba insertados correctamente.");
    }

}
