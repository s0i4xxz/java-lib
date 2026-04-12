package s0i4x.satoshidevlib.resources;

import s0i4x.satoshidevlib.http.HttpMethod;
import s0i4x.satoshidevlib.http.SatoshiHttpClient;
import s0i4x.satoshidevlib.models.ChatRequest;
import s0i4x.satoshidevlib.models.ChatResponse;

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

    private final SatoshiHttpClient httpClient;

    public ChatResource(SatoshiHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Send a simple user message and get a response.
     *
     * @param message the user message text
     * @return the chat response
     */
    public ChatResponse sendMessage(String message) {
        ChatRequest request = ChatRequest.ofMessage(message);
        return create(request);
    }

    /**
     * Send a message with a specific model.
     *
     * @param model   the model identifier
     * @param message the user message text
     * @return the chat response
     */
    public ChatResponse sendMessage(String model, String message) {
        ChatRequest request = ChatRequest.builder()
                .model(model)
                .messages(Arrays.asList(ChatRequest.Message.user(message)))
                .build();
        return create(request);
    }

    /**
     * Send a fully-constructed chat request.
     *
     * @param request the chat request
     * @return the chat response
     */
    public ChatResponse create(ChatRequest request) {
        return httpClient.request(HttpMethod.POST, CHAT_PATH, request, ChatResponse.class);
    }

    /**
     * Send a multi-turn conversation.
     *
     * @param messages the conversation messages
     * @return the chat response
     */
    public ChatResponse create(List<ChatRequest.Message> messages) {
        ChatRequest request = ChatRequest.builder()
                .messages(messages)
                .build();
        return create(request);
    }
}
