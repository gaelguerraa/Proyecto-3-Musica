/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusica.exception.PersistenciaException;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicapersistencia.interfaces.IIntegrantesDAO;

/**
 *
 * @author PC
 */
public class IntegrantesDAO implements IIntegrantesDAO {

    public final String COLECCION = "integrantes";
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

}
