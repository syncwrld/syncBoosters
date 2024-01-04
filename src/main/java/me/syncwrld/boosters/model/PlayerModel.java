package me.syncwrld.boosters.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.syncwrld.boosters.Constants;
import me.syncwrld.boosters.core.form.Serializable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerModel implements Serializable {
  @Expose
  @SerializedName("player")
  private String player;

  @Expose
  @SerializedName("boosters")
  private BoosterModel[] boosters;

  public static PlayerModel fromJson(String json) {
    return Constants.GSON.fromJson(json, PlayerModel.class);
  }

  public Player getBukkitPlayer() {
    return Bukkit.getPlayer(player);
  }
}
