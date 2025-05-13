package mcpst.mcPlayerStatsTracker.handlers;

import mcpst.mcPlayerStatsTracker.McPlayerStatsTracker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerLog implements Listener {

    static HashMap<String, Integer> playerLogins = new HashMap<>();
    static HashMap<String, Integer> playerLogouts = new HashMap<>();

    public PlayerLog(McPlayerStatsTracker plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String name = event.getPlayer().getName();
        if (!playerLogins.containsKey(name)) {
            playerLogins.put(name, 1);
        } else {
            playerLogins.merge(name, 1, Integer::sum);
        }
        Bukkit.getLogger().info(name + ": " + playerLogins.get(name) + " login(s).");
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        String name = event.getPlayer().getName();
        if (!playerLogouts.containsKey(name)) {
            playerLogouts.put(name, 1);
        } else {
            playerLogouts.merge(name, 1, Integer::sum);
        }
        Bukkit.getLogger().info(name + ": " + playerLogouts.get(name) + " logout(s).");
    }
}
