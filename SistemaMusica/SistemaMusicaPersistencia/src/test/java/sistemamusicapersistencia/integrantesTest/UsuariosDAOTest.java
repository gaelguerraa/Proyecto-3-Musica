/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sistemamusicapersistencia.integrantesTest;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import sistemamusica.dtos.UsuarioDTO;
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
    }

}
