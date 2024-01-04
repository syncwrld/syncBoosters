package me.syncwrld.boosters.core;

import org.bukkit.entity.Player;

public interface CancelableBooster extends TemporaryBooster {
  public void cancel(Player player, boolean shouldRecharge);
  public boolean isCancelled(Player player);
}
