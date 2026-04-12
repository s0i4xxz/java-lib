package s0i4x.satoshidevlib.exceptions;

/**
 * Thrown when a network-level failure occurs (timeout, connection refused, etc.).
 */
public class NetworkException extends SatoshiException {

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
