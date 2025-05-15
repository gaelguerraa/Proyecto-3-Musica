/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Usuario;

/**
 *
 * @author gael_
 */
public interface IUsuariosDAO {

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
     * Metodo para consultar un usuario en base a un username en la base de
     * datos
     *
     * @param username Nombre del usuario
     * @return Usuario obtenido con el username mencionado
     */
    public Usuario consultarPorUsername(String username);

    /**
     * Metodo para modificar un usuario especificado por su id
     *
     * @param idUsuario ID del usuario a modificar
     * @param datosActualizados Datos a actualizar del usuario
     * @return Usuario modificado de la base de datos
     */
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados);

    /**
     * Metodo para obtener un usuario en base a un nombre de usuario y una
     * contrasenia
     *
     * @param username Nombre de usuario
     * @param contrasenia Contrasenia del usuario
     * @return Un usuario que coincida con ambos filtros
     */
    public Usuario consultarInicioSesion(String username, String contrasenia);

}
