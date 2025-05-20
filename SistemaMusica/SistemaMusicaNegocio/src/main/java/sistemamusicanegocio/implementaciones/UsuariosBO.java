/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.implementaciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import sistemamusica.dtos.AlbumFavoritoDTO;
import sistemamusica.dtos.ArtistaFavoritoDTO;
import sistemamusica.dtos.CancionFavoritaDTO;
import sistemamusica.dtos.FavoritoDTO;
import sistemamusica.dtos.GeneroFavoritoDTO;
import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Favorito;
import sistemamusicadominio.Usuario;
import sistemamusicanegocio.exception.NegocioException;
import sistemamusicanegocio.interfaces.IUsuariosBO;
import sistemamusicapersistencia.interfaces.IUsuariosDAO;

/**
 *
 * @author gael_
 */
public class UsuariosBO implements IUsuariosBO {

    private IUsuariosDAO usuariosDAO;

    /**
     * Constructor que recibe una implementación de IUsuariosDAO.
     *
     * @param usuariosDAO Objeto que implementa la interfaz IUsuariosDAO para el
     * acceso a datos.
     */
    public UsuariosBO(IUsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
    }

    /**
     * Metodo para que el usuario pueda iniciar sesion, mandando su nombre de
     * usuario y su contrasenia
     *
     * @param username Nombre del usuario
     * @param contrasenia Contrasenia del usuario
     * @return UsuarioDTO de solo lectura para mostrar datos del usuario
     * @throws NegocioException si ocurre una incidencia
     */
    @Override
    public UsuarioDTO iniciarSesion(String username, String contrasenia) throws NegocioException {
        validarUsername(username);
        validarContrasenia(contrasenia);

        Usuario usuarioExistente = usuariosDAO.consultarPorUsername(username);

        if (usuarioExistente != null) {

            Usuario usuario = usuariosDAO.consultarInicioSesion(username, contrasenia);

            if (usuario != null) {
                UsuarioDTO usuarioObtenido = new UsuarioDTO();
                usuarioObtenido.setId(usuario.getId().toString()); // Esto estaba en toHexString() si hay un error cambiarlo nuevamente
                usuarioObtenido.setUsername(usuario.getUsername());
                usuarioObtenido.setEmail(usuario.getEmail());
                usuarioObtenido.setContrasenia(usuario.getContrasenia());
                usuarioObtenido.setImagenPerfil(usuario.getImagenPerfil());
                usuarioObtenido.setFavoritos(usuario.getFavoritos());
                usuarioObtenido.setRestricciones(usuario.getRestricciones());

                return usuarioObtenido;
            } else {
                throw new NegocioException("Credenciales invalidas: usuario o contraseña incorrectos.");
            }
        } else {
            throw new NegocioException("El usuario no esta registrado");
        }
    }

    /**
     * Metodo para agregar un nuevo usuario a la base de datos
     *
     * @param nuevoUsuario Nuevo usuario a agregar a la base de datos
     * @return Usuario que ha sido agregado
     * @throws NegocioException Si ocurre una incidencia al agregar el usuario
     */
    @Override
    public UsuarioDTO agregarUsuario(UsuarioDTO nuevoUsuario) throws NegocioException {
        String username = nuevoUsuario.getUsername();
        String email = nuevoUsuario.getEmail();
        String contrasenia = nuevoUsuario.getContrasenia();

        validarUsername(username);
        validarEmail(email);
        validarContrasenia(contrasenia);

        Usuario usuarioExistente = usuariosDAO.consultarPorUsername(username);

        if (usuarioExistente == null) {

            Usuario usuario = usuariosDAO.agregarUsuario(nuevoUsuario);

            if (usuario != null) {
                UsuarioDTO usuarioObtenido = new UsuarioDTO();
                usuarioObtenido.setUsername(usuario.getUsername());
                usuarioObtenido.setEmail(usuario.getEmail());
                usuarioObtenido.setContrasenia(usuario.getContrasenia());
                usuarioObtenido.setImagenPerfil(usuario.getImagenPerfil());
                usuarioObtenido.setFavoritos(usuario.getFavoritos());
                usuarioObtenido.setRestricciones(usuario.getRestricciones());

                return usuarioObtenido;
            } else {
                throw new NegocioException("No se pudo agregar al usuario a la base de datos.");
            }

        } else {
            throw new NegocioException("El usuario con el username "
                    + username + " ya existe");
        }
    }

