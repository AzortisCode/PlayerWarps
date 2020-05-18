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

import com.azortis.playerwarps.impl.command.executors.ISubCommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCommand {

    private final String name;
    private List<String> aliases;

    private Map<String, Alias> aliasesMap;
    private final Command parent;
    private final ISubCommandExecutor executor;

    public SubCommand(String name, List<String> aliases, Command parent, ISubCommandExecutor executor){
        this.name = name.toLowerCase();
        if(aliases != null){
            List<String> processedAliases = new ArrayList<>();
            for (String alias : aliases){
                if(!alias.contains("-f")){
                    processedAliases.add(alias.toLowerCase());
                    break;
                }
                if(this.aliasesMap == null)this.aliasesMap = new HashMap<>();
                Alias aliasFunction = new Alias(alias);
                processedAliases.add(aliasFunction.getAlias().toLowerCase());
                this.aliasesMap.put(aliasFunction.getAlias(), aliasFunction);
            }
            this.aliases = processedAliases;
        }
        this.parent = parent;
        this.executor = executor;
    }

    public boolean execute(CommandSender commandSender, String label, String[] args){
        return executor.onSubCommand(commandSender, this, label, args);
    }

    public String getName() {
        return name;
    }

    public boolean hasAliases(){
        return aliases != null;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public Map<String, Alias> getAliasesMap() {
        return aliasesMap;
    }

    public Alias getAlias(String alias){
        return aliasesMap.get(alias);
    }

    public Command getParent() {
        return parent;
    }

    public ISubCommandExecutor getExecutor() {
        return executor;
    }

}
