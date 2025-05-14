/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bson.Document;
import org.bson.types.ObjectId;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Integrante;
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
