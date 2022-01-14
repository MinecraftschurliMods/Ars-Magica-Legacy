package com.github.minecraftschurlimods.arsmagicalegacy.client;

/**
 * Ugly hack to prevent unwanted classloading.
 */
public final class DistProxy {
    public static void init() {
        ClientInit.init();
    }
}
