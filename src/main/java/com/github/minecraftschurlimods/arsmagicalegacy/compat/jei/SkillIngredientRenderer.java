package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

final class SkillIngredientRenderer implements IIngredientRenderer<Skill> {
    @Override
    public void render(GuiGraphicsExtractor graphics, Skill skill) {
        AMClientUtil.blit(graphics, SkillAtlasHolder.getSprite(skill), 0, 0, 16, 16);
    }

    @Override
    public List<Component> getTooltip(Skill skill, TooltipFlag tooltipFlag) {
        return List.of(Skill.getName(AMRegistries.skills(true).wrapAsHolder(skill)));
    }
}
