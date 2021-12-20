package com.github.minecraftschurli.arsmagicalegacy.client.renderer;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarViewBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.Collection;

public class AltarViewBER implements BlockEntityRenderer<AltarViewBlockEntity> {

    private final BlockEntityRenderDispatcher dispatcher;
    private final Font font;

    public AltarViewBER(BlockEntityRendererProvider.Context pContext) {
        this.dispatcher = pContext.getBlockEntityRenderDispatcher();
        this.font = pContext.getFont();
    }

    @Override
    public void render(AltarViewBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        blockEntity.itemRotation += 1;
        if (blockEntity.itemRotation == 360)
            blockEntity.itemRotation = 0;
        blockEntity.getAltar().filter(AltarCoreBlockEntity::isMultiblockFormed).ifPresent(altar -> {
            poseStack.pushPose();
            poseStack.translate(0.5, 0.5, 0.5);
            doRender(altar, blockEntity, poseStack, bufferSource, packedLight, packedOverlay);
            poseStack.popPose();
        });
    }

    private void doRender(AltarCoreBlockEntity altar, AltarViewBlockEntity view, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Collection<ISpellIngredient> recipe = altar.getRecipe();
        if (recipe == null || recipe.isEmpty()) return;
        poseStack.pushPose();
        if (altar.hasEnoughPower()) {
            ISpellIngredient ingredient = altar.getCurrentIngredient();
            if (ingredient == null) {
                poseStack.popPose();
                return;
            }
            drawNameplate(ingredient.getTooltip(), poseStack, bufferSource, packedLight);
        } else {
            drawNameplate(new TranslatableComponent(TranslationConstants.ALTAR_CORE_LOW_POWER), poseStack, bufferSource, packedLight);
        }
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(view.itemRotation));
        if (altar.hasEnoughPower()) {
            ISpellIngredient currentIngredient = altar.getCurrentIngredient();
            if (currentIngredient != null) {
                ArsMagicaAPI.get()
                            .getSpellDataManager()
                            .getSpellIngredientRenderer(currentIngredient.getType())
                            .renderInWorld(currentIngredient, poseStack, bufferSource, packedLight, packedOverlay);
            }
        } else {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ItemStack stack = new ItemStack(Blocks.BARRIER);
            BakedModel model = itemRenderer.getModel(stack, view.getLevel(), null, 0);
            itemRenderer.render(stack, ItemTransforms.TransformType.GROUND, false, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY, model);
        }
        poseStack.popPose();
    }

    private void drawNameplate(Component component, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.9, 0);
        poseStack.mulPose(dispatcher.camera.rotation());
        poseStack.scale(-0.025F, -0.025F, 0.025F);
        poseStack.scale(1.2f, 1.2f, 1.2f);
        float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int) (f1 * 255) << 24;
        float f2 = (float) (-font.width(component) / 2);
        font.drawInBatch(component, f2, 0, 0xbbffffff, false, poseStack.last().pose(), bufferSource, false, j, packedLight);
        poseStack.popPose();
    }
}
