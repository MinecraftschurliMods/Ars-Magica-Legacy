package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.item;

import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.item.SpellItemHandModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SpellItemRenderProperties extends BlockEntityWithoutLevelRenderer implements IClientItemExtensions {
    public SpellItemRenderProperties() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return this;
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pStack.is(AMItems.SPELL_BOOK.get())) {
            pStack = SpellBookItem.getSelectedSpell(pStack);
        }
        if (!pStack.is(AMItems.SPELL.get())) return;
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        // Pops off the transformation applied by ItemRenderer before calling this, thanks to XFactHD#5288 from the Forge Discord for the fix!
        pPoseStack.popPose();
        pPoseStack.pushPose();
        BakedModel model = renderer.getModel(pStack, ClientHelper.getLocalLevel(), ClientHelper.getLocalPlayer(), 0).applyTransform(pTransformType, pPoseStack, pTransformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || pTransformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
        pPoseStack.translate(-0.5, -0.5, -0.5);
        if (model instanceof SpellItemHandModel spellItemHandModel) {
            model = spellItemHandModel.originalModel;
        }
        renderer.renderModelLists(model, pStack, pPackedLight, pPackedOverlay, pPoseStack, ItemRenderer.getFoilBufferDirect(pBuffer, ItemBlockRenderTypes.getRenderType(pStack, true), true, pStack.hasFoil()));
    }
}
