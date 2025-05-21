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
    
    @Override
    public Integrante agregarIntegrante(String idArtista, IntegranteDTO nuevoIntegrante) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection("artistas");

        // se crea el documento del integrante
        Document integranteDoc = new Document("nombre", nuevoIntegrante.getNombre())
                .append("rol", nuevoIntegrante.getRol().toString())
                .append("fechaIngreso", nuevoIntegrante.getFechaIngreso())
                .append("activo", nuevoIntegrante.isActivo());

        if (nuevoIntegrante.getFechaSalida() != null) {
            integranteDoc.append("fechaSalida", nuevoIntegrante.getFechaSalida());
        }

        // se realiza el push dentro del documento del artista
        ObjectId id = new ObjectId(idArtista);
        coleccion.updateOne(
                Filters.eq("_id", id), 
                Updates.push("integrantes", integranteDoc));

        //se crea una instancia de integrante para usarlo al buscar integrantes
        Integrante integrante = new Integrante();
        integrante.setNombre(nuevoIntegrante.getNombre());
        integrante.setRol(nuevoIntegrante.getRol());
        integrante.setFechaIngreso(nuevoIntegrante.getFechaIngreso());
        integrante.setFechaSalida(nuevoIntegrante.getFechaSalida());
        integrante.setActivo(nuevoIntegrante.isActivo());

        return integrante;
    }

    
    
    /**
     * Metodo que recupera todos los integrantes de un artista con el id del artista
     * 1. Accede a la coleccion "artistas", creando un filtro para buscar el documento que coincida con el id proporcionado
     * 2. Selecciona el campo "integrantes" y excluye el campo "_id" del documento,
     * 3. Ejecuta la consulta con el filtro y la proyeccion y agarra el primer resultado (solo hay uno),
     * 4. Verifica que existe el resultado y que es de tipo "integrantes"
     * 5. Extrae los documentos que contienen los integrantes y por cada documento integrante lo convierte a a un objeto Integrante
     * 6. Agrega los resultados a la lista y la devuelve
     * @param idArtista
     * @return lista de integrantes
     */
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

    /**
     * Metodo que recupera todos los integrantes que estan activos de un artista con el id del artista
     * 1. Accede a la coleccion "artistas", creando un filtro para buscar el documento que coincida con el id proporcionado
     * 2. Selecciona el campo "integrantes" y excluye el campo "_id" del documento,
     * 3. Ejecuta la consulta con el filtro y la proyeccion y agarra el primer resultado (solo hay uno),
     * 4. Verifica que existe el resultado y que es de tipo "integrantes"
     * 5. Extrae los documentos que contienen los integrantes y verifica que su estado sea activo, si no tiene estado lo tomara como no activo(false)  
     *    y por cada documento integrante que su estado sea activo lo convierte a a un objeto Integrante
     * 6. Agrega los resultados a la lista y la devuelve
     * @param idArtista
     * @return lista de integrantes
     */
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

    /**
     * Metodo de soporte para realizar la conversion de documento integrante a objeto integrante
     * Crea un objeto integrante con todos los datos del documento integrante y devuelve el objeto integrante
     * @param doc
     * @return integrante
     */
    public Integrante convertirDocumentoAIntegrante(Document doc) {
        Integrante integrante = new Integrante();
        
        integrante.setNombre(doc.getString("nombre"));
        integrante.setRol(RolIntegrante.valueOf(doc.getString("rol")));
        integrante.setActivo(doc.getBoolean("activo", false));
        integrante.setFechaIngreso(doc.getDate("fechaIngreso"));
        integrante.setFechaSalida(doc.getDate("fechaSalida")); // puede ser null
        
        return integrante;
    }
    
    private List<String> obtenerGenerosRestringidos(String idUsuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = db.getCollection("usuarios", Usuario.class);

        Usuario usuario = coleccion.find(new Document("_id", new ObjectId(idUsuario))).first();
        return (usuario != null && usuario.getRestricciones() != null)
                ? usuario.getRestricciones()
                : new ArrayList<>();
    }


    
}
