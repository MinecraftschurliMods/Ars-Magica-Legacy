package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SpellIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
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
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SpellItemModel extends BakedModelWrapper<BakedModel> {
    private static final RenderType SPELL_ICON = RenderType.itemEntityTranslucentCull(SpellIconAtlas.SPELL_ICON_ATLAS);
    private static final RenderType SPELL_ICON_FAB = RenderType.entityTranslucentCull(SpellIconAtlas.SPELL_ICON_ATLAS);
    private Optional<ResourceLocation> icon;
    private IAffinity affinity;
    private final ItemOverrides overrides = new ItemOverrides() {
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            icon = SpellItem.getSpellIcon(stack);
            if (!stack.isEmpty()) {
                affinity = SpellItem.getSpell(stack).primaryAffinity();
            }
            return SpellItemModel.this;
        }
    };

    public SpellItemModel(BakedModel originalModel) {
        super(originalModel);
    }

    static boolean isHand(ItemTransforms.TransformType cameraTransformType) {
        return cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || cameraTransformType.firstPerson();
    }

    static BakedModel getPerspectiveModel(ResourceLocation modelLocation, ItemTransforms.TransformType cameraTransformType, PoseStack poseStack) {
        return new SpellHandModel(Minecraft.getInstance().getModelManager().getModel(modelLocation));
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
    public boolean doesHandlePerspectives() {
        return true;
    }

    @Override
    public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack) {
        LocalPlayer player = ClientHelper.getLocalPlayer();
        if (player == null || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) {
            return getPerspectiveModel(new ModelResourceLocation(AMItems.SPELL_PARCHMENT.getId(), "inventory"), cameraTransformType, poseStack);
        }
        if (isHand(cameraTransformType) && affinity != null) {
            return getPerspectiveModel(new ResourceLocation(affinity.getId().getNamespace(), "item/spell_" + affinity.getId().getPath()), cameraTransformType, poseStack);
        }
        if (icon.isEmpty() || cameraTransformType != ItemTransforms.TransformType.GUI) {
            return getPerspectiveModel(new ModelResourceLocation(AMItems.SPELL_PARCHMENT.getId(), "inventory"), cameraTransformType, poseStack);
        }
        super.handlePerspective(cameraTransformType, poseStack);
        return new SpellIconModel(this, cameraTransformType, icon.get());
    }

    @SuppressWarnings("deprecation")
    public static class SpellHandModel extends SimpleBakedModel {
        public final BakedModel originalModel;

        public SpellHandModel(BakedModel originalModel) {
            super(originalModel.getQuads(null, null, new Random(42L)), directionMap(originalModel), false, false, originalModel.isGui3d(), originalModel.getParticleIcon(), ItemTransforms.NO_TRANSFORMS, originalModel.getOverrides());
            this.originalModel = originalModel;
        }

        @Override
        public boolean isCustomRenderer() {
            return true;
        }

        private static Map<Direction, List<BakedQuad>> directionMap(BakedModel model) {
            Map<Direction, List<BakedQuad>> map = Maps.newEnumMap(Direction.class);
            Random r = new Random();
            for (Direction d : Direction.values()) {
                r.setSeed(42L);
                map.put(d, model.getQuads(null, d, r));
            }
            return map;
        }
    }

    private static class SpellIconModel extends BakedModelWrapper<BakedModel> {
        private final Cache<ResourceLocation, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().maximumSize(5).build();
        private final ItemTransforms.TransformType cameraTransformType;
        private final ResourceLocation icon;

        public SpellIconModel(BakedModel originalModel, ItemTransforms.TransformType cameraTransformType, ResourceLocation icon) {
            super(originalModel);
            this.cameraTransformType = cameraTransformType;
            this.icon = icon;
        }

        @NotNull
        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
            if (cameraTransformType == ItemTransforms.TransformType.GUI) {
                try {
                    return CACHE.get(icon, () -> {
                        TextureAtlasSprite sprite = SpellIconAtlas.instance().getSprite(icon);
                        return List.of(new FaceBakery().bakeQuad(new Vector3f(0, 0, 8.504f), new Vector3f(16, 16, 8.504f), new BlockElementFace(null, 2, sprite.getName().toString(), new BlockFaceUV(new float[]{0, 0, 16, 16}, 0)), sprite, Direction.SOUTH, BlockModelRotation.X0_Y0, null, true, sprite.getName()));
                    });
                } catch (ExecutionException ignored) {
                }
            }
            return super.getQuads(state, side, rand);
        }

        @Override
        public boolean isCustomRenderer() {
            return false;
        }

        @Override
        public boolean isLayered() {
            return cameraTransformType == ItemTransforms.TransformType.GUI;
        }

        @Override
        public List<Pair<BakedModel, RenderType>> getLayerModels(ItemStack itemStack, boolean fabulous) {
            return Collections.singletonList(Pair.of(this, fabulous ? SPELL_ICON_FAB : SPELL_ICON));
        }
    }
}
