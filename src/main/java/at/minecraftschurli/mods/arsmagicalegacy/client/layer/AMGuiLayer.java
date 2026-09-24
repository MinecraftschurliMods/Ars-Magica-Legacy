package at.minecraftschurli.mods.arsmagicalegacy.client.layer;

import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.joml.Matrix3x2fStack;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public abstract class AMGuiLayer implements GuiLayer {
    private final Supplier<LayerAnchor.X> xAnchor;
    private final Supplier<LayerAnchor.Y> yAnchor;
    private final IntSupplier xDistance;
    private final IntSupplier yDistance;

    public AMGuiLayer(Supplier<LayerAnchor.X> xAnchor, Supplier<LayerAnchor.Y> yAnchor, IntSupplier xDistance, IntSupplier yDistance) {
        this.xAnchor = xAnchor;
        this.yAnchor = yAnchor;
        this.xDistance = xDistance;
        this.yDistance = yDistance;
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (AMClientUtil.mc().options.hideGui) return;
        LocalPlayer player = AMClientUtil.player();
        if (player == null || player.isSpectator()) return;
        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        pose.translate(xAnchor.get().getLocation(xDistance), yAnchor.get().getLocation(yDistance));
        render(graphics, deltaTracker, player);
        pose.popMatrix();
    }

    public abstract void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, LocalPlayer player);
}
