package at.minecraftschurli.mods.arsmagicalegacy.client.layer;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.LifeWardAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.GuiLayer;

public class LifeWardLayer implements GuiLayer {
    private static final Identifier HEART = ArsMagicaApi.id("life_ward_heart");
    private static final Identifier HEART_HALF = ArsMagicaApi.id("life_ward_heart_half");

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (AMClientUtil.mc().options.hideGui) return;
        Player player = AMClientUtil.player();
        if (player == null || player.isSpectator()) return;
        LifeWardAttachment attachment = player.getData(AMAttachments.LIFE_WARD);
        if (attachment.isEmpty()) return;
        int x = AMClientConfig.LIFE_WARD_X_ANCHOR.get().getLocation(AMClientConfig.LIFE_WARD_X);
        int y = AMClientConfig.LIFE_WARD_Y_ANCHOR.get().getLocation(AMClientConfig.LIFE_WARD_Y);
        for (int i = (int) attachment.health(); i > 0; i -= 2) {
            AMClientUtil.blitSprite(graphics, i == 1 ? HEART_HALF : HEART, x, y, 9, 9);
            x += 8;
        }
    }
}
