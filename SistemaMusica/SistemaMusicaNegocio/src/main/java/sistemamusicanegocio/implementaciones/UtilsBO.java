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

    public UtilsBO(IUtilsDAO utilsDAO) {
        this.utilsDAO = utilsDAO;
    }
    
    
    @Override
    public void insertarDatos(){
        utilsDAO.insertarDatos();
    }
}
