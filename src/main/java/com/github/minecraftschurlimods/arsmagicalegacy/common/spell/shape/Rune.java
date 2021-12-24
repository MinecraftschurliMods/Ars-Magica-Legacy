package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Rune extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!(hit instanceof BlockHitResult bHit)) return SpellCastResult.EFFECT_FAILED;
        Direction direction = bHit.getDirection();
        BlockPos pos = bHit.getBlockPos().relative(direction);
        BlockState blockState = level.getBlockState(pos);
        if (!blockState.isAir()) return SpellCastResult.EFFECT_FAILED;
        level.setBlock(pos, AMBlocks.SPELL_RUNE.get().defaultBlockState().setValue(SpellRuneBlock.FACE, direction.getOpposite()), Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_CLIENTS);
        ((SpellRuneBlockEntity) level.getBlockEntity(pos)).setSpell(spell, caster, index, awardXp);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public boolean needsPrecedingShape() {
        return true;
    }

    @Override
    public boolean isEndShape() {
        return true;
    }
}
