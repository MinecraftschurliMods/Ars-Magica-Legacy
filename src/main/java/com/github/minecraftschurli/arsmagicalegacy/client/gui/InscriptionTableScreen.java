package com.github.minecraftschurli.arsmagicalegacy.client.gui;

import com.github.minecraftschurli.arsmagicalegacy.Config;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillManager;
import com.github.minecraftschurli.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis.BasicDropZone;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis.DragPane;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis.DraggableWithData;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis.FilteredFilledDropArea;
import com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurli.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
    private static final ResourceLocation GUI                 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/inscription_table.png");
    private static final Component        SEARCH_LABEL        = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_SEARCH_BAR_LABEL);
    private static final Component        NAME_LABEL          = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_NAME_LABEL);
    private static final Component        DEFAULT_NAME        = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_DEFAULT_NAME_VALUE);
    private static final int              SHAPE_GROUP_WIDTH   = 34;
    private static final int              SHAPE_GROUP_HEIGHT  = 34;
    private static final int              SHAPE_GROUP_PADDING = 3;
    private static final int              SHAPE_GROUP_Y       = 108;
    private static final int              SHAPE_GROUP_X       = 13;
    private static final int              ICON_SIZE           = 16;

    private       EditBox             searchBar;
    private       EditBox             nameBar;
    private       DragPane            dragPane;
    private       BasicDropZone       spellStackDropZone;
    private final List<BasicDropZone> shapeGroupDropZones = new ArrayList<>();

    public InscriptionTableScreen(InscriptionTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageWidth = 220;
        imageHeight = 252;
    }

    @Override
    protected void init() {
        super.init();

        ArsMagicaAPI.IArsMagicaAPI api = ArsMagicaAPI.get();
        ISkillManager skillManager = api.getSkillManager();
        Predicate<ResourceLocation> searchFilter = spellPart -> {
            String value = searchBar.getValue();
            if (StringUtil.isNullOrEmpty(value) || value.equals(SEARCH_LABEL.getString())) return true;
            return skillManager.get(spellPart).getDisplayName().getString().contains(value);
        };
        Predicate<ResourceLocation> knowsFilter = spellPart -> {
            assert Minecraft.getInstance().player != null;
            return api.getSkillHelper().knows(Minecraft.getInstance().player, spellPart);
        };

        nameBar = addRenderableWidget(new SelfClearingEditBox(39 + this.leftPos, 93 + this.topPos, 141, 12, 64, this.nameBar, this.font, NAME_LABEL));
        menu.getSpellName().ifPresent(name -> {
            nameBar.setValue(name);
            nameBar.setTextColor(0xffffff);
        });
        if (nameBar.getValue().equals(NAME_LABEL.getString())) {
            nameBar.setValue(DEFAULT_NAME.getString());
            nameBar.setTextColor(0xffffff);
        }

        searchBar = addRenderableWidget(new SelfClearingEditBox(39 + this.leftPos, 59 + this.topPos, 141, 12, 64, this.searchBar, this.font, SEARCH_LABEL));

        dragPane = new DragPane(this.leftPos, this.topPos, this.width, this.height);
        FilteredFilledDropArea<ResourceLocation> sourceBox = dragPane.addDropArea(new FilteredFilledDropArea<>(40 + this.leftPos, 5 + this.topPos,
                                                                                                               138, 48,
                                                                                                               ICON_SIZE, ICON_SIZE,
                                                                                                               knowsFilter.and(searchFilter),
                                                                                                               api.getSpellPartRegistry().getKeys(),
                                                                                                               rl -> Pair.of(SkillIconAtlas.instance().getSprite(rl),
                                                                                                                             skillManager.get(rl).getDescription())));
        searchBar.setResponder(s -> sourceBox.update());
        int offsetX = leftPos + SHAPE_GROUP_X;
        for (int sg = 0; sg < menu.allowedShapeGroups(); sg++) {
            BasicDropZone old = shapeGroupDropZones.size() > sg ? shapeGroupDropZones.get(sg) : null;
            BasicDropZone shapeGroupDropZone = dragPane.addDropArea(new BasicDropZone(offsetX + (sg * (SHAPE_GROUP_WIDTH + SHAPE_GROUP_PADDING)), topPos + SHAPE_GROUP_Y, SHAPE_GROUP_WIDTH, SHAPE_GROUP_HEIGHT, ICON_SIZE, ICON_SIZE, 1, 4, old));
            if (shapeGroupDropZones.size() > sg) {
                shapeGroupDropZones.set(sg, shapeGroupDropZone);
            } else {
                shapeGroupDropZones.add(shapeGroupDropZone);
            }
        }
        spellStackDropZone = dragPane.addDropArea(new BasicDropZone(leftPos + 39, topPos + 144, 141, 18, ICON_SIZE, ICON_SIZE, 1, 8, spellStackDropZone));
        addRenderableWidget(dragPane);
        sourceBox.update();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        int offsetX = leftPos + SHAPE_GROUP_X;
        for (int sg = 0; sg < Config.SERVER.MAX_STAGE_GROUPS.get(); sg++) {
            if (sg >= menu.allowedShapeGroups()) {
                RenderSystem.setShaderFogColor(0.5f, 0.5f, 0.5f);
            }
            blit(poseStack, offsetX + (sg * (SHAPE_GROUP_WIDTH + SHAPE_GROUP_PADDING)), topPos + SHAPE_GROUP_Y, 220, 18, SHAPE_GROUP_WIDTH, SHAPE_GROUP_HEIGHT);
        }
        blit(poseStack, leftPos + 101, topPos + 73, 220, 0, 18, 18);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
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

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button) | dragPane.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return this.dragPane.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return super.charTyped(codePoint, modifiers);
    }
}
