package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.betterhudlib.HUDElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public final class ShapeGroupHUD extends HUDElement {
    public ShapeGroupHUD() {
        super(Config.CLIENT.SHAPE_GROUP_ANCHOR_X, Config.CLIENT.SHAPE_GROUP_ANCHOR_Y, Config.CLIENT.SHAPE_GROUP_X::get, Config.CLIENT.SHAPE_GROUP_Y::get, () -> 180, () -> 34);
    }

    @Override
    public void draw(ForgeGui forgeGui, GuiGraphics graphics, float partialTicks) {
        // TODO
    }
}
