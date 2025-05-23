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
    
    private final String COLECCION_ARTISTAS = "artistas";
    private final String COLECCION_ALBUMES = "albumes";
  
    /**
     * Metodo que inserta masivamente 60 artistas, siendo 30 de estos solistas y 30 bandas, por cada artista 
     * añade 2 albumes con 3 canciones
     */
  @Override
    public void insertarDatos() {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> colArtistas = db.getCollection(COLECCION_ARTISTAS, Artista.class);
        MongoCollection<Album> colAlbumes = db.getCollection(COLECCION_ALBUMES, Album.class);

        Faker faker = new Faker();
        Random random = new Random();

        List<Artista> artistas = new ArrayList<>();
        List<Album> albumes = new ArrayList<>();

        for (int i = 1; i <= 60; i++) {
            boolean esSolista = i <= 30;

            Artista artista = new Artista();
            artista.setId(new ObjectId());
            artista.setNombre(faker.artist().name());
            artista.setGenero(Genero.values()[random.nextInt(Genero.values().length)]);
            artista.setImagen("https://dummyimage.com/300x300");
            artista.setTipo(esSolista ? TipoArtista.SOLISTA : TipoArtista.BANDA);

            if (!esSolista) {
                List<Integrante> integrantes = new ArrayList<>();
                int cantidadIntegrantes = 2 + random.nextInt(3);
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

            for (int j = 0; j < 2; j++) {
                Album album = new Album();
                album.setId(new ObjectId());
                album.setNombre(faker.book().title());
                album.setGenero(artista.getGenero());
                album.setFechaLanzamiento(faker.date().past(2000, TimeUnit.DAYS));
                album.setImagenPortada("https://dummyimage.com/200x200");
                album.setIdArtista(artista.getId());

                List<Cancion> cancionesAlbum = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    Cancion cancion = new Cancion();
                    cancion.setId(new ObjectId());
                    cancion.setTitulo(faker.rockBand().name() + " " + faker.music().instrument());
                    cancion.setDuracion(2 + random.nextFloat() * 4);
                    cancion.setIdArtista(artista.getId());

                    cancionesAlbum.add(cancion);
                }

                album.setCanciones(cancionesAlbum);
                albumes.add(album);
            }
        }

        colArtistas.insertMany(artistas);
        colAlbumes.insertMany(albumes);

        System.out.println("Datos de prueba insertados correctamente.");
    }


}
