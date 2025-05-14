/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Integrante;
import sistemamusicapersistencia.interfaces.IIntegrantesDAO;

/**
 * Clase Data Access Object para los Integrantes
 *
 * @author PC
 */
public class IntegrantesDAO implements IIntegrantesDAO {

    public final String COLECCION = "integrantes";
    public final String CAMPO_ID = "_id";
    public final String CAMPO_NOMBRE = "nombre";
    public final String CAMPO_ROL = "rol";
    public final String CAMPO_FECHA_INGRESO = "fechaIngreso";
    public final String CAMPO_FECHA_SALIDA = "fechaSalida";
    public final String CAMPO_ACTIVO = "activo";

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

}
