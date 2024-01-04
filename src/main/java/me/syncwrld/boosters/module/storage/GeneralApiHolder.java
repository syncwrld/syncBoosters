package me.syncwrld.boosters.module.storage;

import br.com.ystoreplugins.product.economy.manager.StoreRegistered;
import br.com.ystoreplugins.product.ymaquinas.MaquinaAPIHolder;
import br.com.ystoreplugins.product.ynewbosses.BossAPIHolder;
import br.com.ystoreplugins.product.ypesca.PescaAPIHolder;
import br.com.ystoreplugins.product.yplantacoes.PlantacaoAPIHolder;
import br.com.ystoreplugins.product.yspawnersv2.SpawnerV2APIHolder;
import lombok.SneakyThrows;
import me.syncwrld.boosters.exception.HolderAlreadyLoadedException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class GeneralApiHolder {

  public static SpawnerV2APIHolder YSPAWNERS_V2 = null;
  public static BossAPIHolder YNEWBOSSES = null;
  public static PlantacaoAPIHolder YPLANTACOES = StoreRegistered.getPlantacaoAPI();
  public static MaquinaAPIHolder YMAQUINAS = StoreRegistered.getMaquinaAPI();
  public static PescaAPIHolder YPESCA = StoreRegistered.getPescaAPI();

  public static void createNonStoredInstances() {
    createYSpawnersV2();
    createYNewBosses();
  }

  @SneakyThrows
  static void createYSpawnersV2() {
    if (YSPAWNERS_V2 != null) {
      throw new HolderAlreadyLoadedException("ySpawnersV2");
    }
    YSPAWNERS_V2 = get(SpawnerV2APIHolder.class);
  }

  @SneakyThrows
  static void createYNewBosses() {
    if (YNEWBOSSES != null) {
      throw new HolderAlreadyLoadedException("yNewBosses");
    }
    YNEWBOSSES = get(BossAPIHolder.class);
  }

  static <T> T get(Class<T> clazz) {
    RegisteredServiceProvider<T> serviceProvider =
        Bukkit.getServicesManager().getRegistration(clazz);
    if (serviceProvider == null) {
      return null;
    }
    return (T) serviceProvider.getProvider();
  }

}
