package s0i4x.satoshidevlib.exceptions;

/**
 * Thrown when the server returns a 4xx client error.
 */
public class ClientException extends SatoshiException {

    public ClientException(String message, int statusCode, String errorType) {
        super(message, statusCode, errorType);
    }

    public ClientException(String message, int statusCode, String errorType, Throwable cause) {
        super(message, statusCode, errorType, cause);
    }
}
