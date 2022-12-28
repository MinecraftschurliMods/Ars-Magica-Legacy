package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis.BasicDropZone;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis.DragPane;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis.Draggable;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis.DraggableWithData;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis.DropArea;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis.DropValidator;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis.FilteredFilledDropArea;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/inscription_table.png");
    private static final Component SEARCH_LABEL = Component.translatable(TranslationConstants.INSCRIPTION_TABLE_SEARCH);
    private static final Component NAME_LABEL = Component.translatable(TranslationConstants.INSCRIPTION_TABLE_NAME);
    private static final Component DEFAULT_NAME = Component.translatable(TranslationConstants.INSCRIPTION_TABLE_DEFAULT_NAME);
    private static final int SHAPE_GROUP_WIDTH = 36;
    private static final int SHAPE_GROUP_HEIGHT = 36;
    private static final int SHAPE_GROUP_PADDING = 3;
    private static final int SHAPE_GROUP_Y = 108;
    private static final int SHAPE_GROUP_X = 13;
    private static final int ICON_SIZE = 16;
    private final List<BasicDropZone> shapeGroupDropZones = new ArrayList<>();
    private EditBox searchBar;
    private EditBox nameBar;
    private DragPane dragPane;
    private BasicDropZone spellStackDropZone;

    public InscriptionTableScreen(InscriptionTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageWidth = 220;
        imageHeight = 252;
        nameBar = new EditBox(font, 0, 0, 0, 0, NAME_LABEL);
        pMenu.getSpellName().ifPresent(pText -> nameBar.setValue(pText.getString()));
        pMenu.getSpellRecipe().ifPresent(this::setFromRecipe);
    }

    public static void onSlotChanged() {
        if (Minecraft.getInstance().screen instanceof InscriptionTableScreen inscriptionTableScreen) {
            inscriptionTableScreen.onSlotChangedInt();
        }
    }

    private void onSlotChangedInt() {
        sync();
        if (menu.getSlot(0).getItem().getItem() instanceof ISpellItem) {
            menu.getSpellRecipe().ifPresent(this::setFromRecipe);
        }
    }

    @Override
    protected void init() {
        super.init();
        var api = ArsMagicaAPI.get();
        var registryAccess = getMinecraft().getConnection().registryAccess();
        var skillRegistry = registryAccess.registryOrThrow(Skill.REGISTRY_KEY);
        var spellPartRegistry = api.getSpellPartRegistry();
        Predicate<ResourceLocation> searchFilter = spellPart -> {
            String value = searchBar.getValue();
            if (StringUtil.isNullOrEmpty(value) || value.equals(SEARCH_LABEL.getString())) return true;
            Skill skill = skillRegistry.get(spellPart);
            if (skill == null) return false;
            return StringUtils.containsIgnoreCase(skill.getDisplayName(registryAccess).getString(), value);
        };
        Predicate<ResourceLocation> knowsFilter = spellPart -> {
            assert Minecraft.getInstance().player != null;
            return api.getSkillHelper().knows(Minecraft.getInstance().player, spellPart);
        };
        Predicate<ResourceLocation> hasValidPlace = spellPart -> {
            ISpellPart value = spellPartRegistry.getValue(spellPart);
            assert value != null;
            return shapeGroupDropZones
                    .stream()
                    .map(basicDropZone -> DraggableWithData.<ResourceLocation>dataList(basicDropZone.items())
                            .stream()
                            .map(spellPartRegistry::getValue)
                            .filter(Objects::nonNull)
                            .toList())
                    .anyMatch(iSpellParts -> isValidInShapeGroup(iSpellParts, value)) || isValidInSpellStack(DraggableWithData.<ResourceLocation>dataList(spellStackDropZone.items())
                    .stream()
                    .map(spellPartRegistry::getValue)
                    .filter(Objects::nonNull)
                    .toList(), value);
        };
        nameBar = addRenderableWidget(new SelfClearingEditBox(39 + leftPos, 93 + topPos, 141, 12, 64, nameBar, font, NAME_LABEL));
        menu.getSpellName().ifPresent(name -> {
            nameBar.setValue(name.getString());
            nameBar.setTextColor(0xffffff);
        });
        if (nameBar.getValue().equals(NAME_LABEL.getString())) {
            nameBar.setValue(DEFAULT_NAME.getString());
            nameBar.setTextColor(0xffffff);
        }
        searchBar = addRenderableWidget(new SelfClearingEditBox(39 + leftPos, 59 + topPos, 141, 12, 64, searchBar, font, SEARCH_LABEL));
        dragPane = new DragPane(leftPos, topPos, width, height);
        FilteredFilledDropArea<ResourceLocation> sourceBox = dragPane.addDropArea(new FilteredFilledDropArea<>(40 + leftPos, 5 + topPos, 138, 48, ICON_SIZE, ICON_SIZE, knowsFilter.and(searchFilter).and(hasValidPlace), spellPartRegistry.getValues()
                .stream()
                .sorted(Comparator.comparing(ISpellPart::getType).thenComparing(ISpellPart::getId))
                .map(ISpellPart::getId)
                .toList(), rl -> Pair.of(SkillIconAtlas.instance().getSprite(rl), skillRegistry.get(rl).getDisplayName(registryAccess))));
        searchBar.setResponder(s -> sourceBox.update());
        int offsetX = leftPos + SHAPE_GROUP_X;
        DropValidator.WithData<ResourceLocation> dropValidator = ((DropValidator.WithData<ISpellPart>) this::isValidInShapeGroup).map(spellPartRegistry::getValue);
        for (int sg = 0; sg < menu.allowedShapeGroups(); sg++) {
            BasicDropZone old = shapeGroupDropZones.size() > sg ? shapeGroupDropZones.get(sg) : null;
            BasicDropZone shapeGroupDropZone = dragPane.addDropArea(new BasicDropZone(offsetX + (sg * (SHAPE_GROUP_WIDTH + SHAPE_GROUP_PADDING)), topPos + SHAPE_GROUP_Y, SHAPE_GROUP_WIDTH, SHAPE_GROUP_HEIGHT, ICON_SIZE, ICON_SIZE, 1, 4, old));
            shapeGroupDropZone.setDropValidator(dropValidator);
            shapeGroupDropZone.setOnDropListener(p -> sourceBox.update());
            shapeGroupDropZone.setOnDragListener(p -> sourceBox.update());
            if (shapeGroupDropZones.size() > sg) {
                shapeGroupDropZones.set(sg, shapeGroupDropZone);
            } else {
                shapeGroupDropZones.add(shapeGroupDropZone);
            }
        }
        spellStackDropZone = dragPane.addDropArea(new BasicDropZone(leftPos + 39, topPos + 143, 141, 18, ICON_SIZE, ICON_SIZE, 2, 8, spellStackDropZone));
        spellStackDropZone.setDropValidator(((DropValidator.WithData<ISpellPart>) this::isValidInSpellStack).map(spellPartRegistry::getValue));
        spellStackDropZone.setOnDropListener(p -> sourceBox.update());
        spellStackDropZone.setOnDragListener(p -> sourceBox.update());
        addRenderableWidget(dragPane);
        sourceBox.update();
        if (getMinecraft().player.isCreative()) {
            addRenderableWidget(new Button(leftPos + imageWidth + 5, topPos + 5, 100, 20, Component.translatable(TranslationConstants.INSCRIPTION_TABLE_CREATE_SPELL), button -> {
                sync();
                menu.createSpell();
            }));
        }
    }

    private boolean isValidInSpellStack(List<ISpellPart> items, ISpellPart item) {
        Function<ResourceLocation, ISpellPart> registryAccess = ArsMagicaAPI.get().getSpellPartRegistry()::getValue;
        Function<BasicDropZone, List<Draggable>> itemGetter = BasicDropZone::items;
        Function<List<Draggable>, List<ResourceLocation>> draggableMapper = DraggableWithData::dataList;
        Function<List<ResourceLocation>, List<ISpellPart>> registryMapper = rls -> rls.stream().map(registryAccess).toList();
        Function<BasicDropZone, List<ISpellPart>> mapper = itemGetter.andThen(draggableMapper).andThen(registryMapper);
        Predicate<List<ISpellPart>> isValidInShapeGroup = iSpellParts -> isValidInShapeGroup(iSpellParts, item);
        boolean shapeGroupsEmpty = shapeGroupDropZones.stream().map(itemGetter).allMatch(List::isEmpty);
        return switch (item.getType()) {
            case COMPONENT -> true;
            case MODIFIER -> {
                Deque<ISpellPart> deque = new ArrayDeque<>(items);
                for (Iterator<ISpellPart> it = deque.descendingIterator(); it.hasNext(); ) {
                    ISpellPart part = it.next();
                    if (part.getType() == ISpellPart.SpellPartType.MODIFIER) continue;
                    if (part.getType() == ISpellPart.SpellPartType.COMPONENT) {
                        HashSet<ISpellPartStat> stats = new HashSet<>(((ISpellComponent) part).getStatsUsed());
                        stats.retainAll(((ISpellModifier) item).getStatsModified());
                        yield !stats.isEmpty();
                    }
                    if (part.getType() == ISpellPart.SpellPartType.SHAPE) {
                        HashSet<ISpellPartStat> stats = new HashSet<>(((ISpellShape) part).getStatsUsed());
                        stats.retainAll(((ISpellModifier) item).getStatsModified());
                        yield !stats.isEmpty();
                    }
                }
                yield !shapeGroupsEmpty && shapeGroupDropZones.stream().map(mapper).anyMatch(isValidInShapeGroup);
            }
            case SHAPE -> shapeGroupDropZones.stream().map(mapper).allMatch(isValidInShapeGroup);
        };
    }

    private boolean isValidInShapeGroup(List<ISpellPart> items, ISpellPart item) {
        Deque<ISpellPart> deque = new ArrayDeque<>(items);
        return switch (item.getType()) {
            case COMPONENT -> false;
            case MODIFIER -> {
                if (deque.isEmpty()) yield false;
                for (Iterator<ISpellPart> it = deque.descendingIterator(); it.hasNext(); ) {
                    ISpellPart part = it.next();
                    if (part.getType() == ISpellPart.SpellPartType.SHAPE) {
                        HashSet<ISpellPartStat> stats = new HashSet<>(((ISpellShape) part).getStatsUsed());
                        stats.retainAll(((ISpellModifier) item).getStatsModified());
                        yield !stats.isEmpty();
                    }
                }
                yield true;
            }
            case SHAPE -> {
                if (((ISpellShape) item).needsToComeFirst()) yield deque.isEmpty();
                if (((ISpellShape) item).needsPrecedingShape() && deque.isEmpty()) yield false;
                for (Iterator<ISpellPart> it = deque.descendingIterator(); it.hasNext(); ) {
                    ISpellPart part = it.next();
                    if (part.getType() == ISpellPart.SpellPartType.SHAPE) yield !((ISpellShape) part).isEndShape();
                }
                yield true;
            }
        };
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
        for (int sg = 0; sg < Config.SERVER.MAX_SHAPE_GROUPS.get(); sg++) {
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
    public void onClose() {
        sync();
        super.onClose();
    }

    private void sync() {
        menu.sendDataToServer(Component.literal(nameBar.getValue()), DraggableWithData.dataList(spellStackDropZone.items()), shapeGroupDropZones.stream().map(DropArea::items).<List<ResourceLocation>>map(DraggableWithData::dataList).toList());
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button) | dragPane.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return dragPane.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputConstants.KEY_ESCAPE && shouldCloseOnEsc()) {
            onClose();
            return true;
        } else if (keyCode == InputConstants.KEY_TAB) {
            boolean flag = !hasShiftDown();
            if (!changeFocus(flag)) {
                changeFocus(flag);
            }
            return false;
        } else {
            return getFocused() != null && getFocused().keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public Minecraft getMinecraft() {
        return minecraft != null ? minecraft : Minecraft.getInstance();
    }

    private void setFromRecipe(ISpell spell) {
        var registryAccess = getMinecraft().getConnection().registryAccess();
        var skillRegistry = registryAccess.registryOrThrow(Skill.REGISTRY_KEY);
        spellStackDropZone = new BasicDropZone(spellStackDropZone != null ? spellStackDropZone.getX() : 0, spellStackDropZone != null ? spellStackDropZone.getY() : 0, 141, 18, ICON_SIZE, ICON_SIZE, 4, spellStackDropZone);
        spellStackDropZone.clear();
        for (ISpellPart iSpellPart : spell.spellStack().parts()) {
            spellStackDropZone.add(new DraggableWithData<>(0, 0, 0, 0, SkillIconAtlas.instance().getSprite(iSpellPart.getId()), skillRegistry.get(iSpellPart.getId()).getDisplayName(registryAccess), iSpellPart.getId()));
        }
        int i = 0;
        for (ShapeGroup shapeGroup : spell.shapeGroups()) {
            BasicDropZone zone;
            if (shapeGroupDropZones.size() > i) {
                BasicDropZone prev = shapeGroupDropZones.get(i);
                zone = new BasicDropZone(prev.getX(), prev.getY(), SHAPE_GROUP_WIDTH, SHAPE_GROUP_HEIGHT, ICON_SIZE, ICON_SIZE, 1, 8, prev);
            } else {
                zone = new BasicDropZone(0, 0, SHAPE_GROUP_WIDTH, SHAPE_GROUP_HEIGHT, ICON_SIZE, ICON_SIZE, 1, 8, null);
            }
            zone.clear();
            for (ISpellPart spellPart : shapeGroup.parts()) {
                zone.add(new DraggableWithData<>(0, 0, 0, 0, SkillIconAtlas.instance().getSprite(spellPart.getId()), skillRegistry.get(spellPart.getId()).getDisplayName(registryAccess), spellPart.getId()));
            }
            if (shapeGroupDropZones.size() > i) {
                shapeGroupDropZones.set(i, zone);
            } else {
                shapeGroupDropZones.add(zone);
            }
            i++;
        }
        if (minecraft != null) {
            init();
        }
    }
}
