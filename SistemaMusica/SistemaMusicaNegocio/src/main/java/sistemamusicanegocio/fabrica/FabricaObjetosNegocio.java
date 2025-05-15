/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.fabrica;

import sistemamusicanegocio.implementaciones.ArtistasBO;
import sistemamusicanegocio.implementaciones.IntegrantesBO;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicanegocio.interfaces.IIntegrantesBO;
import sistemamusicapersistencia.implementaciones.ArtistasDAO;
import sistemamusicapersistencia.implementaciones.IntegrantesDAO;
import sistemamusicapersistencia.interfaces.IArtistasDAO;
import sistemamusicapersistencia.interfaces.IIntegrantesDAO;

/**
 *
 * @author gael_
 */
public class FabricaObjetosNegocio {
    
    public static IArtistasBO crearArtistasBO(){
        IArtistasDAO artistasDAO = new ArtistasDAO();
        return new ArtistasBO(artistasDAO);
    }
    
    public static IIntegrantesBO crearIntegrantesBO(){
        IIntegrantesDAO integrantesDAO = new IntegrantesDAO();
        return new IntegrantesBO(integrantesDAO);
    }
    
}
