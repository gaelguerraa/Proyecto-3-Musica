/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicapersistencia.interfaces.IIntegrantesDAO;

/**
 * Clase Data Access Object para los Integrantes
 *
 * @author PC
 */
public class IntegrantesDAO implements IIntegrantesDAO {

    private final String COLECCION = "integrantes";
    private final String COLECCION_ARTISTAS = "artistas";
    private final String CAMPO_ID = "_id";

    /**
     * Metodo que agrega un integrante a la base de datos
     *
     * @param nuevoIntegrante Nuevo integrante a agregar a la base de datos
     * @return Integrante agregado en la base de datos
     */
    @Override
    public Integrante agregarIntegrante(IntegranteDTO nuevoIntegrante) {

        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Integrante> coleccion = baseDatos.getCollection(
                COLECCION, Integrante.class);

        Integrante integrante = new Integrante();
        integrante.setNombre(nuevoIntegrante.getNombre());
        integrante.setRol(nuevoIntegrante.getRol());
        integrante.setFechaIngreso(nuevoIntegrante.getFechaIngreso());
        if (nuevoIntegrante.getFechaSalida() != null) {
            integrante.setFechaSalida(nuevoIntegrante.getFechaSalida());
        }
        integrante.setActivo(nuevoIntegrante.isActivo());

        coleccion.insertOne(integrante);
        return integrante;

    }

    /**
     * Metodo para consultar todos los integrantes
     *
     * @return Lista con todos los integrantes de la base de datos
     */
    @Override
    public List<Integrante> consultarTodos() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Integrante> coleccion = baseDatos.getCollection(
                COLECCION, Integrante.class);

        FindIterable<Integrante> resultados = coleccion.find();
        List<Integrante> listaResultados = new LinkedList<>();
        resultados.into(listaResultados);
        return listaResultados;
    }

    /**
     * Metodo para consultar un integrante segun su ID
     *
     * @param idIntegrante ID del integrante a buscar
     * @return Integrante con el id solicitada
     */
    @Override
    public Integrante consultarPorId(String idIntegrante) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Integrante> coleccion = baseDatos.getCollection(
                COLECCION, Integrante.class);

        Document filtros = new Document();
        filtros.append(CAMPO_ID, new ObjectId(idIntegrante));
        FindIterable<Integrante> integrantes = coleccion.find(filtros);
        Integrante integrante = integrantes.first();

        return integrante;
    }

    @Override
    public List<Integrante> consultarTodosLosIntegrantes(String idArtista) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection("artistas");

        Document filtro = new Document("_id", new ObjectId(idArtista));
        Document proyeccion = new Document("integrantes", 1).append("_id", 0);

        Document resultado = coleccion.find(filtro).projection(proyeccion).first();

        List<Integrante> lista = new ArrayList<>();

        if (resultado != null && resultado.containsKey("integrantes")) {
            List<Document> docs = (List<Document>) resultado.get("integrantes");

            for (Document doc : docs) {
                Integrante integrante = convertirDocumentoAIntegrante(doc);
                lista.add(integrante);
            }
        }

        return lista;
    }

    @Override
    public List<Integrante> consultarIntegrantesActivos(String idArtista) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection("artistas");

        Document filtro = new Document("_id", new ObjectId(idArtista));
        Document proyeccion = new Document("integrantes", 1).append("_id", 0);

        Document resultado = coleccion.find(filtro).projection(proyeccion).first();

        List<Integrante> lista = new ArrayList<>();

        if (resultado != null && resultado.containsKey("integrantes")) {
            List<Document> docs = (List<Document>) resultado.get("integrantes");

            for (Document doc : docs) {
                if (doc.getBoolean("activo", false)) {
                    Integrante integrante = convertirDocumentoAIntegrante(doc);
                    lista.add(integrante);
                }
            }
        }

        return lista;
    }

    
    public Integrante convertirDocumentoAIntegrante(Document doc) {
        Integrante integrante = new Integrante();
        
        integrante.setNombre(doc.getString("nombre"));
        integrante.setRol(RolIntegrante.valueOf(doc.getString("rol")));
        integrante.setActivo(doc.getBoolean("activo", false));
        integrante.setFechaIngreso(doc.getDate("fechaIngreso"));
        integrante.setFechaSalida(doc.getDate("fechaSalida")); // puede ser null
        
        return integrante;
    }

}
