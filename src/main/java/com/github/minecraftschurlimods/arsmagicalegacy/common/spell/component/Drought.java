package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drought extends AbstractComponent {
    //TODO
    private static final Map<Block, Block> TRANSITIONS = Util.make(new HashMap<>(), map -> {
        map.put(Blocks.STONE, Blocks.COBBLESTONE);
        map.put(Blocks.CLAY, Blocks.SAND);
        map.put(Blocks.GRAVEL, Blocks.SAND);
        map.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
        map.put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES);
        map.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        map.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        map.put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        map.put(Blocks.QUARTZ_BLOCK, Blocks.SMOOTH_QUARTZ);
        map.put(Blocks.RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE);
        map.put(Blocks.SANDSTONE, Blocks.SMOOTH_SANDSTONE);
    });

    public Drought() {
        super();
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        BlockPos pos = target.getBlockPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (Tags.Blocks.DIRT.contains(block)) {
            level.setBlock(pos, Blocks.SAND.defaultBlockState(), Block.UPDATE_ALL);
            return SpellCastResult.SUCCESS;
        } else if (BlockTags.FLOWERS.contains(block)) {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL);
            return SpellCastResult.SUCCESS;
        } else if (level.getBlockState(pos.offset(target.getDirection().getNormal())).getBlock() == Blocks.WATER) {
            level.setBlock(pos.offset(target.getDirection().getNormal()), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            return SpellCastResult.SUCCESS;
        } else if (TRANSITIONS.containsKey(block)) {
            level.setBlock(pos, TRANSITIONS.get(block).defaultBlockState(), Block.UPDATE_ALL);
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
