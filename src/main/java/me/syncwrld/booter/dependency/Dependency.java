package me.syncwrld.booter.dependency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RequiredArgsConstructor
@Getter
public class Dependency {

  private final String name;
  private final String version;
  private final String downloadURL;

  public CompletableFuture<Void> download() {
    return CompletableFuture.runAsync(
        () -> {
          if (Bukkit.getPluginManager().getPlugin(name) != null) return;

          String $s = System.getProperty("file.separator");
          String pathName = System.getProperty("user.dir") + $s + "plugins";
          URL url = null;

          try {
            url = new URL(downloadURL);
          } catch (MalformedURLException e) {
            Bukkit.getConsoleSender()
                .sendMessage(
                    "§c[syncBooter] Failed to download dependency: "
                        + name
                        + " - Cause: Invalid Download URL");
            return;
          }

          try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            try {
              Files.copy(
                  inputStream, new File(pathName + $s + name + "-" + version + ".jar").toPath());
            } catch (IOException e) {
              Bukkit.getConsoleSender()
                  .sendMessage(
                      "§f[syncBooter] Unable to download dependency: "
                          + name
                          + " because it's already downloaded.");
              return;
            }

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(
                () -> {
                  File pluginFile = new File(pathName + $s + name + "-" + version + ".jar");

                  try {
                    Plugin loadedPlugin = Bukkit.getPluginManager().loadPlugin(pluginFile);
                    Bukkit.getPluginManager().enablePlugin(loadedPlugin);
                  } catch (Exception e) {
                    Bukkit.getConsoleSender()
                        .sendMessage(
                            "§c[syncBooter] Failed to start dependency: "
                                + name
                                + " - Cause: "
                                + e.getMessage()
                                + " | The server will restart soon!");
                  }
                },
                1,
                java.util.concurrent.TimeUnit.SECONDS);

            Bukkit.getConsoleSender()
                .sendMessage(
                    "§a[syncBooter] Downloaded dependency: " + name + " - Version: " + version);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }
}
