/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemamusicapresentacion.exception;

/**
 *
 * @author gael_
 */
public class PresentacionException extends Exception{

    public PresentacionException() {
    }

    public PresentacionException(String message) {
        super(message);
    }

    public PresentacionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresentacionException(Throwable cause) {
        super(cause);
    }

    public PresentacionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
