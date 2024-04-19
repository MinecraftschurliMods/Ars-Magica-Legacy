package com.github.minecraftschurlimods.arsmagicalegacy.data.mixin;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RecipeProvider.class)
class RecipeProviderMixin {
    @Redirect(method = "fenceBuilder", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/ShapedRecipeBuilder;define(Ljava/lang/Character;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/data/recipes/ShapedRecipeBuilder;"))
    private static ShapedRecipeBuilder fenceBuilder(ShapedRecipeBuilder instance, Character pSymbol, ItemLike pItem) {
        if (pItem == Items.STICK) return instance.define(pSymbol, Tags.Items.RODS_WOODEN);
        return instance.define(pSymbol, pItem);
    }

    @Redirect(method = "fenceGateBuilder", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/ShapedRecipeBuilder;define(Ljava/lang/Character;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/data/recipes/ShapedRecipeBuilder;"))
    private static ShapedRecipeBuilder fenceGateBuilder(ShapedRecipeBuilder instance, Character pSymbol, ItemLike pItem) {
        return instance.define(pSymbol, Tags.Items.RODS_WOODEN);
    }

    @Redirect(method = "signBuilder", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/ShapedRecipeBuilder;define(Ljava/lang/Character;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/data/recipes/ShapedRecipeBuilder;"))
    private static ShapedRecipeBuilder signBuilder(ShapedRecipeBuilder instance, Character pSymbol, ItemLike pItem) {
        return instance.define(pSymbol, Tags.Items.RODS_WOODEN);
    }

    @ModifyArg(method = "oreCooking", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/SimpleCookingRecipeBuilder;save(Lnet/minecraft/data/recipes/RecipeOutput;Ljava/lang/String;)V"))
    private static String oreCooking(String pName) {
        return ArsMagicaAPI.MOD_ID + ":" + pName;
    }

    @ModifyArg(method = "oneToOneConversionRecipe(Lnet/minecraft/data/recipes/RecipeOutput;Lnet/minecraft/world/level/ItemLike;Lnet/minecraft/world/level/ItemLike;Ljava/lang/String;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/ShapelessRecipeBuilder;save(Lnet/minecraft/data/recipes/RecipeOutput;Ljava/lang/String;)V"))
    private static String oneToOneConversionRecipe(String pName) {
        return ArsMagicaAPI.MOD_ID + ":" + pName;
    }
}
