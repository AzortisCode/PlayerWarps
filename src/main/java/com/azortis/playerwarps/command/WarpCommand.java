package com.azortis.playerwarps.command;

import com.azortis.playerwarps.PlayerWarps;
import com.azortis.playerwarps.command.subcommand.AdminCommand;
import com.azortis.playerwarps.command.subcommand.ManageCommand;
import com.azortis.playerwarps.command.subcommand.ReloadCommand;
import com.azortis.playerwarps.impl.command.Command;
import com.azortis.playerwarps.impl.command.CommandInjector;
import com.azortis.playerwarps.impl.command.builders.CommandBuilder;
import com.azortis.playerwarps.impl.command.builders.SubCommandBuilder;
import com.azortis.playerwarps.impl.command.executors.ICommandExecutor;
import com.azortis.playerwarps.impl.command.executors.ITabCompleter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand extends Injected implements ICommandExecutor, ITabCompleter {
    public WarpCommand(PlayerWarps playerWarps) {
        super(playerWarps);
        Command command = new CommandBuilder()
                .setDescription("The main warp command.")
                .setExecutor(this)
                .setUsage("/warp (Argument | Player|Manage|Admin|)")
                .setName("warp")
                .setTabCompleter(this)
                .addAlias("warps")
                .setPlugin(playerWarps)
                .addSubCommands(
                        new SubCommandBuilder().setExecutor(new AdminCommand(playerWarps)).setName("admin"),
                        new SubCommandBuilder().setExecutor(new ManageCommand(playerWarps)).setName("manage"),
                        new SubCommandBuilder().setExecutor(new ReloadCommand(playerWarps)).setName("reload")
                ).build();
        CommandInjector.injectCommand("playerwarps", command, true);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            getPlayerWarps().getLogger().info("The console may not use this command.");
            return false;
        }
        ((Player) commandSender).openInventory(getPlayerWarps().getInventoryManager().getMainMenu().getInventory());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args, Location location) {
        List<String> possible = new ArrayList<>();
        if (!(sender instanceof Player)) return null;
        if (sender.hasPermission("warp.set") ||
                sender.hasPermission("warp.settings")) possible.add("manage");
        if (sender.hasPermission("warp.admin")){
            possible.add("admin");
            possible.add("reload");
        }
        if (args[0].length() > 0) return StringUtil.copyPartialMatches(args[0], possible, new ArrayList<>());
        return possible;
    }
}
