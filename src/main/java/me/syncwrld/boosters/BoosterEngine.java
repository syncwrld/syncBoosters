package me.syncwrld.boosters;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import me.syncwrld.boosters.database.connector.DatabaseConnector;
import me.syncwrld.boosters.model.BoosterModel;
import me.syncwrld.boosters.module.storage.GeneralApiHolder;
import me.syncwrld.booter.loader.BukkitPlugin;
import org.bukkit.Bukkit;

@Data
@EqualsAndHashCode(callSuper = false)
public final class BoosterEngine extends BukkitPlugin {

  private DatabaseConnector databaseConnector;

  @Override
  protected void whenLoad() {
    this.saveConfig();
    this.databaseConnector =
        DatabaseConnector.withConfiguration(this.getConfig().getConfigurationSection("database"));
  }

  @Override
  protected void whenEnable() {
    this.connectDatabase();
    this.loadAllModules();
  }

  @Override
  protected void whenDisable() {}

  private void loadAllModules() {
    GeneralApiHolder.createNonStoredInstances();
  }

  @SneakyThrows
  private void connectDatabase() {
    if (!this.databaseConnector.isConnected()) {
      if (this.databaseConnector.connect()) {
        this.log(
            "&aSuccessfully connected to "
                + this.databaseConnector.getFormattedType()
                + " database!");
      } else {
        this.log(
            "&cFailed to connect to " + this.databaseConnector.getFormattedType() + " database!");
        Thread.sleep(6000L);
        Bukkit.shutdown();
      }
    }
  }
}
