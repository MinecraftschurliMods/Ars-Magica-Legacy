package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ColorPickerScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.SelfClearingEditBox;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragArea;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier.Color;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/inscription_table.png");
    private static final Component SEARCH_LABEL = Component.translatable(TranslationConstants.INSCRIPTION_TABLE_SEARCH);
    private static final Component NAME_LABEL = Component.translatable(TranslationConstants.INSCRIPTION_TABLE_NAME);
    private final List<DragArea<SpellPartDraggable>> dragAreas = new ArrayList<>();
    private final Map<Pair<Integer, Integer>, Integer> colorData = new HashMap<>();
    private SpellPartDraggable dragged;
    private EditBox searchBar;
    private EditBox nameBar;
    private SpellPartSourceArea sourceArea;
    private SpellGrammarArea spellGrammarArea;
    private ShapeGroupListArea shapeGroupArea;

    public InscriptionTableScreen(InscriptionTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageWidth = 220;
        imageHeight = 252;
    }

    public static void onSlotChanged() {
        if (Minecraft.getInstance().screen instanceof InscriptionTableScreen screen) {
            screen.sync();
            if (screen.menu.getSlot(0).getItem().getItem() instanceof ISpellItem) {
                screen.menu.getSpellRecipe().ifPresent(screen::existingRecipe);
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        dragAreas.clear();
        if (Objects.requireNonNull(getMinecraft().player).isCreative()) {
            addRenderableWidget(new Button(leftPos + 72, topPos + 72, 100, 20, Component.translatable(TranslationConstants.INSCRIPTION_TABLE_CREATE_SPELL), button -> {
                sync();
                menu.getSpellRecipe().ifPresent(e -> {
                    if (ArsMagicaAPI.get().getSpellHelper().isValidSpell(e)) {
                        menu.createSpell();
                    }
                });
            }));
        }
        sourceArea = new SpellPartSourceArea(leftPos + 42, topPos + 6, 136, 48);
        spellGrammarArea = new SpellGrammarArea(leftPos + 42, topPos + 144, 136, 16, (part, index) -> this.onDrop(part, -1, index));
        shapeGroupArea = new ShapeGroupListArea(leftPos + 20, topPos + 107, this, this::onDrop);
        searchBar = addRenderableWidget(new SelfClearingEditBox(leftPos + 40, topPos + 59, 140, 12, 64, searchBar, font, SEARCH_LABEL));
        searchBar.setResponder(e -> sourceArea.setNameFilter(e.equals(SEARCH_LABEL.getString()) ? "" : e));
        nameBar = addRenderableWidget(new SelfClearingEditBox(leftPos + 40, topPos + 93, 140, 12, 64, nameBar, font, NAME_LABEL));
        dragAreas.add(sourceArea);
        dragAreas.add(spellGrammarArea);
        dragAreas.add(shapeGroupArea);
        menu.getSpellRecipe().ifPresent(this::existingRecipe);
        menu.getSpellName().ifPresent(this::existingName);
        setDragged(null);
    }

    private void onDrop(SpellPartDraggable part, int groupIndex, int innerIndex) {
        if (part.getPart() == AMSpellParts.COLOR.get()) {
            Pair<Integer, Integer> key = Pair.of(groupIndex, innerIndex);
            ColorPickerScreen colorPicker = new ColorPickerScreen(Component.translatable(TranslationConstants.INSCRIPTION_TABLE_COLOR_PICKER_TITLE), colorData.getOrDefault(key, 0xffffff), true, color -> {
                colorData.put(key, color);
            });
            getMinecraft().pushGuiLayer(colorPicker);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        for (DragArea<SpellPartDraggable> area : dragAreas) {
            area.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }
        if (dragged != null) {
            dragged.render(pPoseStack, pMouseX - SpellPartDraggable.SIZE / 2, pMouseY - SpellPartDraggable.SIZE / 2, pPartialTick);
        } else {
            SpellPartDraggable part = getHoveredElement(pMouseX, pMouseY);
            if (part != null) {
                renderTooltip(pPoseStack, part.getTranslationKey(), pMouseX, pMouseY);
            }
        }
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        blit(pPoseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        blit(pPoseStack, leftPos + (Objects.requireNonNull(getMinecraft().player).isCreative() ? 47 : 101), topPos + 73, 220, 0, 18, 18);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
    }

    @Override
    public void onClose() {
        sync();
        super.onClose();
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (dragged != null) return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        int mouseX = (int) pMouseX;
        int mouseY = (int) pMouseY;
        DragArea<SpellPartDraggable> area = getHoveredArea(mouseX, mouseY);
        SpellPartDraggable part = getHoveredElement(mouseX, mouseY);
        if (area != null && part != null && area.canPick(part, mouseX, mouseY)) {
            area.pick(part, mouseX, mouseY);
        }
        setDragged(part);
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (dragged == null) return super.mouseReleased(pMouseX, pMouseY, pButton);
        int mouseX = (int) pMouseX;
        int mouseY = (int) pMouseY;
        DragArea<SpellPartDraggable> area = getHoveredArea(mouseX, mouseY);
        SpellPartDraggable part = dragged;
        if (area != null && part != null && area.canDrop(part, mouseX, mouseY)) {
            area.drop(part, mouseX, mouseY);
        }
        setDragged(null);
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputConstants.KEY_ESCAPE && shouldCloseOnEsc()) {
            onClose();
            return true;
        } else if (keyCode == InputConstants.KEY_TAB) {
            boolean flag = !hasShiftDown();
            if (!changeFocus(flag)) {
                changeFocus(flag);
            }
            return false;
        } else {
            return getFocused() != null && getFocused().keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public void setFocused(@Nullable GuiEventListener listener) {
        if (getFocused() instanceof EditBox editBox) {
            editBox.setFocus(false);
        }
        super.setFocused(listener);
        if (listener instanceof EditBox editBox) {
            editBox.setFocus(true);
        }
    }

    public int allowedShapeGroups() {
        return menu.allowedShapeGroups();
    }

    private void setDragged(@Nullable SpellPartDraggable dragged) {
        this.dragged = dragged;
        sourceArea.setTypeFilter(shapeGroupArea.canStore(), spellGrammarArea.canStore(), !spellGrammarArea.getAll().isEmpty() && spellGrammarArea.getAll().get(0).getPart().getType() == ISpellPart.SpellPartType.COMPONENT || !shapeGroupArea.getAll().isEmpty() && shapeGroupArea.getAll().get(0).getPart().getType() == ISpellPart.SpellPartType.SHAPE);
    }

    @Nullable
    private DragArea<SpellPartDraggable> getHoveredArea(int mouseX, int mouseY) {
        for (DragArea<SpellPartDraggable> area : dragAreas) {
            if (area.isAbove(mouseX, mouseY)) return area;
        }
        return null;
    }

    @Nullable
    private SpellPartDraggable getHoveredElement(int mouseX, int mouseY) {
        DragArea<SpellPartDraggable> area = getHoveredArea(mouseX, mouseY);
        return area == null ? null : area.elementAt(mouseX, mouseY);
    }

    private void existingRecipe(ISpell spell) {
        CompoundTag additionalData = spell.additionalData();
        {
            List<ISpellPart> parts = spell.spellStack().parts();
            for (int i = 0; i < parts.size(); i++) {
                ISpellPart part = parts.get(i);
                if (part == AMSpellParts.COLOR.get()) {
                    Integer data = Color.getData(additionalData, -1, i);
                    if (data != null) {
                        colorData.put(Pair.of(-1, i), data);
                    }
                }
                spellGrammarArea.drop(new SpellPartDraggable(part), Integer.MAX_VALUE, Integer.MAX_VALUE);
            }
        }
        List<ShapeGroup> shapeGroups = spell.shapeGroups();
        for (int i = 0; i < shapeGroups.size(); i++) {
            ShapeGroupArea area = shapeGroupArea.get(i);
            ShapeGroup group = shapeGroups.get(i);
            List<ISpellPart> parts = group.parts();
            for (int j = 0; j < parts.size(); j++) {
                ISpellPart part = parts.get(j);
                if (part == AMSpellParts.COLOR.get()) {
                    Integer data = Color.getData(additionalData, j, i);
                    if (data != null) {
                        colorData.put(Pair.of(j, i), data);
                    }
                }
                area.drop(new SpellPartDraggable(part), Integer.MAX_VALUE, Integer.MAX_VALUE);
            }
        }
        shapeGroupArea.setLocks();
    }

    private void existingName(Component name) {
        nameBar.setValue(name.getString());
        nameBar.setTextColor(0xffffff);
    }

    private void sync() {
        menu.sendDataToServer(nameBar.getValue().equals(NAME_LABEL.getString()) ? null : Component.literal(nameBar.getValue()), spellGrammarArea.getAll().stream().map(e -> e.getPart().getId()).toList(), shapeGroupArea.getShapeGroupData(), compileAdditionalData());
    }

    private CompoundTag compileAdditionalData() {
        CompoundTag compoundTag = new CompoundTag();
        colorData.forEach((key, value) -> {
            int shapeGropIndex = key.getFirst();
            int innerIndex;
            if (shapeGropIndex < 0) {
                innerIndex = findIndexOfModified(spellGrammarArea.getAll(), key.getSecond());
            } else {
                innerIndex = findIndexOfModified(shapeGroupArea.get(shapeGropIndex).getAll(), key.getSecond());
            }
            compoundTag.putInt(Color.getKey(shapeGropIndex, innerIndex), value);
        });
        return compoundTag;
    }
    
    private int findIndexOfModified(List<SpellPartDraggable> parts, int modifierIndex) {
        for (int i = modifierIndex; i >= 0; i--) {
            SpellPartDraggable part = parts.get(i);
            if (part.getPart().getType() != ISpellPart.SpellPartType.MODIFIER) {
                return i;
            }
        }
        return -1;
    }
}
