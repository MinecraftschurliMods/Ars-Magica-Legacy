package com.github.minecraftschurli.arsmagicalegacy.client.gui;

import com.github.minecraftschurli.arsmagicalegacy.Config;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurli.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;

public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/inscription_table.png");
    private static final int SHAPE_GROUP_WIDTH = 37;
    private static final int SHAPE_GROUP_PADDING = 3;
    private static final int SHAPE_GROUP_Y = 108;
    private static final int SHAPE_GROUP_X = 13;

    private final Component searchLabel = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_SEARCH_BAR_LABEL);
    private final Component nameLabel = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_NAME_LABEL);
    private final Component defaultName = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_DEFAULT_NAME_VALUE);

    private EditBox searchBar;
    private EditBox nameBar;

    public InscriptionTableScreen(InscriptionTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageWidth = 220;
        imageHeight = 252;
    }

    @Override
    protected void init() {
        super.init();
        searchBar = addRenderableWidget(new EditBox(font, 39 + leftPos, 59 + topPos, 141, 12, searchBar, searchLabel));
        searchBar.setMaxLength(64);
        nameBar = addRenderableWidget(new EditBox(font, 39 + leftPos, 93 + topPos, 141, 12, nameBar, nameLabel));
        nameBar.setMaxLength(64);
        menu.getSpellName().ifPresent(nameBar::setValue);
        if (StringUtil.isNullOrEmpty(nameBar.getValue())) {
            nameBar.setValue(defaultName.getString());
        }
        searchBar.setMessage(searchLabel);
    }

    @Override
    public void render(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        renderTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        blit(pPoseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        int offsetX = leftPos + SHAPE_GROUP_X;
        for (int sg = 0; sg < Config.SERVER.MAX_STAGE_GROUPS.get(); sg++) {
            if (sg >= menu.allowedShapeGroups()) {
                RenderSystem.setShaderFogColor(0.5f, 0.5f, 0.5f);
            }
            blit(pPoseStack, offsetX + (sg * (SHAPE_GROUP_WIDTH + SHAPE_GROUP_PADDING)), topPos + SHAPE_GROUP_Y, 220, 18, 37, 37);
        }
        blit(pPoseStack, leftPos + 101, topPos + 73, 220, 0, 18, 18);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
    }
}
