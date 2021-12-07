package com.github.minecraftschurli.arsmagicalegacy.client.gui;

import com.github.minecraftschurli.arsmagicalegacy.Config;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillManager;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurli.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis.BasicDropZone;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis.DragPane;
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
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/inscription_table.png");
    private static final int SHAPE_GROUP_WIDTH = 36;
    private static final int SHAPE_GROUP_HEIGHT = 36;
    private static final int SHAPE_GROUP_PADDING = 3;
    private static final int SHAPE_GROUP_Y = 108;
    private static final int SHAPE_GROUP_X = 13;
    private static final int ICON_SIZE = 16;

    private final Component searchLabel = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_SEARCH_BAR_LABEL);
    private final Component nameLabel = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_NAME_LABEL);
    private final Component defaultName = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_DEFAULT_NAME_VALUE);

    private EditBox searchBar;
    private EditBox nameBar;
    private FilteredFilledDropArea<ISpellPart> sourceBox;
    private DragPane dragPane;

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
        Predicate<ISpellPart> searchFilter = spellPart -> {
            String value = searchBar.getValue();
            if (StringUtil.isNullOrEmpty(value) || value.equals(searchLabel.getString())) return true;
            assert spellPart.getRegistryName() != null;
            if (spellPart.getRegistryName() == null) throw new IllegalStateException("an unregistered spell part in the registry ???");
            return skillManager.get(spellPart.getRegistryName()).getDisplayName().getString().contains(value);
        };
        Predicate<ISpellPart> knowsFilter = spellPart -> {
            assert Minecraft.getInstance().player != null;
            assert spellPart.getRegistryName() != null;
            return api.getSkillHelper().knows(Minecraft.getInstance().player, spellPart.getRegistryName());
        };

        nameBar = addRenderableWidget(new SelfClearingEditBox(39 + this.leftPos, 93 + this.topPos, 141, 12, 64, this.nameBar, this.font, this.nameLabel));
        menu.getSpellName().ifPresent(name -> {
            nameBar.setValue(name);
            nameBar.setTextColor(0xffffff);
        });
        if (nameBar.getValue().equals(nameLabel.getString())) {
            nameBar.setValue(defaultName.getString());
        }

        searchBar = addRenderableWidget(new SelfClearingEditBox(39 + this.leftPos, 59 + this.topPos, 141, 12, 64, this.searchBar, this.font, this.searchLabel));

        dragPane = new DragPane(this.leftPos, this.topPos, this.width, this.height);
        sourceBox = dragPane.addDropArea(new FilteredFilledDropArea<>(40 + this.leftPos, 5 + this.topPos,
                                                                      138, 48,
                                                                      ICON_SIZE, ICON_SIZE,
                                                                      knowsFilter.and(searchFilter),
                                                                      api.getSpellPartRegistry(),
                                                                      ((Function<ISpellPart, ResourceLocation>)IForgeRegistryEntry::getRegistryName)
                                                                              .andThen(rl -> Pair.of(SkillIconAtlas.instance().getSprite(rl),
                                                                                                     skillManager.get(rl).getDescription()))));
        searchBar.setResponder(s -> sourceBox.update());
        int offsetX = leftPos + SHAPE_GROUP_X;
        for (int sg = 0; sg < menu.allowedShapeGroups(); sg++) {
            dragPane.addDropArea(new BasicDropZone(offsetX + (sg * (SHAPE_GROUP_WIDTH + SHAPE_GROUP_PADDING)), topPos + SHAPE_GROUP_Y, SHAPE_GROUP_WIDTH, SHAPE_GROUP_HEIGHT, ICON_SIZE, ICON_SIZE, 4));
        }
        dragPane.addDropArea(new BasicDropZone(leftPos + 39, topPos + 144, 141, 18, ICON_SIZE, ICON_SIZE, 8));
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
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return this.dragPane.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return super.charTyped(codePoint, modifiers);
    }
}
