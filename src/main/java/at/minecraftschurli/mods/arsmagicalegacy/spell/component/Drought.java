package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMRecipes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.recipe.spelltransformation.SpellTransformationInput;
import at.minecraftschurli.mods.arsmagicalegacy.recipe.spelltransformation.SpellTransformationRecipe;
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
