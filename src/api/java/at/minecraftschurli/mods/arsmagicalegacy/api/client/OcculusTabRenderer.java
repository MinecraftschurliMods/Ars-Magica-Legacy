package at.minecraftschurli.mods.arsmagicalegacy.api.client;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;

import java.util.List;

/// Represents the rendering portion of an [OcculusTab]. One renderer may be associated with multiple [OcculusTab]s.
/// For example, in Ars Magica: Legacy, multiple tabs use the default skill tree renderer.
///
/// Register [OcculusTabRenderer]s during [RegisterOcculusTabRenderersEvent], using the [OcculusTabRenderer.Factory] interface.
public abstract class OcculusTabRenderer extends AbstractContainerEventHandler implements Renderable {
    public static final int TAB_SIZE = 196;
    protected final Holder<OcculusTab> occulusTab;

    /// Constructs a new [OcculusTabRenderer].
    ///
    /// @param occulusTab The [Holder] of the [OcculusTab] being rendered.
    public OcculusTabRenderer(Holder<OcculusTab> occulusTab) {
        this.occulusTab = occulusTab;
    }

    /// Note: Coordinates are normalized to the renderer's top left corner, i.e., rendering at 0/0 uses the top left corner of the occulus frame,
    /// not the top left corner of the screen. Additionally, a scissor is enabled around the occulus frame.
    /// @see Renderable#extractRenderState(GuiGraphicsExtractor, int, int, float) for parameter documentation.
    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, OcculusTab.getBackground(occulusTab), 0, 0, 0, 0, TAB_SIZE, TAB_SIZE, TAB_SIZE, TAB_SIZE);
    }

    /// Render tooltips here. This is kept in a separate method to allow drawing outside the scissor space.
    ///
    /// @param graphics    The [GuiGraphicsExtractor] to use.
    /// @param mouseX      The mouse X position.
    /// @param mouseY      The mouse Y position.
    /// @param partialTick The partial tick value.
    public void renderTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
    }

    /// @return Whether to have the occulus screen draw the skill point panel widget when this renderer is active or not.
    public boolean hasSkillPointPanel() {
        return true;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

    /// Factory interface used in registering the renderer.
    @FunctionalInterface
    public interface Factory {
        /// @param occulusTab The [Holder] of the [OcculusTab] being rendered.
        OcculusTabRenderer create(Holder<OcculusTab> occulusTab);
    }
}
