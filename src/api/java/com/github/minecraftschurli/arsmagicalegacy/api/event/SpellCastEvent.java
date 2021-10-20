package com.github.minecraftschurli.arsmagicalegacy.api.event;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

@Cancelable
public class SpellCastEvent extends Event {
    public final LivingEntity caster;
    public final Spell        spell;

    public float                               mana;
    public float                               burnout;
    public List<Either<Ingredient, ItemStack>> reagents;

    public SpellCastEvent(LivingEntity caster, Spell spell) {
        this.caster = caster;
        this.spell = spell;
        this.mana = spell.manaCost(caster);
        this.burnout = spell.burnout();
        this.reagents = new ArrayList<>(spell.reagents());
    }
}
