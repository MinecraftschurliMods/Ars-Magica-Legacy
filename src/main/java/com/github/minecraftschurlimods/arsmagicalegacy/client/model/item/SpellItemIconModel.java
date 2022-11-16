package com.github.minecraftschurlimods.arsmagicalegacy.client.model.item;

import com.github.minecraftschurlimods.arsmagicalegacy.client.SpellIconAtlas;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SpellItemIconModel extends BakedModelWrapper<BakedModel> {
    private static final RenderType SPELL_ICON = RenderType.itemEntityTranslucentCull(SpellIconAtlas.SPELL_ICON_ATLAS);
    private static final RenderType SPELL_ICON_FAB = RenderType.entityTranslucentCull(SpellIconAtlas.SPELL_ICON_ATLAS);
    private final Cache<ResourceLocation, List<BakedQuad>> cache = CacheBuilder.newBuilder().maximumSize(5).build();
    private final ItemTransforms.TransformType cameraTransformType;
    private final ResourceLocation icon;

    public SpellItemIconModel(BakedModel originalModel, ItemTransforms.TransformType cameraTransformType, ResourceLocation icon) {
        super(originalModel);
        this.cameraTransformType = cameraTransformType;
        this.icon = icon;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        if (cameraTransformType == ItemTransforms.TransformType.GUI) {
            try {
                return cache.get(icon, () -> {
                    TextureAtlasSprite sprite = SpellIconAtlas.instance().getSprite(icon);
                    return List.of(new FaceBakery().bakeQuad(new Vector3f(0, 0, 8.504f), new Vector3f(16, 16, 8.504f), new BlockElementFace(null, 2, sprite.getName().toString(), new BlockFaceUV(new float[]{0, 0, 16, 16}, 0)), sprite, Direction.SOUTH, BlockModelRotation.X0_Y0, null, true, sprite.getName()));
                });
            } catch (ExecutionException ignored) {
            }
        }
        return super.getQuads(state, side, rand);
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
        return Collections.singletonList(fabulous ? SPELL_ICON_FAB : SPELL_ICON);
    }

    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        return List.of(this);
    }
}
