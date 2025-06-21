package mcpst.mcPlayerStatsTracker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mcpst.mcPlayerStatsTracker.models.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class StatApiClient {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();


    public static void getStats(String playerName, Consumer<PlayerStats> callback) {
        try {
            URI uri = new URI("http://localhost:8080/api/stats/" + playerName);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            JsonObject obj = JsonParser.parseString(response.body()).getAsJsonObject();
                            PlayerStats stats = new PlayerStats();
                            stats.kills = obj.get("kills").getAsInt();
                            stats.deaths = obj.get("deaths").getAsInt();
                            stats.blocksBroken = obj.get("blocksBroken").getAsInt();

                            Bukkit.getScheduler().runTask(JavaPlugin.getProvidingPlugin(StatApiClient.class), () -> {
                                callback.accept(stats);
                            });
                        } else {
                            Bukkit.getLogger().warning("Failed to fetch stats for " + playerName);
                          }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logEvent(String playerName, String eventType) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/logs"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            String.format("{\"playerName\":\"%s\",\"eventType\":\"%s\"}", playerName, eventType)
                    ))
                    .build();

            HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                    .thenAccept(response -> Bukkit.getLogger().info("[Logger] " + eventType + " for " + playerName + " logged. Response: " + response.statusCode()));

        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to log event: " + e.getMessage());
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
