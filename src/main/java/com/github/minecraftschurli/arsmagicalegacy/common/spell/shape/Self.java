package com.github.minecraftschurli.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Self extends AbstractShape {
    @Override
    public SpellCastResult invoke(Spell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable Entity targetEntity, @Nullable BlockPos targetBlock, Vec3 targetPos, int ticksUsed, int index, boolean awardXp) {
        return ArsMagicaAPI.get().getSpellHelper().invoke(spell, caster, level, caster, null, targetPos, ticksUsed, index, awardXp);
    }
}
