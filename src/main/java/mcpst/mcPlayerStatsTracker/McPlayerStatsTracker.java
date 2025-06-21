package mcpst.mcPlayerStatsTracker;

import mcpst.mcPlayerStatsTracker.commands.myStatsCommand;
import mcpst.mcPlayerStatsTracker.handlers.BlocksBrokenHandler;
import mcpst.mcPlayerStatsTracker.handlers.KDListener;
import mcpst.mcPlayerStatsTracker.handlers.PlayerLog;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class McPlayerStatsTracker extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Player Logger started.");
        Bukkit.getLogger().info("KD Listener started.");
        Bukkit.getLogger().info("Block Breaker started");
        new PlayerLog(this);
        new KDListener(this);
        new BlocksBrokenHandler(this);
        this.getCommand("mystats").setExecutor(new myStatsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Shutting down.");
    }
}
