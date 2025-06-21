package mcpst.mcPlayerStatsTracker.commands;

import mcpst.mcPlayerStatsTracker.StatApiClient;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class myStatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("This command can only be run by a player.");
            return true;
        }

        player.sendMessage("[DEBUG] You ran /mystats");

        StatApiClient.getStats(player.getName(), stats -> {
            player.sendMessage("§aYour Stats:");
            player.sendMessage("§fKills: §b" + stats.kills);
            player.sendMessage("§fDeaths: §b" + stats.deaths);
            player.sendMessage("§fBlocks Broken: §b" + stats.blocksBroken);
        });
        return true;
    }
}
