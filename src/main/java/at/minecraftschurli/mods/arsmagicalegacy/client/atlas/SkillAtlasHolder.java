package at.minecraftschurli.mods.arsmagicalegacy.client.atlas;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
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
