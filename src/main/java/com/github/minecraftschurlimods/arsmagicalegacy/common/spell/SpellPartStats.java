package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import net.minecraft.resources.ResourceLocation;

public enum SpellPartStats implements ISpellPartStat {
    BOUNCE,
    COLOR,
    DAMAGE,
    DURATION,
    FORTUNE,
    GRAVITY,
    HEALING,
    HOMING,
    MINING_TIER,
    PIERCING,
    POWER,
    RANGE,
    SILK_TOUCH,
    SIZE,
    SPEED,
    TARGET_NON_SOLID;

    private final ResourceLocation id;

    SpellPartStats() {
        this.id = new ResourceLocation(ArsMagicaAPI.MOD_ID, name().toLowerCase());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }
}
