package me.syncwrld.booter.dependency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import org.bukkit.plugin.Plugin;

import java.util.Queue;
import java.util.concurrent.*;

@AllArgsConstructor
@Getter
public class Dependencies {

  private Queue<CompletableFuture<Void>> downloadQueue;
  private Plugin plugin;
  private ScheduledExecutorService executor;

  public static Dependencies of(Plugin plugin) {
    return new Dependencies(
        new ConcurrentLinkedQueue<>(), plugin, Executors.newSingleThreadScheduledExecutor());
  }

  public void queue(Dependency dependency) {
    try {
      this.downloadQueue.add(dependency.download());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void queue(Dependency... dependencies) {
    for (Dependency dependency : dependencies) {
      this.queue(dependency);
    }
  }

  public void remove(Dependency dependency) {
    this.downloadQueue.remove(dependency.download());
  }

  public void runQueue() {
    CompletableFuture<Void> voidQueue = CompletableFuture.completedFuture(null);
    while (!this.downloadQueue.isEmpty()) {
      CompletableFuture<Void> poll = downloadQueue.poll();
      voidQueue = voidQueue.thenCompose(__ -> poll);
    }
    voidQueue.join();
  }

}
