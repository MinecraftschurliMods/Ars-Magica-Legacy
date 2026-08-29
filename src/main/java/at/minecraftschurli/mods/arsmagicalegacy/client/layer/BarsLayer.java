package at.minecraftschurli.mods.arsmagicalegacy.client.layer;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public class BarsLayer extends AMGuiLayer {
    private static final Identifier TEXTURE = ArsMagicaApi.id("textures/gui/bar.png");
    private static final int WIDTH = 80;
    private static final int HEIGHT = 10;

    public BarsLayer() {
        super(AMClientConfig.BARS_X_ANCHOR, AMClientConfig.BARS_Y_ANCHOR, AMClientConfig.BARS_X, AMClientConfig.BARS_Y);
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, LocalPlayer player) {
        MagicHelper magicHelper = ArsMagicaApi.magicHelper();
        if (!magicHelper.knowsMagic(player)) return;
        ItemStackTemplate book = ArsMagicaApi.book();
        if (!player.getInventory().contains(stack -> stack.is(AMTags.Items.SHOWS_BARS_LAYER) || ItemStack.isSameItemSameComponents(stack, book))) return;
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        int level = magicHelper.getLevel(player);
        boolean renderLevelAtTop = AMClientConfig.RENDER_LEVEL_AT_TOP.getAsBoolean();
        String text = String.valueOf(level);
        Font font = AMClientUtil.font();
        renderOutlineText(graphics, font, Component.literal(text), (WIDTH - font.width(text)) / 2, renderLevelAtTop ? 0 : 30, 0xff7777ff);
        renderBar(graphics, font, renderLevelAtTop ? 10 : 20, magicHelper.getXp(player), magicHelper.getXpForNextLevel(level), AMTranslations.BARS_VALUE_XP_KEY, 0xff7777ff);
        renderBar(graphics, font, renderLevelAtTop ? 20 : 0, manaHelper.getMana(player), manaHelper.getMaxMana(player), AMTranslations.BARS_VALUE_MANA_KEY, 0xff99ffff);
        renderBar(graphics, font, renderLevelAtTop ? 30 : 10, burnoutHelper.getBurnout(player), burnoutHelper.getMaxBurnout(player), AMTranslations.BARS_VALUE_BURNOUT_KEY, 0xff880000);
    }

    private static void renderBar(GuiGraphicsExtractor graphics, Font font, int y, double value, double maxValue, String translationKey, int color) {
        AMClientUtil.blitFull(graphics, TEXTURE, 0, y, WIDTH + 1, HEIGHT - 1);
        AMClientUtil.blitFull(graphics, TEXTURE, 2, y + 2, 2, HEIGHT + 1, maxValue <= 0 ? -1 : (int) Math.max(Math.ceil(WIDTH * value / maxValue), 0) - 1, HEIGHT - 3, color);
        if (AMClientConfig.SHOW_VALUES.get()) {
            Component text = Component.translatable(translationKey, String.format("%.2f", value), String.format("%.2f", maxValue));
            renderOutlineText(graphics, font, text, AMClientConfig.BARS_X_ANCHOR.get() == LayerAnchor.X.RIGHT ? -3 - font.width(text) : 4 + WIDTH, y + 1, color);
        }
    }

    private static void renderOutlineText(GuiGraphicsExtractor graphics, Font font, Component text, int x, int y, int color) {
        graphics.text(font, text, x + 1, y, 0xff000000, false);
        graphics.text(font, text, x - 1, y, 0xff000000, false);
        graphics.text(font, text, x, y + 1, 0xff000000, false);
        graphics.text(font, text, x, y - 1, 0xff000000, false);
        graphics.text(font, text, x, y, color, false);
    }
}
