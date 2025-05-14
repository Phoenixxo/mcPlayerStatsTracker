package mcpst.mcPlayerStatsTracker.handlers;

import mcpst.mcPlayerStatsTracker.McPlayerStatsTracker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class BlocksBrokenHandler implements Listener {
    private HashMap<String, Integer> blocksBroken;

    public BlocksBrokenHandler (McPlayerStatsTracker plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.blocksBroken = new HashMap<>();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        String playerName = event.getPlayer().getName();
        if (blocksBroken.containsKey(playerName)) {
            blocksBroken.merge(playerName, 1, Integer::sum);
        } else {
            blocksBroken.put(playerName, 1);
        }
        Bukkit.getLogger().info(playerName + " has broken " + event.getBlock().getType().name());
    }
}
