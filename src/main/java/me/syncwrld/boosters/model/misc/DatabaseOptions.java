package me.syncwrld.boosters.model.misc;

import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;

@Data
public class DatabaseOptions {

  private String tableName;
  private boolean useHikari;
  private boolean useSSL;

  public DatabaseOptions(ConfigurationSection section) {
    this.tableName = section.getString("table", "leaf_levels");
    this.useHikari = section.getBoolean("use-hikari", false);
    this.useSSL = section.getBoolean("use-ssl", false);
  }

  public DatabaseOptions() {}

  public static DatabaseOptions getDefault() {
    DatabaseOptions options = new DatabaseOptions();
    options.tableName = "leaf_levels";
    options.useHikari = false;
    options.useSSL = false;

    return options;
  }
}
