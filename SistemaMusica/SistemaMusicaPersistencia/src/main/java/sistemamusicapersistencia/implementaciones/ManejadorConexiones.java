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
    private static final String BASE_DATOS = "bibliotecaMusicalPruebas";
    
    public static MongoDatabase obtenerBaseDatos(){
         //configuracion mapeador automatico de mongo
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        
        //asignar la confirmacion del mapeador con la conexion para que nuestras clases POJO sean reconocidas en automatico
        MongoClientSettings configuraciones = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();
        
        //conexion a mongodb mongodb://localhost:27017
        MongoClient cliente = MongoClients.create(configuraciones);
        
        //conexin a bd
        MongoDatabase baseDatos = cliente.getDatabase(BASE_DATOS);
        return baseDatos;
     }
}
