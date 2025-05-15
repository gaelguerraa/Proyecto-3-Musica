/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.implementaciones;

import sistemamusica.dtos.UsuarioDTO;
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

        Usuario usuario = usuariosDAO.consultarInicioSesion(username, contrasenia);

        if (usuario != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId().toHexString());
            usuarioDTO.setUsername(usuario.getUsername());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setContrasenia(usuario.getContrasenia());
            usuarioDTO.setImagenPerfil(usuario.getImagenPerfil());
            usuarioDTO.setFavoritos(usuario.getFavoritos());
            usuarioDTO.setRestricciones(usuario.getRestricciones());

            return usuarioDTO;
        } else {
            throw new NegocioException("Credenciales invalidas: usuario o contraseña incorrectos.");
        }
    }

    @Override
    public Usuario agregarUsuario(UsuarioDTO nuevoUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

        if (!username.matches("^[A-Za-z]+$")) {
            throw new NegocioException("El nombre de usuario solo puede contener letras (sin numeros ni caracteres especiales).");
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

}
