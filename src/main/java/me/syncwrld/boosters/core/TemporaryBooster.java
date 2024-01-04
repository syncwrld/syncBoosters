package me.syncwrld.boosters.core;

import org.bukkit.entity.Player;

public interface TemporaryBooster extends BaseBooster, BoosterRegistar {
    public void activate(Player player, long time, double multiplier);
    public void expire(Player player);
    public boolean isActive(Player player);
    public long getRemainingTime(Player player);
}
