package com.azortis.playerwarps.command.subcommand;

import com.azortis.playerwarps.PlayerWarps;
import com.azortis.playerwarps.command.Injected;
import com.azortis.playerwarps.impl.command.SubCommand;
import com.azortis.playerwarps.impl.command.executors.ISubCommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminCommand extends Injected implements ISubCommandExecutor {
    public AdminCommand(PlayerWarps playerWarps) {
        super(playerWarps);
    }

    @Override
    public boolean onSubCommand(CommandSender commandSender, SubCommand subCommand, String label, String[] args) {
        return false;
    }
}
