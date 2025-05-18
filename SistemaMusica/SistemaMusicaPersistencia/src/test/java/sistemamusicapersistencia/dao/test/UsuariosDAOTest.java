/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sistemamusicapersistencia.dao.test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import sistemamusica.dtos.FavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Favorito;
import sistemamusicadominio.TipoContenido;
import sistemamusicadominio.Usuario;
import sistemamusicapersistencia.implementaciones.ManejadorConexiones;
import sistemamusicapersistencia.implementaciones.UsuariosDAO;

/**
 *
 * @author PC
 */
public class UsuariosDAOTest {

    public UsuariosDAOTest() {
    }

    private Usuario usuarioGuardado;
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    private final String COLECCION = "usuarios";
    private final String COLECCION_ARTISTAS = "artistas";
    private final String CAMPO_ID = "_id";

    @BeforeAll
    public static void activarModoPruebas() {
        ManejadorConexiones.activateTestMode();
    }

    @AfterEach
    public void limpiarBD() {
        if (usuarioGuardado != null) {
            MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
            MongoCollection<Usuario> coleccion = baseDatos.getCollection(
                    COLECCION, Usuario.class);

            coleccion.deleteOne(Filters.eq(CAMPO_ID, usuarioGuardado.getId()));
            usuarioGuardado = null;
        }
    }

    @Test
    public void testAgregarUsuarioSinImagenSinFavoritosSinRestriccionesOk() {
        final String CONTRASENIA_ENCRIPTADA = "bec5dd096ebf60d66caf925de00f7aa011c988a99928ae90b453070f6fcdbe1b";
        final String DIRECTORIO_IMAGEN_PLACEHOLDER = "userImages/userPlaceholder.jpg";

        UsuarioDTO nuevoUsuario = new UsuarioDTO(
                "BartoloXD",
                "bartoloSabritones@gmail.com",
                "123contrasenia123",
                null,
                null,
                null
        );

        Usuario usuarioAgregado = usuariosDAO.agregarUsuario(nuevoUsuario);
        usuarioGuardado = usuarioAgregado; // Comentar esta linea para ver en MongoDB

        assertEquals("BartoloXD", usuarioAgregado.getUsername());
        assertEquals("bartoloSabritones@gmail.com", usuarioAgregado.getEmail());
        assertEquals(CONTRASENIA_ENCRIPTADA, usuarioAgregado.getContrasenia());
        assertEquals(DIRECTORIO_IMAGEN_PLACEHOLDER, usuarioAgregado.getImagenPerfil());
        assertNull(usuarioAgregado.getFavoritos());
        assertNull(usuarioAgregado.getRestricciones());

    }

    @Test
    public void testConsultarUsuarioPorId() {
        final String CONTRASENIA_ENCRIPTADA = "bec5dd096ebf60d66caf925de00f7aa011c988a99928ae90b453070f6fcdbe1b";
        final String DIRECTORIO_IMAGEN_PLACEHOLDER = "userImages/userPlaceholder.jpg";

        UsuarioDTO nuevoUsuario = new UsuarioDTO(
                "BartoloXD",
                "bartoloSabritones@gmail.com",
                "123contrasenia123",
                null,
                null,
                null
        );

        Usuario usuarioAgregado = usuariosDAO.agregarUsuario(nuevoUsuario);
        usuarioGuardado = usuarioAgregado; // Comentar esta linea para ver en MongoDB

        System.out.println(usuarioAgregado.getId().toString());

        String ID_OBTENIDA = usuarioAgregado.getId().toString();

        Usuario usuarioObtenido = usuariosDAO.consultarPorId(ID_OBTENIDA);

        assertNotNull(usuarioObtenido);
        assertEquals(ID_OBTENIDA, usuarioObtenido.getId().toString());
        assertEquals(CONTRASENIA_ENCRIPTADA, usuarioObtenido.getContrasenia());
        assertEquals(DIRECTORIO_IMAGEN_PLACEHOLDER, usuarioObtenido.getImagenPerfil());
    }
    
    @Test
    public void testConsultarUsuarioPorUsername(){
        final String USERNAME_BUSCADO = "BartoloXD";
        
        UsuarioDTO nuevoUsuario = new UsuarioDTO(
                "BartoloXD",
                "bartoloSabritones@gmail.com",
                "123contrasenia123",
                null,
                null,
                null
        );
        
        Usuario usuarioAgregado = usuariosDAO.agregarUsuario(nuevoUsuario);
        usuarioGuardado = usuarioAgregado; // Comentar esta linea para ver en MongoDB
        
        System.out.println(usuarioAgregado.getUsername());
        
        Usuario usuarioObtenido = usuariosDAO.consultarPorUsername(USERNAME_BUSCADO);
        
        String USERNAME_OBTENIDO = usuarioObtenido.getUsername();
        
        assertNotNull(usuarioObtenido);
        assertEquals(USERNAME_BUSCADO, USERNAME_OBTENIDO);
    }
        

