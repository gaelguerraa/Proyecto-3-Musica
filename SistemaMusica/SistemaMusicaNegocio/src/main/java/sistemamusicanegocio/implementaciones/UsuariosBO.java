/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.implementaciones;

import java.util.Date;
import java.util.List;
import sistemamusica.dtos.AlbumFavoritoDTO;
import sistemamusica.dtos.ArtistaFavoritoDTO;
import sistemamusica.dtos.CancionFavoritaDTO;
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

    @Override
    public Usuario consultarPorId(String idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    @Override
    public boolean agregarFavorito(String idUsuario, Favorito favorito) {
        return usuariosDAO.agregarFavorito(idUsuario, favorito);
    }

    @Override
    public boolean eliminarFavorito(String idUsuario, String idContenido) {
        return usuariosDAO.eliminarFavorito(idUsuario, idContenido);
    }

    @Override
    public List<AlbumFavoritoDTO> obtenerAlbumesFavoritos(String idUsario, String nombreAlbum) {
        return usuariosDAO.obtenerAlbumesFavoritos(idUsario, nombreAlbum);
    }

    @Override
    public List<ArtistaFavoritoDTO> obtenerArtistasFavoritos(String idUsuario, String nombreArtista) {
        return usuariosDAO.obtenerArtistasFavoritos(idUsuario, nombreArtista);
    }

    @Override
    public List<CancionFavoritaDTO> obtenerCancionesFavoritas(String idUsuario, String nombreCancion) {
        return usuariosDAO.obtenerCancionesFavoritas(idUsuario, nombreCancion);
    }

    @Override
    public List<GeneroFavoritoDTO> obtenerGenerosFavoritos(String idUsuario, String genero) {
        return usuariosDAO.obtenerGenerosFavoritos(idUsuario, genero);
    }

    @Override
    public List<GeneroFavoritoDTO> consultarFavoritosPorRangoFechas(String idUsuario, Date fechaInicio, Date fechaFin) {
        return usuariosDAO.consultarFavoritosPorRangoFechas(idUsuario, fechaInicio, fechaFin);
    }

    @Override
    public List<GeneroFavoritoDTO> obtenerTodosFavoritos(String idUsuario) {
        return usuariosDAO.obtenerTodosFavoritos(idUsuario);
    }

}
