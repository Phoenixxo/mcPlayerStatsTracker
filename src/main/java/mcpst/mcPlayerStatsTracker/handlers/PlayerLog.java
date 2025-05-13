package mcpst.mcPlayerStatsTracker.handlers;

import mcpst.mcPlayerStatsTracker.McPlayerStatsTracker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.HashMap;

public class PlayerLog implements Listener {

    static HashMap<String, Integer> playerLog = new HashMap<>();

    public PlayerLog(McPlayerStatsTracker plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String name = event.getPlayer().getName();
        if (!playerLog.containsKey(name)) {
            playerLog.put(name, 1);
        } else {
            playerLog.merge(name, 1, Integer::sum);
        }
        Bukkit.getLogger().info(name + ": " + playerLog.get(name) + " login(s).");
    }
}
