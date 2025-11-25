package pl.edu.pg.eti.kask.app.authorization.exception;

public class NoPrincipalException extends SecurityException {

    public NoPrincipalException() {
    }

    public NoPrincipalException(String s) {
        super(s);
    }

    public NoPrincipalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPrincipalException(Throwable cause) {
        super(cause);
    }

}
