package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Marks a class as a spell effect entity. Spell effect entities should always have an owner. This is mainly intended for spell parts that want to special-case spell entities for whatever reason.
 */
public interface ISpellEffectEntity extends OwnableEntity {
    @Nullable
    @Override
    default UUID getOwnerUUID() {
        return getOwner() instanceof Player player ? player.getUUID() : null;
    }
}
