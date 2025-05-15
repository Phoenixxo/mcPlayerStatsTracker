package mcpst.mcPlayerStatsTracker;

import org.bukkit.Bukkit;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class StatApiClient {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    // TODO: Remake method to log events (player logging, etc.)
    public static void logger(String playerName, int kills, int deaths) {
        try {
            URL url = new URL("http://localhost:8080/api/stats");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInput = String.format(
                    "{\"playerName\":\"%s\", \"kills\":%d, \"deaths\":%d}",
                    playerName, kills, deaths
            );

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonInput.getBytes("utf-8"));
            }

            int responseCode = connection.getResponseCode();
            System.out.println("API response code: " + responseCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void incrementBlockBreak(String playerName) {
        try {
            String encodedName = URLEncoder.encode(playerName, StandardCharsets.UTF_8);
            URI uri = new URI("http://localhost:8080/api/stats/block?playerName=" + encodedName);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                    .thenAccept(response -> {
                        int statusCode = response.statusCode();
                        if (statusCode != 200) {
                            Bukkit.getLogger().warning("[Blocks Broken Stat]: " + statusCode);
                        } else {
                            Bukkit.getLogger().info("[Blocks Broken Stat]: " + statusCode);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static void incrementKills(String playerName) {
            Bukkit.getLogger().info("[DEBUG] incrementKills called for: " + playerName);
            try {
                String encodedName = URLEncoder.encode(playerName, StandardCharsets.UTF_8);
                URI uri = new URI("http://localhost:8080/api/stats/kills?playerName=" + encodedName);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(uri)
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build();
                HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                        .thenAccept(response -> {
                            int code = response.statusCode();
                            Bukkit.getLogger().info("[Kill Stat] Response code: " + code);
                            if (code != 200) {
                                Bukkit.getLogger().warning("Kill stat update failed for " + playerName);
                            }
                                }
                        );

            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

    public static void incrementDeaths(String playerName) {
        try {
            String encodedName = URLEncoder.encode(playerName, StandardCharsets.UTF_8);
            URI uri = new URI("http://localhost:8080/api/stats/deaths?playerName=" + encodedName);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                    .thenAccept(response -> {
                                int statusCode = response.statusCode();
                                if (statusCode != 200) {
                                    Bukkit.getLogger().warning("Death Stat Error: " + statusCode);
                                } else {
                                    Bukkit.getLogger().info("Death Stat: " + statusCode);
                                }
                            }
                    );

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
