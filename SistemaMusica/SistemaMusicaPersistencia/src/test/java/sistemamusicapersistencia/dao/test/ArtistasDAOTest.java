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
import sistemamusicadominio.Artista;
import sistemamusicadominio.Genero;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicadominio.TipoArtista;
import sistemamusicapersistencia.implementaciones.ArtistasDAO;
import sistemamusicapersistencia.implementaciones.ManejadorConexiones;

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

//    @Test
//    public void testRegistrarBanda() {
//        
//        List<Integrante> integrantes = new ArrayList<>();
//        
//        LocalDate fechaIngreso = LocalDate.of(2020, Month.MARCH, 15);
//        Date fechaIngresoDate = Date.from(fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant());
//         
//        Integrante i1 = new Integrante();
//        i1.setNombre("hassan laija");
//        i1.setRol(RolIntegrante.VOCALISTA);
//        i1.setFechaIngreso(fechaIngresoDate);
//        i1.setFechaIngreso(null);
//        i1.setActivo(true);
//        integranteGuardado = i1;
//        
//        Integrante i2 = new Integrante();
//        i2.setNombre("roberto laija");
//        i2.setRol(RolIntegrante.VOCALISTA);
//        i2.setFechaIngreso(fechaIngresoDate);
//        i2.setFechaIngreso(null);
//        i2.setActivo(true);
//        integranteGuardado2 = i2;
//        
//        integrantes.add(i1);
//        integrantes.add(i2);
//        
//        ArtistaDTO dto = new ArtistaDTO();
//        dto.setTipo(TipoArtista.BANDA);
//        dto.setNombre("Peso pluma");
//        dto.setImagen("pesopluma.jpg");
//        dto.setGenero(Genero.CORRIDOS);
//        dto.setIntegrantes(integrantes);
//        
//
//        Artista registrado = dao.registrarBanda(dto);
//        artistaGuardado = registrado;
//        assertNotNull(registrado);
//        assertEquals("Peso pluma", registrado.getNombre());
//        assertEquals(TipoArtista.BANDA, registrado.getTipo());
//        assertEquals(2, registrado.getIntegrantes().size());
//    }

    @Test
    public void testBuscarArtistasPorNombre() {
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Luis Miguel");
        dto.setImagen("sol.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarArtista(dto);
        artistaGuardado = registrado;
        List<Artista> resultados = dao.buscarArtistasPorNombre("Luis Miguel");
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testBuscarArtistasPorGenero() {
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Jose Jose");
        dto.setImagen("josejose.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarArtista(dto);
        artistaGuardado = registrado;
        List<Artista> resultados = dao.buscarArtistasPorGenero("MUSICAMEXICANA");
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testBuscarArtistasPorNombreGenero() {
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Vicente Fernandez");
        dto.setImagen("elcharro.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarArtista(dto);
        artistaGuardado = registrado;
        List<Artista> resultados = dao.buscarArtistasPorNombreGenero("Vicente Fernandez", "MUSICAMEXICANA");
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testBuscarArtistas() {
        
        ArtistasDAO dao = new ArtistasDAO();
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Raphael");
        dto.setImagen("raphael.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarArtista(dto);
        artistaGuardado = registrado;
        List<Artista> resultados = dao.buscarArtistas();
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
