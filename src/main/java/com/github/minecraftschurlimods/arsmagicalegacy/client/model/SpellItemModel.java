package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SpellIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SpellItemModel extends BakedModelWrapper<BakedModel> {
    private static final RenderType SPELL_ICON = RenderType.itemEntityTranslucentCull(SpellIconAtlas.SPELL_ICON_ATLAS);
    private static final RenderType SPELL_ICON_FAB = RenderType.entityTranslucentCull(SpellIconAtlas.SPELL_ICON_ATLAS);

    private final Cache<ResourceLocation, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().maximumSize(5).build();
    private Optional<ResourceLocation> icon;
    private final ItemOverrides overrides = new ItemOverrides() {
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            icon = ISpellItem.getSpellIcon(stack);
            return super.resolve(model, stack, level, entity, seed);
        }
    };

    public SpellItemModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public BakedModel applyTransform(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        Player player = Minecraft.getInstance().player;
        if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player) && !isHand(cameraTransformType) || cameraTransformType == ItemTransforms.TransformType.GROUND || icon.isEmpty()) {
            return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(AMItems.SPELL_PARCHMENT.getId(), "inventory")).applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
        }
        super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
        return new TransformModel(cameraTransformType);
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    private static boolean isHand(ItemTransforms.TransformType cameraTransformType) {
        return cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;
    }

    private class TransformModel extends BakedModelWrapper<BakedModel> {
        private final ItemTransforms.TransformType cameraTransformType;

        public TransformModel(ItemTransforms.TransformType cameraTransformType) {
            super(SpellItemModel.this);
            this.cameraTransformType = cameraTransformType;
        }

        @NotNull
        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
            if (cameraTransformType == ItemTransforms.TransformType.GUI && icon.isPresent()) {
                ResourceLocation key = icon.get();
                try {
                    return CACHE.get(key, () -> {
                        TextureAtlasSprite sprite = SpellIconAtlas.instance().getSprite(key);
                        return List.of(genQuad(sprite));
                    });
                } catch (ExecutionException ignored) {
                }
            }
            return super.getQuads(state, side, rand);
        }

        private static BakedQuad genQuad(TextureAtlasSprite sprite) {
            return new FaceBakery().bakeQuad(
                    new Vector3f(0.0F, 0.0F, 8.504f), new Vector3f(16.0F, 16.0F, 8.504f),
                    new BlockElementFace(null, 2, sprite.getName().toString(), new BlockFaceUV(new float[]{ 0.0F, 0.0F, 16.0F, 16.0F }, 0)), sprite, Direction.SOUTH, BlockModelRotation.X0_Y0, null, true,
                    sprite.getName()
            );
        }

        @Override
        public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
            return List.of(fabulous ? SPELL_ICON_FAB : SPELL_ICON);
        }

        @Override
        public boolean isCustomRenderer() {
            return isHand(cameraTransformType) || super.isCustomRenderer();
        }

        @Override
        public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
            return List.of(this);
        }
    }
}
