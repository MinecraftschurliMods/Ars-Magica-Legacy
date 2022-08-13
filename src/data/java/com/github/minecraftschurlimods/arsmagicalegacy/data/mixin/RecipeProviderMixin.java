package com.github.minecraftschurlimods.arsmagicalegacy.data.mixin;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeProvider.class)
class RecipeProviderMixin {
    @Inject(method = "fenceBuilder", at = @At("HEAD"), cancellable = true)
    private static void fenceBuilder(ItemLike pFence, Ingredient pMaterial, CallbackInfoReturnable<RecipeBuilder> cir) {
        int i = pFence == Blocks.NETHER_BRICK_FENCE ? 6 : 3;
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(pFence, i).define('W', pMaterial).pattern("W#W").pattern("W#W");
        if (pFence == Blocks.NETHER_BRICK_FENCE) {
            Item item = Items.NETHER_BRICK;
            cir.setReturnValue(builder.define('#', item));
        } else {
            cir.setReturnValue(builder.define('#', Tags.Items.RODS_WOODEN));
        }
    }

    @Inject(method = "fenceGateBuilder", at = @At("HEAD"), cancellable = true)
    private static void fenceGateBuilder(ItemLike pFenceGate, Ingredient pMaterial, CallbackInfoReturnable<RecipeBuilder> cir) {
        cir.setReturnValue(ShapedRecipeBuilder.shaped(pFenceGate).define('#', Tags.Items.RODS_WOODEN).define('W', pMaterial).pattern("#W#").pattern("#W#"));
    }

    @Inject(method = "signBuilder", at = @At("HEAD"), cancellable = true)
    private static void signBuilder(ItemLike pSign, Ingredient pMaterial, CallbackInfoReturnable<RecipeBuilder> cir) {
        cir.setReturnValue(ShapedRecipeBuilder.shaped(pSign, 3).group("sign").define('#', pMaterial).define('X', Tags.Items.RODS_WOODEN).pattern("###").pattern("###").pattern(" X "));
    }

    @ModifyArg(method = "oreCooking", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/SimpleCookingRecipeBuilder;save(Ljava/util/function/Consumer;Ljava/lang/String;)V"))
    private static String oreCooking(String pName) {
        return ArsMagicaAPI.MOD_ID + ":" + pName;
    }
}
