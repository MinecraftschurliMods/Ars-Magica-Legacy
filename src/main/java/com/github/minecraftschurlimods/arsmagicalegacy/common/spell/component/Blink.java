package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Blink extends AbstractComponent {
    public Blink() {
        super(SpellPartStats.RANGE);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION.get()) || target.getEntity() instanceof LivingEntity living && living.hasEffect(AMMobEffects.ASTRAL_DISTORTION.get()))
            return SpellCastResult.EFFECT_FAILED;
        Entity entity = target.getEntity();
        double oldX = entity.getX(), oldY = entity.getEyeY(), oldZ = entity.getZ();
        for (int range = Math.round(ArsMagicaAPI.get().getSpellHelper().getModifiedStat(5, SpellPartStats.RANGE, modifiers, spell, caster, target, index) * 4); range > 0; range--) {
            double x = oldX + entity.getLookAngle().x() * range;
            double y = oldY + entity.getLookAngle().y() * range;
            double z = oldZ + entity.getLookAngle().z() * range;
            if (y >= level.getMinBuildHeight() && y <= level.getMaxBuildHeight() - 1 && level.getBlockState(BlockPos.containing(x, y, z)).isAir() && level.getBlockState(BlockPos.containing(x, y + 1, z)).isAir()) {
                target.getEntity().moveTo(x, y, z);
                return SpellCastResult.SUCCESS;
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
