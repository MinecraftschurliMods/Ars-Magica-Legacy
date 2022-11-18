package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SpellIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpellIconSelectPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.GuiUtils;
import net.minecraftforge.client.gui.ScrollPanel;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpellCustomizationScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/spell_customization.png");
    private static final int ICON_SIZE = 15;
    private static final int ICON_MARGIN = 1;
    private final int imageWidth = 176;
    private final int imageHeight = 256;
    private int xStart;
    private int yStart;
    private SpellIconSelector spellIconSelector;
    private EditBox editBox;

    public SpellCustomizationScreen(ItemStack stack) {
        super(TextComponent.EMPTY);
        editBox = new EditBox(font, 0, 0, 0, 0, new TranslatableComponent(TranslationConstants.SPELL_CUSTOMIZATION_TITLE));
        spellIconSelector = new SpellIconSelector(0, 0, 0, 0, null);
        var helper = ArsMagicaAPI.get().getSpellHelper();
        helper.getSpellName(stack).ifPresent(pText -> editBox.setValue(pText.getString()));
        helper.getSpellIcon(stack).ifPresent(spellIconSelector::setSelected);
    }

    @Override
    protected void init() {
        super.init();
        xStart = (width - imageWidth) / 2;
        yStart = (height - imageHeight) / 2;
        editBox = addRenderableWidget(new EditBox(font, xStart + 8, yStart + 8, 100, 16, editBox, new TranslatableComponent(TranslationConstants.SPELL_CUSTOMIZATION_TITLE)));
        spellIconSelector = addRenderableWidget(new SpellIconSelector(xStart + 7, yStart + 30, imageWidth - 15, imageHeight - 38, spellIconSelector));
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        blit(pPoseStack, xStart, yStart, 0, 0, imageWidth, imageHeight);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void onClose() {
        super.onClose();
        String name = editBox.getValue();
        ResourceLocation icon = spellIconSelector.getSelected();
        if (!name.isBlank() && icon != null) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new SpellIconSelectPacket(name, icon));
        }
    }

    private static class SpellIconSelector extends ScrollPanel {
        private final List<ResourceLocation> icons = new ArrayList<>();
        private final int elementsX;
        private ResourceLocation selected = null;

        public SpellIconSelector(int x, int y, int width, int height, @Nullable SpellIconSelector spellIconSelector) {
            super(Minecraft.getInstance(), width, height, y, x, 2);
            elementsX = (width - border + 2) / (ICON_SIZE + 2 * ICON_MARGIN);
            if (spellIconSelector != null) {
                setSelected(spellIconSelector.getSelected());
            }
            SpellIconAtlas.instance().getRegisteredIcons().stream().sorted().forEachOrdered(icons::add);
        }

        @Override
        public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
            drawGradientRect(matrix, left, top, right, bottom, 0x80000000, 0x80000000);
            super.render(matrix, mouseX, mouseY, partialTicks);
        }

        @Nullable
        public ResourceLocation getSelected() {
            return selected;
        }

        public void setSelected(@Nullable ResourceLocation id) {
            selected = id;
        }

        @Override
        public NarrationPriority narrationPriority() {
            return NarrationPriority.NONE;
        }

        @Override
        public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
        }

        @Override
        protected boolean clickPanel(double mouseX, double mouseY, int button) {
            ResourceLocation hovered = getHovered(mouseX - 2 * ICON_MARGIN, mouseY + border - 2 * ICON_MARGIN);
            if (hovered != null) {
                selected = hovered;
                return true;
            }
            return false;
        }

        @Override
        protected void drawPanel(PoseStack mStack, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
            int i = 0;
            ResourceLocation hovered = getHovered(mouseX - left - 2 * ICON_MARGIN, mouseY - top + (int) scrollDistance - 2 * ICON_MARGIN);
            RenderSystem.setShaderTexture(0, SpellIconAtlas.SPELL_ICON_ATLAS);
            for (ResourceLocation icon : icons) {
                int x = i % elementsX;
                int y = i / elementsX;
                x *= ICON_SIZE + 2 * ICON_MARGIN;
                y *= ICON_SIZE + 2 * ICON_MARGIN;
                x += left + 2 * ICON_MARGIN;
                y += relativeY * ICON_MARGIN;
                if (y > 0 && y < bottom) {
                    if (icon.equals(selected)) {
                        GuiUtils.drawGradientRect(mStack.last().pose(), 1, x - ICON_MARGIN, y - ICON_MARGIN, x + ICON_SIZE + ICON_MARGIN, y + ICON_SIZE + ICON_MARGIN, 0xffffff00, 0xffffff00);
                    } else if (icon.equals(hovered)) {
                        GuiUtils.drawGradientRect(mStack.last().pose(), 1, x - ICON_MARGIN, y - ICON_MARGIN, x + ICON_SIZE + ICON_MARGIN, y + ICON_SIZE + ICON_MARGIN, 0xffffffff, 0xffffffff);
                    }
                    blit(mStack, x, y, 2, ICON_SIZE, ICON_SIZE, SpellIconAtlas.instance().getSprite(icon));
                }
                i++;
            }
        }

        @Override
        protected int getContentHeight() {
            return (icons.size() / elementsX + ((icons.size() % elementsX > 0) ? 1 : 0)) * (ICON_SIZE + ICON_MARGIN * 2) - border + 2 * ICON_MARGIN;
        }

        @Nullable
        private ResourceLocation getHovered(double pMouseX, double pMouseY) {
            if (pMouseY - scrollDistance + border - (2 * ICON_MARGIN) < 0) return null;
            if (pMouseY - scrollDistance + border - (2 * ICON_MARGIN) > height) return null;
            int x = (int) Math.floor((pMouseX + ICON_MARGIN) / (ICON_SIZE + (2 * ICON_MARGIN)));
            int y = (int) Math.floor((pMouseY + ICON_MARGIN) / (ICON_SIZE + (2 * ICON_MARGIN)));
            if (x >= elementsX || x < 0) return null;
            if (y < 0) return null;
            int i = x + y * elementsX;
            if (i < icons.size() && i >= 0) return icons.get(i);
            else return null;
        }

        @Override
        protected int getScrollAmount() {
            return ICON_SIZE + ICON_MARGIN * 2;
        }
    }
}
