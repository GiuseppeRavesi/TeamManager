/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package exception;

/**
 *
 * @author Giuseppe Ravesi
 */
public class SovrapposizioneEventoException extends Exception {

    /**
     * Creates a new instance of <code>SovrapposizioneEventoException</code>
     * without detail message.
     */
    public SovrapposizioneEventoException() {
        super("Sovrapposizione degli eventi rilevata");
    }

   
    public SovrapposizioneEventoException(String msg) {
        super(msg);
    }
}
