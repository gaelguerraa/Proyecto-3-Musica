/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sistemamusicapersistencia.interfaces;

import sistemamusica.dtos.ArtistaDTO;
import sistemamusicadominio.Artista;

/**
 *
 * @author gael_
 */
public interface IArtistasDAO {
    public abstract Artista registrarSolista(ArtistaDTO nuevoSolista);
    public abstract Artista registrarBanda(ArtistaDTO nuevaBanda);
}
