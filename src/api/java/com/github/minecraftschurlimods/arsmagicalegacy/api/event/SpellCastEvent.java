package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Event that fires when a spell is cast.
 */
@Cancelable
public final class SpellCastEvent extends LivingEvent {
    /**
     * The spell being cast.
     */
    public final ISpell spell;

    /**
     * The mana cost for the spell. (can be modified)
     */
    public float mana;

    /**
     * The burnout for the spell. (can be modified)
     */
    public float burnout;

    /**
     * The reagents for the spell. (can be modified)
     */
    public List<Either<Ingredient, ItemStack>> reagents;

    public SpellCastEvent(LivingEntity caster, ISpell spell) {
        super(caster);
        this.spell = spell;
        mana = spell.manaCost(caster);
        burnout = spell.burnout();
        reagents = new ArrayList<>(spell.reagents());
    }
}
