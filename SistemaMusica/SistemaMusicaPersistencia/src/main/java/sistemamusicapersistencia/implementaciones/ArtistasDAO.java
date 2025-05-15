/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.TipoArtista;
import sistemamusicapersistencia.interfaces.IArtistasDAO;

/**
 *
 * @author gael_
 */
public class ArtistasDAO implements IArtistasDAO {

    private final String COLECCION = "artistas";
    private final String CAMPO_ID = "_id";
    private final String CAMPO_NOMBRE = "nombre";
    private final String CAMPO_GENERO = "genero";
    private final String CAMPO_TIPO = "tipo";
    
    @Override
    public Artista registrarSolista(ArtistaDTO nuevoSolista) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(
                COLECCION, Artista.class);
        
        Artista solista = new Artista();
        solista.setNombre(nuevoSolista.getNombre());
        solista.setGenero(nuevoSolista.getGenero());
        solista.setTipo(TipoArtista.SOLISTA);
        solista.setImagen(nuevoSolista.getImagen());
        
        coleccion.insertOne(solista);
        return solista;
    }

    @Override
    public Artista registrarBanda(ArtistaDTO nuevaBanda) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(
                COLECCION, Artista.class);
        
        Artista banda = new Artista();
        banda.setNombre(nuevaBanda.getNombre());
        banda.setGenero(nuevaBanda.getGenero());
        banda.setTipo(TipoArtista.BANDA);
        banda.setImagen(nuevaBanda.getImagen());
        banda.setIntegrantes(nuevaBanda.getIntegrantes());
        
        
        coleccion.insertOne(banda);
        return banda;
    }

    @Override
    public List<Artista> buscarArtistasPorNombre(String nombre) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        Document filtros = new Document();
        filtros.append(CAMPO_NOMBRE, nombre);
        
        FindIterable<Artista> resultados = coleccion.find();
        List<Artista> listaArtistas = new LinkedList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    @Override
    public List<Artista> buscarArtistasPorGenero(String genero) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        Document filtros = new Document();
        filtros.append(CAMPO_GENERO, genero);
        
        FindIterable<Artista> resultados = coleccion.find(filtros);
        List<Artista> listaArtistas = new ArrayList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    @Override
    public List<Artista> buscarArtistasPorNombreGenero(String nombre, String genero) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        Document filtros = new Document();
        filtros.append(CAMPO_NOMBRE, nombre);
        filtros.append(CAMPO_GENERO, genero);
        
        FindIterable<Artista> resultados = coleccion.find(filtros);
        List<Artista> listaArtistas = new ArrayList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    @Override
    public List<Artista> buscarArtistas() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        FindIterable<Artista> resultados = coleccion.find();
        List<Artista> listaArtistas = new ArrayList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    @Override
    public Artista buscarArtistaPorNombre(String nombre) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        Document filtro = new Document();
        filtro.append(CAMPO_NOMBRE, nombre);
        
        FindIterable<Artista> resultados = coleccion.find(filtro);
        
        Artista artista = resultados.first();
        
        if(artista != null){
            return artista;
        }else{
            return null;
        }
    }
    
}
