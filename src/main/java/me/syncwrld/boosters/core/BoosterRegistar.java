package me.syncwrld.boosters.core;

import me.syncwrld.boosters.Constants;

public interface BoosterRegistar {
    default void register(BaseBooster booster) {
        Constants.BOOSTER_LOADER.boosters().add(booster);
    }
    default void unregister(BaseBooster booster) {
        Constants.BOOSTER_LOADER.boosters().remove(booster);
    }
}
