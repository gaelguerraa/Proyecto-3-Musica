/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bson.Document;
import org.bson.types.ObjectId;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusica.exception.PersistenciaException;
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
    
    public boolean agregarFavorito(String idUsuario, Favorito favorito){
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> usuarios = db.getCollection(COLECCION);
        
        Document nuevoFavorito = new Document()
                //.append("idContenido", new ObjectId(favorito.getIdContenido()))
                .append("tipo", favorito.getTipo())
                .append("fechaAgregacion", favorito.getFechaAgregacion());
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
