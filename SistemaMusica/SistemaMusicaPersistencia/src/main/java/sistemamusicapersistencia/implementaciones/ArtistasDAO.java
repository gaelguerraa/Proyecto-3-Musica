/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
    private final String CAMPO_IMAGEN = "imagen";
    
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
    
}
