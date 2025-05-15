/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapresentacion.usuario;

import sistemamusica.dtos.UsuarioDTO;
import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class ControladorUsuario {
    frmUsuarioPrincipal usuarioPrincipal;
    frmFavoritosUsuario favoritosUsuario;
    frmCambiarDatos cambiarDatos;
    frmRestringidosUsuario restringidosUsuario;
    
    ControladorUniversal universal;
    UsuarioDTO usuarioDTO;

    public ControladorUsuario() {
    }
    
//    public void mostrarUsuarioPrincipal(){
//        this.usuarioPrincipal = new frmUsuarioPrincipal(this, universal, usuarioDTO);
//        usuarioPrincipal.setVisible(true);
//    }
    
//    public void mostrarFavoritosUsuario(){
//        this.favoritosUsuario = new frmFavoritosUsuario(this, universal);
//        favoritosUsuario.setVisible(true);
//    }
    
//    public void mostrarRestringidosUsuario(){
//        this.restringidosUsuario = new frmRestringidosUsuario(this, universal);
//        restringidosUsuario.setVisible(true);
//    }
    
//    public void mostrarCambiarDatos(){
//        this.cambiarDatos = new frmCambiarDatos(this);
//        cambiarDatos.setVisible(true);
//    }
}
