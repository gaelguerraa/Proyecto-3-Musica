/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author gael_
 */
public class ManejadorConexiones {

    private static boolean isTestMode = false;
    private static final String BASE_DATOS_PRUEBA = "bibliotecaMusicalPruebas";
    private static final String BASE_DATOS = "bibliotecaMusical";

    /**
     * Regresa un MongoDatabase dependiendo si el modo de pruebas esta activado
     * o no
     *
     * @return MongoDatabase a utilizar
     */
    public static MongoDatabase obtenerBaseDatos() {
        if (isTestMode) {
            return crearMongoDatabase(BASE_DATOS_PRUEBA);
        } else {
            return crearMongoDatabase(BASE_DATOS);
        }
    }

    /**
     * Crea el MongoDatabase a partir de un string con el nombre de la base de
     * datos
     *
     * @param nombreBaseDatos Nombre de la base de datos a utilizar
     * @return Base de datos a utilizar
     */
    public static MongoDatabase crearMongoDatabase(String nombreBaseDatos) {
        //configuracion mapeador automatico de mongo
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        //asignar la confirmacion del mapeador con la conexion para que nuestras clases POJO sean reconocidas en automatico
        MongoClientSettings configuraciones = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();

        //conexion a mongodb mongodb://localhost:27017
        MongoClient cliente = MongoClients.create(configuraciones);

        //conexin a bd
        MongoDatabase baseDatos = cliente.getDatabase(nombreBaseDatos);
        return baseDatos;
    }

    /**
     * Activa el modo de pruebas
     */
    public static void activateTestMode() {
        isTestMode = true;
    }

    /**
     * Desactiva el modo de pruebas
     */
    public static void deactivateTestMode() {
        isTestMode = false;
    }

}
