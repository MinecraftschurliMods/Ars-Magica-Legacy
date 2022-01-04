package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Ignition extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (target.getEntity().isOnFire()) return SpellCastResult.EFFECT_FAILED;
        target.getEntity().setSecondsOnFire(3 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()));
        return SpellCastResult.SUCCESS;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        BlockPos pos = target.getBlockPos();
        if (target.getDirection().getAxis() != Direction.Axis.Y) {
            pos = pos.offset(target.getDirection().getNormal());
        }
        if (level.getBlockState(pos).isAir()) {
            if (!level.isClientSide()) {
                level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
            }
            return SpellCastResult.SUCCESS;
        }
        if (level.getBlockState(pos.above()).isAir()) {
            if (!level.isClientSide()) {
                level.setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), 3);
            }
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
