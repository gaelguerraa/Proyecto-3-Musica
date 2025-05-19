/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sistemamusicapersistencia.dao.test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Genero;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicadominio.TipoArtista;
import sistemamusicadominio.Usuario;
import sistemamusicapersistencia.implementaciones.ArtistasDAO;
import sistemamusicapersistencia.implementaciones.ManejadorConexiones;
import sistemamusicapersistencia.implementaciones.UsuariosDAO;

/**
 *
 * @author gael_
 */
public class ArtistasDAOTest {
    
    private final String COLECCION = "artistas";
    private final String CAMPO_ID = "_id";
    private final String CAMPO_NOMBRE = "nombre";
    private final String CAMPO_GENERO = "genero";
    private final String CAMPO_TIPO = "tipo";
    private Artista artistaGuardado;
    private Artista artistaGuardado2;
    private Integrante integranteGuardado;
    private Integrante integranteGuardado2;
    private final ArtistasDAO dao = new ArtistasDAO();
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
            
    public ArtistasDAOTest() {
    }
    
    @BeforeAll
    public static void activarModoPruebas() {
        ManejadorConexiones.activateTestMode();
    }

    @AfterEach
    public void limpiarBD() {
        if (artistaGuardado != null) {
            MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
            MongoCollection<Artista> coleccion = baseDatos.getCollection(
                    COLECCION, Artista.class);

            coleccion.deleteOne(Filters.eq(CAMPO_ID, artistaGuardado.getId()));
            artistaGuardado = null;

            if (artistaGuardado2 != null) {
                coleccion.deleteOne(Filters.eq(CAMPO_ID, artistaGuardado2.getId()));
                artistaGuardado2 = null;
            }
        }
        
        if (integranteGuardado != null) {
            MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
            MongoCollection<Integrante> coleccion = baseDatos.getCollection(
                    COLECCION, Integrante.class);

            coleccion.deleteOne(Filters.eq(CAMPO_ID, integranteGuardado.getId()));
            integranteGuardado = null;

            if (integranteGuardado2 != null) {
                coleccion.deleteOne(Filters.eq(CAMPO_ID, integranteGuardado2.getId()));
                integranteGuardado2 = null;
            }
        }
    }

    @Test
    public void testRegistrarSolista() {
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Solista Test");
        dto.setImagen("solista.jpg");
        dto.setGenero(Genero.ROCK);


        Artista registrado = dao.registrarArtista(dto);
        artistaGuardado = registrado;
        assertNotNull(registrado);
        assertEquals("Solista Test", registrado.getNombre());
        assertEquals(TipoArtista.SOLISTA, registrado.getTipo());
    }



    @Test
    public void testBuscarArtistasPorNombre() {

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsername("usuarioRaphael");
        usuario.setContrasenia("1234");
        Usuario user = usuariosDAO.agregarUsuario(usuario);
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Luis Miguel");
        dto.setImagen("sol.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarArtista(dto);
        artistaGuardado = registrado;
        List<Artista> resultados = dao.buscarArtistasPorNombre(user.getId().toString(), "Luis Miguel");
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testBuscarArtistasPorGenero() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsername("usuarioJorge");
        usuario.setContrasenia("12345");
        Usuario user = usuariosDAO.agregarUsuario(usuario);
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Jose Jose");
        dto.setImagen("josejose.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarArtista(dto);
        artistaGuardado = registrado;
        List<Artista> resultados = dao.buscarArtistasPorGenero(user.getId().toString(), "MUSICAMEXICANA");
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testBuscarArtistasPorNombreGenero() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsername("usuarioFelipe");
        usuario.setContrasenia("123456");
        Usuario user = usuariosDAO.agregarUsuario(usuario);
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Vicente Fernandez");
        dto.setImagen("elcharro.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarArtista(dto);
        artistaGuardado = registrado;
        List<Artista> resultados = dao.buscarArtistasPorNombreGenero(user.getId().toString(), "Vicente Fernandez", "MUSICAMEXICANA");
        assertFalse(resultados.isEmpty());
    }
    
    @Test
    public void testBuscarArtistaPorNombre(){
        ArtistasDAO dao = new ArtistasDAO();
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Christian Nodal");
        dto.setImagen("nodal.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);
        
        Artista registrado = dao.registrarArtista(dto);
        artistaGuardado = registrado;
        
        Artista artista = dao.buscarArtistaPorNombre("Christian Nodal");
        assertNotNull(artista);
    }
    
        @Test
    public void consultarTodosLosIntegrantes(){
  
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection("artistas");

        Document integrante1 = new Document("nombre", "Ana")
                .append("rol", "VOCALISTA")
                .append("activo", true)
                .append("fechaIngreso", new Date());

        Document integrante2 = new Document("nombre", "Luis")
                .append("rol", "ARREGLISTA")
                .append("activo", false)
                .append("fechaIngreso", new Date())
                .append("fechaSalida", new Date());

        Document artista = new Document("nombre", "Banda de Prueba")
                .append("genero", "ROCK")
                .append("integrantes", Arrays.asList(integrante1, integrante2));

        coleccion.insertOne(artista);

        String idArtista = artista.getObjectId("_id").toHexString();

        List<Integrante> integrantes = dao.consultarTodosLosIntegrantes(idArtista);

        assertEquals(2, integrantes.size());
        assertTrue(integrantes.stream().anyMatch(i -> i.getNombre().equals("Ana")));
        assertTrue(integrantes.stream().anyMatch(i -> i.getNombre().equals("Luis")));

        // Limpieza
        coleccion.deleteOne(Filters.eq("_id", artista.getObjectId("_id")));
    }

    @Test
    public void consultarIntegrantesActivos(){
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection("artistas");

        Document integrante1 = new Document("nombre", "Ana")
                .append("rol", "VOCALISTA")
                .append("activo", true)
                .append("fechaIngreso", new Date());

        Document integrante2 = new Document("nombre", "Luis")
                .append("rol", "ARREGLISTA")
                .append("activo", false)
                .append("fechaIngreso", new Date())
                .append("fechaSalida", new Date());

        Document artista = new Document("nombre", "Banda de Prueba")
                .append("genero", "ROCK")
                .append("integrantes", Arrays.asList(integrante1, integrante2));

        coleccion.insertOne(artista);

        String idArtista = artista.getObjectId("_id").toHexString();

        List<Integrante> integrantes = dao.consultarIntegrantesActivos(idArtista);

        assertEquals(1, integrantes.size());
        assertEquals("Ana", integrantes.get(0).getNombre());
        assertTrue(integrantes.get(0).isActivo());

        // Limpieza
        coleccion.deleteOne(Filters.eq("_id", artista.getObjectId("_id")));
    }
    
}
