/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicanegocio.implementaciones;

import sistemamusicanegocio.interfaces.IUtilsBO;
import sistemamusicapersistencia.interfaces.IUtilsDAO;


/**
 *
 * @author gael_
 */
public class UtilsBO implements IUtilsBO{
    
    IUtilsDAO utilsDAO;

    /**
     * Constructor que recibe una implementación de IUtilsDAO.
     * 
     * @param utilsDAO Objeto que implementa la interfaz IUtilsDAO para el acceso a datos.
     */
    public UtilsBO(IUtilsDAO utilsDAO) {
        this.utilsDAO = utilsDAO;
    }
    
    /**
     * Inserta artistas con albumemes y canciones masivamente en la base de datos.
     * Este método no retorna ningún valor y no lanza excepciones.
     */
    @Override
    public void insertarDatos(){
        utilsDAO.insertarDatos();
    }
}
