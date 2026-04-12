/**
 * SatoshiDevLib — a production-ready Java SDK.
 *
 * <p>Quick start:</p>
 * <pre>{@code
 * import s0i4x.satoshidevlib.*;
 *
 * SatoshiClient client = SatoshiClient.builder()
 *     .apiKey("sk-your-api-key")
 *     .build();
 *
 * ChatResponse response = client.chat().sendMessage("Hello!");
 * System.out.println(response.getContent());
 * }</pre>
 */
package s0i4x.satoshidevlib;
