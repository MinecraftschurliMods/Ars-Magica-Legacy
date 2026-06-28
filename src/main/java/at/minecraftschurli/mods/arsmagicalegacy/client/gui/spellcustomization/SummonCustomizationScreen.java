package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.screen.AbstractContainerSpellPartCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.CrystalPhylacteryContentsSize;
import at.minecraftschurli.mods.arsmagicalegacy.container.SingleItemContainer;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.item.CrystalPhylacteryItem;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SummonCustomizationScreen extends AbstractContainerSpellPartCustomizationScreen<EntityType<?>> {
    private static final Identifier INVENTORY = ArsMagicaApi.id("textures/gui/spell_customization/inventory.png");
    private static final Identifier SLOT = ArsMagicaApi.id("textures/gui/spell_customization/summon.png");
    private final Container container;

    public SummonCustomizationScreen(Function<DataComponentType<EntityType<?>>, @Nullable EntityType<?>> valueGetter, BiConsumer<DataComponentType<EntityType<?>>, @Nullable EntityType<?>> valueSetter) {
        super(AMTranslations.SPELL_CUSTOMIZATION_SUMMON, AMDataComponents.SPELL_SUMMON.get(), valueGetter, valueSetter);
        imageHeight = 132;
        container = new SingleItemContainer(value == null ? ItemStack.EMPTY : CrystalPhylacteryItem.getFilled(value)) {
            @Override
            public void setChanged() {
                value = getValue(stack);
                setValue();
            }
        };
    }

    @Override
    @Nullable
    protected EntityType<?> getValue(ItemStack stack) {
        CrystalPhylacteryItem.Contents contents = stack.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null) return null;
        EntityType<?> type = contents.type();
        int size = CrystalPhylacteryContentsSize.get(type);
        return size > 0 && contents.amount() >= size ? type : null;
    }

    @Override
    protected void addSlots() {
        slots.add(new Slot(container, 0, 80, 8) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(AMItems.CRYSTAL_PHYLACTERY) && CrystalPhylacteryItem.isFull(stack);
            }
        });
        addInventorySlots(8, 50);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        AMClientUtil.blit(graphics, SLOT, leftPos + 72, topPos, 32, 32);
        AMClientUtil.blitFull(graphics, INVENTORY, leftPos, topPos + 32, 176, 100);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);
        graphics.text(font, Objects.requireNonNull(AMClientUtil.player()).getInventory().getDisplayName(), 8, 38, 0xff404040, false);
    }
}
