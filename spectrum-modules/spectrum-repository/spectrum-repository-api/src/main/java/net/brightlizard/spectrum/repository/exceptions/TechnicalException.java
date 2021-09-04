package net.brightlizard.spectrum.repository.exceptions;

/**
 * @author Ovcharov Ilya (ovcharov.ilya@gmail.com)
 * net.brightlizard (c)
 */
public class TechnicalException extends Exception {

    private static final long serialVersionUID = -6926167778154953100L;

    public TechnicalException() {
        super();
    }

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }
}