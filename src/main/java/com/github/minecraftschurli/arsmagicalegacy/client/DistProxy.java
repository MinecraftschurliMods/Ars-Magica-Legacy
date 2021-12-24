package com.github.minecraftschurli.arsmagicalegacy.client;

/**
 * Ugly hack to prevent unwanted classloading.
 */
public class DistProxy {
    public static void init() {
        ClientInit.init();
    }
}
