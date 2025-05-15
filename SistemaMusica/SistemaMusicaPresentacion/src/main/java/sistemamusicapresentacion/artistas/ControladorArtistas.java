/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapresentacion.artistas;

import sistemamusicanegocio.fabrica.FabricaObjetosNegocio;
import sistemamusicanegocio.interfaces.IArtistasBO;
import sistemamusicanegocio.interfaces.IIntegrantesBO;
import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class ControladorArtistas {
    
    IArtistasBO artistasBO; 
    IIntegrantesBO integrantesBO;
    
    frmAgregarBanda agregarBanda;
    frmAgregarSolista agregarSolista;
    frmArtistasDetalles artistasDetalles;
    frmArtistasPrincipal artistasPrincipal;
    
    ControladorUniversal universal;

    public ControladorArtistas() {
        this.artistasBO=FabricaObjetosNegocio.crearArtistasBO();
        
    }
    
    public void mostrarArtistasPrincipal(){
        this.artistasPrincipal = new frmArtistasPrincipal(this, universal, artistasBO);
        artistasPrincipal.setVisible(true);
    }
    
    public void mostrarAgregarSolista(){
        this.agregarSolista = new frmAgregarSolista(this,artistasBO);
        agregarSolista.setVisible(true);
    }
    
    public void mostrarAgregarBanda(){
        this.agregarBanda = new frmAgregarBanda(this, artistasBO, integrantesBO);
        agregarBanda.setVisible(true);
    }
    
    public void mostrarArtistasDetalles(){
        this.artistasDetalles = new frmArtistasDetalles(this, universal, artistasBO);
        artistasDetalles.setVisible(true);
    }
}
