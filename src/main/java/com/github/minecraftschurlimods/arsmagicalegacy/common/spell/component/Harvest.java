package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Harvest extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        BlockPos pos = target.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof CropBlock) && target.getDirection() == Direction.UP) {
            pos = pos.above();
            state = level.getBlockState(pos);
        }
        if (state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state) && !level.isClientSide()) {
            List<ItemStack> drops = Block.getDrops(state, (ServerLevel) level, pos, level.getBlockEntity(pos));
            if (drops.stream().anyMatch(e -> e.getItem() == crop.asItem())) {
                level.setBlock(pos, level.getBlockState(pos).setValue(CropBlock.AGE, 0), Block.UPDATE_ALL);
                for (ItemStack stack : drops) {
                    int count = stack.getItem() == crop.asItem() ? stack.getCount() - 1 : stack.getCount();
                    if (count > 0) {
                        level.addFreshEntity(new ItemEntity(level, target.getLocation().x(), target.getLocation().y(), target.getLocation().z(), new ItemStack(stack.getItem(), count)));
                    }
                }
            } else {
                level.destroyBlock(pos, true, caster);
            }
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
