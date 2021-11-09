package com.github.minecraftschurli.arsmagicalegacy.api.event;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Event that fires when a spell is cast.
 */
@Cancelable
public final class SpellCastEvent extends Event {
    /**
     * The caster of the spell.
     */
    public final LivingEntity caster;

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
        this.caster = caster;
        this.spell = spell;
        this.mana = spell.manaCost(caster);
        this.burnout = spell.burnout();
        this.reagents = new ArrayList<>(spell.reagents());
    }
}
