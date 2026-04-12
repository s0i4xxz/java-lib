package io.mysticcraft;

import io.mysticcraft.client.MysticCraftClient;

/**
 * Convenience facade for quickly creating a {@link MysticCraftClient}.
 *
 * <pre>{@code
 * import io.mysticcraft.*;
 *
 * MysticCraftClient client = MysticCraft.client();
 * }</pre>
 */
public final class MysticCraft {

    private MysticCraft() {
        // utility class
    }

    /**
     * Create a client with default settings (no auth required).
     *
     * @return a configured {@link MysticCraftClient}
     */
    public static MysticCraftClient client() {
        return MysticCraftClient.builder().build();
    }

    /**
     * Create a client with a custom base URL.
     *
     * @param baseUrl the base URL of the API
     * @return a configured {@link MysticCraftClient}
     */
    public static MysticCraftClient client(String baseUrl) {
        return MysticCraftClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Returns a new builder for advanced configuration.
     *
     * @return a {@link MysticCraftClient.Builder}
     */
    public static MysticCraftClient.Builder builder() {
        return MysticCraftClient.builder();
    }
}
