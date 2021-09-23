package com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.OcculusTab;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class OcculusScreen extends Screen {
    private static final ResourceLocation SKILL_POINT_BG = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/skill_points.png");
    private static final ResourceLocation OVERLAY = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/overlay.png");

    private static final Map<ResourceLocation, BiFunction<IOcculusTab, Player, OcculusTabRenderer>> RENDERERS = new HashMap<>();
    private static final Component TITLE = new TranslatableComponent("gui.%s.occulus".formatted(ArsMagicaAPI.MOD_ID));

    private final int xSize = 210;
    private final int ySize = 210;
    private final Player player;
    private int maxPage;
    private int page;
    private Button prevPage;
    private Button nextPage;
    private OcculusTabRenderer activeTab;
    private int posX;
    private int posY;

    public OcculusScreen(Player player) {
        super(TITLE);
        this.player = player;
    }

    @Override
    protected void init() {
        posX = width / 2 - xSize / 2;
        posY = height / 2 - ySize / 2;
        var registry = ArsMagicaAPI.get().getOcculusTabRegistry();
        for (IOcculusTab tab : registry) {
            int tabIndex = tab.getOcculusIndex();
            Button.OnPress onPress = pButton -> setActiveTab(tabIndex);
            int tabSize = 22;
            if (tabIndex % 16 < 8) {
                addRenderableWidget(new OcculusTabButton(tabIndex, posX + 7 + ((tabIndex % 16) * (tabSize + 2)), posY - tabSize, false, tab, onPress));
            } else {
                addRenderableWidget(new OcculusTabButton(tabIndex, posX + 7 + (((tabIndex % 16) - 8) * (tabSize + 2)), posY + ySize, true, tab, onPress));
            }
        }

        maxPage = (int) Math.floor((float) (registry.getValues().size() - 1) / 16F);
        nextPage = new Button(posX + 212, posY - 21, 20, 20, new TextComponent(">"), this::nextPage);
        prevPage = new Button(posX - 15, posY - 21, 20, 20, new TextComponent("<"), this::prevPage);
        nextPage.active = page < maxPage;
        prevPage.active = false;
        addRenderableWidget(nextPage);
        addRenderableWidget(prevPage);

        setActiveTab(0);
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
        IOcculusTab tab = OcculusTab.TAB_BY_INDEX.get(tabIndex);
        Optional.ofNullable(RENDERERS.get(tab.getId()))
                .map(factory -> factory.apply(tab, this.player))
                .ifPresent(occulusTabRenderer -> {
                    removeWidget(this.activeTab);
                    this.activeTab = occulusTabRenderer;
                    addRenderableWidget(this.activeTab);
                });
        activeTab.init(getMinecraft(), width, height);
    }

    @Override
    public void render(@NotNull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        renderBg(pMatrixStack, posX, posY);
        drawSkillPointBackground(pMatrixStack, posX, posY);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
    }

    private void renderBg(PoseStack pMatrixStack, int posX, int posY) {
        setBlitOffset(-5);
        RenderSystem.setShaderTexture(0, OVERLAY);
        blit(pMatrixStack, posX, posY, 0, 0, xSize, ySize);
    }

    private void drawSkillPointBackground(PoseStack stack, int startX, int startY) {
        var api = ArsMagicaAPI.get();
        var knowledgeHelper = api.getKnowledgeHelper();
        int maxSize = api.getSkillPointRegistry()
                .getValues()
                .stream()
                .filter(ISkillPoint::canRenderInGui)
                .map(point -> point.getDisplayName().copy().append(new TextComponent(" : " + knowledgeHelper.getSkillPoint(player, point))))
                .mapToInt(font::width)
                .max()
                .orElse(0);
        int width = maxSize + 10;
        int height = ySize;
        setBlitOffset(-1);
        RenderSystem.setShaderTexture(0, SKILL_POINT_BG);
        int posX = xSize;
        int posY = 0;
        blit(stack, startX + posX + width - 4, startY + posY, 252, 0, 4, 4);
        blit(stack, startX + posX + width - 4, startY + posY + height - 4, 252, 252, 4, 4);
        int w = width - 4;
        int h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            while (h > 0) {
                int y = Math.min(h, 248);
                blit(stack, startX + posX + w - x, startY + posY + 4 + h - y, 4, 4, x, y);
                h -= y;
            }
            w -= x;
        }
        w = width - 4;
        h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            blit(stack, startX + posX + w - x, startY + posY, 4, 0, x, 4);
            blit(stack, startX + posX + w - x, startY + posY + height - 4, 4, 252, x, 4);
            w -= x;
        }
        while (h > 0) {
            int y = Math.min(h, 248);
            blit(stack, startX + posX + width - 4, startY + posY + 4 + h - y, 252, 4, 4, y);
            h -= y;
        }
    }

    public static void registerRenderer(IOcculusTab tab, BiFunction<IOcculusTab, Player, OcculusTabRenderer> factory) {
        RENDERERS.put(tab.getId(), factory);
    }
}
