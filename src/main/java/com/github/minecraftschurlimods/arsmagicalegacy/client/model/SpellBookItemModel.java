package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

public class SpellBookItemModel extends BakedModelWrapper<BakedModel> {
    private ItemStack stack;
    private final ItemOverrides overrides = new ItemOverrides() {
        @Override
        public BakedModel resolve(BakedModel model, ItemStack pStack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            stack = SpellBookItem.getSelectedSpell(pStack);
            return super.resolve(model, pStack, level, entity, seed);
        }
    };

    public SpellBookItemModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack) {
        LocalPlayer player = ClientHelper.getLocalPlayer();
        if (player != null && ArsMagicaAPI.get().getMagicHelper().knowsMagic(player) && !stack.isEmpty() && SpellItemModel.isHand(cameraTransformType)) {
            ResourceLocation affinity = SpellItem.getSpell(stack).primaryAffinity().getId();
            return SpellItemModel.getPerspectiveModel(new ResourceLocation(affinity.getNamespace(), "item/" + AMItems.SPELL.getId().getPath() + "_" + affinity.getPath()), cameraTransformType, poseStack);
        }
        return Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(AMItems.SPELL_BOOK.getId().getNamespace(), "item/" + AMItems.SPELL_BOOK.getId().getPath() + "_handheld")).handlePerspective(cameraTransformType, poseStack);
    }

    @Override
    public boolean doesHandlePerspectives() {
        return true;
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }
}
