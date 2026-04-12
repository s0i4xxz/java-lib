package s0i4x.satoshidevlib.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import s0i4x.satoshidevlib.config.SatoshiConfig;
import s0i4x.satoshidevlib.http.SatoshiHttpClient;
import s0i4x.satoshidevlib.resources.ChatResource;

import java.time.Duration;

/**
 * Main entry point for the SatoshiDevLib SDK.
 *
 * <pre>{@code
 * SatoshiClient client = SatoshiClient.builder()
 *     .apiKey("sk-your-api-key")
 *     .build();
 *
 * ChatResponse response = client.chat().sendMessage("Hello!");
 * System.out.println(response.getContent());
 * }</pre>
 *
 * This class is thread-safe. A single instance can be shared across multiple threads.
 */
public final class SatoshiClient {

    private final SatoshiConfig config;
    private final SatoshiHttpClient httpClient;

    // Lazily initialized resources — thread-safe via volatile + double-checked locking
    private volatile ChatResource chatResource;

    private SatoshiClient(SatoshiConfig config) {
        this.config = config;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.httpClient = new SatoshiHttpClient(config, objectMapper);
    }

    /**
     * Access the Chat resource.
     *
     * @return the {@link ChatResource} instance
     */
    public ChatResource chat() {
        if (chatResource == null) {
            synchronized (this) {
                if (chatResource == null) {
                    chatResource = new ChatResource(httpClient);
                }
            }
        }
        return chatResource;
    }

    /**
     * Returns the configuration used by this client.
     */
    public SatoshiConfig getConfig() {
        return config;
    }

    /**
     * Create a new builder for constructing a {@link SatoshiClient}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link SatoshiClient}.
     */
    public static final class Builder {
        private final SatoshiConfig.Builder configBuilder = SatoshiConfig.builder();

        private Builder() {
        }

        /**
         * Set the API key for authentication (required).
         */
        public Builder apiKey(String apiKey) {
            configBuilder.apiKey(apiKey);
            return this;
        }

        /**
         * Set a custom base URL for the API.
         */
        public Builder baseUrl(String baseUrl) {
            configBuilder.baseUrl(baseUrl);
            return this;
        }

        /**
         * Set the request timeout.
         */
        public Builder timeout(Duration timeout) {
            configBuilder.timeout(timeout);
            return this;
        }

        /**
         * Set the maximum number of retries on transient errors.
         */
        public Builder maxRetries(int maxRetries) {
            configBuilder.maxRetries(maxRetries);
            return this;
        }

        /**
         * Configure an HTTP proxy.
         */
        public Builder proxy(String host, int port) {
            configBuilder.proxy(host, port);
            return this;
        }

        /**
         * Enable or disable debug logging.
         */
        public Builder debugMode(boolean debugMode) {
            configBuilder.debugMode(debugMode);
            return this;
        }

        /**
         * Build the {@link SatoshiClient}.
         *
         * @throws NullPointerException if apiKey is not set
         */
        public SatoshiClient build() {
            return new SatoshiClient(configBuilder.build());
        }
    }
}
