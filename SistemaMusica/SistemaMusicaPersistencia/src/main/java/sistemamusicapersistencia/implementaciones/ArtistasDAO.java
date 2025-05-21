/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapersistencia.implementaciones;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import sistemamusica.dtos.ArtistaDTO;
import sistemamusica.dtos.IntegranteDTO;
import sistemamusicadominio.Artista;
import sistemamusicadominio.Integrante;
import sistemamusicadominio.RolIntegrante;
import sistemamusicadominio.Usuario;
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
     * Metodo que registra a un nuevo artista 
     * Accede a la coleccion "artistas" y mapea en ella los atributos especificados en la clase POJO "Artista"
     * Crea un objeto artista, le da los datos del artistaDTO y persiste al nuevo artista banda
     * 
     * @param nuevoArtista
     * @return 
     */
    @Override
    public Artista registrarArtista(ArtistaDTO nuevoArtista) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(
                COLECCION, Artista.class);
        
        Artista artista = new Artista();
        artista.setNombre(nuevoArtista.getNombre());
        artista.setGenero(nuevoArtista.getGenero());
        artista.setTipo(nuevoArtista.getTipo());
        artista.setImagen(nuevoArtista.getImagen());
       
        
        coleccion.insertOne(artista);
        return artista;
    }

    /**
     * Metodo que busca artistas por su nombre
     * Accede a la coleccion "artistas", crea un nuevo documento para filtrar los resultados con base al campo nombre del artista,
     * Se buscaran los artistas que su nombre coincida con el nombre introducido por el usuario, siendo insensible a mayusculas o minusculas
     * Se guardaran los artistas que coincidieron con el nombre en una lista y se regresara la lista. 
     * 
     * @param idUsuario
     * @param nombre
     * @return lista de artisas
     */
    @Override
    public List<Artista> buscarArtistasPorNombre(String idUsuario, String nombre) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
        
         Document filtros = new Document(CAMPO_NOMBRE, new Document("$regex", nombre).append("$options", "i"));
        if (!restricciones.isEmpty()) {
            filtros.append(CAMPO_GENERO, new Document("$nin", restricciones));
        }
        
        FindIterable<Artista> resultados = coleccion.find(filtros);
        List<Artista> listaArtistas = new LinkedList<>();
        resultados.into(listaArtistas);
        return listaArtistas;
    }

    /**
     * Metodo para buscar artistas con un genero
     * Accede a la coleccion "artistas", crea un nuevo documento para filtrar los resultados con base al genero del artista
     * Se buscaran los artistas que su genero coincida con el genero introducido y se guardaran en una lista y se regresara.
     * @param idUsuario
     * @param genero
     * @return lista de artistas
     */
    @Override
    public List<Artista> buscarArtistasPorGenero(String idUsuario, String genero) {
        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
        if (restricciones.contains(genero)) {
            return new ArrayList<>(); // No mostrar si está restringido
        }
        
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
     * @param idUsuario
     * @param nombre
     * @param genero
     * @return 
     */
    @Override
    public List<Artista> buscarArtistasPorNombreGenero(String idUsuario, String nombre, String genero) {
        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
        if (restricciones.contains(genero)) {
            return new ArrayList<>();
        }

        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = db.getCollection(COLECCION, Artista.class);

        Document filtro = new Document()
            .append(CAMPO_NOMBRE, new Document("$regex", nombre).append("$options", "i"))
            .append(CAMPO_GENERO, genero);

        FindIterable<Artista> resultados = coleccion.find(filtro);
        List<Artista> lista = new ArrayList<>();
        resultados.into(lista);
        return lista;
    }

    /**
     * Metodo que busca todos los artistas
     * Accede a la coleccion atristas y regresa todos los artistas
     * @param idUsuario
     * @return lista de artistas
     */
    @Override
    public List<Artista> buscarArtistas(String idUsuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Artista> coleccion = baseDatos.getCollection(COLECCION, Artista.class);
        
        List<String> restricciones = obtenerGenerosRestringidos(idUsuario);
    
        Document filtro = new Document();
        if (!restricciones.isEmpty()) {
            filtro.append(CAMPO_GENERO, new Document("$nin", restricciones));
        }
        
        FindIterable<Artista> resultados = coleccion.find(filtro);
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
    
    /**
     * Crea un nuevo documento integrante dentro del documento artista
     * Crea el documento integrante con los atributos savados del nuevoIntegranteDTO y los inserta en el documento artista
     * que coincida con el idArtista y los añade al espacio "integrantes"
     * 
     * @param idArtista
     * @param nuevoIntegrante
     * @return boolean
     */
    @Override
    public boolean agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        // se crea el documento del integrante
        Document integranteDoc = new Document("nombre", nuevoIntegrante.getNombre())
                .append("rol", nuevoIntegrante.getRol().toString())
                .append("fechaIngreso", nuevoIntegrante.getFechaIngreso())
                .append("activo", nuevoIntegrante.isActivo());

        if (nuevoIntegrante.getFechaSalida() != null) {
            integranteDoc.append("fechaSalida", nuevoIntegrante.getFechaSalida());
        }

        // convierte el string idArtista en un objectid
        // y agrega el integrante en el documento con ese mismo id
        ObjectId id = new ObjectId(idArtista);
        UpdateResult result = coleccion.updateOne(
            Filters.eq(CAMPO_ID, id), //filtro
            Updates.push("integrantes", integranteDoc)); //accion



        return result.getModifiedCount() >0;
    }

    
    
    /**
     * Metodo que recupera todos los integrantes de un artista con el id del artista
     * 1. Accede a la coleccion "artistas", creando un filtro para buscar el documento que coincida con el id proporcionado
     * 2. Selecciona el campo "integrantes" y excluye el campo "_id" del documento,
     * 3. Ejecuta la consulta con el filtro y la proyeccion y agarra el primer resultado (solo hay uno),
     * 4. Verifica que existe el resultado y que es de tipo "integrantes"
     * 5. Extrae los documentos que contienen los integrantes
     * 6. Agrega los resultados a la lista y la devuelve
     * @param idArtista
     * @return lista de integrantes
     */
    @Override
    public List<Document> consultarTodosLosIntegrantes(String idArtista) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        Document filtro = new Document(CAMPO_ID, new ObjectId(idArtista));
        Document proyeccion = new Document("integrantes", 1).append("_id", 0);

        Document resultado = coleccion.find(filtro).projection(proyeccion).first();

        if (resultado != null && resultado.containsKey("integrantes")) {
            return (List<Document>) resultado.get("integrantes");
        }

        return new ArrayList<>();
    }

    /**
     * Metodo que recupera todos los integrantes que estan activos de un artista con el id del artista
     * 1. Accede a la coleccion "artistas", creando un filtro para buscar el documento que coincida con el id proporcionado
     * 2. Selecciona el campo "integrantes" y excluye el campo "_id" del documento,
     * 3. Ejecuta la consulta con el filtro y la proyeccion y agarra el primer resultado (solo hay uno),
     * 4. Verifica que existe el resultado y que es de tipo "integrantes"
     * 5. Extrae los documentos que contienen los integrantes y verifica que su estado sea activo, si no tiene estado lo tomara como no activo(false)  
     * 6. Agrega los resultados a la lista y la devuelve
     * @param idArtista
     * @return lista de integrantes
     */
    @Override
    public List<Document> consultarIntegrantesActivos(String idArtista) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        Document filtro = new Document(CAMPO_ID, new ObjectId(idArtista));
        Document proyeccion = new Document("integrantes", 1).append("_id", 0);

        Document resultado = coleccion.find(filtro).projection(proyeccion).first();

        List<Document> lista = new ArrayList<>();

        if (resultado != null && resultado.containsKey("integrantes")) {
            List<Document> docs = (List<Document>) resultado.get("integrantes");

            for (Document doc : docs) {
                if (doc.getBoolean("activo", false)) {
                    lista.add(doc);
                }
            }
        }

        return lista;
    }

    /**
     * Metodo que obtiene los generos restringidos por el usuario
     * busca el documento con el id del usuario y devuelve sus restricciones si es que tiene
     * @param idUsuario
     * @return lista de generos restringidos
     */
    private List<String> obtenerGenerosRestringidos(String idUsuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection("usuarios", Usuario.class);

        //si el usuario existe y tiene generos en restricciones devolver las restricciones y agregarlas en una lista
        Usuario usuario = coleccion.find(new Document("_id", new ObjectId(idUsuario))).first();
        return (usuario != null && usuario.getRestricciones() != null)
                ? usuario.getRestricciones()
                : new ArrayList<>();
    }


    
}
