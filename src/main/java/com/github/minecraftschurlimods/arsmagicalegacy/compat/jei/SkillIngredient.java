package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SkillIngredient {
    public static final IIngredientType<Skill> TYPE = () -> Skill.class;

    public static class Helper implements IIngredientHelper<Skill> {
        @Override
        public IIngredientType<Skill> getIngredientType() {
            return SkillIngredient.TYPE;
        }

        @Override
        public String getDisplayName(Skill ingredient) {
            return ingredient.getDisplayName(AMUtil.getRegistryAccess()).getString();
        }

        @Override
        public String getUniqueId(Skill ingredient, UidContext context) {
            return getResourceLocation(ingredient).toString();
        }

        @Override
        public ResourceLocation getResourceLocation(Skill ingredient) {
            return ingredient.getId(AMUtil.getRegistryAccess());
        }

        @Override
        public Skill copyIngredient(Skill ingredient) {
            return ingredient;
        }

        @Override
        public String getErrorInfo(@Nullable Skill ingredient) {
            return ingredient == null ? "Unknown skill" : ingredient.toString();
        }
    }

    public static class Renderer implements IIngredientRenderer<Skill> {
        @Override
        public void render(GuiGraphics graphics, Skill ingredient) {
            RenderSystem.setShaderTexture(0, SkillIconAtlas.SKILL_ICON_ATLAS);
            graphics.blit(0, 0, 0, 16, 16, SkillIconAtlas.instance().getSprite(ingredient.getId(ClientHelper.getRegistryAccess())));
        }

        @Override
        public List<Component> getTooltip(Skill ingredient, TooltipFlag tooltipFlag) {
            return List.of(ingredient.getDisplayName(AMUtil.getRegistryAccess()));
        }
    }
}