    /**
     * Consulta un usuario por su ID después de validar que el ID sea válido.
     *
     * @param idUsuario ID del usuario a consultar.
     * @return El usuario encontrado.
     */
    @Override
    public Usuario consultarPorId(String idUsuario) {
        return usuariosDAO.consultarPorId(idUsuario);
    }

    /**
     * Consulta un usuario por su ID despues de validar el ID, para
     * posteriormente regresarlo como DTO
     *
     * @param idUsuario ID del usuario a consultar
     * @return El usuario encontrado en forma de DTO
     */
    @Override
    public UsuarioDTO consultarPorIdDTO(String idUsuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        Usuario usuarioObtenido = usuariosDAO.consultarPorId(idUsuario);

        usuarioDTO.setUsername(usuarioObtenido.getUsername());
        usuarioDTO.setEmail(usuarioObtenido.getEmail());
        usuarioDTO.setContrasenia(usuarioObtenido.getContrasenia());
        usuarioDTO.setImagenPerfil(usuarioObtenido.getImagenPerfil());
        usuarioDTO.setFavoritos(usuarioObtenido.getFavoritos());
        usuarioDTO.setRestricciones(usuarioObtenido.getRestricciones());

        return usuarioDTO;
    }

    /**
     * Modifica los datos de un usuario existente después de validar todos los
     * campos.
     *
     * @param idUsuario ID del usuario a modificar.
     * @param datosActualizados DTO con los nuevos datos del usuario.
     * @return El usuario modificado.
     * @throws NegocioException Si los datos no son válidos o si ocurre un error
     * durante la actualización.
     */
    @Override
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados) throws NegocioException {
        if (datosActualizados == null) {
            throw new NegocioException("Los datos actualizados no pueden ser nulos.");
        }

        // Validar que exista el usuario antes de modificarlo
        Usuario usuarioExistente = consultarPorId(idUsuario);
        if (usuarioExistente == null) {
            throw new NegocioException("El usuario a modificar no existe.");
        }

        // Validar campos individuales si están presentes
        if (datosActualizados.getUsername() != null) {
            validarUsername(datosActualizados.getUsername());

            // Verificar que el nuevo username no esté en uso por otro usuario
            Usuario usuarioConMismoUsername = usuariosDAO.consultarPorUsername(datosActualizados.getUsername());
            if (usuarioConMismoUsername != null && !usuarioConMismoUsername.getId().toString().equals(idUsuario)) {
                throw new NegocioException("El nombre de usuario ya está en uso por otro usuario.");
            }
        }

        if (datosActualizados.getEmail() != null) {
            validarEmail(datosActualizados.getEmail());
        }

        if (datosActualizados.getContrasenia() != null) {
            validarContrasenia(datosActualizados.getContrasenia());
        }

        // Verificar que al menos un campo sea modificado
        if (datosActualizados.getUsername() == null
                && datosActualizados.getEmail() == null
                && datosActualizados.getContrasenia() == null
                && datosActualizados.getImagenPerfil() == null) {
            throw new NegocioException("Debe proporcionar al menos un campo para actualizar.");
        }

        return usuariosDAO.modificarUsuario(idUsuario, datosActualizados);

    }

    /**
     * Validador para el nombre de usuario
     *
     * @param username Nombre de usuario a validar
     * @return Verdadero si cumple con las validaciones, falso en caso contrario
     * @throws NegocioException En caso de que haya una incidencia con el nombre
     * de usuario
     */
    private void validarUsername(String username) throws NegocioException {
        final int CANTIDAD_MAX_CARACTERES_USERNAME = 5;

        if (username == null || username.isBlank()) {
            throw new NegocioException("El nombre de usuario no puede estar vacio.");
        }

        if (username.length() < CANTIDAD_MAX_CARACTERES_USERNAME) {
            throw new NegocioException("El nombre de usuario debe tener al menos 5 caracteres.");
        }

        if (!username.matches("^[A-Za-z0-9]+$")) {
            throw new NegocioException("El nombre de usuario no puede contener caracteres especiales.");
        }
    }

    /**
     * Validador para la contrasenia del usuario
     *
     * @param contrasenia Contrasenia del usuario a validar
     * @throws NegocioException En caso de que haya una incidencia con la
     * contrasenia del usuario
     */
    private void validarContrasenia(String contrasenia) throws NegocioException {
        final int CANTIDAD_MAX_DIGITOS_CONTRASENIA = 10;

        if (contrasenia == null || contrasenia.isBlank()) {
            throw new NegocioException("La contraseña no puede estar vacia.");
        }

        if (contrasenia.length() < CANTIDAD_MAX_DIGITOS_CONTRASENIA) {
            throw new NegocioException("La contraseña debe tener al menos "
                    + CANTIDAD_MAX_DIGITOS_CONTRASENIA + " caracteres.");
        }

        if (!contrasenia.matches(".*[A-Z].*")) {
            throw new NegocioException("La contraseña debe contener al menos una letra mayuscula.");
        }

        if (!contrasenia.matches(".*[a-z].*")) {
            throw new NegocioException("La contraseña debe contener al menos una letra minuscula.");
        }

        if (!contrasenia.matches(".*\\d.*")) {
            throw new NegocioException("La contraseña debe contener al menos un numero.");
        }

        if (!contrasenia.matches(".*[^a-zA-Z0-9].*")) {
            throw new NegocioException("La contraseña debe contener al menos un caracter especial.");
        }
    }

    /**
     * Validador para el email del usuario
     *
     * @param email Email del usuario a validar
     * @throws NegocioException En caso de que haya una incidencia con el email
     * del usuario
     */
    private void validarEmail(String email) throws NegocioException {
        if (email == null || email.isBlank()) {
            throw new NegocioException("El email no puede estar vacio.");
        }

        if (!email.endsWith("@gmail.com") && !email.endsWith("@yahoo.com")
                && !email.endsWith("@outlook.com")
                && !email.endsWith("@potros.itson.edu.mx")) {
            throw new NegocioException("""
                                       El email no cuenta con una direccion de correo valida.
                                       Necesita ser una de las siguientes:
                                       @gmail.com
                                       @yahoo.com
                                       @outlook.com
                                       @potros.itson.edu.mx
                                       """);
        }

        // Extraer la parte antes del @ para validacion adicional
        String nombreUsuario = email.substring(0, email.indexOf('@'));

        if (!nombreUsuario.matches("^[A-Za-z0-9]+$")) {
            throw new NegocioException("El email solo puede contener letras y numeros antes del '@'");
        }
    }

    /**
     * Agrega un contenido a los favoritos de un usuario.
     *
     * @param idUsuario ID del usuario que agrega el favorito.
     * @param favoritoDTO DTO con la información del contenido favorito.
     * @return true si se agregó correctamente, false en caso contrario.
     * @throws NegocioException Si el usuario o el contenido son nulos.
     */
    @Override
    public boolean agregarFavorito(String idUsuario, FavoritoDTO favoritoDTO) throws NegocioException {
        if (idUsuario == null || favoritoDTO == null) {
            throw new NegocioException("Debe seleccionar un usario y un contenido a agregar a favoritos.");
        }
        return usuariosDAO.agregarFavorito(idUsuario, favoritoDTO);
    }

    /**
     * Elimina un contenido de los favoritos de un usuario.
     *
     * @param idUsuario ID del usuario que elimina el favorito.
     * @param idContenido ID del contenido a eliminar de favoritos.
     * @return true si se eliminó correctamente, false en caso contrario.
     * @throws NegocioException Si el usuario o el contenido son nulos.
     */
    @Override
    public boolean eliminarFavorito(String idUsuario, String idContenido) throws NegocioException {
        if (idUsuario == null || idContenido == null) {
            throw new NegocioException("Debe seleccionar un usario y un contenido a eliminar favoritos.");
        }
        return usuariosDAO.eliminarFavorito(idUsuario, idContenido);
    }

    /**
     * Obtiene los álbumes favoritos de un usuario, filtrados por nombre.
     *
     * @param idUsuario ID del usuario.
     * @param nombreAlbum Nombre o parte del nombre del álbum a buscar.
     * @return Lista de álbumes favoritos que coinciden con el criterio.
     */
    @Override
    public List<AlbumFavoritoDTO> obtenerAlbumesFavoritos(String idUsuario, String nombreAlbum) {
        List<Document> documentos = usuariosDAO.obtenerAlbumesFavoritos(idUsuario, nombreAlbum);
        
        List<AlbumFavoritoDTO> resultado = new ArrayList<>();
        for (Document doc : documentos) {
            AlbumFavoritoDTO dto = new AlbumFavoritoDTO();
            dto.setIdFavorito(doc.getObjectId("idFavorito").toString());
            dto.setIdAlbum(doc.getObjectId("idAlbum").toString());
            dto.setNombreAlbum(doc.getString("nombreAlbum"));
            dto.setNombreArtista(doc.getString("nombreArtista"));
            dto.setGenero(doc.getString("genero"));
            dto.setFechaLanzamiento(doc.getDate("fechaLanzamiento"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
             resultado.add(dto);
        }

        return resultado;
    }

    /**
     * Obtiene los artistas favoritos de un usuario, filtrados por nombre.
     *
     * @param idUsuario ID del usuario.
     * @param nombreArtista Nombre o parte del nombre del artista a buscar.
     * @return Lista de artistas favoritos que coinciden con el criterio.
     */
    @Override
    public List<ArtistaFavoritoDTO> obtenerArtistasFavoritos(String idUsuario, String nombreArtista) {
        List<Document> documentos = usuariosDAO.obtenerArtistasFavoritos(idUsuario, nombreArtista);
        
        List<ArtistaFavoritoDTO> resultado = new ArrayList<>();
        for (Document doc : documentos) {
            ArtistaFavoritoDTO dto = new ArtistaFavoritoDTO();
            dto.setIdFavorito(doc.getObjectId("idFavorito").toString());
            dto.setIdArtista(doc.getObjectId("idArtista").toString());
            dto.setNombreArtista(doc.getString("nombreArtista"));
            dto.setTipoArtista(doc.getString("tipoArtista"));
            dto.setGeneroArtista(doc.getString("generoArtista"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
            resultado.add(dto);
        }

        return resultado;
  
    }

    /**
     * Obtiene las canciones favoritas de un usuario, filtradas por nombre.
     *
     * @param idUsuario ID del usuario.
     * @param nombreCancion Nombre o parte del nombre de la canción a buscar.
     * @return Lista de canciones favoritas que coinciden con el criterio.
     */
    @Override
    public List<CancionFavoritaDTO> obtenerCancionesFavoritas(String idUsuario, String nombreCancion) {
        
        List<Document> documentos = usuariosDAO.obtenerCancionesFavoritas(idUsuario, nombreCancion);
        
        List<CancionFavoritaDTO> resultado = new ArrayList<>();
        for (Document doc : documentos) {
            CancionFavoritaDTO dto = new CancionFavoritaDTO();
            dto.setIdFavorito(doc.getObjectId("idFavorito").toString());
            dto.setIdCancion(doc.getObjectId("idCancion").toString());
            dto.setTitulo(doc.getString("titulo"));
            dto.setDuracion(doc.getDouble("duracion").floatValue());
            dto.setNombreArtista(doc.getString("nombreArtista"));
            dto.setNombreAlbum(doc.getString("nombreAlbum"));
            dto.setGenero(doc.getString("genero"));
            dto.setFechaAgregacion(doc.getDate("fechaAgregacion"));
            resultado.add(dto);
        }

        return resultado;

    }

    /**
     * Obtiene los géneros favoritos de un usuario, filtrados por género.
     *
     * @param idUsuario ID del usuario.
     * @param genero Género musical a buscar.
     * @return Lista de géneros favoritos que coinciden con el criterio.
     */
    @Override
    public List<GeneroFavoritoDTO> obtenerGenerosFavoritos(String idUsuario, String genero) {
        
        List<Document> documentos = usuariosDAO.obtenerGenerosFavoritos(idUsuario, genero);
        List<GeneroFavoritoDTO> resultado = new ArrayList<>();
        for (Document fav : documentos) {
                String generoFav = fav.getString("genero");
                if (generoFav != null && generoFav.toLowerCase().contains(genero.toLowerCase())) {
                    GeneroFavoritoDTO dto = new GeneroFavoritoDTO();
                    dto.setIdFavorito(fav.getObjectId("_id").toHexString());
                    dto.setIdContenido(fav.getObjectId("idContenido").toHexString());
                    dto.setNombre(fav.getString("nombre"));
                    dto.setGenero(generoFav);
                    dto.setTipo(fav.getString("tipo"));
                    dto.setFechaAgregacion(fav.getDate("fechaAgregacion"));
                    resultado.add(dto);
                }
            }
        
        return resultado;
    }

    /**
     * Consulta los favoritos de un usuario dentro de un rango de fechas.
     *
     * @param idUsuario ID del usuario.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de favoritos agregados en el rango de fechas especificado.
     */
    @Override
    public List<GeneroFavoritoDTO> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin) {
        
        List<Document> documentos = usuariosDAO.consultarFavoritosPorRangoFechas(idUsuario, fechaInicio, fechaFin);
        List<GeneroFavoritoDTO> resultados = new ArrayList<>();
        
        for (Document fav : documentos) {
                Date fecha = fav.getDate("fechaAgregacion");
                if (fecha != null && !fecha.before(fechaInicio) && !fecha.after(fechaFin)) {
                    GeneroFavoritoDTO dto = new GeneroFavoritoDTO();
                    dto.setIdFavorito(fav.getObjectId("_id").toHexString());
                    dto.setIdContenido(fav.getObjectId("idContenido").toHexString());
                    dto.setNombre(fav.getString("nombre"));
                    dto.setGenero(fav.getString("genero"));
                    dto.setTipo(fav.getString("tipo"));
                    dto.setFechaAgregacion(fecha);
                    resultados.add(dto);
                }
            }
        
        return resultados;
    }

    /**
     * Obtiene todos los favoritos de un usuario.
     *
     * @param idUsuario ID del usuario.
     * @return Lista completa de todos los favoritos del usuario.
     */
    @Override
    public List<GeneroFavoritoDTO> obtenerTodosFavoritos(String idUsuario) {
        List<Document> documentos = usuariosDAO.obtenerTodosFavoritos(idUsuario);
        List<GeneroFavoritoDTO> resultado = new ArrayList<>();
        
        for (Document fav : documentos) {
                GeneroFavoritoDTO dto = new GeneroFavoritoDTO();
                dto.setIdFavorito(fav.getObjectId("_id").toHexString());
                dto.setIdContenido(fav.getObjectId("idContenido").toHexString());
                dto.setNombre(fav.getString("nombre"));
                dto.setGenero(fav.getString("genero"));
                dto.setTipo(fav.getString("tipo"));
                dto.setFechaAgregacion(fav.getDate("fechaAgregacion"));
                resultado.add(dto);
               } 
        return resultado;
    }

    /**
     * Consulta los favoritos de un usuario.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de favoritos del usuario.
     */
    @Override
    public List<Favorito> consultarFavoritos(String idUsuario) {
        return usuariosDAO.consultarFavoritos(idUsuario);
    }

    /**
     * Agrega un género a la lista de restringidos de un usuario.
     *
     * @param idUsuario ID del usuario.
     * @param genero Género a restringir.
     */
    @Override
    public void agregarGeneroRestringido(String idUsuario, String genero) {
        usuariosDAO.agregarGeneroRestringido(idUsuario, genero);
    }

    /**
     * Elimina un género de la lista de restringidos de un usuario.
     *
     * @param idUsuario ID del usuario.
     * @param genero Género a dejar de restringir.
     */
    @Override
    public void eliminarGeneroRestringido(String idUsuario, String genero) {
        usuariosDAO.eliminarGeneroRestringido(idUsuario, genero);
    }

    /**
     * Muestra los géneros restringidos de un usuario.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de géneros restringidos para el usuario.
     */
    @Override
    public List<String> mostrarGenerosRestringidos(String idUsuario) {
        return usuariosDAO.mostrarGenerosRestringidos(idUsuario);
    }

}
