package s0i4x.satoshidevlib.exceptions;

/**
 * Base exception for all SatoshiDevLib errors.
 */
public class SatoshiException extends RuntimeException {

    private final int statusCode;
    private final String errorType;

    public SatoshiException(String message) {
        this(message, 0, null, null);
    }

    public SatoshiException(String message, Throwable cause) {
        this(message, 0, null, cause);
    }

    public SatoshiException(String message, int statusCode, String errorType) {
        this(message, statusCode, errorType, null);
    }

    public SatoshiException(String message, int statusCode, String errorType, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorType = errorType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorType() {
        return errorType;
    }
}
