package com.github.minecraftschurlimods.arsmagicalegacy.common.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class SimpleEtheriumProvider implements IEtheriumProvider {
    private final EtheriumType type;
    private final int maxValue;
    private int etheriumValue;
    private ConsumeCallback callback;

    public SimpleEtheriumProvider(EtheriumType type, int maxValue) {
        this.type = type;
        this.maxValue = maxValue;
        etheriumValue = 0;
    }

    @Override
    public EtheriumType getType() {
        return type;
    }

    @Override
    public int getMax() {
        return maxValue;
    }

    @Override
    public int consume(Level level, BlockPos consumerPos, int amount) {
        int min = Math.min(etheriumValue, amount);
        etheriumValue -= min;
        if (callback != null) {
            callback.onConsume(level, consumerPos, amount);
        }
        return min;
    }

    @Override
    public int getAmount() {
        return etheriumValue;
    }

    /**
     * Sets the given value for this etherium provider's storage.
     *
     * @param value The etherium to add.
     */
    public void set(int value) {
        etheriumValue = Mth.clamp(value, 0, maxValue);
    }

    /**
     * Adds the given value to this etherium provider's storage.
     *
     * @param value The etherium to add.
     */
    public void add(int value) {
        set(etheriumValue + value);
    }

    /**
     * Sets a consumer callback.
     *
     * @param callback The callback to set.
     * @return This provider, for chaining.
     */
    public SimpleEtheriumProvider setCallback(ConsumeCallback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * @param additional The etherium that would be added.
     * @return Whether this storage can store the given additional amount of etherium or not.
     */
    public boolean canStore(int additional) {
        return etheriumValue + additional <= maxValue;
    }

    @FunctionalInterface
    public interface ConsumeCallback {
        void onConsume(Level level, BlockPos consumerPos, int amount);
    }
}
