package com.github.minecraftschurli.arsmagicalegacy.api.client;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellIngredient;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

/**
 * Renderer for spell ingredients
 */
public interface ISpellIngredientRenderer<T extends ISpellIngredient> {
    void render(T ingredient, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay);
}