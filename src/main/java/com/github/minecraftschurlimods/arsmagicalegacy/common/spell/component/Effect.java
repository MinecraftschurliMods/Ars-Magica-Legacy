package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
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

    public Effect(Supplier<? extends MobEffect> effect) {
        this.effect = Lazy.concurrentOf(effect);
    }

    public Effect(MobEffect effect) {
        this(() -> effect);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (!(target.getEntity() instanceof LivingEntity living)) return SpellCastResult.EFFECT_FAILED;
        int duration = (int) (600 * (1 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()) * 0.5f));
        int amplifier = ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.EFFECT_POWER.getId());
        if (effect.get().isInstantenous()) {
            effect.get().applyInstantenousEffect(caster, caster, living, amplifier, 1);
            return SpellCastResult.SUCCESS;
        }
        MobEffectInstance instance = new MobEffectInstance(effect.get(), duration, amplifier);
        return living.addEffect(instance) ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
