package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class Drought extends AbstractComponent.OnBlock {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        BlockPos pos = target.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (level.getBlockState(pos.offset(target.getDirection().getNormal())).getBlock() == Blocks.WATER) {
            level.setBlock(pos.offset(target.getDirection().getNormal()), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            return SpellCastResult.SUCCESS;
        } else {
            Optional<BlockState> optional = ArsMagicaAPI.get().getSpellTransformationFor(state, level, getId());
            if (optional.isPresent()) {
                level.setBlock(pos, optional.get(), Block.UPDATE_ALL);
                return SpellCastResult.SUCCESS;
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
