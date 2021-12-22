package com.github.minecraftschurli.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Self extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult target, int ticksUsed, int index, boolean awardXp) {
        return ArsMagicaAPI.get().getSpellHelper().invoke(spell, caster, level, new EntityHitResult(caster), ticksUsed, index, awardXp);
    }

    @Override
    public boolean needsPrecedingShape() {
        return false;
    }

    @Override
    public boolean needsToComeFirst() {
        return true;
    }
}
