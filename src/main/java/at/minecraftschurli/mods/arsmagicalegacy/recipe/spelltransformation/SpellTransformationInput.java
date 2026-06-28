package at.minecraftschurli.mods.arsmagicalegacy.recipe.spelltransformation;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;

public record SpellTransformationInput(BlockState state, Holder<SpellPart> spellPart) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
