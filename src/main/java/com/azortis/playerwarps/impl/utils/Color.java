package com.azortis.playerwarps.impl.utils;

import org.bukkit.ChatColor;

public class Color {
    public static String[] color(String... s) {
        for (int i = 0; i < s.length; i++)
            s[i] = color(s[i]);
        return s;
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
