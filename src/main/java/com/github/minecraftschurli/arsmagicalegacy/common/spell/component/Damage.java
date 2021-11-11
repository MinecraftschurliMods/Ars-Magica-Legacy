package com.github.minecraftschurli.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.util.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Function;

public class Damage extends AbstractComponent {
    private final Function<LivingEntity, DamageSource> damageSourceFunction;
    private final float damage;

    public Damage(Function<LivingEntity, DamageSource> damageSourceFunction, float damage) {
        this.damageSourceFunction = damageSourceFunction;
        this.damage = damage;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, Entity target, Vec3 targetPosition, int index, int ticksUsed) {
        float damage = this.damage;
        // TODO modifier
        return SpellUtil.castSucceeded(target.hurt(damageSourceFunction.apply(caster), damage));
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockPos target, Vec3 targetPosition, int index, int ticksUsed) {
        return SpellCastResult.FAIL;
    }
}
