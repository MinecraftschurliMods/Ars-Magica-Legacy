package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.OcculusTab;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Comparator;

public class OcculusScreen extends Screen {
    private static final ResourceLocation OVERLAY = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/overlay.png");
    private static final Component TITLE = Component.translatable("gui.%s.occulus".formatted(ArsMagicaAPI.MOD_ID));
    private final int guiWidth;
    private final int guiHeight;
    private final int tabWidth;
    private final int tabHeight;
    private int posX;
    private int posY;
    private int maxPage;
    private int page;
    private Button prevPage;
    private Button nextPage;
    private OcculusTabRenderer activeTab;
    private int activeTabIndex;

    public OcculusScreen() {
        super(TITLE);
        tabWidth = 196;
        tabHeight = 196;
        guiWidth = 210;
        guiHeight = 210;
        setActiveTab(0);
    }

    @Override
    protected void init() {
        posX = width / 2 - guiWidth / 2;
        posY = height / 2 - guiHeight / 2;
        var registry = minecraft.level.registryAccess().registryOrThrow(OcculusTab.REGISTRY_KEY);
        int tabSize = 22;
        for (OcculusTab tab : registry) {
            int tabIndex = tab.index();
            addRenderableWidget(new OcculusTabButton(tabIndex, 7 + ((tabIndex % 8) * (tabSize + 2)), -tabSize, tab, pButton -> setActiveTab(tabIndex)));
        }
        maxPage = (int) Math.floor((float) (registry.size() - 1) / 16F);
        nextPage = addRenderableWidget(new Button(guiWidth + 2, -21, 20, 20, Component.literal(">"), this::nextPage));
        prevPage = addRenderableWidget(new Button(-15, -21, 20, 20, Component.literal("<"), this::prevPage));
        nextPage.active = page < maxPage;
        prevPage.active = false;
        addRenderableWidget(new SkillPointPanel()).init(getMinecraft(), guiWidth, guiHeight);
        if (activeTab == null) {
            setActiveTab(activeTabIndex);
        }
        addRenderableWidget(activeTab).init(tabWidth, tabHeight, width, height, posX + 7, posY + 7);
    }

    @Override
    public void render(PoseStack stack, int pMouseX, int pMouseY, float pPartialTicks) {
        renderBackground(stack);
        stack.pushPose();
        stack.translate(posX, posY, 0);
        setBlitOffset(-5);
        RenderSystem.setShaderTexture(0, OVERLAY);
        blit(stack, 0, 0, 0, 0, guiWidth, guiHeight);
        super.render(stack, pMouseX, pMouseY, pPartialTicks);
        stack.popPose();
    }

    private void prevPage(Button button) {
        page -= 1;
        if (page <= 0) {
            prevPage.active = false;
        }
        if (page < maxPage) {
            nextPage.active = true;
        }
        onPageChanged();
    }

    private void nextPage(Button button) {
        page += 1;
        if (page >= maxPage) {
            nextPage.active = false;
        }
        if (page > 0) {
            prevPage.active = true;
        }
        onPageChanged();
    }

    private void onPageChanged() {
        for (Widget button : renderables) {
            if (button instanceof OcculusTabButton b) {
                b.visible = (int) Math.floor((float) b.getIndex() / 16F) == page;
            }
        }
    }

    private void setActiveTab(int tabIndex) {
        activeTabIndex = tabIndex;
        if (minecraft != null) {
            clearWidgets();
            Registry<OcculusTab> occulusTabRegistry = minecraft.level.registryAccess().registryOrThrow(OcculusTab.REGISTRY_KEY);
            OcculusTab tab = occulusTabRegistry.stream().sorted(Comparator.comparing(OcculusTab::index)).toArray(OcculusTab[]::new)[tabIndex];
            activeTab = tab.rendererFactory().get().create(tab, this);
            init();
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return super.mouseClicked(pMouseX - posX, pMouseY - posY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        activeTab.setDragging(false);
        return super.mouseReleased(pMouseX - posX, pMouseY - posY, pButton);
    }
}
