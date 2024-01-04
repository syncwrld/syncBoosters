package me.syncwrld.boosters.core;

import org.bukkit.entity.Player;

public interface PermanentBooster extends BaseBooster, BoosterRegistar {
    public void activate(Player player, double multiplier);
}
