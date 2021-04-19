package com.booster.core.util;

public enum ChatColor {
    BLACK("§0"),
    NAVY("§01"),
    GREEN("§2"),
    BLUE("§3"),
    RED("§4"),
    PURPLE("§5"),
    GOLD("§6"),
    LIGHT_GRAY("§7"),
    GRAY("§8"),
    DARK_PURPLE("§9"),
    LIGHT_GREEN("§a"),
    LIGHT_BLUE("§b"),
    LIGHT_RED("§c"),
    LIGHT_PURPLE("§d"),
    YELLOW("§e"),
    WHITE("§f");

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
