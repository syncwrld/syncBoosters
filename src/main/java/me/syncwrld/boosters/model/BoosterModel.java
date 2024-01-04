package me.syncwrld.boosters.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.syncwrld.boosters.core.BaseBooster;
import me.syncwrld.boosters.core.form.Serializable;

@Data(staticConstructor = "create")
public class BoosterModel implements Serializable {
    @Expose @SerializedName("baseBooster")
    private final BaseBooster booster;
    @Expose @SerializedName("time")
    private final long time;
}
