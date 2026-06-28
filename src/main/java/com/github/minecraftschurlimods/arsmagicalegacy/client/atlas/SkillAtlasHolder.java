package com.github.minecraftschurlimods.arsmagicalegacy.client.atlas;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class SkillAtlasHolder {
    public static final Identifier ATLAS = ArsMagicaApi.id("textures/atlas/skill.png");
    public static final Identifier ATLAS_ID = ArsMagicaApi.id("skill");

    public static TextureAtlasSprite getSprite(@Nullable Identifier identifier) {
        TextureAtlas atlas = AMClientUtil.mc().getAtlasManager().getAtlasOrThrow(ATLAS_ID);
        return identifier == null ? atlas.missingSprite() : atlas.getSprite(identifier);
    }

    public static TextureAtlasSprite getSprite(Skill skill) {
        return getSprite(AMRegistries.skills(true).getKey(skill));
    }
}
