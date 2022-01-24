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
        this.etheriumValue = 0;
        this.maxValue = maxValue;
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
        if (this.callback != null) {
            this.callback.onConsume(level, consumerPos, amount);
        }
        return min;
    }

    @Override
    public int getAmount() {
        return etheriumValue;
    }

    public void set(int value) {
        this.etheriumValue = Mth.clamp(value, 0, maxValue);
    }

    public void add(int value) {
        set(etheriumValue + value);
    }

    public SimpleEtheriumProvider setCallback(ConsumeCallback callback) {
        this.callback = callback;
        return this;
    }

    public boolean canStore(int additional) {
        return etheriumValue + additional <= maxValue;
    }

    @FunctionalInterface
    public interface ConsumeCallback {
        void onConsume(Level level, BlockPos consumerPos, int amount);
    }
}
