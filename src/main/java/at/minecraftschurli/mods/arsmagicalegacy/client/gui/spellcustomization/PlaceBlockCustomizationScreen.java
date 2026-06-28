package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.screen.AbstractContainerSpellPartCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.container.SingleItemContainer;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PlaceBlockCustomizationScreen extends AbstractContainerSpellPartCustomizationScreen<Block> {
    private static final Identifier INVENTORY = ArsMagicaApi.id("textures/gui/spell_customization/inventory.png");
    private static final Identifier SLOT = ArsMagicaApi.id("textures/gui/spell_customization/place_block.png");
    private final Container container;

    public PlaceBlockCustomizationScreen(Function<DataComponentType<Block>, @Nullable Block> valueGetter, BiConsumer<DataComponentType<Block>, @Nullable Block> valueSetter) {
        super(AMTranslations.SPELL_CUSTOMIZATION_PLACE_BLOCK, AMDataComponents.SPELL_BLOCK.get(), valueGetter, valueSetter);
        imageHeight = 132;
        container = new SingleItemContainer(value == null ? ItemStack.EMPTY : new ItemStack(value.asItem())) {
            @Override
            public void setChanged() {
                value = getValue(stack);
                setValue();
            }
        };
    }

    @Override
    @Nullable
    protected Block getValue(ItemStack stack) {
        Block block = Block.byItem(stack.getItem());
        return block.defaultBlockState().isAir() ? null : block;
    }

    @Override
    protected void addSlots() {
        slots.add(new Slot(container, 0, 80, 8) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof BlockItem;
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
