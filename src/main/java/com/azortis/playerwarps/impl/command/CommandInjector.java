/*
 * An open source utilities library used for Azortis plugins.
 *     Copyright (C) 2019  Azortis
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.azortis.playerwarps.impl.command;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CommandInjector {

    @SuppressWarnings("unchecked")
    public static void injectCommand(String fallBackPrefix, Command command, boolean override){
        try{
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());
            if(override && commandMap.getCommand(command.getName()) != null){
                org.bukkit.command.Command conflictCommand = commandMap.getCommand(command.getName());
                List<String> conflictCommandAliases = new ArrayList<>();
                assert conflictCommand != null;
                if(!conflictCommand.getAliases().isEmpty())
                    conflictCommandAliases.addAll(conflictCommand.getAliases());

                Field knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);
                Map<String, org.bukkit.command.Command> knownCommands = (Map<String, org.bukkit.command.Command>) knownCommandsField.get(commandMap);
                knownCommands.remove(conflictCommand.getName());
                if(!conflictCommandAliases.isEmpty()){
                    for (String alias : conflictCommandAliases){
                        knownCommands.remove(alias);
                    }
                }
                knownCommandsField.setAccessible(false);
            }
            commandMap.register(fallBackPrefix, command.getBukkitCommand());
            commandMapField.setAccessible(false);
        }catch (NoSuchFieldException | IllegalAccessException ex){
            ex.printStackTrace();
        }
    }

}
