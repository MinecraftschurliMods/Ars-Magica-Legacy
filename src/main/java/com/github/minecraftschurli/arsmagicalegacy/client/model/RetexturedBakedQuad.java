package com.github.minecraftschurli.arsmagicalegacy.client.model;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class RetexturedBakedQuad extends BakedQuad {
    public RetexturedBakedQuad(BakedQuad quad, TextureAtlasSprite texture) {
        super(quad.getVertices(), quad.getTintIndex(), quad.getDirection(), texture, quad.isShade());
        remapQuad();
    }

    private static float getUnInterpolatedU(TextureAtlasSprite sprite, float u) {
        float f = sprite.getU1() - sprite.getU0();
        return (u - sprite.getU0()) / f * 16.0F;
    }

    private static float getUnInterpolatedV(TextureAtlasSprite sprite, float v) {
        float f = sprite.getV1() - sprite.getV0();
        return (v - sprite.getV0()) / f * 16.0F;
    }

    private void remapQuad() {
        for (int i = 0; i < 4; ++i) {
            int j = DefaultVertexFormat.BLOCK.getIntegerSize() * i;
            int uvIndex = 4;
            vertices[j + uvIndex] = Float.floatToRawIntBits(sprite.getU(getUnInterpolatedU(sprite, Float.intBitsToFloat(vertices[j + uvIndex]))));
            vertices[j + uvIndex + 1] = Float.floatToRawIntBits(sprite.getV(getUnInterpolatedV(sprite, Float.intBitsToFloat(vertices[j + uvIndex + 1]))));
        }
    }
}
