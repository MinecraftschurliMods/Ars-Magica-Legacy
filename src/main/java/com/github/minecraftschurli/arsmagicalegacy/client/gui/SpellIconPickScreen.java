package com.github.minecraftschurli.arsmagicalegacy.client.gui;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.client.SpellIconAtlas;
import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurli.arsmagicalegacy.network.SpellIconSelectPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmlclient.gui.GuiUtils;
import net.minecraftforge.fmlclient.gui.widget.ExtendedButton;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SpellIconPickScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/spell_customization.png");
    private static final String NAME_FIELD_MESSAGE = "gui_components." + ArsMagicaAPI.MOD_ID + ".spell_customization_screen.name_box";
    private static final int ICON_SIZE = 16;

    private final int imageWidth = 176;
    private final int imageHeight = 256;

    private SpellIconSelector spellIconSelector;
    private EditBox editBox;
    private int xStart;
    private int yStart;
    private ExtendedButton btnPrev;
    private ExtendedButton btnNext;

    public SpellIconPickScreen(ItemStack stack) {
        super(TextComponent.EMPTY);
        this.editBox = new EditBox(this.font, 0, 0, 0, 0, new TranslatableComponent(NAME_FIELD_MESSAGE));
        SpellItem.getSpellName(stack).ifPresent(this.editBox::setValue);
    }

    @Override
    protected void init() {
        super.init();
        this.xStart = (this.width - this.imageWidth) / 2;
        this.yStart = (this.height - this.imageHeight) / 2;
        this.editBox = new EditBox(this.font, this.xStart + 8, this.yStart + 6, 100, 16, this.editBox, new TranslatableComponent(NAME_FIELD_MESSAGE));
        this.addRenderableWidget(this.editBox);
        this.spellIconSelector = new SpellIconSelector(this.xStart + 8, this.yStart + 52, this.imageWidth - 16, this.imageHeight - 60, this.spellIconSelector);
        this.addRenderableWidget(this.spellIconSelector);
        this.btnPrev = addRenderableWidget(new ExtendedButton(this.xStart + 8, this.yStart + 26, 48, 20, new TranslatableComponent("gui_components." + ArsMagicaAPI.MOD_ID + ".spell_customization_screen.prev"), button -> this.spellIconSelector.changePage(button, this.btnNext, this.btnPrev)));
        this.btnPrev.active = false;
        this.btnNext = addRenderableWidget(new ExtendedButton(this.xStart + this.imageWidth - 56, this.yStart + 26, 48, 20, new TranslatableComponent("gui_components." + ArsMagicaAPI.MOD_ID + ".spell_customization_screen.next"), button -> this.spellIconSelector.changePage(button, this.btnNext, this.btnPrev)));
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
        SpellIconSelector.Icon icon = this.spellIconSelector.selected;
        if (!name.isBlank() && icon != null) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new SpellIconSelectPacket(name, icon.icon()));
        }
    }

    private static class SpellIconSelector implements Widget, GuiEventListener, NarratableEntry {
        private final List<Icon> icons = new ArrayList<>();
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private final int elementsX;
        private final int elementsY;
        private final int maxPage;

        private Icon selected  = null;
        private int  page      = 0;

        public SpellIconSelector(int x, int y, int width, int height, @Nullable SpellIconSelector spellIconSelector) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.elementsX = (width+2) / (ICON_SIZE + 2);
            this.elementsY = (height+2) / (ICON_SIZE + 2);
            if (spellIconSelector != null) {
                this.page = spellIconSelector.page;
                this.selected = spellIconSelector.selected;
            }
            SpellIconAtlas.instance()
                          .getRegisteredIcons()
                          .stream()
                          .sorted()
                          .map(icon -> new Icon(this, icon, SpellIconAtlas.instance()::getSprite))
                          .forEachOrdered(this.icons::add);
            this.maxPage = this.icons.size() / (this.elementsX * this.elementsY);
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            var hovered = getHovered(pMouseX, pMouseY);
            if (hovered != null) {
                this.selected = hovered;
                return true;
            }
            return false;
        }

        public void changePage(Button button, Button next, Button prev) {
            if (next == button) {
                page++;
            } else if (prev == button) {
                page--;
            }
            if (page >= maxPage) {
                page = maxPage;
                next.active = false;
            } else {
                next.active = true;
            }
            if (page <= 0) {
                page = 0;
                prev.active = false;
            } else {
                prev.active = true;
            }
        }

        @Nullable
        private Icon getHovered(double pMouseX, double pMouseY) {
            int x = (int) Math.floor((pMouseX - this.x + 1) / (ICON_SIZE + 2));
            int y = (int) Math.floor((pMouseY - this.y + 1) / (ICON_SIZE + 2));
            if (x >= this.elementsX || x < 0) return null;
            if (y >= this.elementsY || y < 0) return null;
            int i = x + y * this.elementsX + this.page * this.elementsX * this.elementsY;
            if (i < this.icons.size() && i >= 0) {
                return this.icons.get(i);
            } else {
                return null;
            }
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            pPoseStack.pushPose();
            pPoseStack.translate(this.x, this.y, 0);
            GuiUtils.drawGradientRect(pPoseStack.last().pose(), -2, -1, -1, this.width+1, this.height+1, 0x80000000, 0x80000000);
            int i = 0;
            var hovered = getHovered(pMouseX, pMouseY);
            for (Icon icon : this.icons) {
                int x = i % this.elementsX;
                int y = i / this.elementsX % this.elementsY;
                int p = i / this.elementsX / this.elementsY;
                if (p == page) {
                    pPoseStack.pushPose();
                    pPoseStack.translate((ICON_SIZE + 2) * x, (ICON_SIZE + 2) * y, 0);
                    if (icon == this.selected) {
                        GuiUtils.drawGradientRect(pPoseStack.last().pose(),
                                                  -1,
                                                  -1,
                                                  -1,
                                                  ICON_SIZE + 1,
                                                  ICON_SIZE + 1,
                                                  0xffffff00,
                                                  0xffffff00);
                    } else if (icon == hovered) {
                        GuiUtils.drawGradientRect(pPoseStack.last().pose(),
                                                  -1,
                                                  -1,
                                                  -1,
                                                  ICON_SIZE + 1,
                                                  ICON_SIZE + 1,
                                                  0xffffffff,
                                                  0xffffffff);
                    }
                    icon.render(pPoseStack);
                    pPoseStack.popPose();
                }
                i++;
            }
            pPoseStack.popPose();
        }

        @Override
        public NarrationPriority narrationPriority() {
            return NarrationPriority.NONE;
        }

        @Override
        public void updateNarration(final NarrationElementOutput pNarrationElementOutput) {}

        private record Icon(SpellIconSelector parent, ResourceLocation icon, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {

            public void render(PoseStack pPoseStack) {
                RenderSystem.setShaderTexture(0, SpellItem.SPELL_ICON_ATLAS);
                blit(pPoseStack, 0, 0, 0, ICON_SIZE, ICON_SIZE, this.textureGetter().apply(this.icon()));
            }
        }
    }
}
