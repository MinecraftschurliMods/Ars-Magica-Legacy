package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Objects;

class IngredientsPage extends Page<SpellIngredient> {
    public IngredientsPage(List<SpellIngredient> ingredients) {
        super(5, 13, 16, 6, 5, ingredients);
    }

    @Override
    public Component getTitle() {
        return AMTranslations.SPELL_RECIPE_INGREDIENTS;
    }

    @Override
    public void extractElement(SpellIngredient element, int index, GuiGraphicsExtractor graphics, int x, int y) {
        ItemStack stack = AMUtil.getByTick(element.asItemStacks(), (int) (Objects.requireNonNull(AMClientUtil.level()).getGameTime() / 20));
        if (stack == null || stack.isEmpty()) {
            return;
        }
        x = x + index % maxPerLine * (size + spacing);
        y = y + index / maxPerLine * (size + spacing);
        AMClientUtil.renderItem(graphics, stack, x, y);
    }

    @Override
    public List<Component> getElementTooltip(SpellIngredient element) {
        return element.tooltip(AMClientUtil.level());
    }
}
