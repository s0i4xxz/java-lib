package io.mysticcraft.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mysticcraft.config.MysticCraftConfig;
import io.mysticcraft.http.MysticCraftHttpClient;
import io.mysticcraft.resources.ChatResource;

import java.time.Duration;

/**
 * Main entry point for the MysticCraft SDK.
 *
 * <pre>{@code
 * MysticCraftClient client = MysticCraftClient.builder()
 *     .build();
 *
 * ChatResponse response = client.chat().sendMessage("Hello!");
 * System.out.println(response.getContent());
 * }</pre>
 *
 * This class is thread-safe. A single instance can be shared across multiple threads.
 */
public final class MysticCraftClient {

    private final MysticCraftConfig config;
    private final MysticCraftHttpClient httpClient;

    // Lazily initialized resources — thread-safe via volatile + double-checked locking
    private volatile ChatResource chatResource;

    private MysticCraftClient(MysticCraftConfig config) {
        this.config = config;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.httpClient = new MysticCraftHttpClient(config, objectMapper);
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
    public MysticCraftConfig getConfig() {
        return config;
    }

    /**
     * Create a new builder for constructing a {@link MysticCraftClient}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link MysticCraftClient}.
     */
    public static final class Builder {
        private final MysticCraftConfig.Builder configBuilder = MysticCraftConfig.builder();

        private Builder() {
        }

        /**
         * Set an optional API key for authenticated endpoints.
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
         * Build the {@link MysticCraftClient}.
         */
        public MysticCraftClient build() {
            return new MysticCraftClient(configBuilder.build());
        }
    }
}
