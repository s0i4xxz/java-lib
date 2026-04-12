package s0i4x.satoshidevlib;

import s0i4x.satoshidevlib.client.SatoshiClient;

import java.time.Duration;

/**
 * Convenience facade for quickly creating a {@link SatoshiClient}.
 *
 * <pre>{@code
 * import s0i4x.satoshidevlib.*;
 *
 * SatoshiClient client = Satoshi.client("sk-your-api-key");
 * }</pre>
 */
public final class Satoshi {

    private Satoshi() {
        // utility class
    }

    /**
     * Create a client with just an API key using default settings.
     *
     * @param apiKey your API key
     * @return a configured {@link SatoshiClient}
     */
    public static SatoshiClient client(String apiKey) {
        return SatoshiClient.builder()
                .apiKey(apiKey)
                .build();
    }

    /**
     * Create a client with an API key and custom base URL.
     *
     * @param apiKey  your API key
     * @param baseUrl the base URL of the API
     * @return a configured {@link SatoshiClient}
     */
    public static SatoshiClient client(String apiKey, String baseUrl) {
        return SatoshiClient.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Returns a new builder for advanced configuration.
     *
     * @return a {@link SatoshiClient.Builder}
     */
    public static SatoshiClient.Builder builder() {
        return SatoshiClient.builder();
    }
}
