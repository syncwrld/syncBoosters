package me.syncwrld.boosters.core.form;

import me.syncwrld.boosters.Constants;

public interface Serializable {
    default String getAsJson() {
        return Constants.GSON.toJson(this);
    }
}
