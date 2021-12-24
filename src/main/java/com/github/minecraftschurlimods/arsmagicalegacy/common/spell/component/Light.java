package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class Light extends Effect {
    public Light() {
        super(AMMobEffects.ILLUMINATION);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        Direction direction = target.getDirection();
        BlockPos pos = target.getBlockPos().offset(direction.getStepX(), direction.getStepY(), direction.getStepZ());
        if (level.getBlockState(pos).isAir()) {
            level.setBlock(pos, AMBlocks.SPELL_LIGHT.get().defaultBlockState(), 3);
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
