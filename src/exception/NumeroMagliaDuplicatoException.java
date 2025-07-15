package exception;

/**
 *
 * @author Giuseppe Ravesi
 */
public class NumeroMagliaDuplicatoException extends Exception {

    public NumeroMagliaDuplicatoException() {
        super("Numero maglia già utilizzato");
    }

}
