/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.fabrica;

import sistemamusicanegocio.implementaciones.ArtistasBO;
import sistemamusicanegocio.implementaciones.UsuariosBO;
import sistemamusicanegocio.implementaciones.UtilsBO;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicanegocio.interfaces.IUsuariosBO;
import sistemamusicanegocio.interfaces.IUtilsBO;
import sistemamusicapersistencia.implementaciones.ArtistasDAO;
import sistemamusicapersistencia.implementaciones.UsuariosDAO;
import sistemamusicapersistencia.implementaciones.UtilsDAO;
import sistemamusicapersistencia.interfaces.IArtistasDAO;
import sistemamusicapersistencia.interfaces.IUsuariosDAO;
import sistemamusicapersistencia.interfaces.IUtilsDAO;

/**
 *
 * @author gael_
 */
public class FabricaObjetosNegocio {

    /**
     * Metodo constructor de Artistas
     *
     * @return Constructor de Artistas
     */
    public static IArtistasBO crearArtistasBO() {
        IArtistasDAO artistasDAO = new ArtistasDAO();
        return new ArtistasBO(artistasDAO);
    }


    /**
     * Metodo constructor de Usuarios
     *
     * @return Constructor de Usuarios
     */
    public static IUsuariosBO crearUsuariosBO() {
        IUsuariosDAO usuariosDAO = new UsuariosDAO();
        return new UsuariosBO(usuariosDAO);
    }

    /**
     * Metodo constructor de Utils
     *
     * @return Constructor de Utils
     */
    public static IUtilsBO crearUtilsBO(){
        IUtilsDAO utilsDAO = new UtilsDAO();
        return new UtilsBO(utilsDAO);
    }
    
}
