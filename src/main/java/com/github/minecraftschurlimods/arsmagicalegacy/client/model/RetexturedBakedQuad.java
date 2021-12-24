package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class RetexturedBakedQuad extends BakedQuad {
    public RetexturedBakedQuad(BakedQuad quad, TextureAtlasSprite texture) {
        super(quad.getVertices(), quad.getTintIndex(), quad.getDirection(), texture, quad.isShade());
        remapQuad();
    }

    private void remapQuad() {
        int uvIndex = 4;
        for (int i = 0; i < 4; ++i) {
            int j = DefaultVertexFormat.BLOCK.getIntegerSize() * i;
            int i1 = j + uvIndex;
            int i2 = j + uvIndex + 1;
            vertices[i1] = Float.floatToRawIntBits(sprite.getU(sprite.getUOffset(Float.intBitsToFloat(vertices[i1]))));
            vertices[i2] = Float.floatToRawIntBits(sprite.getV(sprite.getVOffset(Float.intBitsToFloat(vertices[i2]))));
        }
    }
}
