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
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import sistemamusica.dtos.FavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Favorito;
import sistemamusicadominio.TipoContenido;
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
    public boolean agregarFavorito(String idUsuario, FavoritoDTO favoritoDTO) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        Document nuevoFavorito = new Document()
                .append("_id", new ObjectId())
                .append("idContenido", new ObjectId(favoritoDTO.getIdContenido()))
                .append("tipo", favoritoDTO.getTipo().name())
                .append("nombre", favoritoDTO.getNombreContenido())
                .append("genero", favoritoDTO.getGeneroContenido())
                .append("fechaAgregacion", favoritoDTO.getFechaAgregacion());

        Document filtroDuplicado = new Document("_id", new ObjectId(idUsuario))
                .append("favoritos.idContenido", new ObjectId(favoritoDTO.getIdContenido()))
                .append("favoritos.tipo", favoritoDTO.getTipo().name());

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
    public List<Document> obtenerArtistasFavoritos(String idUsuario, String nombreArtista) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
        Aggregates.unwind("$favoritos"),
        Aggregates.match(Filters.and(
            Filters.eq("favoritos.tipo", TipoContenido.ARTISTA.name()),
            Filters.regex("favoritos.nombre", ".*" + nombreArtista + ".*", "i")
        )),
        Aggregates.lookup("artistas", "favoritos.idContenido", "_id", "artistaInfo"),
        Aggregates.unwind("$artistaInfo"),
        Aggregates.project(Projections.fields(
            Projections.computed("idFavorito", "$favoritos._id"),
            Projections.computed("idArtista", "$favoritos.idContenido"),    
            Projections.computed("tipoArtista","$artistaInfo.tipo"),
            Projections.computed("nombreArtista", "$artistaInfo.nombre"),
            Projections.computed("generoArtista", "$artistaInfo.genero"),
            Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")

        ))
    );
        List<Document> resultado = new ArrayList<>();
        for (Document doc : usuarios.aggregate(pipeline)) {
            resultado.add(doc);
        }
        return resultado;
    }


    @Override
    public List<Document> obtenerAlbumesFavoritos(String idUsuario, String nombreAlbum ) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
        Aggregates.unwind("$favoritos"),
        Aggregates.match(Filters.and(
            Filters.eq("favoritos.tipo", TipoContenido.ALBUM.name()),
            Filters.regex("favoritos.nombre", ".*" + nombreAlbum + ".*", "i")
        )),
        Aggregates.lookup("albumes", "favoritos.idContenido", "_id", "albumInfo"),
        Aggregates.unwind("$albumInfo"),
        Aggregates.lookup("artistas", "albumInfo.idArtista", "_id", "artistaInfo"),
        Aggregates.unwind("$artistaInfo"),
        Aggregates.project(Projections.fields(
            Projections.computed("idFavorito", "$favoritos._id"),
            Projections.computed("idAlbum", "$favoritos.idContenido"),
            Projections.computed("nombreAlbum", "$albumInfo.nombre"),
            Projections.computed("nombreArtista", "$artistaInfo.nombre"),
            Projections.computed("genero", "$albumInfo.genero"),
            Projections.computed("fechaLanzamiento", "$albumInfo.fechaLanzamiento"),
            Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")

        ))
    );
        List<Document> resultado = new ArrayList<>();
        for (Document doc : usuarios.aggregate(pipeline)) {
            resultado.add(doc);
        }
        return resultado;

               
    }
    
    @Override
    public List<Document> obtenerCancionesFavoritas(String idUsuario, String nombreCancion) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("_id", new ObjectId(idUsuario))),
        Aggregates.unwind("$favoritos"),
        Aggregates.match(Filters.and(
            Filters.eq("favoritos.tipo", TipoContenido.CANCION.name()),
            Filters.regex("favoritos.nombre", ".*" + nombreCancion + ".*", "i")
        )),
        Aggregates.lookup("canciones", "favoritos.idContenido", "_id", "cancionInfo"),
        Aggregates.unwind("$cancionInfo"),
        Aggregates.lookup("albumes", "cancionInfo.idAlbum", "_id", "albumInfo"),
        Aggregates.unwind("$albumInfo"),
        Aggregates.lookup("artistas", "cancionInfo.idArtista", "_id", "artistaInfo"),
        Aggregates.unwind("$artistaInfo"),
        Aggregates.project(Projections.fields(
            Projections.computed("idFavorito", "$favoritos._id"),
            Projections.computed("idCancion", "$favoritos.idContenido"),
            Projections.computed("titulo", "$cancionInfo.titulo"),
            Projections.computed("duracion", "$cancionInfo.duracion"),
            Projections.computed("nombreArtista", "$artistaInfo.nombre"),
            Projections.computed("nombreAlbum", "$albumInfo.nombre"),
            Projections.computed("genero", "$albumInfo.genero"),
            Projections.computed("fechaAgregacion", "$favoritos.fechaAgregacion")

        ))
        );
        List<Document> resultado = new ArrayList<>();
        for (Document doc : usuarios.aggregate(pipeline)) {
            resultado.add(doc);
        }
        return resultado;
        

    }
    
    @Override
    public List<Document> obtenerGenerosFavoritos(String idUsuario, String genero) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION); 

        Document usuario = usuarios.find(Filters.eq("_id", new ObjectId(idUsuario))).first();
         if (usuario != null) {
            List<Document> favoritos = usuario.getList("favoritos", Document.class, new ArrayList<>());

            return favoritos;
        }

        return new ArrayList<>();

    }
    
    @Override
    public List<Document> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        Document usuario = usuarios.find(Filters.eq("_id", new ObjectId(idUsuario))).first();

        if (usuario != null) {
            List<Document> favoritos = usuario.getList("favoritos", Document.class, new ArrayList<>());

            return favoritos;
        }

        return new ArrayList<>();
       
    }
    
    @Override
    public List<Document> obtenerTodosFavoritos(String idUsuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);

        
        Document usuario = usuarios.find(Filters.eq("_id", new ObjectId(idUsuario))).first();
        if (usuario != null) {
            List<Document> favoritos = usuario.getList("favoritos", Document.class, new ArrayList<>());

            return favoritos;
        }

        return new ArrayList<>();
       
    }


    @Override
    public List<Favorito> consultarFavoritos(String idUsuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection(COLECCION, Usuario.class);
        Usuario usuario = coleccion.find(Filters.eq("_id", new ObjectId(idUsuario))).first();

        if (usuario != null) {
            return usuario.getFavoritos();
        }
        return new ArrayList<>();
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

    @Override
    public void agregarGeneroRestringido(String idUsuario, String genero) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection(COLECCION);

        Document filtro = new Document("_id", new ObjectId(idUsuario));
        Document usuario = coleccion.find(filtro).first(); // Obtener el usuario

        if (usuario == null) {
            return; // No existe el usuario
        }
        
                // Verificar favoritos del usuario con ese género
        List<Document> favoritos = usuario.getList("favoritos", Document.class, new ArrayList<>());
        List<Document> aEliminar = new ArrayList<>();
        for (Document fav : favoritos) {
            if (genero.equals(fav.getString("genero"))) {
                aEliminar.add(fav);
            }
        }

        // se eliminan los favoritos con este genero 
        if (!aEliminar.isEmpty()) {
            coleccion.updateOne(filtro, new Document("$pull", new Document("favoritos", new Document("genero", genero))));
        }

        List<String> generos = usuario.getList("restricciones", String.class);
        if (generos == null) {
            generos = new ArrayList<>(); // Si no tiene aún lista de géneros
        }

        if (generos.contains(genero)) {
            return; // El género ya está restringido
        }

        // Aquí puedes buscar favoritos del género y eliminarlos si es necesario

        // Finalmente agregas el género restringido
        coleccion.updateOne(
            filtro,
            new Document("$addToSet", new Document("restricciones", genero))
        );
    }

    

    @Override
    public void eliminarGeneroRestringido(String idUsuario, String genero) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection(COLECCION, Usuario.class);
        
        Document filtro = new Document("_id", new ObjectId(idUsuario));
        Document update = new Document("$pull", new Document("restricciones", genero));

        coleccion.updateOne(filtro, update);
    }
    
    @Override
    public List<String> mostrarGenerosRestringidos(String idUsuario){
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection(COLECCION, Usuario.class);
        
        Document filtro = new Document("_id", new ObjectId(idUsuario));
        Usuario usuario = coleccion.find(filtro).first();

        if (usuario != null && usuario.getRestricciones()!= null) {
            return usuario.getRestricciones();
        } else {
            return new ArrayList<>();
        }
        
    }
    

}
