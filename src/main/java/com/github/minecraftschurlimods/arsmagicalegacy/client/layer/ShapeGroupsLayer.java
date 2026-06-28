package com.github.minecraftschurlimods.arsmagicalegacy.client.layer;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.client.AMClientConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable.Draggable;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable.ShapeGroupArea;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.GuiLayer;

import java.util.List;

public class ShapeGroupsLayer implements GuiLayer {
    private static final Identifier TEXTURE = ArsMagicaApi.id("textures/gui/shape_group.png");
    private static final int ROWS = ShapeGroupArea.ROWS;
    private static final int COLUMNS = ShapeGroupArea.COLUMNS;
    private static final int X_PADDING = ShapeGroupArea.X_PADDING;
    private static final int Y_PADDING = ShapeGroupArea.Y_PADDING;
    private static final int WIDTH = ShapeGroupArea.WIDTH;
    private static final int HEIGHT = ShapeGroupArea.HEIGHT;
    private static final int SIZE = Draggable.SIZE;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (AMClientUtil.mc().options.hideGui) return;
        Player player = AMClientUtil.player();
        if (player == null || player.isSpectator()) return;
        ItemStack item = player.getMainHandItem();
        if (!item.is(AMTags.Items.SHOWS_SPELL_VISUALS) || !item.has(AMDataComponents.SPELL)) {
            item = player.getOffhandItem();
            if (!item.is(AMTags.Items.SHOWS_SPELL_VISUALS) || !item.has(AMDataComponents.SPELL)) return;
        }
        Spell spell = item.get(AMDataComponents.SPELL);
        int x = AMClientConfig.SHAPE_GROUPS_X_ANCHOR.get().getLocation(AMClientConfig.SHAPE_GROUPS_X);
        int y = AMClientConfig.SHAPE_GROUPS_Y_ANCHOR.get().getLocation(AMClientConfig.SHAPE_GROUPS_Y);
        graphics.pose().pushMatrix();
        graphics.pose().translate(x, y);
        List<SpellShapeGroup> shapeGroups = spell.shapeGroups();
        for (int i = 0; i < shapeGroups.size(); i++) {
            graphics.pose().pushMatrix();
            graphics.pose().translate(i * WIDTH, 0);
            List<SpellPart> shapeGroup = shapeGroups.get(i).parts();
            if (!shapeGroup.isEmpty()) {
                AMClientUtil.blit(graphics, TEXTURE, 0, 0, WIDTH, HEIGHT);
                for (int j = 0; j < ROWS; j++) {
                    for (int k = 0; k < COLUMNS; k++) {
                        int index = j * COLUMNS + k;
                        if (index < shapeGroup.size()) {
                            AMClientUtil.blit(graphics, SkillAtlasHolder.getSprite(AMRegistries.SPELL_PARTS.getKey(shapeGroup.get(index))), k * SIZE + X_PADDING, j * SIZE + Y_PADDING, SIZE, SIZE);
                        }
                    }
                }
                if (i != spell.activeShapeGroup()) {
                    graphics.fill(0, 0, WIDTH, HEIGHT, 0x7f000000);
                }
            }
            graphics.pose().popMatrix();
        }
        graphics.pose().popMatrix();
    }
}
