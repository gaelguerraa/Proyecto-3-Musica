/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapresentacion.artistas;

import sistemamusicapresentacion.main.ControladorUniversal;

/**
 *
 * @author gael_
 */
public class ControladorArtistas {
    frmAgregarBanda agregarBanda;
    frmAgregarSolista agregarSolista;
    frmArtistasDetalles artistasDetalles;
    frmArtistasPrincipal artistasPrincipal;
    
    ControladorUniversal universal;

    public ControladorArtistas() {
    }
    
    public void mostrarArtistasPrincipal(){
        this.artistasPrincipal = new frmArtistasPrincipal(this, universal);
        artistasPrincipal.setVisible(true);
    }
    
    public void mostrarAgregarSolista(){
        this.agregarSolista = new frmAgregarSolista();
        agregarSolista.setVisible(true);
    }
    
    public void mostrarAgregarBanda(){
        this.agregarBanda = new frmAgregarBanda();
        agregarBanda.setVisible(true);
    }
    
    public void mostrarArtistasDetalles(){
        this.artistasDetalles = new frmArtistasDetalles(this, universal);
        artistasDetalles.setVisible(true);
    }
}
