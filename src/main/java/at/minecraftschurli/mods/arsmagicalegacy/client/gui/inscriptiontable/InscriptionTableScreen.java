package at.minecraftschurli.mods.arsmagicalegacy.client.gui.inscriptiontable;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.blockentity.InscriptionTableBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.menu.InscriptionTableMenu;
import at.minecraftschurli.mods.arsmagicalegacy.packet.InscriptionTableCreateSpellPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.InscriptionTableSyncPacket;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/inscription_table/background.png");
    private static final Identifier SLOT = ArsMagicaApi.id("textures/gui/inscription_table/slot.png");
    private final List<DragArea> dragAreas = new ArrayList<>();
    private final List<ShapeGroupArea> shapeGroupAreas = new ArrayList<>();
    @Nullable
    private Draggable dragged;
    @Nullable
    private SpellPartSourceArea sourceArea;
    @Nullable
    private GrammarArea grammarArea;
    @Nullable
    private EditBox searchBar;
    @Nullable
    private EditBox nameBar;
    private InscriptionTableBlockEntity.@Nullable MenuData cachedData;

    public InscriptionTableScreen(InscriptionTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 220, 252);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        updateCachedData();
        AMClientUtil.blitFull(graphics, BACKGROUND, leftPos, topPos, imageWidth, imageHeight);
        if (dragged != null) {
            graphics.fill(leftPos, topPos, leftPos + 220, topPos + 165, 0x7f000000);
        }
        AMClientUtil.blit(graphics, SLOT, leftPos + 101, topPos + 73, 18, 18);
        for (DragArea dragArea : dragAreas) {
            dragArea.extractBackground(graphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    protected void init() {
        super.init();
        topPos -= 12;
        shapeGroupAreas.clear();
        dragAreas.clear();
        grammarArea = new GrammarArea(leftPos + 42, topPos + 144, 136, 16, this::onDrop);
        for (int i = 0; i < Spell.MAX_SHAPE_GROUPS; i++) {
            ShapeGroupArea area = new ShapeGroupArea(leftPos + 20 + i * ShapeGroupArea.WIDTH, topPos + 107, this::onDrop);
            area.locked = i >= menu.getShapeGroups();
            shapeGroupAreas.add(area);
        }
        sourceArea = new SpellPartSourceArea(leftPos + 42, topPos + 6, 136, 48, this);
        dragAreas.add(sourceArea);
        dragAreas.add(grammarArea);
        dragAreas.addAll(shapeGroupAreas);
        searchBar = addRenderableWidget(new EditBox(AMClientUtil.font(), leftPos + 40, topPos + 59, 140, 12, searchBar, AMTranslations.INSCRIPTION_TABLE_SEARCH));
        searchBar.setHint(AMTranslations.INSCRIPTION_TABLE_SEARCH);
        searchBar.setResponder(sourceArea::setNameFilter);
        addRenderableWidget(Button.builder(AMTranslations.INSCRIPTION_TABLE_CLEAR, _ -> clear()).bounds(leftPos + 40, topPos + 72, 60, 20).build());
        if (Objects.requireNonNull(AMClientUtil.player()).isCreative()) {
            addRenderableWidget(Button.builder(AMTranslations.INSCRIPTION_TABLE_GIVE_SPELL, _ -> giveSpellRecipe()).bounds(leftPos + 120, topPos + 72, 60, 20).build());
        }
        nameBar = addRenderableWidget(new EditBox(AMClientUtil.font(), leftPos + 40, topPos + 93, 140, 12, nameBar, AMTranslations.INSCRIPTION_TABLE_NAME));
        nameBar.setHint(AMTranslations.INSCRIPTION_TABLE_NAME);
        nameBar.setResponder(_ -> sync());
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, _ -> onClose()).bounds(leftPos + 10, topPos + imageHeight + 4, 200, 20).build());
        updateCachedData();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        for (DragArea area : dragAreas) {
            area.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }
        if (dragged != null) {
            dragged.extractRenderState(graphics, mouseX - Draggable.SIZE / 2, mouseY - Draggable.SIZE / 2, partialTick);
        } else {
            Draggable part = getHoveredSkill(mouseX, mouseY);
            if (part != null) {
                graphics.setTooltipForNextFrame(AMClientUtil.font(), Skill.getName(part.getSkill()), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
    }

    @Nullable
    private DragArea getHoveredArea(int mouseX, int mouseY) {
        return dragAreas.stream().filter(area -> area.isHovered(mouseX, mouseY)).findFirst().orElse(null);
    }

    @Nullable
    private Draggable getHoveredSkill(int mouseX, int mouseY) {
        DragArea area = getHoveredArea(mouseX, mouseY);
        return area == null ? null : area.elementAt(mouseX, mouseY);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (dragged != null) return super.mouseDragged(event, dx, dy);
        int x = (int) event.x();
        int y = (int) event.y();
        DragArea area = getHoveredArea(x, y);
        Draggable skill = getHoveredSkill(x, y);
        if (area == null || skill == null || !area.canPick(skill, x, y)) return super.mouseDragged(event, dx, dy);
        area.pick(skill, x, y);
        setDragged(skill);
        return true;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (dragged == null) return super.mouseReleased(event);
        int x = (int) event.x();
        int y = (int) event.y();
        DragArea area = getHoveredArea(x, y);
        if (area != null && area.canDrop(dragged, x, y)) {
            area.drop(dragged, x, y);
            setDragged(null);
            return true;
        }
        setDragged(null);
        return super.mouseReleased(event);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() == InputConstants.KEY_ESCAPE && shouldCloseOnEsc()) {
            onClose();
            return true;
        }
        if (event.key() == InputConstants.KEY_TAB) {
            boolean flag = !event.hasShiftDown();
            FocusNavigationEvent e = new FocusNavigationEvent.TabNavigation(flag);
            ComponentPath componentPath = nextFocusPath(e);
            if (componentPath != null) {
                changeFocus(componentPath);
            }
            return false;
        }
        if (getFocused() instanceof EditBox editBox) {
            editBox.keyPressed(event);
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public void setFocused(@Nullable GuiEventListener listener) {
        if (getFocused() instanceof EditBox editBox) {
            editBox.setFocused(false);
        }
        super.setFocused(listener);
        if (listener instanceof EditBox editBox) {
            editBox.setFocused(true);
        }
    }

    @Override
    public void onClose() {
        sync();
        super.onClose();
    }

    public GrammarArea getGrammarArea() {
        return Objects.requireNonNull(grammarArea);
    }

    public List<ShapeGroupArea> getShapeGroupAreas() {
        return shapeGroupAreas;
    }

    private void setDragged(@Nullable Draggable dragged) {
        this.dragged = dragged;
        if (grammarArea != null) {
            grammarArea.darken = dragged != null && !grammarArea.canDrop(dragged, grammarArea.x, grammarArea.y);
        }
        shapeGroupAreas.forEach(area -> area.darken = dragged != null && !area.canDrop(dragged, area.x, area.y));
    }

    private void sync() {
        InscriptionTableBlockEntity.MenuData data = new InscriptionTableBlockEntity.MenuData(
            Optional.ofNullable(nameBar).map(EditBox::getValue).map(Component::literal),
            getGrammarArea().getVisible().stream().map(Draggable::getSkill).toList(),
            shapeGroupAreas.stream().map(area -> area.getVisible().stream().map(Draggable::getSkill).toList()).toList());
        menu.getBlockEntity().setMenuData(data);
        ClientPacketDistributor.sendToServer(new InscriptionTableSyncPacket(menu.getBlockEntity().getBlockPos(), data));
    }

    private void onDrop() {
        sync();
        setLocksAndFilters();
    }

    private void giveSpellRecipe() {
        sync();
        ClientPacketDistributor.sendToServer(new InscriptionTableCreateSpellPacket(menu.getBlockEntity().getBlockPos()));
    }

    private void clear() {
        getGrammarArea().getAll().clear();
        shapeGroupAreas.forEach(area -> area.getAll().clear());
        if (searchBar != null) {
            searchBar.setValue("");
        }
        if (nameBar != null) {
            nameBar.setValue("");
        }
        sync();
    }

    @SuppressWarnings("DataFlowIssue")
    private void setLocksAndFilters() {
        sourceArea.setTypeFilter(
            shapeGroupAreas.stream().anyMatch(ShapeGroupArea::isEmpty),
            shapeGroupAreas.stream().anyMatch(e -> !e.isEmpty() && e.isNotFull() && e.getAll().stream().noneMatch(p -> {
                Optional<? extends Holder<SpellPart>> holder = AMRegistries.SPELL_PARTS.get(p.getSkill().getKey().identifier());
                return holder.isPresent() && holder.get().value().isSecondaryShape();
            })),
            grammarArea.isNotFull(),
            (shapeGroupAreas.stream().anyMatch(e -> !e.isEmpty() && e.isNotFull()) || !grammarArea.isEmpty() && grammarArea.isNotFull())
        );
        shapeGroupAreas.forEach(area -> area.locked = true);
        shapeGroupAreas.getFirst().locked = false;
        for (int i = 1; i < Math.min(shapeGroupAreas.size(), menu.getShapeGroups()); i++) {
            shapeGroupAreas.get(i).locked = shapeGroupAreas.get(i - 1).isEmpty();
        }
    }

    private void updateCachedData() {
        InscriptionTableBlockEntity.MenuData data = menu.getBlockEntity().getMenuData();
        if (data == cachedData) return;
        cachedData = data;
        if (nameBar != null) {
            cachedData.name().ifPresent(name -> nameBar.setValue(name.getString()));
        }
        getGrammarArea().setFromData(cachedData);
        for (int i = 0; i < Math.min(cachedData.shapeGroups().size(), shapeGroupAreas.size()); i++) {
            shapeGroupAreas.get(i).setFromData(cachedData.shapeGroups().get(i));
        }
        onDrop();
    }
}
