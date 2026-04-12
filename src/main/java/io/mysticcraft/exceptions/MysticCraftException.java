package io.mysticcraft.exceptions;

/**
 * Base exception for all MysticCraft errors.
 */
public class MysticCraftException extends RuntimeException {

    private final int statusCode;
    private final String errorType;

    public MysticCraftException(String message) {
        this(message, 0, null, null);
    }

    public MysticCraftException(String message, Throwable cause) {
        this(message, 0, null, cause);
    }

    public MysticCraftException(String message, int statusCode, String errorType) {
        this(message, statusCode, errorType, null);
    }

    public MysticCraftException(String message, int statusCode, String errorType, Throwable cause) {
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
