package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SpellIconAtlas;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

class AMSpriteSourceProvider extends SpriteSourceProvider {
    AMSpriteSourceProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper, ArsMagicaAPI.MOD_ID);
    }

    @Override
    protected void addSources() {
        atlas(SpellIconAtlas.SPELL_ICON_ATLAS_INFO).addSource(new DirectoryLister("icon/spell", ""));
        atlas(SkillIconAtlas.SKILL_ICON_ATLAS_INFO).addSource(new DirectoryLister("icon/skill", ""));
    }
}
