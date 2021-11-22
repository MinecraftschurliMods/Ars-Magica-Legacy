package com.github.minecraftschurli.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.util.Lazy;

import java.util.List;
import java.util.function.Supplier;

public class Effect extends AbstractComponent {
    private final Lazy<? extends MobEffect> effect;
    private final int duration;

    public Effect(Supplier<? extends MobEffect> effect, int duration) {
        this.effect = Lazy.concurrentOf(effect);
        this.duration = duration;
    }

    public Effect(MobEffect effect, int duration) {
        this(() -> effect, duration);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (!(target.getEntity() instanceof LivingEntity living)) return SpellCastResult.EFFECT_FAILED;
        MobEffectInstance instance = new MobEffectInstance(effect.get(), duration);
        // TODO modifier
        return living.addEffect(instance) ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
