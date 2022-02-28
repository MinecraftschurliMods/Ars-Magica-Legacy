package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

public class SpellItemRenderProperties extends BlockEntityWithoutLevelRenderer implements IItemRenderProperties {
    public SpellItemRenderProperties() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
        return this;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (transformType.firstPerson()) {
            VertexConsumer consumer = buffer.getBuffer(SpellRenderType.SPELL.apply(new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/item/spell_" + SpellItem.getSpell(stack).primaryAffinity().getId().getPath() + ".png")));
            PoseStack.Pose pose = poseStack.last();
            Matrix4f matrix = pose.pose();
            Matrix3f normal = pose.normal();
            consumer.vertex(matrix, 0, 1, 0).color(255, 255, 255, 255).uv(0, 0).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
            consumer.vertex(matrix, 0, 1, 1).color(255, 255, 255, 255).uv(0, 1).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
            consumer.vertex(matrix, 1, 1, 1).color(255, 255, 255, 255).uv(1, 1).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
            consumer.vertex(matrix, 1, 1, 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
        }
    }
}
