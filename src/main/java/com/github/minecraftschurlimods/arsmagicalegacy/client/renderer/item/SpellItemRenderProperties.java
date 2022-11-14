package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.item;

import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
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
        if (stack.getItem() instanceof SpellBookItem) {
            stack = SpellBookItem.getSelectedSpell(stack);
        }
        renderSpellInHandEffect(stack, transformType, poseStack, buffer, packedLight, packedOverlay);
    }

    private void renderSpellInHandEffect(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        // TODO render spell effect in hand
    }
}
