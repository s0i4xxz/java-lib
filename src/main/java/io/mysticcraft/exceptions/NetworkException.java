package io.mysticcraft.exceptions;

/**
 * Thrown when a network-level failure occurs (timeout, connection refused, etc.).
 */
public class NetworkException extends MysticCraftException {

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
