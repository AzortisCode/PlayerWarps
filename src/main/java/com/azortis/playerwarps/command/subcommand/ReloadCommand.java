package com.azortis.playerwarps.command.subcommand;

import com.azortis.playerwarps.PlayerWarps;
import com.azortis.playerwarps.command.Injected;
import com.azortis.playerwarps.impl.command.SubCommand;
import com.azortis.playerwarps.impl.command.executors.ISubCommandExecutor;
import com.azortis.playerwarps.impl.utils.Color;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends Injected implements ISubCommandExecutor {
    @Override
    public boolean onSubCommand(CommandSender commandSender, SubCommand subCommand, String label, String[] args) {
        if (commandSender.hasPermission("warp.admin")) {
            getPlayerWarps().getSettingsManager().reloadSettingsFile();
            getPlayerWarps().getInventoryManager().reload();
            commandSender.sendMessage(Color.color("&cThe plugin has been reloaded."));
            return true;
        }
        return false;
    }

    public ReloadCommand(PlayerWarps playerWarps) {
        super(playerWarps);
    }
}
