package com.github.minecraftschurli.arsmagicalegacy.common.spell.modifier;

import net.minecraft.world.item.Tier;

public class MiningPower extends AbstractModifier {
    private final Tier tier;

    public MiningPower(Tier tier) {
        this.tier = tier;
    }

    public Tier getTier() {
        return this.tier;
    }
}
