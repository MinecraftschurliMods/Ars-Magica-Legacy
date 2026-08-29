package at.minecraftschurli.mods.arsmagicalegacy.client.layer;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.mods.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.inscriptiontable.Draggable;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.inscriptiontable.ShapeGroupArea;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;

import java.util.List;

public class ShapeGroupsLayer extends AMGuiLayer {
    private static final Identifier TEXTURE = ArsMagicaApi.id("textures/gui/shape_group.png");
    private static final int ROWS = ShapeGroupArea.ROWS;
    private static final int COLUMNS = ShapeGroupArea.COLUMNS;
    private static final int X_PADDING = ShapeGroupArea.X_PADDING;
    private static final int Y_PADDING = ShapeGroupArea.Y_PADDING;
    private static final int WIDTH = ShapeGroupArea.WIDTH;
    private static final int HEIGHT = ShapeGroupArea.HEIGHT;
    private static final int SIZE = Draggable.SIZE;

    public ShapeGroupsLayer() {
        super(AMClientConfig.SHAPE_GROUPS_X_ANCHOR, AMClientConfig.SHAPE_GROUPS_Y_ANCHOR, AMClientConfig.SHAPE_GROUPS_X, AMClientConfig.SHAPE_GROUPS_Y);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, LocalPlayer player) {
        ItemStack item = player.getMainHandItem();
        if (!item.is(AMTags.Items.SHOWS_SPELL_VISUALS) || !item.has(AMDataComponents.SPELL)) {
            item = player.getOffhandItem();
            if (!item.is(AMTags.Items.SHOWS_SPELL_VISUALS) || !item.has(AMDataComponents.SPELL)) return;
        }
        Spell spell = item.get(AMDataComponents.SPELL);
        List<SpellShapeGroup> shapeGroups = spell.shapeGroups();
        for (int i = 0; i < shapeGroups.size(); i++) {
            int x = i * WIDTH;
            List<SpellPart> shapeGroup = shapeGroups.get(i).parts();
            if (!shapeGroup.isEmpty()) {
                AMClientUtil.blit(graphics, TEXTURE, x, 0, WIDTH, HEIGHT);
                for (int j = 0; j < ROWS; j++) {
                    for (int k = 0; k < COLUMNS; k++) {
                        int index = j * COLUMNS + k;
                        if (index < shapeGroup.size()) {
                            AMClientUtil.blitSprite(graphics, SkillAtlasHolder.getSprite(AMRegistries.SPELL_PARTS.getKey(shapeGroup.get(index))), x + k * SIZE + X_PADDING, j * SIZE + Y_PADDING, SIZE, SIZE);
                        }
                    }
                }
                if (i != spell.activeShapeGroup()) {
                    graphics.fill(x, 0, WIDTH, HEIGHT, 0x7f000000);
                }
            }
        }
    }
}
