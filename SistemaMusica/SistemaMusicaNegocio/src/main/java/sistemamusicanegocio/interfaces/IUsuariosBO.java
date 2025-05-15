/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicanegocio.interfaces;

import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Usuario;
import sistemamusicanegocio.exception.NegocioException;

/**
 *
 * @author gael_
 */
public interface IUsuariosBO {

    /**
     * Metodo para agregar un nuevo usuario a la base de datos
     *
     * @param nuevoUsuario Nuevo usuario a agregar a la base de datos
     * @return Usuario que ha sido agregado
     */
    public Usuario agregarUsuario(UsuarioDTO nuevoUsuario);

    /**
     * Metodo para consultar un usuario en base a un id en la base de datos
     *
     * @param idUsuario ID del usuario a buscar
     * @return Usuario obtenido con el id mencionado
     */
    public Usuario consultarPorId(String idUsuario);

    /**
     * Metodo para modificar un usuario especificado por su id
     *
     * @param idUsuario ID del usuario a modificar
     * @param datosActualizados Datos a actualizar del usuario
     * @return Usuario modificado de la base de datos
     */
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados);

    /**
     * Metodo para que el usuario pueda iniciar sesion, mandando su nombre de
     * usuario y su contrasenia
     *
     * @param username Nombre del usuario
     * @param contrasenia Contrasenia del usuario
     * @return UsuarioDTO de solo lectura para mostrar datos del usuario
     * @throws NegocioException si ocurre una incidencia
     */
    public UsuarioDTO iniciarSesion(String username, String contrasenia) throws NegocioException;
}
