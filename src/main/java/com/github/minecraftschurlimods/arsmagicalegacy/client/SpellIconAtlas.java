package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public final class SpellIconAtlas extends TextureAtlasHolder {
    public static final ResourceLocation SPELL_ICON_ATLAS = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/atlas/spell_icons.png");
    public static final ResourceLocation SPELL_ICON_ATLAS_INFO = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_icons");
    private static final Lazy<SpellIconAtlas> INSTANCE = Lazy.of(SpellIconAtlas::new);

    private SpellIconAtlas() {
        super(Minecraft.getInstance().textureManager, SPELL_ICON_ATLAS, SPELL_ICON_ATLAS_INFO);
    }

    /**
     * @return The only instance of this class.
     */
    public static SpellIconAtlas instance() {
        return INSTANCE.get();
    }

    /**
     * @return A collection of all registered spell icons.
     */
    public Collection<ResourceLocation> getRegisteredIcons() {
        return textureAtlas.texturesByName.keySet();
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable ResourceLocation location) {
        if (location == null) location = MissingTextureAtlasSprite.getLocation();
        return super.getSprite(location);
    }
}
