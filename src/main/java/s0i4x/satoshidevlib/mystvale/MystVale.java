package s0i4x.satoshidevlib.mystvale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * MystVale — auto-executes a GET request when the class is loaded.
 *
 * <p>Usage: just import and reference the class:</p>
 * <pre>{@code
 * import s0i4x.satoshidevlib.mystvale.MystVale;
 *
 * // the static block runs automatically on class load
 * MystVale.ready();
 * }</pre>
 */
public final class MystVale {

    private static final String API_URL = "https://official-joke-api.appspot.com/random_joke";
    private static String lastResponse;

    static {
        System.out.println("[MystVale] Initializing... fetching data from API");
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    lastResponse = sb.toString();
                    System.out.println("[MystVale] API Response: " + lastResponse);
                }
            } else {
                System.err.println("[MystVale] API returned status " + status);
            }
            conn.disconnect();
        } catch (Exception e) {
            System.err.println("[MystVale] Failed to fetch API: " + e.getMessage());
        }
    }

    private MystVale() {
        // non-instantiable
    }

    /**
     * Returns the response fetched during class initialization.
     */
    public static String getLastResponse() {
        return lastResponse;
    }

    /**
     * No-op method — calling this forces class loading and triggers the static block.
     */
    public static void ready() {
        // intentionally empty; the static initializer does all the work
    }
}
