package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

final class SkillCategory implements IRecipeCategory<SkillCategory.Recipe> {
    public static final IRecipeType<Recipe> RECIPE_TYPE = IRecipeType.create(ArsMagicaApi.MOD_ID, "skill", Recipe.class);
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/skill_category.png");
    @SuppressWarnings("DataFlowIssue")
    private static final Comparator<Holder<Affinity>> COMPARATOR = Comparator.comparing(Holder::getKey);
    private static final int INGREDIENT_COLUMNS = 7;
    private static final int SLOT_SIZE = 18;
    private static final int WIDTH = INGREDIENT_COLUMNS * SLOT_SIZE;
    private static final int HEIGHT = 192;
    private static final int TEXT_BOTTOM_PADDING = 2;
    private final IDrawable icon;

    public SkillCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableItemStack(AMItems.ALTAR_CORE.toStack());
    }

    @Override
    public IRecipeType<Recipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return AMTranslations.JEI_SKILL_TITLE;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
        List<SpellIngredient> ingredients = recipe.recipe;
        Map<Holder<Affinity>, Double> affinityShifts = recipe.affinityShifts;
        List<Skill> modifiers = recipe.modifiers;
        int x = 0;
        int y = AMClientUtil.font().lineHeight + TEXT_BOTTOM_PADDING;
        builder.addSlot(RecipeIngredientRole.OUTPUT, (WIDTH - SLOT_SIZE) / 2, y).add(AMJeiPlugin.SKILL_TYPE, recipe.skill.value());
        y += SLOT_SIZE + TEXT_BOTTOM_PADDING;
        if (!ingredients.isEmpty()) {
            for (int i = 0; i < ingredients.size(); i++) {
                if (i % INGREDIENT_COLUMNS != 0) {
                    x += SLOT_SIZE;
                } else {
                    x = (WIDTH - Math.min(ingredients.size() - i, INGREDIENT_COLUMNS) * SLOT_SIZE) / 2;
                    y += SLOT_SIZE;
                }
                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStacks(ingredients.get(i).asItemStacks());
            }
            y += SLOT_SIZE;
        }
        if (!affinityShifts.isEmpty()) {
            x = getAffinityValueAnchor(affinityShifts) - 9;
            y += SLOT_SIZE + TEXT_BOTTOM_PADDING;
            for (Holder<Affinity> affinity : affinityShifts.keySet().stream().sorted(COMPARATOR).toList()) {
                builder.addSlot(RecipeIngredientRole.RENDER_ONLY, x, y)
                    .add(AMUtil.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), affinity))
                    .addRichTooltipCallback((slot, tooltip) -> {
                        tooltip.clear();
                        tooltip.add(Affinity.getName(affinity));
                    });
                y += SLOT_SIZE - TEXT_BOTTOM_PADDING;
            }
            y += TEXT_BOTTOM_PADDING;
        }
        if (!modifiers.isEmpty()) {
            y += TEXT_BOTTOM_PADDING;
            for (int i = 0; i < modifiers.size(); i++) {
                if (i % INGREDIENT_COLUMNS != 0) {
                    x += SLOT_SIZE;
                } else {
                    x = (WIDTH - Math.min(modifiers.size() - i, INGREDIENT_COLUMNS) * SLOT_SIZE) / 2;
                    y += SLOT_SIZE;
                }
                builder.addSlot(RecipeIngredientRole.RENDER_ONLY, x, y).add(AMJeiPlugin.SKILL_TYPE, modifiers.get(i));
            }
        }
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor graphics, double mouseX, double mouseY) {
        AMClientUtil.blitFull(graphics, BACKGROUND, 0, 0, WIDTH, HEIGHT);
        Font font = AMClientUtil.font();
        drawCentered(graphics, font, Skill.getName(recipe.skill), 0);
        int y = SLOT_SIZE * 2 + TEXT_BOTTOM_PADDING;
        drawCentered(graphics, font, AMTranslations.JEI_SKILL_INGREDIENTS, y);
        y += (recipe.recipe.size() / INGREDIENT_COLUMNS + 1) * SLOT_SIZE + font.lineHeight + TEXT_BOTTOM_PADDING;
        if (!recipe.affinityShifts.isEmpty()) {
            y += SLOT_SIZE - font.lineHeight;
            drawCentered(graphics, font, AMTranslations.JEI_SKILL_AFFINITY_BREAKDOWN, y);
            y += font.lineHeight + font.lineHeight / 2 + TEXT_BOTTOM_PADDING;
            int x = getAffinityValueAnchor(recipe.affinityShifts) + 9;
            for (Holder<Affinity> affinity : recipe.affinityShifts.keySet().stream().sorted(COMPARATOR).toList()) {
                graphics.text(font, String.valueOf(Math.round(recipe.affinityShifts.get(affinity) * 1000) / 1000.), x, y, 0xff000000 | affinity.value().color(), false);
                y += SLOT_SIZE - 2;
            }
            y += 2 - font.lineHeight / 2 + TEXT_BOTTOM_PADDING;
        }
        if (!recipe.modifiers.isEmpty()) {
            y += SLOT_SIZE - font.lineHeight;
            drawCentered(graphics, font, AMTranslations.JEI_SKILL_MODIFIED_BY, y);
        }
    }

    private static void drawCentered(GuiGraphicsExtractor graphics, Font font, Component component, int y) {
        graphics.text(font, component, (int) ((WIDTH - font.getSplitter().stringWidth(component.getString())) / 2), y, 0xff404040, false);
    }

    private static int getAffinityValueAnchor(Map<Holder<Affinity>, Double> affinityShifts) {
        return (int) (WIDTH - AMClientUtil.font().getSplitter().stringWidth(String.valueOf(Math.round(affinityShifts.values().stream().min(Double::compareTo).orElse(0.) * 1000) / 1000.))) / 2;
    }

    public record Recipe(Holder<Skill> skill, List<SpellIngredient> recipe, Map<Holder<Affinity>, Double> affinityShifts, List<Skill> modifiers) {
        @SuppressWarnings("DataFlowIssue")
        public static Recipe of(Holder<Skill> skill, Set<Holder<Skill>> hiddenModifiers) {
            Registry<Skill> skills = AMRegistries.skills(true);
            Registry<SpellPart> spellParts = AMRegistries.SPELL_PARTS;
            SpellPart part = spellParts.getValue(skills.getKey(skill.value()));
            SpellPartData data = part.getData(AMClientUtil.level().registryAccess());
            return new Recipe(skill, data.recipe(), data.affinityShifts(), ArsMagicaApi.spellHelper()
                .getModifiers(part)
                .stream()
                .map(e -> skills.get(spellParts.getKey(e)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(e -> !hiddenModifiers.contains(e))
                .map(Holder::value)
                .toList());
        }
    }
}
