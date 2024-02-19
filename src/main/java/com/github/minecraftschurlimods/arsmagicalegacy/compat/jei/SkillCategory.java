package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.IngredientSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SkillCategory implements IRecipeCategory<SkillCategory.Recipe> {
    public static final RecipeType<Recipe> RECIPE_TYPE = RecipeType.create(ArsMagicaAPI.MOD_ID, "skill", Recipe.class);
    private static final Component TITLE = Component.translatable(TranslationConstants.SKILL_CATEGORY);
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/skill_category.png");
    private static final int INGREDIENT_COLUMNS = 7;
    private static final int SLOT_SIZE = 18;
    private static final int WIDTH = INGREDIENT_COLUMNS * SLOT_SIZE;
    private static final int HEIGHT = 192;
    private static final int TEXT_BOTTOM_PADDING = 2;
    private final IDrawable background;
    private final IDrawable icon;

    public SkillCategory(IGuiHelper helper) {
        icon = helper.createDrawableItemStack(new ItemStack(AMItems.ALTAR_CORE.get()));
        background = helper.createDrawable(BACKGROUND, 0, 0, WIDTH, HEIGHT);
    }

    @Override
    public RecipeType<Recipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
        var api = ArsMagicaAPI.get();
        RegistryAccess registryAccess = ClientHelper.getRegistryAccess();
        int x = 0, y = Minecraft.getInstance().font.lineHeight + TEXT_BOTTOM_PADDING;
        builder.addSlot(RecipeIngredientRole.OUTPUT, (WIDTH - SLOT_SIZE) / 2, y).addIngredient(SkillIngredient.TYPE, recipe.skill);
        y += SLOT_SIZE + TEXT_BOTTOM_PADDING;
        for (int i = 0; i < recipe.recipe.size(); i++) {
            if (i % INGREDIENT_COLUMNS != 0) {
                x += SLOT_SIZE;
            } else {
                x = (WIDTH - Math.min(recipe.recipe.size() - i * INGREDIENT_COLUMNS, INGREDIENT_COLUMNS) * SLOT_SIZE) / 2;
                y += SLOT_SIZE;
            }
            ISpellIngredient ingredient = recipe.recipe.get(i);
            if (ingredient instanceof IngredientSpellIngredient isi) {
                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStacks(Arrays.asList(isi.ingredient().getItems()));
            } else if (ingredient instanceof EtheriumSpellIngredient esi) {
                builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStacks(esi.types().stream().map(e -> {
                    ItemStack stack = new ItemStack(AMItems.ETHERIUM_PLACEHOLDER.get(), esi.amount());
                    api.getEtheriumHelper().setEtheriumType(stack, e);
                    return stack;
                }).toList());
            }
        }
        y += SLOT_SIZE;
        if (!recipe.affinityShifts.isEmpty()) {
            x = getAffinityValueAnchor(Minecraft.getInstance().font, recipe.affinityShifts) - 9;
            y += SLOT_SIZE + TEXT_BOTTOM_PADDING;
            for (Affinity affinity : recipe.affinityShifts.keySet().stream().sorted().toList()) {
                builder.addSlot(RecipeIngredientRole.RENDER_ONLY, x, y)
                        .addItemStack(api.getAffinityHelper().getEssenceForAffinity(affinity))
                        .addTooltipCallback((slot, tooltip) -> {
                            tooltip.clear();
                            tooltip.add(affinity.getDisplayName(registryAccess));
                        });
                y += SLOT_SIZE - 2;
            }
            y += 2;
        }
        if (!recipe.modifiers.isEmpty()) {
            y += TEXT_BOTTOM_PADDING;
            for (int i = 0; i < recipe.modifiers.size(); i++) {
                if (i % INGREDIENT_COLUMNS != 0) {
                    x += SLOT_SIZE;
                } else {
                    x = (WIDTH - Math.min(recipe.modifiers.size() - i * INGREDIENT_COLUMNS, INGREDIENT_COLUMNS) * SLOT_SIZE) / 2;
                    y += SLOT_SIZE;
                }
                builder.addSlot(RecipeIngredientRole.CATALYST, x, y).addIngredient(SkillIngredient.TYPE, Objects.requireNonNull(registryAccess
                        .registryOrThrow(Skill.REGISTRY_KEY)
                        .get(api.getSpellPartRegistry().getKey(recipe.modifiers.get(i)))));
            }
        }
    }

    @Override
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        drawCentered(graphics, font, recipe.skill().getDisplayName(ClientHelper.getRegistryAccess()), 0);
        int y = SLOT_SIZE * 2 + TEXT_BOTTOM_PADDING;
        drawCentered(graphics, font, Component.translatable(TranslationConstants.SKILL_INGREDIENTS), y);
        y += (recipe.recipe.size() / INGREDIENT_COLUMNS + 1) * SLOT_SIZE + font.lineHeight + TEXT_BOTTOM_PADDING;
        if (!recipe.affinityShifts.isEmpty()) {
            y += SLOT_SIZE - font.lineHeight;
            drawCentered(graphics, font, Component.translatable(TranslationConstants.SKILL_AFFINITY_BREAKDOWN), y);
            y += font.lineHeight + font.lineHeight / 2 + TEXT_BOTTOM_PADDING;
            int x = getAffinityValueAnchor(font, recipe.affinityShifts) + 9;
            for (Affinity affinity : recipe.affinityShifts.keySet().stream().sorted().toList()) {
                graphics.drawString(font, String.valueOf(recipe.affinityShifts.get(affinity)), x, y, affinity.color());
                y += SLOT_SIZE - 2;
            }
            y += 2 - font.lineHeight / 2 + TEXT_BOTTOM_PADDING;
        }
        if (!recipe.modifiers.isEmpty()) {
            y += SLOT_SIZE - font.lineHeight;
            drawCentered(graphics, font, Component.translatable(TranslationConstants.SKILL_MODIFIED_BY), y);
        }
    }

    public record Recipe(Skill skill, List<ISpellIngredient> recipe, Map<Affinity, Float> affinityShifts, List<ISpellModifier> modifiers) {
        @SuppressWarnings("ConstantConditions")
        public static Recipe of(Skill skill) {
            ArsMagicaAPI api = ArsMagicaAPI.get();
            ISpellPart part = api.getSpellPartRegistry().getValue(skill.getId(ClientHelper.getRegistryAccess()));
            ISpellPartData data = api.getSpellDataManager().getDataForPart(part);
            return new Recipe(skill, data.recipe(), data.affinityShifts(), AMUtil.getModifiersForPart(part));
        }
    }

    private static void drawCentered(GuiGraphics graphics, Font font, Component component, int y) {
        graphics.drawString(font, component, (int) ((WIDTH - font.getSplitter().stringWidth(component.getString())) / 2), y, 0x404040);
    }

    private static int getAffinityValueAnchor(Font font, Map<Affinity, Float> affinityShifts) {
        return (int) (WIDTH - font.getSplitter().stringWidth(String.valueOf(affinityShifts.values().stream().min(Float::compareTo).orElse(0f)))) / 2;
    }
}
