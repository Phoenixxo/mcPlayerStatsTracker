package mcpst.mcPlayerStatsTracker.handlers;

import mcpst.mcPlayerStatsTracker.McPlayerStatsTracker;
import mcpst.mcPlayerStatsTracker.StatApiClient;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class KDListener implements Listener {

    public KDListener(McPlayerStatsTracker plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player deadPlayer = event.getEntity();
            if (killer != null) {
                StatApiClient.incrementKills(killer.getName());
                StatApiClient.logEvent(killer.getName(), "Kill");
            }
            StatApiClient.incrementDeaths(deadPlayer.getName());
            StatApiClient.logEvent(deadPlayer.getName(), "Death");
    }

}
