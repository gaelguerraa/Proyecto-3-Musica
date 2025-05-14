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

}
