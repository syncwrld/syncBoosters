package me.syncwrld.boosters.exception;

import lombok.experimental.StandardException;

@StandardException
public class HolderAlreadyLoadedException extends Exception {
    public HolderAlreadyLoadedException(String holder) {
        super(holder + "Holder already loaded, can't be loaded again");
    }
}
