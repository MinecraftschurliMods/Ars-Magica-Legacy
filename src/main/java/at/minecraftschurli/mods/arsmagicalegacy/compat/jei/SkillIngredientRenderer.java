package at.minecraftschurli.mods.arsmagicalegacy.compat.jei;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

final class SkillIngredientRenderer implements IIngredientRenderer<Skill> {
    @Override
    public void render(GuiGraphicsExtractor graphics, Skill skill) {
        AMClientUtil.blitSprite(graphics, SkillAtlasHolder.getSprite(skill), 0, 0, 16, 16);
    }

    @Override
    public List<Component> getTooltip(Skill skill, TooltipFlag tooltipFlag) {
        return List.of(Skill.getName(AMRegistries.skills(true).wrapAsHolder(skill)));
    }
}
