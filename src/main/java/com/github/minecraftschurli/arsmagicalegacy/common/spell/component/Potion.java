package com.github.minecraftschurli.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.util.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Potion extends AbstractComponent {
    private final MobEffectInstance effect;

    public Potion(MobEffectInstance effect) {
        this.effect = effect;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, Entity target, Vec3 targetPosition, int index, int ticksUsed) {
        if (!(target instanceof LivingEntity)) return SpellCastResult.FAIL;
        MobEffectInstance effect = new MobEffectInstance(this.effect);
        // TODO modifier
        return SpellUtil.castSucceeded(((LivingEntity) target).addEffect(effect));
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockPos target, Vec3 targetPosition, int index, int ticksUsed) {
        return SpellCastResult.FAIL;
    }
}
