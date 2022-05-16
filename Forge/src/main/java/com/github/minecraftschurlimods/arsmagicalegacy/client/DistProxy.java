package com.github.minecraftschurlimods.arsmagicalegacy.client;

public final class DistProxy {
    /**
     * Runs client initialization. Needs a separate class to prevent unwanted classloading.
     */
    public static void init() {
        ClientInit.init();
    }
}
