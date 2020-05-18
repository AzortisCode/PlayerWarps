package com.azortis.playerwarps.command.subcommand;

import com.azortis.playerwarps.PlayerWarps;
import com.azortis.playerwarps.command.Injected;
import com.azortis.playerwarps.impl.command.SubCommand;
import com.azortis.playerwarps.impl.command.executors.ISubCommandExecutor;
import org.bukkit.command.CommandSender;

public class ManageCommand extends Injected implements ISubCommandExecutor {
    @Override
    public boolean onSubCommand(CommandSender commandSender, SubCommand subCommand, String label, String[] args) {
        return false;
    }

    public ManageCommand(PlayerWarps playerWarps) {
        super(playerWarps);
    }
}
