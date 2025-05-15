package mcpst.mcPlayerStatsTracker.handlers;

import mcpst.mcPlayerStatsTracker.McPlayerStatsTracker;
import mcpst.mcPlayerStatsTracker.StatApiClient;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;


/*
TODO:
    - Redo class to log times to database of logins and logouts
 */
public class PlayerLog implements Listener {

    public PlayerLog(McPlayerStatsTracker plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        StatApiClient.logEvent(event.getPlayer().getName(), "Login");
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        StatApiClient.logEvent(event.getPlayer().getName(), "Logout");
    }
}
