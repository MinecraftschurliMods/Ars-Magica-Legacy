package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SetLecternPagePacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.TakeSpellRecipeFromLecternPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpellRecipeScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/spell_recipe.png");
    private final List<SpellRecipePage> pages = new ArrayList<>();
    private final boolean playTurnSound;
    private final int startPage;
    private final BlockPos lecternPos;
    private int currentPage = -1;
    private int cachedPage = -1;
    private PageButton forwardButton;
    private PageButton backButton;

    public SpellRecipeScreen(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        super(TextComponent.EMPTY);
        this.playTurnSound = playTurnSound;
        this.startPage = startPage;
        this.lecternPos = lecternPos;
        ISpell spell = ArsMagicaAPI.get().getSpellHelper().getSpell(stack);
        Objects.requireNonNull(spell);
        pages.add(new IngredientsPage(spell.recipe()));
        List<Ingredient> reagents = AMUtil.reagentsToIngredients(spell.reagents(Objects.requireNonNull(ClientHelper.getLocalPlayer())));
        if (!reagents.isEmpty()) {
            pages.add(new ReagentsPage(reagents));
        }
        List<ShapeGroup> shapeGroups = spell.shapeGroups();
        for (int i = 0; i < shapeGroups.size(); i++) {
            ShapeGroup shapeGroup = shapeGroups.get(i);
            if (!shapeGroup.isEmpty()) {
                pages.add(new ShapeGroupPage(shapeGroup.parts(), i + 1));
            }
        }
        pages.add(new SpellGrammarPage(spell.spellStack().parts()));
        pages.add(new AffinityPage(spell.affinityShifts()));
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        blit(pPoseStack, (width - 192) / 2, 2, 0, 0, 192, 192);
        Font font = Minecraft.getInstance().font;
        String title = pages.get(currentPage).getTitle().getString();
        font.draw(pPoseStack, title, (width - 192) / 2f + 93 - font.width(title) / 2f, 18, 0);
        pages.get(currentPage).render(pPoseStack, (width - 192) / 2 + 36, 32);
        if (cachedPage != currentPage) {
            cachedPage = currentPage;
        }
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
                ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new TakeSpellRecipeFromLecternPacket(lecternPos));
                minecraft.setScreen(null);
            }));
        }
        forwardButton = addRenderableWidget(new PageButton((width - 192) / 2 + 116, 159, true, p -> setPage(currentPage + 1), playTurnSound));
        backButton = addRenderableWidget(new PageButton((width - 192) / 2 + 43, 159, false, p -> setPage(currentPage - 1), playTurnSound));
        setPage(startPage);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private boolean setPage(int pPageNum) {
        int i = Mth.clamp(pPageNum, 0, pages.size() - 1);
        if (i == currentPage) return false;
        currentPage = i;
        cachedPage = -1;
        forwardButton.visible = currentPage < pages.size() - 1;
        backButton.visible = currentPage > 0;
        if (lecternPos != null) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new SetLecternPagePacket(lecternPos, currentPage));
        }
        return true;
    }

    private static abstract class SpellRecipePage {
        protected abstract Component getTitle();

        protected abstract void render(PoseStack poseStack, int x, int y);
    }

    private static final class ShapeGroupPage extends SpellRecipePage {
        private final List<ISpellPart> spellParts;
        private final Component title;

        private ShapeGroupPage(List<ISpellPart> spellParts, int index) {
            this.spellParts = spellParts;
            this.title = new TranslatableComponent(TranslationConstants.SPELL_RECIPE_SHAPE_GROUP, index);
        }

        @Override
        protected Component getTitle() {
            return title;
        }

        @Override
        protected void render(PoseStack poseStack, int x, int y) {
            for (int i = 0; i < spellParts.size(); i++) {
                ClientHelper.drawSpellPart(poseStack, spellParts.get(i), x + 3 + i % 3 * 36, y + 11 + i / 3 * 36, 32, 32);
            }
        }
    }

    private static final class SpellGrammarPage extends SpellRecipePage {
        private static final Component TITLE = new TranslatableComponent(TranslationConstants.SPELL_RECIPE_SPELL_GRAMMAR);
        private final List<ISpellPart> spellParts;

        private SpellGrammarPage(List<ISpellPart> spellParts) {
            this.spellParts = spellParts;
        }

        @Override
        protected Component getTitle() {
            return TITLE;
        }

        @Override
        protected void render(PoseStack poseStack, int x, int y) {
            for (int i = 0; i < spellParts.size(); i++) {
                ClientHelper.drawSpellPart(poseStack, spellParts.get(i), x + 3 + i % 3 * 36, y + 11 + i / 3 * 36, 32, 32);
            }
        }
    }

    private static final class IngredientsPage extends SpellRecipePage {
        private static final Component TITLE = new TranslatableComponent(TranslationConstants.SPELL_RECIPE_INGREDIENTS);
        private final List<ISpellIngredient> ingredients;

        private IngredientsPage(List<ISpellIngredient> ingredients) {
            this.ingredients = AMUtil.combineSpellIngredients(ingredients);
        }

        @Override
        protected Component getTitle() {
            return TITLE;
        }

        @Override
        protected void render(PoseStack poseStack, int x, int y) {
            var manager = ArsMagicaAPI.get().getSpellDataManager();
            for (int i = 0; i < ingredients.size(); i++) {
                ISpellIngredient ingredient = ingredients.get(i);
                manager.getSpellIngredientRenderer(ingredient.getType()).renderInGui(ingredient, poseStack, x + 5 + i % 5 * 22, y + 13 + i / 5 * 22, 0, 0);
            }
        }
    }

    private static final class ReagentsPage extends SpellRecipePage {
        private static final Component TITLE = new TranslatableComponent(TranslationConstants.SPELL_RECIPE_REAGENTS);
        private final List<Ingredient> reagents;

        private ReagentsPage(List<Ingredient> reagents) {
            this.reagents = AMUtil.combineIngredients(reagents);
        }

        @Override
        protected Component getTitle() {
            return TITLE;
        }

        @Override
        protected void render(PoseStack poseStack, int x, int y) {
            for (int i = 0; i < reagents.size(); i++) {
                ItemStack stack = AMUtil.getByTick(reagents.get(i).getItems(), Objects.requireNonNull(ClientHelper.getLocalPlayer()).tickCount / 20).copy();
                ClientHelper.drawItemStack(poseStack, stack, x + 5 + i % 5 * 22, y + 13 + i / 5 * 22);
            }
        }
    }

    private static final class AffinityPage extends SpellRecipePage {
        private static final Component TITLE = new TranslatableComponent(TranslationConstants.SPELL_RECIPE_AFFINITIES);
        private final List<Pair<IAffinity, Double>> affinities;

        private AffinityPage(Map<IAffinity, Double> affinities) {
            this.affinities = affinities.keySet()
                    .stream()
                    .map(e -> new Pair<>(e, affinities.get(e)))
                    .sorted(Comparator.comparing(Pair::getFirst))
                    .sorted(Comparator.comparing(Pair::getSecond))
                    .toList();
        }

        @Override
        protected Component getTitle() {
            return TITLE;
        }

        @Override
        protected void render(PoseStack poseStack, int x, int y) {
            var helper = ArsMagicaAPI.get().getAffinityHelper();
            for (int i = 0; i < affinities.size(); i++) {
                Pair<IAffinity, Double> pair = affinities.get(i);
                IAffinity affinity = pair.getFirst();
                ClientHelper.drawItemStack(poseStack, helper.getEssenceForAffinity(affinity), x + 5, y + 13 + i / 5 * 22);
                Minecraft.getInstance().font.draw(poseStack, "%.3f".formatted(pair.getSecond()), x + 27, y + 17 + i / 5 * 22, affinity.getColor());
            }
        }
    }
}
