package com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.OcculusTabManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class OcculusScreen extends Screen {
    private static final ResourceLocation OVERLAY = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/overlay.png");
    private static final Component TITLE = new TranslatableComponent("gui.%s.occulus".formatted(ArsMagicaAPI.MOD_ID));

    private final int guiWidth;
    private final int guiHeight;
    private final int tabWidth;
    private final int tabHeight;
    private int maxPage;
    private int page;
    private Button prevPage;
    private Button nextPage;
    private OcculusTabRenderer activeTab;
    private int posX;
    private int posY;

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
        var registry = ArsMagicaAPI.get().getOcculusTabManager();
        int tabSize = 22;
        for (IOcculusTab tab : registry.getTabs()) {
            int tabIndex = tab.getOcculusIndex();
            addRenderableWidget(new OcculusTabButton(tabIndex, 7 + ((tabIndex % 8) * (tabSize + 2)), -tabSize, tab, pButton -> setActiveTab(tabIndex)));
        }

        maxPage = (int) Math.floor((float) (registry.getTabs().size() - 1) / 16F);
        nextPage = new Button(guiWidth + 2, -21, 20, 20, new TextComponent(">"), this::nextPage);
        prevPage = new Button(-15, -21, 20, 20, new TextComponent("<"), this::prevPage);
        nextPage.active = page < maxPage;
        prevPage.active = false;
        addRenderableWidget(nextPage);
        addRenderableWidget(prevPage);

        addRenderableWidget(activeTab);
        addRenderableWidget(new SkillPointPanel()).init(getMinecraft(), guiWidth, guiHeight);
        activeTab.init(tabWidth, tabHeight, width, height, posX+7, posY+7);
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
        IOcculusTab tab = OcculusTabManager.instance().getByIndex(tabIndex);
        this.removeWidget(this.activeTab);
        this.activeTab = tab.getRendererFactory().get().create(tab);
        this.addRenderableWidget(this.activeTab);
        if (this.minecraft != null) {
            this.activeTab.init(this.tabWidth, this.tabHeight, this.width, this.height, this.posX+7, this.posY+7);
        }
    }

    @Override
    public void render(@NotNull PoseStack stack, int pMouseX, int pMouseY, float pPartialTicks) {
        stack.pushPose();
        stack.translate(posX, posY, 0);
        setBlitOffset(-5);
        RenderSystem.setShaderTexture(0, OVERLAY);
        blit(stack, 0, 0, 0, 0, guiWidth, guiHeight);
        super.render(stack, pMouseX, pMouseY, pPartialTicks);
        stack.popPose();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return super.mouseClicked(pMouseX-posX, pMouseY-posY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        activeTab.setDragging(false);
        return super.mouseReleased(pMouseX-posX, pMouseY-posY, pButton);
    }
}
