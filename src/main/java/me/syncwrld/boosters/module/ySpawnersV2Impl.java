package me.syncwrld.boosters.module;

import br.com.ystoreplugins.product.yspawnersv2.SpawnerV2APIHolder;
import br.com.ystoreplugins.product.yspawnersv2.event.SpawnerV2KillEvent;
import lombok.extern.java.Log;
import me.syncwrld.boosters.core.TemporaryBooster;
import me.syncwrld.boosters.module.storage.GeneralApiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

@Log
public class ySpawnersV2Impl implements TemporaryBooster, Listener {

  private final SpawnerV2APIHolder apiHolder = GeneralApiHolder.YSPAWNERS_V2;

  @Override
  public void activate(Player player, long time, double multiplier) {
  }

  @Override
  public void expire(Player player) {

  }

  @Override
  public boolean isActive(Player player) {
    return false;
  }

  @Override
  public long getRemainingTime(Player player) {
    return 0;
  }

  public void tryCapture(SpawnerV2KillEvent event) {
    double drops = event.getDrops();
  }

  @Override
  public String getName() {
    return "Booster de Drops - ySpawnersV2";
  }

  @Override
  public String getNativeVersion() {
    return "1.8.8";
  }

  @Override
  public String getAuthor() {
    return "syncwrld";
  }

  @Override
  public String getDescription() {
    return "Multiplica os drops dos spawners do ySpawnersV2";
  }

  @Override
  public String getRequiredPlugins() {
    return "yPlugins, ySpawnersV2";
  }
}
