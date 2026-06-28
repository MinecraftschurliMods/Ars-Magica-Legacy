package com.github.minecraftschurlimods.arsmagicalegacy.client.atlas;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class SpellIconAtlasHolder {
    public static final Identifier ATLAS = ArsMagicaApi.id("textures/atlas/spell_icon.png");
    public static final Identifier ATLAS_ID = ArsMagicaApi.id("spell_icon");

    public static TextureAtlasSprite getSprite(@Nullable Identifier identifier) {
        TextureAtlas atlas = AMClientUtil.mc().getAtlasManager().getAtlasOrThrow(ATLAS_ID);
        return identifier == null ? atlas.missingSprite() : atlas.getSprite(identifier);
    }

    public static TextureAtlasSprite getSprite(SpriteGetter getter, Identifier identifier) {
        return getter.get(new SpriteId(ATLAS, identifier));
    }

    public static @Nullable TextureAtlasSprite getSpriteOrNull(SpriteGetter getter, Identifier identifier) {
        TextureAtlasSprite sprite = getSprite(getter, identifier);
        if (sprite == getMissingSprite()) {
            return null;
        }
        return sprite;
    }

    public static TextureAtlasSprite getMissingSprite() {
        return AMClientUtil.mc().getAtlasManager().getAtlasOrThrow(ATLAS_ID).missingSprite();
    }

    public static Collection<Identifier> getIcons() {
        return AMClientUtil.mc().getAtlasManager().getAtlasOrThrow(ATLAS_ID).getTextures().keySet();
    }
}
