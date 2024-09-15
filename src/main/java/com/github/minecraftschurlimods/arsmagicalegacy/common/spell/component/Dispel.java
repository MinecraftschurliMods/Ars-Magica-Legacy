package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Dispel extends AbstractComponent.OnEntity {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (target.getEntity() instanceof LivingEntity living) {
            List<Holder<MobEffect>> effects = new ArrayList<>();
            int left = 6;
            for (MobEffectInstance effect : living.getActiveEffects()) {
                int amplifier = effect.getAmplifier() + 1;
                if (left >= amplifier) {
                    left -= amplifier;
                    effects.add(effect.getEffect());
                }
            }
            for (Holder<MobEffect> effect : effects) {
                living.removeEffect(effect);
            }
            return effects.isEmpty() ? SpellCastResult.EFFECT_FAILED : SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
