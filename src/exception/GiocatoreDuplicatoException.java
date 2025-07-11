package exception;

/**
 *
 * @author Giuseppe Ravesi
 */
public class GiocatoreDuplicatoException extends Exception {

    public GiocatoreDuplicatoException() {
        super("Il giocatore è già presente nella rosa.");
    }
}
