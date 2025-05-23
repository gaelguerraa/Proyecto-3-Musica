/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import sistemamusica.dtos.AlbumDTO;
import sistemamusica.dtos.CancionDTO;
import sistemamusicadominio.Album;
import sistemamusicadominio.Cancion;
import sistemamusicadominio.Genero;
import sistemamusicadominio.Usuario;
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
    private final String CAMPO_ARTISTA_INFO = "artistaInfo";

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
                    nuevaCancion.setId(new ObjectId());
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

    /**
     * Metodo para obtener todos los albumes de la base de datos
     *
     * @param idUsuario
     * @return Lista con todos los albumes de la base de datos
     */
    @Override
    public List<Album> obtenerAlbumes(String idUsuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        List<Bson> pipeline = new ArrayList<>();

        // Paso 1: aplicar restricciones de géneros si existen
        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(CAMPO_GENERO, restricciones)));
        }

        // Paso 2: continuar con el pipeline normal
        pipeline.addAll(Arrays.asList(
            Aggregates.lookup("artistas", CAMPO_ID_ARTISTA, CAMPO_ID, CAMPO_ARTISTA_INFO),
            Aggregates.unwind("$" + CAMPO_ARTISTA_INFO),
            Aggregates.project(Projections.fields(
                Projections.computed("id", "$" + CAMPO_ID),
                Projections.include(CAMPO_NOMBRE, CAMPO_FECHA_LANZAMIENTO, CAMPO_GENERO, CAMPO_IMAGEN_PORTADA),
                Projections.computed(CAMPO_ID_ARTISTA, "$" + CAMPO_ID_ARTISTA),
                Projections.computed("nombreArtista", "$" + CAMPO_ARTISTA_INFO + ".nombre"),
                 Projections.include(CAMPO_CANCIONES)
            ))
        ));

        return ejecutarConsultaAlbumes(pipeline, coleccion);
    }


    /**
     * Metodo para obtener albumes por genero de la base de datos
     *
     * @param idUsuario
     * @param generoBuscado Genero por el que son buscados los albumes
     * @return Albumes obtenidos que compartan el mismo genero
     */
    @Override
    public List<Album> obtenerAlbumesPorGenero(String idUsuario, String generoBuscado) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);

        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.match(Filters.regex(CAMPO_GENERO, ".*" + generoBuscado + ".*", "i")));
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(CAMPO_GENERO, restricciones)));
        }

        pipeline.addAll(Arrays.asList(
                Aggregates.lookup("artistas", CAMPO_ID_ARTISTA, CAMPO_ID, CAMPO_ARTISTA_INFO),
                Aggregates.unwind("$" + CAMPO_ARTISTA_INFO),
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$" + CAMPO_ID),
                        Projections.include(CAMPO_NOMBRE, CAMPO_FECHA_LANZAMIENTO, CAMPO_GENERO, CAMPO_IMAGEN_PORTADA),
                        Projections.computed(CAMPO_ID_ARTISTA, "$" + CAMPO_ID_ARTISTA),
                        Projections.computed("nombreArtista", "$" + CAMPO_ARTISTA_INFO + ".nombre"),
                        Projections.include(CAMPO_CANCIONES)
                ))
        ));

        return ejecutarConsultaAlbumes(pipeline, coleccion);
    }


    /**
     * Metodo para obtener albumes por fecha de lanzamiento de la base de datos
     *
     * @param idUsuario
     * @param fechaTexto Fecha de lanzamiento del album por el cual se va a
     * buscar
     * @return Albumes obtenidos que compratan la misma fecha de lanzamiento
     */
    @Override
    public List<Album> obtenerAlbumesPorFecha(String idUsuario, String fechaTexto) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        ZonedDateTime inicioZdt, finZdt;

        try {
            LocalDate fechaLocal = LocalDate.parse(fechaTexto); // yyyy-MM-dd
            inicioZdt = fechaLocal.atStartOfDay(ZoneOffset.UTC);
            finZdt = fechaLocal.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1);
        } catch (DateTimeParseException e) {
            System.out.println("Fecha inválida: " + fechaTexto);
            return new ArrayList<>();
        }

        Date inicio = Date.from(inicioZdt.toInstant());
        Date fin = Date.from(finZdt.toInstant());

        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);

        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.match(Filters.and(
                Filters.gte(CAMPO_FECHA_LANZAMIENTO, inicio),
                Filters.lte(CAMPO_FECHA_LANZAMIENTO, fin)
        )));
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(CAMPO_GENERO, restricciones)));
        }

        pipeline.addAll(Arrays.asList(
            //Para cada álbum, busca en la colección artistas el documento cuyo _id coincida con el idArtista del álbum
                Aggregates.lookup("artistas", CAMPO_ID_ARTISTA, CAMPO_ID, CAMPO_ARTISTA_INFO),
                Aggregates.unwind("$" + CAMPO_ARTISTA_INFO),
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$" + CAMPO_ID),
                        Projections.include(CAMPO_NOMBRE, CAMPO_FECHA_LANZAMIENTO, CAMPO_GENERO, CAMPO_IMAGEN_PORTADA),
                        Projections.computed(CAMPO_ID_ARTISTA, "$" + CAMPO_ID_ARTISTA),
                        Projections.computed("nombreArtista", "$" + CAMPO_ARTISTA_INFO + ".nombre"),
                        Projections.include(CAMPO_CANCIONES)
                ))
        ));

        return ejecutarConsultaAlbumes(pipeline, coleccion);
    }


    /**
     * Metodo para obtener albumes por nombre de la base de datos
     *
     * @param idUsuario
     * @param nombreBuscado Nombre por el que son buscados los albumes
     * @return Albumes obtenidos que comparten el mismo nombre
     */
    @Override
    public List<Album> obtenerAlbumesPorNombre(String idUsuario, String nombreBuscado) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);

        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(Aggregates.match(Filters.regex(CAMPO_NOMBRE, ".*" + nombreBuscado + ".*", "i")));
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(CAMPO_GENERO, restricciones)));
        }

        pipeline.addAll(Arrays.asList(
                //Para cada álbum, busca en la colección artistas el documento cuyo _id coincida con el idArtista del álbum
                Aggregates.lookup("artistas", CAMPO_ID_ARTISTA, CAMPO_ID, CAMPO_ARTISTA_INFO),
                Aggregates.unwind("$" + CAMPO_ARTISTA_INFO),
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$" + CAMPO_ID),
                        Projections.include(CAMPO_NOMBRE, CAMPO_FECHA_LANZAMIENTO, CAMPO_GENERO, CAMPO_IMAGEN_PORTADA),
                        Projections.computed(CAMPO_ID_ARTISTA, "$" + CAMPO_ID_ARTISTA),
                        Projections.computed("nombreArtista", "$" + CAMPO_ARTISTA_INFO + ".nombre"),
                        Projections.include(CAMPO_CANCIONES)
                ))
        ));

        return ejecutarConsultaAlbumes(pipeline, coleccion);
    }


    /**
     * Metodo para obtener un album por nombre de la base de datos
     *
     * @param nombreBuscado Nombre por el que es buscado el album
     * @return Album obtenido que comparte el mismo nombre
     */
    @Override
    public Album obtenerAlbumPorNombre(String nombreBuscado) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq(CAMPO_NOMBRE, nombreBuscado)),
                //Busca en la colección artistas un documento cuyo _id coincida con el idArtista del álbume actual y guarda el resultado en artistaInfo
                Aggregates.lookup("artistas", CAMPO_ID_ARTISTA, CAMPO_ID, CAMPO_ARTISTA_INFO),
                Aggregates.unwind("$" + CAMPO_ARTISTA_INFO),
                Aggregates.project(Projections.fields(
                        Projections.computed("id", "$" + CAMPO_ID),
                        Projections.include(CAMPO_NOMBRE, CAMPO_FECHA_LANZAMIENTO, CAMPO_GENERO, CAMPO_IMAGEN_PORTADA),
                        Projections.computed(CAMPO_ID_ARTISTA, "$" + CAMPO_ID_ARTISTA),
                        Projections.computed("nombreArtista", "$" + CAMPO_ARTISTA_INFO + ".nombre"),
                        Projections.include(CAMPO_CANCIONES)
                )),
                Aggregates.limit(1) // <-- Importante: limitar a un resultado
        );

        List<Album> resultado = ejecutarConsultaAlbumes(pipeline, coleccion);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    /**
     * Metodo para obtener todas las canciones de un album.
     *
     * @param idAlbum ID del album a obtener sus canciones
     * @return Lista de canciones que contiene el album
     */
    @Override
    public List<Cancion> obtenerCancionesPorIdAlbum(String idAlbum) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);
        
        ObjectId objectId;
        try {
            objectId = new ObjectId(idAlbum);
        } catch (IllegalArgumentException e) {
            System.out.println("ID de album invalido:" + idAlbum);
            return new ArrayList<>();
        }
        
        Document filtro = new Document();
        filtro.append("_id", objectId);
        Document albumDoc = coleccion.find(filtro).first();
        
        if (albumDoc == null) {
            System.out.println("No se encontro el album con ID: " + idAlbum);
            return new ArrayList<>();
        }
        
        List<Cancion> canciones = new ArrayList<>();
        List<Document> cancionesDoc = (List<Document>) albumDoc.get(CAMPO_CANCIONES, List.class);
        
        if (cancionesDoc != null){
            for (Document c : cancionesDoc) {
                Cancion cancion = new Cancion();
                
                if (c.containsKey(CAMPO_ID)) {
                    cancion.setId(c.getObjectId(CAMPO_ID));
                }
                
                cancion.setTitulo(c.getString("titulo"));
                cancion.setDuracion(((Number) c.get("duracion")).floatValue());
                
                if (c.containsKey(CAMPO_ID_ARTISTA)) {
                    cancion.setIdArtista(c.getObjectId(CAMPO_ID_ARTISTA));
                }
                
                canciones.add(cancion);
            }
        }
        
        return canciones;
    }
    
    /**
     * Metodo para buscar una cancion por id
     * Busca una cancion con su id dentro del arreglo canciones en albumes y la devuelve
     * @param idCancion
     * @return cancionDTO
     */
    @Override
    public CancionDTO buscarCancionPorId(String idCancion) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> albumes = db.getCollection("albumes", Document.class);

        ObjectId id = new ObjectId(idCancion);

        List<Bson> pipeline = Arrays.asList(
            Aggregates.unwind("$canciones"),
            Aggregates.match(Filters.eq("canciones._id", id)),
            Aggregates.project(Projections.fields(
                Projections.include("canciones"),
                Projections.computed("idArtista", "$idArtista")
            ))
        );

        AggregateIterable<Document> resultado = albumes.aggregate(pipeline);

        Document doc = resultado.first();
        if (doc == null) return null;

        Document cancionDoc = (Document) doc.get("canciones");

        CancionDTO dto = new CancionDTO();
        dto.setId(cancionDoc.getObjectId("_id").toHexString());
        dto.setTitulo(cancionDoc.getString("titulo"));
        dto.setDuracion(cancionDoc.getDouble("duracion").floatValue());

        ObjectId idArtista = cancionDoc.getObjectId("idArtista");
        if (idArtista != null) {
            dto.setIdArtista(idArtista.toHexString());
        }

        return dto;
    }


    /**
     * Metodo para ejecutar la consulta a los albumes.
     *
     * @param pipeline Pipeline a utilizar en la consulta
     * @param coleccion Coleccion a la que se le realizara la consulta
     * @return Lista de albumes proveniente de la base de datos
     */
    private List<Album> ejecutarConsultaAlbumes(List<Bson> pipeline, MongoCollection<Document> coleccion) {
        List<Album> resultado = new ArrayList<>();

        try (MongoCursor<Document> cursor = coleccion.aggregate(pipeline).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Album album = new Album();
                album.setId(doc.getObjectId("id"));
                album.setNombre(doc.getString(CAMPO_NOMBRE));
                album.setFechaLanzamiento(doc.getDate(CAMPO_FECHA_LANZAMIENTO));
                album.setGenero(Genero.valueOf(doc.getString(CAMPO_GENERO)));
                album.setImagenPortada(doc.getString(CAMPO_IMAGEN_PORTADA));
                album.setIdArtista(doc.getObjectId(CAMPO_ID_ARTISTA));

                List<Cancion> canciones = new ArrayList<>();
                List<Document> cancionesDoc = (List<Document>) doc.get(CAMPO_CANCIONES, List.class);

                if (cancionesDoc != null) {
                    for (Document c : cancionesDoc) {
                        Cancion cancion = new Cancion();
                        if (c.containsKey(CAMPO_ID)) {
                            cancion.setId(c.getObjectId(CAMPO_ID));
                        }
                        cancion.setTitulo(c.getString("titulo"));
                        cancion.setDuracion(((Number) c.get("duracion")).floatValue());
                        if (c.containsKey(CAMPO_ID_ARTISTA)) {
                            cancion.setIdArtista(c.getObjectId(CAMPO_ID_ARTISTA));
                        }
                        canciones.add(cancion);
                    }
                }

                album.setCanciones(canciones);
                resultado.add(album);

            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta de albumes: " + e);
        }

        return resultado;
    }
    
    /**
     * Metodo para obtener todas las canciones existentes
     * Hace que todas las canciones sean documentos separados para luego unir las canciones con sus artistas
     * Y de esa union saca el idCancion, su titulo, su duracion, el nombre de su artista y el nombre de su album.
     * Excluye a las canciones que sean de un album que tengan un genero restringido 
     * @param idUsuario
     * @return Lista de documentos
     */
    @Override
    public List<Document> obtenerTodasLasCanciones(String idUsuario) {
            MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
            MongoCollection<Document> albumes = db.getCollection("albumes", Document.class);
            List<Bson> pipeline = new ArrayList<>();

            List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
            if (!restricciones.isEmpty()) {
                pipeline.add(Aggregates.match(Filters.nin(CAMPO_GENERO, restricciones)));
            }

            pipeline.addAll(Arrays.asList(
                Aggregates.unwind("$" + CAMPO_CANCIONES), //permite que la cancion sea un documento separado
                Aggregates.lookup("artistas", CAMPO_ID_ARTISTA, "_id", CAMPO_ARTISTA_INFO),
                Aggregates.unwind("$" + CAMPO_ARTISTA_INFO), //permite acceder a los datos del artista luego de un lookup
                Aggregates.project(Projections.fields(
                    Projections.computed("idCancion", "$" + CAMPO_CANCIONES + "._id"),
                    Projections.computed("titulo", "$" + CAMPO_CANCIONES + ".titulo"),
                    Projections.computed("duracion", "$" + CAMPO_CANCIONES + ".duracion"),
                    Projections.computed("nombreArtista", "$" + CAMPO_ARTISTA_INFO + ".nombre"),
                    Projections.computed("nombreAlbum", "$nombre")
                ))
            ));

            return albumes.aggregate(pipeline).into(new ArrayList<>());
        }

    /**
     * Metodo que busca todas las canciones con un nombre o nombre a medias
     * Aplana todas las canciones, luego filtra por las canciones que tengan de titulo el titulo que el usuario introducio
     * Para luego unirlas con sus artistas que tienen las canciones a su nombre
     * Excluye las canciones que pertenezcan a un album que tenga un genero restringido.
     * 
     * @param nombre
     * @param idUsuario
     * @return lista de documentos
     */
    @Override
    public List<Document> buscarCancionesPorNombre(String nombre, String idUsuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> albumes = db.getCollection("albumes", Document.class);
        
        List<Bson> pipeline = new ArrayList<>();

        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
        if (!restricciones.isEmpty()) {
            pipeline.add(Aggregates.match(Filters.nin(CAMPO_GENERO, restricciones)));
        }

        pipeline.addAll(Arrays.asList(
            Aggregates.unwind("$" + CAMPO_CANCIONES),
            Aggregates.match(Filters.regex(CAMPO_CANCIONES + ".titulo", Pattern.compile(nombre, Pattern.CASE_INSENSITIVE))),
            Aggregates.lookup("artistas", CAMPO_ID_ARTISTA, "_id", CAMPO_ARTISTA_INFO),
            Aggregates.unwind("$" + CAMPO_ARTISTA_INFO),
            Aggregates.project(Projections.fields(
                Projections.computed("idCancion", "$" + CAMPO_CANCIONES + "._id"),
                Projections.computed("titulo", "$" + CAMPO_CANCIONES + ".titulo"),
                Projections.computed("duracion", "$" + CAMPO_CANCIONES + ".duracion"),
                Projections.computed("nombreArtista", "$" + CAMPO_ARTISTA_INFO + ".nombre"),
                Projections.computed("idAlbum", "$_id"),
                Projections.computed("nombreAlbum", "$nombre")
            ))
        ));

        return albumes.aggregate(pipeline).into(new ArrayList<>());
    }

    
    /**
     * Metodo que obtiene los generos restringidos por el usuario busca el
     * documento con el id del usuario y devuelve sus restricciones si es que
     * tiene
     *
     * @param idUsuario
     * @return lista de generos restringidos
     */
    private List<String> obtenerGenerosRestringidos(String idUsuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection("usuarios", Usuario.class);

        //si el usuario existe y tiene generos en restricciones devolver las restricciones y agregarlas en una lista
        Usuario usuario = coleccion.find(new Document("_id", new ObjectId(idUsuario))).first();
        return (usuario != null && usuario.getRestricciones() != null)
                ? usuario.getRestricciones()
                : new ArrayList<>();
    }

}
