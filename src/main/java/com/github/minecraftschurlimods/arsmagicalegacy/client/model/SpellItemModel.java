package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SpellIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.ItemTextureQuadConverter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;

public class SpellItemModel extends BakedModelWrapper<BakedModel> {
    private static final RenderType SPELL_ICON = RenderType.itemEntityTranslucentCull(SpellIconAtlas.SPELL_ICON_ATLAS);
    private static final RenderType SPELL_ICON_FAB = RenderType.entityTranslucentCull(SpellIconAtlas.SPELL_ICON_ATLAS);

    private final Cache<ResourceLocation, List<BakedQuad>> CACHE = CacheBuilder.newBuilder().maximumSize(5).build();
    private final ItemOverrides overrides = new ItemOverrides() {
        @Nullable
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            icon = SpellItem.getSpellIcon(stack);
            return super.resolve(model, stack, level, entity, seed);
        }
    };

    private Optional<ResourceLocation> icon;
    private ItemTransforms.TransformType cameraTransformType;

    public SpellItemModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack) {
        this.cameraTransformType = cameraTransformType;
        Player player = Minecraft.getInstance().player;
        if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player) && !isHand() || cameraTransformType == ItemTransforms.TransformType.GROUND || this.icon.isEmpty()) {
            return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(AMItems.SPELL_PARCHMENT.getId(), "inventory")).handlePerspective(cameraTransformType, poseStack);
        }
        super.handlePerspective(cameraTransformType, poseStack);
        return this;
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
        return isHand() || super.isCustomRenderer();
    }

    private boolean isHand() {
        return this.cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || this.cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || this.cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || this.cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        if (this.cameraTransformType == ItemTransforms.TransformType.GUI && icon.isPresent()) {
            ResourceLocation key = icon.get();
            try {
                return CACHE.get(key, () -> {
                    TextureAtlasSprite sprite = SpellIconAtlas.instance().getSprite(key);
                    return List.of(ItemTextureQuadConverter.genQuad(Transformation.identity(), 0, 0, 16, 16, 8.504f / 16f, sprite, Direction.SOUTH, -1, 2));
                });
            } catch (ExecutionException ignored) {
            }
        }
        return super.getQuads(state, side, rand);
    }

    @Override
    public boolean isLayered() {
        return this.cameraTransformType == ItemTransforms.TransformType.GUI;
    }

    @Override
    public List<Pair<BakedModel, RenderType>> getLayerModels(ItemStack itemStack, boolean fabulous) {
        return Collections.singletonList(Pair.of(this, fabulous ? SPELL_ICON_FAB : SPELL_ICON));
    }
}
