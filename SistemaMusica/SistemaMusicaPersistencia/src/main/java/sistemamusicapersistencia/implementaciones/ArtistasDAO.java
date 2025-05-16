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
    
    /**
     * Metodo que registra a un nuevo artista de tipo solista
     * Accede a la coleccion "artistas" y mapea en ella los atributos especificados en la clase POJO "Artista"
     * Crea un objeto artista, le da los datos del artistaDTO y persiste al nuevo artista solista
     * @param nuevoSolista
     * @return 
     */
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

    /**
     * Metodo que registra a un nuevo artista de tipo banda
     * Accede a la coleccion "artistas" y mapea en ella los atributos especificados en la clase POJO "Artista"
     * Crea un objeto artista, le da los datos del artistaDTO y persiste al nuevo artista banda
     * 
     * @param nuevaBanda
     * @return 
     */
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

    /**
     * Metodo que busca artistas por su nombre
     * Accede a la coleccion "artistas", crea un nuevo documento para filtrar los resultados con base al campo nombre del artista,
     * Se buscaran los artistas que su nombre coincida con el nombre introducido por el usuario, siendo insensible a mayusculas o minusculas
     * Se guardaran los artistas que coincidieron con el nombre en una lista y se regresara la lista. 
     * 
     * @param nombre
     * @return lista de artisas
     */
    @Override
    public List<Artista> buscarArtistasPorNombre(String nombre) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        Document filtros = new Document();
        filtros.append(CAMPO_NOMBRE, new Document("$regex", nombre).append("$options", "i"));
        
        FindIterable<Artista> resultados = coleccion.find(filtros);
        List<Artista> listaArtistas = new LinkedList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    /**
     * Metodo para buscar artistas con un genero
     * Accede a la coleccion "artistas", crea un nuevo documento para filtrar los resultados con base al genero del artista
     * Se buscaran los artistas que su genero coincida con el genero introducido y se guardaran en una lista y se regresara.
     * @param genero
     * @return lista de artistas
     */
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

    /**
     * Metodo que busca artistas por su nombre y por su genero
     * Accede a la coleccion "artistas", crea un nuevo documento para filtrar los resultados con base al genero y al nombre del artista
     * Se buscaran los artistas que tengan el mismo genero que el introducido y el mismo nombre que el introducido siendo insensible a mayusculas o minusculas
     * Se guardaran los resultados en una lista y se regresara.
     * @param nombre
     * @param genero
     * @return 
     */
    @Override
    public List<Artista> buscarArtistasPorNombreGenero(String nombre, String genero) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        Document filtros = new Document();
        filtros.append(CAMPO_NOMBRE, new Document("$regex", nombre).append("$options", "i"));
        filtros.append(CAMPO_GENERO, genero);
        
        FindIterable<Artista> resultados = coleccion.find(filtros);
        List<Artista> listaArtistas = new ArrayList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    /**
     * Metodo que busca todos los artistas
     * Accede a la coleccion atristas y regresa todos los artistas
     * @return lista de artistas
     */
    @Override
    public List<Artista> buscarArtistas() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        FindIterable<Artista> resultados = coleccion.find();
        List<Artista> listaArtistas = new ArrayList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    /**
     * Metodo que busca un artista por su nombre
     * Accede a la coleccion artistas, crea un filtro para filtrar por nombre, busca si hay artistas con el mismo nombre
     * si hay un artista con el mismo nombre se devuelve al artista, de lo contrario no devuelve nada  
     * @param nombre
     * @return 
     */
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
