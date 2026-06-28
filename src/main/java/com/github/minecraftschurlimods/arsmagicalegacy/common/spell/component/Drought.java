package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRecipes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.github.minecraftschurlimods.arsmagicalegacy.common.recipe.spelltransformation.SpellTransformationInput;
import com.github.minecraftschurlimods.arsmagicalegacy.common.recipe.spelltransformation.SpellTransformationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;
import java.util.Optional;

public class Drought extends SpellComponent.CastBlock {
    @Override
    public SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult) {
        Spell spell = context.spell();
        Level level = context.level();
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        BlockPos normalPos = pos.offset(hitResult.getDirection().getUnitVec3i());
        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.WATERLOGGED, false));
            return SpellComponentCastResult.success(spell);
        }
        if (!(level instanceof ServerLevel serverLevel)) return SpellComponentCastResult.pass(spell);
        Optional<RecipeHolder<SpellTransformationRecipe>> optional = serverLevel.recipeAccess().getRecipeFor(AMRecipes.SPELL_TRANSFORMATION_TYPE.get(), new SpellTransformationInput(state, AMSpells.DROUGHT), level);
        if (optional.isPresent()) {
            level.setBlockAndUpdate(pos, optional.get().value().result());
        } else if (level.getBlockState(normalPos).is(Blocks.WATER)) {
            level.setBlockAndUpdate(normalPos, Blocks.AIR.defaultBlockState());
        }
        return SpellComponentCastResult.success(spell);
    }
}
