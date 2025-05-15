/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.fabrica;

import sistemamusicanegocio.implementaciones.ArtistasBO;
import sistemamusicanegocio.implementaciones.IntegrantesBO;
import sistemamusicanegocio.implementaciones.UsuariosBO;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicanegocio.interfaces.IIntegrantesBO;
import sistemamusicanegocio.interfaces.IUsuariosBO;
import sistemamusicapersistencia.implementaciones.ArtistasDAO;
import sistemamusicapersistencia.implementaciones.IntegrantesDAO;
import sistemamusicapersistencia.implementaciones.UsuariosDAO;
import sistemamusicapersistencia.interfaces.IArtistasDAO;
import sistemamusicapersistencia.interfaces.IIntegrantesDAO;
import sistemamusicapersistencia.interfaces.IUsuariosDAO;

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
     * Metodo constructor de Integrantes
     *
     * @return Constructor de Integrantes
     */
    public static IIntegrantesBO crearIntegrantesBO() {
        IIntegrantesDAO integrantesDAO = new IntegrantesDAO();
        return new IntegrantesBO(integrantesDAO);
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

}
