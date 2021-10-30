package com.github.minecraftschurli.arsmagicalegacy.client.gui;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.client.SpellIconAtlas;
import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurli.arsmagicalegacy.network.SpellIconSelectPacket;
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
import net.minecraftforge.client.gui.ScrollPanel;
import net.minecraftforge.fmlclient.gui.GuiUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpellIconPickScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/spell_customization.png");
    private static final String NAME_FIELD_MESSAGE = "gui_components." + ArsMagicaAPI.MOD_ID + ".spell_customization_screen.name_box";
    private static final int ICON_SIZE   = 15;
    private static final int ICON_MARGIN = 1;

    private final int imageWidth = 176;
    private final int imageHeight = 256;

    private SpellIconSelector spellIconSelector;
    private EditBox editBox;
    private int xStart;
    private int yStart;

    public SpellIconPickScreen(ItemStack stack) {
        super(TextComponent.EMPTY);
        this.editBox = new EditBox(this.font, 0, 0, 0, 0, new TranslatableComponent(NAME_FIELD_MESSAGE));
        this.spellIconSelector = new SpellIconSelector(0, 0, 0, 0, null);

        SpellItem.getSpellName(stack).ifPresent(this.editBox::setValue);
        SpellItem.getSpellIcon(stack).ifPresent(this.spellIconSelector::setSelected);
    }

    @Override
    protected void init() {
        super.init();
        this.xStart = (this.width - this.imageWidth) / 2;
        this.yStart = (this.height - this.imageHeight) / 2;
        this.editBox = this.addRenderableWidget(new EditBox(this.font, this.xStart + 8, this.yStart + 8, 100, 16, this.editBox, new TranslatableComponent(NAME_FIELD_MESSAGE)));
        this.spellIconSelector = this.addRenderableWidget(new SpellIconSelector(this.xStart + 7, this.yStart + 30, this.imageWidth - 13, this.imageHeight - 38, this.spellIconSelector));
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        blit(pPoseStack, this.xStart, this.yStart, 0, 0, imageWidth, imageHeight);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void onClose() {
        super.onClose();
        String name = this.editBox.getValue();
        ResourceLocation icon = this.spellIconSelector.getSelected();
        if (!name.isBlank() && icon != null) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new SpellIconSelectPacket(name, icon));
        }
    }

    private static class SpellIconSelector extends ScrollPanel {
        private final List<ResourceLocation> icons = new ArrayList<>();
        private final int elementsX;

        private ResourceLocation selected = null;

        public SpellIconSelector(int x, int y, int width, int height, @Nullable SpellIconSelector spellIconSelector) {
            super(Minecraft.getInstance(), width, height, y, x);
            this.elementsX = (width - this.border + 2) / (ICON_SIZE + 2 * ICON_MARGIN);
            if (spellIconSelector != null) {
                setSelected(spellIconSelector.getSelected());
            }
            SpellIconAtlas.instance()
                          .getRegisteredIcons()
                          .stream()
                          .sorted()
                          .forEachOrdered(this.icons::add);
        }

        @Override
        protected int getContentHeight() {
            return (this.icons.size() / this.elementsX + ((this.icons.size() % this.elementsX > 0) ? 1 : 0)) * (ICON_SIZE + ICON_MARGIN * 2) - this.border + 2 * ICON_MARGIN;
        }

        @Override
        protected int getScrollAmount() {
            return ICON_SIZE + ICON_MARGIN * 2;
        }

        @Override
        public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
            drawGradientRect(matrix,
                             this.left,
                             this.top,
                             this.right,
                             this.bottom,
                             0x80000000,
                             0x80000000);
            super.render(matrix, mouseX, mouseY, partialTicks);
        }

        @Override
        protected void drawPanel(PoseStack mStack,
                                 int entryRight,
                                 int relativeY,
                                 Tesselator tess,
                                 int mouseX,
                                 int mouseY) {
            int i = 0;
            var hovered = getHovered(mouseX - this.left - 2 * ICON_MARGIN, mouseY - this.top + (int)this.scrollDistance - 2 * ICON_MARGIN);
            RenderSystem.setShaderTexture(0, SpellItem.SPELL_ICON_ATLAS);
            for (ResourceLocation icon : this.icons) {
                int x = i % this.elementsX;
                int y = i / this.elementsX;
                x *= ICON_SIZE + 2 * ICON_MARGIN;
                y *= ICON_SIZE + 2 * ICON_MARGIN;
                x += this.left + 2 * ICON_MARGIN;
                y += relativeY - this.border + 2 * ICON_MARGIN;
                if (y > 0 && y < this.bottom) {
                    if (icon.equals(this.selected)) {
                        GuiUtils.drawGradientRect(mStack.last().pose(),
                                         1,
                                         x - ICON_MARGIN,
                                         y - ICON_MARGIN,
                                         x + ICON_SIZE + ICON_MARGIN,
                                         y + ICON_SIZE + ICON_MARGIN,
                                         0xffffff00,
                                         0xffffff00);
                    } else if (icon.equals(hovered)) {
                        GuiUtils.drawGradientRect(mStack.last().pose(),
                                         1,
                                         x - ICON_MARGIN,
                                         y - ICON_MARGIN,
                                         x + ICON_SIZE + ICON_MARGIN,
                                         y + ICON_SIZE + ICON_MARGIN,
                                         0xffffffff,
                                         0xffffffff);
                    }
                    blit(mStack, x, y, 2, ICON_SIZE, ICON_SIZE, SpellIconAtlas.instance().getSprite(icon));
                }
                i++;
            }
        }

        @Override
        protected boolean clickPanel(double mouseX, double mouseY, int button) {
            var hovered = getHovered(mouseX - 2 * ICON_MARGIN, mouseY + this.border - 2 * ICON_MARGIN);
            if (hovered != null) {
                this.selected = hovered;
                return true;
            }
            return false;
        }

        public void setSelected(@Nullable ResourceLocation id) {
            this.selected = id;
        }

        @Nullable
        public ResourceLocation getSelected() {
            return this.selected;
        }

        @Nullable
        private ResourceLocation getHovered(double pMouseX, double pMouseY) {
            if (pMouseY - this.scrollDistance + this.border - (2 * ICON_MARGIN) < 0) return null;
            if (pMouseY - this.scrollDistance + this.border - (2 * ICON_MARGIN) > this.height) return null;
            int x = (int) Math.floor((pMouseX + ICON_MARGIN) / (ICON_SIZE + (2 * ICON_MARGIN)));
            int y = (int) Math.floor((pMouseY + ICON_MARGIN) / (ICON_SIZE + (2 * ICON_MARGIN)));
            if (x >= this.elementsX || x < 0) return null;
            if (y < 0) return null;
            int i = x + y * this.elementsX;
            if (i < this.icons.size() && i >= 0) {
                return this.icons.get(i);
            } else {
                return null;
            }
        }

        @Override
        public NarrationPriority narrationPriority() {
            return NarrationPriority.NONE;
        }

        @Override
        public void updateNarration(final NarrationElementOutput pNarrationElementOutput) {}
    }
}
