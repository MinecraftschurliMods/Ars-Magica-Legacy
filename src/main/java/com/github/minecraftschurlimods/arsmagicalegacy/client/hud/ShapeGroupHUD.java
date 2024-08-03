package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable.ShapeGroupArea;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable.SpellPartDraggable;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.betterhudlib.HUDElement;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class ShapeGroupHUD extends HUDElement {
    private static final ResourceLocation GUI = ArsMagicaAPI.resource("textures/gui/inscription_table.png");

    public ShapeGroupHUD() {
        super(Config.CLIENT.SHAPE_GROUP_ANCHOR_X, Config.CLIENT.SHAPE_GROUP_ANCHOR_Y, Config.CLIENT.SHAPE_GROUP_X::get, Config.CLIENT.SHAPE_GROUP_Y::get, () -> ShapeGroupArea.WIDTH * 5, () -> ShapeGroupArea.HEIGHT);
    }

    @Override
    public void draw(GuiGraphics graphics, DeltaTracker partialTicks) {
        Player player = ClientHelper.getLocalPlayer();
        if (player == null || Minecraft.getInstance().options.hideGui) return;
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (mainHand.getItem() instanceof SpellBookItem) {
            mainHand = SpellBookItem.getSelectedSpell(mainHand);
        } else if (offHand.getItem() instanceof SpellBookItem) {
            offHand = SpellBookItem.getSelectedSpell(offHand);
        }
        if (mainHand.getItem() instanceof ISpellItem) {
            renderShapeGroupHUD(graphics, mainHand);
        } else if (offHand.getItem() instanceof ISpellItem) {
            renderShapeGroupHUD(graphics, offHand);
        }
    }

    @Override
    protected void onPositionUpdate(AnchorX anchorX, AnchorY anchorY, int x, int y) {
        Config.CLIENT.SHAPE_GROUP_ANCHOR_X.set(anchorX);
        Config.CLIENT.SHAPE_GROUP_ANCHOR_Y.set(anchorY);
        Config.CLIENT.SHAPE_GROUP_X.set(x);
        Config.CLIENT.SHAPE_GROUP_Y.set(y);
    }

    @Override
    protected void save() {
        Config.CLIENT.save();
    }

    private void renderShapeGroupHUD(GuiGraphics graphics, ItemStack itemStack) {
        ISpell spell = ArsMagicaAPI.get().getSpellHelper().getSpell(itemStack);
        List<ShapeGroup> shapeGroups = spell.shapeGroups();
        if (shapeGroups.isEmpty()) return;
        int activeShapeGroup = spell.currentShapeGroupIndex();
        Window window = Minecraft.getInstance().getWindow();
        int x = getX(window.getScreenWidth());
        int y = getY(window.getScreenHeight());
        for (int i = 0; i < shapeGroups.size(); i++) {
            ShapeGroup shapeGroup = shapeGroups.get(i);
            if (shapeGroup.isEmpty()) continue;
            graphics.blit(GUI, x + i * ShapeGroupArea.WIDTH, y, 5, 220, 18, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT, 256, 256);
            if (i != activeShapeGroup) {
                graphics.pose().pushPose();
                graphics.pose().translate(0, 0, 10);
                graphics.fill(x + i * ShapeGroupArea.WIDTH, y, x + ShapeGroupArea.WIDTH + i * ShapeGroupArea.WIDTH, y + ShapeGroupArea.HEIGHT, ShapeGroupArea.DISABLED_OVERLAY_COLOR);
                graphics.pose().popPose();
            }
            List<ISpellPart> parts = shapeGroup.parts();
            for (int j = 0; j < ShapeGroupArea.ROWS; j++) {
                for (int k = 0; k < ShapeGroupArea.COLUMNS; k++) {
                    int index = j * ShapeGroupArea.COLUMNS + k;
                    if (index >= parts.size()) continue;
                    ISpellPart part = parts.get(index);
                    graphics.pose().pushPose();
                    graphics.blit(x + i * ShapeGroupArea.WIDTH + k * SpellPartDraggable.SIZE + ShapeGroupArea.X_PADDING, y + j * SpellPartDraggable.SIZE + ShapeGroupArea.Y_PADDING, 10, SpellPartDraggable.SIZE, SpellPartDraggable.SIZE, SkillIconAtlas.instance().getSprite(part.getId()));
                    graphics.pose().popPose();
                }
            }
        }
    }
}
