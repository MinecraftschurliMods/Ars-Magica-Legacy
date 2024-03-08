package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
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
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SpellRecipeScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/spell_recipe.png");
    private static final int WIDTH = 192;
    private static final int HEIGHT = 192;
    private final List<SpellRecipePage> pages = new ArrayList<>();
    private final boolean playTurnSound;
    private final int startPage;
    private final BlockPos lecternPos;
    private int currentPage = -1;
    private int cachedPage = -1;
    private int xPos;
    private PageButton forwardButton;
    private PageButton backButton;

    public SpellRecipeScreen(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        super(Component.empty());
        this.playTurnSound = playTurnSound;
        this.startPage = startPage;
        this.lecternPos = lecternPos;
        ISpell spell = ArsMagicaAPI.get().getSpellHelper().getSpell(stack);
        Objects.requireNonNull(spell);
        pages.add(new IngredientsPage(spell.recipe()));
        List<ItemStack[]> reagents = AMUtil.reagentsToIngredients(spell.reagents(Objects.requireNonNull(ClientHelper.getLocalPlayer())));
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
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderTransparentBackground(graphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        graphics.setColor(1f, 1f, 1f, 1f);
        graphics.blit(BACKGROUND, xPos, 2, 0, 0, WIDTH, HEIGHT);
        Font font = Minecraft.getInstance().font;
        SpellRecipePage page = pages.get(currentPage);
        String title = page.getTitle().getString();
        graphics.drawString(font, title, xPos + 93 - font.width(title) / 2, 18, 0, false);
        page.render(graphics, xPos + 36, 32);
        if (cachedPage != currentPage) {
            cachedPage = currentPage;
        }
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        List<Component> tooltip = page.getTooltip(pMouseX - xPos - 36, pMouseY - 32);
        if (!tooltip.isEmpty()) {
            graphics.renderTooltip(font, tooltip, Optional.empty(), pMouseX, pMouseY);
        }
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
        xPos = (width - WIDTH) / 2;
        Objects.requireNonNull(minecraft);
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, e -> minecraft.setScreen(null)).pos(width / 2 - 100, 196).size(lecternPos == null ? 200 : 98, 20).build());
        if (lecternPos != null) {
            addRenderableWidget(Button.builder(Component.translatable("lectern.take_book"), e -> {
                PacketDistributor.SERVER.noArg().send(new TakeSpellRecipeFromLecternPacket(lecternPos));
                minecraft.setScreen(null);
            }).pos(this.width / 2 + 2, 196).size(98, 20).build());
        }
        forwardButton = addRenderableWidget(new PageButton(xPos + 116, 159, true, p -> setPage(currentPage + 1), playTurnSound));
        backButton = addRenderableWidget(new PageButton(xPos + 43, 159, false, p -> setPage(currentPage - 1), playTurnSound));
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
            PacketDistributor.SERVER.noArg().send(new SetLecternPagePacket(lecternPos, currentPage));
        }
        return true;
    }

    private static abstract class SpellRecipePage {
        protected abstract Component getTitle();

        protected abstract void render(GuiGraphics graphics, int x, int y);

        protected abstract List<Component> getTooltip(int mouseX, int mouseY);

        /**
         * @param x          The x value to compare against.
         * @param y          The y value to compare against.
         * @param size       The size of an element.
         * @param spacing    The spacing between elements.
         * @param maxPerLine The max elements per line.
         * @param maxTotal   The total max amounts of elements.
         * @return The tooltip index of the position (as given by x and y), or -1 if it exceeds the maxTotal value.
         */
        protected int getTooltipIndex(int x, int y, int size, int spacing, int maxPerLine, int maxTotal) {
            int resX = -1, resY = -1;
            for (int i = 0; i < maxPerLine; i++) {
                int min = i * size + i * spacing;
                int max = size + i * size + i * spacing;
                if (x >= min && x < max) {
                    resX = i;
                }
                if (y >= min && y < max) {
                    resY = i;
                }
            }
            if (resX == -1 || resY == -1) return -1;
            int result = resX + resY * maxPerLine;
            return result >= maxTotal ? -1 : result;
        }
    }

    private static final class ShapeGroupPage extends SpellRecipePage {
        private static final int X_OFFSET = 3;
        private static final int Y_OFFSET = 11;
        private static final int SIZE = 32;
        private static final int SPACING = 4;
        private static final int MAX_PER_LINE = 3;
        private final List<ISpellPart> spellParts;
        private final Component title;

        private ShapeGroupPage(List<ISpellPart> spellParts, int index) {
            this.spellParts = spellParts;
            this.title = Component.translatable(TranslationConstants.SPELL_RECIPE_SHAPE_GROUP, index);
        }

        @Override
        protected Component getTitle() {
            return title;
        }

        @Override
        protected void render(GuiGraphics graphics, int x, int y) {
            for (int i = 0; i < spellParts.size(); i++) {
                ClientHelper.drawSpellPart(graphics, spellParts.get(i), x + X_OFFSET + i % MAX_PER_LINE * (SIZE + SPACING), y + Y_OFFSET + i / MAX_PER_LINE * (SIZE + SPACING), SIZE, SIZE);
            }
        }

        @Override
        protected List<Component> getTooltip(int mouseX, int mouseY) {
            int i = getTooltipIndex(mouseX - X_OFFSET, mouseY - Y_OFFSET, SIZE, SPACING, MAX_PER_LINE, spellParts.size());
            RegistryAccess registryAccess = ClientHelper.getRegistryAccess();
            return i == -1 ? List.of() : List.of(registryAccess.registryOrThrow(Skill.REGISTRY_KEY).get(Objects.requireNonNull(spellParts.get(i).getId())).getDisplayName(registryAccess));
        }
    }

    private static final class SpellGrammarPage extends SpellRecipePage {
        private static final Component TITLE = Component.translatable(TranslationConstants.SPELL_RECIPE_SPELL_GRAMMAR);
        private static final int X_OFFSET = 3;
        private static final int Y_OFFSET = 11;
        private static final int SIZE = 32;
        private static final int SPACING = 4;
        private static final int MAX_PER_LINE = 3;
        private final List<ISpellPart> spellParts;

        private SpellGrammarPage(List<ISpellPart> spellParts) {
            this.spellParts = spellParts;
        }

        @Override
        protected Component getTitle() {
            return TITLE;
        }

        @Override
        protected void render(GuiGraphics graphics, int x, int y) {
            for (int i = 0; i < spellParts.size(); i++) {
                ClientHelper.drawSpellPart(graphics, spellParts.get(i), x + X_OFFSET + i % MAX_PER_LINE * (SIZE + SPACING), y + Y_OFFSET + i / MAX_PER_LINE * (SIZE + SPACING), SIZE, SIZE);
            }
        }

        @Override
        protected List<Component> getTooltip(int mouseX, int mouseY) {
            int i = getTooltipIndex(mouseX - X_OFFSET, mouseY - Y_OFFSET, SIZE, SPACING, MAX_PER_LINE, spellParts.size());
            RegistryAccess registryAccess = ClientHelper.getRegistryAccess();
            return i == -1 ? List.of() : List.of(registryAccess.registryOrThrow(Skill.REGISTRY_KEY).get(Objects.requireNonNull(spellParts.get(i).getId())).getDisplayName(registryAccess));
        }
    }

    private static final class IngredientsPage extends SpellRecipePage {
        private static final Component TITLE = Component.translatable(TranslationConstants.SPELL_RECIPE_INGREDIENTS);
        private static final int X_OFFSET = 5;
        private static final int Y_OFFSET = 13;
        private static final int SIZE = 16;
        private static final int SPACING = 6;
        private static final int MAX_PER_LINE = 5;
        private final List<ISpellIngredient> ingredients;

        private IngredientsPage(List<ISpellIngredient> ingredients) {
            this.ingredients = AMUtil.combineSpellIngredients(ingredients);
        }

        @Override
        protected Component getTitle() {
            return TITLE;
        }

        @Override
        protected void render(GuiGraphics graphics, int x, int y) {
            for (int i = 0; i < ingredients.size(); i++) {
                ISpellIngredient ingredient = ingredients.get(i);
                ISpellIngredientRenderer.getFor(ingredient.getType()).renderInGui(ingredient, graphics, x + X_OFFSET + i % MAX_PER_LINE * (SIZE + SPACING), y + Y_OFFSET + i / MAX_PER_LINE * (SIZE + SPACING), 0, 0);
            }
        }

        @Override
        protected List<Component> getTooltip(int mouseX, int mouseY) {
            int i = getTooltipIndex(mouseX - X_OFFSET, mouseY - Y_OFFSET, SIZE, SPACING, MAX_PER_LINE, ingredients.size());
            return i == -1 ? List.of() : ingredients.get(i).getTooltip();
        }
    }

    private static final class ReagentsPage extends SpellRecipePage {
        private static final Component TITLE = Component.translatable(TranslationConstants.SPELL_RECIPE_REAGENTS);
        private static final int X_OFFSET = 5;
        private static final int Y_OFFSET = 13;
        private static final int SIZE = 16;
        private static final int SPACING = 6;
        private static final int MAX_PER_LINE = 5;
        private final List<ItemStack[]> reagents;

        private ReagentsPage(List<ItemStack[]> reagents) {
            this.reagents = reagents;
        }

        @Override
        protected Component getTitle() {
            return TITLE;
        }

        @Override
        protected void render(GuiGraphics graphics, int x, int y) {
            for (int i = 0; i < reagents.size(); i++) {
                ClientHelper.drawItemStack(graphics, AMUtil.getByTick(reagents.get(i), Objects.requireNonNull(ClientHelper.getLocalPlayer()).tickCount / 20).copy(), x + X_OFFSET + i % MAX_PER_LINE * (SIZE + SPACING), y + Y_OFFSET + i / MAX_PER_LINE * (SIZE + SPACING));
            }
        }

        @Override
        protected List<Component> getTooltip(int mouseX, int mouseY) {
            int i = getTooltipIndex(mouseX - X_OFFSET, mouseY - Y_OFFSET, SIZE, SPACING, MAX_PER_LINE, reagents.size());
            return i == -1 ? List.of() : AMUtil.getByTick(reagents.get(i), Objects.requireNonNull(ClientHelper.getLocalPlayer()).tickCount / 20).copy().getTooltipLines(ClientHelper.getLocalPlayer(), TooltipFlag.Default.NORMAL);
        }
    }

    private static final class AffinityPage extends SpellRecipePage {
        private static final Component TITLE = Component.translatable(TranslationConstants.SPELL_RECIPE_AFFINITIES);
        private static final int X_OFFSET = 5;
        private static final int Y_OFFSET = 13;
        private static final int SIZE = 16;
        private static final int SPACING = 6;
        private static final int MAX_PER_LINE = 1;
        private final List<Pair<Affinity, Double>> affinities;

        private AffinityPage(Map<Affinity, Double> affinities) {
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
        protected void render(GuiGraphics graphics, int x, int y) {
            var helper = ArsMagicaAPI.get().getAffinityHelper();
            for (int i = 0; i < affinities.size(); i++) {
                Pair<Affinity, Double> pair = affinities.get(i);
                Affinity affinity = pair.getFirst();
                ClientHelper.drawItemStack(graphics, helper.getEssenceForAffinity(affinity), x + X_OFFSET, y + Y_OFFSET + i * (SIZE + SPACING));
                graphics.drawString(Minecraft.getInstance().font, "%.3f".formatted(pair.getSecond()), x + X_OFFSET + SIZE + SPACING, y + Y_OFFSET + 4 + i * (SIZE + SPACING), affinity.color(), false);
            }
        }

        @Override
        protected List<Component> getTooltip(int mouseX, int mouseY) {
            int i = getTooltipIndex(mouseX - X_OFFSET, mouseY - Y_OFFSET, SIZE, SPACING, MAX_PER_LINE, affinities.size());
            return i == -1 ? List.of() : List.of(affinities.get(i).getFirst().getDisplayName());
        }
    }
}
