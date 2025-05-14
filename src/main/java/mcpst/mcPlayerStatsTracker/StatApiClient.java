package mcpst.mcPlayerStatsTracker;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class StatApiClient {

    public static void send(String playerName, int kills, int deaths) {
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
}
