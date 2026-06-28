package at.minecraftschurli.mods.arsmagicalegacy.api.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/// A more specialized version of [AbstractSpellPartCustomizationScreen] that mimics a container GUI, minus some functionality such as dropping and quick moving.
/// Items are expected to be copied into slots rather than actually be placed, i.e., this screen is not to be used for storage.
/// Since this is a client-only screen, mutations to the inventory are not possible, as they would not be persistent anyway.
///
/// @param <T> The type of the modified data component.
public abstract class AbstractContainerSpellPartCustomizationScreen<T> extends AbstractSpellPartCustomizationScreen<T> {
    private static final Identifier SLOT_HIGHLIGHT_BACK_SPRITE = Identifier.withDefaultNamespace("container/slot_highlight_back");
    private static final Identifier SLOT_HIGHLIGHT_FRONT_SPRITE = Identifier.withDefaultNamespace("container/slot_highlight_front");
    protected final List<Slot> slots = new ArrayList<>();
    protected int imageWidth = 176;
    protected int imageHeight = 166;
    protected int leftPos;
    protected int topPos;
    protected int inventorySlotCount = 36;
    protected ItemStack carried = ItemStack.EMPTY;
    @Nullable
    protected Slot hoveredSlot;

    public AbstractContainerSpellPartCustomizationScreen(Component title, DataComponentType<T> type, Function<DataComponentType<T>, @Nullable T> valueGetter, BiConsumer<DataComponentType<T>, @Nullable T> valueSetter) {
        super(title, type, valueGetter, valueSetter);
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2 - 12;
        addSlots();
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, _ -> onClose()).bounds(width / 2 - 100, topPos + imageHeight + 4, 200, 20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        graphics.pose().pushMatrix();
        graphics.pose().translate(leftPos, topPos);
        hoveredSlot = null;
        for (Slot slot : slots) {
            if (slot.isActive() && isHovering(slot, mouseX, mouseY)) {
                hoveredSlot = slot;
                break;
            }
        }
        if (hoveredSlot != null && hoveredSlot.isHighlightable()) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_BACK_SPRITE, hoveredSlot.x - 4, hoveredSlot.y - 4, 24, 24);
        }
        for (Slot slot : slots) {
            if (slot.isActive()) {
                extractSlot(graphics, slot);
            }
        }
        if (this.hoveredSlot != null && this.hoveredSlot.isHighlightable()) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_FRONT_SPRITE, hoveredSlot.x - 4, hoveredSlot.y - 4, 24, 24);
        }
        extractLabels(graphics, mouseX, mouseY);
        if (!carried.isEmpty()) {
            graphics.item(carried, mouseX - leftPos - 8, mouseY - topPos - 8);
        }
        graphics.pose().popMatrix();
        extractTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (super.keyPressed(event)) return true;
        InputConstants.Key mouseKey = InputConstants.getKey(event);
        if (getMinecraft().options.keyInventory.isActiveAndMatches(mouseKey)) {
            onClose();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (super.mouseClicked(event, doubleClick)) return true;
        if (hoveredSlot != null) {
            if (hoveredSlot.index < slots.size() - inventorySlotCount) {
                if (event.hasShiftDown() || carried.isEmpty()) {
                    clearSlot(hoveredSlot);
                } else {
                    setSlot(hoveredSlot);
                }
            } else if (!hoveredSlot.getItem().isEmpty()) {
                if (event.hasShiftDown()) {
                    quickMoveSlot(hoveredSlot);
                } else {
                    pickSlot(hoveredSlot);
                }
            }
        } else {
            carried = ItemStack.EMPTY;
        }
        return true;
    }

    /// Converts an [ItemStack] into a `T`.
    ///
    /// @param stack The [ItemStack] to convert.
    /// @return The converted `T`.
    @Nullable
    protected abstract T getValue(ItemStack stack);

    /// Override this to add [Slot]s to the screen.
    ///
    /// @see #addSlot(Slot)
    protected abstract void addSlots();

    /// Adds a [Slot] to the screen.
    ///
    /// @param slot The [Slot] to add.
    protected void addSlot(Slot slot) {
        slot.index = slots.size();
        slots.add(slot);
    }

    /// Convenience method to add the player inventory to the screen.
    ///
    /// @param x The x position of the inventory.
    /// @param y The y position of the inventory.
    @SuppressWarnings({"DataFlowIssue", "SameParameterValue"})
    protected void addInventorySlots(int x, int y) {
        Inventory inventory = Minecraft.getInstance().player.getInventory();
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, x + i * 18, y + 58));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, x + j * 18, y + i * 18));
            }
        }
    }

    /// Called from [#extractRenderState(GuiGraphicsExtractor, int, int, float)] to render inventory labels.
    ///
    /// @param graphics The [GuiGraphicsExtractor] to use.
    /// @param mouseX   The mouse x position.
    /// @param mouseY   The mouse y position.
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
    }

    /// Called from [#extractRenderState(GuiGraphicsExtractor, int, int, float)] to render a [Slot].
    ///
    /// @param graphics The [GuiGraphicsExtractor] to use.
    /// @param slot     The [Slot] to render.
    protected void extractSlot(GuiGraphicsExtractor graphics, Slot slot) {
        int x = slot.x;
        int y = slot.y;
        ItemStack stack = slot.getItem();
        graphics.pose().pushMatrix();
        if (stack.isEmpty() && slot.isActive()) {
            Identifier icon = slot.getNoItemIcon();
            if (icon != null) {
                graphics.blitSprite(RenderPipelines.GUI, icon, x, y, 16, 16);
            }
        } else if (!stack.isEmpty()) {
            int seed = x + y * imageWidth;
            if (slot.isFake()) {
                graphics.fakeItem(stack, x, y, seed);
            } else {
                graphics.item(stack, x, y, seed);
            }
            graphics.itemDecorations(font, stack, x, y);
        }
        graphics.pose().popMatrix();
    }

    /// Called from [#extractRenderState(GuiGraphicsExtractor, int, int, float)] to render inventory labels.
    ///
    /// @param graphics The [GuiGraphicsExtractor] to use.
    /// @param mouseX      The mouse x position.
    /// @param mouseY      The mouse y position.
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (carried.isEmpty() && hoveredSlot != null && hoveredSlot.hasItem()) {
            graphics.setTooltipForNextFrame(font, hoveredSlot.getItem(), mouseX, mouseY);
        }
    }

    /// Called when a non-player inventory slot is shift-clicked.
    /// Expected behavior is to clear the given slot.
    ///
    /// @param slot The affected [Slot].
    protected void clearSlot(Slot slot) {
        slot.set(ItemStack.EMPTY);
    }

    /// Called when a player inventory slot is shift-clicked.
    /// Expected behavior is to copy the slot contents to the non-player inventory.
    ///
    /// @param slot The affected [Slot].
    protected void quickMoveSlot(Slot slot) {
        ItemStack stack = slot.getItem().copyWithCount(1);
        Slot first = slots.getFirst();
        if (first.mayPlace(stack)) {
            first.set(stack);
        }
    }

    /// Called when a non-player inventory slot is clicked.
    /// Expected behavior is to set the carried item into the given slot.
    ///
    /// @param slot The affected [Slot].
    protected void setSlot(Slot slot) {
        if (slot.mayPlace(carried)) {
            slot.set(carried);
        }
    }

    /// Called when a player inventory slot is clicked.
    /// Expected behavior is to copy-pick the item in the given slot.
    ///
    /// @param slot The affected [Slot].
    protected void pickSlot(Slot slot) {
        carried = slot.getItem().copyWithCount(1);
    }

    private boolean isHovering(Slot slot, double mouseX, double mouseY) {
        int x = slot.x;
        int y = slot.y;
        mouseX -= leftPos;
        mouseY -= topPos;
        return mouseX >= x - 1 && mouseX < x + 16 + 1 && mouseY >= y - 1 && mouseY < y + 16 + 1;
    }
}
