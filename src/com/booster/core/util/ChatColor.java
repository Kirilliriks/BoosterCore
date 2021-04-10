package com.booster.core.util;

public enum ChatColor {
    Black("§0"),
    Navy("§01"),
    Green("§2"),
    Blue("§3"),
    Red("§4"),
    Purple("§5"),
    Gold("§6"),
    LightGray("§7"),
    Gray("§8"),
    DarkPurple("§9"),
    LightGreen("§a"),
    LightBlue("§b"),
    LightRed("§c"),
    LightPurple("§d"),
    Yellow("§e"),
    White("§f");

    private final String symbol;
    ChatColor(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
