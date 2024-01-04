package me.syncwrld.boosters;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.experimental.Tolerate;
import lombok.experimental.UtilityClass;
import me.syncwrld.boosters.core.BaseBooster;
import me.syncwrld.boosters.exception.EngineAlreadySetException;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
@Data
public class BoosterModuleManager {

  private final BoosterLoader boosterLoader = Constants.BOOSTER_LOADER;
  private final Map<String, BaseBooster> temporaryBoostersMap = new HashMap<>();
  private static BoosterEngine engine;

  @Tolerate
  public static void setEngine(BoosterEngine boosterEngine) {
    if (engine != null) engine = boosterEngine;
    else throw new EngineAlreadySetException();
  }

  public static void insertInitialValues() {
  }

  public static void load() {
    if (engine == null) {
      return;
    }

    FileConfiguration config = engine.getConfig();

  }
}
