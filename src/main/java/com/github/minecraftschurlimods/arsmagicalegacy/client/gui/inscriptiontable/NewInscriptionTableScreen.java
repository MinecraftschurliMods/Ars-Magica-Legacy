package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.SelfClearingEditBox;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragArea;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewInscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/inscription_table.png");
    private static final Component SEARCH_LABEL = Component.translatable(TranslationConstants.INSCRIPTION_TABLE_SEARCH);
    private final List<DragArea<SpellPartDraggable>> dragAreas = new ArrayList<>();
    private SpellPartDraggable dragged;
    private EditBox searchBar;
    private SpellPartSourceArea sourceArea;
    private SpellGrammarArea spellGrammarArea;
    private ShapeGroupListArea shapeGroupArea;

    public NewInscriptionTableScreen(InscriptionTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageWidth = 220;
        imageHeight = 252;
    }

    public static void onSlotChanged() {
        if (Minecraft.getInstance().screen instanceof NewInscriptionTableScreen screen) {
            screen.sync();
            if (screen.menu.getSlot(0).getItem().getItem() instanceof ISpellItem) {
                screen.menu.getSpellRecipe().ifPresent(screen::existingRecipe);
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        if (Objects.requireNonNull(getMinecraft().player).isCreative()) {
            addRenderableWidget(new Button(leftPos + 72, topPos + 78, 100, 20, Component.translatable(TranslationConstants.INSCRIPTION_TABLE_CREATE_SPELL), button -> {
                sync();
                if (shapeGroupArea.isValid() && !spellGrammarArea.getAll().isEmpty()) {
                    menu.createSpell();
                }
            }));
        }
        sourceArea = new SpellPartSourceArea(leftPos + 42, topPos + 6, 136, 48);
        spellGrammarArea = new SpellGrammarArea(leftPos + 42, topPos + 144, 136, 16);
        shapeGroupArea = new ShapeGroupListArea(leftPos + 20, topPos + 102, this);
        searchBar = addRenderableWidget(new SelfClearingEditBox(leftPos + 40, topPos + 62, 140, 12, 64, searchBar, font, SEARCH_LABEL));
        searchBar.setResponder(e -> sourceArea.setNameFilter(e.equals(SEARCH_LABEL.getString()) ? "" : e));
        dragAreas.add(sourceArea);
        dragAreas.add(spellGrammarArea);
        dragAreas.add(shapeGroupArea);
        menu.getSpellRecipe().ifPresent(this::existingRecipe);
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
        blit(pPoseStack, leftPos + (Objects.requireNonNull(getMinecraft().player).isCreative() ? 47 : 101), topPos + 79, 220, 0, 18, 18);
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
        return 2; // TODO
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
        for (ISpellPart part : spell.spellStack().parts()) {
            spellGrammarArea.drop(new SpellPartDraggable(part), 0, 0);
        }
        List<ShapeGroup> shapeGroups = spell.shapeGroups();
        for (int i = 0; i < shapeGroups.size(); i++) {
            ShapeGroupArea area = shapeGroupArea.get(i);
            ShapeGroup group = shapeGroups.get(i);
            for (ISpellPart part : group.parts()) {
                area.drop(new SpellPartDraggable(part), 0, 0);
            }
        }
    }

    private void sync() {
        menu.sendDataToServer(spellGrammarArea.getAll().stream().map(e -> e.getPart().getId()).toList(), shapeGroupArea.getShapeGroupData());
    }
}
