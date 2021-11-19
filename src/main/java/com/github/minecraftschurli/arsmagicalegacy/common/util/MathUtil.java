package com.github.minecraftschurli.arsmagicalegacy.common.util;

import java.util.Random;

public class MathUtil {
    public static <T> T getRandom(T[] array, Random rand) {
        return array[rand.nextInt(array.length)];
    }

    public static <T> T getByTick(T[] array, int tick) {
        return array[tick % array.length];
    }
}
