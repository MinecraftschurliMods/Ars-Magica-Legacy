package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SetLecternPagePacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.TakeSpellRecipeFromLecternPacket;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SpellRecipeScreen extends Screen {
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/spell_recipe.png");
    private static final int WIDTH = 192;
    private static final int HEIGHT = 192;
    private final List<Page<?>> pages = new ArrayList<>();
    private final boolean playTurnSound;
    private final int startPage;
    @Nullable
    private final BlockPos lecternPos;
    private int currentPage = -1;
    private int cachedPage = -1;
    private int xPos;
    @Nullable
    private PageButton forwardButton;
    @Nullable
    private PageButton backButton;

    public SpellRecipeScreen(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        super(stack.getDisplayName());
        this.playTurnSound = playTurnSound;
        this.startPage = startPage;
        this.lecternPos = lecternPos;
        RegistryAccess registryAccess = Objects.requireNonNull(AMClientUtil.level()).registryAccess();
        Spell spell = stack.getOrDefault(AMDataComponents.SPELL, Spell.EMPTY);
        pages.add(new IngredientsPage(ArsMagicaApi.spellHelper().getFlatRecipe(spell, registryAccess)));
        List<SpellShapeGroup> shapeGroups = spell.shapeGroups();
        for (int i = 0; i < shapeGroups.size(); i++) {
            SpellShapeGroup shapeGroup = shapeGroups.get(i);
            if (!shapeGroup.isEmpty()) {
                pages.add(new PartsPage(shapeGroup.parts(), Component.translatable(AMTranslations.SPELL_RECIPE_SHAPE_GROUP_KEY, i + 1)));
            }
        }
        pages.add(new PartsPage(spell.grammar().parts(), AMTranslations.SPELL_RECIPE_GRAMMAR));
        pages.add(new AffinityPage(spell.grammar().affinityShifts(registryAccess)));
    }

    @Override
    protected void init() {
        xPos = (width - WIDTH) / 2;
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, _ -> onClose()).bounds(width / 2 - 100, 196, lecternPos == null ? 200 : 98, 20).build());
        if (lecternPos != null) {
            addRenderableWidget(Button.builder(Component.translatable("lectern.take_book"), _ -> {
                ClientPacketDistributor.sendToServer(new TakeSpellRecipeFromLecternPacket(lecternPos));
                onClose();
            }).pos(this.width / 2 + 2, 196).size(98, 20).build());
        }
        forwardButton = addRenderableWidget(new PageButton(xPos + 116, 159, true, _ -> setPage(currentPage + 1), playTurnSound));
        backButton = addRenderableWidget(new PageButton(xPos + 43, 159, false, _ -> setPage(currentPage - 1), playTurnSound));
        setPage(startPage);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        AMClientUtil.blitFull(graphics, BACKGROUND, xPos, 2, WIDTH, HEIGHT);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        Page<?> page = pages.get(currentPage);
        String title = page.getTitle().getString();
        graphics.text(font, title, xPos + 93 - font.width(title) / 2, 18, 0xff000000, false);
        page.extractRenderState(graphics, xPos + 36, 32);
        if (cachedPage != currentPage) {
            cachedPage = currentPage;
        }
        for (Renderable renderable : renderables) {
            renderable.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }
        List<Component> tooltip = page.getTooltip(mouseX - xPos - 36, mouseY - 32);
        if (!tooltip.isEmpty()) {
            graphics.setTooltipForNextFrame(font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (super.keyPressed(event)) return true;
        if (backButton != null && event.key() == GLFW.GLFW_KEY_PAGE_UP) {
            backButton.onPress(event);
            return true;
        }
        if (forwardButton != null && event.key() == GLFW.GLFW_KEY_PAGE_DOWN) {
            forwardButton.onPress(event);
            return true;
        }
        return false;
    }

    private void setPage(int pPageNum) {
        int i = Math.clamp(pPageNum, 0, pages.size() - 1);
        if (i == currentPage) return;
        currentPage = i;
        cachedPage = -1;
        if (forwardButton != null) {
            forwardButton.visible = currentPage < pages.size() - 1;
        }
        if (backButton != null) {
            backButton.visible = currentPage > 0;
        }
        if (lecternPos != null) {
            ClientPacketDistributor.sendToServer(new SetLecternPagePacket(lecternPos, currentPage));
        }
    }
}
