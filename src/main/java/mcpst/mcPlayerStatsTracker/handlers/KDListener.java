package mcpst.mcPlayerStatsTracker.handlers;

import mcpst.mcPlayerStatsTracker.McPlayerStatsTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class KDListener implements Listener {

    private HashMap<String, Integer> kills;
    private HashMap<String, Integer> deaths;

    public KDListener(McPlayerStatsTracker plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.kills = new HashMap<>();
        this.deaths = new HashMap<>();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player deadPlayer = event.getEntity();
        try {
            if (killer == null) {
                throw new EventException();
            }
            if (kills.containsKey(killer.getName())) {
                kills.merge(killer.getName(), 1, Integer::sum);
            } else {
                kills.put(killer.getName(), 1);
            }
        } catch (EventException ignored) {}

        if (deaths.containsKey(deadPlayer.getName())) {
            deaths.merge(deadPlayer.getName(), 1, Integer::sum);
        } else {
            deaths.put(deadPlayer.getName(), 1);
        }
        Bukkit.getLogger().info(kills.toString());
        Bukkit.getLogger().info(deaths.toString());
    }

}
