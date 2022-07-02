package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Ignition extends AbstractComponent {
    public Ignition() {
        super(SpellPartStats.DURATION);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        if (target.getEntity() instanceof Creeper creeper && !creeper.isIgnited()) {
            creeper.ignite();
            return SpellCastResult.SUCCESS;
        }
        if (target.getEntity().isOnFire()) return SpellCastResult.EFFECT_FAILED;
        target.getEntity().setSecondsOnFire((int) ArsMagicaAPI.get().getSpellHelper().getModifiedStat(3, SpellPartStats.DURATION, modifiers, spell, caster, target));
        return SpellCastResult.SUCCESS;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        BlockPos pos = target.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof CampfireBlock && !state.getValue(CampfireBlock.LIT)) {
            level.setBlock(pos, state.setValue(CampfireBlock.LIT, true), Block.UPDATE_ALL);
            return SpellCastResult.SUCCESS;
        } else if (state.getBlock() instanceof CandleBlock && !state.getValue(CandleBlock.LIT)) {
            level.setBlock(pos, state.setValue(CandleBlock.LIT, true), Block.UPDATE_ALL);
            return SpellCastResult.SUCCESS;
        } else if (state.getBlock() instanceof CandleCakeBlock && !state.getValue(CandleCakeBlock.LIT)) {
            level.setBlock(pos, state.setValue(CandleCakeBlock.LIT, true), Block.UPDATE_ALL);
            return SpellCastResult.SUCCESS;
        } else {
            if (target.getDirection().getAxis() != Direction.Axis.Y) {
                pos = pos.offset(target.getDirection().getNormal());
            }
            if (state.isAir()) {
                if (!level.isClientSide()) {
                    level.setBlock(pos, Blocks.FIRE.defaultBlockState(), Block.UPDATE_ALL);
                }
                return SpellCastResult.SUCCESS;
            }
            if (level.getBlockState(pos.above()).isAir()) {
                if (!level.isClientSide()) {
                    level.setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), Block.UPDATE_ALL);
                }
                return SpellCastResult.SUCCESS;
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
