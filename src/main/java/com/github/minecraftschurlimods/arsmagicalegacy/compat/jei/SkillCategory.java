package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.IngredientSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.jei.ingredient.SkillIngredient;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SkillCategory implements IRecipeCategory<SkillCategory.Recipe> {
    static final RecipeType<Recipe> RECIPE_TYPE = RecipeType.create(ArsMagicaAPI.MOD_ID, "skill", Recipe.class);
    private static final Component TITLE = Component.translatable(TranslationConstants.SPELL_PART_CATEGORY);
    private static final ResourceLocation BACKGROUND = new ResourceLocation("jei", "textures/gui/gui_vanilla.png"); //TODO new background
    private static final int INGREDIENT_COLUMNS = 7;
    private static final int SLOT_SIZE = 18;
    private static final int WIDTH = 116;
    private static final int HEIGHT = 116;
    private final IDrawable background;
    private final IDrawable icon;

    public SkillCategory(IGuiHelper helper) {
        icon = helper.createDrawableItemStack(new ItemStack(AMItems.ALTAR_CORE.get()));
        background = helper.createDrawable(BACKGROUND, 0, 60, WIDTH, HEIGHT);
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
        int x = 0, y = 0;
        builder.addSlot(RecipeIngredientRole.OUTPUT, (WIDTH - SLOT_SIZE) / 2, y).addIngredient(SkillIngredient.TYPE, recipe.skill);
        y += SLOT_SIZE * 2;
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
                    ArsMagicaAPI.get().getEtheriumHelper().setEtheriumType(stack, e);
                    return stack;
                }).toList());
            }
        }
        //TODO reagents
        //TODO affinities
        //TODO modified by
    }

    @Override
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        //TODO draw Ingredients string
        //TODO draw slots behind items
        //TODO draw Reagents string
        //TODO draw slots behind reagents
        //TODO draw Affinity Breakdown string
        //TODO draw affinity icons
        //TODO draw affinity values
        //TODO draw Modified By string
    }

    public record Recipe(Skill skill, List<ISpellIngredient> recipe, Map<Affinity, Float> affinityShifts) {
        @SuppressWarnings("ConstantConditions")
        public static Recipe of(Skill skill) {
            ArsMagicaAPI api = ArsMagicaAPI.get();
            ISpellPartData data = api.getSpellDataManager().getDataForPart(api.getSpellPartRegistry().getValue(skill.getId(ClientHelper.getRegistryAccess())));
            return new Recipe(skill, data.recipe(), data.affinityShifts());
        }
    }
}
