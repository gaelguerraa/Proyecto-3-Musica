/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import sistemamusica.dtos.AlbumFavoritoDTO;
import sistemamusica.dtos.ArtistaFavoritoDTO;
import sistemamusica.dtos.CancionFavoritaDTO;
import sistemamusica.dtos.GeneroFavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Favorito;
import sistemamusicadominio.Usuario;
import sistemamusicapersistencia.interfaces.IUsuariosDAO;

/**
 *
 * @author gael_
 */
public class UsuariosDAO implements IUsuariosDAO {

    private final String COLECCION = "usuarios";
    private final String CAMPO_ID = "_id";
    private final String CAMPO_USERNAME = "username";
    private final String CAMPO_EMAIL = "email";
    private final String CAMPO_CONTRASENIA = "contrasenia";
    private final String CAMPO_IMAGEN_PERFIL = "imagenPerfil";
    private final String CAMPO_FAVORITOS = "favoritos";
    private final String CAMPO_RESTRICCIONES = "restricciones";
    
    private final String CAMPO_IDFAVORITO = "idContenido";
    private final String CAMPO_TIPO_CONTENIDO = "tipoContenido";
    private final String CAMPO_FECHA_AGREGACION = "fechaAgregacion";

    /**
     * Metodo para agregar un nuevo usuario a la base de datos
     *
     * @param nuevoUsuario Nuevo usuario a agregar a la base de datos
     * @return Usuario que ha sido agregado
     */
    @Override
    public Usuario agregarUsuario(UsuarioDTO nuevoUsuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(
                COLECCION, Usuario.class);
        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(nuevoUsuario.getUsername());
            usuario.setEmail(nuevoUsuario.getEmail());
            usuario.setContrasenia(encriptarContrasenia(nuevoUsuario.getContrasenia()));
            if (nuevoUsuario.getImagenPerfil() != null) {
                usuario.setImagenPerfil(nuevoUsuario.getImagenPerfil());
            } else {
                usuario.setImagenPerfil("userImages/userPlaceholder.jpg");
            }
            if (nuevoUsuario.getFavoritos() != null) {
                usuario.setFavoritos(nuevoUsuario.getFavoritos());
            }
            if (nuevoUsuario.getRestricciones() != null) {
                usuario.setRestricciones(nuevoUsuario.getRestricciones());
            }

            coleccion.insertOne(usuario);

            return usuario;

        } catch (NoSuchAlgorithmException e) {
            System.out.println("No se pudo encriptar la contraseña: " + e);
            return null;
        }
    }

    /**
     * Metodo para consultar un usuario en base a un id en la base de datos
     *
     * @param idUsuario ID del usuario a buscar
     * @return Usuario obtenido con el id mencionado
     */
    @Override
    public Usuario consultarPorId(String idUsuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(
                COLECCION, Usuario.class);

        Document filtros = new Document();
        filtros.append(CAMPO_ID, new ObjectId(idUsuario));
        FindIterable<Usuario> usuarios = coleccion.find(filtros);
        Usuario usuario = usuarios.first();

        return usuario;
    }
    
    /**
     * Metodo para consultar un usuario en base a un username en la base de
     * datos
     *
     * @param username Nombre del usuario
     * @return Usuario obtenido con el username mencionado
     */
    @Override
    public Usuario consultarPorUsername(String username){
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(
                COLECCION, Usuario.class);
        
        Document filtros = new Document();
        filtros.append(CAMPO_USERNAME, username);
        FindIterable<Usuario> usuarios = coleccion.find(filtros);
        Usuario usuario = usuarios.first();
        
        return usuario;
    }

    /**
     * Metodo para modificar un usuario especificado por su id
     *
     * @param idUsuario ID del usuario a modificar
     * @param datosActualizados Datos a actualizar del usuario
     * @return Usuario modificado de la base de datos
     */
    @Override
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(
                COLECCION, Usuario.class);

        Document camposActualizar = new Document();

        if (datosActualizados.getUsername() != null) {
            camposActualizar.append(CAMPO_USERNAME, datosActualizados.getUsername());
        }
        if (datosActualizados.getEmail() != null) {
            camposActualizar.append(CAMPO_EMAIL, datosActualizados.getEmail());
        }
        if (datosActualizados.getContrasenia() != null) {
            try {
                camposActualizar.append(CAMPO_CONTRASENIA, encriptarContrasenia(
                        datosActualizados.getContrasenia()));
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Error al encriptar la contraseña: " + e.getMessage());
                return null;
            }
        }
        if (datosActualizados.getImagenPerfil() != null) {
            camposActualizar.append(CAMPO_IMAGEN_PERFIL, datosActualizados.getImagenPerfil());
        }

        if (camposActualizar.isEmpty()) {
            System.out.println("No hay campos para actualizar.");
            return null;
        }

        Document actualizacion = new Document("$set", camposActualizar);

        coleccion.updateOne(Filters.eq(CAMPO_ID, new ObjectId(idUsuario)), actualizacion);

        return consultarPorId(idUsuario);
    }

    /**
     * Metodo para obtener un usuario en base a un nombre de usuario y una
     * contrasenia
     *
     * @param username Nombre de usuario
     * @param contrasenia Contrasenia del usuario
     * @return Un usuario que coincida con ambos filtros
     */
    @Override
    public Usuario consultarInicioSesion(String username, String contrasenia) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(
                COLECCION, Usuario.class);
        
        Document filtro = new Document(CAMPO_USERNAME, username);
        FindIterable<Usuario> resultado = coleccion.find(filtro);
        Usuario usuario = resultado.first();
        
        if (usuario != null){
            try {
                String contraseniaBD = usuario.getContrasenia();
                String contraseniaEncriptadaIngresada = encriptarContrasenia(contrasenia);
                
                if (contraseniaBD.equals(contraseniaEncriptadaIngresada)){
                    return usuario;
                } else {
                    System.err.println("Contrasenia incorrecta.");
                }
            } catch (NoSuchAlgorithmException e){
                System.err.println("Error al encriptar la contrasenia: " + e.getMessage());
            }
        } else {
            System.err.println("Correo no registrado.");
        }
        
        return null;
    }

    @Override
    public boolean agregarFavorito(String idUsuario, Favorito favorito){
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        Document nuevoFavorito = new Document()
                .append("idContenido", favorito.getIdContenido())
                .append("tipo", favorito.getTipo().name())
                .append("fechaAgregacion", favorito.getFechaAgregacion());
        
        Document filtroDuplicado = new Document("_id", new ObjectId(idUsuario))
                .append("favoritos.idContenido", favorito.getIdContenido())
                .append("favoritos.tipo", favorito.getTipo().name());
        
                Document usuarioExistente = usuarios.find(filtroDuplicado).first();
                if (usuarioExistente != null) {
                    return false; // Ya está en favoritos
                }
        UpdateResult resultado = usuarios.updateOne(
            Filters.eq("_id", new ObjectId(idUsuario)),
            Updates.push("favoritos", nuevoFavorito)
        );
            return resultado.getModifiedCount() > 0;
    }
    
    @Override
    public boolean eliminarFavorito(String idUsuario, String idContenido) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        Document favorito = new Document()
            .append("idContenido",new ObjectId(idContenido));

        UpdateResult resultado = usuarios.updateOne(
            Filters.eq("_id", new ObjectId(idUsuario)),
            Updates.pull("favoritos", favorito)
        );

        return resultado.getModifiedCount() > 0;
    }
    
    @Override
    public List<ArtistaFavoritoDTO> obtenerArtistasFavoritos(String idUsuario, String nombreArtista) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))), // Buscar al usuario
        Aggregates.unwind("$favoritos"), // Separar cada favorito
        Aggregates.match(Filters.eq("favoritos.tipo", "ARTISTA")), // Solo favoritos tipo artista
        Aggregates.lookup("artistas", "favoritos.idContenido", "_id", "artistaInfo"), // Join con colección de artistas
        Aggregates.unwind("$artistaInfo"), // Unir cada favorito con su artista
        Aggregates.match(Filters.regex("artistaInfo.nombre", ".*" + Pattern.quote(nombreArtista) + ".*", "i")), // Filtrar por nombre con regex (ignora mayúsculas)
        Aggregates.project(Projections.fields(
            Projections.computed("nombreArtista", "$artistaInfo.nombre"),
            Projections.computed("tipoArtista", "$artistaInfo.tipo"),
            Projections.computed("generoArtista", "$artistaInfo.genero"),
            Projections.computed("fechaAgregacion", "$favoritos.fecha")
        ))
    );

        List<ArtistaFavoritoDTO> resultado = new ArrayList<>();
        for (Document doc : usuarios.aggregate(pipeline)) {
            ArtistaFavoritoDTO dto = new ArtistaFavoritoDTO();
            dto.setNombreArtista(doc.getString("nombreArtista"));
            dto.setTipoArtista(doc.getString("tipoArtista"));
            dto.setGeneroArtista(doc.getString("generoArtista"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
            resultado.add(dto);
        }

        return resultado;
    }


    @Override
    public List<AlbumFavoritoDTO> obtenerAlbumesFavoritos(String idUsuario, String nombreAlbum ) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))), // Buscar al usuario
        Aggregates.unwind("$favoritos"), // Separar cada favorito
        Aggregates.match(Filters.eq("favoritos.tipo", "ALBUM")), // Solo favoritos tipo álbum
        Aggregates.lookup("albumes", "favoritos.idContenido", "_id", "albumInfo"), // Join con colección de álbumes
        Aggregates.unwind("$albumInfo"), // Unir cada favorito con su álbum
        Aggregates.lookup("artistas", "albumInfo.idArtista", "_id", "artistaInfo"), // Join adicional con artistas
        Aggregates.unwind("$artistaInfo"), // Unir cada álbum con su artista
        Aggregates.match(Filters.regex("albumInfo.nombre", ".*" + Pattern.quote(nombreAlbum) + ".*", "i")), // Filtrar por nombre de álbum
        Aggregates.project(Projections.fields(
            Projections.computed("nombreAlbum", "$albumInfo.nombre"),
            Projections.computed("nombreArtista", "$artistaInfo.nombre"),
            Projections.computed("genero", "$albumInfo.genero"),
            Projections.computed("fechaLanzamiento", "$albumInfo.fechaLanzamiento"),
            Projections.computed("fechaAgregacion", "$favoritos.fecha")

        ))
    );

    List<AlbumFavoritoDTO> resultado = new ArrayList<>();
    for (Document doc : usuarios.aggregate(pipeline)) {
        AlbumFavoritoDTO dto = new AlbumFavoritoDTO();
        dto.setNombreAlbum(doc.getString("nombreAlbum"));
        dto.setNombreArtista(doc.getString("nombreArtista"));
        dto.setGenero(doc.getString("genero")); // Asume que Genero es un enum
        dto.setFechaLanzamiento(doc.getDate("fechaLanzamiento"));
        dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
        resultado.add(dto);
    }

    return resultado;

        
    }
    
    @Override
    public List<CancionFavoritaDTO> obtenerCancionesFavoritas(String idUsuario, String nombreCancion) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))), // Buscar al usuario
        Aggregates.unwind("$favoritos"), // Separar cada favorito
        Aggregates.match(Filters.eq("favoritos.tipo", "CANCION")), // Solo favoritos tipo canción
        Aggregates.lookup("canciones", "favoritos.idContenido", "_id", "cancionInfo"), // Join con canciones
        Aggregates.unwind("$cancionInfo"), // Unir cada favorito con su canción
        Aggregates.lookup("artistas", "cancionInfo.idArtista", "_id", "artistaInfo"), // Join con artistas
        Aggregates.unwind("$artistaInfo"), // Unir cada canción con su artista
        Aggregates.lookup("albumes", "cancionInfo.idAlbum", "_id", "albumInfo"), // Join con álbumes
        Aggregates.unwind("$albumInfo"), // Unir cada canción con su álbum
        Aggregates.match(Filters.regex("cancionInfo.titulo", ".*" + Pattern.quote(nombreCancion) + ".*", "i")), // Filtrar por título
        Aggregates.project(Projections.fields(
            Projections.computed("titulo", "$cancionInfo.titulo"),
            Projections.computed("duracion", "$cancionInfo.duracion"),
            Projections.computed("nombreArtista", "$artistaInfo.nombre"),
            Projections.computed("nombreAlbum", "$albumInfo.nombre"),
            Projections.computed("genero", "$albumInfo.genero"),
            Projections.computed("fechaLanzamiento", "$albumInfo.fechaLanzamiento"),
            Projections.computed("fechaAgregacion", "$favoritos.fecha")
        ))
        );

        List<CancionFavoritaDTO> resultado = new ArrayList<>();
        for (Document doc : usuarios.aggregate(pipeline)) {
            CancionFavoritaDTO dto = new CancionFavoritaDTO();
            dto.setTitulo(doc.getString("titulo"));
            dto.setDuracion(doc.getDouble("duracion").floatValue());
            dto.setNombreArtista(doc.getString("nombreArtista"));
            dto.setNombreAlbum(doc.getString("nombreAlbum"));
            dto.setGenero(doc.getString("genero"));
            dto.setFechaLanzamiento(doc.getDate("fechaLanzamiento"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));

            resultado.add(dto);
        }

        return resultado;
        
    }
    
    @Override
    public List<GeneroFavoritoDTO> obtenerGenerosFavoritos(String idUsuario, String genero) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION); // o álbumes/artistas si aplican

        List<GeneroFavoritoDTO> resultado = new ArrayList<>();

        // ----------- Pipeline para ARTISTAS -----------
        List<Bson> pipelineArtistas = Arrays.asList(
            Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
            Aggregates.unwind("$favoritos"),
            Aggregates.match(Filters.eq("favoritos.tipo", "ARTISTA")),
            Aggregates.lookup("artistas", "favoritos.idContenido", "_id", "artistaInfo"),
            Aggregates.unwind("$artistaInfo"),
            Aggregates.match(Filters.eq("artistaInfo.genero", genero)),
            Aggregates.project(Projections.fields(
                Projections.computed("tipo", new BsonString("artista")),
                Projections.computed("nombre", "$artistaInfo.nombre"),
                Projections.computed("genero", "$artistaInfo.genero"),
                Projections.computed("fechaAgregacion", "$favoritos.fecha")
            ))
        );

        // ----------- Pipeline para ALBUMES -----------
        List<Bson> pipelineAlbumes = Arrays.asList(
            Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
            Aggregates.unwind("$favoritos"),
            Aggregates.match(Filters.eq("favoritos.tipo", "ALBUM")),
            Aggregates.lookup("albumes", "favoritos.idContenido", "_id", "albumInfo"),
            Aggregates.unwind("$albumInfo"),
            Aggregates.match(Filters.eq("albumInfo.genero", genero)),
            Aggregates.project(Projections.fields(
                Projections.computed("tipo", new BsonString("album")),
                Projections.computed("nombre", "$albumInfo.nombre"),
                Projections.computed("genero", "$albumInfo.genero"),
                Projections.computed("fechaAgregacion", "$favoritos.fecha")
            ))
        );

        // Ejecutar ambos pipelines
        for (Document doc : usuarios.aggregate(pipelineArtistas)) {
            GeneroFavoritoDTO dto = new GeneroFavoritoDTO();
            dto.setTipo(doc.getString("tipo"));
            dto.setNombre(doc.getString("nombre"));
            dto.setGenero(doc.getString("genero"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
            resultado.add(dto);
        }

        for (Document doc : usuarios.aggregate(pipelineAlbumes)) {
            GeneroFavoritoDTO dto = new GeneroFavoritoDTO();
            dto.setTipo(doc.getString("tipo"));
            dto.setNombre(doc.getString("nombre"));
            dto.setGenero(doc.getString("genero"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
            resultado.add(dto);
        }

        return resultado;

    }
    
    @Override
    public List<GeneroFavoritoDTO> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        List<GeneroFavoritoDTO> resultado = new ArrayList<>();

        // Pipeline para ARTISTAS
        List<Bson> pipelineArtistas = Arrays.asList(
            Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
            Aggregates.unwind("$favoritos"),
            Aggregates.match(Filters.and(
                Filters.eq("favoritos.tipo", "ARTISTA"),
                Filters.gte("favoritos.fechaAgregacion", fechaInicio),
                Filters.lte("favoritos.fechaAgregacion", fechaFin)
            )),
            Aggregates.lookup("artistas", "favoritos.idContenido", "_id", "artistaInfo"),
            Aggregates.unwind("$artistaInfo"),
            Aggregates.project(Projections.fields(
                Projections.computed("tipo", new BsonString("artista")),
                Projections.computed("nombre", "$artistaInfo.nombre"),
                Projections.computed("genero", "$artistaInfo.genero"),
                Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")
            ))
        );

        // Pipeline para ALBUMES
        List<Bson> pipelineAlbumes = Arrays.asList(
            Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
            Aggregates.unwind("$favoritos"),
            Aggregates.match(Filters.and(
                Filters.eq("favoritos.tipo", "ALBUM"),
                Filters.gte("favoritos.fechaAgregacion", fechaInicio),
                Filters.lte("favoritos.fechaAgregacion", fechaFin)
            )),
            Aggregates.lookup("albumes", "favoritos.idContenido", "_id", "albumInfo"),
            Aggregates.unwind("$albumInfo"),
            Aggregates.project(Projections.fields(
                Projections.computed("tipo", new BsonString("album")),
                Projections.computed("nombre", "$albumInfo.nombre"),
                Projections.computed("genero", "$albumInfo.genero"),
                Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")
            ))
        );

        // Pipeline para CANCIONES (si es necesario)
        List<Bson> pipelineCanciones = Arrays.asList(
            Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
            Aggregates.unwind("$favoritos"),
            Aggregates.match(Filters.and(
                Filters.eq("favoritos.tipo", "CANCION"),
                Filters.gte("favoritos.fechaAgregacion", fechaInicio),
                Filters.lte("favoritos.fechaAgregacion", fechaFin)
            )),
            Aggregates.lookup("canciones", "favoritos.idContenido", "_id", "cancionInfo"),
            Aggregates.unwind("$cancionInfo"),
            Aggregates.lookup("albumes", "cancionInfo.idAlbum", "_id", "albumInfo"),
            Aggregates.unwind("$albumInfo"),
            Aggregates.project(Projections.fields(
                Projections.computed("tipo", new BsonString("cancion")),
                Projections.computed("nombre", "$cancionInfo.titulo"),
                Projections.computed("genero", "$albumInfo.genero"),
                Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")
            ))
        );

        // Ejecutar pipelines y procesar resultados
        procesarPipeline(pipelineArtistas, resultado);
        procesarPipeline(pipelineAlbumes, resultado);
        procesarPipeline(pipelineCanciones, resultado);

        return resultado;
    }
    
    @Override
    public List<GeneroFavoritoDTO> obtenerTodosFavoritos(String idUsuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        List<GeneroFavoritoDTO> resultado = new ArrayList<>();

        // Pipeline para ARTISTAS
        List<Bson> pipelineArtistas = Arrays.asList(
            Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
            Aggregates.unwind("$favoritos"),
            Aggregates.match(Filters.eq("favoritos.tipo", "ARTISTA")),
            Aggregates.lookup("artistas", "favoritos.idContenido", "_id", "artistaInfo"),
            Aggregates.unwind("$artistaInfo"),
            Aggregates.project(Projections.fields(
                Projections.computed("tipo", new BsonString("artista")),
                Projections.computed("nombre", "$artistaInfo.nombre"),
                Projections.computed("genero", "$artistaInfo.genero"),
                Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")
            ))
        );

        // Pipeline para ALBUMES
        List<Bson> pipelineAlbumes = Arrays.asList(
            Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
            Aggregates.unwind("$favoritos"),
            Aggregates.match(Filters.eq("favoritos.tipo", "ALBUM")),
            Aggregates.lookup("albumes", "favoritos.idContenido", "_id", "albumInfo"),
            Aggregates.unwind("$albumInfo"),
            Aggregates.project(Projections.fields(
                Projections.computed("tipo", new BsonString("album")),
                Projections.computed("nombre", "$albumInfo.nombre"),
                Projections.computed("genero", "$albumInfo.genero"),
                Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")
            ))
        );

        // Pipeline para CANCIONES
        List<Bson> pipelineCanciones = Arrays.asList(
            Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
            Aggregates.unwind("$favoritos"),
            Aggregates.match(Filters.eq("favoritos.tipo", "CANCION")),
            Aggregates.lookup("canciones", "favoritos.idContenido", "_id", "cancionInfo"),
            Aggregates.unwind("$cancionInfo"),
            Aggregates.lookup("albumes", "cancionInfo.idAlbum", "_id", "albumInfo"),
            Aggregates.unwind("$albumInfo"),
            Aggregates.project(Projections.fields(
                Projections.computed("tipo", new BsonString("cancion")),
                Projections.computed("nombre", "$cancionInfo.titulo"),
                Projections.computed("genero", "$albumInfo.genero"),
                Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")
            ))
        );

        // Ejecutar todos los pipelines y combinar resultados
        procesarPipeline(pipelineArtistas, resultado);
        procesarPipeline(pipelineAlbumes, resultado);
        procesarPipeline(pipelineCanciones, resultado);

        // Ordenar por fecha de agregación (opcional)
        resultado.sort(Comparator.comparing(GeneroFavoritoDTO::getFechaAgregacion).reversed());

        return resultado;
    }

    private void procesarPipeline(List<Bson> pipeline, List<GeneroFavoritoDTO> resultado) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        for (Document doc : usuarios.aggregate(pipeline)) {
            GeneroFavoritoDTO dto = new GeneroFavoritoDTO();
            dto.setTipo(doc.getString("tipo"));
            dto.setNombre(doc.getString("nombre"));
            dto.setGenero(doc.getString("genero"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
            resultado.add(dto);
        }
    }




    /**
     * Metodo para encriptar la contrasenia utilizando SHA-256
     *
     * @param contrasenia Contrasenia a encriptar
     * @return Regresa la contrasenia encriptada (o hasheada)
     * @throws NoSuchAlgorithmException
     */
    private static String encriptarContrasenia(String contrasenia) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Se usa el algorimto SHA-256
        byte[] hashBytes = digest.digest(contrasenia.getBytes(StandardCharsets.UTF_8)); // Realiza el hash
        return bytesAHex(hashBytes);
    }

    /**
     * Metodo que convierte un arreglo de bytes a una cadena hexadecimal
     *
     * @param bytes Arreglo de bytes a convertir
     * @return Cadena de Hexadecimal
     */
    private static String bytesAHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
