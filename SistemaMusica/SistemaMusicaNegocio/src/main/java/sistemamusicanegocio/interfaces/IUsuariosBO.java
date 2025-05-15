/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicanegocio.interfaces;

import sistemamusica.dtos.UsuarioDTO;
import sistemamusicadominio.Usuario;

/**
 *
 * @author gael_
 */
public interface IUsuariosBO {
    public Usuario agregarUsuario(UsuarioDTO nuevoUsuario);
    public Usuario consultarPorId(String idUsuario);
    public Usuario modificarUsuario(String idUsuario, UsuarioDTO datosActualizados);
}
