package com.github.minecraftschurlimods.arsmagicalegacy.data.update120;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

/**
 *
 */
public class Update120RecipeProvider extends RecipeProvider {
    public Update120RecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        hangingSign(consumer, AMItems.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
    }
}
