package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import net.minecraft.resources.ResourceLocation;

public enum SpellPartStats implements ISpellPartStat {
    SPEED,
    SIZE,
    DURATION,
    POWER,
    DAMAGE,
    HEALING,
    RANGE,
    PIERCING,
    COLOR,
    GRAVITY,
    BOUNCE,
    TARGET_NON_SOLID,
    MINING_TIER,
    FORTUNE,
    SILKTOUCH,
    HOMING;

    private final ResourceLocation id;

    SpellPartStats() {
        this.id = new ResourceLocation(ArsMagicaAPI.MOD_ID, name().toLowerCase());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }
}
