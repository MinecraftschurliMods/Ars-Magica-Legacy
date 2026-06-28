package at.minecraftschurli.mods.arsmagicalegacy.client.gui.occulus;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;

public class OcculusTabButton extends Button {
    public static final int SIZE = 22;
    public static final int ICON_SIZE = 20;
    private static final Identifier TEXTURE = ArsMagicaApi.id("textures/gui/occulus/tab_button.png");
    private final Holder<OcculusTab> tab;

    public OcculusTabButton(Holder<OcculusTab> tab, int x, int y, OnPress onPress) {
        super(x, y, SIZE, SIZE, OcculusTab.getName(tab), onPress, DEFAULT_NARRATION);
        this.tab = tab;
        setTooltip(Tooltip.create(getMessage()));
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        AMClientUtil.blit(graphics, TEXTURE, getX(), getY(), SIZE, SIZE);
        AMClientUtil.blit(graphics, OcculusTab.getIcon(tab), getX() + 1, getY() + 1, ICON_SIZE, ICON_SIZE);
    }
}
