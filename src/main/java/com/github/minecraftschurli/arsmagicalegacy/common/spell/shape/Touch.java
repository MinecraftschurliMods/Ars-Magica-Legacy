package com.github.minecraftschurli.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class Touch extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, HitResult hit, int ticksUsed, int index, boolean awardXp) {
        return ArsMagicaAPI.get().getSpellHelper().invoke(spell, caster, level, ProjectileUtil.getHitResult(caster, entity -> !entity.isSpectator()), ticksUsed, index, awardXp);
    }
}
