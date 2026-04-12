package io.mysticcraft.resources;

import io.mysticcraft.http.HttpMethod;
import io.mysticcraft.http.MysticCraftHttpClient;
import io.mysticcraft.models.ChatRequest;
import io.mysticcraft.models.ChatResponse;

import java.util.Arrays;
import java.util.List;

/**
 * Resource for interacting with the chat API.
 *
 * <pre>{@code
 * ChatResponse response = client.chat().sendMessage("Hello!");
 * System.out.println(response.getContent());
 * }</pre>
 */
public class ChatResource {

    private static final String CHAT_PATH = "/v1/chat/completions";

    private final MysticCraftHttpClient httpClient;

    public ChatResource(MysticCraftHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public ChatResponse sendMessage(String message) {
        ChatRequest request = ChatRequest.ofMessage(message);
        return create(request);
    }

    public ChatResponse sendMessage(String model, String message) {
        ChatRequest request = ChatRequest.builder()
                .model(model)
                .messages(Arrays.asList(ChatRequest.Message.user(message)))
                .build();
        return create(request);
    }

    public ChatResponse create(ChatRequest request) {
        return httpClient.request(HttpMethod.POST, CHAT_PATH, request, ChatResponse.class);
    }

    public ChatResponse create(List<ChatRequest.Message> messages) {
        ChatRequest request = ChatRequest.builder()
                .messages(messages)
                .build();
        return create(request);
    }
}
