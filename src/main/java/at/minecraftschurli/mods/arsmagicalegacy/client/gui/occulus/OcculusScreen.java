package at.minecraftschurli.mods.arsmagicalegacy.client.gui.occulus;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.packet.ForgetSkillsPacket;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class OcculusScreen extends Screen {
    private static final Identifier BUTTON_INDICATOR = ArsMagicaApi.id("textures/gui/occulus/tab_button_indicator.png");
    private static final Identifier FRAME = ArsMagicaApi.id("textures/gui/occulus/frame.png");
    private static final Identifier SKILL_POINTS = ArsMagicaApi.id("textures/gui/occulus/skill_points.png");
    private static final int SIZE = 210;
    private static final int FRAME_SIZE = 7;
    private final List<Holder<OcculusTab>> tabs = new ArrayList<>();
    private final List<OcculusTabButton> buttons = new ArrayList<>();
    @Nullable
    private Button nextButton;
    @Nullable
    private Button prevButton;
    @Nullable
    private OcculusTabRenderer renderer;
    private int leftPos;
    private int topPos;
    private int tabX;
    private int tabY;
    private int tab = 0;
    private int page = 0;
    private int maxPage = 0;

    public OcculusScreen() {
        super(AMTranslations.OCCULUS);
    }

    @Override
    protected void init() {
        leftPos = (width - SIZE) / 2;
        topPos = (height - SIZE - OcculusTabButton.SIZE - 24) / 2;
        tabX = leftPos + FRAME_SIZE;
        tabY = topPos + OcculusTabButton.SIZE + FRAME_SIZE;
        tabs.clear();
        buttons.clear();
        LocalPlayer player = Objects.requireNonNull(AMClientUtil.player());
        Registry<OcculusTab> registry = AMRegistries.occulusTabs(true);
        List<? extends Holder<OcculusTab>> list = registry
            .listElements()
            .sorted(Comparator.comparingInt(e -> e.value().index()))
            .toList();
        if (list.isEmpty()) return;
        tabs.addAll(list);
        if (list.size() < 10) {
            // we don't need page buttons
            maxPage = 0;
            for (int i = 0; i < list.size(); i++) {
                final int j = i;
                buttons.add(addRenderableWidget(new OcculusTabButton(list.get(i), leftPos + 6 + i * OcculusTabButton.SIZE, topPos, _ -> setTab(j))));
            }
        } else {
            // we need page buttons
            maxPage = list.size() / 7;
            for (int i = 0; i < list.size(); i++) {
                final int j = i;
                buttons.add(addRenderableWidget(new OcculusTabButton(list.get(i), leftPos + 28 + i % 7 * OcculusTabButton.SIZE, topPos, _ -> setTab(j))));
            }
            nextButton = Button.builder(AMTranslations.OCCULUS_NEXT, _ -> nextPage()).bounds(leftPos + SIZE - 20, topPos, 20, 20).build();
            prevButton = Button.builder(AMTranslations.OCCULUS_PREV, _ -> prevPage()).bounds(leftPos, topPos, 20, 20).build();
            onPageChange();
        }
        setRenderer(tabs.getFirst());
        Button button = addRenderableWidget(Button.builder(AMTranslations.OCCULUS_FORGET_ALL, _ -> forgetAll())
            .bounds(width / 2 - 100, topPos + SIZE + OcculusTabButton.SIZE + 4, 98, 20)
            .tooltip(Tooltip.create(AMTranslations.OCCULUS_FORGET_ALL_TOOLTIP))
            .build());
        button.active = player.getInventory().contains(AMTags.Items.OCCULUS_FORGET_ALL) || player.isCreative();
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, _ -> onClose())
            .bounds(width / 2 + 2, topPos + SIZE + OcculusTabButton.SIZE + 4, 98, 20)
            .build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        AMClientUtil.blitFull(graphics, FRAME, leftPos, topPos + OcculusTabButton.SIZE, SIZE, SIZE);
        AMClientUtil.blit(graphics, BUTTON_INDICATOR, maxPage == 0 ? leftPos + 6 + tab * OcculusTabButton.SIZE : leftPos + 28 + tab % 7 * OcculusTabButton.SIZE, topPos + OcculusTabButton.SIZE, OcculusTabButton.SIZE, FRAME_SIZE);
        if (renderer == null) return;
        if (renderer.hasSkillPointPanel()) {
            LocalPlayer player = Objects.requireNonNull(AMClientUtil.player());
            List<? extends Holder<SkillPoint>> holders = AMRegistries.skillPoints(true)
                .listElements()
                .toList();
            List<MutableComponent> components = holders
                .stream()
                .map(e -> ArsMagicaApi.magicHelper().getSkillPoint(player, e))
                .map(String::valueOf)
                .map(Component::literal)
                .toList();
            int width = 24 + components.stream()
                .mapToInt(AMClientUtil.font()::width)
                .max()
                .orElse(0);
            int height = components.size() * 16 + 4;
            AMClientUtil.blitFull(graphics, SKILL_POINTS, leftPos - width, topPos + OcculusTabButton.SIZE, width, height);
            AMClientUtil.blitFull(graphics, SKILL_POINTS, leftPos - width, topPos + OcculusTabButton.SIZE + height, 0, 252, width, 4);
            for (int i = 0; i < holders.size(); i++) {
                Holder<SkillPoint> holder = holders.get(i);
                ItemStack stack = AMItems.INFINITY_ORB.toStack();
                stack.set(AMDataComponents.SKILL_POINT, holder);
                graphics.item(stack, leftPos - width + 4, topPos + OcculusTabButton.SIZE + 4 + i * 16);
                graphics.text(AMClientUtil.font(), components.get(i), leftPos - width + 22, topPos + OcculusTabButton.SIZE + 9 + i * 16, 0xff000000 | holder.value().color(), false);
            }
        }
        graphics.enableScissor(tabX, tabY, tabX + OcculusTabRenderer.TAB_SIZE, tabY + OcculusTabRenderer.TAB_SIZE);
        graphics.pose().pushMatrix();
        graphics.pose().translate(tabX, tabY);
        renderer.extractRenderState(graphics, mouseX - tabX, mouseY - tabY, partialTick);
        graphics.pose().popMatrix();
        graphics.disableScissor();
        if (mouseX >= tabX && mouseX < tabX + OcculusTabRenderer.TAB_SIZE && mouseY >= tabY && mouseY < tabY + OcculusTabRenderer.TAB_SIZE) {
            renderer.renderTooltip(graphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void setTab(int tab) {
        if (this.tab == tab) return;
        this.tab = tab;
        setRenderer(tabs.get(tab));
    }

    private void setRenderer(Holder<OcculusTab> occulusTab) {
        OcculusTabRenderer.Factory factory = ArsMagicaClientApi.occulusTabRendererFactory(occulusTab);
        if (factory != null) {
            renderer = factory.create(occulusTab);
        }
    }

    private void nextPage() {
        page++;
        onPageChange();
    }

    private void prevPage() {
        page--;
        onPageChange();
    }

    private void onPageChange() {
        if (nextButton != null) {
            nextButton.active = page < maxPage;
        }
        if (prevButton != null) {
            prevButton.active = page > 0;
        }
        buttons.forEach(button -> button.visible = false);
        for (int i = page * 7; i < (page + 1) * 7 && i < buttons.size(); i++) {
            buttons.get(i).visible = true;
        }
    }

    private void forgetAll() {
        ClientPacketDistributor.sendToServer(new ForgetSkillsPacket());
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        return super.mouseClicked(event, doubleClick) || renderer != null && renderer.mouseClicked(new MouseButtonEvent(event.x() - tabX, event.y() - tabY, event.buttonInfo()), doubleClick);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        return super.mouseDragged(event, dx, dy) || renderer != null && renderer.mouseDragged(new MouseButtonEvent(event.x() - tabX, event.y() - tabY, event.buttonInfo()), dx, dy);
    }
}
