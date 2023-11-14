package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class RandomTeleport extends AbstractComponent {
    public RandomTeleport() {
        super(SpellPartStats.RANGE);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION.get()) || target.getEntity() instanceof LivingEntity living && living.hasEffect(AMMobEffects.ASTRAL_DISTORTION.get()))
            return SpellCastResult.EFFECT_FAILED;
        float range = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(5, SpellPartStats.RANGE, modifiers, spell, caster, target) * 4;
        Entity entity = target.getEntity();
        boolean validPosition;
        Vec3 vec;
        int i = 0;
        do {
            if (i == 100) return SpellCastResult.EFFECT_FAILED;
            vec = new Vec3(entity.getX() + level.getRandom().nextDouble() * range - range / 2f, entity.getY() + level.getRandom().nextDouble() * range - range / 2f, entity.getZ() + level.getRandom().nextDouble() * range - range / 2f);
            BlockPos pos = new BlockPos(vec);
            validPosition = level.getBlockState(pos).isAir() && level.getBlockState(pos.below()).canOcclude();
            i++;
        } while (!validPosition);
        entity.moveTo(vec);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
