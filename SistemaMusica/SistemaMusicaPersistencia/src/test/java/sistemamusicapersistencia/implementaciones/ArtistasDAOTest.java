/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Genero;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicadominio.TipoArtista;

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
    
    public ArtistasDAOTest() {
    }

    @Test
    public void testRegistrarSolista() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        ArtistasDAO dao = new ArtistasDAO();
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Solista Test");
        dto.setImagen("solista.jpg");
        dto.setGenero(Genero.ROCK);


        Artista registrado = dao.registrarSolista(dto);
        assertNotNull(registrado);
        assertEquals("Solista Test", registrado.getNombre());
        assertEquals(TipoArtista.SOLISTA, registrado.getTipo());
    }

    @Test
    public void testRegistrarBanda() {
         MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        ArtistasDAO dao = new ArtistasDAO();
        
        List<Integrante> integrantes = new ArrayList<>();
        
        LocalDate fechaIngreso = LocalDate.of(2020, Month.MARCH, 15);
        Date fechaIngresoDate = Date.from(fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant());
         
        Integrante i1 = new Integrante();
        i1.setNombre("hassan laija");
        i1.setRol(RolIntegrante.VOCALISTA);
        i1.setFechaIngreso(fechaIngresoDate);
        i1.setFechaIngreso(null);
        i1.setActivo(true);
        
        Integrante i2 = new Integrante();
        i2.setNombre("roberto laija");
        i2.setRol(RolIntegrante.VOCALISTA);
        i2.setFechaIngreso(fechaIngresoDate);
        i2.setFechaIngreso(null);
        i2.setActivo(true);
        
        integrantes.add(i1);
        integrantes.add(i2);
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.BANDA);
        dto.setNombre("Peso pluma");
        dto.setImagen("pesopluma.jpg");
        dto.setGenero(Genero.CORRIDOS);
        dto.setIntegrantes(integrantes);
        

        Artista registrado = dao.registrarBanda(dto);
        assertNotNull(registrado);
        assertEquals("Peso pluma", registrado.getNombre());
        assertEquals(TipoArtista.BANDA, registrado.getTipo());
        assertEquals(2, registrado.getIntegrantes().size());
    }

    @Test
    public void testBuscarArtistasPorNombre() {
         MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        ArtistasDAO dao = new ArtistasDAO();
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Luis Miguel");
        dto.setImagen("sol.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarSolista(dto);
        List<Artista> resultados = dao.buscarArtistasPorNombre("Luis Miguel");
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testBuscarArtistasPorGenero() {
         MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        ArtistasDAO dao = new ArtistasDAO();
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Luis Miguel");
        dto.setImagen("sol.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarSolista(dto);
        List<Artista> resultados = dao.buscarArtistasPorGenero("MUSICAMEXICANA");
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testBuscarArtistasPorNombreGenero() {
         MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        ArtistasDAO dao = new ArtistasDAO();
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Luis Miguel");
        dto.setImagen("sol.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarSolista(dto);
        List<Artista> resultados = dao.buscarArtistasPorNombreGenero("Luis Miguel", "MUSICAMEXICANA");
        assertFalse(resultados.isEmpty());
    }

    @Test
    public void testBuscarArtistas() {
         MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        ArtistasDAO dao = new ArtistasDAO();
        
        ArtistaDTO dto = new ArtistaDTO();
        dto.setTipo(TipoArtista.SOLISTA);
        dto.setNombre("Luis Miguel");
        dto.setImagen("sol.jpg");
        dto.setGenero(Genero.MUSICAMEXICANA);


        Artista registrado = dao.registrarSolista(dto);
        List<Artista> resultados = dao.buscarArtistas();
        assertFalse(resultados.isEmpty());
    }
    
}
