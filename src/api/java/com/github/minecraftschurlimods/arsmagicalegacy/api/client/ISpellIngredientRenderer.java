package com.github.minecraftschurlimods.arsmagicalegacy.api.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

/**
 * Renderer for spell ingredients
 */
public interface ISpellIngredientRenderer<T extends ISpellIngredient> {
    /**
     * Render the passed ingredient instance in world.
     *
     * @param ingredient the ingredient to render
     */
    void renderInWorld(T ingredient, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay);

    /**
     * Render the passed ingredient in a gui.
     *
     * @param ingredient the ingredient to render
     */
    void renderInGui(T ingredient, PoseStack poseStack, int x, int y, int mouseX, int mouseY);
}