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
