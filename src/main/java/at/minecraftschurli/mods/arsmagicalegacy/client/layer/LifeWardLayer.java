package at.minecraftschurli.mods.arsmagicalegacy.client.layer;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.LifeWardAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;

public class LifeWardLayer extends AMGuiLayer {
    private static final Identifier HEART = ArsMagicaApi.id("life_ward_heart");
    private static final Identifier HEART_HALF = ArsMagicaApi.id("life_ward_heart_half");

    public LifeWardLayer() {
        super(AMClientConfig.LIFE_WARD_X_ANCHOR, AMClientConfig.LIFE_WARD_Y_ANCHOR, AMClientConfig.LIFE_WARD_X, AMClientConfig.LIFE_WARD_Y);
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, LocalPlayer player) {
        if (player.isCreative()) return;
        LifeWardAttachment attachment = player.getData(AMAttachments.LIFE_WARD);
        if (attachment.isEmpty()) return;
        int x = 0;
        int y = 0;
        for (int i = (int) attachment.health(); i > 0; i -= 2) {
            AMClientUtil.blitSprite(graphics, i == 1 ? HEART_HALF : HEART, x, y, 9, 9);
            x += 8;
            if (x >= 80) {
                y -= 10;
                x = 0;
            }
        }
    }
}
