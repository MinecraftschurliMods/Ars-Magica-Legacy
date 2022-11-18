package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpellRecipeScreen extends Screen {
    private final List<SpellRecipePage> pages = new ArrayList<>();
    private final boolean playTurnSound;
    private final int startPage;
    private final BlockPos lecternPos;
    private int currentPage = -1;
    private int cachedPage = -1;
    private Component pageMsg = Component.nullToEmpty("");
    private PageButton forwardButton;
    private PageButton backButton;

    public SpellRecipeScreen(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        super(TextComponent.EMPTY);
        this.playTurnSound = playTurnSound;
        this.startPage = startPage;
        this.lecternPos = lecternPos;
        ISpell spell = ArsMagicaAPI.get().getSpellHelper().getSpell(stack);
        for (ShapeGroup shapeGroup : Objects.requireNonNull(spell).shapeGroups()) {
            if (!shapeGroup.isEmpty()) {
                pages.add(new ShapeGroupPage(shapeGroup));
            }
        }
        pages.add(new SpellGrammarPage(spell.parts()));
        pages.add(new IngredientsPage(spell.recipe()));
        pages.add(new AffinityPage(spell.affinityShifts()));
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, BookViewScreen.BOOK_LOCATION);
        blit(pPoseStack, (width - 192) / 2, 2, 0, 0, 192, 192);
        pages.get(currentPage).render(pPoseStack, (width - 192) / 2 + 36, 32);
        if (cachedPage != currentPage) {
            pageMsg = new TranslatableComponent("book.pageIndicator", currentPage + 1, Math.max(pages.size(), 1));
            cachedPage = currentPage;
        }
        font.draw(pPoseStack, pageMsg, (float) ((width - 192) / 2 - font.width(pageMsg) + 192 - 44), 18, 0);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (super.keyPressed(pKeyCode, pScanCode, pModifiers)) return true;
        if (pKeyCode == GLFW.GLFW_KEY_PAGE_UP) {
            backButton.onPress();
            return true;
        }
        if (pKeyCode == GLFW.GLFW_KEY_PAGE_DOWN) {
            forwardButton.onPress();
            return true;
        }
        return false;
    }

    @Override
    public boolean handleComponentClicked(@Nullable Style pStyle) {
        if (pStyle == null) return false;
        ClickEvent clickevent = pStyle.getClickEvent();
        if (clickevent == null) return false;
        if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            try {
                return setPage(Integer.parseInt(clickevent.getValue()) - 1);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return super.handleComponentClicked(pStyle);
    }

    @Override
    protected void init() {
        Objects.requireNonNull(minecraft);
        addRenderableWidget(new Button(width / 2 - 100, 196, lecternPos == null ? 200 : 98, 20, CommonComponents.GUI_DONE, e -> minecraft.setScreen(null)));
        if (lecternPos != null) {
            addRenderableWidget(new Button(this.width / 2 + 2, 196, 98, 20, new TranslatableComponent("lectern.take_book"), e -> {
                //ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new TakeSpellRecipeFromLecternPacket(lecternPos));
                minecraft.setScreen(null);
            }));
        }
        forwardButton = addRenderableWidget(new PageButton((width - 192) / 2 + 116, 159, true, p -> setPage(currentPage + 1), playTurnSound));
        backButton = addRenderableWidget(new PageButton((width - 192) / 2 + 43, 159, false, p -> setPage(currentPage - 1), playTurnSound));
        setPage(startPage);
    }

    @Override
    public boolean isPauseScreen() {
        return lecternPos == null;
    }

    private boolean setPage(int pPageNum) {
        int i = Mth.clamp(pPageNum, 0, pages.size() - 1);
        if (i == currentPage) return false;
        currentPage = i;
        cachedPage = -1;
        forwardButton.visible = currentPage < pages.size() - 1;
        backButton.visible = currentPage > 0;
        if (lecternPos != null) {
            //ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new SetLecternPagePacket(lecternPos, currentPage));
        }
        return true;
    }

    private static abstract sealed class SpellRecipePage permits ShapeGroupPage, SpellGrammarPage, IngredientsPage, AffinityPage {
        protected abstract void render(PoseStack poseStack, int x, int y);
    }

    private static final class ShapeGroupPage extends SpellRecipePage {
        private final ShapeGroup shapeGroup;

        private ShapeGroupPage(ShapeGroup shapeGroup) {
            this.shapeGroup = shapeGroup;
        }

        @Override
        protected void render(PoseStack poseStack, int x, int y) {
        }
    }

    private static final class SpellGrammarPage extends SpellRecipePage {
        private final List<ISpellPart> spellParts;

        private SpellGrammarPage(List<ISpellPart> spellParts) {
            this.spellParts = spellParts;
        }

        @Override
        protected void render(PoseStack poseStack, int x, int y) {
        }
    }

    private static final class IngredientsPage extends SpellRecipePage {
        private final List<ISpellIngredient> ingredients;

        private IngredientsPage(List<ISpellIngredient> ingredients) {
            this.ingredients = ingredients;
        }

        @Override
        protected void render(PoseStack poseStack, int x, int y) {
        }
    }

    private static final class AffinityPage extends SpellRecipePage {
        private final Map<IAffinity, Double> affinities;

        private AffinityPage(Map<IAffinity, Double> affinities) {
            this.affinities = affinities;
        }

        @Override
        protected void render(PoseStack poseStack, int x, int y) {
        }
    }
}
