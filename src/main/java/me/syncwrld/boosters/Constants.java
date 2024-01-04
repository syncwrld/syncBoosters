package me.syncwrld.boosters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

@UtilityClass
public class Constants {
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public static final BoosterLoader BOOSTER_LOADER = new BoosterLoader();
  public static final BoosterEngine ENGINE = JavaPlugin.getPlugin(BoosterEngine.class);
}
