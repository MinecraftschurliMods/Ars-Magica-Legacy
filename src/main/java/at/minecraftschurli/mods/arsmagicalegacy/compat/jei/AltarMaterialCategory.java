package at.minecraftschurli.mods.arsmagicalegacy.compat.jei;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

final class AltarMaterialCategory implements IRecipeCategory<AltarMaterialCategory.Recipe> {
    public static final IRecipeType<Recipe> RECIPE_TYPE = IRecipeType.create(ArsMagicaApi.MOD_ID, "altar_material", AltarMaterialCategory.Recipe.class);
    private final IDrawable icon;

    public AltarMaterialCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableItemStack(AMItems.ALTAR_CORE.toStack());
    }

    @Override
    public IRecipeType<Recipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return AMTranslations.JEI_ALTAR_MATERIAL_TITLE;
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 24;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
        if (recipe.stair.isPresent()) {
            builder.addInputSlot(4, 4).add(recipe.block);
            builder.addInputSlot(22, 4).add(recipe.stair.get());
        } else {
            builder.addInputSlot(13, 4).add(recipe.block);
        }
    }

    @Override
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor graphics, double mouseX, double mouseY) {
        Component string = Component.translatable(recipe.stair.isPresent() ? AMTranslations.JEI_ALTAR_MATERIAL_DESCRIPTION_KEY : AMTranslations.JEI_ALTAR_MATERIAL_CAP_DESCRIPTION_KEY, recipe.power);
        Font font = AMClientUtil.font();
        graphics.text(font, string, 109 - font.width(string) / 2, 8, 0xff7f7f7f, false);
    }

    public record Recipe(ItemStack block, Optional<ItemStack> stair, int power) {
        public static Recipe of(AltarMaterial material) {
            return new Recipe(new ItemStack(material.block()), Optional.of(new ItemStack(material.stair())), material.power());
        }

        public static Recipe of(AltarCapMaterial material) {
            return new Recipe(new ItemStack(material.block()), Optional.empty(), material.power());
        }
    }
}
