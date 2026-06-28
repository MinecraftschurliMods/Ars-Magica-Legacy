package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

/// The base class for all events involving a spell.
@SuppressWarnings("unused")
public abstract class SpellEvent extends LivingEvent {
    private final Spell spell;

    public SpellEvent(LivingEntity entity, Spell spell) {
        super(entity);
        this.spell = spell;
    }

    /// @return The involved spell.
    public Spell getSpell() {
        return spell;
    }
}
