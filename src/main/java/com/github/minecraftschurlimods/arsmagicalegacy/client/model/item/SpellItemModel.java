package com.github.minecraftschurlimods.arsmagicalegacy.client.model.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SpellItemModel extends BakedModelWrapper<BakedModel> {
    private static final ModelResourceLocation SPELL_PARCHMENT = new ModelResourceLocation(AMItems.SPELL_PARCHMENT.getId(), "inventory");
    private Optional<ResourceLocation> icon;
    private Affinity affinity;
    private final ItemOverrides overrides = new ItemOverrides() {
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            var helper = ArsMagicaAPI.get().getSpellHelper();
            icon = helper.getSpellIcon(stack);
            affinity = stack.isEmpty() ? AMAffinities.NONE.get() : helper.getSpell(stack).primaryAffinity();
            return SpellItemModel.this;
        }
    };

    public SpellItemModel(BakedModel originalModel) {
        super(originalModel);
    }

    static boolean isHand(ItemTransforms.TransformType cameraTransformType) {
        return cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || cameraTransformType.firstPerson();
    }

    static BakedModel getModel(ResourceLocation modelLocation) {
        return Minecraft.getInstance().getModelManager().getModel(modelLocation);
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    @Override
    public BakedModel applyTransform(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        Player player = ClientHelper.getLocalPlayer();
        if (player == null || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) {
            return getModel(SPELL_PARCHMENT);
        }
        if (isHand(cameraTransformType) && affinity != null) {
            return new SpellItemHandModel(getModel(new ResourceLocation(affinity.getId().getNamespace(), "item/spell_" + affinity.getId().getPath()))).applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
        }
        if (icon.isEmpty() || cameraTransformType != ItemTransforms.TransformType.GUI) {
            return getModel(SPELL_PARCHMENT);
        }
        super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
        return new SpellItemIconModel(this, cameraTransformType, icon.get());
    }
}