    @Test
    public void testModificarUsuario() {
        final String CONTRASENIA_ENCRIPTADA = "19cdf9613859fb11858c564cb02e00596b9bc1414c724fe9a4ca38ed79bd2254";
        final String DIRECTORIO_IMAGEN_PLACEHOLDER = "userImages/user123.jpg";

        UsuarioDTO nuevoUsuario = new UsuarioDTO(
                "BartoloXD",
                "bartoloSabritones@gmail.com",
                "123contrasenia123",
                null,
                null,
                null
        );

        Usuario usuarioAgregado = usuariosDAO.agregarUsuario(nuevoUsuario);
        usuarioGuardado = usuarioAgregado; // Comentar esta linea para ver en MongoDB

        System.out.println(usuarioAgregado.getId().toString());

        String ID_OBTENIDA = usuarioAgregado.getId().toString();

        UsuarioDTO nuevaInformacion = new UsuarioDTO(
                "BartoloXD240",
                "bartoloSabritones123@gmail.com",
                "holaSoyBartolo",
                "userImages/user123.jpg",
                null,
                null
        );

        Usuario usuarioObtenido = usuariosDAO.modificarUsuario(ID_OBTENIDA, nuevaInformacion);

        System.out.println("Nueva contrasenia encriptada: " + usuarioObtenido.getContrasenia());

        assertEquals("BartoloXD240", usuarioObtenido.getUsername());
        assertEquals("bartoloSabritones123@gmail.com", usuarioObtenido.getEmail());
        assertEquals(CONTRASENIA_ENCRIPTADA, usuarioObtenido.getContrasenia());
        assertEquals(DIRECTORIO_IMAGEN_PLACEHOLDER, usuarioObtenido.getImagenPerfil());
    }
    
//    @Test
//    public void testAgregarFavorito(){
//        
//    MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
//
//        // Crear usuario de prueba
//        Document usuario = new Document("_id", new ObjectId())
//                .append("username", "usuarioPrueba")
//                .append("favoritos", new java.util.ArrayList<Document>());
//        baseDatos.getCollection(COLECCION).insertOne(usuario);
//        String idUsuario = usuario.getObjectId("_id").toHexString();
//
//        // Crear artista de prueba
//        Document artista = new Document("_id", new ObjectId())
//                .append("nombre", "ArtistaTest")
//                .append("tipo", "Solista")
//                .append("genero", "Rock");
//        baseDatos.getCollection(COLECCION_ARTISTAS).insertOne(artista);
//        String idArtista = artista.getObjectId("_id").toHexString();
//
//        // Preparar favorito
//        FavoritoDTO favorito = new FavoritoDTO();
//        favorito.setIdElemento(favorito.getIdElemento());
//        favorito.setTipo(TipoContenido.ARTISTA);
//        favorito.setFechaAgregado(new Date());
//
//        // Agregar favorito - debe ser exitoso
//        boolean agregado = usuariosDAO.agregarFavorito(idUsuario, favorito);
//        assertTrue(agregado, "El favorito debe ser agregado correctamente");
//
//        // Intentar agregar el mismo favorito de nuevo - debe fallar (duplicado)
//        boolean agregadoDuplicado = usuariosDAO.agregarFavorito(idUsuario, favorito);
//        assertFalse(agregadoDuplicado, "No debe agregar favorito duplicado");
//
//        // Eliminar favorito - debe ser exitoso
//        boolean eliminado = usuariosDAO.eliminarFavorito(idUsuario, idArtista);
//        assertTrue(eliminado, "El favorito debe eliminarse correctamente");
//
//        // Intentar eliminar de nuevo - debe fallar (ya no existe)
//        boolean eliminadoDeNuevo = usuariosDAO.eliminarFavorito(idUsuario, idArtista);
//        assertFalse(eliminadoDeNuevo, "No debe eliminar favorito inexistente");
//
//        // Limpiar registros creados para no afectar otras pruebas
//        baseDatos.getCollection(COLECCION).deleteOne(Filters.eq("_id", new ObjectId(idUsuario)));
//        baseDatos.getCollection(COLECCION_ARTISTAS).deleteOne(Filters.eq("_id", new ObjectId(idArtista)));
//        }
//
}
