package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

public final class SkillIconAtlas extends TextureAtlasHolder {
    public static final ResourceLocation SKILL_ICON_ATLAS = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/atlas/skill_icons.png");
    public static final ResourceLocation SKILL_ICON_ATLAS_INFO = new ResourceLocation(ArsMagicaAPI.MOD_ID, "skill_icons");
    private static final Lazy<SkillIconAtlas> INSTANCE = Lazy.of(SkillIconAtlas::new);

    private SkillIconAtlas() {
        super(Minecraft.getInstance().textureManager, SKILL_ICON_ATLAS, SKILL_ICON_ATLAS_INFO);
    }

    /**
     * @return The only instance of this class.
     */
    public static SkillIconAtlas instance() {
        return INSTANCE.get();
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable ResourceLocation location) {
        if (location == null) location = MissingTextureAtlasSprite.getLocation();
        return super.getSprite(location);
    }
}
