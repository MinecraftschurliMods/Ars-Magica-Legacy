package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Effect extends AbstractComponent {
    private final Holder<? extends MobEffect> effect;

    public Effect(Holder<? extends MobEffect> effect) {
        super(SpellPartStats.DURATION, SpellPartStats.POWER);
        this.effect = effect;
    }

    public Effect(MobEffect effect) {
        this(effect.builtInRegistryHolder());
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (!(target.getEntity() instanceof LivingEntity living)) return SpellCastResult.EFFECT_FAILED;
        var helper = ArsMagicaAPI.get().getSpellHelper();
        int amplifier = (int) helper.getModifiedStat(0, SpellPartStats.POWER, modifiers, spell, caster, target, index);
        if (effect.value().isInstantenous()) {
            effect.value().applyInstantenousEffect(caster, caster, living, amplifier, 1);
            return SpellCastResult.SUCCESS;
        }
        MobEffectInstance instance = new MobEffectInstance(effect.value(), (int) helper.getModifiedStat(Config.SERVER.DURATION.get(), SpellPartStats.DURATION, modifiers, spell, caster, target, index), amplifier);
        return living.addEffect(instance) ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
